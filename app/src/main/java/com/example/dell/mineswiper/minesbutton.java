package com.example.dell.mineswiper;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;

public class minesbutton extends AppCompatButton {

    int n;
    public int i,j;
    boolean isFlagged,isRevealed;

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public minesbutton(Context context) {
        super(context);
        isFlagged=false;
        isRevealed = false;

    }

    public void setValue(int value){
        n= value;
    }

    public int  getValue(){
        return n;
    }

    public void coordinates(int i, int j){
        this.i=i;
        this.j=j;
    }

}
