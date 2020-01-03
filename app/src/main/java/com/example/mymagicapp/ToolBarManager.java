package com.example.mymagicapp;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

public class ToolBarManager {

    public static void Setting(final Context context, Toolbar toolbar, String title,  final String type, final Class activity) {

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitle(title);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, activity);

                if(type != null)
                    intent.putExtra("type",type);

                context.startActivity(intent);
            }
        });
    }
}
