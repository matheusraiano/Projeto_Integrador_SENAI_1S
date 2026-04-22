package telas;

import util.ApenasNumerosFilter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

import static banco.Verificacoes.*;
import static banco.Verificacoes.cpfExiste;
import static banco.Verificacoes.cpfValido;

//Código feito por Matheus Raiano
public class TelaCadastroNova {
    public static void cadastro() {
        AtomicInteger etapa = new AtomicInteger(1);

        //CRIANDO O FRAME
        //nome do frame(janela)
        JFrame frame = new JFrame("Senna Bank - Cadastro");
        //modo que fecha a janela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //tamanho da janela
        frame.setSize(600,420);
        //local onde vai aparecer a janela, como está null, fica no centro
        frame.setLocationRelativeTo(null);


        //CRIANDO O PAINEL
        //criar o painel com o layout que eu quiser
        JPanel painel = new JPanel(new GridLayout(5, 1, 10, 10));
        JPanel pPrVo = new JPanel(new GridLayout(2, 2, 10, 10));
        JPanel passo1 = new JPanel(new GridLayout(3, 2, 10, 10));
        JPanel passo2 = new JPanel(new GridLayout(2, 2, 10, 10));
        JPanel passo3 = new JPanel(new GridLayout(1, 2, 10, 10));
        //criar uma borda com 15 de espaçamento geral
        painel.setBorder(new EmptyBorder(15, 15, 15, 15));


        //ADICIONANDO O PAINEL NA JANELA
        frame.add(painel);


        //CRIANDO OS COMPONENTES
        JLabel lblTit = new JLabel("Senna Bank - Cadastro");
        JLabel lblCont = new JLabel("Passo "+etapa.get());
        JButton btnProx = new JButton("Próximo");
        JButton btnVolt = new JButton("Voltar");
        JButton btnCriar = new JButton("Criar Conta!");
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
        JButton btnentrar = new JButton("Já tem conta? Clique aqui!");
        //filtrando o cpf e numero
        ((AbstractDocument) txtcpf.getDocument()).setDocumentFilter(new ApenasNumerosFilter(11));
        ((AbstractDocument) txtnumero.getDocument()).setDocumentFilter(new ApenasNumerosFilter(11));


        //ADICIONANDO OS COMPONENTES AO PAINEL
        if (etapa.get() == 1) {
            painel.add(lblTit);
            painel.add(lblCont);
            painel.add(passo1);
            passo1.add(lblnome);
            passo1.add(txtnome);
            passo1.add(lblcpf);
            passo1.add(txtcpf);
            passo1.add(lblnascimento);
            passo1.add(txtnascimento);
            painel.add(pPrVo);
            pPrVo.add(btnProx);
            painel.add(btnentrar);
        } else if (etapa.get() == 2) {
            painel.add(lblTit);
            painel.add(lblCont);
            painel.add(passo2);
            passo2.add(lblemail);
            passo2.add(txtemail);
            passo2.add(lblnumero);
            passo2.add(txtnumero);
            painel.add(pPrVo);
            pPrVo.add(btnVolt);
            pPrVo.add(btnProx);
            painel.add(btnentrar);
        } else {
            painel.add(lblTit);
            painel.add(lblCont);
            painel.add(passo3);
            passo3.add(lblsenha);
            passo3.add(txtSenha);
            painel.add(pPrVo);
            pPrVo.add(btnVolt);
            pPrVo.add(btnCriar);
            painel.add(btnentrar);
        }


        //AÇÕES DOS BOTÕES
        JFormattedTextField finalTxtcpf = txtcpf;
        JFormattedTextField finalTxtnascimento = txtnascimento;
        JFormattedTextField finalTxtnumero = txtnumero;
        String senha = new String(txtSenha.getPassword());
        String nome = txtnome.getText();
        String cpfBruto = finalTxtcpf.getText();
        String cpf = limparCpf(cpfBruto);
        String nascimentoFormatado = formatarDataParaMySQL(finalTxtnascimento.getText());
        String email = txtemail.getText();
        String celularBruto = finalTxtnumero.getText();
        String celular = limparNumero(celularBruto);
        //proximo
        btnProx.addActionListener(e -> {
            etapa.addAndGet(1);
            atualizarTela(painel, lblCont, lblTit, btnProx,
                    btnVolt, btnCriar, etapa, passo1,
                    passo2, passo3, lblnome, txtnome,
                    lblcpf, finalTxtcpf, lblnascimento, finalTxtnascimento,
                    lblemail, txtemail, lblnumero, finalTxtnumero,
                    lblsenha, txtSenha, btnentrar);
            if (etapa.get() == 1) {
                if (nascimentoFormatado == null) {
                    JOptionPane.showMessageDialog(frame, "Data inválida!", "ERRO", JOptionPane.ERROR_MESSAGE);
                    return;
                }
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
                if (!dataValida(nascimentoFormatado)) {
                    JOptionPane.showMessageDialog(frame, "Data inválida! Use dd/mm/yyyy", "ERRO", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else if (etapa.get() == 2) {
                if (!emailValido(email)) {
                    JOptionPane.showMessageDialog(frame, "Email inválido!", "ERRO", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!celularValido(celular)) {
                    JOptionPane.showMessageDialog(frame, "Celular inválido!", "ERRO", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });
        //voltar
        btnVolt.addActionListener(e -> {
            etapa.addAndGet(-1);
            atualizarTela(painel, lblCont, lblTit, btnProx,
                    btnVolt, btnCriar, etapa, passo1,
                    passo2, passo3, lblnome, txtnome,
                    lblcpf, finalTxtcpf, lblnascimento, finalTxtnascimento,
                    lblemail, txtemail, lblnumero, finalTxtnumero,
                    lblsenha, txtSenha, btnentrar);
        });
        //abrir
        btnCriar.addActionListener(e -> {
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

        frame.setVisible(true);
    }

    private static void atualizarTela(JPanel painel, JLabel lblTit, JLabel lblCont, JButton btnProx,
                                      JButton btnVolt, JButton btnCriar, AtomicInteger etapa, JPanel passo1,
                                      JPanel passo2, JPanel passo3, JLabel lblnome, JTextField txtnome,
                                      JLabel lblcpf, JFormattedTextField txtcpf, JLabel lblnascimento, JFormattedTextField txtnascimento,
                                      JLabel lblemail, JTextField txtemail, JLabel lblnumero, JFormattedTextField txtnumero,
                                      JLabel lblsenha, JPasswordField txtSenha, JButton btnentrar) {
        painel.removeAll();

        JPanel pPrVo = new JPanel(new GridLayout(1, 2, 10, 10));

        lblCont.setText("Passo " + etapa.get());

        if (etapa.get() == 1) {
            painel.add(lblTit);
            painel.add(lblCont);
            painel.add(passo1);
            passo1.add(lblnome);
            passo1.add(txtnome);
            passo1.add(lblcpf);
            passo1.add(txtcpf);
            passo1.add(lblnascimento);
            passo1.add(txtnascimento);
            painel.add(pPrVo);
            pPrVo.add(btnProx);
            painel.add(btnentrar);
        } else if (etapa.get() == 2) {
            painel.add(lblTit);
            painel.add(lblCont);
            painel.add(passo2);
            passo2.add(lblemail);
            passo2.add(txtemail);
            passo2.add(lblnumero);
            passo2.add(txtnumero);
            painel.add(pPrVo);
            pPrVo.add(btnProx);
            pPrVo.add(btnVolt);
            painel.add(btnentrar);
        } else {
            painel.add(lblTit);
            painel.add(lblCont);
            painel.add(passo3);
            passo3.add(lblsenha);
            passo3.add(txtSenha);
            painel.add(pPrVo);
            pPrVo.add(btnCriar);
            pPrVo.add(btnVolt);
            painel.add(btnentrar);
        }

        painel.revalidate();
        painel.repaint();
    }
}