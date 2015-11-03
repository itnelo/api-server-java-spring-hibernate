package api.services.impl;

import org.springframework.stereotype.Service;
import java.text.MessageFormat;
import java.util.*;
import api.services.Translator;

@Service("apiTranslator")
public class TranslatorImpl
    implements Translator<String, String>
{
    private static Map<CATEGORY, ResourceBundle> resourceBundles;
    private static Locale currentLocale;

    public TranslatorImpl() {
        resourceBundles = new HashMap<CATEGORY, ResourceBundle>();
        String language = System.getProperty("user.language");
        currentLocale = new Locale((language != null) ? language : "en");
    }

    public String tr(CATEGORY category, final String str) {
        ResourceBundle bundle = resourceBundles.get(category);
        if (bundle != null) {
            return bundle.getString(str);
        }
        bundle = setupResourceBundle(category);
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
