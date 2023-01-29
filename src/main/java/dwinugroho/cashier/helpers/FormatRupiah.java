package dwinugroho.cashier.helpers;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class FormatRupiah {
    /**
     * Format the given number to an appropriate Rupiah format.
     *
     * @param num - The number you want to format
     * @return formattedNumber
     */
    public static String format(long num) {
        DecimalFormat curs = (DecimalFormat) DecimalFormat.getInstance();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();

        symbols.setMonetaryDecimalSeparator(',');
        symbols.setGroupingSeparator('.');

        curs.setDecimalFormatSymbols(symbols);

        return "Rp. " + curs.format(num);
    }

    /**
     * Normalise the formatted number
     *
     * @param str - The string you want to normalise
     * @return normalisedString
     */
    public static Long normalise(String str) {
        return Long.parseLong(str.replaceAll("[a-zA-Z\\.\\s]", ""));
    }
}
