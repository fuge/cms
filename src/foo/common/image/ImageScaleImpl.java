package foo.common.image;

import java.awt.Color;
import java.io.File;

import magick.Magick;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 图片缩小类
 * 
 * 根据环境情况选择java图片缩小方式或专业的magick图片缩小方式
 * 
 * @author liufang
 * 
 */
public class ImageScaleImpl implements ImageScale {
	private static final Logger log = LoggerFactory
			.getLogger(ImageScaleImpl.class);

	public void resizeFix(File srcFile, File destFile, int boxWidth,
			int boxHeight) throws Exception {
		if (isMagick) {
			MagickImageScale.resizeFix(srcFile, destFile, boxWidth, boxHeight);
		} else {
			AverageImageScale.resizeFix(srcFile, destFile, boxWidth, boxHeight);
		}
	}

	public void resizeFix(File srcFile, File destFile, int boxWidth,
			int boxHeight, int cutTop, int cutLeft, int cutWidth, int catHeight)
			throws Exception {
		if (isMagick) {
			MagickImageScale.resizeFix(srcFile, destFile, boxWidth, boxHeight,
					cutTop, cutLeft, cutWidth, catHeight);
		} else {
			AverageImageScale.resizeFix(srcFile, destFile, boxWidth, boxHeight,
					cutTop, cutLeft, cutWidth, catHeight);
		}
	}

	public void imageMark(File srcFile, File destFile, int minWidth,
			int minHeight, int pos, int offsetX, int offsetY, String text,
			Color color, int size, int alpha) throws Exception {
		if (isMagick) {
			MagickImageScale.imageMark(srcFile, destFile, minWidth, minHeight,
					pos, offsetX, offsetY, text, color, size, alpha);
		} else {
			AverageImageScale.imageMark(srcFile, destFile, minWidth, minHeight,
					pos, offsetX, offsetY, text, color, size, alpha);
		}
	}

	public void imageMark(File srcFile, File destFile, int minWidth,
			int minHeight, int pos, int offsetX, int offsetY, File markFile)
			throws Exception {
		if (isMagick) {
			MagickImageScale.imageMark(srcFile, destFile, minWidth, minHeight,
					pos, offsetX, offsetY, markFile);
		} else {
			AverageImageScale.imageMark(srcFile, destFile, minWidth, minHeight,
					pos, offsetX, offsetY, markFile);
		}
	}

	/**
	 * 检查是否安装magick
	 */
	public void init() {
		if (tryMagick) {
			try {
				System.setProperty("jmagick.systemclassloader", "no");
				new Magick();
				log.info("using jmagick");
				isMagick = true;
			} catch (Throwable e) {
				log.warn("load jmagick fail, use java image scale. message:{}",
						e.getMessage());
				isMagick = false;
			}
		} else {
			log.info("jmagick is disabled.");
			isMagick = false;
		}
	}

	private boolean isMagick = false;
	private boolean tryMagick = true;

	public void setTryMagick(boolean tryMagick) {
		this.tryMagick = tryMagick;
	}
}
