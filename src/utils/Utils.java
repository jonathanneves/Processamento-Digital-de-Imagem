package utils;


import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.nio.ByteBuffer;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;

public final class Utils
{

	public static Image mat2Image(Mat frame)
	{
		try
		{
			BufferedImage bi = matToBufferedImage(frame);
			/*
			WritableImage wi = new WritableImage(bi.getWidth(), bi.getHeight());
            PixelWriter pw = wi.getPixelWriter();
            for (int x = 0; x < bi.getWidth(); x++) {
                for (int y = 0; y < bi.getHeight(); y++) {
                    pw.setArgb(x, y, bi.getRGB(x, y));
                }
            }
            return wi;*/
			return SwingFXUtils.toFXImage(bi, null);
		}
		catch (Exception e)
		{
			System.err.println("Cannot convert the Mat object: " + e);
			return null;
		}
	}
	
	public static Mat imageToMat(Image image)
	{
		try {
		    int width = (int) image.getWidth();
		    int height = (int) image.getHeight();
		    byte[] buffer = new byte[width * height * 4];
	
		    PixelReader reader = image.getPixelReader();
		    WritablePixelFormat<ByteBuffer> format = WritablePixelFormat.getByteBgraInstance();
		    reader.getPixels(0, 0, width, height, format, buffer, 0, width * 4);
	
		    Mat mat = new Mat(height, width, CvType.CV_8UC4);
		    mat.put(0, 0, buffer);
		    
		    return mat;
		}
		catch (Exception e)
		{
			System.err.println("Cannot convert the Image object: " + e);
			return null;
		}
	}

	public static <T> void onFXThread(final ObjectProperty<T> property, final T value)
	{
		Platform.runLater(() -> {
			property.set(value);
		});
	}
	

	private static BufferedImage matToBufferedImage(Mat m) {
	    if (!m.empty()) {
	        int type = BufferedImage.TYPE_BYTE_GRAY;
	        if (m.channels() > 1) {
	            type = BufferedImage.TYPE_3BYTE_BGR;
	        }
	        int bufferSize = m.channels() * m.cols() * m.rows();
	        byte[] b = new byte[bufferSize];
	        m.get(0, 0, b); // get all the pixels
	        BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
	        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	        System.arraycopy(b, 0, targetPixels, 0, b.length);
	        return image;
	    }
	    
	    return null;
	}
}