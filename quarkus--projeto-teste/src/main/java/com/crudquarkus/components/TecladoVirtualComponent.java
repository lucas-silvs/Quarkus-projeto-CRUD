package com.crudquarkus.components;

import java.util.Arrays;

public class TecladoVirtualComponent {
    public static boolean isSenhaCorreta(String senha, String[][] tecladoVirtual, int[] teclasPresionadas) {
        System.out.println("matriz do teclado virtual: "+ Arrays.spliterator(tecladoVirtual));
        System.out.println("teclas pressionadas: "+ Arrays.toString(teclasPresionadas));
        System.out.println("senha: " + senha);
        return true;
    }

     TecladoVirtualComponent() {
        throw new IllegalStateException("componente não deve ser instanciado: " + this.getClass().getName());
    }
}
