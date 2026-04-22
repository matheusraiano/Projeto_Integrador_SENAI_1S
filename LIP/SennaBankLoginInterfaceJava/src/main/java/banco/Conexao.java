package banco;

import java.sql.*;
//Código feito por Matheus Raiano
public class Conexao {

    // Dados básicos da conexão
    public static String host = "localhost"; // endereço do banco
    public static int porta = 3306; // porta padrão do MySQL
    public static String banco = "senna_bank"; // nome do banco

    // Monta a URL de conexão JDBC
    private static final String URL = "jdbc:mysql://" + host + ":" + porta + "/" + banco + "?useSSL=false&serverTimezone=UTC";

    private static final String USUARIO = "root"; // usuário do banco
    private static final String SENHA = "9532"; // senha do banco

    // Método responsável por abrir a conexão com o banco
    public static Connection conectar() {
        try {
            // Tenta conectar com os dados fornecidos
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            // Se der erro, lança exceção (melhor que retornar null)
            throw new RuntimeException("Erro ao conectar ao banco", e);
        }
    }
}
