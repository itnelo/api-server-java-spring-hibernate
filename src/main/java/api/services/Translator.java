package api.services;

public interface Translator<I,O> {

    enum CATEGORY {
        ERRORS("errors");

        private final String name;

        CATEGORY(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }

    O tr(final CATEGORY bundle, final I inputValue);
    O tr(final CATEGORY bundle, final I inputValue, Object... args);

}
