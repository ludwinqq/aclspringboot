package bo.academia.carvic.domain.common.helpers;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public class TypographyHelper {

    // Equivalente a url_title() de CodeIgniter
    public static String toSlug(String input) {
        if (input == null || input.isBlank()) return "";
        String nonWhitespace = input.trim().replaceAll("\\s+", "-");
        String normalized = Normalizer.normalize(nonWhitespace, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("[^\\w\\-]+");
        return pattern.matcher(normalized).replaceAll("").toLowerCase(Locale.ENGLISH);
    }

    public static String capitalizeWords(String input) {
        if (input == null || input.isBlank()) return "";
        StringBuilder result = new StringBuilder();
        String[] words = input.toLowerCase().split(" ");
        for (String word : words) {
            if (!word.isBlank()) {
                result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
            }
        }
        return result.toString().trim();
    }
}
