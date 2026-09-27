package cr.ac.ucr.paraiso.ie.c5l811.expresofast;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GenerarPassword {

    public static void main(String[] args) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String password = "123456";

        String hash = encoder.encode(password);

        System.out.println("Contraseña: " + password);
        System.out.println("Hash: " + hash);
    }
}