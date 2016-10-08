package ru.itacademy.maze;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Админ on 31.07.2016.
 */
public class Solution {
    public static void main(String[] args) throws IOException {
        V[][] ourVMaze;
        Point start;
        Point finish;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "UTF-8"));
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "UTF-8"))) {
            String str;
            ourVMaze = createVMaze(reader.readLine());
            start = createPoint(reader.readLine());
            finish = createPoint(reader.readLine());

            int counter = 0;
            while ((str = reader.readLine()) != null) {
                for (int i = 0; i < str.length(); i++) {
                    ourVMaze[counter][i] = new V(new Point(i, counter), str.charAt(i) == '1');
                }
                counter++;
            }

            printWay(writer, findWay(ourVMaze, start, finish));
        }
    }

    public static ArrayList<V> findWay(V[][] ourVMaze, Point start, Point finish) {
        ourVMaze[(int) start.getY()][(int) start.getX()].setMarked(true);
        Stack<V> stack = new Stack<>();
        ArrayList<V> returnedList = new ArrayList<>();

        V currentV = new V(start, false);
        stack.add(currentV);

        do {
            ArrayList<V> currentPointNears = haveNears(ourVMaze, currentV.getPoint());
            int nearsCount = currentPointNears.size();
            if (stack.size() == 0) {
                return returnedList;
            }
            if (currentV.getPoint().equals(finish)) {
                for (V v : stack) {
                    returnedList.add(v);
                }
                returnedList.remove(0);
                returnedList.add(new V(finish, false));
                return returnedList;

            } else if (nearsCount != 0) {
                stack.add(currentV);
                V nextV = currentPointNears.get(0);
                ourVMaze[(int) nextV.getPoint().getY()][(int) nextV.getPoint().getX()].setMarked(true);
                currentV = nextV;
            } else {
                currentV = stack.pop();
            }
        }
        while (true);
    }


    public static ArrayList<V> haveNears(V[][] ourVMaze, Point point) {
        ArrayList<V> p = new ArrayList<>();
        int x = (int) point.getX();
        int y = (int) point.getY();

        if (!ourVMaze[y][x + 1].isWall() && !ourVMaze[y][x + 1].isMarked() && isExist(ourVMaze, y, x + 1)) {
            p.add(ourVMaze[y][x + 1]);
        }
        if (!ourVMaze[y + 1][x].isWall() && !ourVMaze[y + 1][x].isMarked() && isExist(ourVMaze, y + 1, x)) {
            p.add(ourVMaze[y + 1][x]);
        }
        if (!ourVMaze[y][x - 1].isWall() && !ourVMaze[y][x - 1].isMarked() && isExist(ourVMaze, y, x - 1)) {
            p.add(ourVMaze[y][x - 1]);
        }
        if (!ourVMaze[y - 1][x].isWall() && !ourVMaze[y - 1][x].isMarked() && isExist(ourVMaze, y - 1, x)) {
            p.add(ourVMaze[y - 1][x]);
        }

        return p;
    }

    public static boolean isExist(V[][] ourVMaze, int y, int x){
        return  (x > 0 && y > 0 && y < ourVMaze.length - 1 && x < ourVMaze[0].length - 1) ? true : false;
    }

    public static void printWay(PrintWriter writer, ArrayList<V> wayList) {
        if (wayList.size() != 0) {
            for (V v : wayList) {
                writer.println(v);
            }
        } else {
            writer.println("NONE");
        }
    }

    public static V[][] createVMaze(String s) {
        String[] sizeStr = s.split(" ");
        int width = Integer.parseInt(sizeStr[0]);
        int height = Integer.parseInt(sizeStr[1]);
        return new V[height][width];
    }

    public static Point createPoint(String s) {
        String[] sizeStr = s.split(" ");
        int x = Integer.parseInt(sizeStr[0]);
        int y = Integer.parseInt(sizeStr[1]);
        return new Point(x, y);
    }
}
