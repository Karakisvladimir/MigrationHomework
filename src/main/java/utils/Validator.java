package utils;

public class Validator {
    public static void validateName(String name) {
        if (name == null || name.isEmpty() || name.length() < 2 || name.length() > 1000) {
            throw new IllegalArgumentException("Invalid client name");
        }
    }

    private Validator() {
        throw new IllegalStateException("Validator class");
    }
}

