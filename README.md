# Processamento Digital de Imagem
###### Um Editor de Imagem utilizando a interface do JavaFX, a linguagem Java e alguns recursos do OpenCV.

## **Filtros:**

**Escala de Cinza:**
* Aritmética: a imagem é transformada em cinza. (Utiliza-se a média do RGB);
* Ponderada: o usuário define os valores desejados do RGB em porcentagens e a imagem é transformada em cinza baseado nos valores com maior preferência (Ex: R: 80%, G: 0%, B:20%);
  
**Limiarização:** a imagem é transformada em apenas duas cores, preto e branco (Baseia-se no limite definido pelo usuário);

**Negativa:** as cores da imagens são invertidas. (Cálculo utilizado 255-R, 255-G, 255-B);

**Desafio 1:** divide a imagem em 4 partes verticais, onde cada parte utiliza-se um filtro respectivamente: original, escala de cinza aritmética, limiarização e negativa;
  
