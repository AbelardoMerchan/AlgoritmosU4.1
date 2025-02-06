package main;

import java.util.Arrays;
import java.util.Random;

public class BusquedaNumero {

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

        // Número a buscar
        int x = 500;

        // Búsqueda secuencial
        boolean foundSequential = sequentialSearch(matrix, x);
        boolean foundSequentialNeg = sequentialSearch(matrix, -x);
        System.out.println("Búsqueda secuencial para " + x + ": " + foundSequential);
        System.out.println("Búsqueda secuencial para " + -x + ": " + foundSequentialNeg);

        // Convertir la matriz en un arreglo unidimensional y ordenarlo para búsquedas binaria e interpolación
        int[] flatArray = flattenMatrix(matrix);
        Arrays.sort(flatArray);

        // Búsqueda binaria
        boolean foundBinary = binarySearch(flatArray, x);
        boolean foundBinaryNeg = binarySearch(flatArray, -x);
        System.out.println("Búsqueda binaria para " + x + ": " + foundBinary);
        System.out.println("Búsqueda binaria para " + -x + ": " + foundBinaryNeg);

        // Búsqueda por interpolación
        boolean foundInterpolation = interpolationSearch(flatArray, x);
        boolean foundInterpolationNeg = interpolationSearch(flatArray, -x);
        System.out.println("Búsqueda por interpolación para " + x + ": " + foundInterpolation);
        System.out.println("Búsqueda por interpolación para " + -x + ": " + foundInterpolationNeg);

        // Ordenar usando diferentes algoritmos
        System.out.println("Ordenando usando Bubble Sort...");
        int[] bubbleSortedArray = bubbleSort(Arrays.copyOf(flatArray, flatArray.length));

        System.out.println("Ordenando usando Insertion Sort...");
        int[] insertionSortedArray = insertionSort(Arrays.copyOf(flatArray, flatArray.length));

        System.out.println("Ordenando usando Merge Sort...");
        int[] mergeSortedArray = mergeSort(Arrays.copyOf(flatArray, flatArray.length));

        System.out.println("Ordenando usando Shell Sort...");
        int[] shellSortedArray = shellSort(Arrays.copyOf(flatArray, flatArray.length));

        System.out.println("Ordenaciones completadas.");
    }

    // Búsqueda secuencial
    public static boolean sequentialSearch(int[][] matrix, int target) {
        for (int[] row : matrix) {
            for (int num : row) {
                if (num == target) {
                    return true;
                }
            }
        }
        return false;
    }

    // Búsqueda binaria
    public static boolean binarySearch(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (array[mid] == target) {
                return true;
            }
            if (array[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return false;
    }

    // Búsqueda por interpolación
    public static boolean interpolationSearch(int[] array, int target) {
        int low = 0;
        int high = array.length - 1;

        while (low <= high && target >= array[low] && target <= array[high]) {
            if (low == high) {
                if (array[low] == target) return true;
                return false;
            }

            // Fórmula de interpolación para encontrar la posición probable
            int pos = low + ((target - array[low]) * (high - low)) / (array[high] - array[low]);

            // Si encontramos el valor
            if (array[pos] == target) {
                return true;
            }

            // Ajustar límites
            if (array[pos] < target) {
                low = pos + 1;
            } else {
                high = pos - 1;
            }
        }
        return false;
    }

    // Bubble Sort
    public static int[] bubbleSort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        return array;
    }

    // Insertion Sort
    public static int[] insertionSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
        return array;
    }

    // Merge Sort
    public static int[] mergeSort(int[] array) {
        if (array.length <= 1) return array;

        int mid = array.length / 2;
        int[] left = Arrays.copyOfRange(array, 0, mid);
        int[] right = Arrays.copyOfRange(array, mid, array.length);

        mergeSort(left);
        mergeSort(right);

        return merge(array, left, right);
    }

    private static int[] merge(int[] array, int[] left, int[] right) {
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                array[k++] = left[i++];
            } else {
                array[k++] = right[j++];
            }
        }
        while (i < left.length) {
            array[k++] = left[i++];
        }
        while (j < right.length) {
            array[k++] = right[j++];
        }
        return array;
    }

    // Shell Sort
    public static int[] shellSort(int[] array) {
        int n = array.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = array[i];
                int j;
                for (j = i; j >= gap && array[j - gap] > temp; j -= gap) {
                    array[j] = array[j - gap];
                }
                array[j] = temp;
            }
        }
        return array;
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
}
