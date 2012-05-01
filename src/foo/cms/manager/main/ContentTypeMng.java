package foo.cms.manager.main;

import java.util.List;

import foo.cms.entity.main.ContentType;

public interface ContentTypeMng {
	public List<ContentType> getList(boolean containDisabled);

	public ContentType getDef();

	public ContentType findById(Integer id);

	public ContentType save(ContentType bean);

	public ContentType update(ContentType bean);

	public ContentType deleteById(Integer id);

	public ContentType[] deleteByIds(Integer[] ids);
}