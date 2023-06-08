import java.util.LinkedList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
public class Board {
    private final int[][] board;
    private final int n;
    private int[] empty;

    // create a board from an n-by-n array of tiles
    // wehere tiles[row][col] = tile at (row,col)
    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.board = clone(tiles);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0) {
                    empty = new int[] {i, j};
                }
            }
        }

    }

    //
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.dimension()).append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(board[i][j]);
                if (j == n - 1) break;
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return this.n;
    }

    // number of tiles out of place
    public int hamming() {
        int countWrong = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != (n * i + (j + 1)) && board[i][j] != 0) {
                    countWrong++;
                }
            }
        }
        return countWrong;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int countWrong = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != 0 && board[i][j] != (n * i + (j + 1))) {
                    int yDiff = Math.floorDiv(board[i][j] - 1, n);
                    int xDiff = board[i][j] - (n * yDiff) - 1;
                    countWrong += Math.abs(i - yDiff) + Math.abs(j - xDiff);
                }
            }
        }
        return countWrong;
    }



    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
       if (y == this) {
           return true;
       }
       if (!(y instanceof Board)) {
           return false;
       }
       if (this.n != ((Board)y).dimension()) {
           return false;
       }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != ((Board) y).board[i][j]) return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        LinkedList<Board> neighbors = new LinkedList<>();

        // neighbor - up
        if (empty[0] != 0) {
            neighbors.add(exch(empty[0], empty[1], empty[0] - 1, empty[1]));
        }

        // neighbor down
        if (empty[0] != n - 1) {
            neighbors.add(exch(empty[0], empty[1], empty[0] + 1, empty[1]));
        }

        // neighbor left
        if (empty[1] != 0) {
            neighbors.add(exch(empty[0], empty[1], empty[0], empty[1] - 1));
        }

        // neighbor right
        if (empty[1] != n - 1) {
            neighbors.add(exch(empty[0], empty[1], empty[0], empty[1] + 1));
        }

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if (board[0][0] != 0 && board[0][1] != 0) {
            return exch(0, 0, 0, 1);
        } else return exch(1, 0, 1, 1);

    }

    private int[][] clone(int[][] tiles) {
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            copy[i] = tiles[i].clone();
        }
        return copy;
    }

    private Board exch(int x1, int y1, int x2, int y2) {
        int[][] clone = clone(board);
        int temp = clone[x2][y2];
        clone[x2][y2] = clone[x1][y1];
        clone[x1][y1] = temp;
        return new Board(clone);
    }


    // unit testing (not graded)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        StdOut.println("Neighbors: ");
        for (Board temp : initial.neighbors()) {
            StdOut.println(temp);
        }

        StdOut.println("The Board: ");
        StdOut.println(initial);

        StdOut.println("Hamming Distance: ");
        StdOut.println(initial.hamming());

        StdOut.println("Manhattan Distance: ");
        StdOut.println(initial.manhattan());

        StdOut.println("Twin: ");
        StdOut.println(initial.twin());
    }


}
