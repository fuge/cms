package foo.cms.entity.main;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class ContentTxt implements Serializable{
	private static final long serialVersionUID = 8494048187509013515L;
	public static String PAGE_START = "<p>[NextPage]";
	public static String PAGE_END = "[/NextPage]</p>";

	public int getTxtCount() {
		String txt = getTxt();
		if (StringUtils.isBlank(txt)) {
			return 1;
		} else {
			return StringUtils.countMatches(txt, PAGE_START) + 1;
		}
	}

	public String getTxtByNo(int pageNo) {
		String txt = getTxt();
		if (StringUtils.isBlank(txt) || pageNo < 1) {
			return null;
		}
		int start = 0, end = 0;
		for (int i = 0; i < pageNo; i++) {
			// 如果不是第一页
			if (i != 0) {
				start = txt.indexOf(PAGE_END, end);
				if (start == -1) {
					return null;
				} else {
					start += PAGE_END.length();
				}
			}
			end = txt.indexOf(PAGE_START, start);
			if (end == -1) {
				end = txt.length();
			}
		}
		return txt.substring(start, end);
	}

	public String getTitleByNo(int pageNo) {
		if (pageNo < 1) {
			return null;
		}
		String title = getContent().getTitle();
		if (pageNo == 1) {
			return title;
		}
		String txt = getTxt();
		int start = 0, end = 0;
		for (int i = 1; i < pageNo; i++) {
			start = txt.indexOf(PAGE_START, end);
			if (start == -1) {
				return title;
			} else {
				start += PAGE_START.length();
			}
			end = txt.indexOf(PAGE_END, start);
			if (end == -1) {
				return title;
			}
		}
		String result = txt.substring(start, end);
		if (!StringUtils.isBlank(result)) {
			return result;
		} else {
			return title;
		}
	}

	public void init() {
		blankToNull();
	}

	public void blankToNull() {
		if (StringUtils.isBlank(getTxt())) {
			setTxt(null);
		}
		if (StringUtils.isBlank(getTxt1())) {
			setTxt1(null);
		}
		if (StringUtils.isBlank(getTxt2())) {
			setTxt2(null);
		}
		if (StringUtils.isBlank(getTxt3())) {
			setTxt3(null);
		}
	}

	/**
	 * 是否所有属性都为空
	 * 
	 * @return
	 */
	public boolean isAllBlank() {
		return StringUtils.isBlank(getTxt()) && StringUtils.isBlank(getTxt1())
				&& StringUtils.isBlank(getTxt2())
				&& StringUtils.isBlank(getTxt3());
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public ContentTxt () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ContentTxt (Integer id) {
		this.setId(id);
	}

	/* [CONSTRUCTOR MARKER END] */
	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private Integer id;

	// fields
	private String txt;
	private String txt1;
	private String txt2;
	private String txt3;

	// one to one
	private foo.cms.entity.main.Content content;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="foreign"
     *  column="content_id"
     */
	public Integer getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (Integer id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: txt
	 */
	public String getTxt () {
		return txt;
	}

	/**
	 * Set the value related to the column: txt
	 * @param txt the txt value
	 */
	public void setTxt (String txt) {
		this.txt = txt;
	}


	/**
	 * Return the value associated with the column: txt1
	 */
	public String getTxt1 () {
		return txt1;
	}

	/**
	 * Set the value related to the column: txt1
	 * @param txt1 the txt1 value
	 */
	public void setTxt1 (String txt1) {
		this.txt1 = txt1;
	}


	/**
	 * Return the value associated with the column: txt2
	 */
	public String getTxt2 () {
		return txt2;
	}

	/**
	 * Set the value related to the column: txt2
	 * @param txt2 the txt2 value
	 */
	public void setTxt2 (String txt2) {
		this.txt2 = txt2;
	}


	/**
	 * Return the value associated with the column: txt3
	 */
	public String getTxt3 () {
		return txt3;
	}

	/**
	 * Set the value related to the column: txt3
	 * @param txt3 the txt3 value
	 */
	public void setTxt3 (String txt3) {
		this.txt3 = txt3;
	}


	/**
	 * Return the value associated with the column: content
	 */
	public foo.cms.entity.main.Content getContent () {
		return content;
	}

	/**
	 * Set the value related to the column: content
	 * @param content the content value
	 */
	public void setContent (foo.cms.entity.main.Content content) {
		this.content = content;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof foo.cms.entity.main.ContentTxt)) return false;
		else {
			foo.cms.entity.main.ContentTxt contentTxt = (foo.cms.entity.main.ContentTxt) obj;
			if (null == this.getId() || null == contentTxt.getId()) return false;
			else return (this.getId().equals(contentTxt.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}

}