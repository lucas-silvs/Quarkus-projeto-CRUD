package com.somnus.components;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

public class PasswordComponents {
    private static final Random random = new SecureRandom();
    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int ITERATIONS = 10000;
    private static final int KEYLENGTH = 256;

    private static final int DEFAULT_SALT_VALUE = 30;

    private PasswordComponents() {
        throw new IllegalStateException("classe de utilidade não pode ser instanciada");
    }

    //gerar o valor do salt com o valor passado, consultar como funciona o uso do valor
    public static String getSaltvalue()
    {
        StringBuilder finalval = new StringBuilder(DEFAULT_SALT_VALUE);

        for (int i = 0; i < DEFAULT_SALT_VALUE; i++)
        {
            finalval.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        return new String(finalval);
    }

    // gera uma hash com a senha pura e o valor do salt gerado
    public static byte[] hash(char[] password, byte[] salt)
    {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEYLENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try
        {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e)
        {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        }
        finally
        {
            spec.clearPassword();
        }
    }

    //metodo de encriptar a senha utilizando a senha pura e o salt gerado
    public static String generateSecurePassword(String password, String salt)
    {
        String finalval = null;

        //com o valor do salt e a senha , cria uma hash
        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());

        // cripografa a hash para base64, sendo esse o valor que será armazenado no banco
        finalval = Base64.getEncoder().encodeToString(securePassword);

        return finalval;
    }

    //compara a chave recebida, descriptografa a chave da base utilizando o salt e compara
    public static boolean verifyUserPassword(String providedPassword, String securedPassword, String salt)
    {
        boolean finalval = false;

        // gera uma hash com a senha pura e o valor do salt
        String newSecurePassword = generateSecurePassword(providedPassword, salt);

        // compara se a hash gerada é igual a senha criptografada da base de dados
        finalval = newSecurePassword.equalsIgnoreCase(securedPassword);

        return finalval;
    }
}


