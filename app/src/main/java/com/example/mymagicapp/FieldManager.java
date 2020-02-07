package com.example.mymagicapp;

import android.content.Context;
import android.widget.EditText;

import java.util.List;

public class FieldManager {

    public static boolean CheckFieldRequired(Context context, List<EditText> fields){
        int check = 0;

        for (EditText field:fields) {
            if(field.getText().toString().trim().isEmpty()) {
                field.setError(context.getString(R.string.required));
                check++;
            }
        }
        return check > 0;
    }
}
