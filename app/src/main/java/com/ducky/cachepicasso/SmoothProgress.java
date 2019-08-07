package com.ducky.cachepicasso;


import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;

import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ProgressBar;

public class SmoothProgress {

    private static final int UPER_LIMIT = 96;
    private static final int LOW1_LIMIT = 95;
    private static final int LOW_LIMIT =90;
    private static final int INCREASE_MANUFATUAL = 1;
    private ProgressBar mProgressBar;
    private ValueAnimator valueAnimator;

    public SmoothProgress(View progressbar) {
        this.mProgressBar = (ProgressBar)progressbar;
    }

    public void setProgressByAnimation(int progress){
        if(valueAnimator == null) {
            valueAnimator = new ValueAnimator();
        }
        int to = getSmooth(progress);
        int time = getAnimateTime(progress);
        if(valueAnimator.isRunning()){
            int mvalue = (Integer)valueAnimator.getAnimatedValue();
            valueAnimator.cancel();
            valueAnimator.setDuration(time);
            valueAnimator.setIntValues(mvalue, to);
            valueAnimator.start();
        }else {
            valueAnimator.setDuration(time);
            valueAnimator.setIntValues(mProgressBar.getProgress(), getSmooth(progress));
            valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            valueAnimator.start();
        }

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (Integer)valueAnimator.getAnimatedValue();
                Log.i("wq","animator value:"+value);
                mProgressBar.setProgress(value);
            }
        });
    }

    private int getAnimateTime(int progress) {
        if(progress<LOW_LIMIT){
            return 4000;
        }else if(progress<LOW1_LIMIT){
            return 3000;
        }else if(progress<UPER_LIMIT){
            return 2000;
        }else {
            return 1000;
        }
    }


    /**
     * 通过插值办法平滑离散进度值
     * @param progress 当前进度设置
     */
    public void setProgressBySmoothValue(int progress) {
        if(mProgressBar.getVisibility() ==  View.VISIBLE){
            int gto = getSmooth(progress);

            while (progress < gto){
                progress += INCREASE_MANUFATUAL;
                mProgressBar.setProgress(progress);
            }
        }
    }

    private int getSmooth(int progress){

        if(progress<=LOW_LIMIT){
            return LOW_LIMIT;
        }else if(progress<=LOW1_LIMIT){
            return  UPER_LIMIT;
        }else if(progress<=UPER_LIMIT){
            return  UPER_LIMIT;
        }else {
            return 100;
        }
    }

    /**
     * 隐藏当前进度条
     */
    public void hideProgress(){
        if(mProgressBar.getVisibility() == View.VISIBLE){
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }
}
