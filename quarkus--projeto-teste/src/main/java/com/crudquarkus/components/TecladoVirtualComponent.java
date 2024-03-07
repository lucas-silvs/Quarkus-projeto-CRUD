package com.crudquarkus.components;

import com.crudquarkus.exception.LayerException;
import org.eclipse.microprofile.config.ConfigProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;

public class TecladoVirtualComponent {

//    instancia logger
    private static final Logger LOGGER = LoggerFactory.getLogger(TecladoVirtualComponent.class);

    //criar propertie numberThreads
    private static final int numberThreads = ConfigProvider.getConfig().getValue("quarkus.thread.number", Integer.class);

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

    public static boolean isSenhaCorreta(String senha, List<List<Character>> tecladoVirtual, int[] teclasPresionadas){

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

    public static boolean isSenhaCorretaBinario(String senha, char[][] tecladoVirtual, int[] teclasPresionadas) {

        String senhadecodificada = decodeBinarySearch(tecladoVirtual, teclasPresionadas, (String candidate) -> {
                    try {
                        LOGGER.info(candidate);
                        return PasswordComponents.isSenhaCorreta(senha, candidate);
                    } catch (Exception e){
                        throw new LayerException("Erro durante geração de senha teclado virtual: " + e.getMessage(), "INTERNO", Response.Status.INTERNAL_SERVER_ERROR, "TecladoVirtualComponent.isSenhaCorreta()");
                    }
                }
        );
        return senhadecodificada != null;
    }

    public static boolean isSenhaCorretaParallel(String senha, char[][] tecladoVirtual, int[] teclasPresionadas) {
        char[] senhadecodificada = decodeParallel(tecladoVirtual, teclasPresionadas, (String candidate) -> {
                    try {
                        LOGGER.info(candidate);
                        return PasswordComponents.isSenhaCorreta(senha, candidate);
                    } catch (Exception e){
                        throw new LayerException("Erro durante geração de senha teclado virtual: " + e.getMessage(), "INTERNO", Response.Status.INTERNAL_SERVER_ERROR, "TecladoVirtualComponent.isSenhaCorreta()");
                    }
                }
        );
        return senhadecodificada != null;
    }
    public static boolean isSenhaCorretaParallel(String senha, List<List<Character>> tecladoVirtual, int[] teclasPresionadas) {
        char[] senhadecodificada = decodeParallel(tecladoVirtual, teclasPresionadas, (String candidate) -> {
                    try {
                        LOGGER.info(candidate);
                        return PasswordComponents.isSenhaCorreta(senha, String.valueOf(candidate));
                    } catch (Exception e){
                        throw new LayerException("Erro durante geração de senha teclado virtual: " + e.getMessage(), "INTERNO", Response.Status.INTERNAL_SERVER_ERROR, "TecladoVirtualComponent.isSenhaCorreta()");
                    }
                }
        );
        return senhadecodificada != null;
    }


    private static String decodeBinarySearch(char[][] tecladoVirtual, int[] teclasPresionadas, Function<String, Boolean> visitor) {
        int groupSize = tecladoVirtual[0].length;
        int numeroDeCombinacoes = pow(groupSize, teclasPresionadas.length);

        char[] candidate = new char[teclasPresionadas.length];
        int left = 0;
        int right = numeroDeCombinacoes - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            int auxiliar = mid;
            for (int j = (teclasPresionadas.length - 1); j >= 0; j--) {
                int idx = auxiliar % groupSize;
                candidate[j] = tecladoVirtual[teclasPresionadas[j]][idx];
                auxiliar = auxiliar / groupSize;
            }

            boolean isCorrect = visitor.apply(new String(candidate));
            if (isCorrect) {
                return new String(candidate);
            }

            if (isCorrect) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return null;
    }

    private static char[] decodeParallel(List<List<Character>> tecladoVirtual, int[] teclasPresionadas, Function<String, Boolean> visitor) {
        int groupSize = tecladoVirtual.get(0).size();
        int numeroDeCombinacoes = (int) Math.pow(groupSize, teclasPresionadas.length);
        char[] candidate = new char[teclasPresionadas.length];
        int numThreads = numberThreads;

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CompletionService<Boolean> completionService = new ExecutorCompletionService<>(executor);

        int batchSize = numeroDeCombinacoes / numThreads;
        for (int i = 0; i < numThreads; i++) {
            final int start = i * batchSize;
            final int end = (i == numThreads - 1) ? numeroDeCombinacoes : (i + 1) * batchSize;

            completionService.submit(() -> {
                for (int j = start; j < end; j++) {
                    int auxiliar = j;

                    for (int k = teclasPresionadas.length - 1; k >= 0; k--) {
                        int idx = auxiliar % groupSize;
                        candidate[k] = tecladoVirtual.get(teclasPresionadas[k]).get(idx);
                        auxiliar = auxiliar / groupSize;
                    }

                    if (Boolean.TRUE.equals(visitor.apply(new String(candidate)))) {
                        return true;
                    }
                }

                return false;
            });
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                Future<Boolean> future = completionService.take();
                if (future.get()) {
                    return candidate;
                }
            } catch (InterruptedException | ExecutionException e) {

            }
        }

        return null;
    }


    private static char[] decodeParallel(char[][] tecladoVirtual, int[] teclasPresionadas, Function<String, Boolean> visitor) {
        int groupSize = tecladoVirtual[0].length;
        int numeroDeCombinacoes = (int) Math.pow(groupSize, teclasPresionadas.length);
        char[] candidate = new char[teclasPresionadas.length];
        int numThreads = numberThreads;

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CompletionService<Boolean> completionService = new ExecutorCompletionService<>(executor);

        int batchSize = numeroDeCombinacoes / numThreads;
        for (int i = 0; i < numThreads; i++) {
            final int start = i * batchSize;
            final int end = (i == numThreads - 1) ? numeroDeCombinacoes : (i + 1) * batchSize;

            completionService.submit(() -> {
                for (int j = start; j < end; j++) {
                    int auxiliar = j;

                    for (int k = teclasPresionadas.length - 1; k >= 0; k--) {
                        int idx = auxiliar % groupSize;
                        candidate[k] = tecladoVirtual[teclasPresionadas[k]][idx];
                        auxiliar = auxiliar / groupSize;
                    }

                    if (Boolean.TRUE.equals(visitor.apply(new String(candidate)))) {
                        return true;
                    }
                }

                return false;
            });
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                Future<Boolean> future = completionService.take();
                if (future.get()) {
                    return candidate;
                }
            } catch (InterruptedException | ExecutionException e) {
                LOGGER.info(e.getMessage());            }
        }

        return null;
    }

    private static char[] decode(List<List<Character>> tecladoVirtual, int[] teclasPresionadas, Function<String, Boolean> visitor) {

        int groupSize = tecladoVirtual.get(0).size();

        //determina o número de combinações que iremos usar, assumindo que o tamanho do teclado virtual possui o mesmo numero de simbolos por tecla
        int numeroDeCombinacoes = pow(groupSize, teclasPresionadas.length);

        //inicia o tamanho da cadeia de caracteres que representa o candidato a senha
        char[] candidate = new char[teclasPresionadas.length];

        for (int i = 0; i < numeroDeCombinacoes; i++){
            int auxiliar = i;

            //prepara uma nova combinação
            for (int j = (teclasPresionadas.length - 1); j>= 0; j--) {
                int idx = auxiliar % groupSize;
                candidate[j] = tecladoVirtual.get(teclasPresionadas[j]).get(idx);
                auxiliar = auxiliar / groupSize;
            }
            if(Boolean.TRUE.equals(visitor.apply(new String(candidate)))){
                return candidate;
            }
        }
        return null;
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
