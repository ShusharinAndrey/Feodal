package com.example.feodal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

class Player {
    private final String mName;
    private final int[][] mTriangle = new int[4][3];
    private final int mPoint;
    private final TextView mTextAccount;
    private int mAccount = 0;

    public enum Corner {UPPER_LEFT, UPPER_RIGHT, LOWER_RIGHT, LOWER_LEFT}
    public enum Color {BLUE, RED, ORANGE, VIOLET}
    public enum PartTriangle {LEFT, CENTER, RIGHT}

    @SuppressLint("UseCompatLoadingForDrawables")
    public Player(Context context, String name, Color color, TextView textView) {
        this.mName = name;
        this.mTextAccount = textView;

        mPoint = getIdDrawable(context, "point_" + color.ordinal());

        for (int i = 0; i < Corner.values().length; i++) {
            for (int j = 0; j < PartTriangle.values().length; j++) {
                mTriangle[i][j] = getIdDrawable(context, "triangle_" + color.ordinal() + "_" + i + "_" + j);
            }
        }

    }

    public static int getIdDrawable(Context context, String name) {
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }

    public String getName() {
        return mName;
    }

    public int getPoint() {
        return mPoint;
    }

    public int getTriangle(@NotNull Corner corner, @NotNull PartTriangle partTriangle) {
        return mTriangle[corner.ordinal()][partTriangle.ordinal()];
    }

    public TextView getTextAccount() {
        return mTextAccount;
    }

    public void setAccount(int account) {
        this.mAccount = account;
        this.mTextAccount.setText(String.valueOf(account));
    }

    public int getAccount() {
        return mAccount;
    }
}
