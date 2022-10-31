package com.example.mydiet.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mydiet.Models.Step;
import com.example.mydiet.R;

import java.util.List;
import java.util.Objects;

public class InstructionStepAdapter extends RecyclerView.Adapter<InstructionStepViewHolder>{
    Context context;
    List<Step> list;

    public InstructionStepAdapter (Context context, List<Step> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public InstructionStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstructionStepViewHolder(LayoutInflater.from(context).
                inflate(R.layout.list_instructions_steps, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionStepViewHolder holder, int position) {
        holder.instructions_step_number.setText(String.valueOf(list.get(position).number));
        holder.instructions_step_title.setText(list.get(position).step);
        holder.instructions_ingredients.setHasFixedSize(true);
        holder.instructions_ingredients.setLayoutManager(new LinearLayoutManager
                (context, LinearLayoutManager.HORIZONTAL, false));
        InstructionsIngredientsAdapter instructionsIngredientsAdapter = new InstructionsIngredientsAdapter
                (context, list.get(position).ingredients);
        holder.instructions_ingredients.setAdapter(instructionsIngredientsAdapter);
        holder.instructions_equipments.setHasFixedSize(true);
        holder.instructions_equipments.setLayoutManager(new LinearLayoutManager
                (context, LinearLayoutManager.HORIZONTAL, false));
        InstructionsEquipmentsAdapter instructionsEquipmentsAdapter = new InstructionsEquipmentsAdapter
                (context, list.get(position).equipment);
        holder.instructions_equipments.setAdapter(instructionsEquipmentsAdapter);
    }

    @Override
    public int getItemCount() {

        return list.size();
    }
}
class InstructionStepViewHolder extends RecyclerView.ViewHolder{
    TextView instructions_step_number, instructions_step_title;
    RecyclerView instructions_equipments, instructions_ingredients;

    public InstructionStepViewHolder(@NonNull View itemView) {
        super(itemView);
        instructions_step_number = itemView.findViewById(R.id.instructions_step_number);
        instructions_step_title = itemView.findViewById(R.id.instructions_step_title);
        instructions_equipments = itemView.findViewById(R.id.instructions_equipments);
        instructions_ingredients = itemView.findViewById(R.id.instructions_ingredients);
    }
}
