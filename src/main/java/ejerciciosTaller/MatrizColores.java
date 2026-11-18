
package ejerciciosTaller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class MatrizColores {

    public static void main(String[] args) {

        new File("imagenesColores").mkdirs();

        File original = new File("imagenes/paisaje.jpg");

        // Cambiar el nombre según el filtro seleccionado
        File destino = new File(
            "imagenesColores/paisajeTransformado.png"
        );

        try {

            BufferedImage imagen = ImageIO.read(original);

            if (imagen == null) {
                throw new Exception("No se pudo cargar la imagen");
            }

            int ancho = imagen.getWidth();
            int alto = imagen.getHeight();

            // Se utiliza ARGB para conservar transparencia
            BufferedImage imagen2 = new BufferedImage(
                ancho,
                alto,
                BufferedImage.TYPE_INT_ARGB
            );


            float[][] matriz = Matrices.matrizGrisesRGB;


            for (int x = 0; x < ancho; x++) {

                for (int y = 0; y < alto; y++) {

                    int pixel = imagen.getRGB(x, y);

                    Color color = new Color(pixel, true);

                    int r = color.getRed();
                    int g = color.getGreen();
                    int b = color.getBlue();
                    int a = color.getAlpha();

                    
                    // APLICACIÓN DE LA MATRIZ

                    int r1 = clamping(Math.round(r * matriz[0][0] + g * matriz[0][1] + b * matriz[0][2] + (matriz[0].length == 5
                            ? a * matriz[0][3] + matriz[0][4] : matriz[0][3])));

                    int g1 = clamping(Math.round(r * matriz[1][0] + g * matriz[1][1] + b * matriz[1][2] +
                        (matriz[1].length == 5 ? a * matriz[1][3] + matriz[1][4] : matriz[1][3])));

                    int b1 = clamping(Math.round(r * matriz[2][0] + g * matriz[2][1] + b * matriz[2][2] + 
                    		(matriz[2].length == 5 ? a * matriz[2][3] + matriz[2][4] : matriz[2][3])));


                    // CÁLCULO DEL ALPHA

                    int a1;

                    if (matriz.length == 4) {

                        a1 = clamping(Math.round(r * matriz[3][0] + g * matriz[3][1] + b * matriz[3][2] + a * matriz[3][3] + matriz[3][4]));

                    } else {

                        // Las matrices RGB conservan
                        // la transparencia original
                        a1 = a;
                    }

                    int pixel2 = (a1 << 24) | (r1 << 16) | (g1 << 8) | b1;

                    imagen2.setRGB(x, y, pixel2);

                }
            }

            ImageIO.write(imagen2, "png", destino);

            System.out.println(
                "Imagen generada exitosamente!!"
            );

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    private static int clamping(int val) {

        return Math.max(0, Math.min(255, val));

    }

}
