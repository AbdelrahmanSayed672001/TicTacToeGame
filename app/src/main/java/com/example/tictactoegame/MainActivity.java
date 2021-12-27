package com.example.tictactoegame;

import androidx.appcompat.app.AppCompatActivity;

//import android.annotation.SuppressLint;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView playerStatus;
    private Button[] buttons=new Button[9];

    private int rountCount;
    private boolean activePlayer;
    private int[] gameState={2,2,2,2,2,2,2,2,2};
    private int[][] winningPositions={
            {0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},
            {0,4,8},{2,4,6}
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerStatus=findViewById(R.id.playerStatus);

        for (int i=0;i<buttons.length;i++){
            String buttonID="btn_"+ i;
            int resourceID=getResources().getIdentifier(buttonID,"id",getPackageName());
            buttons[i]=findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }

        rountCount=0;
        activePlayer=true;

    }

    @Override
    public void onClick(View v) {

        if (!((Button)v).getText().toString().equals("")){
            return;
        }
        String buttonID=v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer= Integer.parseInt(buttonID.substring(buttonID.length()-1,buttonID.length()));

        if (activePlayer){
            ((Button)v).setText("X");
            ((Button)v).setTextColor(Color.parseColor("#FDE50F"));
            gameState[gameStatePointer]=0;
        }else {
            ((Button)v).setText("O");
            ((Button)v).setTextColor(Color.parseColor("#FFE40606"));
            gameState[gameStatePointer]=1;
        }
        rountCount++;

        if (checkWinner()){
            if (activePlayer) {
                playerStatus.setText("It’s player 1");
                playerStatus.setTextColor(Color.parseColor("#FDE50F"));
                playAgain();
            }
            else {
                playerStatus.setText("It’s player 2");
                playerStatus.setTextColor(Color.parseColor("#FFE40606"));
                playAgain();
            }
        }else if (rountCount==9){
            playerStatus.setText("It’s a Draw");
            playerStatus.setTextColor(Color.WHITE);
            playAgain();
        }
        else {
            activePlayer= !activePlayer;
        }

    }

    public boolean checkWinner(){
        boolean winnerResult=false;

        for (int[] winningPose: winningPositions){
            if (gameState[winningPose[0]]==gameState[winningPose[1]] &&
                    gameState[winningPose[1]]==gameState[winningPose[2]] && gameState[winningPose[0]]!=2){
                winnerResult=true;
            }
        }
        return winnerResult;
    }

    public void playAgain(){
        rountCount=0;
        activePlayer=true;

        for (int i=0;i<buttons.length;i++){
            gameState[i]=2;
            buttons[i].setText("");
        }
    }
}