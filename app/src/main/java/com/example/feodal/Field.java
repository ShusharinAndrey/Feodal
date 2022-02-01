package com.example.feodal;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.feodal.MainActivity.hideNavigationBar;


public class Field extends AppCompatActivity {
    public static Player[] mPlayers;
    private TableViewer mTableLayout;

    @SuppressLint({"ClickableViewAccessibility", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        openOptionsMenu();
        hideNavigationBar(getWindow());

        mTableLayout = findViewById(R.id.table);
        mTableLayout.sMotion = findViewById(R.id.motion);
        mTableLayout.sMotionText = findViewById(R.id.text_motion);

        int quantityPlayers = getIntent().getIntExtra(ParametersGame.EXTRA_QUANTITY_PLAYERS, 2);
        int quantityPoints = getIntent().getIntExtra(ParametersGame.EXTRA_QUANTITY_COLUMNS, 25);

        mTableLayout.create(quantityPlayers, quantityPoints);

        mPlayers = new Player[quantityPlayers];
        View[] mAccounts = new View[quantityPlayers];

        mPlayers[0] = new Player(getApplicationContext(), "Игрок1", Player.Color.BLUE, findViewById(R.id.text_account1));
        mPlayers[1] = new Player(getApplicationContext(), "Игрок2", Player.Color.RED, findViewById(R.id.text_account2));


        mAccounts[0] = findViewById(R.id.account1);
        mAccounts[1] = findViewById(R.id.account2);

        if (quantityPlayers > 2) {
            mAccounts[2] = findViewById(R.id.account3);
            mAccounts[2].setVisibility(View.VISIBLE);

            mPlayers[2] = new Player(getApplicationContext(), "Игрок3", Player.Color.ORANGE, findViewById(R.id.text_account3));
            mPlayers[2].getTextAccount().setVisibility(View.VISIBLE);

            if (quantityPlayers > 3) {
                mAccounts[3] = findViewById(R.id.account4);
                mAccounts[3].setVisibility(View.VISIBLE);

                mPlayers[3] = new Player(getApplicationContext(), "Игрок4", Player.Color.VIOLET, findViewById(R.id.text_account4));
                mPlayers[3].getTextAccount().setVisibility(View.VISIBLE);

            }
        }

        for (int i = 0; i < quantityPlayers; i++) {
            mAccounts[i].setBackground(getResources().getDrawable(mPlayers[i].getPoint(), getTheme()));
        }



    }

    @Override
    protected void onStart() {
        super.onStart();
        mTableLayout.fillPaint();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(0, 0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
