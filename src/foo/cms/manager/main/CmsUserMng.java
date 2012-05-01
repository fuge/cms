package foo.cms.manager.main;

import java.util.List;

import foo.cms.entity.main.CmsSite;
import foo.cms.entity.main.CmsUser;
import foo.cms.entity.main.CmsUserExt;
import foo.common.email.EmailSender;
import foo.common.email.MessageTemplate;
import foo.common.page.Pagination;

public interface CmsUserMng {
	public Pagination getPage(String username, String email, Integer siteId,
			Integer groupId, Boolean disabled, Boolean admin, Integer rank,
			int pageNo, int pageSize);
	
	public List getList(String username, String email, Integer siteId,
			Integer groupId, Boolean disabled, Boolean admin, Integer rank);

	public List<CmsUser> getAdminList(Integer siteId, Boolean allChannel,
			Boolean disabled, Integer rank);

	public CmsUser findById(Integer id);

	public CmsUser findByUsername(String username);

	public CmsUser registerMember(String username, String email,
			String password, String ip, Integer groupId, CmsUserExt userExt);
	
	public CmsUser registerMember(String username, String email,
			String password, String ip, Integer groupId, CmsUserExt userExt, Boolean activation , EmailSender sender, MessageTemplate msgTpl) ;

	public void updateLoginInfo(Integer userId, String ip);

	public void updateUploadSize(Integer userId, Integer size);

	public void updatePwdEmail(Integer id, String password, String email);

	public boolean isPasswordValid(Integer id, String password);

	public CmsUser saveAdmin(String username, String email, String password,
			String ip, boolean viewOnly, boolean selfAdmin, int rank,
			Integer groupId, Integer[] roleIds, Integer[] channelIds,
			Integer[] siteIds, Byte[] steps, Boolean[] allChannels,
			CmsUserExt userExt);

	public CmsUser updateAdmin(CmsUser bean, CmsUserExt ext, String password,
			Integer groupId, Integer[] roleIds, Integer[] channelIds,
			Integer[] siteIds, Byte[] steps, Boolean[] allChannels);

	public CmsUser updateAdmin(CmsUser bean, CmsUserExt ext, String password,
			Integer groupId, Integer[] roleIds, Integer[] channelIds,
			Integer siteId, Byte step, Boolean allChannel);

	public CmsUser updateMember(Integer id, String email, String password,
			Boolean isDisabled, CmsUserExt ext, Integer groupId);
	
	public CmsUser updateUserConllection(CmsUser user,Integer cid,Integer operate);

	public void addSiteToUser(CmsUser user, CmsSite site, Byte checkStep);

	public CmsUser deleteById(Integer id);

	public CmsUser[] deleteByIds(Integer[] ids);

	public boolean usernameNotExist(String username);
	
	public boolean usernameNotExistInMember(String username);

	public boolean emailNotExist(String email);
}