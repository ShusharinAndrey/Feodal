package com.example.feodal;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.feodal.MainActivity.hideNavigationBar;

public class ParametersGame extends AppCompatActivity implements
        View.OnClickListener {

    static final String EXTRA_QUANTITY_PLAYERS = "QUANTITY_PLAYERS";
    static final String EXTRA_QUANTITY_COLUMNS = "QUANTITY_COLUMNS";

    private TextView mNumberPlayersText, mNumberColumnsText;
    private int mQuantityPlayers = 2;
    private int mQuantityColumns = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameters_game);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        openOptionsMenu();
        hideNavigationBar(getWindow());

        findViewById(R.id.create).setOnClickListener(this);

        SeekBar numberPlayersBar = findViewById(R.id.numberPlayersBar);
        numberPlayersBar.setMin(2);
        numberPlayersBar.setMax(4);


        SeekBar numberColumnsBar = findViewById(R.id.numberColumnsBar);
        numberColumnsBar.setMin(TableViewer.MIN_QUANTITY_POINTS);
        numberColumnsBar.setMax(TableViewer.MAX_QUANTITY_POINTS);
        numberColumnsBar.setProgress(mQuantityColumns, false);

        mNumberPlayersText = findViewById(R.id.numberPlayers);
        mNumberColumnsText = findViewById(R.id.numberColons);

        numberPlayersBar.setOnSeekBarChangeListener(numberPlayersBarChangeListener);
        numberColumnsBar.setOnSeekBarChangeListener(numberColumnsBarChangeListener);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        intent = new Intent(ParametersGame.this, Field.class);
        intent.putExtra(EXTRA_QUANTITY_PLAYERS, mQuantityPlayers)
                .putExtra(EXTRA_QUANTITY_COLUMNS, mQuantityColumns);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
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

    private final SeekBar.OnSeekBarChangeListener numberPlayersBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            mQuantityPlayers = progress;
            mNumberPlayersText.setText(String.valueOf(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
    private final SeekBar.OnSeekBarChangeListener numberColumnsBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            mQuantityColumns = progress;
            mNumberColumnsText.setText(String.valueOf(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

}