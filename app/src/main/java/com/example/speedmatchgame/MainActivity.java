package com.example.speedmatchgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button startButton;
    private Button bestScoreBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        clickStartButton();
        clickBestBtn();
    }
    private void findView(){
        startButton = findViewById(R.id.start_btn);
        bestScoreBtn = findViewById(R.id.best_score_btn);
    }

    private void clickStartButton(){
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayerNameDialog dialog = new PlayerNameDialog(MainActivity.this, new PlayerNameListener() {
                    @Override
                    public void getPlayerName(String playerName) {
                        Bundle bundle = new Bundle();
                        bundle.putString("player_name", playerName);
                        GameFragment gameFragment = new GameFragment();
                        gameFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.game_container, gameFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                });
                dialog.show();

            }
        });

    }

    private void clickBestBtn(){
        bestScoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BestScoreFragment bestScoreFragment = new BestScoreFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.game_container, bestScoreFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }



}
