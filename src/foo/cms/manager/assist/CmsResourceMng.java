package foo.cms.manager.assist;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import foo.cms.entity.main.CmsSite;
import foo.common.file.FileWrap;
import foo.common.util.Zipper.FileEntry;

/**
 * 模板资源管理接口
 * 
 * @author liufang
 * 
 */
public interface CmsResourceMng {
	/**
	 * 获得子文件列表
	 * 
	 * @param path
	 *            父文件夹路径
	 * @param dirAndEditable
	 *            是否只获取文件夹和可编辑文件
	 * @return
	 */
	public List<FileWrap> listFile(String path, boolean dirAndEditable);

	/**
	 * 保存文件
	 * 
	 * @param path
	 *            保存路径
	 * @param file
	 *            保存文件
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public void saveFile(String path, MultipartFile file)
			throws IllegalStateException, IOException;

	/**
	 * 创建文件夹
	 * 
	 * @param path
	 *            当前目录
	 * @param dirName
	 *            文件夹名
	 * @return
	 */
	public boolean createDir(String path, String dirName);

	/**
	 * 创建文件
	 * 
	 * @param path
	 *            当前目录
	 * @param filename
	 *            文件名
	 * @param data
	 *            文件内容
	 * @throws IOException
	 */
	public void createFile(String path, String filename, String data)
			throws IOException;

	/**
	 * 读取文件
	 * 
	 * @param name
	 *            文件名称
	 * @return 文件内容
	 * @throws IOException
	 */
	public String readFile(String name) throws IOException;

	/**
	 * 更新文件
	 * 
	 * @param name
	 *            文件名称
	 * @param data
	 *            文件内容
	 * @throws IOException
	 */
	public void updateFile(String name, String data) throws IOException;

	/**
	 * 文件重命名
	 * 
	 * @param origName
	 *            原文件名
	 * @param destName
	 *            目标文件名
	 */
	public void rename(String origName, String destName);

	/**
	 * 删除文件
	 * 
	 * @param names
	 * @return
	 */
	public int delete(String[] names);

	/**
	 * 拷贝模板及资源
	 * 
	 * @param from
	 *            源站点
	 * @param to
	 *            目标站点
	 * @throws IOException
	 */
	public void copyTplAndRes(CmsSite from, CmsSite to) throws IOException;

	/**
	 * 删除模板及资源
	 * 
	 * @param site
	 * @throws IOException
	 */
	public void delTplAndRes(CmsSite site) throws IOException;

	/**
	 * 列出所有模板方案
	 * 
	 * @param path
	 *            模板方案路径
	 * @return
	 */
	public String[] getSolutions(String path);

	/**
	 * 导出模板
	 * 
	 * @param site
	 * @param solution
	 * @return
	 */
	public List<FileEntry> export(CmsSite site, String solution);

	/**
	 * 导入模板
	 * 
	 * @param file
	 * @param site
	 * @throws IOException
	 */
	public void imoport(File file, CmsSite site) throws IOException;
}
