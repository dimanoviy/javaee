package edu.javaee.gameoflife;

import java.io.*;

public class GameOfLife {
    final static int fieldSize = 50;
    static byte[][] fieldToday = new byte[fieldSize][fieldSize];
    static byte[][] fieldTomorrow = new byte[fieldSize][fieldSize];
    private final static int LIFELASTS = 1000000;

    private static void calculateLifeToday() {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if ((2 <= surroundLife(i, j)) & (surroundLife(i, j) <= 3)) {
                    fieldTomorrow[i][j] = 1;
                } else {
                    fieldTomorrow[i][j] = 0;
                }
            }
        }
    }

    private static int surroundLife(int i, int j) {
        int sum = fieldToday[prev(i)][prev(j)] + fieldToday[prev(i)][j] + fieldToday[prev(i)][next(j)] +
                fieldToday[i][prev(j)] + fieldToday[i][next(j)] +
                fieldToday[next(i)][prev(j)] + fieldToday[next(i)][j] + fieldToday[next(i)][next(j)];
        return sum;
    }

    private static int prev(int i) {
        if (i == 0) {
            return fieldSize - 1;
        }
        return i - 1;
    }

    private static int next(int i) {
        if (i == fieldSize - 1) {
            return 0;
        }
        return i + 1;
    }

    private static void visualizeField() {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                System.out.print(fieldToday[i][j] + " ");
            }
            System.out.println();
        }
    }
    private static boolean isThereLife() {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if (fieldToday[i][j] == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean lifeLasts(int k) {
        return k < LIFELASTS;
    }

    private static void fillFieldFromFile(String fileName) throws IOException {
        File file = new File(fileName);
        DataInputStream targetStream = new DataInputStream(new FileInputStream(file));
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if (targetStream.read() == 48) {
                    fieldToday[i][j] = 0;
                } else {
                    fieldToday[i][j] = 1;
                }
            }
        }
        targetStream.close();
    }

    private static void fillRandomlyField() {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                fieldToday[i][j] = (byte) ((byte) (Math.random() * 10) / 5);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        fillFieldFromFile("/Users/dn/IdeaProjects/javaee/src/main/java/edu/javaee/gameoflife/input.txt");
//        fillRandomlyField();
        int k = 0;
        long t = System.currentTimeMillis();
        while (isThereLife() & lifeLasts(k)) {
            calculateLifeToday();
            fieldToday = fieldTomorrow;
            k++;
        }
        System.out.println("This life lasted for " + k + "days");
        System.out.println(System.currentTimeMillis() - t);
    }
}
