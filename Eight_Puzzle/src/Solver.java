import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;


import java.util.LinkedList;

public class Solver {
    private boolean isSolvable;
    private final LinkedList<Board> solutions;

    private class Node implements Comparable<Node> {

        private final Board board;
        private final int moves;
        private final Node previous;
        private final int priority;

        public Node(Board board, Node previous) {
            this.board = board;
            this.previous = previous;
            if (previous != null) {
                this.moves = previous.moves + 1;
            } else {
                this.moves = 0;
            }
            this.priority = board.manhattan() + moves;
        }

        @Override
        public int compareTo(Node that) {
            if (this.priority > that.priority) {
                return 1;
            } else if (this.priority < that.priority) {
                return -1;
            } else {
                return 0;
            }
        }

    }
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        Node initialNode = new Node(initial, null);
        Node twinNode = new Node(initial.twin(), null);

        MinPQ<Node> minPQ = new MinPQ<>();
        MinPQ<Node> twinPQ = new MinPQ<>();

        this.solutions = new LinkedList<>();

        minPQ.insert(initialNode);
        twinPQ.insert(twinNode);

        while (true) {
            Node min = minPQ.delMin();
            Node twinMin = twinPQ.delMin();

            if (min.board.isGoal()) {
                fillSolns(min);
                isSolvable = true;
                break;
            } else if (twinMin.board.isGoal()) {
                break;
            }

            for (Board neighbor : min.board.neighbors()) {
                if (min.previous == null) {
                    Node neighborNode = new Node(neighbor, min);
                    minPQ.insert(neighborNode);
                    continue;
                }
                if (!neighbor.equals(min.previous.board)) {
                    Node neighborNode = new Node(neighbor, min);
                    minPQ.insert(neighborNode);
                }
            }

            // twin board:
            for (Board neighbor : twinMin.board.neighbors()) {
                if (min.previous == null) {
                    Node neighborNode = new Node(neighbor, twinMin);
                    twinPQ.insert(neighborNode);
                    continue;
                }
                if (!neighbor.equals(twinMin.previous.board)) {
                    Node neighborNode = new Node(neighbor, twinMin);
                    twinPQ.insert(neighborNode);
                }
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable()) {
            return solutions.size() - 1;
        } else return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (isSolvable()) {
            return solutions;
        } return null;
    }

    private void fillSolns(Node fin) {
        Node copy = fin;
        while (copy != null) {
            solutions.addFirst(copy.board);
            copy = copy.previous;
        }
    }
    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}