package utilidades;

import java.util.Objects;

public class Rut {
    private String rut;

    public Rut(String rut) {
        if (!validar(rut)) {
            throw new IllegalArgumentException("RUT no válido: " + rut);
        }
        this.rut = formatear(rut);
    }

    public static boolean validar(String rut) {
        if (rut == null) {
            return false;
        }
        try {
            String rutLimpio = rut.replace(".", "").replace("-", "").trim().toUpperCase();
            if (rutLimpio.length() < 2) {
                return false;
            }
            char dv = rutLimpio.charAt(rutLimpio.length() - 1);
            String rutNumerico = rutLimpio.substring(0, rutLimpio.length() - 1);

            int rutNum = Integer.parseInt(rutNumerico);
            int m = 0, s = 1;
            for (; rutNum != 0; rutNum /= 10) {
                s = (s + rutNum % 10 * (9 - m++ % 6)) % 11;
            }
            char dvCalculado = (char) (s != 0 ? s + 47 : 75); // 75 = 'K'

            return dv == dvCalculado;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Método para estandarizar el formato
    private String formatear(String rut) {
        String rutLimpio = rut.replace(".", "").replace("-", "").trim();
        if (rutLimpio.isEmpty()) return "";

        String dv = rutLimpio.substring(rutLimpio.length() - 1);
        String num = rutLimpio.substring(0, rutLimpio.length() - 1);

        // Formato con puntos y guion
        StringBuilder sb = new StringBuilder(num);
        int i = sb.length() - 3;
        while (i > 0) {
            sb.insert(i, ".");
            i -= 3;
        }
        sb.append("-").append(dv);
        return sb.toString();
    }

    @Override
    public String toString() {
        return rut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rut rut1 = (Rut) o;
        // Comparamos el formato estandarizado
        return Objects.equals(this.rut, rut1.rut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rut);
    }
}
