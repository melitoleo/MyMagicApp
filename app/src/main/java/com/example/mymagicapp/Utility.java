package com.example.mymagicapp;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

public class Utility {

        public static GradientDrawable setRounded(String hexColor){
            GradientDrawable gdDefault = new GradientDrawable();
            gdDefault.setColor(Color.parseColor(hexColor));
            gdDefault.setCornerRadius(15);
            gdDefault.setStroke(15, Color.parseColor(hexColor));

            return  gdDefault;
        }
}
