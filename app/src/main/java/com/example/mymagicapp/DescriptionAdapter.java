package com.example.mymagicapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymagicapp.dao.AccountDatabase;
import com.example.mymagicapp.domain.Rgb;
import com.example.mymagicapp.domain.Type;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

import java.util.List;

public class DescriptionAdapter extends RecyclerView.Adapter<DescriptionAdapter.MyViewHolder> {

    private List<Type> typeList;
    private Context context;
    private AccountDatabase database;
    private Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView typeDescription, accountCount;
        public ImageButton btnOpenColorPicker, btnRemoveType;

        public MyViewHolder(View view){
            super(view);
            typeDescription = view.findViewById(R.id.txtRowType);
            accountCount = view.findViewById(R.id.txtAccountRowCount);
            btnOpenColorPicker = view.findViewById(R.id.btnOpenColorPicker);
            btnRemoveType = view.findViewById(R.id.btnRemoveType);

            Utility.setApplicationImgButton(view.getContext(), btnRemoveType);
        }
    }

    public DescriptionAdapter(Context context, Activity activity){

        database = AccountDatabase.getDatabase(context);

        this.typeList = database.typeDao().getTypeAggregate();
        this.context = context;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.description_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Type type = typeList.get(position);
        holder.typeDescription.setText(type.getDescription());
        holder.btnOpenColorPicker.setBackgroundColor(Color.parseColor(type.getHexcolor()));
        holder.accountCount.setText(String.format("Account associati: %s", String.valueOf(type.getAccountCount())));

        holder.btnRemoveType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type.getAccountCount() > 0)
                {
                    Toast.makeText(context, String.format("Ci sono %d associati a questa categoria", type.getAccountCount()), Toast.LENGTH_LONG).show();
                }
                else {
                    database.typeDao().deleteByDescription(type.getDescription());
                    Toast.makeText(context, String.format("Categoria %s eliminata", type.getDescription()), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    context.startActivity(intent);
                }
            }
        });

        holder.btnOpenColorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker(type);
            }
        });
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }

    private void openColorPicker(final Type type){
        Rgb rgb = new Rgb(type.getHexcolor());

        final ColorPicker cp = new ColorPicker(activity, rgb.Red, rgb.Green, rgb.Blue);

        cp.show();

        cp.enableAutoClose();

        cp.setCallback(new ColorPickerCallback() {
            @Override
            public void onColorChosen(@ColorInt int color) {
                type.hexcolor= String.format("#%06X", (0xFFFFFF & color));
                database.typeDao().update(type);
            }
        });
    }
}
