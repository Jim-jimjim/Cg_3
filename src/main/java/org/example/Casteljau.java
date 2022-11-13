package org.example;

import java.awt.*;

public class Casteljau {

    private final double[] x;
    private final double[] y;
    private final int n;

    private final double[][] b;

    public Casteljau(double[] x, double[] y, int n) {
        this.x = x;
        this.y = y;
        this.n = n;
        this.b = new double[n][n];
    }

    private void init(double[] initialValues) {
        if (n >= 0) System.arraycopy(initialValues, 0, b[0], 0, n);
    }

    private double evaluate(double t, double[] initialValues) {
        init(initialValues);
        for (int j = 1; j < n; j++) {
            for (int i = 0; i < n - j; i++) {
                b[j][i] = b[j-1][i] * (1-t) + b[j-1][i+1] * t;
            }
        }
        return(b[n-1][0]);
    }

    public Point getXYValues(double t) {
        double xVal = evaluate(t, x);
        double yVal = evaluate(t, y);
        return new Point((int) xVal, (int) yVal);
    }

}