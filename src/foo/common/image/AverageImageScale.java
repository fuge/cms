package foo.common.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import foo.common.image.ImageUtils.Position;

/**
 * 图片缩小类。使用方型区域颜色平均算法
 * 
 * @author liufang
 * 
 */
public class AverageImageScale {
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
	public static void resizeFix(File srcFile, File destFile, int boxWidth,
			int boxHeight) throws IOException {
		BufferedImage srcImgBuff = ImageIO.read(srcFile);
		int width = srcImgBuff.getWidth();
		int height = srcImgBuff.getHeight();
		if (width <= boxWidth && height <= boxHeight) {
			FileUtils.copyFile(srcFile, destFile);
			return;
		}
		int zoomWidth;
		int zoomHeight;
		if ((float) width / height > (float) boxWidth / boxHeight) {
			zoomWidth = boxWidth;
			zoomHeight = Math.round((float) boxWidth * height / width);
		} else {
			zoomWidth = Math.round((float) boxHeight * width / height);
			zoomHeight = boxHeight;
		}
		BufferedImage imgBuff = scaleImage(srcImgBuff, width, height,
				zoomWidth, zoomHeight);
		writeFile(imgBuff, destFile);
	}

	/**
	 * 裁剪并压缩
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
	public static void resizeFix(File srcFile, File destFile, int boxWidth,
			int boxHeight, int cutTop, int cutLeft, int cutWidth, int catHeight)
			throws IOException {
		BufferedImage srcImgBuff = ImageIO.read(srcFile);
		srcImgBuff = srcImgBuff.getSubimage(cutTop, cutLeft, cutWidth,
				catHeight);
		int width = srcImgBuff.getWidth();
		int height = srcImgBuff.getHeight();
		if (width <= boxWidth && height <= boxHeight) {
			writeFile(srcImgBuff, destFile);
			return;
		}
		int zoomWidth;
		int zoomHeight;
		if ((float) width / height > (float) boxWidth / boxHeight) {
			zoomWidth = boxWidth;
			zoomHeight = Math.round((float) boxWidth * height / width);
		} else {
			zoomWidth = Math.round((float) boxHeight * width / height);
			zoomHeight = boxHeight;
		}
		BufferedImage imgBuff = scaleImage(srcImgBuff, width, height,
				zoomWidth, zoomHeight);
		writeFile(imgBuff, destFile);
	}

	public static void writeFile(BufferedImage imgBuf, File destFile)
			throws IOException {
		File parent = destFile.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		}
		ImageIO.write(imgBuf, "jpeg", destFile);
	}

	/**
	 * 添加文字水印
	 * 
	 * @param srcFile
	 *            源图片文件。需要加水印的图片文件。
	 * @param destFile
	 *            目标图片。加水印后保存的文件。如果和源图片文件一致，则覆盖源图片文件。
	 * @param minWidth
	 *            需要加水印的最小宽度，如果源图片宽度小于该宽度，则不加水印。
	 * @param minHeight
	 *            需要加水印的最小高度，如果源图片高度小于该高度，则不加水印。
	 * @param pos
	 *            加水印的位置。
	 * @param offsetX
	 *            加水印的位置的偏移量x。
	 * @param offsetY
	 *            加水印的位置的偏移量y。
	 * @param text
	 *            水印文字
	 * @param color
	 *            水印颜色
	 * @param size
	 *            水印字体大小
	 * @param alpha
	 *            透明度
	 * @throws IOException
	 */
	public static void imageMark(File srcFile, File destFile, int minWidth,
			int minHeight, int pos, int offsetX, int offsetY, String text,
			Color color, int size, int alpha) throws IOException {
		BufferedImage imgBuff = ImageIO.read(srcFile);
		int width = imgBuff.getWidth();
		int height = imgBuff.getHeight();
		if (width <= minWidth || height <= minHeight) {
			imgBuff = null;
			if (!srcFile.equals(destFile)) {
				FileUtils.copyFile(srcFile, destFile);
			}
		} else {
			imageMark(imgBuff, width, height, pos, offsetX, offsetY, text,
					color, size, alpha);
			writeFile(imgBuff, destFile);
			imgBuff = null;
		}
	}

	/**
	 * 添加图片水印
	 * 
	 * @param srcFile
	 *            源图片文件。需要加水印的图片文件。
	 * @param destFile
	 *            目标图片。加水印后保存的文件。如果和源图片文件一致，则覆盖源图片文件。
	 * @param minWidth
	 *            需要加水印的最小宽度，如果源图片宽度小于该宽度，则不加水印。
	 * @param minHeight
	 *            需要加水印的最小高度，如果源图片高度小于该高度，则不加水印。
	 * @param pos
	 *            加水印的位置。
	 * @param offsetX
	 *            加水印的位置的偏移量x。
	 * @param offsetY
	 *            加水印的位置的偏移量y。
	 * @param markFile
	 *            水印图片
	 * @throws IOException
	 */
	public static void imageMark(File srcFile, File destFile, int minWidth,
			int minHeight, int pos, int offsetX, int offsetY, File markFile)
			throws IOException {
		BufferedImage imgBuff = ImageIO.read(srcFile);
		int width = imgBuff.getWidth();
		int height = imgBuff.getHeight();
		if (width <= minWidth || height <= minHeight) {
			imgBuff = null;
			if (!srcFile.equals(destFile)) {
				FileUtils.copyFile(srcFile, destFile);
			}
		} else {
			imageMark(imgBuff, width, height, pos, offsetX, offsetY, markFile);
			writeFile(imgBuff, destFile);
			imgBuff = null;
		}

	}

	/**
	 * 添加文字水印
	 * 
	 * @param imgBuff
	 *            原图片
	 * @param width
	 *            原图宽度
	 * @param height
	 *            原图高度
	 * @param pos
	 *            位置。1：左上；2：右上；3左下；4右下；5：中央；0或其他：随机。
	 * @param offsetX
	 *            水平偏移量。
	 * @param offsetY
	 *            垂直偏移量。
	 * @param text
	 *            水印内容
	 * @param color
	 *            水印颜色
	 * @param size
	 *            文字大小
	 * @param alpha
	 *            透明度。0-100。越小越透明。
	 * @throws IOException
	 */
	private static void imageMark(BufferedImage imgBuff, int width, int height,
			int pos, int offsetX, int offsetY, String text, Color color,
			int size, int alpha) throws IOException {
		Position p = ImageUtils.markPosition(width, height, pos, offsetX,
				offsetY);
		Graphics2D g = imgBuff.createGraphics();
		g.setColor(color);
		g.setFont(new Font(null, Font.PLAIN, size));
		AlphaComposite a = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
				(float) alpha / 100);
		g.setComposite(a);
		g.drawString(text, p.getX(), p.getY());
		g.dispose();
	}

	private static void imageMark(BufferedImage imgBuff, int width, int height,
			int pos, int offsetX, int offsetY, File markFile)
			throws IOException {
		Position p = ImageUtils.markPosition(width, height, pos, offsetX,
				offsetY);
		Graphics2D g = imgBuff.createGraphics();
		AlphaComposite a = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP);
		g.setComposite(a);
		g.drawImage(ImageIO.read(markFile), p.getX(), p.getY(), null);
		g.dispose();
	}

	private static BufferedImage scaleImage(BufferedImage srcImgBuff,
			int width, int height, int zoomWidth, int zoomHeight) {
		int[] colorArray = srcImgBuff.getRGB(0, 0, width, height, null, 0,
				width);
		BufferedImage outBuff = new BufferedImage(zoomWidth, zoomHeight,
				BufferedImage.TYPE_INT_RGB);
		// 宽缩小的倍数
		float wScale = (float) width / zoomWidth;
		int wScaleInt = (int) (wScale + 0.5);
		// 高缩小的倍数
		float hScale = (float) height / zoomHeight;
		int hScaleInt = (int) (hScale + 0.5);
		int area = wScaleInt * hScaleInt;
		int x0, x1, y0, y1;
		int color;
		long red, green, blue;
		int x, y, i, j;
		for (y = 0; y < zoomHeight; y++) {
			// 得到原图高的Y坐标
			y0 = (int) (y * hScale);
			y1 = y0 + hScaleInt;
			for (x = 0; x < zoomWidth; x++) {
				x0 = (int) (x * wScale);
				x1 = x0 + wScaleInt;
				red = green = blue = 0;
				for (i = x0; i < x1; i++) {
					for (j = y0; j < y1; j++) {
						color = colorArray[width * j + i];
						red += getRedValue(color);
						green += getGreenValue(color);
						blue += getBlueValue(color);
					}
				}
				outBuff.setRGB(x, y, comRGB((int) (red / area),
						(int) (green / area), (int) (blue / area)));
			}
		}
		return outBuff;
	}

	private static int getRedValue(int rgbValue) {
		return (rgbValue & 0x00ff0000) >> 16;
	}

	private static int getGreenValue(int rgbValue) {
		return (rgbValue & 0x0000ff00) >> 8;
	}

	private static int getBlueValue(int rgbValue) {
		return rgbValue & 0x000000ff;
	}

	private static int comRGB(int redValue, int greenValue, int blueValue) {
		return (redValue << 16) + (greenValue << 8) + blueValue;
	}

	public static void main(String[] args) throws Exception {
		long time = System.currentTimeMillis();
		AverageImageScale.resizeFix(new File(
				"test/foo/common/util/1.bmp"), new File(
				"test/foo/common/util/1-n-2.bmp"), 310, 310, 50, 50,
				320, 320);
		time = System.currentTimeMillis() - time;
		System.out.println("resize2 img in " + time + "ms");
	}
}