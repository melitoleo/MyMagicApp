package com.example.mymagicapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.Button;

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
}
