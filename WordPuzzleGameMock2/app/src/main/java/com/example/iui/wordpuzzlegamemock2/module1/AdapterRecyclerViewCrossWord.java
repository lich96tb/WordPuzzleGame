package com.example.iui.wordpuzzlegamemock2.module1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.iui.wordpuzzlegamemock2.R;

import java.util.List;

/**
 * Created by IUI on 3/1/2018.
 */

public class AdapterRecyclerViewCrossWord extends RecyclerView.Adapter<AdapterRecyclerViewCrossWord.ViewHolder> {
    private List<ModelItemRecyclerViewCrossWord> wordList;
    private Context context;

    public AdapterRecyclerViewCrossWord(List<ModelItemRecyclerViewCrossWord> wordList, Context context) {
        this.wordList = wordList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_create_crossword, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ModelItemRecyclerViewCrossWord crossWord = wordList.get(position);
        holder.imageView.setBackgroundResource(crossWord.getImgTable());
        holder.textView.setText(crossWord.getmNum() + "");
        holder.editText.setText(crossWord.getmSuggestion());

    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements TextWatcher {
        private ImageView imageView;
        private TextView textView;
        private EditText editText;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView6);
            textView = itemView.findViewById(R.id.textView);
            editText = itemView.findViewById(R.id.editText);
            editText.addTextChangedListener(this);
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            wordList.get(getAdapterPosition()).setmSuggestion(editable.toString());
        }
    }
}
