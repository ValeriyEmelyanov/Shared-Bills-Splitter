import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class UserProfile implements Serializable {
    private static final long serialVersionUID = 26292552485L;

    private String login;
    private String email;
    private transient String password;

    public UserProfile(String login, String email, String password) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    private void readObject(ObjectInputStream ois) throws Exception {
        ois.defaultReadObject();
        password = decrypt((String) ois.readObject());
    }

    private void writeObject(ObjectOutputStream oos) throws Exception {
        oos.defaultWriteObject();
        oos.writeObject(encrypt(password));
    }

    String decrypt(String input) {
        if (input == null) {
            return null;
        }

        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = --chars[i];
        }
        return String.valueOf(chars);
    }

    String encrypt(String input) {
        if (input == null) {
            return null;
        }

        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = ++chars[i];
        }

        return String.valueOf(chars);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
