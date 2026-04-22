package telas;

import util.ApenasNumerosFilter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.MaskFormatter;
import java.awt.*;

import static banco.Conexao.*;
import static banco.Verificacoes.*;

public class TelaCadastro {
    public static void cadastro() {
        //CRIANDO O FRAME
        //nome do frame(janela)
        JFrame frame = new JFrame("Senna Bank - Cadastro");
        //modo que fecha a janela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //tamanho da janela
        frame.setSize(600,720);
        //local onde vai aparecer a janela, como está null, fica no centro
        frame.setLocationRelativeTo(null);


        //CRIANDO O PAINEL
        //criar o painel com o layout que eu quiser
        JPanel painel = new JPanel(new GridLayout(4, 2, 10, 10));
        JPanel painel2 = new JPanel(new GridLayout(6, 2, 10, 10));
        //criar uma borda com 15 de espaçamento geral
        painel.setBorder(new EmptyBorder(15, 15, 15, 15));


        //CRIANDO OS COMPONENTES
        JLabel lblTit = new JLabel("Senna Bank - Cadastro");
        JLabel lblnome = new JLabel("Nome Completo:");
        JTextField txtnome = new JTextField();
        JLabel lblcpf = new JLabel("CPF:");
        JFormattedTextField txtcpf;
            try {
                MaskFormatter mascaraCpf = new MaskFormatter("###.###.###-##");
                mascaraCpf.setPlaceholderCharacter('_');
                txtcpf = new JFormattedTextField(mascaraCpf);
            } catch (Exception e) {
                txtcpf = new JFormattedTextField();
            }
        JLabel lblemail = new JLabel("Email:");
        JTextField txtemail = new JTextField();
        JLabel lblnumero = new JLabel("Número:");
        JFormattedTextField txtnumero;
            try {
                MaskFormatter mascaraCelular = new MaskFormatter("(##) #####-####");
                mascaraCelular.setPlaceholderCharacter('_');
                txtnumero = new JFormattedTextField(mascaraCelular);
            } catch (Exception e) {
                txtnumero = new JFormattedTextField();
            }
        JLabel lblnascimento = new JLabel("Data de Nascimento:");
        JFormattedTextField txtnascimento;
            try {
                MaskFormatter mascaraData = new MaskFormatter("##/##/####");
                mascaraData.setPlaceholderCharacter('_');
                txtnascimento = new JFormattedTextField(mascaraData);
            } catch (Exception e) {
                txtnascimento = new JFormattedTextField();
            }
        JLabel lblsenha = new JLabel("Senha:");
        JPasswordField txtSenha = new JPasswordField();
        JButton btnabrir = new JButton("Criar Conta");
        JButton btnentrar = new JButton("Já tem conta? Cliquen aqui!");
        //filtrando o cpf e numero
        ((AbstractDocument) txtcpf.getDocument()).setDocumentFilter(new ApenasNumerosFilter(11));
        ((AbstractDocument) txtnumero.getDocument()).setDocumentFilter(new ApenasNumerosFilter(11));
        ((AbstractDocument) txtemail.getDocument()).setDocumentFilter(new ApenasNumerosFilter(8));


        //ADICIONANDO OS COMPONENTES AO PAINEL
        painel2.add(lblnome);
        painel2.add(txtnome);
        painel2.add(lblcpf);
        painel2.add(txtcpf);
        painel2.add(lblemail);
        painel2.add(txtemail);
        painel2.add(lblnumero);
        painel2.add(txtnumero);
        painel2.add(lblnascimento);
        painel2.add(txtnascimento);
        painel2.add(lblsenha);
        painel2.add(txtSenha);
        painel.add(lblTit);
        painel.add(painel2);
        painel.add(btnabrir);
        painel.add(btnentrar);

        //ADICIONANDO O PAINEL NA JANELA
        frame.add(painel);
        frame.setVisible(true);


        //AÇÕES DOS BOTÕES
        //entrar
        btnentrar.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                frame.setVisible(false);
                TelaLogin.login();
            });
        });
        //abrirConta
        JFormattedTextField finalTxtnascimento = txtnascimento;
        JFormattedTextField finalTxtnumero = txtnumero;
        JFormattedTextField finalTxtcpf = txtcpf;
        btnabrir.addActionListener(e -> {
            String nome = txtnome.getText();
            String cpfBruto = finalTxtcpf.getText();
            String cpf = limparCpf(cpfBruto);
            String email = txtemail.getText();
            String celularBruto = finalTxtnumero.getText();
            String celular = limparNumero(celularBruto);
            String nascimentoFormatado = formatarDataParaMySQL(finalTxtnascimento.getText());

            if (nascimentoFormatado == null) {
                JOptionPane.showMessageDialog(frame, "Data inválida!", "ERRO", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String senha = new String(txtSenha.getPassword());

            if (!nomeValido(nome)) {
                JOptionPane.showMessageDialog(frame, "Nome inválido!", "ERRO", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!cpfValido(cpf)) {
                JOptionPane.showMessageDialog(frame, "CPF deve ter 11 números!", "ERRO", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (cpfExiste(cpf)) {
                JOptionPane.showMessageDialog(frame, "CPF já cadastrado!", "ERRO", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!emailValido(email)) {
                JOptionPane.showMessageDialog(frame, "Email inválido!", "ERRO", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!celularValido(celular)) {
                JOptionPane.showMessageDialog(frame, "Celular inválido!", "ERRO", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!dataValida(nascimentoFormatado)) {
                JOptionPane.showMessageDialog(frame, "Data inválida! Use dd/mm/yyyy", "ERRO", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!senhaValida(senha)) {
                JOptionPane.showMessageDialog(frame, "Senha fraca! Precisa ter:\n- 8+ caracteres\n- Maiúscula\n- Minúscula\n- Especial", "ERRO", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean sucesso = cadastrarUsuario(nome, cpf, email, celular, nascimentoFormatado, senha);

            if (sucesso) {
                JOptionPane.showMessageDialog(frame, "Conta criada com sucesso!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
                TelaLogin.login();
            } else {
                JOptionPane.showMessageDialog(frame, "Erro ao cadastrar!", "ERRO", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}