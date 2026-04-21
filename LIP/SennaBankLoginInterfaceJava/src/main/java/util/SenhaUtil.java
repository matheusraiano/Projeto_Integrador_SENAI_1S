package util;

import org.mindrot.jbcrypt.BCrypt;

public class SenhaUtil {

    // Gera o hash da senha antes de salvar no banco
    public static String criptografar(String senha) {
        return BCrypt.hashpw(senha, BCrypt.gensalt(12));
    }

    // Verifica se a senha digitada bate com o hash do banco
    public static boolean verificar(String senhaDigitada, String hashDoBanco) {
        return BCrypt.checkpw(senhaDigitada, hashDoBanco);
    }
}