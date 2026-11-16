package ejerciciosTaller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class imagenContraste {

	public static void main(String[] args) {
		new File("imagenesContraste").mkdirs();
		File original = new File("imagenes/paisaje.jpg");
		File destino = new File("imagenesContraste/paisajeContraste.jpg");
		
	
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
				
				
					//subir contraste
					
					int r1 = Math.min(r + 100, 255); 
					int g1 = Math.min(g + 100, 255); 
					int b1 = Math.min(b + 100, 255); 
					
					//bajar contraste
					
//					int r1 = Math.max(r - 150, 0); 
//					int g1 = Math.min(g - 150, 0); 
//					int b1 = Math.min(b - 150, 0); 
					
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
