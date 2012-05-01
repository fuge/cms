package foo.cms.service;

import java.io.IOException;
import java.net.URI;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foo.cms.entity.assist.CmsAcquisition;
import foo.cms.entity.assist.CmsAcquisitionHistory;
import foo.cms.entity.assist.CmsAcquisitionTemp;
import foo.cms.entity.assist.CmsAcquisition.AcquisitionResultType;
import foo.cms.entity.main.CmsSite;
import foo.cms.entity.main.Content;
import foo.cms.manager.assist.CmsAcquisitionHistoryMng;
import foo.cms.manager.assist.CmsAcquisitionMng;
import foo.cms.manager.assist.CmsAcquisitionTempMng;

@Service
public class AcquisitionSvcImpl implements AcquisitionSvc {
	private Logger log = LoggerFactory.getLogger(AcquisitionSvcImpl.class);

	public boolean start(Integer id) {
		CmsAcquisition acqu = cmsAcquisitionMng.findById(id);
		if (acqu == null || acqu.getStatus() == CmsAcquisition.START) {
			return false;
		}
		Thread thread = new AcquisitionThread(acqu);
		thread.start();
		return true;
	}
	
	private void end(CmsAcquisition acqu){
		Integer siteId = acqu.getSite().getId();
		cmsAcquisitionMng.end(acqu.getId());
		CmsAcquisition acquisition = cmsAcquisitionMng.popAcquFromQueue(siteId);
		if (acquisition != null) {
			Integer id = acquisition.getId();
			start(id);
		}
	}

	private CmsAcquisitionMng cmsAcquisitionMng;
	private CmsAcquisitionHistoryMng cmsAcquisitionHistoryMng;
	private CmsAcquisitionTempMng cmsAcquisitionTempMng;

	@Autowired
	public void setCmsAcquisitionMng(CmsAcquisitionMng cmsAcquisitionMng) {
		this.cmsAcquisitionMng = cmsAcquisitionMng;
	}

	@Autowired
	public void setCmsAcquisitionHistoryMng(
			CmsAcquisitionHistoryMng cmsAcquisitionHistoryMng) {
		this.cmsAcquisitionHistoryMng = cmsAcquisitionHistoryMng;
	}

	@Autowired
	public void setCmsAcquisitionTempMng(
			CmsAcquisitionTempMng cmsAcquisitionTempMng) {
		this.cmsAcquisitionTempMng = cmsAcquisitionTempMng;
	}

	private class AcquisitionThread extends Thread {
		private CmsAcquisition acqu;

		public AcquisitionThread(CmsAcquisition acqu) {
			super(acqu.getClass().getName() + "#" + acqu.getId());
			this.acqu = acqu;
		}

		@Override
		public void run() {
			if (acqu == null) {
				return;
			}
			acqu = cmsAcquisitionMng.start(acqu.getId());
			String[] plans = acqu.getAllPlans();
			HttpClient client = new DefaultHttpClient();
			CharsetHandler handler = new CharsetHandler(acqu.getPageEncoding());
			List<String> contentList;
			String url;
			int currNum = acqu.getCurrNum();
			int currItem = acqu.getCurrItem();
			Integer acquId = acqu.getId();
			for (int i = plans.length - currNum; i >= 0; i--) {
				url = plans[i];
				contentList = getContentList(client, handler, url, acqu
						.getLinksetStart(), acqu.getLinksetEnd(), acqu
						.getLinkStart(), acqu.getLinkEnd());
				String link;
				for (int j = contentList.size() - currItem; j >= 0; j--) {
					if (cmsAcquisitionMng.isNeedBreak(acqu.getId(),
							plans.length - i, contentList.size() - j,
							contentList.size())) {
						client.getConnectionManager().shutdown();
						log.info("Acquisition#{} breaked", acqu.getId());
						return;
					}
					if (acqu.getPauseTime() > 0) {
						try {
							Thread.sleep(acqu.getPauseTime());
						} catch (InterruptedException e) {
							log.warn(null, e);
						}
					}
					link = contentList.get(j);
					float curr = contentList.size() - j;
					float total = contentList.size();
					CmsAcquisitionTemp temp = AcquisitionSvcImpl.this.newTemp(
							url, link, contentList.size() - j, curr, total,
							acqu.getSite());
					CmsAcquisitionHistory history = AcquisitionSvcImpl.this
							.newHistory(url, link, acqu);
					saveContent(client, handler, acquId, link, acqu
							.getTitleStart(), acqu.getTitleEnd(), acqu
							.getContentStart(), acqu.getContentEnd(), temp,
							history);
				}
				currItem = 1;
			}
			client.getConnectionManager().shutdown();
			AcquisitionSvcImpl.this.end(acqu);
			log.info("Acquisition#{} complete", acqu.getId());
		}

		private List<String> getContentList(HttpClient client,
				CharsetHandler handler, String url, String linksetStart,
				String linksetEnd, String linkStart, String linkEnd) {
			List<String> list = new ArrayList<String>();
			try {
				HttpGet httpget = new HttpGet(new URI(url));
				String base = url.substring(0, url.indexOf("/", url
						.indexOf("//") + 2));
				String html = client.execute(httpget, handler);
				int start = html.indexOf(linksetStart);
				if (start == -1) {
					return list;
				}
				start += linksetStart.length();
				int end = html.indexOf(linksetEnd, start);
				if (end == -1) {
					return list;
				}
				String s = html.substring(start, end);
				start = 0;
				String link;
				log.info(s);
				while ((start = s.indexOf(linkStart, start)) != -1) {
					start += linkStart.length();
					end = s.indexOf(linkEnd, start);
					if (end == -1) {
						return list;
					} else {
						link = s.substring(start, end);
						if (!link.startsWith("http")) {
							link = base + link;
						}
						log.debug("content link: {}", link);
						list.add(link);
						start = end + linkEnd.length();
					}
				}
			} catch (Exception e) {
				log.warn(null, e);
			}
			return list;
		}

		private Content saveContent(HttpClient client, CharsetHandler handler,
				Integer acquId, String url, String titleStart, String titleEnd,
				String contentStart, String contentEnd,
				CmsAcquisitionTemp temp, CmsAcquisitionHistory history) {
			CmsAcquisition acqu = cmsAcquisitionMng.findById(acquId);
			history.setAcquisition(acqu);
			try {
				int start, end;
				HttpGet httpget = new HttpGet(new URI(url));
				String html = client.execute(httpget, handler);
				start = html.indexOf(titleStart);
				if (start == -1) {
					return handerResult(temp, history, null,
							AcquisitionResultType.TITLESTARTNOTFOUND);
				}
				start += titleStart.length();
				end = html.indexOf(titleEnd, start);
				if (end == -1) {
					return handerResult(temp, history, null,
							AcquisitionResultType.TITLEENDNOTFOUND);
				}
				String title = html.substring(start, end);
				if (cmsAcquisitionHistoryMng
						.checkExistByProperties(true, title)) {
					return handerResult(temp, history, title,
							AcquisitionResultType.TITLEEXIST, true);
				}
				start = html.indexOf(contentStart);
				if (start == -1) {
					return handerResult(temp, history, title,
							AcquisitionResultType.CONTENTSTARTNOTFOUND);
				}
				start += contentStart.length();
				end = html.indexOf(contentEnd, start);
				if (end == -1) {
					return handerResult(temp, history, title,
							AcquisitionResultType.CONTENTENDNOTFOUND);
				}
				String txt = html.substring(start, end);
				Content content = cmsAcquisitionMng.saveContent(title, txt,
						acquId, AcquisitionResultType.SUCCESS, temp, history);
				cmsAcquisitionTempMng.save(temp);
				cmsAcquisitionHistoryMng.save(history);
				return content;
			} catch (Exception e) {
				log.warn(null, e);
				return handerResult(temp, history, null,
						AcquisitionResultType.UNKNOW);
			}
		}

		private Content handerResult(CmsAcquisitionTemp temp,
				CmsAcquisitionHistory history, String title,
				AcquisitionResultType errorType) {
			return handerResult(temp, history, title, errorType, false);
		}

		private Content handerResult(CmsAcquisitionTemp temp,
				CmsAcquisitionHistory history, String title,
				AcquisitionResultType errorType, Boolean repeat) {
			temp.setDescription(errorType.name());
			temp.setTitle(title);
			cmsAcquisitionTempMng.save(temp);
			if (!repeat) {
				history.setTitle(title);
				history.setDescription(errorType.name());
				cmsAcquisitionHistoryMng.save(history);
			}
			return null;
		}
	}

	private CmsAcquisitionTemp newTemp(String channelUrl, String contentUrl,
			Integer id, Float curr, Float total, CmsSite site) {
		CmsAcquisitionTemp temp = new CmsAcquisitionTemp();
		temp.setChannelUrl(channelUrl);
		temp.setContentUrl(contentUrl);
		temp.setSeq(id);
		NumberFormat nf = NumberFormat.getPercentInstance();
		String percent = nf.format(curr / total);
		temp.setPercent(Integer.parseInt(percent.substring(0,
				percent.length() - 1)));
		temp.setSite(site);
		return temp;
	}

	private CmsAcquisitionHistory newHistory(String channelUrl,
			String contentUrl, CmsAcquisition acqu) {
		CmsAcquisitionHistory history = new CmsAcquisitionHistory();
		history.setChannelUrl(channelUrl);
		history.setContentUrl(contentUrl);
		history.setAcquisition(acqu);
		return history;
	}

	private class CharsetHandler implements ResponseHandler<String> {
		private String charset;

		public CharsetHandler(String charset) {
			this.charset = charset;
		}

		public String handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() >= 300) {
				throw new HttpResponseException(statusLine.getStatusCode(),
						statusLine.getReasonPhrase());
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				if (!StringUtils.isBlank(charset)) {
					return EntityUtils.toString(entity, charset);
				} else {
					return EntityUtils.toString(entity);
				}
			} else {
				return null;
			}
		}
	}
}
