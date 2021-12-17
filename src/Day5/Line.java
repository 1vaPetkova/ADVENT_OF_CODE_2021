package Day5;

import java.util.ArrayList;
import java.util.List;


public class Line {
    private Point start;
    private Point end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public int getDeltaX() {
        if (start.getX() < end.getX()) {
            return 1;
        } else if (start.getX() > end.getX()) {
            return -1;
        }
        return 0;
    }

    public int getDeltaY() {
        if (start.getY() < end.getY()) {
            return 1;
        } else if (start.getY() > end.getY()) {
            return -1;
        }
        return 0;
    }

    public String getType() {
        if (this.getDeltaX() == 0) {
            return "vertical";
        } else if (this.getDeltaY() == 0) {
            return "horizontal";
        }
        return "diagonal";
    }

    public List<Point> allPoints() {
        List<Point> points = new ArrayList<>();
        int x = start.getX();
        int y = start.getY();
        switch (this.getType()) {
            case "vertical" -> {
                while (y != end.getY() + this.getDeltaY()) {
                    points.add(new Point(start.getX(), y));
                    y += this.getDeltaY();
                }
            }
            case "horizontal" -> {
                while (x != end.getX() + this.getDeltaX()) {
                    points.add(new Point(x, start.getY()));
                    x += this.getDeltaX();
                }
            }
            case "diagonal" -> {
                while (x != end.getX() + this.getDeltaX() && y != end.getY() + this.getDeltaY()) {
                    points.add(new Point(x, y));
                    x += this.getDeltaX();
                    y += this.getDeltaY();
                }
            }
        }
        return points;
    }
}
