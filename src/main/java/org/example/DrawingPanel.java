package org.example;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class DrawingPanel extends JPanel {
    Argument x = new Argument("x");
    Argument a = new Argument("a", 0);

    int[] y = new int[10001];
    public static double cof = 50;
    String ext = null;

    @Override
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D g = (Graphics2D) gr;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        start(g);
    }

    public Polygon repaint(String ex, Double ax) {
        ext = ex;
        a.setArgumentValue(ax * cof);
        Expression e = new Expression(ex, x, a);
        for (int i = -500; i <= 500; i++) {
            //xa[i+500] = i * 10;
            x.setArgumentValue(i * (1.0 / cof));
            y[i + 500] = (int) (e.calculate() * cof);
        }

        return new Polygon(IntStream.rangeClosed(-500, 500).toArray(), y, 1001);
    }

    private static void start(Graphics2D g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, 1000, 1000);
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(1));
        g.drawLine(500, 0, 500, 1000);
        g.drawLine(0,500,1000, 500);
        g.setColor(Color.GRAY);
        for (int i = 0; i < 1001; i += 100) {
            g.drawString(String.valueOf((i - 500) / cof), i, 10);
            g.drawString(String.valueOf(((i - 500) / cof) * -1), 0, i);
            g.drawLine(i, 0, i, 1000);
            g.drawLine(0,i,1000, i);
        }
        g.setStroke(new BasicStroke(2));
        g.setColor(Color.BLACK);
        g.drawLine(500, 0, 500, 1000);
        g.drawLine(0,500,1000, 500);
        g.translate(500, 500);
        g.rotate(Math.PI);
        g.scale(-1,1);

        g.setStroke(new BasicStroke(3));
        g.setColor(Color.BLUE);
    }

    public void drawBezier(List<Point> points) {
        Graphics2D g = (Graphics2D) this.getGraphics();
        start(g);
        g.setStroke(new BasicStroke(3));
        g.setColor(Color.BLUE);
        int pointCount = points.size();
        double[] xs = new double[pointCount];
        double[] ys = new double[pointCount];
        for (int i = 0; i < pointCount; i++) {
            xs[i] = points.get(i).getX();
            ys[i] = points.get(i).getY();
        }
        List<Integer> x = new ArrayList<>();
        List<Integer> y = new ArrayList<>();
        Casteljau alg = new Casteljau(xs, ys, pointCount);

        if(pointCount > 1){
            float t = 0;
            while (t <= 1) {
                Point temp = alg.getXYValues(t);
                x.add(temp.x);
                y.add(temp.y);
                System.out.println(temp);
                t += 0.01;
            }
        }
        for (var i : points) {
            g.fillOval(i.x, i.y,7, 7);
        }
        pointCount = x.size();
        g.drawPolyline(x.stream().mapToInt(i->i).toArray(), y.stream().mapToInt(i->i).toArray(), pointCount);
    }

    public void draw(Polygon polygon) {
        Graphics2D g = (Graphics2D) this.getGraphics();
        start(g);
        g.setStroke(new BasicStroke(3));
        g.setColor(Color.BLUE);

        polygon.addPoint(Integer.MAX_VALUE, Integer.MAX_VALUE);
        polygon.addPoint(0, Integer.MAX_VALUE);

        g.draw(polygon);
    }
}
