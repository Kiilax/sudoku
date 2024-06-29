package grille;

import java.util.Random;

public class GrilleSudoku {
    private int[][] tabGrille = new int[9][9];
    private Random rd = new Random();

    public GrilleSudoku() {
        genereGrille();
    }

    /**
     * Remplit tabGrille avec une solution de Sudoku valide.
     */
    private void genereGrille() {
        if (!fillGrid(0, 0)) {
            System.out.println("Failed to generate a valid Sudoku grid.");
        }
    }

    /**
     * Remplit la grille de manière récursive en utilisant l'algorithme de backtracking.
     * @param row ligne actuelle
     * @param col colonne actuelle
     * @return true si la grille est remplie correctement, false sinon
     */
    private boolean fillGrid(int row, int col) {
        if (row == 9) {
            row = 0;
            if (++col == 9) {
                return true;
            }
        }
        if (tabGrille[row][col] != 0) {
            return fillGrid(row + 1, col);
        }

        int[] numbers = generateRandomOrder();
        for (int num : numbers) {
            if (isValid(row, col, num)) {
                tabGrille[row][col] = num;
                if (fillGrid(row + 1, col)) {
                    return true;
                }
                tabGrille[row][col] = 0;
            }
        }
        return false;
    }

    /**
     * Génère un tableau contenant les nombres de 1 à 9 dans un ordre aléatoire.
     * @return tableau de nombres dans un ordre aléatoire
     */
    private int[] generateRandomOrder() {
        int[] numbers = new int[9];
        for (int i = 0; i < 9; i++) {
            numbers[i] = i + 1;
        }
        for (int i = 0; i < numbers.length; i++) {
            int randomIndex = rd.nextInt(numbers.length);
            int temp = numbers[i];
            numbers[i] = numbers[randomIndex];
            numbers[randomIndex] = temp;
        }
        return numbers;
    }

    /**
     * Vérifie si un numéro peut être placé à la position donnée sans violer les règles de Sudoku.
     * @param row ligne de la case
     * @param col colonne de la case
     * @param num numéro à vérifier
     * @return true si le numéro peut être placé, false sinon
     */
    private boolean isValid(int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (tabGrille[row][i] == num || tabGrille[i][col] == num) {
                return false;
            }
        }
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabGrille[startRow + i][startCol + j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @return le tableau sous forme de grille Sudoku prêt à être affiché
     */
    private String getGrille() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            for (int y = 0; y < 9; y++) {
                res.append(tabGrille[i][y]).append(" ");
                if ((y + 1) % 3 == 0 && y != 8) {
                    res.append("| ");
                }
            }
            res.append("\n");
            if ((i + 1) % 3 == 0 && i != 8) {
                res.append("------+-------+------\n");
            }
        }
        return res.toString();
    }

    @Override
    public String toString() {
        return getGrille();
    }
}
