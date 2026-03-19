public class Alvaro {
    public static void main(String[] args) {
        imprimirI();
        imprimirNose(12, 2);
        imprimirNose(15, 5);
        imprimirPero();
    }

    static void imprimirI() {
        System.out.println("=== i() ===");

        int ancho = 25;
        int alto = 15;
        char[][] lienzo = crearLienzo(alto, ancho);

        // Coordenadas lógicas
        double xMin = 0;
        double xMax = 10;
        double yMin = 0;
        double yMax = 10;

        // Fórmulas originales adaptadas
        double xy1 = 1;
        double x2 = 9;
        double palo = (x2 - xy1) / (16.0 / 9.0); // 4.5 aprox
        double yalto = 2;

        // Línea superior horizontal
        dibujarLinea(lienzo, xMin, xMax, yMin, yMax,
                xy1, xy1, x2, xy1);

        // Palo vertical
        dibujarLinea(lienzo, xMin, xMax, yMin, yMax,
                palo, xy1, x2 / yalto, palo);

        // Línea inferior horizontal
        dibujarLinea(lienzo, xMin, xMax, yMin, yMax,
                xy1, palo, x2, palo);

        imprimirLienzo(lienzo);
    }

    static void imprimirNose(double xCentro, double yCentro) {
        System.out.println("=== nose(" + xCentro + ", " + yCentro + ") ===");

        int ancho = 41;
        int alto = 21;
        char[][] lienzo = crearLienzo(alto, ancho);

        // La fórmula se evalúa sobre un plano alrededor del punto dado
        // Se imprime el contorno aproximado donde formula ~= 0
        double rangoX = 6.0;
        double rangoY = 6.0;

        double xMin = xCentro - rangoX;
        double xMax = xCentro + rangoX;
        double yMin = yCentro - rangoY;
        double yMax = yCentro + rangoY;

        double dx = 12;
        double dy = 2;
        double s = 3;

        for (int fila = 0; fila < alto; fila++) {
            for (int col = 0; col < ancho; col++) {
                double xReal = map(col, 0, ancho - 1, xMin, xMax);
                double yReal = map(alto - 1 - fila, 0, alto - 1, yMin, yMax);

                double xn = (xReal - dx) / s;
                double yn = (yReal - dy) / s;

                double formula = Math.pow(xn * xn + yn * yn - 1, 3)
                        - (xn * xn * yn * yn * yn);

                // Umbral para dibujar el contorno
                if (Math.abs(formula) < 0.08) {
                    lienzo[fila][col] = '*';
                }
            }
        }

        imprimirLienzo(lienzo);
    }

    static void imprimirPero() {
        System.out.println("=== pero() ===");

        int ancho = 41;
        int alto = 16;
        char[][] lienzo = crearLienzo(alto, ancho);

        double a = 1;
        double h = 20;
        double k = 1;
        double ns = 17;
        double nsm = 23;
        double l = 5;

        double xMin = 16;
        double xMax = 24;
        double yMin = 0;
        double yMax = 6;

        int cantidadPuntos = 400;
        double paso = (nsm - ns) / (cantidadPuntos - 1);

        for (int i = 0; i < cantidadPuntos; i++) {
            double x = ns + i * paso;
            double y = a * Math.pow(x - h, 2) + k;

            if (y <= l) {
                ponerPunto(lienzo, xMin, xMax, yMin, yMax, x, y, '@');
            }
        }

        imprimirLienzo(lienzo);
    }

    // =========================
    // Utilidades de dibujo
    // =========================

    static char[][] crearLienzo(int alto, int ancho) {
        char[][] lienzo = new char[alto][ancho];
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                lienzo[i][j] = ' ';
            }
        }
        return lienzo;
    }

    static void imprimirLienzo(char[][] lienzo) {
        for (char[] fila : lienzo) {
            System.out.println(new String(fila));
        }
    }

    static void ponerPunto(char[][] lienzo,
                           double xMin, double xMax,
                           double yMin, double yMax,
                           double x, double y,
                           char c) {
        int alto = lienzo.length;
        int ancho = lienzo[0].length;

        int col = (int) Math.round(map(x, xMin, xMax, 0, ancho - 1));
        int fila = (int) Math.round(map(y, yMin, yMax, alto - 1, 0));

        if (fila >= 0 && fila < alto && col >= 0 && col < ancho) {
            lienzo[fila][col] = c;
        }
    }

    static void dibujarLinea(char[][] lienzo,
                             double xMin, double xMax,
                             double yMin, double yMax,
                             double x1, double y1,
                             double x2, double y2) {
        int pasos = 300;
        for (int i = 0; i <= pasos; i++) {
            double t = (double) i / pasos;
            double x = x1 + (x2 - x1) * t;
            double y = y1 + (y2 - y1) * t;
            ponerPunto(lienzo, xMin, xMax, yMin, yMax, x, y, '#');
        }
    }

    static double map(double valor,
                      double inMin, double inMax,
                      double outMin, double outMax) {
        return outMin + (valor - inMin) * (outMax - outMin) / (inMax - inMin);
    }
}
