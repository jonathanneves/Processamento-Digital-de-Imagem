# Processamento Digital de Imagem
###### Um Editor de Imagem utilizando a interface do JavaFX, a linguagem Java e alguns recursos do OpenCV.

### Histograma:

Gráfico das três imagens do editor onde mostra a contagem total dos pixels RGB (Vermelho, Verde e Azul) de 0 a 255;
<br/>

### **Filtros:**

**Escala de Cinza:**
* Aritmética: a imagem é transformada em cinza. (Utiliza-se a média do RGB);
* Ponderada: o usuário define os valores desejados do RGB em porcentagens e a imagem é transformada em cinza baseado nos valores com maior preferência (Ex: R: 80%, G: 0%, B:20%);
  
**Limiarização:** a imagem é transformada em apenas duas cores, preto e branco (Baseia-se no limite definido pelo usuário);

**Negativa:** as cores da imagens são invertidas. (Cálculo utilizado 255-R, 255-G, 255-B);

**Desafio 4 filtros:** divide a imagem em 4 partes verticais, onde cada parte utiliza-se um filtro respectivamente: original, escala de cinza aritmética, limiarização e negativa;

**Vizinhaça Pixel:** não é um filtro de imagem, é apenas um detector de pixels. O usuário pode clicar em qualquer posição da imagem ou digitar manualmente, e utilizar os três tipos de Vizinhaça (X, Cruz ou 3x3) para descobrir quais são os pixels que estão ao redor do pixel escolhido;

**Adição:** união de duas imagens diferentes em uma só. O usuário pode definir a porcentagem das 2 imagens escolhendo qual vai prevalecer sobre a outra;

**Subtração:** remove as semelhanças entre duas imagens transformando em uma imagem só. O usuário pode definir a porcentagem das 2 imagens escolhendo qual vai prevalecer sobre a outra;

**Equalização:** filtro sobre a imagem na qual utiliza-se cálculo acumulado baseado no _histograma_. Existe a equialização 255 e por X tons cada um com detalhes diferentes;

**Desafio 1 (Segmentação):** a imagem é transformada em apenas três cores - semelhante a limiarização - se baseando em condições de valores e média do RGB do pixel;

**Dividir em Grade** o usuário defini a cor e a distância entre as linhas das grades.

**Aumentar tamanho:** a tamanho da imagem é aumentada em 2X.

**Reduzir tamanho:** o tamanho da imagem é reduzido em 2X.

**Inverter metade:** a metade da imagem é invertida (cabeça para baixo).

**Rotacionar Marcação:** o usuário pode marcar com o mouse clicando em uma posição e levando até outra posição (criando um quadrado). E a marcação selecionada pelo usuário será invertida (cabeça para baixo).

**Exercício 1** o usuário pode definir o número de colunas e a imagem será dividida entre uma coluna escala de cinza e outra coluna a imagem original;

**Exercício 2:** Realizar o mesmo processo do _Rotacionar Marcação_;

**Exercício 3:** detecta se a imagem é um quadrado ou círculo. Só funciona em uma imagem com a forma geometrica na cor preta e espessura 1;
<br/>

### **Open CV:**

**Detector de Faces:** detecta quantos rosto a imagem possui, delimitando com um quadrado verde;

**Dilatação:** dilata uma imagem ou expande os pixels da imagem dentro do quadrante. É possível definir o tamanho desejado.

**Erosão:** encolhe uma image, erodindo os pixels da imagem dentro do quadrante. É possível definir o tamanho desejado.

**Detector de bordas:** 
 * Canny: deixa a imagem preto e branco. Onde todas as bordas detectadas ficam em linhas brancas. É possível definir dois parâmetros no processo (Sugestão 150 e 150).
 * Sobel e Laplace: deixa a imagem preta com alguns tons de cinza e branco destacando as bordas da imagem. As duas funções são bem semelhantes.

