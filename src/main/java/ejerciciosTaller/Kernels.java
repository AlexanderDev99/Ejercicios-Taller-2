package ejerciciosTaller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;

import javax.imageio.ImageIO;

public class Kernels {
	
	static float[] deteccionBorde1 = { 1, 0, -1, 0, 0, 0, -1, 0, 1 };

	static float[][] deteccionBorde1M = { { 1, 0, -1 }, { 0, 0, 0 }, { -1, 0, 1 } };

	static float[] deteccionBorde2 = { 0, 1, 0, 1, -4, 1, 0, 1, 0 };

	static float[] deteccionBorde3 = { 1, 1, 1, 1, -8, 1, 1, 1, 1 };

	static float[] sharpen = { 0, -1, 0, -1, 5, -1, 0, -1, 0 };

	static float[][] sharpenM = { { 0, -1, 0 }, { -1, 5, -1 }, { 0, -1, 0 } };

	static float[][] sharpen2M = { { 0, 1, 0 }, { 1, -5, 1 }, { 0, 1, 0 } };

	static float[][] enfoqueM = { { 0, -1, 0 }, { -1, 5, -1 }, { 0, -1, 0 } };

	static float[] boxBlur = { 1f / 9, 1f / 9, 1f / 9, 1f / 9, 1f / 9, 1f / 9, 1f / 9, 1f / 9, 1f / 9 };

	public static void main(String[] args) {

		File imagenOriginal = new File("imagenes/paisaje.jpg");
		File carpeta = new File("img/ConvolucionKernels3veces");

		float[][] kernel = sharpen2M;

		try {
			carpeta.mkdirs();

			BufferedImage bi1 = ImageIO.read(imagenOriginal);

			int repeticiones = 3;

			for (int i = 0; i < repeticiones; i++) {
				// bi1= funcionConvolucion(bi1);
				bi1 = funcionConvolucion(bi1);
				System.out.println("iteraciones: " + (i + 1));
			}

			/*
			 * BufferedImage bi2 = funcionConvolucion(bi1); bi2 = funcionConvolucion(bi2);
			 * bi2 = funcionConvolucion(bi2);
			 */

			File salida = new File(carpeta, "autoConvolucion.jpg");

			ImageIO.write(bi1, "jpg", salida);

			System.out.println("Filtro de convolucion aplicado correctamente " + repeticiones + " veces");

		} catch (Exception e) {
			System.out.println("Error al procesar la imagen: " + e.getMessage());
		}
	}

	static BufferedImage funcionConvolucion(BufferedImage bi1) {

		int ancho = bi1.getWidth();
		int alto = bi1.getHeight();

		int borde = 1;
		float[][] kernel = deteccionBorde1M;

		BufferedImage bi2 = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

		/*for (int x = 0; x < ancho; x++) {
			for (int y = 0; y < alto; y++) {
				bi2.setRGB(x, y, bi1.getRGB(x, y));
			}
		}*/
		
		//cambiar el color de los bordes
		for (int x = 0; x < ancho; x++) {
		    for (int y = 0; y < alto; y++) {

		        if (x < borde || x >= ancho - borde ||
		            y < borde || y >= alto - borde) {

		            // Pintar los bordes de negro
		            bi2.setRGB(x, y, Color.BLACK.getRGB());

		        } else {

		            // Copiar los píxeles interiores
		            bi2.setRGB(x, y, bi1.getRGB(x, y));

		        }
		    }
		}

		for (int x = borde; x < ancho - borde; x++) {
			for (int y = borde; y < alto - borde; y++) {

				float sumaR = 0;
				float sumaG = 0;
				float sumaB = 0;

				for (int i = -borde; i <= borde; i++) {
					for (int j = -borde; j <= borde; j++) {

						int pixel1 = bi1.getRGB(x + i, y + j);

						Color color = new Color(pixel1);

						int r = color.getRed();
						int g = color.getGreen();
						int b = color.getBlue();

						sumaR += r * kernel[i + borde][j + borde];
						sumaG += g * kernel[i + borde][j + borde];
						sumaB += b * kernel[i + borde][j + borde];
					}
				}

				int r1 = Math.min(255, Math.max(0, Math.round(sumaR)));
				int g1 = Math.min(255, Math.max(0, Math.round(sumaG)));
				int b1 = Math.min(255, Math.max(0, Math.round(sumaB)));

				int pixel2 = (r1 << 16) | (g1 << 8) | b1;

				bi2.setRGB(x, y, pixel2);
			}
		}

		return bi2;
	}

	static BufferedImage funcionConvOpt(BufferedImage bi1) {

		int ancho = bi1.getWidth();
		int alto = bi1.getHeight();

		float[] matriz =deteccionBorde1 ;

		int filas = (int) Math.sqrt(matriz.length);
		int cols = filas;

		Kernel kernel = new Kernel(filas, cols, matriz);

		ConvolveOp co = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);

		BufferedImage bi2 = co.filter(bi1, null);
		
		//pintar los bordes
		/*
		    // Definir el grosor del borde
    int borde = 1;

    // Pintar los bordes de negro
    for (int x = 0; x < ancho; x++) {
        for (int y = 0; y < alto; y++) {

            if (x < borde || x >= ancho - borde ||
                y < borde || y >= alto - borde) {

                bi2.setRGB(x, y, Color.BLACK.getRGB());

            }
        }
    }
		 */

		return bi2;
	}


}
