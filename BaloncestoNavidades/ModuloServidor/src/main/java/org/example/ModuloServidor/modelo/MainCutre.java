package org.example.ModuloServidor.modelo;

public class MainCutre {

    public static void main(String[] args) {

       /* Pbkdf2PasswordHash passwordHash = new Pbkdf2PasswordHash() {
            @Override
            public String generate(char[] chars) {
                return null;
            }

            @Override
            public boolean verify(char[] chars, String s) {
                return false;
            }
        };
        Map<String, String> parameters = new HashMap<>();
        parameters.put("Pbkdf2PasswordHash.Iterations", "3072");
        parameters.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA512");
        parameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "32");
        parameters.put("Pbkdf2PasswordHash.KeySizeBytes", "32");
        passwordHash.initialize(parameters);*/


        String password = "mamabicho";
/*
        var pussy = passwordHash.generate(password.toCharArray());
*/

/*
        Usuario u = new Usuario("mamabicho@gmail.com",pussy, "crack",1, LocalDateTime.now(),2,1);
*/

        DaoUsuarios dao = new DaoUsuarios();
        dao.getCustomers().forEach(System.out::println);

/*
        var pussyOK = passwordHash.verify(password.toCharArray(), pussy);
*/

    }
}
