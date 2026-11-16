package ejerciciosTaller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImagenesConvolucion {

	public static void main(String[] args) {
		new File("imagenesConvolucion").mkdirs();
		File original = new File("imagenes/paisaje.jpg");
		File destino = new File("imagenesConvolucion/paisajeConvolucion.jpg");
		
	
		try {
			BufferedImage imagen = ImageIO.read(original);
			int ancho = imagen.getWidth();
			int alto = imagen.getHeight();
			
			BufferedImage imagen2 = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
			
			int borde = 1;
			//Matriz de convolucion
			int [] kernel = {
					1/9,1/9,1/9,
					1/9,1/9,1/9,
					1/9,1/9,1/9
			};
			
			
			for(int x = borde; x < ancho - borde; x++) {
				for(int y = borde; y < alto - borde; y++) {
					
					int sumaR = 0, sumaG = 0, sumaB = 0;
					int indice = 0;
					
					
					for(int i = -borde; i <= borde; i++) {
						for(int j = -borde; j <= borde; j++) {
							
							int pixel = imagen.getRGB(x + i, y + j);
							
							Color color = new Color(pixel);
							int r = color.getRed();
							int g = color.getGreen();
							int b = color.getBlue();
							
							
							 sumaR += r * kernel[indice];
						     sumaG += g * kernel[indice];
						     sumaB += b * kernel[indice];
							
							indice++;
						}
					}
				
					
					int r1 = (int) Math.min(255, Math.max(0, sumaR));
					int g1 = (int) Math.min(255, Math.max(0, sumaG)); 
					int b1 = (int) Math.min(255, Math.max(0, sumaB)); 
					

					
					int pixel2 = (r1 << 16) | (g1 << 8) | b1;
					imagen2.setRGB(x, y, pixel2);
					
				}
				
				ImageIO.write(imagen2, "jpg", destino);
			}
			System.out.println("Imagen generada exitosamente!!");
		}catch(Exception e) {
			
		}

	}

}
