package com.example.feodal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Arrays;

import static com.example.feodal.Field.mPlayers;

public class TableViewer extends TableLayout {
    public TextView sMotionText;
    public ImageView sMotion;
    public final static int MIN_QUANTITY_POINTS = 9;
    public final static int MAX_QUANTITY_POINTS = 100;
    private final static int QUANTITY_LAYER = 4;
    private final GestureDetector gestureDetector;
    private final ScaleGestureDetector scaleGestureDetector;
    private final Scroller scroller;
    public Pole pole;
    public int mNumberPlayer = 0;
    public int mQuantityPlayers = 2;
    public int mNumberStroke = 1;
    public ImageView[][] mPointViews;
    public int[][][] mTriangle;
    Context context;
    private float mScale = 1;
    private float mScaleMax = (float) 2.77778;

    public TableViewer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        gestureDetector = new GestureDetector(context, new MyGestureListener());
        scroller = new Scroller(context);
        scaleGestureDetector = new ScaleGestureDetector(context, new MyScaleGestureListener());

        // init scrollbars
        setHorizontalScrollBarEnabled(true);
        setVerticalScrollBarEnabled(true);
        setShrinkAllColumns(true);
        setStretchAllColumns(true);
        awakenScrollBars(scroller.getDuration());
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getPointerCount() == 1) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
            }
        }
        scaleGestureDetector.onTouchEvent(event);
        gestureDetector.onTouchEvent(event);
        return true;
    }

    public int convertCoordinate(int length, float point, int scroll) {
        int coordinate = (int) (((point + scroll) / (length / pole.getQuantityPoints())));
        if (coordinate >= 0 && coordinate < pole.getQuantityPoints()) return coordinate;
        return -1;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void create(int quantityPlayers, int quantityPoints) {
        mQuantityPlayers = quantityPlayers;
        pole = new Pole(quantityPoints);

        mPointViews = new ImageView[pole.getQuantityPoints()][pole.getQuantityPoints()];
        mTriangle = new int[QUANTITY_LAYER][pole.getQuantityPoints()][pole.getQuantityPoints()];

        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1;

        for (int y = 0; y < pole.getQuantityPoints(); y++) {

            TableRow tableRow = new TableRow(context);
            tableRow.setLayoutParams(layoutParams);
            for (int x = 0; x < pole.getQuantityPoints(); x++) {

                ImageView imageView = new ImageView(context);
                imageView.setAdjustViewBounds(true);
                imageView.setImageResource(R.drawable.pole0);
                imageView.setBackgroundResource(R.drawable.point_);
                pole.setPoint(y, x, R.drawable.point_);

                mPointViews[y][x] = imageView;

                tableRow.addView(imageView);
            }
            for (int i = 0; i < QUANTITY_LAYER; i++) {
                Arrays.fill(mTriangle[i][y], R.drawable.transperent);
            }

            addView(tableRow, y);
        }

        fillTable();

        mScaleMax = (float) (pole.getQuantityPoints() * 1.0 / MIN_QUANTITY_POINTS);
    }

    private void fillTable() {
        for (int y = 1; y < pole.getQuantityPoints() - 1; y++) {
            mPointViews[y][0].setImageResource(R.drawable.pole1);
            mPointViews[y][pole.getQuantityPoints() - 1].setImageResource(R.drawable.pole3);
        }
        for (int x = 1; x < pole.getQuantityPoints() - 1; x++) {
            mPointViews[0][x].setImageResource(R.drawable.pole2);
            mPointViews[pole.getQuantityPoints() - 1][x].setImageResource(R.drawable.pole4);
        }
        mPointViews[0][0].setImageResource(R.drawable.pole6);
        mPointViews[pole.getQuantityPoints() - 1][0].setImageResource(R.drawable.pole5);
        mPointViews[0][pole.getQuantityPoints() - 1].setImageResource(R.drawable.pole7);
        mPointViews[pole.getQuantityPoints() - 1][pole.getQuantityPoints() - 1].setImageResource(R.drawable.pole8);

    }

    public void fillTriangle(Player player) {

        int[] number = new int[mQuantityPlayers];

        IDTrianglePart idTrianglePart = new IDTrianglePart(player);

        for (int y = pole.getMinY(); y < pole.getMaxY(); y++) {
            for (int x = pole.getMinX(); x < pole.getMaxX(); x++) {

                idTrianglePart.setXY(y, x);

                if (pole.isOurPoint(y, x, player.getPoint())) {
                    if (pole.getPointFigures(y, x) == pole.getPointFigures(y + 1, x)
                            && pole.getPointFigures(y, x) == pole.getPointFigures(y, x + 1)
                            && pole.getPointFigures(y, x) == pole.getPointFigures(y + 1, x + 1)) {

                        setTriangle(idTrianglePart.invoke(Player.Corner.UPPER_LEFT, Player.PartTriangle.CENTER));
                        setTriangle(idTrianglePart.invoke(Player.Corner.UPPER_RIGHT, Player.PartTriangle.CENTER));
                        setTriangle(idTrianglePart.invoke(Player.Corner.LOWER_RIGHT, Player.PartTriangle.CENTER));
                        setTriangle(idTrianglePart.invoke(Player.Corner.LOWER_LEFT, Player.PartTriangle.CENTER));

                    } else if (pole.getPointFigures(y, x) == pole.getPointFigures(y + 1, x)
                            && pole.getPointFigures(y, x) == pole.getPointFigures(y, x + 1)) {

                        setTriangle(idTrianglePart.invoke(Player.Corner.UPPER_RIGHT, Player.PartTriangle.LEFT));
                        setTriangle(idTrianglePart.invoke(Player.Corner.LOWER_RIGHT, Player.PartTriangle.CENTER));
                        setTriangle(idTrianglePart.invoke(Player.Corner.LOWER_LEFT, Player.PartTriangle.RIGHT));

                    } else if (pole.getPointFigures(y, x) == pole.getPointFigures(y + 1, x)
                            && pole.getPointFigures(y, x) == pole.getPointFigures(y + 1, x + 1)) {

                        setTriangle(idTrianglePart.invoke(Player.Corner.UPPER_LEFT, Player.PartTriangle.LEFT));
                        setTriangle(idTrianglePart.invoke(Player.Corner.UPPER_RIGHT, Player.PartTriangle.CENTER));
                        setTriangle(idTrianglePart.invoke(Player.Corner.LOWER_RIGHT, Player.PartTriangle.RIGHT));

                    } else if (pole.getPointFigures(y, x) == pole.getPointFigures(y + 1, x + 1)
                            && pole.getPointFigures(y, x) == pole.getPointFigures(y, x + 1)) {

                        setTriangle(idTrianglePart.invoke(Player.Corner.UPPER_LEFT, Player.PartTriangle.RIGHT));
                        setTriangle(idTrianglePart.invoke(Player.Corner.LOWER_RIGHT, Player.PartTriangle.LEFT));
                        setTriangle(idTrianglePart.invoke(Player.Corner.LOWER_LEFT, Player.PartTriangle.CENTER));

                    }
                }

                if (pole.getPointFigures(y + 1, x + 1) == pole.getPointFigures(y + 1, x)
                        && pole.getPointFigures(y + 1, x + 1) == pole.getPointFigures(y, x + 1)
                        && pole.getPointFigures(y + 1, x + 1) != pole.getPointFigures(y, x)
                        && pole.isOurPoint(y + 1, x + 1, player.getPoint())) {

                    setTriangle(idTrianglePart.invoke(Player.Corner.UPPER_LEFT, Player.PartTriangle.CENTER));
                    setTriangle(idTrianglePart.invoke(Player.Corner.UPPER_RIGHT, Player.PartTriangle.RIGHT));
                    setTriangle(idTrianglePart.invoke(Player.Corner.LOWER_LEFT, Player.PartTriangle.LEFT));
                }

                calculate(y, x, number);
            }
        }

        for (int numberPlayer = 0; numberPlayer < mQuantityPlayers; numberPlayer++) {
            mPlayers[numberPlayer].setAccount(number[numberPlayer]);
        }
    }

    private void setTriangle(IDTrianglePart idTrianglePart) {
        if (!idTrianglePart.compare(mTriangle)) {
            idTrianglePart.putInTriangle(mTriangle);
            setPointForeground(idTrianglePart.getY(), idTrianglePart.getX());
        }
    }

    private void calculate(int y, int x, int[] number) {
        for (int numberPlayer = 0; numberPlayer < mQuantityPlayers; numberPlayer++) {

            Player player = mPlayers[numberPlayer];
            IDTrianglePart idTrianglePart = new IDTrianglePart(player, y, x);

            if (idTrianglePart.invoke(Player.Corner.UPPER_LEFT, Player.PartTriangle.CENTER).compare(mTriangle) && idTrianglePart.invoke(Player.Corner.UPPER_RIGHT, Player.PartTriangle.CENTER).compare(mTriangle)) {
                number[numberPlayer] += 2;
            } else if (
                    idTrianglePart.invoke(Player.Corner.UPPER_LEFT, Player.PartTriangle.LEFT).compare(mTriangle) && idTrianglePart.invoke(Player.Corner.UPPER_RIGHT, Player.PartTriangle.CENTER).compare(mTriangle)
                            || idTrianglePart.invoke(Player.Corner.UPPER_RIGHT, Player.PartTriangle.LEFT).compare(mTriangle) && idTrianglePart.invoke(Player.Corner.LOWER_RIGHT, Player.PartTriangle.CENTER).compare(mTriangle)
                            || idTrianglePart.invoke(Player.Corner.LOWER_RIGHT, Player.PartTriangle.LEFT).compare(mTriangle) && idTrianglePart.invoke(Player.Corner.LOWER_LEFT, Player.PartTriangle.CENTER).compare(mTriangle)
                            || idTrianglePart.invoke(Player.Corner.LOWER_LEFT, Player.PartTriangle.LEFT).compare(mTriangle) && idTrianglePart.invoke(Player.Corner.UPPER_LEFT, Player.PartTriangle.CENTER).compare(mTriangle)) {
                number[numberPlayer]++;
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setPointForeground(int y, int x) {
        mPointViews[y][x].setForeground(new LayerDrawable(new Drawable[]{
                getResources().getDrawable(mTriangle[0][y][x], context.getTheme()),
                getResources().getDrawable(mTriangle[1][y][x], context.getTheme()),
                getResources().getDrawable(mTriangle[2][y][x], context.getTheme()),
                getResources().getDrawable(mTriangle[3][y][x], context.getTheme())}));
    }

    public void check(int y, int x) {
        if (pole.getPointFigures(y, x) == R.drawable.point_) {

            pole.updateRange(y, x);

            int count = 0;

            Player player = mPlayers[mNumberPlayer];

            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (pole.isOurPoint(y + i, x + j, player.getPoint())) {
                        count++;
                    }
                }
            }

            setPoint(y, x, player.getPoint());

            if (count > 1) {
                pole.checkTriangles(player.getPoint());
                fillTriangle(player);
            }
            nextPlayer();
        }
    }

    private void nextPlayer() {

        mNumberPlayer++;

        if (mNumberPlayer == mQuantityPlayers) {
            mNumberPlayer = 0;
        }
        mNumberStroke++;

        showMotion();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setPointBackground(int y, int x) {
        mPointViews[y][x].setBackgroundResource(pole.getPoint(y, x));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void fillPaint() {
        for (int y = 0; y < pole.getQuantityPoints(); y++) {
            for (int x = 0; x < pole.getQuantityPoints(); x++) {
                setPointBackground(y, x);
                setPointForeground(y, x);
            }
        }
        showMotion();
    }

    private void setPoint(int y, int x, int point) {
        pole.setPoint(y, x, point);
        setPointBackground(y, x);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void showMotion() {
        sMotion.setBackground(getResources().getDrawable(mPlayers[mNumberPlayer].getPoint(), context.getTheme()));
        sMotionText.setText(context.getString(R.string.motion_text, String.valueOf(mNumberStroke), mPlayers[mNumberPlayer].getName()));
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            int oldX = getScrollX();
            int oldY = getScrollY();
            int x = scroller.getCurrX();
            int y = scroller.getCurrY();
            scrollTo(x, y);
            if (oldX != getScrollX() || oldY != getScrollY()) {
                onScrollChanged(getScrollX(), getScrollY(), oldX, oldY);
            }

            postInvalidate();
        }
    }

    @Override
    protected int computeVerticalScrollRange() {
        return getMaxScroll(getHeight());
    }

    @Override
    protected int computeHorizontalScrollRange() {
        return getMaxScroll(getWidth());
    }

    private int getMaxScroll(int length) {
        return (int) (length * (1 - (1 / mScale)) / 2);
    }

    private static class IDTrianglePart {
        private final Player player;
        private Player.Corner corner;
        private int y = 0;
        private int x = 0;
        private int yElementary;
        private int xElementary;
        private int drawableID;

        private IDTrianglePart(Player player, int y, int x) {
            this.player = player;
            this.yElementary = y;
            this.xElementary = x;
        }

        private IDTrianglePart(Player player) {
            this.player = player;
        }

        private void setXY(int y, int x) {
            this.yElementary = y;
            this.xElementary = x;
        }

        private int getY() {
            return y;
        }

        private int getX() {
            return x;
        }

        private boolean compare(int[][][] triangle) {
            return triangle[corner.ordinal()][y][x] == drawableID;
        }

        private void putInTriangle(int[][][] triangle) {
            triangle[corner.ordinal()][y][x] = drawableID;
        }

        private IDTrianglePart invoke(Player.Corner corner, Player.PartTriangle part) {
            this.corner = corner;
            y = yElementary;
            x = xElementary;
            switch (corner) {
                case UPPER_LEFT:
                    y++;
                    x++;
                    break;
                case UPPER_RIGHT:
                    y++;
                    break;
                case LOWER_LEFT:
                    x++;
                    break;
            }

            drawableID = player.getTriangle(corner, part);
            return this;
        }
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent event) {
            int x = convertCoordinate(getHeight(), event.getX(), getScrollX());
            int y = convertCoordinate(getHeight(), event.getY(), getScrollY());
            if (y != -1 && x != -1) {
                check(y, x);
                return true;
            }
            return super.onSingleTapConfirmed(event);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            int maxScrollHeight = (int) (getMaxScroll(getHeight()));
            int maxScrollWidth = (int) (getMaxScroll(getWidth()));

            if (getScrollX() + distanceX < -maxScrollWidth) {
                distanceX = -maxScrollWidth - getScrollX();
            } else if (getScrollX() + distanceX > maxScrollWidth) {
                distanceX = maxScrollWidth - getScrollX();
            }

            if (getScrollY() + distanceY < -maxScrollHeight) {
                distanceY = -maxScrollHeight - getScrollY();
            } else if (getScrollY() + distanceY > maxScrollHeight) {
                distanceY = maxScrollHeight - getScrollY();
            }

            scrollBy((int) distanceX, (int) distanceY);
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            int maxScrollHeight = getMaxScroll(getHeight());
            int maxScrollWidth = getMaxScroll(getWidth());

            boolean scrollBeyondImage = ((getScrollX() < -maxScrollWidth) || (getScrollX() > maxScrollWidth) || (getScrollY() < -maxScrollHeight) || (getScrollY() > maxScrollHeight));
            if (scrollBeyondImage) return false;

            scroller.fling(getScrollX(), getScrollY(), -(int) velocityX, -(int) velocityY, -maxScrollWidth, maxScrollWidth, -maxScrollHeight, maxScrollHeight);
            awakenScrollBars(scroller.getDuration());

            return true;
        }

    }

    private class MyScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        public boolean onScale(ScaleGestureDetector detector) {
            float dxy = (detector.getScaleFactor() - 1) * (mScaleMax + mScale) / 10;
            if (Math.abs(dxy) > 0.001 && Math.abs(dxy) < mScaleMax / 10) {
                mScale += dxy;

                if (mScale > mScaleMax) {
                    mScale = mScaleMax;
                } else if (mScale < 1) {
                    mScale = 1;
                }

                if (getScaleX() != mScale) {
                    setScaleX(mScale);
                    setScaleY(mScale);

                    if ((detector.getScaleFactor() - 1) < 0) {
                        int maxScrollHeight = getMaxScroll(getHeight());
                        int maxScrollWidth = getMaxScroll(getWidth());

                        int newScrollX = getScrollX();
                        int newScrollY = getScrollY();

                        if (getScrollY() > maxScrollHeight) {
                            newScrollY = maxScrollHeight;
                        } else if (getScrollY() < -maxScrollHeight) {
                            newScrollY = -maxScrollHeight;
                        }

                        if (getScrollX() > maxScrollWidth) {
                            newScrollX = maxScrollWidth;
                        } else if (getScrollX() < -maxScrollWidth) {
                            newScrollX = -maxScrollWidth;
                        }

                        scrollTo(newScrollX, newScrollY);

                    }
                }
            }
            return true;
        }

        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        public void onScaleEnd(ScaleGestureDetector detector) {

        }
    }
}


