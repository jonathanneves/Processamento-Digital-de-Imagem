package opencv;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import javafx.scene.image.Image;
import utils.Utils;


public class OpenCV {
	

	public static Image reconheceFaces() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		CascadeClassifier faceDetector = new CascadeClassifier("xmls/haarcascade_frontalface_alt.xml");
		Mat image = Imgcodecs.imread("xmls/ImagemOpenCV.png");
		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(image, faceDetections);
		System.out.println("Detectado: " + faceDetections.toArray().length + " rostos");
		for(Rect rect : faceDetections.toArray()) {
			Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
					new Scalar(0,255,0),2);
		}
		
		Imgcodecs.imwrite("xmls/rostos_identificados.jpg", image);
		
        Image img = Utils.mat2Image(image);
        return img;
	}
		
	
	public static Image dilata(int dilation_size) {
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

        Mat source = Imgcodecs.imread("xmls/ImagemOpenCV.png");
        Mat destination = new Mat(source.rows(),source.cols(),source.type());
        
        destination = source;
        
        Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new  Size(2*dilation_size+1, 2*dilation_size+1));
        Imgproc.dilate(source, destination, element);
        Imgcodecs.imwrite("xmls/dilatacao.jpg", destination);
        
        Image img = Utils.mat2Image(destination);
        return img;
	}
	
	public static Image erodi(int erosion_size) {
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        Mat source = Imgcodecs.imread("xmls/ImagemOpenCV.png");
        Mat destination = new Mat(source.rows(),source.cols(),source.type());
        
        destination = source;
    
        Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new  Size(2*erosion_size + 1, 2*erosion_size+1));
        Imgproc.erode(source, destination, element);
        Imgcodecs.imwrite("xmls/erosao.jpg", destination);
        
        Image img = Utils.mat2Image(destination);
        return img;
	}


	public static void detectaBordasCanny(int valorX, int valorY) {
		if(valorX == 0 && valorY == 0) {
			valorX = 150;
			valorY = 150;
		}
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        Mat src = Imgcodecs.imread("xmls/ImagemOpenCV.png");
        Mat dst = new Mat(src.rows(),src.cols(),src.type());
        
        Imgproc.blur(src, src, new Size(3,3));
        //Imgproc.cvtColor(src, src, Imgproc.COLOR_RGB2GRAY, 0);
        Imgproc.Canny(src, dst, valorX, valorY);  

        Imgcodecs.imwrite("xmls/canny.jpg", dst);	
              
	}
	
	public static void detectaBordasLaplace() {
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        Mat src = Imgcodecs.imread("xmls/ImagemOpenCV.png");
        Mat dst = new Mat(src.rows(),src.cols(),src.type());
        
        Imgproc.GaussianBlur( src, src, new Size(3, 3), 0, 0, Core.BORDER_DEFAULT );
        // Convert the image to grayscale
        Imgproc.cvtColor( src, src, Imgproc.COLOR_RGB2GRAY );
        Imgproc.Laplacian( src, dst, CvType.CV_16S, 3, 1, 0, Core.BORDER_DEFAULT );
        // converting back to CV_8U
        Mat abs_dst = new Mat();
        Core.convertScaleAbs( dst, abs_dst );
        
        Imgcodecs.imwrite("xmls/laplace.jpg", dst);	
        
	}
	
	public static void detectaBordasSobel() {
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        Mat src = Imgcodecs.imread("xmls/ImagemOpenCV.png");
        Mat dst = new Mat(src.rows(),src.cols(),src.type());
        
        Imgproc.cvtColor(src, src, Imgproc.COLOR_RGB2GRAY, 0);
        Imgproc.Sobel(src, dst, CvType.CV_16S, 0, 1, 3, 1, 0);
        
        Imgcodecs.imwrite("xmls/sobel.jpg", dst);
        
	}
}

