package ejerciciosTaller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Convolucion {


	public static void main(String[] args) {
		new File("imagenesConvolucion").mkdirs();
		File original = new File("imagenes/paisaje.jpg");
		File destino = new File("imagenesConvolucion/paisajeConvolucion.jpg");

		// Matriz de convolución 
		double[][] kernel = {
			{  0, -1,  0 },
			{ -1,  5, -1 },
			{  0, -1,  0 }
		};

		// Factor de normalización y desplazamiento 
		double factor = 1.0;
		double bias = 0.0;

		try {
			BufferedImage imagen = ImageIO.read(original);
			int ancho = imagen.getWidth();
			int alto = imagen.getHeight();

			BufferedImage imagen2 = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

			int tam = kernel.length;      // 3
			int mitad = tam / 2;          // 1

			for (int x = 0; x < ancho; x++) {
				for (int y = 0; y < alto; y++) {

					double sumaR = 0, sumaG = 0, sumaB = 0;

					// Recorrer la vecindad del píxel
					for (int i = 0; i < tam; i++) {
						for (int j = 0; j < tam; j++) {

							int px = x + j - mitad;
							int py = y + i - mitad;

							// Manejo de bordes: repetir el píxel más cercano
							px = Math.min(Math.max(px, 0), ancho - 1);
							py = Math.min(Math.max(py, 0), alto - 1);

							Color color = new Color(imagen.getRGB(px, py));

							sumaR += color.getRed()   * kernel[i][j];
							sumaG += color.getGreen() * kernel[i][j];
							sumaB += color.getBlue()  * kernel[i][j];
						}
					}

					int r1 = limitar((int) (sumaR * factor + bias));
					int g1 = limitar((int) (sumaG * factor + bias));
					int b1 = limitar((int) (sumaB * factor + bias));

					int pixel2 = (r1 << 16) | (g1 << 8) | b1;
					imagen2.setRGB(x, y, pixel2);
				}
			}

			// Se guarda una sola vez, fuera de los ciclos
			ImageIO.write(imagen2, "jpg", destino);
			System.out.println("Imagen generada exitosamente!!");

		} catch (Exception e) {
			System.out.println("Error al generar la imagen " + e.getMessage());
		}
	}

	// Mantiene el valor dentro del rango válido de color
	private static int limitar(int valor) {
		if (valor < 0) return 0;
		if (valor > 255) return 255;
		return valor;
	}

}
