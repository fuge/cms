package foo.common.image;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

/**
 * 图片缩小接口
 * 
 * @author liufang
 * 
 */
public interface ImageScale {
	/**
	 * 缩小图片
	 * 
	 * @param srcFile
	 *            原图片
	 * @param destFile
	 *            目标图片
	 * @param boxWidth
	 *            缩略图最大宽度
	 * @param boxHeight
	 *            缩略图最大高度
	 * @throws IOException
	 */
	public void resizeFix(File srcFile, File destFile, int boxWidth,
			int boxHeight) throws Exception;

	/**
	 * 缩小并裁剪图片
	 * 
	 * @param srcFile
	 *            原文件
	 * @param destFile
	 *            目标文件
	 * @param boxWidth
	 *            缩略图最大宽度
	 * @param boxHeight
	 *            缩略图最大高度
	 * @param cutTop
	 *            裁剪TOP
	 * @param cutLeft
	 *            裁剪LEFT
	 * @param cutWidth
	 *            裁剪宽度
	 * @param catHeight
	 *            裁剪高度
	 * @throws IOException
	 */
	public void resizeFix(File srcFile, File destFile, int boxWidth,
			int boxHeight, int cutTop, int cutLeft, int cutWidth, int catHeight)
			throws Exception;

	public void imageMark(File srcFile, File destFile, int minWidth,
			int minHeight, int pos, int offsetX, int offsetY, String text,
			Color color, int size, int alpha) throws Exception;

	public void imageMark(File srcFile, File destFile, int minWidth,
			int minHeight, int pos, int offsetX, int offsetY, File markFile)
			throws Exception;
}
