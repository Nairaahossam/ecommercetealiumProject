package utils;
public class TestAccount {

    private static String email;
    private static String password;

    public static void setCredentials(String accountEmail, String accountPassword) {
        email = accountEmail;
        password = accountPassword;
    }

    public static String getEmail() {
        return email;
    }

    public static String getPassword() {
        return password;
    }
}
