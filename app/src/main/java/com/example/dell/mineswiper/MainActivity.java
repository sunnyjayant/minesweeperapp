package com.example.dell.mineswiper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener {

    LinearLayout rootlayout;

    public int SIZE=5;
    public ArrayList<LinearLayout> rows;
    public minesbutton[][] board;

    Random random_number=new Random();
    int x[]={-1,-1,-1,0,1,1,1,0};
    int y[]={-1,0,1,1,1,0,-1,-1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootlayout =findViewById(R.id.rootlayout);
        setUpBoard();
        setMines();
        setNeighbours();
    }

    public void setUpBoard(){

        rows =new ArrayList<>();
        board=new minesbutton[SIZE][SIZE];
        rootlayout.removeAllViews();
        for(int i=0;i<SIZE;i++){
            LinearLayout l=new LinearLayout(this);
            l.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
            l.setLayoutParams(lp);
            rootlayout.addView(l);
            rows.add(l);

        }

        for (int i = 0; i < SIZE; i++) {

            for (int j = 0; j < SIZE; j++) {
                minesbutton b = new minesbutton(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                b.setLayoutParams(lp);
                b.setOnClickListener(this);
                b.setOnLongClickListener(this);
                LinearLayout row = rows.get(i);
                row.addView(b);
                board[i][j] = b;
                board[i][j].setValue(0);
                board[i][j].coordinates(i,j);
            }

        }

    }

    @Override
    public void onClick(View view) {
        minesbutton button = (minesbutton) view;
        if (button.getValue() == -1) {
            Toast.makeText(this, "GAME OVER", Toast.LENGTH_LONG).show();
            showAll();
        } else if (button.isFlagged) {
            return;
        } else if (button.getValue() > 0) {
            button.setText(button.getValue() + "");
        } else if (button.getValue() == 0) {
            button.isRevealed = true;
            revealNeighbours(button);
        }

    }

    public void revealNeighbours(minesbutton button) {
        int i, j;
        i = button.i;
        j = button.j;
        button.isRevealed = true;
        for (int k = 0; k < 8; k++) {
            int X = i + x[k];
            int Y = j + y[k];
            if ((X >= 0 && X < SIZE) && (Y >= 0 && Y < SIZE)) {
                if (board[X][Y].getValue() > 0) {
                    board[X][Y].setText(board[X][Y].getValue() + "");
                    board[X][Y].isRevealed = true;
                } else if (board[X][Y].getValue() == 0 && !board[X][Y].isRevealed) {
                    revealNeighbours(board[X][Y]);
                }
            }
        }
    }

    public void setMines() {
        int mines = 10;
        for (int i = 1; i <= mines; i++) {
            int row = random_number.nextInt(SIZE);
            int col = random_number.nextInt(SIZE);

            board[row][col].setValue(-1);
            //board[row][col].setText("-1");
        }
    }
    public void setNeighbours() {

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j].getValue() == -1) {
                    for (int k = 0; k < 8; k++) {
                        int X = i + x[k];
                        int Y = j + y[k];
                        if ((X >= 0 && X < SIZE) && (Y >= 0 && Y < SIZE)) {
                            if (board[X][Y].getValue() != -1) {
                                int temp = board[X][Y].getValue();
                                temp++;
                                board[X][Y].setValue(temp);
                            }

                        }
                    }

                }
            }
        }
    }

    public void showAll() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j].setEnabled(false);
                if (board[i][j].getValue() == -1) {
                    board[i][j].setText("-1");
                }
            }
        }
    }

    @Override
    public boolean onLongClick(View view) {
        minesbutton button = (minesbutton) view;

        if (button.isFlagged()) {
            button.setFlagged(false);
            button.setText(button.getValue() + "");
        } else {
            button.setFlagged(true);
            button.setText("F");
        }
        return false;
    }



}
