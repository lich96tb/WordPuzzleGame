package com.example.iui.wordpuzzlegamemock2.selectgame;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.iui.wordpuzzlegamemock2.R;
import com.example.iui.wordpuzzlegamemock2.data.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 01/03/2018.
 */

public class GameTitleAdapter extends RecyclerView.Adapter<GameTitleAdapter.ViewHolder>{

    private OnGameItemClickListener onGameItemClickListener;
    private OnGameItemLongClickListener onGameItemLongClickListener;
    private List<Game> lstGames;

    public GameTitleAdapter(List<Game> lstGames,OnGameItemClickListener onGameItemClickListener,OnGameItemLongClickListener onGameItemLongClickListener) {

        this.lstGames = lstGames;
        this.onGameItemClickListener = onGameItemClickListener;
        this.onGameItemLongClickListener = onGameItemLongClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_game,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvGameTitle.setText(lstGames.get(position).getGameTitle());
        holder.lnItemSelectGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGameItemClickListener.onGameItemClick(lstGames.get(position));
            }
        });
        holder.lnItemSelectGame.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onGameItemLongClickListener.onGameItemLongClick(lstGames.get(position));
                return true;

            }
        });
    }

    @Override
    public int getItemCount() {
        return lstGames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout lnItemSelectGame;
        private TextView tvGameTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            lnItemSelectGame = itemView.findViewById(R.id.ln_item_select_game);
            tvGameTitle = itemView.findViewById(R.id.tv_game_title);
        }
    }
}
