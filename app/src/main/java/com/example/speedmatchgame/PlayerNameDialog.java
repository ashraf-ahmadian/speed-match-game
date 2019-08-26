package com.example.speedmatchgame;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PlayerNameDialog extends Dialog {

    private EditText playerName;
    private Button confirmName;

    private String name;

    PlayerNameListener nameListener;

    public PlayerNameDialog(Context context, PlayerNameListener nameListener) {
        super(context);
        this.nameListener = nameListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_name_dialog);
        findView();
        clickConfirmName();
    }

    private void findView(){
        playerName = findViewById(R.id.name_field);
        confirmName = findViewById(R.id.confirm_name);

    }
    private void clickConfirmName(){
        confirmName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = playerName.getText().toString();
                nameListener.getPlayerName(name);
                dismiss();
            }
        });

    }
}
