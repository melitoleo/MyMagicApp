package com.example.mymagicapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.Random;

public class Utility {
    public static GradientDrawable setRounded(String hexColor){
            GradientDrawable gdDefault = new GradientDrawable();
            gdDefault.setColor(Color.parseColor(hexColor));
            gdDefault.setCornerRadius(15);
            gdDefault.setStroke(15, Color.parseColor(hexColor));

            return  gdDefault;
        }

    public static void setApplicationButton(Context context, Button button){
            String hexColor = context.getString(R.string.colorPrimary_text);

            GradientDrawable gdDefault = new GradientDrawable();
            gdDefault.setColor(Color.parseColor(hexColor));
            gdDefault.setCornerRadius(15);
            gdDefault.setStroke(15, Color.parseColor(hexColor));

            button.setTextColor(Color.parseColor(context.getString(R.string.colorText_text)));
            button.setBackground(gdDefault);
        }

    public static void setApplicationImgButton(ImageButton button, String hexColor){

        GradientDrawable gdDefault = new GradientDrawable();
        gdDefault.setColor(Color.parseColor(hexColor));
        gdDefault.setCornerRadius(15);
        gdDefault.setStroke(15, Color.parseColor(hexColor));

        button.setBackground(gdDefault);
    }

    public static String getRandomHexColor(){
        Random obj = new Random();
        int rand_num = obj.nextInt(0xffffff + 1);

        String hexColor = String.format("#%06x", rand_num);

        if(hexColor == "#006494")
            getRandomHexColor();

        return hexColor;
    }
}
