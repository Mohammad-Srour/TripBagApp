package com.example.trip_bag;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BigItemAdapter extends RecyclerView.Adapter<BigItemAdapter.ViewHolder> {


    private ArrayList<BagItem>bagItems;
    private MainActivity activity;

    public BigItemAdapter(ArrayList<BagItem> bagItems,MainActivity activity) {
        this.bagItems = bagItems;
        this.activity=activity;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
        return new ViewHolder(view);
    }
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BagItem bagItem=bagItems.get(position);
        holder.ItemName.setText(bagItem.getName());
        holder.ItemCategory.setText(bagItem.getCategory());
        holder.BagType.setText(bagItem.getBagType());
        holder.Quantity.setText("Qty: "+bagItem.getQuantity());
        holder.Date.setText("Reminder : "+bagItem.getReminderDate());
        if(bagItem.isPacked()){
            holder.IsPacked.setText("Packed: Yes");
        }else{
            holder.IsPacked.setText("Packed: No");
        }
        if(bagItem.isImportant()){
            holder.IsImportant.setVisibility(View.VISIBLE);
        }else{
            holder.IsImportant.setVisibility(View.INVISIBLE);
        }
        holder.btnDelete.setOnClickListener(v -> {
            int positon = holder.getAdapterPosition();
            if (positon != RecyclerView.NO_POSITION) {
                BagItem item = bagItems.get(positon);
                activity.removeBagItems(item);
            }
        });

        holder.btnEdit.setOnClickListener(v -> {
            int positon = holder.getAdapterPosition();
            if (positon != RecyclerView.NO_POSITION) {

                BagItem item = bagItems.get(positon);

                Intent intent = new Intent(v.getContext(), EditActivity.class);
                intent.putExtra("position", positon);
                intent.putExtra("name", item.getName());
                intent.putExtra("category", item.getCategory());
                intent.putExtra("quantity", item.getQuantity());
                intent.putExtra("date", item.getReminderDate());
                intent.putExtra("isPacked", item.isPacked());
                intent.putExtra("isImportant", item.isImportant());
                intent.putExtra("bagType", item.getBagType());

                activity.startActivityForResult(intent, 2);
            }
        });
    }
    public int getItemCount() {
        return bagItems.size();
    }
    public void updateList(ArrayList<BagItem> newList) {
        this.bagItems = newList;
        notifyDataSetChanged();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView ItemName;
        TextView ItemCategory;
        TextView BagType;
        TextView Quantity;
        TextView Date;
        TextView IsPacked;
        TextView IsImportant;
        Button btnDelete;
        Button btnEdit;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ItemName=itemView.findViewById(R.id.txtName);
            ItemCategory=itemView.findViewById(R.id.txtCategory);
            BagType=itemView.findViewById(R.id.txtBagType);
            Quantity=itemView.findViewById(R.id.txtQuantity);
            Date=itemView.findViewById(R.id.txtDate);
            IsPacked=itemView.findViewById(R.id.txtIsPacked);
            IsImportant=itemView.findViewById(R.id.txtIsImportant);
            btnDelete=itemView.findViewById(R.id.btnDelete);
            btnEdit=itemView.findViewById(R.id.btnEdit);

        }
    }


}

