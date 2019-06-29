package core;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Pixel {

	
	public static Color[] descobrirVizinhacaX(Image imagem, int iPos, int jPos) {
		try {
			int w = (int)imagem.getWidth();
			int h = (int)imagem.getHeight();
			int index = 0;
			Color Pixels[] = new Color[5];
			
			PixelReader pr = imagem.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();

			for(int i=0; i<w; i++) {		//largura
				for(int j=0; j<h; j++) {	//altura
					Color cor = pr.getColor(i,j);
					if((iPos-1 == i && jPos-1 == j) || (iPos-1 == i && jPos+1 == j) 
							|| (iPos+1 == i && jPos-1 == j) || (iPos+1 == i && jPos+1 == j)) {
						informacoesDoPixel(i, j, cor);
						Pixels[index] = cor;
						index++;
					}
					if(index == Pixels.length-1) {
						Pixels[index] = pr.getColor(iPos, jPos);
						return Pixels;
					}
				}
			}
			Pixels[index] = pr.getColor(iPos, jPos);
			return Pixels;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Color[] descobrirVizinhacaCruz(Image imagem, int iPos, int jPos) {
		try {
			int w = (int)imagem.getWidth();
			int h = (int)imagem.getHeight();
			int index = 0;
			Color Pixels[] = new Color[4];
			
			PixelReader pr = imagem.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();
			
			for(int i=0; i<w; i++) {		//largura
				for(int j=0; j<h; j++) {	//altura
					Color cor = pr.getColor(i,j);
					if((iPos-1 == i && jPos == j) || (iPos == i && jPos-1 == j) 
							|| (iPos == i && jPos+1 == j) || (iPos+1 == i && jPos == j)) {
						informacoesDoPixel(i, j, cor);
						Pixels[index] = cor;
						index++;
					}
					if(index == Pixels.length) {
						Pixels[index] = pr.getColor(iPos, jPos);
						return Pixels;
					}
				}
			}
			Pixels[index] = pr.getColor(iPos, jPos);
			return Pixels;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Color[] descobrirVizinhaca3X3(Image imagem, int iPos, int jPos) {
		try {
			int w = (int)imagem.getWidth();
			int h = (int)imagem.getHeight();
			int index = 0;
			Color Pixels[] = new Color[8];
			
			PixelReader pr = imagem.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();
			
			for(int i=0; i<w; i++) {		//largura
				for(int j=0; j<h; j++) {	//altura
					Color cor = pr.getColor(i,j);
					if((iPos-1 == i && jPos == j) || (iPos == i && jPos-1 == j) 
						|| (iPos == i && jPos+1 == j) || (iPos+1 == i && jPos == j)
						|| (iPos-1 == i && jPos-1 == j) || (iPos-1 == i && jPos+1 == j) 
						|| (iPos+1 == i && jPos-1 == j) || (iPos+1 == i && jPos+1 == j)) {
						informacoesDoPixel(i, j, cor);
						Pixels[index] = cor;
						index++;
					}
					if(index == Pixels.length) {
						Pixels[index] = pr.getColor(iPos, jPos);
						return Pixels;
					}
				}
			}
			Pixels[index] = pr.getColor(iPos, jPos);
			return Pixels;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void informacoesDoPixel(int i, int j, Color cor) {
		System.out.print("Posição: "+i+ " - " +j);
		System.out.println(" RGB: "+(int) Math.round(cor.getRed()*255)
				+", "+(int) Math.round(cor.getGreen()*255)
				+", "+(int) Math.round(cor.getBlue()*255));
	}
}
