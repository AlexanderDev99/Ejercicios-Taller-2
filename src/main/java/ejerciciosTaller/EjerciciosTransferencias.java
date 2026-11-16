package ejerciciosTaller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class EjerciciosTransferencias {

	public static void main(String[] args) {
		
		new File("imagenesBitsReducidos").mkdirs();
		File original = new File("imagenes/paisaje.jpg");
		File destino = new File("imagenesBitsReducidos/paisajeBits.jpg");
		
	
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
				
					//representacion de un color pero con 5 bits y no con 8
					//int r1 = (r >> 3) & 0x1F; //HEXADECIMAL
					//int g1 = (g >> 3) & 0B11111; //BINARIO
					//int b1 = (b >> 3) & 31; //DECIMAL
					
					/*
					 * regla de 3
					 * 31 -> 255
					 * r1 -> x
					 * 
					 */
					
					//int r2 = (r1 * 255) /31;
					//int g2 = (g1 * 255) /31;
					//int b2 = (b1 * 255) /31;
					
					//solo 2 bits
					
					int r1 = (r >> 4) & 0x15; //HEXADECIMAL
					int g1 = (g >> 4) & 0b1111; //BINARIO
					int b1 = (b >> 4) & 15; //DECIMAL
					
					/*
					 * regla de 3
					 * 3 -> 255
					 * r1 -> x
					 * 
					 */
					
					
					int r2 = (r1 * 255) /31;
					int g2 = (g1 * 255) /31;
					int b2 = (b1 * 255) /31;
					
					
					//escala de grises 
					//int gris = (int) ((0.2126 * r2)+(0.7152 * g2)+(0.0722 * b2));
					
					/*
					 * blanco y negro
					 * r2 = g2 = b2;
					 */
					
					//demostracion de la perdida de los bits
					int r3 = Math.abs(r - r2);
					int g3 = Math.abs(g - g2);
					int b3 = Math.abs(b - b2);
					
					
					int pixel2 = (r1 << 16) | (g1 << 8) | b1;
					imagen2.setRGB(x, y, pixel2);
					
				}
				
				ImageIO.write(imagen2, "jpg", destino);
			}
			System.out.println("Imagen generada exitosamente!!");
		}catch(Exception e) {
			
		}
		
	
	}
	
	static void imagenNiveles() {
		
		new File("imagenesTransferencia").mkdirs();
		File original = new File("imagenes/paisaje.jpg");
		File destino = new File("imagenesTransferencia/paisajeFinal.jpg");
		
	
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
				
					
					//blanco y negro
					r = nuevaProfundidad(r, 64); //0 - 255
					g = nuevaProfundidad(g, 64); //0 - 255
					b = nuevaProfundidad(b, 64); //0 - 255
					
					//blanco y negro
					//r = g = b;
					
					int pixel2 = (r << 16) | (g << 8) | b;
					imagen2.setRGB(x, y, pixel2);
					
				}
				
				ImageIO.write(imagen2, "jpg", destino);
			}
			System.out.println("Imagen generada exitosamente!!");
		}catch(Exception e) {
			
		}
		
	}
	
	static int nuevaProfundidad(int profundidad, int niveles) {
		int valor = 0;
		int valor2 = 0;
		
		if(niveles == 2) {
		
			valor = (profundidad < 128 ? 0: 255); 
			valor2 = valor;
		}
		
		if (niveles == 4) {
		
			if(profundidad < 64)
				valor = 0;
			if(profundidad >= 64 && profundidad <= 128)
				valor = 64;
			if(profundidad >= 128 && profundidad <= 192)
				valor = 128;
			if(profundidad >= 192)
				valor = 192;
			
			
			/*192 --> 255
			*valor --> x
			*/
			valor2 = (valor * 255)/192;
		}
		
		int n = 256 / niveles;
		int saltos = 255/(niveles - 1);
		
		valor = (profundidad / n) * saltos;
			
		return valor;
	}

}
