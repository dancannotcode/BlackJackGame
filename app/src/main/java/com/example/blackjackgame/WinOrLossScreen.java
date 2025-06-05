package com.example.blackjackgame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WinOrLossScreen extends AppCompatActivity {
    Button game;
    TextView displayWins;
    TextView displayLoses;
    SharedPreferences settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_win_or_loss_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        settings = this.getSharedPreferences("game", 0);
        displayWins = findViewById(R.id.wins);
        displayLoses = findViewById(R.id.loses);
        game = findViewById(R.id.backToGame);

        int loses = settings.getInt("Loses", 0);
        int wins = settings.getInt("Wins", 0);
        //displays how many wins or loses the user has accumulated.
        displayLoses.setText(String.valueOf(loses));
        displayWins.setText(String.valueOf(wins));

        game.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), game.class);
            startActivity(intent);
        });
    }
}