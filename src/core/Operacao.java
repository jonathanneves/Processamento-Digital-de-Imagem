package core;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Operacao {

	public static Image adicao(Image imagem1, Image imagem2, double porcentagem1, double porcentagem2) {
		try {

			int w = Math.min((int)imagem1.getWidth(), (int)imagem2.getWidth());
			int h = Math.min((int)imagem1.getHeight(), (int)imagem2.getHeight());	

			PixelReader pr1 = imagem1.getPixelReader();
			PixelReader pr2 = imagem2.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();	
			
			for(int i=0; i<w; i++) {		//largura
				for(int j=0; j<h; j++) {	//altura
					Color cor1 = pr1.getColor(i,j);
					Color cor2 = pr2.getColor(i, j);
					double r = (cor1.getRed() * porcentagem1)+(cor2.getRed()*porcentagem2);
					double g = (cor1.getGreen() * porcentagem1)+(cor2.getGreen()*porcentagem2);
					double b = (cor1.getBlue() * porcentagem1)+(cor2.getBlue()*porcentagem2);				
					if(r>1) { r = 1; }
					if(g>1) { g = 1; }
					if(b>1) { b = 1; }
					Color corN = new Color(r, g, b, 1);
					pw.setColor(i, j, corN);
				}
			}
			return wi;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Image subtracao(Image imagem1, Image imagem2, double porcentagem1, double porcentagem2) {
		try {

			int w = Math.min((int)imagem1.getWidth(), (int)imagem2.getWidth());
			int h = Math.min((int)imagem1.getHeight(), (int)imagem2.getHeight());	

			PixelReader pr1 = imagem1.getPixelReader();
			PixelReader pr2 = imagem2.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();	
			
			for(int i=0; i<w; i++) {		//largura
				for(int j=0; j<h; j++) {	//altura
					Color cor1 = pr1.getColor(i,j);
					Color cor2 = pr2.getColor(i, j);
					double r = (cor1.getRed() * porcentagem1)-(cor2.getRed()*porcentagem2);
					double g = (cor1.getGreen() * porcentagem1)-(cor2.getGreen()*porcentagem2);
					double b = (cor1.getBlue() * porcentagem1)-(cor2.getBlue()*porcentagem2);	
					if(0>r) { r = 0; }
					if(0>g) { g = 0; }
					if(0>b) { b = 0; }
					Color corN = new Color(r, g, b, 1);
					pw.setColor(i, j, corN);
				}
			}
			return wi;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
