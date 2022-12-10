package com.crudquarkus.components;

import com.crudquarkus.exception.LayerException;

import javax.ws.rs.core.Response;
import java.util.function.Function;

public class TecladoVirtualComponent {

    TecladoVirtualComponent() {
        throw new IllegalStateException("componente não deve ser instanciado: " + this.getClass().getName());
    }

    public static boolean isSenhaCorreta(String senha, char[][] tecladoVirtual, int[] teclasPresionadas){

        char[] senhadecodificada = decode(tecladoVirtual, teclasPresionadas, (String candidate) -> {
            try {
                return PasswordComponents.isSenhaCorreta(senha, candidate);
            } catch (Exception e){
                throw new LayerException("Erro durante geração de senha teclado virtual: " + e.getMessage(), "INTERNO", Response.Status.INTERNAL_SERVER_ERROR, "TecladoVirtualComponent.isSenhaCorreta()");
            }
                }
        );
        return senhadecodificada != null;
    }
    private static char[] decode(char[][] tecladoVirtual, int[] teclasPresionadas, Function<String, Boolean> visitor) {

        int groupSize = tecladoVirtual[0].length;

        //determina o número de combinações que iremos usar, assumindo que o tamanho do teclado virtual possui o mesmo numero de simbolos por tecla
        int numeroDeCombinacoes = pow(groupSize, teclasPresionadas.length);

        //inicia o tamanho da cadeia de caracteres que representa o candidato a senha
        char[] candidate = new char[teclasPresionadas.length];

        for (int i = 0; i < numeroDeCombinacoes; i++){
            int auxiliar = i;

            //prepara uma nova combinação
            for (int j = (teclasPresionadas.length - 1); j>= 0; j--) {
                int idx = auxiliar % groupSize;
                candidate[j] = tecladoVirtual[teclasPresionadas[j]][idx];
                auxiliar = auxiliar / groupSize;
            }
            if(Boolean.TRUE.equals(visitor.apply(new String(candidate)))){
                return candidate;
            }


        }

        return null;
    }

    //Operação de potencia simples para numeros pequenos

    protected static int pow(int base, int expoente){

        //caso expecial onde a base é 2
        if(base == 2){
            return  1 << expoente;
        }

        if(base == 1){
            return 1;
        }

        int resultado = base;
        for(int i = 1; i < expoente; i++){
            resultado = resultado * base;
        }
        return  resultado;
    }


}
