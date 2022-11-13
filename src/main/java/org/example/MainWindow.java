package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainWindow extends JFrame {
    private JPanel panel1;
    private JPanel DrawingPanel;
    private JTextField textField1;
    private JButton button1;
    private JButton buttonPlus;
    private JButton buttonMinus;
    private JButton bezierButton;
    private JButton reset;
    private JButton bsplineButton;
    private JPanel parametrPanel;
    private JSpinner spinner1;
    private JCheckBox useCheckBox;
    List<Point> points = new ArrayList<>();

    public MainWindow() {
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);


        button1.addActionListener(e -> {
            pa();
        });

        buttonPlus.addActionListener(e -> {
            org.example.DrawingPanel.cof*=2;
            if (!Objects.equals(textField1.getText(), "")) {
                pa();
            } else panel1.repaint();
        });

        buttonMinus.addActionListener(e -> {
            org.example.DrawingPanel.cof/=2;
            if (!Objects.equals(textField1.getText(), "")) {
                pa();
            } else  panel1.repaint();
        });

        bezierButton.addActionListener(e -> ((org.example.DrawingPanel) DrawingPanel).drawBezier(points));

        //bsplineButton.addActionListener(e -> ((org.example.DrawingPanel) DrawingPanel).drawBezier(points));

        reset.addActionListener(e -> points.removeAll(points));

        DrawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                points.add(new Point(e.getX()-500, e.getY()*-1 + 500));
                System.out.println(points);
                getGraphics().fillOval(e.getX(), e.getY() + 30, 5, 5);
//                int[] x = new int[points.size()];
//                int[] y = new int[points.size()];
//                for (int i = 0; i < points.size(); i++) {
//                    x[i] = points.get(i).x + 500;
//                    y[i] = points.get(i).y * -1 + 500 + 30;
//                }
//                getGraphics().drawPolyline(x, y, points.size());
            }
        });
    }

    private void pa() {
            Polygon temp = ((org.example.DrawingPanel) DrawingPanel).repaint(textField1.getText(),
                    Double.parseDouble(spinner1.getValue().toString()));
            ((org.example.DrawingPanel) DrawingPanel).draw(temp);
    }

    private void createUIComponents() {
        DrawingPanel = new DrawingPanel();
    }
}
