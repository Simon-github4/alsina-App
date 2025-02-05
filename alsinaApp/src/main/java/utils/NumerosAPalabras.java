package utils;

public class NumerosAPalabras {

    private static final String[] UNIDADES = {
        "", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve"
    };

    private static final String[] DECENAS = {
        "", "diez", "veinte", "treinta", "cuarenta", "cincuenta", "sesenta", "setenta", "ochenta", "noventa"
    };

    private static final String[] ESPECIALES = {
        "once", "doce", "trece", "catorce", "quince", "dieciséis", "diecisiete", "dieciocho", "diecinueve"
    };

    private static final String[] CENTENAS = {
        "", "ciento", "doscientos", "trescientos", "cuatrocientos", "quinientos", 
        "seiscientos", "setecientos", "ochocientos", "novecientos"
    };

    public static String convertirNumeroAPalabras(int numero) {
        if (numero == 0) {
            return "cero";
        }
        if (numero < 0) {
            return "menos " + convertirNumeroAPalabras(Math.abs(numero));
        }
        if (numero < 10) {
            return UNIDADES[numero];
        }
        if (numero < 20) {
            return ESPECIALES[numero - 11];
        }
        if (numero < 100) {
            return DECENAS[numero / 10] + (numero % 10 != 0 ? " y " + UNIDADES[numero % 10] : "");
        }
        if (numero < 1000) {
            return CENTENAS[numero / 100] + (numero % 100 != 0 ? " " + convertirNumeroAPalabras(numero % 100) : "");
        }
        if (numero < 1_000_000) {
            return convertirNumeroAPalabras(numero / 1000) + " mil" + (numero % 1000 != 0 ? " " + convertirNumeroAPalabras(numero % 1000) : "");
        }
        if (numero < 1_000_000_000) {
            return convertirNumeroAPalabras(numero / 1_000_000) + " millón" + (numero % 1_000_000 != 0 ? " " + convertirNumeroAPalabras(numero % 1_000_000) : "");
        }
        return "Número demasiado grande";
    }

    public static String convertirPesosAPalabras(double cantidad) {
        int parteEntera = (int) cantidad;
        int parteDecimal = (int) ((cantidad - parteEntera) * 100);

        String palabras = convertirNumeroAPalabras(parteEntera) + " pesos";
        if (parteDecimal > 0) {
            palabras += " con " + convertirNumeroAPalabras(parteDecimal) + " centavos";
        }
        return palabras;
    }

    public static void main(String[] args) {
        long cantidad = 10500000;
        String palabras = convertirPesosAPalabras(cantidad);
        System.out.println("$" + cantidad + " en palabras es: " + palabras);
    }
}
