package telas;

import banco.Conexao;
import util.MascaraUtil;
import util.SenhaUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;

public class TelaLogin extends JFrame {

    private static final Color PRETO = new Color(0, 0, 0);
    private static final Color FUNDO_CARD = new Color(17, 17, 17);
    private static final Color FUNDO_CAMPO = new Color(26, 26, 26);
    private static final Color BORDA_CAMPO = new Color(42, 42, 42);
    private static final Color VERDE = new Color(62, 209, 62);
    private static final Color CINZA_TEXTO = new Color(136, 136, 136);
    private static final Color CINZA_LABEL = new Color(170, 170, 170);
    private static final Color VERMELHO = new Color(224, 82, 82);
    private static final Color BORDA_CINZA = new Color(68, 68, 68);

    private JTextField campoCpf;
    private JPasswordField campoSenha;
    private JLabel lblErro;

    public TelaLogin() {
        configurarJanela();
        construirTela();
    }

    private void configurarJanela() {
        setTitle("SennaBank — Login");
        setSize(440, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(PRETO);
    }

    private void construirTela() {
        // Painel externo centraliza o card
        JPanel painelExterno = new JPanel(new GridBagLayout());
        painelExterno.setBackground(PRETO);

        // Card central
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(FUNDO_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDA_CINZA, 1),
                new EmptyBorder(40, 45, 40, 45)
        ));
        card.setMaximumSize(new Dimension(360, Integer.MAX_VALUE));

        // ── Logo ──
        JPanel painelLogo = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));
        painelLogo.setBackground(FUNDO_CARD);
        painelLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Três traços coloridos (bandeira)
        painelLogo.add(criarTraco(new Color(0, 128, 0)));
        painelLogo.add(criarTraco(new Color(255, 215, 0)));
        painelLogo.add(criarTraco(new Color(0, 0, 200)));

        JLabel lblLogo = new JLabel("  SENNA BANK");
        lblLogo.setFont(new Font("Arial", Font.BOLD, 18));
        lblLogo.setForeground(Color.WHITE);
        painelLogo.add(lblLogo);

        // ── Título ──
        JLabel titulo = criarLabelCentro("Entrar na conta", 15, Font.BOLD, Color.WHITE);
        JLabel sub = criarLabelCentro("Bem-Vindo de volta!", 12, Font.PLAIN, CINZA_TEXTO);

        // ── Campos ──
        campoCpf = criarCampoTexto();
        MascaraUtil.aplicarMascaraCpf(campoCpf);

        campoSenha = new JPasswordField();
        estilizarCampo(campoSenha);

        // Painel da senha com botão olho
        JPanel painelSenha = new JPanel(new BorderLayout());
        painelSenha.setBackground(FUNDO_CAMPO);
        painelSenha.setBorder(BorderFactory.createLineBorder(BORDA_CAMPO));
        painelSenha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        painelSenha.setAlignmentX(Component.LEFT_ALIGNMENT);
        campoSenha.setBorder(new EmptyBorder(8, 12, 8, 8));
        campoSenha.setBackground(FUNDO_CAMPO);
        campoSenha.setForeground(Color.WHITE);
        campoSenha.setCaretColor(Color.WHITE);
        JButton btnOlho = criarBotaoOlho(campoSenha);
        painelSenha.add(campoSenha, BorderLayout.CENTER);
        painelSenha.add(btnOlho, BorderLayout.EAST);

        // ── Erro ──
        lblErro = new JLabel(" ");
        lblErro.setForeground(VERMELHO);
        lblErro.setFont(new Font("Arial", Font.PLAIN, 11));
        lblErro.setAlignmentX(Component.LEFT_ALIGNMENT);

        // ── Botões ──
        JButton btnEntrar = criarBotaoPrimario("Entrar");
        JButton btnCadastro = criarBotaoSecundario("Não tem conta? Criar conta grátis");

        btnEntrar.addActionListener(e -> realizarLogin());
        btnCadastro.addActionListener(e -> {
            new TelaCadastro().setVisible(true);
            dispose();
        });

        // Permitir Enter para login
        getRootPane().setDefaultButton(btnEntrar);

        // Link esqueci senha
        JButton btnEsqueci = new JButton("Esqueci minha senha");
        btnEsqueci.setBackground(null);
        btnEsqueci.setForeground(CINZA_TEXTO);
        btnEsqueci.setFont(new Font("Arial", Font.PLAIN, 11));
        btnEsqueci.setBorderPainted(false);
        btnEsqueci.setContentAreaFilled(false);
        btnEsqueci.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnEsqueci.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEsqueci.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Funcionalidade disponível em breve.", "Aviso", JOptionPane.INFORMATION_MESSAGE)
        );

        // Linha divisória
        JSeparator sep = new JSeparator();
        sep.setForeground(BORDA_CINZA);
        sep.setBackground(BORDA_CINZA);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        // Montagem do card
        card.add(painelLogo);
        card.add(Box.createVerticalStrut(20));
        card.add(titulo);
        card.add(Box.createVerticalStrut(4));
        card.add(sub);
        card.add(Box.createVerticalStrut(28));
        card.add(criarLabel("CPF"));
        card.add(Box.createVerticalStrut(5));
        card.add(campoCpf);
        card.add(Box.createVerticalStrut(14));
        card.add(criarLabel("Senha"));
        card.add(Box.createVerticalStrut(5));
        card.add(painelSenha);
        card.add(Box.createVerticalStrut(4));
        card.add(lblErro);
        card.add(Box.createVerticalStrut(18));
        card.add(btnEntrar);
        card.add(Box.createVerticalStrut(6));
        card.add(btnEsqueci);
        card.add(Box.createVerticalStrut(16));
        card.add(sep);
        card.add(Box.createVerticalStrut(16));
        card.add(btnCadastro);

        painelExterno.add(card);
        add(painelExterno);
    }

    private void realizarLogin() {
        String cpf = MascaraUtil.apenasNumeros(campoCpf.getText());
        String senha = new String(campoSenha.getPassword());
        lblErro.setText(" ");

        // Validações locais
        if (cpf.length() != 11) {
            lblErro.setText("CPF inválido.");
            campoCpf.requestFocus();
            return;
        }
        if (senha.isEmpty()) {
            lblErro.setText("Digite sua senha.");
            campoSenha.requestFocus();
            return;
        }

        Connection con = Conexao.conectar();
        if (con == null) {
            lblErro.setText("Sem conexão com o banco de dados.");
            return;
        }

        try {
            // Busca o hash da senha pelo CPF
            String sql = "SELECT cd_usuario, nm_usuario, senha FROM usuario WHERE cd_cpf = ? AND ds_status = 'ativo'";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String hashDoBanco = rs.getString("senha");
                // BCrypt compara a senha digitada com o hash
                if (SenhaUtil.verificar(senha, hashDoBanco)) {
                    String nome = rs.getString("nm_usuario");
                    JOptionPane.showMessageDialog(this,
                            "Bem-vindo, " + nome + "!",
                            "Login realizado com sucesso",
                            JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    lblErro.setText("CPF ou senha incorretos.");
                    campoSenha.setText("");
                    campoSenha.requestFocus();
                }
            } else {
                // Mesma mensagem para CPF não encontrado e senha errada
                lblErro.setText("CPF ou senha incorretos.");
                campoSenha.setText("");
                campoSenha.requestFocus();
            }

            rs.close();
            ps.close();
            con.close();

        } catch (SQLException e) {
            lblErro.setText("Erro ao conectar. Tente novamente.");
            System.err.println(e.getMessage());
        }
    }

    // Helpers de estilo

    private JPanel criarTraco(Color cor) {
        JPanel traco = new JPanel();
        traco.setBackground(cor);
        traco.setPreferredSize(new Dimension(4, 18));
        traco.setMaximumSize(new Dimension(4, 18));
        return traco;
    }

    private JLabel criarLabelCentro(String texto, int size, int style, Color cor) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", style, size));
        label.setForeground(cor);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        label.setForeground(CINZA_LABEL);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JTextField criarCampoTexto() {
        JTextField campo = new JTextField();
        estilizarCampo(campo);
        return campo;
    }

    private void estilizarCampo(JTextField campo) {
        campo.setBackground(FUNDO_CAMPO);
        campo.setForeground(Color.WHITE);
        campo.setCaretColor(Color.WHITE);
        campo.setFont(new Font("Arial", Font.PLAIN, 13));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDA_CAMPO),
                new EmptyBorder(8, 12, 8, 12)
        ));
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private JButton criarBotaoOlho(JPasswordField campo) {
        JButton btn = new JButton("👁");
        btn.setBackground(FUNDO_CAMPO);
        btn.setForeground(CINZA_TEXTO);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(40, 40));
        btn.addActionListener(e -> {
            if (campo.getEchoChar() == 0) {
                campo.setEchoChar('•');
                btn.setText("👁");
            } else {
                campo.setEchoChar((char) 0);
                btn.setText("🙈");
            }
        });
        return btn;
    }

    private JButton criarBotaoPrimario(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(VERDE);
        btn.setForeground(Color.BLACK);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        return btn;
    }

    private JButton criarBotaoSecundario(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(PRETO);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.PLAIN, 13));
        btn.setBorder(BorderFactory.createLineBorder(BORDA_CINZA));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        return btn;
    }
}