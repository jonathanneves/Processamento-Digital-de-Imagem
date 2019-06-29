package desafio;

import application.PrincipalController;
import core.Histograma;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Segmentacao {

	public static Image segmentacao(Image imagem) {
		try {
			int w = (int)imagem.getWidth();
			int h = (int)imagem.getHeight();
			
			PixelReader pr = imagem.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();

			int picoR[] = new int[3];
			int picoG[] = new int[3];
			int picoB[] = new int[3];
			
			int histR[] = Histograma.histogramaPorCor(imagem, 1);
			int histG[] = Histograma.histogramaPorCor(imagem, 2);
			int histB[] = Histograma.histogramaPorCor(imagem, 3);
			
			picoR = Histograma.pegarPico(histR);
			picoG = Histograma.pegarPico(histG);
			picoB = Histograma.pegarPico(histB);
			
			//DETECTAR A MENOR COR DO PICO MAXIMO
			int menor=255;
			boolean menorR = false;
			boolean menorG = false;
			boolean menorB = false;
			
			if(menor>picoR[2]) {
				menor = picoR[2];
				menorR = true;
			}
			if(menor>picoG[2]) {
				menor=picoG[2];
				menorG = true;
			}
			if(menor>picoB[2]) {			
				menor = picoB[2];	
				menorB = true;			
			}
			
			for(int i=0; i<w; i++) {		//largura
				for(int j=0; j<h; j++) {	//altura
					Color cor = pr.getColor(i,j);
					Color corN;
					Color corS = new Color(picoR[1]/255.0, picoG[1]/255.0, picoB[1]/255.0, cor.getOpacity());
					double media = (cor.getRed()+cor.getGreen()+cor.getBlue())/3;
					
					//LIMEAR DA MEDIA
					if(media<0.325) 
						corN = new Color(picoR[0]/255.0, picoG[0]/255.0, picoB[0]/255.0, cor.getOpacity());
					else
						corN = new Color(picoR[2]/255.0, picoG[2]/255.0, picoB[2]/255.0, cor.getOpacity());
					
					//SEPARANDO CORES
					if(cor.getRed()*255<120 && menorR)
						corS = new Color(picoR[2]/255.0, picoG[2]/255.0, picoB[2]/255.0, cor.getOpacity());
					if(cor.getGreen()*255<120 && menorG)
						corS = new Color(picoR[2]/255.0, picoG[2]/255.0, picoB[2]/255.0, cor.getOpacity());
					if(cor.getBlue()*255<120 && menorB)
						corS = new Color(picoR[2]/255.0, picoG[2]/255.0, picoB[2]/255.0, cor.getOpacity());
					
					if(corN.getBlue() == corS.getBlue()) //APENAS PARA COMPARARAR SE VALORES SÃO IGUAIS
						corS =  new Color(picoR[0]/255.0, picoG[0]/255.0, picoB[0]/255.0, cor.getOpacity());
					pw.setColor(i, j, corS); 	
				}
			}
				
			return wi;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
