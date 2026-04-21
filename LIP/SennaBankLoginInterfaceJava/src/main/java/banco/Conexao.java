package banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    public static String host = "localhost";
    public static int porta = 3306;
    public static String banco = "senna_bank";

    private static final String URL = "jdbc:mysql://"+host+":"+porta+"/"+banco+"?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String SENHA = "9532";

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco: " + e.getMessage());
            return null;
        }
    }
}
