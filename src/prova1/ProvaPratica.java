package prova1;

import application.PrincipalController;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ProvaPratica {


	public static Image aplicaZebrado(Image imagem, int colunas) {
		try {
						
			int w = (int)imagem.getWidth();
			int h = (int)imagem.getHeight();
			
			int total = (int) w/colunas;
			boolean zebrado = true;
			System.out.println(total);
		
			PixelReader pr = imagem.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();
			
			for(int i=0; i<w; i++) {		//largura
				for(int j=0; j<h; j++) {	//altura
					Color cor = pr.getColor(i,j);
					double media = (cor.getRed()+cor.getGreen()+cor.getBlue())/3;
					Color corCinza = new Color(media, media, media, cor.getOpacity());
					if(i <= total){
						if(zebrado) 
							pw.setColor(i,j, corCinza);	
						else
							pw.setColor(i,j, cor);	
					}else {
						total += (int)w/colunas;
						zebrado = !zebrado;
					}
				}			
			}
			return wi;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Image rotacionaMarcacao(Image imagem) {
		try {
			
			int xIni = PrincipalController.xIni;
			int xFin = PrincipalController.xFin;
			int yIni = PrincipalController.yIni;
			int yFin = PrincipalController.yFin;
			
			int w = (int)imagem.getWidth();
			int h = (int)imagem.getHeight();
			
			PixelReader pr = imagem.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();
			
			System.out.println(w + " - "+ h);

			for(int i=0; i<w; i++) {		//largura
				for(int j=0; j<h; j++) {	//altura
					
					Color cor = pr.getColor(i, j);	
					if(PrincipalController.marcado && PrincipalController.filtrandoMarcacao(i, j))		
						pw.setColor(((int)xFin-(i-(int)xIni)),((int)yFin-(j-(int)yIni)),cor);
					else
						pw.setColor(i, j, cor);					
				}
			}
			return wi;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void detectaPreenchimento(Image imagem) {
		try {
									
			int w = (int)imagem.getWidth();
			int h = (int)imagem.getHeight();
			
			PixelReader pr = imagem.getPixelReader();
			
			boolean estaPreenchido = false;
			
			for(int i=0; i<w; i++) {		//largura
				for(int j=0; j<h; j++) {	//altura
					Color cor = pr.getColor(i,j);
					
					if(cor.getRed() != 1 && cor.getGreen() != 1 && cor.getBlue() != 1) { //CASO O PIXEL NÂO SEJA BRANCO
						if(i != 0 && j != 0 && i != w-1 && j != h-1) {
							Color corAdj1 = pr.getColor(i-1, j-1);
							Color corAdj2 = pr.getColor(i-1, j+1);
							Color corAdj3 = pr.getColor(i+1, j-1);
							Color corAdj4 = pr.getColor(i+1, j+1);

							if(!estaPreenchido) {
								if((corAdj1.getRed() != 1) && (corAdj2.getRed() != 1)
										&& (corAdj3.getRed() != 1)	&& (corAdj4.getRed() != 1))		//CASO TODOS PIXELS VIZINHOS X não sejam brancos
										estaPreenchido = true;
								else
									estaPreenchido = false;
							}
						}
					}
				}
			}
			String resultado = (estaPreenchido)?"Quadrado está Preenchido":"Quadrado está vazio";
			exibeMsg("Detectando preenchimento!", 
					"A imagem atual é um: " +resultado,
					"Está e o resultado indentificado",
					AlertType.INFORMATION);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void exibeMsg(String titulo, String cabecalho, String msg, AlertType tipo) {
		Alert alert = new Alert(tipo);
		alert.setTitle(titulo);
		alert.setHeaderText(cabecalho);
		alert.setContentText(msg);
		alert.showAndWait();
	}
}
