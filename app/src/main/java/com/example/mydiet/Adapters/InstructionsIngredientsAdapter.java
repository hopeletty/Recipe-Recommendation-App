package com.example.mydiet.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mydiet.Models.Ingredient;
import com.example.mydiet.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InstructionsIngredientsAdapter extends RecyclerView.Adapter<InstructionIngredientsViewHolder>{
    Context context;
    List<Ingredient> list;

    public InstructionsIngredientsAdapter(Context context, List<Ingredient> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public InstructionIngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstructionIngredientsViewHolder(LayoutInflater.from(context).inflate
                (R.layout.list_instruction_step_items, parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull InstructionIngredientsViewHolder holder, int position) {
        holder.tv_instructions_step_item.setText(list.get(position).name);
        holder.tv_instructions_step_item.setSelected(true);
        Picasso.get().load("https://spoonacular.com/cdn/ingredients_100x100/"+list.get(position).image).into(holder.instructions_step_items);

    }

    @Override
    public int getItemCount() {

        return list.size();
    }
}
class InstructionIngredientsViewHolder extends RecyclerView.ViewHolder{
    ImageView instructions_step_items;
    TextView tv_instructions_step_item;

    public InstructionIngredientsViewHolder(@NonNull View itemView) {
        super(itemView);
        instructions_step_items = itemView.findViewById(R.id.instructions_step_items);
        tv_instructions_step_item = itemView.findViewById(R.id.tv_instructions_step_item);

    }
}
