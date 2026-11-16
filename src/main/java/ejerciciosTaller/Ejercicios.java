package ejerciciosTaller;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Ejercicios {
	
	static final int mascara = 0xFF;

	public static void main(String[] args) {
		new File("imagenes").mkdirs();
		new File("imagenGrisColorida").mkdirs();
		new File("secuenciaImagenes").mkdirs();
		
		generarImagenGris();
		generarImagenGrisColorida();
		
		File imagenEntrada = new File("imagenes/Slayer.jpg");
		int numImagenes = 10;
		secuenciaImagenes(imagenEntrada, numImagenes);
		

	}
	
	static public void generarImagenGris() {
		File imagenOriginal = new File("imagenes/Slayer.jpg");
		File nuevaImagen = new File("imagenes/SlayerGris.jpg");
		try {
		BufferedImage imagen = ImageIO.read(imagenOriginal);
		int ancho = imagen.getWidth();
		int alto = imagen.getHeight();
		BufferedImage imagenGris = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
		
		for(int x = 0; x < ancho; x++) {
			for(int y = 0; y < alto; y++) {
				int pixel = imagen.getRGB(x, y);
				
				//manipular los pixeles
				int r = (pixel >> 16) & mascara;
				int g = (pixel >> 8) & mascara;
				int b = pixel & mascara;
				
				//obtenemos los pixeles para la imagen gris
				int gris = (r + g + b)/3;
				
				int pixel2 = (gris << 16) | (gris << 8) | gris;
				imagenGris.setRGB(x, y, pixel2);
			}
			
		}
		
		ImageIO.write(imagenGris, "jpg", nuevaImagen);
		System.out.println("Imagen generada correctamente!!");
		
		}catch(Exception e) {
	
			System.out.println("Error al generar las imagenes!!" + e.getMessage());
		}
		
		
	}
	
	static public void generarImagenGrisColorida() {
		File imagenOriginal = new File("imagenes/SlayerGris.jpg");
		File nuevaImagen = new File("imagenGrisColorida/SlayerColorido.jpg");
		try {
		BufferedImage imagen = ImageIO.read(imagenOriginal);
		int ancho = imagen.getWidth();
		int alto = imagen.getHeight();
		BufferedImage imagenColorida = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
		
		for(int x = 0; x < ancho; x++) {
			for(int y = 0; y < alto; y++) {
				int pixel = imagen.getRGB(x, y);
				
				//manipular los pixeles
				int r = (pixel >> 16) & mascara;
				int g = (pixel >> 8) & mascara;
				int b = pixel & mascara;
				
				//obtenemos los pixeles para la imagen gris
				int gris = (r + g + b)/3;
				
				//generamos el color
				r = gris;
				g = gris / 3;
				b = gris / 2;
				
				int pixel2 = (r << 16) | (g << 8) | b;
				imagenColorida.setRGB(x, y, pixel2);
			}
			
		}
		
		ImageIO.write(imagenColorida, "jpg", nuevaImagen);
		System.out.println("Imagen generada correctamente!!");
		
		}catch(Exception e) {
	
			System.out.println("Error al generar las imagenes!!" + e.getMessage());
		}
		
	}
	
	
	static void secuenciaImagenes(File imagenEntrada, int numeroImagenes) {
		
		try {
			//cargamos la imagen
			BufferedImage imagen = ImageIO.read(imagenEntrada);
			int ancho = imagen.getWidth();
			int alto = imagen.getHeight();
			
			for(int i = 1; i <=numeroImagenes; i ++) {
				BufferedImage secuencia = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
				
				double opacidad = 0.20;
				double progreso = (double) (i-1)/(numeroImagenes-1);
				//Operamo el canal alpha
				int alpha = (int) (255 * (1.0 - progreso * (1.0 - opacidad)));
				
				for(int x = 0; x < ancho; x++) {
					for (int y = 0; y < alto; y++) {
						int pixel = imagen.getRGB(x, y);
						
						//operamos pixeles
						int r = (pixel >> 16) & mascara;
						int g = (pixel >> 8) & mascara;
						int b = pixel & mascara;
						
						int pixel2 = (alpha << 24) | (r << 16) | (g << 8) | b;
						secuencia.setRGB(x, y, pixel2);
					}
				}
				
				File secuenciaSalida = new File("secuenciaImagenes/secuencia" + i + ".png");
				System.out.println("Imagen: " + i + " opacidad " + alpha);
				ImageIO.write(secuencia, "png", secuenciaSalida);
			}
			
		}catch(Exception e) {
			System.out.println("Error al generar la secuencia!!" + e.getMessage());
		}
		
	}

}
