package core;

import java.util.Arrays;

import application.PrincipalController;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Histograma {

	public static Image equalizador255(Image imagem) {
		try {
			
			int[] hR = histogramaPorCor(imagem, 1);	
			int[] hG = histogramaPorCor(imagem, 2);
			int[] hB = histogramaPorCor(imagem, 3);
			
			int[] histAcR = histogramaAcumulado(hR);	
			int[] histAcG = histogramaAcumulado(hG);
			int[] histAcB = histogramaAcumulado(hB);
			
			int w = (int)imagem.getWidth();
			int h = (int)imagem.getHeight();
			

			PixelReader pr = imagem.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();
			
			int n = w*h;	//totalpixel
			
			for(int i=0; i<w; i++) {		//largura
				for(int j=0; j<h; j++) {	//altura
					Color cor = pr.getColor(i,j);
					
					double acR = histAcR[(int)(cor.getRed()*255)];
					double acG = histAcG[(int)(cor.getGreen()*255)];
					double acB = histAcB[(int)(cor.getBlue()*255)];

					double pxR = ((255.0/n)*acR)/255.0;
					double pxG = ((255.0/n)*acG)/255.0;
					double pxB = ((255.0/n)*acB)/255.0;
					
					
					Color corN = new Color(pxR, pxG, pxB, cor.getOpacity());
					pw.setColor(i, j, corN);
					if(PrincipalController.marcado) {
						if(!PrincipalController.filtrandoMarcacao(i, j))	
							pw.setColor(i, j, cor);
						if(PrincipalController.criandoBorda(i,j))
							pw.setColor(i, j, new Color(0,0,0,1)); 	
					}					
				}
			}
			return wi;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Image equalizadorXTons(Image imagem) {
		try {
			
			int[] hR = histogramaPorCor(imagem, 1);	
			int[] hG = histogramaPorCor(imagem, 2);
			int[] hB = histogramaPorCor(imagem, 3);
			
			int[] histAcR = histogramaAcumulado(hR);	
			int[] histAcG = histogramaAcumulado(hG);
			int[] histAcB = histogramaAcumulado(hB);
			
			double qtTonsRed = qtTons(hR);
			double qtTonsGreen = qtTons(hG);
			double qtTonsBlue = qtTons(hB);
			
			int w = (int)imagem.getWidth();
			int h = (int)imagem.getHeight();
			

			PixelReader pr = imagem.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();
			
			int n = w*h;	//totalpixel
			
			for(int i=0; i<w; i++) {		//largura
				for(int j=0; j<h; j++) {	//altura
					Color cor = pr.getColor(i,j);
					
					double acR = histAcR[(int)(cor.getRed()*255)];
					double acG = histAcG[(int)(cor.getGreen()*255)];
					double acB = histAcB[(int)(cor.getBlue()*255)];
					
					double pxR = (((qtTonsRed-1)/n)*acR)/255.0;
					double pxG = (((qtTonsGreen-1)/n)*acG)/255.0;
					double pxB = (((qtTonsBlue-1)/n)*acB)/255.0;
					
					//int minimoR = pontoMin(histAcR);
					//int minimoG = pontoMin(histAcG);
					//int minimoB = pontoMin(histAcB);

					Color corN = new Color(pxR, pxG, pxB, cor.getOpacity());
					pw.setColor(i, j, corN);
					if(PrincipalController.marcado) {
						if(!PrincipalController.filtrandoMarcacao(i, j))	
							pw.setColor(i, j, cor);
						if(PrincipalController.criandoBorda(i,j))
							pw.setColor(i, j, new Color(0,0,0,1)); 	
					}					
				}
			}
			return wi;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static int qtTons(int[] hist) {
		int qtZeros = 0;
		for(int i : hist) {
			if(i==0)
				qtZeros++;
		}
		return 255-qtZeros;
	}
	
	public static int pontoMin(int[] hist) {
		int minimo = 0;
		for(int i=0; i<hist.length; i++) {
			if(hist[i]!=0) {
				minimo = i;
				return minimo;
			}
		}
		return minimo;
	}
	
	public static int[] pegarPico(int[] hist) {
		int pico[] = new int[3];
		int max1 = 0;
		int max2 = 0;
		int max3 = 0;
		int picoMax1 = 0;
		int picoMax2 = 0;
		int picoMax3 = 0;
		
		for(int i=0; i<hist.length; i++) {
			if(85>=i) {
				if(hist[i] > max1) {
					max1 = hist[i];	
					picoMax1 = i;
				}
			}
			if(170>=i && i>85) {
				if(hist[i] > max2) {
					max2 = hist[i];	
					picoMax2 = i;
				}
			}
			if(255>=i && i>170) {
				if(hist[i] > max3 && i != 255){
					max3 = hist[i];	
					picoMax3 = i;
				}	
			}
		}
		pico[0] = picoMax1;
		pico[1] = picoMax2;
		pico[2] = picoMax3;
		System.out.println(picoMax1 + " - " + picoMax2 + " - " + picoMax3);
		return pico;
	}
	
	public static int[] histogramaUnico(Image imagem) {
		int[] hist = new int[256];
		
		try {
			int w = (int)imagem.getWidth();
			int h = (int)imagem.getHeight();
						
			PixelReader pr = imagem.getPixelReader();		
			
			for(int i=0; i<w; i++) {		
				for(int j=0; j<h; j++) {	
					Color cor = pr.getColor(i,j);
					hist[(int)(cor.getRed()*255)]++;
					hist[(int)(cor.getGreen()*255)]++;
					hist[(int)(cor.getBlue()*255)]++;
				}
			}
			//System.out.println("HISTOGRAMA UNICO: "+Arrays.toString(hist));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hist;
	}
	
	public static int[] histogramaPorCor(Image imagem, int canal) {
		int[] hist = new int[256];		
		
		try {
			int w = (int)imagem.getWidth();
			int h = (int)imagem.getHeight();
						
			PixelReader pr = imagem.getPixelReader();		
			for(int i=0; i<w; i++) {		
				for(int j=0; j<h; j++) {	
					Color cor = pr.getColor(i,j);
					if(canal == 1)
						hist[(int)(cor.getRed()*255)]++;
					if(canal == 2)
						hist[(int)(cor.getGreen()*255)]++;
					if(canal == 3)
						hist[(int)(cor.getBlue()*255)]++;
				}
			}
		//	System.out.println("HISTOGRAMA POR COR: "+Arrays.toString(hist));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hist;
	} 
	
	public static int[] histogramaAcumulado(int[] hist) {
		int[] histAC = new int[hist.length];	
		
		try {
			int v1 = hist[0];
			for(int i = 0; i<hist.length-1; i++) {		
				histAC[i] = v1;
				v1 += hist[i+1];
			}
			//System.out.println("HISTOGRAMA ACUMULADO: "+Arrays.toString(histAC));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return histAC;
	}  
	
	@SuppressWarnings({"rawtypes","unchecked"})
	public static void getGrafico(Image img, BarChart<String, Number> grafico) {
		/*int[] hist = histogramaUnico(img);
		int minimo = 0;
		minimo = pontoMin(hist);
		System.out.println(minimo);
		XYChart.Series vlr = new XYChart.Series();
		for(int i = minimo; i<hist.length; i++) {
			vlr.getData().add(new XYChart.Data(i+"", hist[i]));
		}
		grafico.getData().addAll(vlr);*/
		
		//CORES RGB SEPARADAS NO GRÁFICO
		int[] histR = histogramaPorCor(img, 1);
		int[] histG = histogramaPorCor(img, 2);
		int[] histB = histogramaPorCor(img, 3);
		XYChart.Series vlrR = new XYChart.Series();
		XYChart.Series vlrG = new XYChart.Series();
		XYChart.Series vlrB = new XYChart.Series();
		for(int i = 0; i<histR.length; i++) {
			vlrR.getData().add(new XYChart.Data(i+"", histR[i]));
			vlrG.getData().add(new XYChart.Data(i+"", histG[i]));
			vlrB.getData().add(new XYChart.Data(i+"", histB[i]));
		}
		grafico.getData().addAll(vlrR);
		grafico.getData().addAll(vlrG);
		grafico.getData().addAll(vlrB);
	}
}
