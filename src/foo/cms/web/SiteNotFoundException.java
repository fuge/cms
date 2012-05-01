package foo.cms.web;

@SuppressWarnings("serial")
public class SiteNotFoundException extends RuntimeException {
	private String domain;

	public SiteNotFoundException(String domain) {
		super(domain);
		this.domain = domain;
	}

	public String getDomain() {
		return this.domain;
	}
}
