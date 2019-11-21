package edu.javaee.gameoflife;

import java.io.*;

public class GameOfLife {
    final private static int FIELD_SIZE = 10_000;
    final private static int LIFE_LASTS = 1_000_000_000;
    final private static boolean MULTITHREADING = true;
    final private static byte threadsNumber = 3;
    final private static int MIN_NEIGHBOURS_TO_LIVE = 2;
    final private static int MAX_NEIGHBOURS_TO_LIVE = 3;
    static byte[][] fieldToday = new byte[FIELD_SIZE][FIELD_SIZE];
    final static byte[][] fieldTomorrow = new byte[FIELD_SIZE][FIELD_SIZE];
    private static int topNeighboursSum;
    private static int bottomNeighboursSum;

    public static void main(String[] args) {
//        fillFieldFromFile("/resources/input.txt");
        fillRandomlyField();
        long t = System.currentTimeMillis();
        for (int i = 0; i < LIFE_LASTS; i++) {
            if (!isThereLife()) {
                break;
            }
            if (MULTITHREADING) {
                calculateLifeTodayMultithreaded(threadsNumber);
            } else {
                calculateLifeToday();
            }
            fieldToday = fieldTomorrow;
        }
        System.out.println(System.currentTimeMillis() - t);
    }

    private static void calculateLifeTodayMultithreaded(byte threadsNumber) {
        for (byte k = 0; k < threadsNumber; k++) {
            byte finalK = k;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    calculateTodayThread(finalK);
                }

                private void calculateTodayThread(byte threadNumber) {
                    for (int i = 0; i < FIELD_SIZE; i++) {
                        for (int j = threadNumber; j < FIELD_SIZE; j+=threadsNumber) {
                            if ((MIN_NEIGHBOURS_TO_LIVE <= surroundLife(i, j)) &
                                    (surroundLife(i, j) <= MAX_NEIGHBOURS_TO_LIVE)) {
                                fieldTomorrow[i][j] = 1;
                            } else {
                                fieldTomorrow[i][j] = 0;
                            }
                        }
                    }
                }
            }).start();
        }
    }

    private static void calculateLifeToday() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if ((MIN_NEIGHBOURS_TO_LIVE <= surroundLife(i, j)) & (surroundLife(i, j) <= MAX_NEIGHBOURS_TO_LIVE)) {
                    fieldTomorrow[i][j] = 1;
                } else {
                    fieldTomorrow[i][j] = 0;
                }
            }
        }
    }

    private static int surroundLife(int i, int j) {
        topNeighboursSum = fieldToday[prev(i)][prev(j)] + fieldToday[prev(i)][j] + fieldToday[prev(i)][next(j)];
        bottomNeighboursSum = fieldToday[next(i)][prev(j)] + fieldToday[next(i)][j] + fieldToday[next(i)][next(j)];
        return topNeighboursSum + fieldToday[i][prev(j)] + fieldToday[i][next(j)] + bottomNeighboursSum;
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
        DataInputStream targetStream = new DataInputStream(new FileInputStream(file));
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
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
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                fieldToday[i][j] = (byte) (Math.round(Math.random()));
//                fieldToday[i][j] = (byte) ((byte) (Math.random() * 10) / 5);
            }
        }
    }
}
