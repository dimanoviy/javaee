package edu.javaee.gameoflife;

import java.io.*;

public class GameOfLife {
    private static final int FIELD_SIZE = 5;
    private static final int LIFE_LASTS = 400;
    private static final byte THREADS_NUMBER = 1;
    private static final int MIN_NEIGHBOURS_TO_LIVE = 2;
    private static final int MAX_NEIGHBOURS_TO_LIVE = 3;
    private static int[][] fieldToday = new int[FIELD_SIZE][FIELD_SIZE];
    private static final int[][] fieldTomorrow = new int[FIELD_SIZE][FIELD_SIZE];

    public static void main(String[] args) throws IOException, InterruptedException {
        fillFieldFromFile("src/main/resources/input.txt");
        long t = System.currentTimeMillis();
        for (int i = 0; i < LIFE_LASTS; i++) {
            if (!isThereLife()) {
                break;
            }
            runMultiThreads(THREADS_NUMBER);
            transferAndClearTomorrowField();
            visualizeField();
        }
        System.out.println(System.currentTimeMillis() - t);
    }

    private static void runMultiThreads(int threadsNumber) throws InterruptedException {
        Thread[] threads = new Thread[threadsNumber];
        for (int i = 0; i < threadsNumber; i++) {
            int finalI = i;
            Runnable runnable = () -> calculateLifeToday(finalI);
            threads[i] = new Thread(runnable);
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }

    private static synchronized void calculateLifeToday(int number) {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = number; j < FIELD_SIZE; j += THREADS_NUMBER) {
                int calculatedSurroundLife = surroundLife(i, j);
                if (calculatedSurroundLife == MAX_NEIGHBOURS_TO_LIVE) {
                    fieldTomorrow[i][j] = 1;
                } else if (calculatedSurroundLife == MIN_NEIGHBOURS_TO_LIVE & fieldToday[i][j] == 1) {
                    fieldTomorrow[i][j] = 1;
                } else {
                    fieldTomorrow[i][j] = 0;
                }
            }
        }
    }

    private static int surroundLife(int i, int j) {
        int topNeighboursSum = fieldToday[prev(i)][prev(j)] + fieldToday[prev(i)][j] + fieldToday[prev(i)][next(j)];
        int middleNeighboursSum = fieldToday[i][prev(j)] + fieldToday[i][next(j)];
        int bottomNeighboursSum = fieldToday[next(i)][prev(j)] + fieldToday[next(i)][j] + fieldToday[next(i)][next(j)];
        return topNeighboursSum + middleNeighboursSum + bottomNeighboursSum;
    }

    private static int prev(int i) {
        if (i == 0) {
            return FIELD_SIZE - 1;
        }
        return i - 1;
    }

    private static int next(int i) {
        if (i == FIELD_SIZE - 1) {
            return 0;
        }
        return i + 1;
    }

    private static void visualizeField() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                System.out.print(fieldToday[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("========================");
    }

    private static boolean isThereLife() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (fieldToday[i][j] == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void fillFieldFromFile(String fileName) throws IOException {
        File file = new File(fileName);
        DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                int read = dataInputStream.read();
                if (read == 10) {
                    j--;
                    continue;
                } else if (read == 49) {
                    fieldToday[i][j] = 1;
                } else if (read == 48) {
                    fieldToday[i][j] = 0;
                }
            }
        }
        dataInputStream.close();
    }

    private static void fillRandomlyField() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                fieldToday[i][j] = (byte) (Math.round(Math.random()));
            }
        }
    }

    private static void transferAndClearTomorrowField() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                fieldToday[i][j] = fieldTomorrow[i][j];
                fieldTomorrow[i][j] = 0;
            }
        }
    }
}
