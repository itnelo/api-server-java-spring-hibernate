package api.util;

import org.springframework.stereotype.Component;
import java.text.MessageFormat;
import java.util.*;

@Component
public class Translator
{
    private static Map<CATEGORY, ResourceBundle> resourceBundles;
    private static Locale currentLocale;

    public enum CATEGORY {
        ERRORS("errors");

        private final String name;

        CATEGORY(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }

    public Translator() {
        resourceBundles = new HashMap<CATEGORY, ResourceBundle>();
        String language = System.getProperty("user.language");
        currentLocale = new Locale((language != null) ? language : "en");
    }

    public String tr(CATEGORY category, final String str) {
        if (resourceBundles.containsKey(category)) {
            return resourceBundles.get(category).getString(str);
        }
        ResourceBundle bundle = setupResourceBundle(category);
        resourceBundles.put(category, bundle);
        return bundle.getString(str);
    }

    public String tr(CATEGORY category, final String str, Object... args) {
        return new MessageFormat(tr(category, str), currentLocale).format(args);
    }

    private ResourceBundle setupResourceBundle(CATEGORY category) {
        String baseName = new Formatter().format("lang.%s.%s", category, category).toString();
        return ResourceBundle.getBundle(baseName, currentLocale);
    }
}
