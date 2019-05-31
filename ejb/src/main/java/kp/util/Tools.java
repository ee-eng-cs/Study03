package kp.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

/**
 * The tools.
 *
 */
public interface Tools {
	/**
	 * Formats the hash code.
	 * 
	 * @param object the object
	 * @return the formated hash code
	 */
	static String hashCodeFormated(Object object) {

		String msg = String.format("%08X", object.hashCode());
		if (msg.length() == 8) {
			msg = String.format("%s %s %s %s", msg.substring(0, 2), msg.substring(2, 4), msg.substring(4, 6),
					msg.substring(6, 8));
		}
		return msg;
	}

	/**
	 * Formats the number.
	 * 
	 * @param number the number
	 * @return the formated text
	 */
	static String formatNumber(long number) {

		final NumberFormat numberFormat = NumberFormat.getInstance();
		if (!(numberFormat instanceof DecimalFormat)) {
			// failed
			return "?";
		}
		final DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
		final DecimalFormatSymbols dfs = decimalFormat.getDecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		dfs.setGroupingSeparator('\'');
		decimalFormat.setDecimalFormatSymbols(dfs);
		return decimalFormat.format(number);
	}
}