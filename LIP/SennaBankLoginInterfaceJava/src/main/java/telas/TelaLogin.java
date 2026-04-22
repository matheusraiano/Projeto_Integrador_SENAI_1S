package telas;

import util.ApenasNumerosFilter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.MaskFormatter;
import java.awt.*;

import static banco.Verificacoes.limparCpf;
import static banco.Verificacoes.verificarLogin;
//Código feito por Matheus Raiano
public class TelaLogin {
    public static void login() {
        //CRIANDO O FRAME
        //nome do frame(janela)
        JFrame frame = new JFrame("Senna Bank - Login");
        //modo que fecha a janela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //tamanho da janela
        frame.setSize(600,320);
        //local onde vai aparecer a janela, como está null, fica no centro
        frame.setLocationRelativeTo(null);


        //CRIANDO O PAINEL
        //criar o painel com o layout que eu quiser
        JPanel painel = new JPanel(new GridLayout(5, 1, 10, 10));
        JPanel painel2 = new JPanel(new GridLayout(2, 2, 10, 10));
        //criar uma borda com 15 de espaçamento geral
        painel.setBorder(new EmptyBorder(15, 15, 15, 15));


        //CRIANDO OS COMPONENTES
        //assim eu crio uma label para colocar algo escrito por exemplo
        JLabel lblTitulo = new JLabel("Senna Bank - Login");
        JLabel lblCpf = new JLabel("CPF:");
        //aqui eu criei o campo de inserir texto
        JFormattedTextField txtCpf;
            try {
                MaskFormatter mascaraCpf = new MaskFormatter("###.###.###-##");
                mascaraCpf.setPlaceholderCharacter('_');
                txtCpf = new JFormattedTextField(mascaraCpf);
            } catch (Exception e) {
                txtCpf = new JFormattedTextField();
            }
        JLabel lblSenha = new JLabel("Senha:");
        JPasswordField txtSenha = new JPasswordField();
        JButton btnEntrar = new JButton("Entrar");
        JButton btnEsqueciSenha = new JButton("Esqueci a senha");
        JButton btnAbrirConta = new JButton("Não tem conta? Clique aqui!");
        //filtrando o cpf
        ((AbstractDocument) txtCpf.getDocument()).setDocumentFilter(new ApenasNumerosFilter(11));


        //ADICIONANDO OS COMPONENTES AO PAINEL
        painel2.add(lblCpf);
        painel2.add(txtCpf);
        painel2.add(lblSenha);
        painel2.add(txtSenha);
        painel.add(lblTitulo);
        painel.add(painel2);
        painel.add(btnEntrar);
        painel.add(btnEsqueciSenha);
        painel.add(btnAbrirConta);


        //ADICIONANDO O PAINEL NA JANELA
        frame.add(painel);
        frame.setVisible(true);


        //AÇÕES DOS BOTÕES
        //entrar
        JFormattedTextField finalTxtcpf = txtCpf;
        btnEntrar.addActionListener(e -> {
            // Captura o CPF digitado no campo
            String cpfBruto = finalTxtcpf.getText();
            String cpf = limparCpf(cpfBruto);
            // Captura a senha (JPasswordField retorna char[], então converte)
            String senha = new String(txtSenha.getPassword());

            // Validação básica (evita consulta desnecessária)
            if (cpf.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Preencha todos os campos!", "AVISO", JOptionPane.ERROR_MESSAGE);
                return; // interrompe execução
            }

            // Chama o método que consulta o banco
            boolean valido = verificarLogin(cpf, senha);

            // Verifica resultado
            if (valido) {
                JOptionPane.showMessageDialog(frame, "Login realizado com sucesso!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "CPF ou senha inválidos!", "AVISO", JOptionPane.ERROR_MESSAGE);
            }
        });
        //esqueciSenha
        btnEsqueciSenha.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Indisponível!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
        });
        //abrirConta
        btnAbrirConta.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                frame.setVisible(false);
                TelaCadastro.cadastro();
            });
        });
    }
}