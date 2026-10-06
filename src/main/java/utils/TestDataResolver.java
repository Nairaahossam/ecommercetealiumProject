package utils;

public class TestDataResolver {

    private static final String TIMESTAMP = "{timestamp}";
    private static final String REGISTERED_EMAIL = "{registered_email}";
    private static final String REGISTERED_PASSWORD = "{registered_password}";

    public static String resolve(String value) {

        String resolved = value.replace(
                TIMESTAMP,
                String.valueOf(System.currentTimeMillis())
        );

        if (resolved.contains(REGISTERED_EMAIL)
                || resolved.contains(REGISTERED_PASSWORD)) {

            if (TestAccount.getEmail() == null) {
                throw new IllegalStateException(
                        "Test account was not created before the suite"
                );
            }

            resolved = resolved
                    .replace(REGISTERED_EMAIL, TestAccount.getEmail())
                    .replace(REGISTERED_PASSWORD, TestAccount.getPassword());
        }

        return resolved;
    }
}
