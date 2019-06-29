package application;

import java.awt.image.BufferedImage;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import core.Histograma;
import core.ImageProcess;
import core.Operacao;
import core.Pixel;
import core.Ruido;
import desafio.FiltrosEspeciais;
import desafio.Segmentacao;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import opencv.OpenCV;
import prova1.ProvaPratica;

public class PrincipalController {
	
	@FXML private ImageView imageView1;
	@FXML private ImageView imageView2;
	@FXML private ImageView imageView3;
	
	//Adicao e subtracao
	@FXML public Slider sliderImg1;
	@FXML public Slider sliderImg2;

	public static int xIni = 0;
	public static int yIni = 0;
	public static int xFin = 0;
	public static int yFin = 0;
	public static boolean marcado;
	
	//Visualização de cor
	@FXML private Label lblR;
	@FXML private Label lblG;
	@FXML private Label lblB;
	@FXML private Pane paneCor;
	
	//Escala de Cinza
	@FXML public TextField txtR;
	@FXML public TextField txtG;
	@FXML public TextField txtB;
	
	//LIMEAR
	@FXML public Slider sliderLimear;
	
	//VIZINHAÇA
	@FXML public TextField txtPosi;
	@FXML public TextField txtPosj;
	
	//RUIDO
	@FXML public RadioButton rdX;
	@FXML public RadioButton rdCruz;
	@FXML public RadioButton rd3x3;
	
	//CANNY
	@FXML public TextField txtValorCanny1;
	@FXML public TextField txtValorCanny2;
	
	//EROSAO DILATACAO
	@FXML public TextField txtErosaoDilatacao;

	private Image img1;
	private Image img2;
	private Image img3;
	private int imgAtual = 0; 
	private File f;
	
	@FXML public TextField txtColunas;
	
	public ArrayList<String> historico = new ArrayList<String>();
	
	//INICIO OPENCV
	public void salvarImagem(Image image) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter("ImagemOpenCV", "*.png")); 	
		File file = new File("xmls/ImagemOpenCV.png");
		if (file !=null){
			BufferedImage bImg = SwingFXUtils.fromFXImage(image, null);
			try {
				ImageIO.write(bImg, "PNG", file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void mostrarImagem(String filePath)
	{
	   File imgSelec = new File("xmls/"+filePath);
	   try {
		   if (imgSelec != null) {
			   f = imgSelec;
			   if(f != null) {
					img3 = new Image(f.toURI().toString());
					imageView3.setImage(img3);
					imageView3.setFitWidth(img3.getWidth());
					imageView3.setFitHeight(img3.getHeight());
					imgAtual = 1;
					marcado = false;
				}
		   }
	   } catch (Exception e) {
		e.printStackTrace();
	   }
	}
	
	//INICIO DETECÇÃO DE BORDA
	@FXML
	public void bordaComCanny() {
		if(imgAtual != 0) {
			if (imgAtual == 1)
				salvarImagem(img1);
			else
				salvarImagem(img2);
			OpenCV.detectaBordasCanny(Integer.parseInt(txtValorCanny1.getText()), Integer.parseInt(txtValorCanny2.getText()));
			mostrarImagem("canny.jpg");
			historico.add("Borda com Canny " +txtValorCanny1.getText()+" - "+txtValorCanny2.getText());
			//atualizaImage3();
		} else {
			exibeMsg("Selecione  uma imagem", 
					"Selecione uma imagem do seu computador para editá-la!", 
					"Não foi possível editar imagem.", 
					AlertType.WARNING);
		}
	}	
	
	@FXML
	public void bordaComLaplace() {
		if(imgAtual != 0) {
			if (imgAtual == 1)
				salvarImagem(img1);
			else
				salvarImagem(img2);
			OpenCV.detectaBordasLaplace();
			mostrarImagem("laplace.jpg");
			historico.add("Borda com Laplace");
			//atualizaImage3();
		} else {
			exibeMsg("Selecione  uma imagem", 
					"Selecione uma imagem do seu computador para editá-la!", 
					"Não foi possível editar imagem.", 
					AlertType.WARNING);
		}
	}	
	
	@FXML
	public void bordaComSobel() {
		if(imgAtual != 0) {
			if (imgAtual == 1)
				salvarImagem(img1);
			else
				salvarImagem(img2);
			OpenCV.detectaBordasSobel();
			mostrarImagem("sobel.jpg");
			historico.add("Borda com Sobel");
			//atualizaImage3();
		} else {
			exibeMsg("Selecione  uma imagem", 
					"Selecione uma imagem do seu computador para editá-la!", 
					"Não foi possível editar imagem.", 
					AlertType.WARNING);
		}
	}	
	//FIM DETECÇÃO DE BORDA
	
	//INICIO OPENCV
	@FXML
	public void detectaFaces() {
		if(imgAtual != 0) {
			if (imgAtual == 1)
				salvarImagem(img1);
			else
				salvarImagem(img2);
			img3 = OpenCV.reconheceFaces();
			historico.add("Detecta Faces");
			//mostrarImagem("rostos_identificados.jpg");
			atualizaImage3();
		} else {
			exibeMsg("Selecione  uma imagem", 
					"Selecione uma imagem do seu computador para editá-la!", 
					"Não foi possível editar imagem.", 
					AlertType.WARNING);
		}
	}	
	
	@FXML
	public void dilatarImagem() {
		if(imgAtual != 0) {
			if (imgAtual == 1)
				salvarImagem(img1);
			else
				salvarImagem(img2);
			img3 = OpenCV.dilata(Integer.parseInt(txtErosaoDilatacao.getText()));
			//mostrarImagem("dilatacao.jpg");
			historico.add("Dilatação");
			atualizaImage3();
		} else {
			exibeMsg("Selecione  uma imagem", 
					"Selecione uma imagem do seu computador para editá-la!", 
					"Não foi possível editar imagem.", 
					AlertType.WARNING);
		}
	}
	
	
	@FXML
	public void erodirImagem() {
		if(imgAtual != 0) {
			if (imgAtual == 1)
				salvarImagem(img1);
			else
				salvarImagem(img2);
			img3 = OpenCV.erodi(Integer.parseInt(txtErosaoDilatacao.getText()));
			historico.add("Erosão");
			//mostrarImagem("erosao.jpg");
			atualizaImage3();
		} else {
			exibeMsg("Selecione  uma imagem", 
					"Selecione uma imagem do seu computador para editá-la!", 
					"Não foi possível editar imagem.", 
					AlertType.WARNING);
		}
	}
	//FIM OPENCV
	
	//INICIO PROVA 1
	@FXML
	public void exercicio1() {
		if(imgAtual != 0) {
			int colunas = Integer.parseInt(txtColunas.getText());
			if(colunas >= 1) {
				img3 = ProvaPratica.aplicaZebrado(imgAtual == 1 ? img1 : img2, colunas);
			} else {
				exibeMsg("Número de coluna incorreto", 
						"Número de colunas dever ser maior que 1", 
						"Não foi possível editar imagem.", 
						AlertType.WARNING);
			}
			atualizaImage3();
			System.out.println("IMAGEM COM FILTRO DE ZEBRA.");
		} else {
			exibeMsg("Selecione  uma imagem", 
					"Selecione uma imagem do seu computador para editá-la!", 
					"Não foi possível editar imagem.", 
					AlertType.WARNING);
		}
	}	
	
	@FXML
	public void exercicio2() {
		if(imgAtual != 0) {
			img3 = ProvaPratica.rotacionaMarcacao(imgAtual == 1 ? img1 : img2);
			atualizaImage3();
			System.out.println("MARCAÇÃO ROTACIONADA.");
		} else {
			exibeMsg("Selecione  uma imagem", 
					"Selecione uma imagem do seu computador para editá-la!", 
					"Não foi possível editar imagem.", 
					AlertType.WARNING);
		}
	}	
	
	@FXML
	public void exercicio3() {
		if(imgAtual != 0) {
			ProvaPratica.detectaPreenchimento(imgAtual == 1 ? img1 : img2);
			//atualizaImage3();
			System.out.println("PREENCHIMENTO DETECTADO.");
		} else {
			exibeMsg("Selecione  uma imagem", 
					"Selecione uma imagem do seu computador para editá-la!", 
					"Não foi possível editar imagem.", 
					AlertType.WARNING);
		}
	}	
	//FIM PROVA 1
	
	//INICIO DESAFIO
	@FXML
	public void aplicarSegmentacao() {
		if(imgAtual != 0) {
			img3 = Segmentacao.segmentacao(imgAtual == 1 ? img1 : img2);
			atualizaImage3();
			System.out.println("IMAGEM DIVIDA EM GRADES.");
			historico.add("Segmentação");
		} else {
			exibeMsg("Selecione  uma imagem", 
					"Selecione uma imagem do seu computador para editá-la!", 
					"Não foi possível editar imagem.", 
					AlertType.WARNING);
		}
	}	
	
	@FXML
	public void dividirEmGrades() {
		if(imgAtual != 0) {
			img3 = FiltrosEspeciais.aplicaGrades(imgAtual == 1 ? img1 : img2);
			atualizaImage3();
			System.out.println("SEGMENTAÇÃO APLICADA.");
		} else {
			exibeMsg("Selecione  uma imagem", 
					"Selecione uma imagem do seu computador para editá-la!", 
					"Não foi possível editar imagem.", 
					AlertType.WARNING);
		}
	}
	
	@FXML
	public void aumentarTamanho() {
		if(imgAtual != 0) {
			img3 = FiltrosEspeciais.aumenta(imgAtual == 1 ? img1 : img2);
			atualizaImage3();
			historico.add("Zoom In");
		} else {
			exibeMsg("Selecione  uma imagem", 
					"Selecione uma imagem do seu computador para editá-la!", 
					"Não foi possível editar imagem.", 
					AlertType.WARNING);
		}
	}
	
	@FXML
	public void reduzirTamanho() {
		if(imgAtual != 0) {
			img3 = FiltrosEspeciais.diminui(imgAtual == 1 ? img1 : img2);
			atualizaImage3();
			historico.add("Zoom Out");
		} else {
			exibeMsg("Selecione  uma imagem", 
					"Selecione uma imagem do seu computador para editá-la!", 
					"Não foi possível editar imagem.", 
					AlertType.WARNING);
		}
	}
	
	@FXML
	public void inverterMetade() {
		if(imgAtual != 0) {
			img3 = FiltrosEspeciais.rotacionaMetade(imgAtual == 1 ? img1 : img2);
			atualizaImage3();
			System.out.println("INVERSÂO APLICADA.");
		} else {
			exibeMsg("Selecione  uma imagem", 
					"Selecione uma imagem do seu computador para editá-la!", 
					"Não foi possível editar imagem.", 
					AlertType.WARNING);
		}
	}
		
	@FXML
	public void rotacionarMarcacao() {
		if(imgAtual != 0) {
			if(marcado) {
				img3 = FiltrosEspeciais.rotacionaMarcacao(imgAtual == 1 ? img1 : img2);
				atualizaImage3();
				System.out.println("MARCAÇÃO ROTACIONADA APLICADA.");
			} else {
				exibeMsg("Sem marcacação", 
						"Selecione uma região da imagem com mouse!", 
						"Não foi possível editar imagem.", 
						AlertType.WARNING);
			}
		} else {
			exibeMsg("Selecione  uma imagem", 
					"Selecione uma imagem do seu computador para editá-la!", 
					"Não foi possível editar imagem.", 
					AlertType.WARNING);
		}
	}
	
	@FXML
	public void descobrirCorMarcada() {
		if(imgAtual != 0) {
			if(marcado) {
				FiltrosEspeciais.detectaCor(imgAtual == 1 ? img1 : img2);
			} else {
				exibeMsg("Sem marcacação", 
						"Selecione uma região da imagem com mouse!", 
						"Não foi possível editar imagem.", 
						AlertType.WARNING);
			}
		} else {
			exibeMsg("Selecione  uma imagem", 
					"Selecione uma imagem do seu computador para editá-la!", 
					"Não foi possível editar imagem.", 
					AlertType.WARNING);
		}
	}
	//FIM DESAFIO
	
	//COMEÇO EQUALIZAR HISTOGRAMA
	@FXML
	public void equalizarPor255() {
		if(imgAtual != 0) {
			img3 = Histograma.equalizador255(imgAtual == 1 ? img1 : img2);
			atualizaImage3();
			System.out.println("EQUALIZAÇÃO APLICADA.");
			historico.add("Equalização Por 255");
		} else {
			exibeMsg("Selecione  uma imagem", 
					"Selecione uma imagem do seu computador para editá-la!", 
					"Não foi possível editar imagem.", 
					AlertType.WARNING);
		}
	}
	
	@FXML
	public void equalizarXTons() {
		if(imgAtual != 0) {
			img3 = Histograma.equalizadorXTons(imgAtual == 1 ? img1 : img2);
			atualizaImage3();
			System.out.println("EQUALIZAÇÃO APLICADA.");
			historico.add("Equalização Por X Tons");
		} else {
			exibeMsg("Selecione  uma imagem", 
					"Selecione uma imagem do seu computador para editá-la!", 
					"Não foi possível editar imagem.", 
					AlertType.WARNING);
		}
	}
	//FIM EQUALIZAR HISTOGRAMA
	
	//COMEÇO ADICAO E SUBTRACAO
	@FXML
	public void aplicarAdicao() {
		if((img1 != null) && (img2 != null)) {
			img3 = Operacao.adicao(img1, img2, sliderImg1.getValue()/100, sliderImg2.getValue()/100);
			atualizaImage3();
			System.out.println("ADIÇÃO EFETUADA.");
			historico.add("Adição "+ sliderImg1.getValue() + "% - "+ sliderImg2.getValue()+"%");
		} else {
			exibeMsg("Selecione  uma imagem", 
					"Selecione uma imagem do seu computador para editá-la!", 
					"Não foi possível editar imagem.", 
					AlertType.WARNING);
		}
	}
	
	@FXML
	public void aplicarSubtracao() {
		if((img1 != null) && (img2 != null)) {
			img3 = Operacao.subtracao(img1, img2, sliderImg1.getValue()/100, sliderImg2.getValue()/100);
			atualizaImage3();
			System.out.println("SUBTRAÇÃO EFETUADA.");
			historico.add("Subtração "+ sliderImg1.getValue() + "% - "+ sliderImg2.getValue()+"%");
		} else {
			exibeMsg("Selecione  uma imagem", 
					"Selecione uma imagem do seu computador para editá-la!", 
					"Não foi possível editar imagem.", 
					AlertType.WARNING);
		}
	}
	//FIM ADICAO E SUBTRACAO
	
	//COMEÇO RUIDO
	@FXML
	public void reduzirRuido() {
		if(imgAtual != 0) {
			if(rdX.isSelected()) {
				System.out.println("REDUZINDO RUIDO");
				img3 = Ruido.reduzirRuidoX(imgAtual == 1 ? img1 : img2);
			} else if(rdCruz.isSelected()) {
				img3 = Ruido.reduzirRuidoCruz(imgAtual == 1 ? img1 : img2);
			} else if(rd3x3.isSelected()) {
				img3 = Ruido.reduzirRuido3x3(imgAtual == 1 ? img1 : img2);
			}
			historico.add("Ruídos");
			atualizaImage3();
		} else {
			exibeMsg("Selecione  uma imagem", 
					"Selecione uma imagem do seu computador para editá-la!", 
					"Não foi possível editar imagem.", 
					AlertType.WARNING);
		}
	}
	//FIM RUIDO
	
	//COMEÇO VIZINHAÇA
	@FXML
	public void descobrirVizinhoX() {
		if(imgAtual != 0) {
			int i = Integer.parseInt(txtPosi.getText());
			int j = Integer.parseInt(txtPosj.getText());
			Pixel.descobrirVizinhacaX(imgAtual == 1 ? img1 : img2, i, j);
			atualizaImage3();
		} else {
			exibeMsg("Selecione  uma imagem", 
					"Selecione uma imagem do seu computador para editá-la!", 
					"Não foi possível editar imagem.", 
					AlertType.WARNING);
		}
	}
	
	@FXML
	public void descobrirVizinhoCruz() {
		if(imgAtual != 0) {
			int i = Integer.parseInt(txtPosi.getText());
			int j = Integer.parseInt(txtPosj.getText());
			Pixel.descobrirVizinhacaCruz(imgAtual == 1 ? img1 : img2, i, j);
			atualizaImage3();
		} else {
			exibeMsg("Selecione  uma imagem", 
					"Selecione uma imagem do seu computador para editá-la!", 
					"Não foi possível editar imagem.", 
					AlertType.WARNING);
		}
	}
	
	@FXML
	public void descobrirVizinho3x3() {
		if(imgAtual != 0) {
			int i = Integer.parseInt(txtPosi.getText());
			int j = Integer.parseInt(txtPosj.getText());
			Pixel.descobrirVizinhaca3X3(imgAtual == 1 ? img1 : img2, i, j);
			atualizaImage3();
		} else {
			exibeMsg("Selecione  uma imagem", 
					"Selecione uma imagem do seu computador para editá-la!", 
					"Não foi possível editar imagem.", 
					AlertType.WARNING);
		}
	}
	//FIM VIZINHAÇA 
	
	//COMEÇO IMAGE PROCESS
	@FXML
	//Imagem dividida em 4 partes, primeira original, depois escala de cinza, limear, negativa
	public void aplicarDesafio() {
		if(imgAtual != 0) {
			img3 = ImageProcess.dividirFiltros(imgAtual == 1 ? img1 : img2);
			atualizaImage3();
			System.out.println("DESAFIO COMPLETADO.");
		} else {
			exibeMsg("Selecione  uma imagem", 
					"Selecione uma imagem do seu computador para editá-la!", 
					"Não foi possível editar imagem.", 
					AlertType.WARNING);
		}
	}
	
	@FXML
	public void aplicarLimiarizacao() {
		if(imgAtual != 0) {
			System.out.println(sliderLimear.getValue()/255);
			img3 = ImageProcess.limiarizacao(imgAtual == 1 ? img1 : img2, (sliderLimear.getValue()/255));
			atualizaImage3();
			System.out.println("LIMEARIZAÇÃO APLICADA.");
			historico.add("Limiar "+sliderLimear.getValue());
		} else {
			exibeMsg("Selecione  uma imagem", 
					"Selecione uma imagem do seu computador para editá-la!", 
					"Não foi possível editar imagem.", 
					AlertType.WARNING);
		}
	}
	
	@FXML
	public void aplicarNegativa() {
		if(imgAtual != 0) {
			img3 = ImageProcess.negativa(imgAtual == 1 ? img1 : img2);
			atualizaImage3();
			System.out.println("NEGATIVA APLICADA.");
			historico.add("Negativa");
		} else {
			exibeMsg("Selecione  uma imagem", 
					"Selecione uma imagem do seu computador para editá-la!", 
					"Não foi possível editar imagem.", 
					AlertType.WARNING);
		}
	}
	
	@FXML
	public void escalaDeCinzaMedia() {
		if(imgAtual != 0) {
			img3 = ImageProcess.escalaDeCinzaMedia(imgAtual == 1 ? img1 : img2);
			atualizaImage3();
			System.out.println("ESCALA DE CINZA MÉDIA APLICADA.");
			historico.add("Escala de Cinza Média");
		} else {
			exibeMsg("Selecione  uma imagem", 
					"Selecione uma imagem do seu computador para editá-la!", 
					"Não foi possível editar imagem.", 
					AlertType.WARNING);
		}
	}
	
	@FXML public void escalaDeCinzaPonderada() {
		try {
			double rPorcentagem = (double) (Integer.parseInt(txtR.getText())/100.0);
			double gPorcentagem = (double) (Integer.parseInt(txtG.getText())/100.0);
			double bPorcentagem = (double) (Integer.parseInt(txtB.getText())/100.0);
			System.out.println("Red: "+rPorcentagem + " Verde: "+gPorcentagem+" Azul: "+bPorcentagem);
			if(rPorcentagem + gPorcentagem + bPorcentagem <= 1) {
				if(imgAtual != 0) {
					img3 = ImageProcess.escalaDeCinzaPonderada(imgAtual == 1 ? img1 : img2, rPorcentagem, gPorcentagem, bPorcentagem);
					atualizaImage3();
					System.out.println("ESCALA DE CINZA MÉDIA APLICADA.");
					historico.add("Escala de Cinza Ponderada "+rPorcentagem+" - "+gPorcentagem+" - "+bPorcentagem);
				}  else {
					exibeMsg("Selecione  uma imagem", 
							"Selecione uma imagem do seu computador para editá-la!", 
							"Não foi possível editar imagem.", 
							AlertType.WARNING);
				}
			} else {
				exibeMsg("Soma maior que 100", 
						"A soma de todas as escalas deve ser menor ou igual que 100!", 
						"Não foi possível editar imagem.", 
						AlertType.ERROR);
			}
		}
		catch (NumberFormatException ex )
		{
			exibeMsg("Caracteres não são aceitos!", 
					"Somente números inteiros são aceitos como porcentagens", 
					"Não foi possível editar imagem.", 
					AlertType.ERROR);
		}
	}
	//FIM IMAGE PROCESS
	
	private void atualizaImage3() {
		imageView3.setImage(img3);
		imageView3.setFitWidth(img3.getWidth());
		imageView3.setFitHeight(img3.getHeight());
	}
	
	@FXML 
	public void abreImagem1() {
		f=selecionaImagem();
		if(f != null) {
			img1 = new Image(f.toURI().toString());
			imageView1.setImage(img1);
			imageView1.setFitWidth(img1.getWidth());
			imageView1.setFitHeight(img1.getHeight());
			imgAtual = 1;
			marcado = false;
		}
	}
	
	@FXML 
	public void abreImagem2() {
		f=selecionaImagem();
		if(f != null) {
			img2 = new Image(f.toURI().toString());
			imageView2.setImage(img2);
			imageView2.setFitWidth(img2.getWidth());
			imageView2.setFitHeight(img2.getHeight());
			imgAtual = 2;
			marcado = false;
		}
	} 
	
	@FXML
	public void rasterImg(MouseEvent evt) {
		ImageView iw = (ImageView)evt.getTarget();
		if(iw.getImage()!=null) 
			verificaCor(iw.getImage(), (int)evt.getX(), (int)evt.getY());	
	}
	
	@FXML
	public void marcarImagemInicial(MouseEvent evt) {
			ImageView iw = (ImageView)evt.getTarget();
			if(iw.getImage()!=null) {
				xIni = (int)evt.getX()-1;
				yIni = (int)evt.getY()-1;
				System.out.println("Posição: "+xIni+" "+yIni);
			}
	}
	
	@FXML
	public void marcarImagemFinal(MouseEvent evt) {
			ImageView iw = (ImageView)evt.getTarget();
			if(iw.getImage()!=null) {
				xFin = (int)evt.getX()-1;
				yFin = (int)evt.getY()-1;
				System.out.println("Posição: "+xFin+" "+yFin);
				marcado = true;
			}
	}
	
	@FXML
	public void trocarImageView() {
		img1 = img3;
		imageView1.setImage(img1);
		imageView1.setFitWidth(img1.getWidth());
		imageView1.setFitHeight(img1.getHeight());
	}
	
	private void verificaCor(Image img, int x, int y){
	try {
			Color cor = img.getPixelReader().getColor(x-1, y-1);
			lblR.setText("R: "+(int) (cor.getRed()*255));
			lblG.setText("G: "+(int) (cor.getGreen()*255));
			lblB.setText("B: "+(int) (cor.getBlue()*255));
			txtPosi.setText(String.valueOf(y));
			txtPosj.setText(String.valueOf(x));
			//modificar a cor de fundo do panel
			paneCor.setStyle("-fx-background-color: RGB("+(int)(cor.getRed()*255)+","
								+(int)(cor.getGreen()*255)+","
								+(int)cor.getBlue()*255+")");
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	private File selecionaImagem() {
		   FileChooser fileChooser = new FileChooser();
		   fileChooser.getExtensionFilters().add(new 
				   FileChooser.ExtensionFilter(
						   "Imagens", "*.jpg", "*.JPG", 
						   "*.png", "*.PNG", "*.gif", "*.GIF", 
						   "*.bmp", "*.BMP")); 	
		   fileChooser.setInitialDirectory(new File(
				   //"C:/Users/apoio.inftb/Pictures"));
				   "C:/Users/Jonathan/Pictures"));	
		   File imgSelec = fileChooser.showOpenDialog(null);
		   try {
			   if (imgSelec != null) {
				   return imgSelec;
			   }
		   } catch (Exception e) {
			e.printStackTrace();
		   }
		   return null;
	}
	
	private void exibeMsg(String titulo, String cabecalho, String msg, AlertType tipo) {
		Alert alert = new Alert(tipo);
		alert.setTitle(titulo);
		alert.setHeaderText(cabecalho);
		alert.setContentText(msg);
		alert.showAndWait();
	}
	
	@FXML
	public void salvar(){
			if (img3 != null){
				FileChooser fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().add(
						new FileChooser.ExtensionFilter("Imagem", "*.png")); 	
				fileChooser.setInitialDirectory(new 
						File("C:/Users/Jonathan/Pictures"));
						//File("C:/Users/apoio.inftb/Pictures"));
				File file = fileChooser.showSaveDialog(null);
				if (file !=null){
					BufferedImage bImg = SwingFXUtils.fromFXImage(img3, null);
					try {
						ImageIO.write(bImg, "PNG", file);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}else{
				exibeMsg("Salvar imagem", 
						"Não é possível salvar a imagem.", 
						"Não há nenhuma imagem modificada.", 
						AlertType.ERROR);
			}
	  }	
	
	@FXML
	public void abreModalHistograma(ActionEvent event) {
		try {
			
			
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().
					getResource("ModalHistograma.fxml"));
			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.setTitle("Histograma");
			stage.initOwner(((Node)event.getSource()).getScene().getWindow());
			stage.show();
			
			HistogramaController controller = (HistogramaController)loader.getController();
			if(img1!=null)
				Histograma.getGrafico(img1, controller.grafico1);		
			if(img2!=null)
				Histograma.getGrafico(img2, controller.grafico2);			
			if(img3!=null)
				Histograma.getGrafico(img3, controller.grafico3);			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@FXML
	public void limparMarcacao() {
		xIni = 0; yIni = 0; xFin = 0; yFin = 0;
		marcado = false;
		System.out.println("MARCAÇÃO LIMPADA!");
		System.out.println("----------------------------------");
		System.out.println("HISTÓRICO:");
		historico.forEach(System.out::println);
		historico.add("-----###-----");
		historico.add("-----###----");
	}
	
	//FILTRAR MARCAÇÃO DA FOTO
	public static boolean filtrandoMarcacao(int i, int j) {
			if(((xIni <= i && yIni <= j) && (xFin >= i && yFin >= j)) 
				|| ((xFin <= i && yFin <= j) && (xIni >= i && yIni >= j))
				|| ((xFin >= i && yFin <= j) && (xIni <= i && yIni >= j))
				|| ((xFin <= i && yFin >= j) && (xIni >= i && yIni <= j))) 		
				return true;
			else 
				return false;
	}
	
	//CRIANDO BORDA PRETA DE MARCAÇÃO
	public static boolean criandoBorda(int i, int j) {
		if(filtrandoMarcacao(i,j)) {
			if((xIni == i || yIni == j || xFin == i || yFin == j)) 	
				return true;
			else 
				return false;
		}
		return false;
	}
	
}
