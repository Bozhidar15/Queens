import java.util.*;

public class QueensSolver {

    private static final int MAX_STEPS = 50000;

    private static void diagonalsOutOfBoard(int y, int x, int size, int[][] conflicts, boolean increment) {
        if (x >= size || y >= size || x < 0 || y < 0)
            return;
        conflicts[x][y] += increment ? 1 : -1;
    }

    private static void fixRowAndDiagonal(int row, int col, int[][] conflicts, int size, boolean increment) {
        for (int j = 0; j < size; j++) {
            if (col != j) {
                conflicts[row][j] += increment ? 1 : -1;
            }
        }
        for (int j = 1; j < size; j++) {
            diagonalsOutOfBoard(col - j, row + j, size, conflicts, increment);
            diagonalsOutOfBoard(col - j, row - j, size, conflicts, increment);
            diagonalsOutOfBoard(col + j, row + j, size, conflicts, increment);
            diagonalsOutOfBoard(col + j, row - j, size, conflicts, increment);
        }
    }

    private static void initializeBoard(int[] queens, int[][] conflicts, int size) {
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            int randomRow = random.nextInt(size);
            queens[i] = randomRow;
            for (int j = 0; j < size; j++) {
                conflicts[j][i]++;
            }
            fixRowAndDiagonal(randomRow, i, conflicts, size, true);
        }
    }

    private static boolean solveWithMinConflicts(int[] queens, int size, int[][] conflicts) {
        int steps = 0;

        while (steps < MAX_STEPS) {
            int maxConflicts = 0;
            List<Integer> conflictColumns = new ArrayList<>();

            for (int col = 0; col < size; col++) {
                int currentConflicts = conflicts[queens[col]][col];
                if (currentConflicts > maxConflicts) {
                    maxConflicts = currentConflicts;
                    conflictColumns.clear();
                    conflictColumns.add(col);
                } else if (currentConflicts == maxConflicts) {
                    conflictColumns.add(col);
                }
            }

            if (maxConflicts == 1) {
                return true;
            }

            int col = conflictColumns.get(new Random().nextInt(conflictColumns.size()));
            //int col = conflictColumns.get(0);
            int currentRow = queens[col];

            int minConflicts = Integer.MAX_VALUE;
            int bestRow = currentRow;
            for (int row = 0; row < size; row++) {
                if (row == currentRow) continue;
                if (conflicts[row][col] < minConflicts) {
                    minConflicts = conflicts[row][col];
                    bestRow = row;
                }
            }

            fixRowAndDiagonal(currentRow, col, conflicts, size, false);
            fixRowAndDiagonal(bestRow, col, conflicts, size, true);
            queens[col] = bestRow;

            steps++;
        }

        return false;
    }

    public static void main(String[] args) {
        System.out.println("Queens count: ");
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        if (N <= 0) {
            System.out.println(-1);
            return;
        }

        int[] queens = new int[N];
        int[][] conflicts = new int[N][N];

        initializeBoard(queens, conflicts, N);

        long startTime = System.currentTimeMillis();
        boolean solved = solveWithMinConflicts(queens, N, conflicts);
        long endTime = System.currentTimeMillis();

        if (!solved) {
            System.out.println(-1); // При неуспех
        } else if (N < 100) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    System.out.print(i == queens[j] ? '*' : '_');
                }
                System.out.println();
            }
        }
        System.out.printf("Time taken: %.2f seconds%n", (endTime - startTime) / 1000.0);
    }
}
