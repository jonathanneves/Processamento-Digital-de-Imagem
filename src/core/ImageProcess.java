package core;

import application.PrincipalController;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ImageProcess {

	public static Image escalaDeCinzaMedia(Image imagem) {
		try {
			int w = (int)imagem.getWidth();
			int h = (int)imagem.getHeight();
			
			PixelReader pr = imagem.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();
			
			for(int i=0; i<w; i++) {		//largura
				for(int j=0; j<h; j++) {	//altura
					Color cor = pr.getColor(i,j);
					double media = (cor.getRed()+cor.getGreen()+cor.getBlue())/3;
					Color corN = new Color(media, media, media, cor.getOpacity());
					pw.setColor(i, j, corN);
					if(PrincipalController.marcado && !PrincipalController.filtrandoMarcacao(i, j))	//SE FOR FALSO VOLTA A COR ORIGINAL
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
	
	public static Image escalaDeCinzaPonderada(Image imagem, double r, double g, double b) {
		try {
			
			int w = (int)imagem.getWidth();
			int h = (int)imagem.getHeight();
			
			PixelReader pr = imagem.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();
			
			for(int i=0; i<w; i++) {		//largura
				for(int j=0; j<h; j++) {	//altura
					Color cor = pr.getColor(i,j);
					double media = (cor.getRed()*r+cor.getGreen()*g+cor.getBlue()*b);
					Color corN = new Color(media, media, media, cor.getOpacity());
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
	
	public static Image limiarizacao(Image imagem, double valor) {
		try {
			int w = (int)imagem.getWidth();
			int h = (int)imagem.getHeight();
			
			PixelReader pr = imagem.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();
			Color corN;
			
			for(int i=0; i<w; i++) {		//largura
				for(int j=0; j<h; j++) {	//altura
					Color cor = pr.getColor(i,j);
					double media = (cor.getRed()+cor.getGreen()+cor.getBlue())/3;
					if(media < valor)  
						corN = new Color(0, 0, 0, cor.getOpacity());
					else 
						corN = new Color(1, 1, 1, cor.getOpacity());
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
	

	public static Image negativa(Image imagem) {
		try {
			int w = (int)imagem.getWidth();
			int h = (int)imagem.getHeight();
			
			PixelReader pr = imagem.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();
			
			for(int i=0; i<w; i++) {		//largura
				for(int j=0; j<h; j++) {	//altura
					Color cor = pr.getColor(i,j);
					Color corN = new Color(1 - cor.getRed(), 1 - cor.getGreen(), 1 - cor.getBlue(), cor.getOpacity());
					pw.setColor(i, j, corN);
					if(PrincipalController.marcado && !PrincipalController.filtrandoMarcacao(i, j))			
						pw.setColor(i, j, cor);
					if(PrincipalController.criandoBorda(i,j))
						pw.setColor(i, j, new Color(0,0,0,1)); //Borda Preta
					}
			}
			return wi;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	//DESAFIO
	public static Image dividirFiltros(Image imagem) {
		try {
			int w = (int)imagem.getWidth();
			int h = (int)imagem.getHeight();
			
			double divisaoComprimento = w/4;
			System.out.println("Comprimento: "+divisaoComprimento);
			
			PixelReader pr = imagem.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();
			
			Color cor;
			
			
			for(int i=0; i<w; i++) {		//largura
				for(int j=0; j<h; j++) {	//altura
					cor = pr.getColor(i,j);
					Color corN = null;
					if(i >= 0 && i <= divisaoComprimento) {
						corN = new Color(cor.getRed(), cor.getGreen(), cor.getBlue(), cor.getOpacity());
					}
					if(i >= divisaoComprimento+1 && i <= divisaoComprimento * 2) {
						double media = (cor.getRed()+cor.getGreen()+cor.getBlue())/3;
						corN = new Color(media, media, media, cor.getOpacity());
					}
					if(i >= (divisaoComprimento*2)+1 && i <= divisaoComprimento * 3) {
						double media = (cor.getRed()+cor.getGreen()+cor.getBlue())/3;
						if(media < (127.0/255.0)) 		
							corN = new Color(0, 0, 0, cor.getOpacity());
						else 
							corN = new Color(1, 1, 1, cor.getOpacity());
					}
					if(i >= (divisaoComprimento*3)+1 && i <= divisaoComprimento * 4) {
						corN = new Color(1 - cor.getRed(), 1 - cor.getGreen(), 1 - cor.getBlue(), cor.getOpacity());
					}
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
