package com.qentelli.automation.utilities;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * UTF-8 friendly ResourceBundle support
 *
 * Utility that allows having multi-byte characters inside java .property files.
 * It removes the need for Sun's native2ascii application, you can simply have
 * UTF-8 encoded editable .property files.
 *
 * Use:
 * ResourceBundle bundle = Utf8ResourceBundle.getBundle("bundle_name");
 *
 * @author Tomas Varaneckas <tomas.varaneckas@gmail.com>
 */
public abstract class Utf8ResourceBundle {

    /**
     * Gets the unicode friendly resource bundle
     *
     * @param baseName
     * @see ResourceBundle#getBundle(String)
     * @return Unicode friendly resource bundle
     */
    public static final ResourceBundle getBundle(final String baseName) {
        return createUtf8PropertyResourceBundle(
                ResourceBundle.getBundle(baseName));
    }

    /**
     * Creates unicode friendly {@link PropertyResourceBundle} if possible.
     *
     * @param bundle
     * @return Unicode friendly property resource bundle
     */
    private static ResourceBundle createUtf8PropertyResourceBundle(
            final ResourceBundle bundle) {
        if (!(bundle instanceof PropertyResourceBundle)) {
            return bundle;
        }
        return new Utf8PropertyResourceBundle((PropertyResourceBundle) bundle);
    }

    /**
     * Resource Bundle that does the hard work
     */
    private static class Utf8PropertyResourceBundle extends ResourceBundle {

        /**
         * Bundle with unicode data
         */
        private final PropertyResourceBundle bundle;

        /**
         * Initializing constructor
         *
         * @param bundle
         */
        private Utf8PropertyResourceBundle(final PropertyResourceBundle bundle) {
            this.bundle = bundle;
        }

        @Override
        @SuppressWarnings("unchecked")
        public Enumeration getKeys() {
            return bundle.getKeys();
        }

        @Override
        protected Object handleGetObject(final String key) {
            final String value = bundle.getString(key);
            if (value == null)
                return null;
            return new String(value.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        }
    }
}