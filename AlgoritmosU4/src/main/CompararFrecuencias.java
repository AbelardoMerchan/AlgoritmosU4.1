package main;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CompararFrecuencias {

    public static void main(String[] args) {
        int rows = 1000;
        int cols = 1000;

        // Generar matriz aleatoria de números entre -1000 y 1000
        int[][] matrix = new int[rows][cols];
        Random random = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextInt(2001) - 1000; // Rango [-1000, 1000]
            }
        }
        System.out.println("Matriz generada exitosamente.");

        // Aplanar matriz
        int[] array = flattenMatrix(matrix);

        // Solución no optimizada
        long startTimeGen = System.nanoTime();
        int mostFrequentGen = findMostFrequentGeneric(array);
        long endTimeGen = System.nanoTime();
        System.out.println("Número más frecuente (genérico): " + mostFrequentGen);
        System.out.println("Tiempo (genérico): " + (endTimeGen - startTimeGen) + " ns");

        // Solución optimizada
        long startTimeOpt = System.nanoTime();
        int mostFrequentOpt = findMostFrequentOptimized(array);
        long endTimeOpt = System.nanoTime();
        System.out.println("Número más frecuente (optimizado): " + mostFrequentOpt);
        System.out.println("Tiempo (optimizado): " + (endTimeOpt - startTimeOpt) + " ns");
    }

    // Función para aplanar la matriz en un array unidimensional
    public static int[] flattenMatrix(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] flatArray = new int[rows * cols];
        int index = 0;
        for (int[] row : matrix) {
            for (int num : row) {
                flatArray[index++] = num;
            }
        }
        return flatArray;
    }

    // Solución no optimizada
    public static int findMostFrequentGeneric(int[] array) {
        int maxCount = 0;
        int mostFrequent = array[0];
        for (int i = 0; i < array.length; i++) {
            int count = 0;
            for (int j = 0; j < array.length; j++) {
                if (array[i] == array[j]) {
                    count++;
                }
            }
            if (count > maxCount) {
                maxCount = count;
                mostFrequent = array[i];
            }
        }
        return mostFrequent;
    }

    // Solución optimizada
    public static int findMostFrequentOptimized(int[] array) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        int maxCount = 0;
        int mostFrequent = array[0];

        for (int num : array) {
            int count = frequencyMap.getOrDefault(num, 0) + 1;
            frequencyMap.put(num, count);
            if (count > maxCount) {
                maxCount = count;
                mostFrequent = num;
            }
        }
        return mostFrequent;
    }
}

