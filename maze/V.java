package ru.itacademy.maze;

import java.awt.*;

/**
 * Created by Админ on 31.07.2016.
 */
public class V {
    private Point point;
    private final boolean wall;
    private boolean marked = false;

    public V(Point point, boolean wall) {
        this.point = point;
        this.wall = wall;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public boolean isMarked() {
        return marked;
    }

    public Point getPoint() {
        return point;
    }

    public boolean isWall() {
        return wall;
    }

    @Override
    public String toString() {
        return "[" + (int) point.getX() + ":" + (int) point.getY() + "]";
    }
}
