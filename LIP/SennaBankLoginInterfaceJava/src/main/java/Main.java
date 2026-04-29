import banco.Conexao;
import telas.TelaLogin;

import javax.swing.*;
import java.sql.Connection;
//Código feito por Matheus Raiano
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaLogin.login();
        });

        Connection conn = Conexao.conectar();

        if(conn != null) {
            System.out.println("Conexão ao banco de dados efetuada com sucesso!");
        }
    }
}
