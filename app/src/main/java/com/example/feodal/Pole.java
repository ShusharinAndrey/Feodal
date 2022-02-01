package com.example.feodal;

public class Pole {

    private final int[][] mPoints;
    private final int[][] mPointsFigures;

    private final int mQuantityPoints;

    private int mMinX;
    private int mMinY;
    private int mMaxX = 0;
    private int mMaxY = 0;

    Pole(int quantityPoints) {
        mQuantityPoints = quantityPoints;
        mMinX = quantityPoints - 1;
        mMinY = quantityPoints - 1;

        mPoints = new int[mQuantityPoints][mQuantityPoints];
        mPointsFigures = new int[mQuantityPoints][mQuantityPoints];
    }

    int getPoint(int y, int x) {
        return mPoints[y][x];
    }

    void setPoint(int y, int x, int point) {
        mPoints[y][x] = point;
        mPointsFigures[y][x] = point;
    }

    int getPointFigures(int y, int x) {
        return mPointsFigures[y][x];
    }

    int getQuantityPoints() {
        return mQuantityPoints;
    }

    int getMinX() {
        return mMinX;
    }

    void updateRange(int y, int x) {

        if (x > mMaxX) {
            this.mMaxX = x;
        } else if (x < mMinX) {
            this.mMinX = x;
        }

        if (y > mMaxY) {
            this.mMaxY = y;
        } else if (y < mMinY) {
            this.mMinY = y;
        }

    }

    int getMinY() {
        return mMinY;
    }

    int getMaxX() {
        return mMaxX;
    }


    int getMaxY() {
        return mMaxY;
    }

    void checkTriangles(int point) {

        int sizeY = mMaxY - mMinY + 1;
        int sizeX = mMaxX - mMinX + 1;

        boolean[][] isPointNotNeedFill = new boolean[sizeY][sizeX];

        for (int y = 0; y < sizeY; y++) {
            isPointNotNeedFill[y][0] = !isOurPoint(y + mMinY, mMinX, point);
            isPointNotNeedFill[y][sizeX - 1] = !isOurPoint(y + mMinY, mMaxX, point);
        }

        for (int x = 0; x < sizeX; x++) {
            isPointNotNeedFill[0][x] = !isOurPoint(mMinY, x + mMinX, point);
            isPointNotNeedFill[sizeY - 1][x] = !isOurPoint(mMaxY, x + mMinX, point);
        }

        for (int y = 1; y < sizeY - 1; y++) {
            for (int x = 1; x < sizeX - 1; x++) {
                CheckPointNotNeedFill(isPointNotNeedFill, y, x, point);
                CheckPointNotNeedFill(isPointNotNeedFill, y, sizeX - x - 1, point);
                CheckPointNotNeedFill(isPointNotNeedFill, sizeY - y - 1, x, point);
                CheckPointNotNeedFill(isPointNotNeedFill, sizeY - y - 1, sizeX - x - 1, point);
            }
        }

        for (int y = mMinY + 1; y < mMaxY; y++) {
            for (int x = mMinX + 1; x < mMaxX; x++) {
                if (!isPointNotNeedFill[y - mMinY][x - mMinX] && !isOurPoint(y, x, point)) {
                    mPointsFigures[y][x] = point;
                }
            }
        }
    }

    private void CheckPointNotNeedFill(boolean[][] isPointNotNeedFill, int y, int x, int point) {
        if (!isPointNotNeedFill[y][x] && !isOurPoint(y + mMinY, x + mMinX, point)) {
            isPointNotNeedFill[y][x] = (isPointNotNeedFill[y - 1][x] || isPointNotNeedFill[y][x - 1]
                    || isPointNotNeedFill[y + 1][x] || isPointNotNeedFill[y][x + 1]);
        }
    }

    boolean isOurPoint(int y, int x, int point) {
        if (y < 0 || y >= mQuantityPoints || x < 0 || x >= mQuantityPoints) {
            return false;
        }
        return mPointsFigures[y][x] == point;
    }
}
