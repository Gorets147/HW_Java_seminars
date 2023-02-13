package HW_Java_seminars.seminar_6;

import java.util.Random;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;

public class dz {
    static int sizeX = 0, sizeY = 0;
    static int exitX1 = 0, exitY1 = 0;
    static int exitX2 = 0, exitY2 = 0;
    static int pointX = 0, pointY = 0;

    static int[][] fieldWork = new int[][] {};
    static char[][] fieldChar = new char[][] {};
    static Scanner inScan = new Scanner(System.in, "Cp1251");
    static Queue<String> queueStep = new LinkedList<String>();
    static Point2d ppt = new Point2d();

    public int W = 0;

    public static void main(String[] args) {

        if (SetMainParam()) {

            System.out.println("Итак, начальные параметры определены:");
            System.out.printf("1. Размер поля (%d, %d)\n", sizeX, sizeY);
            System.out.printf("2. Объект находится в точке (%d, %d)\n", pointX, pointY);
            System.out.printf("3. Выход 1 с поля в точке (%d, %d)\n", exitX1, exitY1);
            System.out.printf("4. Выход 2 с поля в точке (%d, %d)\n", exitX2, exitY2);

            SetField();
            StepWave();
            SetRoute();
            PrintMass(fieldWork);
            System.out.println("\nПоиск маршрута...\n");
            PrintCharMass(fieldChar);

        }

    }

    static void SetRoute() {
        int indY = 0, indX = 0;
        int temp = 0;
        char[][] arr = new char[sizeY][sizeX];
        fieldChar = arr;
        arr = null;
        for (int i = 0; i < sizeY; i++) {
            for (int k = 0; k < sizeX; k++) {
                if (fieldWork[i][k] == -1)
                    fieldChar[i][k] = '█';
            }
        }
        int[] sWay = ShortWay(exitY1 - 1, exitX1 - 1, exitY2 - 1, exitX2 - 1);
        if (sWay[0] != -1) {
            indY = sWay[0];
            indX = sWay[1];

            temp = fieldWork[indY][indX];
            fieldChar[indY][indX] = 'F';

            while (temp > 1) {
                if (indY - 1 >= 0) {
                    if (fieldWork[indY - 1][indX] == temp - 1) {
                        indY--;
                        temp--;
                        fieldChar[indY][indX] = '*';
                    }
                }
                if (indX + 1 < sizeX) {
                    if (fieldWork[indY][indX + 1] == temp - 1) {
                        indX++;
                        temp--;
                        fieldChar[indY][indX] = '*';
                    }
                }
                if (indY + 1 < sizeY) {
                    if (fieldWork[indY + 1][indX] == temp - 1) {
                        indY++;
                        temp--;
                        fieldChar[indY][indX] = '*';
                    }
                }

                if (indX - 1 >= 0) {
                    if (fieldWork[indY][indX - 1] == temp - 1) {
                        indX--;
                        temp--;
                        fieldChar[indY][indX] = '*';
                    }
                }
            }
            fieldChar[pointY - 1][pointX - 1] = 'S';
        } else
            System.out.println("Маршрут не был найден!");
    }

    static int[] ShortWay(int inY1, int inX1, int inY2, int inX2) {
        int[] temp = new int[2];
        int res1 = fieldWork[inY1][inX1];
        int res2 = fieldWork[inY2][inX2];
        if (res1 == -2 && res2 == -2)
            temp[0] = -1;
        else if (res1 == -2 && res2 != -2) {
            temp[0] = inY2;
            temp[1] = inX2;
        } else if (res1 != -2 && res2 == -2) {
            temp[0] = inY1;
            temp[1] = inX1;
        } else {
            if (res1 <= res2) {
                temp[0] = inY1;
                temp[1] = inX1;
            } else {
                temp[0] = inY2;
                temp[1] = inX2;
            }
        }
        return temp;
    }

    static void StepWave() {
        int realY = 0, realX = 0;
        boolean newStep = true;

        String tempStr = ppt.GetStr(pointY - 1, pointX - 1);
        queueStep.offer(tempStr);
        while (queueStep.size() != 0) {
            tempStr = queueStep.peek();
            if (tempStr == null)
                break;
            realY = ppt.GetY(tempStr);
            realX = ppt.GetX(tempStr);

            newStep = (realY - 1 >= 0) ? ValidStep(realY - 1, realX) : false;
            if (newStep)
                StepAdd(realY, realX, realY - 1, realX);

            newStep = (realX + 1 < sizeX) ? ValidStep(realY, realX + 1) : false;
            if (newStep)
                StepAdd(realY, realX, realY, realX + 1);

            newStep = (realY + 1 < sizeY) ? ValidStep(realY + 1, realX) : false;
            if (newStep)
                StepAdd(realY, realX, realY + 1, realX);

            newStep = (realX - 1 >= 0) ? ValidStep(realY, realX - 1) : false;
            if (newStep)
                StepAdd(realY, realX, realY, realX - 1);

            queueStep.poll();
        }
    }

    static boolean ValidStep(int inY, int inX) {
        int temp = fieldWork[inY][inX];
        if (temp == 0 | temp == -2)
            return true;
        else
            return false;
    }

    static void StepAdd(int oldY, int oldX, int newY, int newX) {
        fieldWork[newY][newX] = fieldWork[oldY][oldX] + 1;
        String tempStr = ppt.GetStr(newY, newX);
        queueStep.offer(tempStr);
    }

    static boolean SetMainParam() {
        System.out.println("Введите размеры поля (M x N), где M и N целые числа [10, 100]:");
        sizeX = ValidField("M", 30);
        sizeY = ValidField("N", 30);
        System.out.println("Введите координаты объекта (X, Y) на этом поле, где X и Y целые числа:");
        pointX = ValidField("X", sizeX);
        pointY = ValidField("Y", sizeY);
        System.out.println("Введите координаты первого выхода (X, Y) на этом поле, где X и Y целые числа:");
        exitX1 = ValidField("X", sizeX);
        exitY1 = ValidField("Y", sizeY);
        System.out.println("Введите координаты второго выхода (X, Y) на этом поле, где X и Y целые числа:");
        exitX2 = ValidField("X", sizeX);
        exitY2 = ValidField("Y", sizeY);
        return true;
    }

    static int ValidField(String lit, int max) {
        int inNum = 0, min = 1;
        if (lit == "M" || lit == "N")
            min = 10;

        do {
            System.out.printf("%s в диапазоне от %d до %d: ", lit, min, max);
            while (!inScan.hasNextInt()) {
                System.out.printf("Пожалуйста, введите целое число от %d до %d.\n", min, max);
                System.out.print(lit + " = ");
                inScan.next();
            }
            inNum = inScan.nextInt();
            if (inNum < min || inNum > max)
                System.out.printf("Пожалуйста, введите целое число от %d до %d.\n", min, max);
        } while (inNum < min || inNum > max);

        return inNum;
    }

    static void SetField() {
        int[][] arr = new int[sizeY][sizeX];
        fieldWork = arr;
        arr = null;
        fieldWork[pointY - 1][pointX - 1] = 1;
        fieldWork[exitY1 - 1][exitX1 - 1] = -2;
        fieldWork[exitY2 - 1][exitX2 - 1] = -2;
        int rndNum = (int) (sizeX * sizeY * 0.2);
        RandStone(rndNum);
    }

    public static void PrintMass(int[][] arr) {
        for (int[] a : arr) {
            for (int item : a) {
                if (item == 0)
                    System.out.print(".\t");
                else if (item == -1)
                    System.out.print("X\t");
                else
                    System.out.print(item + "\t");
            }
            System.out.println();
        }
    }

    public static void PrintCharMass(char[][] arr) {
        for (char[] a : arr) {
            for (char item : a) {
                System.out.print(item + "\t");
            }
            System.out.println();
        }
    }

    static void RandStone(int num) {
        int xx = 0, yy = 0;
        Random rand = new Random();
        while (num > 0) {
            xx = rand.nextInt(sizeX);
            yy = rand.nextInt(sizeY);
            if (fieldWork[yy][xx] == 0) {
                fieldWork[yy][xx] = -1;
                num--;
            }
        }
    }

}