package desafio;

import javax.swing.JOptionPane;

import application.PrincipalController;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class FiltrosEspeciais {

	public static Image aplicaGrades(Image imagem) {
		try {
			
			double R = Double.parseDouble((JOptionPane.showInputDialog("Cor da Grade em R (entre 0 e 1)")));
			double G = Double.parseDouble((JOptionPane.showInputDialog("Cor da Grade em G (entre 0 e 1)")));
			double B = Double.parseDouble((JOptionPane.showInputDialog("Cor da Grade em B (entre 0 e 1)")));
			int distancia = 0;
			distancia = Integer.parseInt((JOptionPane.showInputDialog("Distância entre as linhas.")));
			
			int total = distancia;
			
			int w = (int)imagem.getWidth();
			int h = (int)imagem.getHeight();
			
			PixelReader pr = imagem.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();
			
			for(int i=0; i<w; i++) {		//largura
				for(int j=0; j<h; j++) {	//altura
					if(i == total) {
						pw.setColor(i, j, new Color(R, G, B, 1)); 	
						if(j==h-1) 
							total += distancia + 1;
					}else 
						pw.setColor(i, j, pr.getColor(i,j));
				}
			}
			return wi;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static Image rotacionaMetade(Image imagem) {
		try {
									
			int w = (int)imagem.getWidth();
			int h = (int)imagem.getHeight();
			
			double metade = h/2;
			
			PixelReader pr = imagem.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();
			
			for(int i=0; i<w; i++) {		//largura
				for(int j=0; j<h; j++) {	//altura
					
					Color cor = pr.getColor(i, j);
					
					if(j >= metade) {		
						int inversaoH  = (int)(j+metade)-(h-1); 
						pw.setColor((w-1)-i, (h-1)-inversaoH, cor);
					}else
						pw.setColor(i, j, cor);
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
						pw.setColor(((int)PrincipalController.xFin-(i-(int)PrincipalController.xIni)),
								((int)PrincipalController.yFin-(j-(int)PrincipalController.yIni)),cor);
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
	
	public static void detectaCor(Image imagem) {
		try {
									
			int w = (int)imagem.getWidth();
			int h = (int)imagem.getHeight();
			
			PixelReader pr = imagem.getPixelReader();
			
			boolean corR = false;
			boolean corG = false;
			boolean corB = false;
		
			for(int i=0; i<w; i++) {		//largura
				for(int j=0; j<h; j++) {	//altura
					
					Color cor = pr.getColor(i, j);	
			
					if(PrincipalController.marcado && PrincipalController.filtrandoMarcacao(i, j)) {		
							if(cor.getRed()*255 >= 200 && cor.getGreen()<50 && cor.getBlue()<50)
								corR = true;
							if(cor.getGreen()*255 >= 200 && cor.getRed()<50 && cor.getBlue()<50)
								corG = true;
							if(cor.getBlue()*255 >= 200 && cor.getGreen()<50 && cor.getRed()<50)
								corB = true;
					} 
				}
			}
			String resultado = ""; 
			if(corR) { resultado += "Vermelho "; } 
			if(corG) { resultado += "Verde "; } 
			if(corB) { resultado += "Azul "; } 
			if(resultado.equals("")) { resultado = "Nenhuma cor foi selecionada"; }
			exibeMsg("Cores Selecionadas!", 
					resultado,
					"Estas foram as cores selecionadas",
					AlertType.INFORMATION);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Image diminui(Image imagem) {
		try {
									
			int w = (int)(imagem.getWidth());
			int h = (int)(imagem.getHeight());
						
			PixelReader pr = imagem.getPixelReader();
			WritableImage wi = new WritableImage(w/2,h/2);
			PixelWriter pw = wi.getPixelWriter();
			
			for(int i=0; i<w/2; i++) {		//largura
				for(int j=0; j<h/2; j++) {	//altura
					
					Color cor = pr.getColor(i*2, j*2);	
					pw.setColor(i, j, cor);		
						
				}
			}
			
			return wi;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Image aumenta(Image imagem) {
		try {
									
			int w = (int)(imagem.getWidth());
			int h = (int)(imagem.getHeight());
						
			PixelReader pr = imagem.getPixelReader();
			WritableImage wi = new WritableImage(w*2,h*2);
			PixelWriter pw = wi.getPixelWriter();
			
			for(int i=0; i<w; i++) {		//largura
				for(int j=0; j<h; j++) {	//altura
					
					Color cor = pr.getColor(i, j);	
					pw.setColor(i*2, j*2, cor);	
					pw.setColor(i*2, j*2+1, cor);		
					pw.setColor(i*2+1, j*2, cor);		
					pw.setColor(i*2+1, j*2+1, cor);		
						
				}
			}
			
			return wi;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
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
