package com.example.simpleenglish;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GrammarAdapter extends RecyclerView.Adapter<GrammarAdapter.GrammarHolder> {
    private List<Grammar> grammarList ;
    private static OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    interface OnItemClickListener
    {
        void onClick(int position);
    }

    public void setGrammarList(List<Grammar> grammarList) {
        this.grammarList = grammarList;
        notifyDataSetChanged();
    }

    public GrammarAdapter() {
        super();
        grammarList=new ArrayList<>();
    }

    @NonNull
    @Override
    public GrammarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grammar, parent, false);
        return new GrammarHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GrammarHolder holder, int position) {
        Grammar grammar = grammarList.get(position);
        Picasso.get().load(grammar.getImage())
                .into(holder.imageViewGrammar);
        holder.textViewName.setText(grammar.getName());
        holder.textViewInRussia.setText(grammar.getInRussian());
    }

    @Override
    public int getItemCount() {
        return grammarList.size();
    }

    static class GrammarHolder extends RecyclerView.ViewHolder{

        ImageView imageViewGrammar;
        TextView textViewName;
        TextView textViewInRussia;
        GrammarHolder(@NonNull View itemView) {
            super(itemView);
            imageViewGrammar=itemView.findViewById(R.id.imageViewGrammar);
            textViewName=itemView.findViewById(R.id.textViewGrammarName);
            textViewInRussia=itemView.findViewById(R.id.textViewGrammarInRussian);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener!=null)
                    {
                        onItemClickListener.onClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
