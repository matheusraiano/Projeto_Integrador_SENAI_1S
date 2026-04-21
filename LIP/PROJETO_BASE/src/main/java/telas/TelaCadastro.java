package telas;

import banco.Conexao;
import util.MascaraUtil;
import util.SenhaUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

public class TelaCadastro extends JFrame {

    private static final Color PRETO       = new Color(0, 0, 0);
    private static final Color FUNDO_CARD  = new Color(17, 17, 17);
    private static final Color FUNDO_CAMPO = new Color(26, 26, 26);
    private static final Color BORDA_CAMPO = new Color(42, 42, 42);
    private static final Color VERDE       = new Color(62, 209, 62);
    private static final Color CINZA_TEXTO = new Color(136, 136, 136);
    private static final Color CINZA_LABEL = new Color(170, 170, 170);
    private static final Color VERMELHO    = new Color(224, 82, 82);
    private static final Color BORDA_CINZA = new Color(68, 68, 68);

    private JTextField     campoNome;
    private JTextField     campoCpf;
    private JTextField     campoEmail;
    private JTextField     campoCelular;
    private JTextField     campoNascimento;
    private JPasswordField campoSenha;
    private JPasswordField campoConfSenha;
    private JLabel         lblErro;

    // Indicadores de etapa
    private JLabel dot1, dot2, dot3;

    // Painéis das etapas
    private JPanel painelEtapa1;
    private JPanel painelEtapa2;
    private JPanel painelEtapa3;
    private JPanel painelEtapas;
    private JPanel cardCentral;

    private int etapaAtual = 1;

    public TelaCadastro() {
        configurarJanela();
        construirTela();
    }

    private void configurarJanela() {
        setTitle("SennaBank — Criar Conta");
        setSize(460, 580);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(PRETO);
    }

    private void construirTela() {
        JPanel painelExterno = new JPanel(new GridBagLayout());
        painelExterno.setBackground(PRETO);

        cardCentral = new JPanel();
        cardCentral.setLayout(new BoxLayout(cardCentral, BoxLayout.Y_AXIS));
        cardCentral.setBackground(FUNDO_CARD);
        cardCentral.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDA_CINZA, 1),
                new EmptyBorder(35, 45, 35, 45)
        ));

        // Logo
        JLabel lblLogo = criarLabelCentro("SENNA BANK", 18, Font.BOLD, Color.WHITE);
        JLabel titulo  = criarLabelCentro("Abrir sua conta", 15, Font.BOLD, Color.WHITE);
        JLabel sub     = criarLabelCentro("Rápido, gratuito e sem burocracia.", 12, Font.PLAIN, CINZA_TEXTO);

        // Indicador de etapas
        painelEtapas = criarIndicadorEtapas();

        // Etapas
        painelEtapa1 = construirEtapa1();
        painelEtapa2 = construirEtapa2();
        painelEtapa3 = construirEtapa3();

        painelEtapa2.setVisible(false);
        painelEtapa3.setVisible(false);

        // Erro
        lblErro = new JLabel(" ");
        lblErro.setForeground(VERMELHO);
        lblErro.setFont(new Font("Arial", Font.PLAIN, 11));
        lblErro.setAlignmentX(Component.CENTER_ALIGNMENT);

        cardCentral.add(lblLogo);
        cardCentral.add(Box.createVerticalStrut(4));
        cardCentral.add(titulo);
        cardCentral.add(Box.createVerticalStrut(3));
        cardCentral.add(sub);
        cardCentral.add(Box.createVerticalStrut(20));
        cardCentral.add(painelEtapas);
        cardCentral.add(Box.createVerticalStrut(22));
        cardCentral.add(painelEtapa1);
        cardCentral.add(painelEtapa2);
        cardCentral.add(painelEtapa3);
        cardCentral.add(Box.createVerticalStrut(6));
        cardCentral.add(lblErro);

        painelExterno.add(cardCentral);
        add(painelExterno);
    }

    private JPanel construirEtapa1() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(FUNDO_CARD);
        p.setAlignmentX(Component.CENTER_ALIGNMENT);

        campoNome = criarCampo();
        campoCpf  = criarCampo();
        MascaraUtil.aplicarMascaraCpf(campoCpf);

        JButton btnAvancar = criarBotaoPrimario("Continuar →");
        btnAvancar.addActionListener(e -> avancarEtapa(1));

        JButton btnVoltar = criarBotaoLink("Já tenho conta — Entrar");
        btnVoltar.addActionListener(e -> {
            new TelaLogin().setVisible(true);
            dispose();
        });

        p.add(criarLabel("Nome completo"));
        p.add(Box.createVerticalStrut(5));
        p.add(campoNome);
        p.add(Box.createVerticalStrut(14));
        p.add(criarLabel("CPF"));
        p.add(Box.createVerticalStrut(5));
        p.add(campoCpf);
        p.add(Box.createVerticalStrut(22));
        p.add(btnAvancar);
        p.add(Box.createVerticalStrut(10));
        p.add(btnVoltar);
        return p;
    }

    private JPanel construirEtapa2() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(FUNDO_CARD);
        p.setAlignmentX(Component.CENTER_ALIGNMENT);

        campoEmail      = criarCampo();
        campoCelular    = criarCampo();
        campoNascimento = criarCampo();
        MascaraUtil.aplicarMascaraCelular(campoCelular);
        MascaraUtil.aplicarMascaraData(campoNascimento);

        JPanel painelNav = new JPanel(new GridLayout(1, 2, 10, 0));
        painelNav.setBackground(FUNDO_CARD);
        painelNav.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        painelNav.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnVoltar  = criarBotaoSecundario("← Voltar");
        JButton btnAvancar = criarBotaoPrimario("Continuar →");
        btnVoltar.addActionListener(e  -> voltarEtapa(2));
        btnAvancar.addActionListener(e -> avancarEtapa(2));

        painelNav.add(btnVoltar);
        painelNav.add(btnAvancar);

        p.add(criarLabel("E-mail"));
        p.add(Box.createVerticalStrut(5));
        p.add(campoEmail);
        p.add(Box.createVerticalStrut(14));
        p.add(criarLabel("Celular"));
        p.add(Box.createVerticalStrut(5));
        p.add(campoCelular);
        p.add(Box.createVerticalStrut(14));
        p.add(criarLabel("Data de nascimento"));
        p.add(Box.createVerticalStrut(5));
        p.add(campoNascimento);
        p.add(Box.createVerticalStrut(22));
        p.add(painelNav);
        return p;
    }

    private JPanel construirEtapa3() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(FUNDO_CARD);
        p.setAlignmentX(Component.CENTER_ALIGNMENT);

        campoSenha     = new JPasswordField();
        campoConfSenha = new JPasswordField();

        JPanel painelSenha     = criarPainelSenha(campoSenha);
        JPanel painelConfSenha = criarPainelSenha(campoConfSenha);

        // Barra de força da senha
        JProgressBar barraForca = new JProgressBar(0, 4);
        barraForca.setMaximumSize(new Dimension(Integer.MAX_VALUE, 5));
        barraForca.setAlignmentX(Component.LEFT_ALIGNMENT);
        barraForca.setBorderPainted(false);
        barraForca.setBackground(BORDA_CAMPO);

        campoSenha.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { atualizarForca(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { atualizarForca(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) {}

            private void atualizarForca() {
                String s = new String(campoSenha.getPassword());
                int forca = 0;
                if (s.length() >= 8)               forca++;
                if (s.matches(".*[A-Z].*"))         forca++;
                if (s.matches(".*[0-9].*"))         forca++;
                if (s.matches(".*[^A-Za-z0-9].*")) forca++;
                barraForca.setValue(forca);
                Color[] cores = {BORDA_CAMPO, VERMELHO, new Color(224, 148, 82),
                        new Color(224, 212, 82), VERDE};
                barraForca.setForeground(cores[forca]);
            }
        });

        JPanel painelNav = new JPanel(new GridLayout(1, 2, 10, 0));
        painelNav.setBackground(FUNDO_CARD);
        painelNav.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        painelNav.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnVoltar    = criarBotaoSecundario("← Voltar");
        JButton btnCadastrar = criarBotaoPrimario("Criar conta grátis");
        btnVoltar.addActionListener(e    -> voltarEtapa(3));
        btnCadastrar.addActionListener(e -> realizarCadastro());

        painelNav.add(btnVoltar);
        painelNav.add(btnCadastrar);

        p.add(criarLabel("Senha (mínimo 8 caracteres)"));
        p.add(Box.createVerticalStrut(5));
        p.add(painelSenha);
        p.add(Box.createVerticalStrut(5));
        p.add(barraForca);
        p.add(Box.createVerticalStrut(14));
        p.add(criarLabel("Confirmar senha"));
        p.add(Box.createVerticalStrut(5));
        p.add(painelConfSenha);
        p.add(Box.createVerticalStrut(22));
        p.add(painelNav);
        return p;
    }

    // ── Lógica de navegação ────────────────────────────────

    private void avancarEtapa(int etapaAtual) {
        if (!validarEtapa(etapaAtual)) return;

        JPanel[] etapas = {painelEtapa1, painelEtapa2, painelEtapa3};
        etapas[etapaAtual - 1].setVisible(false);
        etapas[etapaAtual].setVisible(true);
        this.etapaAtual = etapaAtual + 1;
        atualizarIndicador();
        lblErro.setText(" ");
    }

    private void voltarEtapa(int etapaAtual) {
        JPanel[] etapas = {painelEtapa1, painelEtapa2, painelEtapa3};
        etapas[etapaAtual - 1].setVisible(false);
        etapas[etapaAtual - 2].setVisible(true);
        this.etapaAtual = etapaAtual - 1;
        atualizarIndicador();
        lblErro.setText(" ");
    }

    private boolean validarEtapa(int etapa) {
        lblErro.setText(" ");

        if (etapa == 1) {
            if (campoNome.getText().trim().length() < 3) {
                lblErro.setText("Nome muito curto.");
                return false;
            }
            if (MascaraUtil.apenasNumeros(campoCpf.getText()).length() != 11) {
                lblErro.setText("CPF inválido.");
                return false;
            }
        }

        if (etapa == 2) {
            String email = campoEmail.getText().trim();
            if (!email.contains("@") || !email.contains(".")) {
                lblErro.setText("E-mail inválido.");
                return false;
            }
            if (MascaraUtil.apenasNumeros(campoCelular.getText()).length() < 10) {
                lblErro.setText("Celular inválido.");
                return false;
            }
            // Valida e converte DD/MM/AAAA
            if (MascaraUtil.converterData(campoNascimento.getText()) == null) {
                lblErro.setText("Data inválida. Use DD/MM/AAAA.");
                return false;
            }
            // Verifica maioridade
            LocalDate nasc  = MascaraUtil.converterData(campoNascimento.getText());
            LocalDate hoje  = LocalDate.now();
            int       idade = hoje.getYear() - nasc.getYear();
            if (nasc.plusYears(idade).isAfter(hoje)) idade--;
            if (idade < 18) {
                lblErro.setText("Você precisa ter 18 anos ou mais.");
                return false;
            }
        }

        return true;
    }

    private void realizarCadastro() {
        String senha     = new String(campoSenha.getPassword());
        String confSenha = new String(campoConfSenha.getPassword());

        if (senha.length() < 8) {
            lblErro.setText("Senha deve ter pelo menos 8 caracteres.");
            return;
        }
        if (!senha.equals(confSenha)) {
            lblErro.setText("As senhas não coincidem.");
            return;
        }

        // Coleta e limpa os dados
        String nome      = campoNome.getText().trim();
        String cpf       = MascaraUtil.apenasNumeros(campoCpf.getText());
        String email     = campoEmail.getText().trim();
        String celular   = MascaraUtil.apenasNumeros(campoCelular.getText());
        LocalDate nasc   = MascaraUtil.converterData(campoNascimento.getText());

        // BCrypt — hash da senha antes de qualquer operação no banco
        String hashSenha = SenhaUtil.criptografar(senha);

        Connection con = Conexao.conectar();
        if (con == null) {
            lblErro.setText("Sem conexão com o banco de dados.");
            return;
        }

        try {
            // Verifica se CPF já existe
            PreparedStatement psVerifica = con.prepareStatement(
                    "SELECT cd_usuario FROM usuario WHERE cd_cpf = ?"
            );
            psVerifica.setString(1, cpf);
            ResultSet rs = psVerifica.executeQuery();
            if (rs.next()) {
                lblErro.setText("CPF já cadastrado.");
                rs.close(); psVerifica.close(); con.close();
                return;
            }
            rs.close(); psVerifica.close();

            // Verifica se e-mail já existe
            PreparedStatement psEmail = con.prepareStatement(
                    "SELECT cd_usuario FROM usuario WHERE ds_email = ?"
            );
            psEmail.setString(1, email);
            ResultSet rsEmail = psEmail.executeQuery();
            if (rsEmail.next()) {
                lblErro.setText("E-mail já cadastrado.");
                rsEmail.close(); psEmail.close(); con.close();
                return;
            }
            rsEmail.close(); psEmail.close();

            // Insere com senha já criptografada pelo BCrypt
            PreparedStatement psInsere = con.prepareStatement(
                    "INSERT INTO usuario (nm_usuario, cd_cpf, cd_celular, ds_email, senha, dt_nascimento) " +
                            "VALUES (?, ?, ?, ?, ?, ?)"
            );
            psInsere.setString(1, nome);
            psInsere.setString(2, cpf);
            psInsere.setString(3, celular);
            psInsere.setString(4, email);
            psInsere.setString(5, hashSenha); // hash BCrypt, não texto puro
            psInsere.setDate(6, java.sql.Date.valueOf(nasc));

            int linhas = psInsere.executeUpdate();
            psInsere.close();
            con.close();

            if (linhas > 0) {
                JOptionPane.showMessageDialog(this,
                        "Conta criada com sucesso!\nBem-vindo ao SennaBank, " + nome + "!",
                        "Cadastro realizado",
                        JOptionPane.INFORMATION_MESSAGE);
                new TelaLogin().setVisible(true);
                dispose();
            }

        } catch (SQLException e) {
            lblErro.setText("Erro no banco: " + e.getMessage());
            System.err.println(e.getMessage());
        }
    }

    // ── Indicador de etapas visual ────────────────────────

    private JPanel criarIndicadorEtapas() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        p.setBackground(FUNDO_CARD);
        p.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        dot1 = criarDot("1", true);
        dot2 = criarDot("2", false);
        dot3 = criarDot("3", false);

        p.add(dot1);
        p.add(criarLinha());
        p.add(dot2);
        p.add(criarLinha());
        p.add(dot3);
        return p;
    }

    private JLabel criarDot(String numero, boolean ativo) {
        JLabel dot = new JLabel(numero, SwingConstants.CENTER);
        dot.setFont(new Font("Arial", Font.BOLD, 11));
        dot.setPreferredSize(new Dimension(26, 26));
        dot.setOpaque(true);
        dot.setBorder(BorderFactory.createLineBorder(ativo ? VERDE : BORDA_CINZA));
        if (ativo) {
            dot.setBackground(VERDE);
            dot.setForeground(Color.BLACK);
        } else {
            dot.setBackground(FUNDO_CAMPO);
            dot.setForeground(CINZA_TEXTO);
        }
        return dot;
    }

    private JPanel criarLinha() {
        JPanel linha = new JPanel();
        linha.setBackground(BORDA_CINZA);
        linha.setPreferredSize(new Dimension(40, 1));
        return linha;
    }

    private void atualizarIndicador() {
        JLabel[] dots = {dot1, dot2, dot3};
        for (int i = 0; i < dots.length; i++) {
            boolean ativo = (i + 1 == etapaAtual);
            dots[i].setBackground(ativo ? VERDE : FUNDO_CAMPO);
            dots[i].setForeground(ativo ? Color.BLACK : CINZA_TEXTO);
            dots[i].setBorder(BorderFactory.createLineBorder(ativo ? VERDE : BORDA_CINZA));
        }
    }

    // ── Helpers de estilo ────────────────────────────────

    private JLabel criarLabelCentro(String t, int size, int style, Color cor) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("Arial", style, size));
        l.setForeground(cor);
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        return l;
    }

    private JLabel criarLabel(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Arial", Font.PLAIN, 12));
        l.setForeground(CINZA_LABEL);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JTextField criarCampo() {
        JTextField campo = new JTextField();
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
        return campo;
    }

    private JPanel criarPainelSenha(JPasswordField campo) {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(FUNDO_CAMPO);
        painel.setBorder(BorderFactory.createLineBorder(BORDA_CAMPO));
        painel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        painel.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setBorder(new EmptyBorder(8, 12, 8, 8));
        campo.setBackground(FUNDO_CAMPO);
        campo.setForeground(Color.WHITE);
        campo.setCaretColor(Color.WHITE);
        campo.setFont(new Font("Arial", Font.PLAIN, 13));
        JButton btnOlho = new JButton("👁");
        btnOlho.setBackground(FUNDO_CAMPO);
        btnOlho.setForeground(CINZA_TEXTO);
        btnOlho.setBorderPainted(false);
        btnOlho.setFocusPainted(false);
        btnOlho.setPreferredSize(new Dimension(38, 38));
        btnOlho.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnOlho.addActionListener(e -> {
            if (campo.getEchoChar() == 0) { campo.setEchoChar('•'); btnOlho.setText("👁"); }
            else                          { campo.setEchoChar((char)0); btnOlho.setText("🙈"); }
        });
        painel.add(campo, BorderLayout.CENTER);
        painel.add(btnOlho, BorderLayout.EAST);
        return painel;
    }

    private JButton criarBotaoPrimario(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(VERDE);
        btn.setForeground(Color.BLACK);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
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
        btn.setFont(new Font("Arial", Font.PLAIN, 12));
        btn.setBorder(BorderFactory.createLineBorder(BORDA_CINZA));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        return btn;
    }

    private JButton criarBotaoLink(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(null);
        btn.setForeground(CINZA_TEXTO);
        btn.setFont(new Font("Arial", Font.PLAIN, 11));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        return btn;
    }
}