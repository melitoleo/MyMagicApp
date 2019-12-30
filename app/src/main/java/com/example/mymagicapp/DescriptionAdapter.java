package com.example.mymagicapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mymagicapp.dao.AccountDatabase;
import com.example.mymagicapp.domain.Type;

import java.util.List;

public class DescriptionAdapter extends RecyclerView.Adapter<DescriptionAdapter.MyViewHolder> {

    private List<Type> typeList;
    private Context context;
    private AccountDatabase database;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView typeDescription, accountCount;
        public ImageButton btnRemoveType;

        public MyViewHolder(View view){
            super(view);
            typeDescription = view.findViewById(R.id.txtRowType);
            accountCount = view.findViewById(R.id.txtAccountRowCount);
            btnRemoveType = view.findViewById(R.id.btnRemoveType);
        }
    }

    public DescriptionAdapter(Context context){

        database = AccountDatabase.getDatabase(context);

        this.typeList = database.typeDao().getTypeAggregate();
        this.context = context;
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

                    Intent intent = new Intent(view.getContext(), AccountTypeActivity.class);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }
}
