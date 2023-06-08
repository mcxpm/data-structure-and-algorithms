import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private LineSegment[] segs;

    public FastCollinearPoints(Point[] points) {

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

        ArrayList<LineSegment> store = new ArrayList<>();

        if (localPoints.length > 3) {
            Point[] tmp = localPoints.clone();
            for (Point p : localPoints) {
                Arrays.sort(tmp, p.slopeOrder());
                isSeg(tmp,p,store);
            }
        }
        segs = store.toArray(new LineSegment[store.size()]);
    }

    public int numberOfSegments() {
        return segs.length;
    }

    public LineSegment[] segments() {
        return segs.clone();
    }

    private void isSeg(Point[] tmp, Point p, ArrayList<LineSegment> store) {
        int first = 1;
        double slope1 = p.slopeTo(tmp[first]);

        for (int i = 2; i < tmp.length; i++) {
            double tmpSlope = p.slopeTo(tmp[i]);
            if (!collinear(tmpSlope, slope1)) {
                if (i - first >= 3) {
                    Point[] lineSeg = createSeg(tmp, p, first, i);
                    if (lineSeg[0].compareTo(p) == 0) {
                        store.add(new LineSegment(lineSeg[0],lineSeg[1]));
                    }
                }
                first = i;
                slope1 = tmpSlope;
            }
        }

        if (tmp.length - first >= 3) {
            Point[] lastPoints = createSeg(tmp, p, first, tmp.length);
            if (lastPoints[0].compareTo(p) == 0) {
                store.add(new LineSegment(lastPoints[0], lastPoints[1]));
            }
        }
    }

    private boolean collinear(double tmpSlope, double slope1) {
        return Double.compare(tmpSlope, slope1) == 0;
    }

    private Point[] createSeg(Point[] tmp, Point p, int first, int end) {
        ArrayList<Point> op = new ArrayList<>();
        op.add(p);
        for (int i = first; i < end; i++) {
            op.add(tmp[i]);
        }
        op.sort(null);
        return new Point[] {op.get(0), op.get(op.size()-1)};
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
