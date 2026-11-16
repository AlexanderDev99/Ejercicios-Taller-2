package ejerciciosTaller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class imagenesNegativo {

	public static void main(String[] args) {
		new File("imagenesNegativas").mkdirs();
		File original = new File("imagenes/paisaje.jpg");
		File destino = new File("imagenesNegativas/paisajeNegativo.jpg");
		
	
		try {
			BufferedImage imagen = ImageIO.read(original);
			int ancho = imagen.getWidth();
			int alto = imagen.getHeight();
			
			BufferedImage imagen2 = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
			
			for(int x = 0; x < ancho; x++) {
				for(int y = 0; y < alto; y++) {
					int pixel = imagen.getRGB(x, y);
					Color color = new Color(pixel);
					
					int r = color.getRed();
					int g = color.getGreen();
					int b = color.getBlue();
				
				
					//negativo de una imagen
					
					int r1 = (255 - r); 
					int g1 = (255 - g); 
					int b1 = (255 - b); 
					
					
					int pixel2 = (r1 << 16) | (g1 << 8) | b1;
					imagen2.setRGB(x, y, pixel2);
					
				}
				
				ImageIO.write(imagen2, "jpg", destino);
			}
			System.out.println("Imagen generada exitosamente!!");
		}catch(Exception e) {
			System.out.println("Error al generar la imagen " + e.getMessage());
		}
		

	}

}
