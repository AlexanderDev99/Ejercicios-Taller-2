package ejerciciosTaller;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

public class Histograma {
	
	private final static int mascara = 0xFF;
	
	 public static void main(String[] args) {

	        new File("Histogramas").mkdirs();

	        File original = new File("imagenes/auto.jpg");
	        File destino = new File("Histogramas/histograma.png");

	        try {

	            BufferedImage imagen = ImageIO.read(original);
	            int ancho = imagen.getWidth();
	            int alto = imagen.getHeight();

	            BufferedImage imagenHisto = new BufferedImage(ancho,alto,BufferedImage.TYPE_INT_RGB);


	            //arreglo del histograma
	            int[] histoRed = new int[256];

	            //optimizacion primero filas luego columnas
	            for (int y = 0; y < alto; y++) {
	                for (int x = 0; x < ancho; x++) {

	                    int pixel = imagen.getRGB(x, y);

	                    //usando optimizacion
	                    int r = (pixel >> 16) & mascara;
	                    int g = (pixel >> 8) & mascara;
	                    int b = pixel & mascara;
	                   
	                    //guardamos las intensidades
	                    histoRed[r]++;
	                 
	                }
	            }
	            
	            //obtenemos la minima intensidad
	            int minInten = Arrays.stream(histoRed).min().getAsInt();
	         
	            //intensidad maxima
	            int maxInten = Arrays.stream(histoRed).max().getAsInt();

	            System.out.println("Intensidad minima: " + minInten);
	            System.out.println("Intensidad maxima: " + maxInten);
	            
	            IntStream.range(0, histoRed.length).forEachOrdered(x -> System.out.println("histo[" + x + "] = "
	            		+ histoRed[x]));
	            
	            //generamos el histograma
	  
	            Graphics2D gr = imagenHisto.createGraphics();
	            gr.setColor(Color.WHITE); //limpiamos la pantalla
	            gr.fillRect(0, 0, ancho, alto); //definimos el tamaño de la ventana igual a la imagen original
	            gr.setColor(Color.RED); //dibujamos con el color rojo
	            
	            
	            Stroke st = new BasicStroke(3);
	            gr.setStroke(st);
	            gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	            
	            //definimos la escala de los valores del histograma con respecto al tamaño de la imagen del 
	            //histograma
	            float escalaX = ancho/256.0f;
	            float escalaY = (float) alto / maxInten;
	            
	            
	            // recorremos el array del histograma
	            for(int i = 1; i < histoRed.length; i++) {
	            	
	            	// obtenemos los valores de x y y del array del histograma
	            	int x1 = (int) ((i - 1) * escalaX);
	            	int y1 = (int) (alto - histoRed[i - 1] * escalaY);
	            	
	            	int x2 = (int) ((i) * escalaX);
	            	int y2 = (int) (alto - histoRed[i] * escalaY);
	            	
	            	//dibujamos
	            	gr.drawLine(x1, y1, x2, y2);   	
	            }

	            ImageIO.write(imagenHisto, "png", destino);
	            System.out.println("histograma generado exitosamente!!");

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	        //DEBER lo mismo pero la imagen completa del histograma con el resto de valores, es decir 
	        //en el mismo histograma graficar el histograma de los 3 canales, en el formato de picos y de barras. 
	    }

}
