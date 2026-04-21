package util;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class MascaraUtil {

    // Aplica máscara de CPF enquanto o usuário digita
    // Entrada: 12345678901 → Exibe: 123.456.789-01
    public static void aplicarMascaraCpf(JTextField campo) {
        campo.getDocument().addDocumentListener(new DocumentListener() {
            boolean editando = false;

            @Override public void insertUpdate(DocumentEvent e)  { formatar(); }
            @Override public void removeUpdate(DocumentEvent e)  { formatar(); }
            @Override public void changedUpdate(DocumentEvent e) { }

            private void formatar() {
                if (editando) return;
                editando = true;
                SwingUtilities.invokeLater(() -> {
                    String texto = campo.getText().replaceAll("[^0-9]", "");
                    if (texto.length() > 11) texto = texto.substring(0, 11);
                    StringBuilder sb = new StringBuilder(texto);
                    if (sb.length() > 9) sb.insert(9, '-');
                    if (sb.length() > 6) sb.insert(6, '.');
                    if (sb.length() > 3) sb.insert(3, '.');
                    campo.setText(sb.toString());
                    editando = false;
                });
            }
        });
    }

    // Aplica máscara de celular: (11) 99999-9999
    public static void aplicarMascaraCelular(JTextField campo) {
        campo.getDocument().addDocumentListener(new DocumentListener() {
            boolean editando = false;

            @Override public void insertUpdate(DocumentEvent e)  { formatar(); }
            @Override public void removeUpdate(DocumentEvent e)  { formatar(); }
            @Override public void changedUpdate(DocumentEvent e) { }

            private void formatar() {
                if (editando) return;
                editando = true;
                SwingUtilities.invokeLater(() -> {
                    String texto = campo.getText().replaceAll("[^0-9]", "");
                    if (texto.length() > 11) texto = texto.substring(0, 11);
                    StringBuilder sb = new StringBuilder(texto);
                    if (sb.length() > 7)  sb.insert(7, '-');
                    if (sb.length() > 2)  sb.insert(2, ") ");
                    if (sb.length() > 0)  sb.insert(0, '(');
                    campo.setText(sb.toString());
                    editando = false;
                });
            }
        });
    }

    // Aplica máscara de data: 21031960 → 21/03/1960
    public static void aplicarMascaraData(JTextField campo) {
        campo.getDocument().addDocumentListener(new DocumentListener() {
            boolean editando = false;

            @Override public void insertUpdate(DocumentEvent e)  { formatar(); }
            @Override public void removeUpdate(DocumentEvent e)  { formatar(); }
            @Override public void changedUpdate(DocumentEvent e) { }

            private void formatar() {
                if (editando) return;
                editando = true;
                SwingUtilities.invokeLater(() -> {
                    String texto = campo.getText().replaceAll("[^0-9]", "");
                    if (texto.length() > 8) texto = texto.substring(0, 8);
                    StringBuilder sb = new StringBuilder(texto);
                    if (sb.length() > 4) sb.insert(4, '/');
                    if (sb.length() > 2) sb.insert(2, '/');
                    campo.setText(sb.toString());
                    editando = false;
                });
            }
        });
    }

    // Converte DD/MM/AAAA para LocalDate (para salvar no banco)
    // Retorna null se a data for inválida
    public static LocalDate converterData(String dataFormatada) {
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(dataFormatada, fmt);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    // Remove tudo que não é número — útil antes de salvar CPF e celular no banco
    public static String apenasNumeros(String texto) {
        return texto.replaceAll("[^0-9]", "");
    }
}