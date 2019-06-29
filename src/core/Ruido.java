package core;

import java.util.Arrays;

import application.PrincipalController;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Ruido {
	
	public static Image reduzirRuidoX(Image imagem) {
		try {
			int w = (int)imagem.getWidth();
			int h = (int)imagem.getHeight();
			Color[] pixels = new Color[5];
			double coresR[] = new double[5];
			double coresG[] = new double[5];			
			double coresB[] = new double[5];
			
			PixelReader pr = imagem.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();

			for(int i=1; i<w-1; i++) {		//largura
				for(int j=1; j<h-1; j++) {	//altura
					Color cor = pr.getColor(i,j);
					pixels[0] = cor;
					pixels[1] = pr.getColor(i-1, j-1);
					pixels[2] = pr.getColor(i-1, j+1);
					pixels[3] = pr.getColor(i+1, j-1);
					pixels[4] = pr.getColor(i+1, j+1);

					
					//pixels = Pixel.descobrirVizinhacaX(imagem, i, j);
					for(int x=0; x<pixels.length; x++) {
						coresR[x] = pixels[x].getRed();
						coresG[x] = pixels[x].getGreen();
						coresB[x] = pixels[x].getBlue();
					}
					
					Arrays.sort(coresR);
					Arrays.sort(coresG);
					Arrays.sort(coresB);
					double medianaR = coresR[2];
					double medianaG = coresG[2];
					double medianaB = coresB[2];
					Color corN = new Color(medianaR, medianaG, medianaB, cor.getOpacity());
					pw.setColor(i, j, corN);
					if(PrincipalController.marcado && !PrincipalController.filtrandoMarcacao(i, j))			
							pw.setColor(i, j, cor);
					if(PrincipalController.criandoBorda(i,j))
						pw.setColor(i, j, new Color(0,0,0,1)); 	
					}		
			}
			return wi;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Image reduzirRuidoCruz(Image imagem) {
		try {
			int w = (int)imagem.getWidth();
			int h = (int)imagem.getHeight();
			Color[] pixels = new Color[5];
			double coresR[] = new double[5];
			double coresG[] = new double[5];			
			double coresB[] = new double[5];
			
			PixelReader pr = imagem.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();

			for(int i=1; i<w-1; i++) {		//largura
				for(int j=1; j<h-1; j++) {	//altura
					Color cor = pr.getColor(i,j);
					pixels[0] = cor;
					pixels[1] = pr.getColor(i-1, j);
					pixels[2] = pr.getColor(i+1, j);
					pixels[3] = pr.getColor(i, j-1);
					pixels[4] = pr.getColor(i, j+1);

					
					//pixels = Pixel.descobrirVizinhacaX(imagem, i, j);
					for(int x=0; x<pixels.length; x++) {
							coresR[x] = pixels[x].getRed();
							coresG[x] = pixels[x].getGreen();
							coresB[x] = pixels[x].getBlue();
					}
					
					Arrays.sort(coresR);
					Arrays.sort(coresG);
					Arrays.sort(coresB);
					double medianaR = coresR[2];
					double medianaG = coresG[2];
					double medianaB = coresB[2];
					Color corN = new Color(medianaR, medianaG, medianaB, cor.getOpacity());
					pw.setColor(i, j, corN);
					if(PrincipalController.marcado && !PrincipalController.filtrandoMarcacao(i, j))			
						pw.setColor(i, j, cor);
					if(PrincipalController.criandoBorda(i,j))
						pw.setColor(i, j, new Color(0,0,0,1)); 			
				}
			}
			return wi;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Image reduzirRuido3x3(Image imagem) {
		try {
			int w = (int)imagem.getWidth();
			int h = (int)imagem.getHeight();
			Color[] pixels = new Color[9];
			double coresR[] = new double[9];
			double coresG[] = new double[9];			
			double coresB[] = new double[9];
			
			PixelReader pr = imagem.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();

			for(int i=1; i<w-1; i++) {		//largura
				for(int j=1; j<h-1; j++) {	//altura
					Color cor = pr.getColor(i,j);
					pixels[0] = cor;
					pixels[1] = pr.getColor(i-1, j);
					pixels[2] = pr.getColor(i+1, j);
					pixels[3] = pr.getColor(i, j-1);
					pixels[4] = pr.getColor(i, j+1);
					pixels[5] = pr.getColor(i-1, j-1);
					pixels[6] = pr.getColor(i-1, j+1);
					pixels[7] = pr.getColor(i+1, j-1);
					pixels[8] = pr.getColor(i+1, j+1);
		
					//pixels = Pixel.descobrirVizinhacaX(imagem, i, j);
					for(int x=0; x<pixels.length; x++) {
						coresR[x] = pixels[x].getRed();
						coresG[x] = pixels[x].getGreen();
						coresB[x] = pixels[x].getBlue();
					}
					
					Arrays.sort(coresR);
					Arrays.sort(coresG);
					Arrays.sort(coresB);
					double medianaR = coresR[4];
					double medianaG = coresG[4];
					double medianaB = coresB[4];
					Color corN = new Color(medianaR, medianaG, medianaB, cor.getOpacity());
					pw.setColor(i, j, corN);
					if(PrincipalController.marcado && !PrincipalController.filtrandoMarcacao(i, j))		
						pw.setColor(i, j, cor);
					if(PrincipalController.criandoBorda(i,j))
						pw.setColor(i, j, new Color(0,0,0,1)); 			
				}
			}
			return wi;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
   
}
