import java.util.*;

public class QueensSolver {

    private void diagonalsOutOfBoard(int x, int y, int size, int[][] conflicts) {
        if (x >= size || y >= size || x < 0 || y < 0 )
            return;
        conflicts[x][y]++;
    }
    
    private void fillBoard(int[] queens, int[][] conflicts, int size){
        for (int i = 0; i < size; i++) {
            Random random = new Random();
            int randomNumber = random.nextInt(size);
            queens[i]=randomNumber;
            for (int j = 0; j < size; j++) { //col
                conflicts[j][i]++;
            }
            for (int j = 0; j < size; j++) { //row
                if (randomNumber == j)
                    continue;
                conflicts[randomNumber][j]++;
            }
            for (int j = 1; j < size; j++) {
                diagonalsOutOfBoard(i-j, randomNumber+j, size, conflicts);
                diagonalsOutOfBoard(i-j, randomNumber-j, size, conflicts);
                diagonalsOutOfBoard(i+j, randomNumber+j, size, conflicts);
                diagonalsOutOfBoard(i+j, randomNumber-j, size, conflicts);
            }
        }
    }

    private void maxConflictQueen(int[] queens, int size, int[][] conflicts){
        int row=0,col=0,max=0;
        for (int i = 0; i < size; i++) {
            if (max < conflicts[queens[i]][i]){
                max=conflicts[queens[i]][i];
                row=i;
                col=queens[i];
            }
        }
        if (max == 0 )
            return;
        
    }

    public static void main(String[] args) {
        System.out.println("Queens count: \n");
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        if (N <= 0) {
            System.out.println(-1);
            return;
        }
        int[] queens = new int[N];
        int[][] conflicts = new int[N][N];

    }
}
