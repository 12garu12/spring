package com.company;

public class ExcEjemplo {

    // Generando una excepción

    static void genExcepcion(){

        int[] nums = new int[5];

        System.out.println("Antes que se genere la excepcion");

        // Generar una excepción fuera de limites
        nums[5] = 10;

        System.out.println("Esto no se mostrará.");

    }

    public static void main(String[] args) {

        try {

            genExcepcion();

        }catch (IndexOutOfBoundsException exc){
            // Capturando la excepcion
            System.out.println("Indice fuera de los limites!");
        }
        System.out.println("Después de que se genere la excepción.");
    }

}
