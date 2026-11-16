package ejerciciosTaller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class EjerciciosTransferenciasOptimizado {
	public static void main(String[] args) {

        File carpeta = new File("imagenesTransferencia");
        carpeta.mkdirs();

        File original = new File("imagenes/paisaje.jpg");
        File destino = new File("imagenesTransferencia/paisajeFinal.jpg");

        try {
            BufferedImage imagen = ImageIO.read(original);

            int ancho = imagen.getWidth();
            int alto = imagen.getHeight();

            BufferedImage resultado = new BufferedImage(
                ancho, alto, BufferedImage.TYPE_INT_RGB
            );

            int niveles = 32;

            for (int y = 0; y < alto; y++) {
                for (int x = 0; x < ancho; x++) {

                    int pixel = imagen.getRGB(x, y);

                    int r = (pixel >> 16) & 255;
                    int g = (pixel >> 8) & 255;
                    int b = pixel & 255;

                    r = cuantizar(r, niveles);
                    g = cuantizar(g, niveles);
                    b = cuantizar(b, niveles);
                    
                    //blanco y negro
                    /*int gris = (r + g + b) / 3;

                    if (gris < 128) {
                        r = 0;
                        g = 0;
                        b = 0;
                    } else {
                        r = 255;
                        g = 255;
                        b = 255;
                    }*/

                    int nuevoPixel = (r << 16)| (g << 8)| b;

                    resultado.setRGB(x, y, nuevoPixel);
                }
            }

            ImageIO.write(resultado, "jpg", destino);

            System.out.println("Imagen generada exitosamente!");

        } catch (IOException e) {
            System.out.println("Error al procesar la imagen: " + e.getMessage());
        }
    }

    static int cuantizar(int color, int niveles) {

        double escala = (niveles - 1) / 255.0;

        int nivel = (int) Math.round(color * escala);

        return (int) Math.round(
            nivel * 255.0 / (niveles - 1)
        );
    }

}
