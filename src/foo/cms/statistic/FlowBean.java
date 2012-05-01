package foo.cms.statistic;

public class FlowBean {
	private String accessDate;
	private String sessionId;
	private String page;
	
	public FlowBean(){}
	
	public FlowBean(String accessDate, String sessionId, String page){
		this.accessDate = accessDate;
		this.sessionId = sessionId;
		this.page = page;
	}
	
	public String getAccessDate() {
		return accessDate;
	}

	public void setAccessDate(String accessDate) {
		this.accessDate = accessDate;
	}

	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accessDate == null) ? 0 : accessDate.hashCode());
		result = prime * result + ((page == null) ? 0 : page.hashCode());
		result = prime * result
				+ ((sessionId == null) ? 0 : sessionId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FlowBean other = (FlowBean) obj;
		if (accessDate == null) {
			if (other.accessDate != null)
				return false;
		} else if (!accessDate.equals(other.accessDate))
			return false;
		if (page == null) {
			if (other.page != null)
				return false;
		} else if (!page.equals(other.page))
			return false;
		if (sessionId == null) {
			if (other.sessionId != null)
				return false;
		} else if (!sessionId.equals(other.sessionId))
			return false;
		return true;
	}
}
