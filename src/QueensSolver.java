import java.util.*;

public class QueensSolver {

    private static void diagonalsOutOfBoard(int y, int x, int size, int[][] conflicts, boolean plusMinus) {
        boolean check = x >= size || y >= size || x < 0 || y < 0;
        if (plusMinus) {
            if (check)
                return;
            conflicts[x][y]++;
        } else {
            if (check)
                return;
            conflicts[x][y]--;
        }
    }

    private static void fixRowAndDiagonal(int row, int col, int[][] conflicts, int size, boolean plusMinus) {
        for (int j = 0; j < size; j++) { // ред
            if (col == j)
                continue;
            if (plusMinus) {
                conflicts[row][j]++;
            } else {
                conflicts[row][j]--;
            }
        }
        for (int j = 1; j < size; j++) {
            diagonalsOutOfBoard(col - j, row + j, size, conflicts, plusMinus);
            diagonalsOutOfBoard(col - j, row - j, size, conflicts, plusMinus);
            diagonalsOutOfBoard(col + j, row + j, size, conflicts, plusMinus);
            diagonalsOutOfBoard(col + j, row - j, size, conflicts, plusMinus);
        }
    }

    private static void fillBoard(int[] queens, int[][] conflicts, int size) {
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            int randomNumber = random.nextInt(size);
            queens[i] = randomNumber;
            for (int j = 0; j < size; j++) { // колона
                conflicts[j][i]++;
            }
            fixRowAndDiagonal(randomNumber, i, conflicts, size, true);
        }
    }

    private static void resolveConflicts(int[] queens, int size, int[][] conflicts) {
        int lastConflictIndex = -1;
        boolean hasConflicts = true;
        while (hasConflicts) {
            hasConflicts = false;
            int row = 0, col = 0, max = 1, min = Integer.MAX_VALUE, newRow = -1;

            for (int i = 0; i < size; i++) {
                if (i == lastConflictIndex)
                    continue;
                if (max < conflicts[queens[i]][i]) {
                    max = conflicts[queens[i]][i];
                    row = queens[i];
                    col = i;
                }
            }

            if (max == 1)
                return;

            for (int i = 0; i < size; i++) {
                if (i == row)
                    continue;
                if (min > conflicts[i][col]) {
                    newRow = i;
                    min = conflicts[i][col];
                }
            }

            fixRowAndDiagonal(row, col, conflicts, size, false);
            fixRowAndDiagonal(newRow, col, conflicts, size, true);
            queens[col] = newRow;
            lastConflictIndex = col;
            hasConflicts = true;
        }
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
        fillBoard(queens, conflicts, N);

        long startTime = System.currentTimeMillis();
        resolveConflicts(queens, N, conflicts);
        long endTime = System.currentTimeMillis();

        if (N < 100) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (i == queens[j])
                        System.out.print('*');
                    else
                        System.out.print('_');
                }
                System.out.println();
            }
        }
        System.out.printf("Time taken: %.2f seconds%n", (endTime - startTime) / 1000.0);
    }
}
