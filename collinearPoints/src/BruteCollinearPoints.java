import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {


        if (!checkNull(points)) {
            throw new IllegalArgumentException();
        }

        Point[] localPoints = points.clone();
        Arrays.sort(localPoints);

        if (localPoints.length > 1) {
            for (int i = 1; i < localPoints.length; i++) {
                if (localPoints[i].compareTo(localPoints[i - 1]) == 0)
                    throw new IllegalArgumentException();
            }
        }

        ArrayList<LineSegment> op = new ArrayList<>();

        if (points.length > 3) {
            Point[] store = new Point[4];
            for (int i = 0; i < points.length - 3; i++) {
                store[0] = points[i];
                for (int j = i + 1; j < points.length - 2; j++) {
                    store[1] = points[j];
                    for (int k = j + 1; k < points.length - 1; k++) {
                        store[2] = points[k];
                        for (int l = k + 1; l < points.length; l++) {
                            store[3] = points[l];
                            if (collinear(store)) {
                                LineSegment fin = createSegment(store);
                                op.add(fin);
                            }
                        }
                    }
                }
            }
        }
        segments = op.toArray(new LineSegment[op.size()]);
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return segments.clone();
    }

    private LineSegment createSegment(Point[] tmp) {
        Arrays.sort(tmp);
        return new LineSegment(tmp[0], tmp[3]);
    }

    private boolean collinear(Point[] tmp ) {
        double slope1 = tmp[0].slopeTo(tmp[1]);
        double slope2 = tmp[0].slopeTo(tmp[2]);
        double slope3 = tmp[0].slopeTo(tmp[3]);

        if (Double.compare(slope1,slope2) == 0 && Double.compare(slope2,slope3) == 0) {
            return true;
        }
        return false;
    }

    private boolean checkNull(Point[] tmp) {
        for (int i = 0; i < tmp.length; i++) {
            if (tmp[i] == null) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
