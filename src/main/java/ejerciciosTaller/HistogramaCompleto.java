
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

public class HistogramaCompleto {

    private final static int mascara = 0xFF;

    public static void main(String[] args) {

        new File("Histogramas").mkdirs();

        File original = new File("imagenes/auto.jpg");

        File destinoPicos = new File("Histogramas/histogramaRGB_picos.png");
        File destinoBarras = new File("Histogramas/histogramaRGB_barras.png");

        try {

            BufferedImage imagen = ImageIO.read(original);

            int ancho = imagen.getWidth();
            int alto = imagen.getHeight();

            BufferedImage imagenHisto = new BufferedImage(
                    ancho, alto, BufferedImage.TYPE_INT_RGB);

            BufferedImage imagenBarras = new BufferedImage(
                    ancho, alto, BufferedImage.TYPE_INT_RGB);

            // Arreglos de los histogramas
            int[] histoRed = new int[256];
            int[] histoGreen = new int[256];
            int[] histoBlue = new int[256];

            // Recorremos primero filas y luego columnas
            for (int y = 0; y < alto; y++) {
                for (int x = 0; x < ancho; x++) {

                    int pixel = imagen.getRGB(x, y);

                    // Extraemos los canales RGB
                    int r = (pixel >> 16) & mascara;
                    int g = (pixel >> 8) & mascara;
                    int b = pixel & mascara;

                    // Guardamos las intensidades
                    histoRed[r]++;
                    histoGreen[g]++;
                    histoBlue[b]++;
                }
            }

            // Obtenemos las intensidades máximas
            int maxRed = Arrays.stream(histoRed).max().getAsInt();
            int maxGreen = Arrays.stream(histoGreen).max().getAsInt();
            int maxBlue = Arrays.stream(histoBlue).max().getAsInt();

            int maxInten = Math.max(maxRed,
                    Math.max(maxGreen, maxBlue));

            System.out.println("Maximo Red: " + maxRed);
            System.out.println("Maximo Green: " + maxGreen);
            System.out.println("Maximo Blue: " + maxBlue);

            System.out.println("Maximo general: " + maxInten);

            // Mostramos los valores de los tres histogramas
            IntStream.range(0, 256).forEachOrdered(x ->
                    System.out.println(
                            "Intensidad[" + x + "] Red: " + histoRed[x]
                            + " Green: " + histoGreen[x]
                            + " Blue: " + histoBlue[x]
                    )
            );

            // =====================================
            // HISTOGRAMA DE PICOS (LINEAS)
            // =====================================

            Graphics2D gr = imagenHisto.createGraphics();

            gr.setColor(Color.WHITE);
            gr.fillRect(0, 0, ancho, alto);

            Stroke st = new BasicStroke(2);

            gr.setStroke(st);

            gr.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            // Escalas
            float escalaX = ancho / 256.0f;
            float escalaY = (float) alto / maxInten;

            // Dibujamos el canal RED
            gr.setColor(Color.RED);

            for (int i = 1; i < 256; i++) {

                int x1 = (int) ((i - 1) * escalaX);
                int y1 = (int) (alto - histoRed[i - 1] * escalaY);

                int x2 = (int) (i * escalaX);
                int y2 = (int) (alto - histoRed[i] * escalaY);

                gr.drawLine(x1, y1, x2, y2);
            }

            // Dibujamos el canal GREEN
            gr.setColor(Color.GREEN);

            for (int i = 1; i < 256; i++) {

                int x1 = (int) ((i - 1) * escalaX);
                int y1 = (int) (alto - histoGreen[i - 1] * escalaY);

                int x2 = (int) (i * escalaX);
                int y2 = (int) (alto - histoGreen[i] * escalaY);

                gr.drawLine(x1, y1, x2, y2);
            }

            // Dibujamos el canal BLUE
            gr.setColor(Color.BLUE);

            for (int i = 1; i < 256; i++) {

                int x1 = (int) ((i - 1) * escalaX);
                int y1 = (int) (alto - histoBlue[i - 1] * escalaY);

                int x2 = (int) (i * escalaX);
                int y2 = (int) (alto - histoBlue[i] * escalaY);

                gr.drawLine(x1, y1, x2, y2);
            }

            gr.dispose();

            ImageIO.write(imagenHisto, "png", destinoPicos);

            // =====================================
            // HISTOGRAMA DE BARRAS
            // =====================================

            Graphics2D gb = imagenBarras.createGraphics();

            gb.setColor(Color.WHITE);
            gb.fillRect(0, 0, ancho, alto);

            gb.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Dibujamos las barras RGB
            for (int i = 0; i < 256; i++) {

                int x = (int) (i * escalaX);

                int alturaRed = (int) (histoRed[i] * escalaY);
                int alturaGreen = (int) (histoGreen[i] * escalaY);
                int alturaBlue = (int) (histoBlue[i] * escalaY);

                int anchoBarra = Math.max(1, (int) Math.ceil(escalaX));

                // Canal RED
                gb.setColor(new Color(255, 0, 0, 120));
                gb.fillRect(x, alto - alturaRed,
                        anchoBarra, alturaRed);

                // Canal GREEN
                gb.setColor(new Color(0, 255, 0, 120));
                gb.fillRect(x, alto - alturaGreen,
                        anchoBarra, alturaGreen);

                // Canal BLUE
                gb.setColor(new Color(0, 0, 255, 120));
                gb.fillRect(x, alto - alturaBlue,
                        anchoBarra, alturaBlue);
            }

            gb.dispose();

            ImageIO.write(imagenBarras, "png", destinoBarras);

            System.out.println("Histogramas RGB generados exitosamente!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
