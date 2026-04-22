package banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
//Código feito por Matheus Raiano
public class Verificacoes {
    public static boolean verificarLogin(String cpf, String senha) {
        // Query SQL com parâmetros (evita SQL Injection)
        String sql = "SELECT * FROM usuario WHERE cd_cpf = ? AND senha = ?";
        try (
                // Abre conexão com o banco usando sua classe
                Connection conn = Conexao.conectar();
                // Prepara a query para execução segura
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            // Substitui os "?" da query pelos valores reais
            stmt.setString(1, cpf); // primeiro "?" recebe cpf
            stmt.setString(2, senha); // segundo "?" recebe senha
            // Executa a consulta no banco
            ResultSet rs = stmt.executeQuery();
            // rs.next() retorna true se encontrou algum resultado
            // ou seja, login válido
            return rs.next();

        } catch (Exception e) {
            // Mostra erro no console para debug
            e.printStackTrace();
            // Retorna falso em caso de erro
            return false;
        }
    }
    public static boolean cadastrarUsuario(String nome, String cpf, String email,
                                           String celular, String nascimentoFormatado, String senha) {

        String sql = "INSERT INTO usuario (nm_usuario, cd_cpf, cd_celular, ds_email, senha, dt_nascimento) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, cpf);
            stmt.setString(3, celular);
            stmt.setString(4, email);
            stmt.setString(5, senha);
            stmt.setDate(6, java.sql.Date.valueOf(nascimentoFormatado));

            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean nomeValido(String nome) {
        return nome.matches("^[A-Za-zÀ-ÿ ]+$");
    }
    public static boolean cpfValido(String cpf) {
        return cpf.matches("\\d{11}");
    }
    public static boolean emailValido(String email) {
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }
    public static boolean celularValido(String celular) {
        return celular.matches("\\d{10,11}");
    }
    public static boolean dataValida(String data) {
        try {
            LocalDate.parse(data);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public static boolean senhaValida(String senha) {
        return senha.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,}$");
    }
    public static boolean cpfExiste(String cpf) {
        String sql = "SELECT 1 FROM usuario WHERE cd_cpf = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static String formatarDataParaMySQL(String dataBR) {
        try {
            DateTimeFormatter entrada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter saida = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            LocalDate data = LocalDate.parse(dataBR, entrada);
            return data.format(saida);

        } catch (Exception e) {
            return null;
        }
    }
    public static String limparNumero(String numero) {
        return numero.replaceAll("[^0-9]", "");
    }
    public static String limparCpf(String cpf) {
        return cpf.replaceAll("[^0-9]", "");
    }
}