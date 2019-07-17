package com.belajar.myrecycleview;

import android.view.View;

public class CustomOnItemClickListener implements View.OnClickListener {

    private int posisition;
    private OnItemClickCallback onItemClickCallback;
    CustomOnItemClickListener(int posisition,OnItemClickCallback onItemClickCallback)
    {
        this.posisition = posisition ;
        this.onItemClickCallback = onItemClickCallback;
    }

    @Override
    public void onClick(View v) {
        onItemClickCallback.onItemClicked(v,posisition);
    }

    public interface  OnItemClickCallback{
        void onItemClicked(View view, int posisition);
    }

}
