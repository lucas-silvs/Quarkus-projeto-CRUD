package com.crudquarkus.components;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class PasswordComponents {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String criptografarSenha(String senhaPura){
        return encoder.encode(senhaPura);
    }
    public static boolean isSenhaCorreta(String senhaCriptografada, String senhaRecebida){
        return encoder.matches(senhaRecebida,senhaCriptografada);
    }

}


