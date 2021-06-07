package ru.senina.itmo.lab8.graphics;

import java.awt.*;

public class LabWorkAnimation {
    private Color color;
    private int x;
    private int y;
    private int radius;
    private int delta;

    public LabWorkAnimation(Color color, int x, int y, int radius) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.radius = radius;
        delta = Math.random() > 0.5 ? 1 : -1;
    }

    public void update(int width, int speed) {
        x += speed * delta;
        if (x + radius >= width) {
            x = width - radius;
            delta *= -1;
        } else if (x < 0) {
            x = 0;
            delta *= -1;
        }
    }

    public void paint(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, radius, radius);
    }
}
