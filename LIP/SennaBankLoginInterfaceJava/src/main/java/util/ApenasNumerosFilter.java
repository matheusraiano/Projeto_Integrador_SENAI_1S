package util;

import javax.swing.text.*;
//Código feito por Matheus Raiano
public class ApenasNumerosFilter extends DocumentFilter {

    private final int maxLength;

    public ApenasNumerosFilter(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
            throws BadLocationException {

        if (string == null) return;

        // remove tudo que não for número
        string = string.replaceAll("[^0-9]", "");

        // limita tamanho
        if ((fb.getDocument().getLength() + string.length()) <= maxLength) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {

        if (text == null) return;

        text = text.replaceAll("[^0-9]", "");

        if ((fb.getDocument().getLength() - length + text.length()) <= maxLength) {
            super.replace(fb, offset, length, text, attrs);
        }
    }
}