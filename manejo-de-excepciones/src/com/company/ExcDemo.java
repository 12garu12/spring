package com.company;

import java.util.SortedMap;

public class ExcDemo {

    public static void main(String[] args) {

        int[] nums = new int[5];

        try {

            System.out.println("Antes que se genere la excepción.");
            //Genera una excepción de indice fuera de limites
            nums[5] = 10;

        }catch (ArrayIndexOutOfBoundsException exc){
            System.out.println("Indice fuera de los límites");
        }
        System.out.println("Después de que se genere la excepción.");


    }


}
