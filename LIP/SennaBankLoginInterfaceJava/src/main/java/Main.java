import banco.Conexao;
import telas.TelaLogin;

import javax.swing.*;
import java.sql.Connection;

public class Main {

    public static void main(String[] args) {
        Connection conn = Conexao.conectar();

        if(conn != null) {
            System.out.println("Conexão ao banco de dados efetuada com sucesso!");
        }

        SwingUtilities.invokeLater(() -> {
            new TelaLogin().setVisible(true);
        });
    }
}