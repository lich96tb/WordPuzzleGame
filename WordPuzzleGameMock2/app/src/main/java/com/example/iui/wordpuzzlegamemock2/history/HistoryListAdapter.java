package com.example.iui.wordpuzzlegamemock2.history;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.iui.wordpuzzlegamemock2.R;
import com.example.iui.wordpuzzlegamemock2.data.Game;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Admin on 06/03/2018.
 */

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.ViewHolder> {

    private ArrayList<HistoryResult> lstResults;
    private Game game;
    private OnHistoryItemClickListener onHistoryItemClickListener;

    public HistoryListAdapter(ArrayList<HistoryResult> lstResults, Game game, OnHistoryItemClickListener onHistoryItemClickListener) {
        this.onHistoryItemClickListener = onHistoryItemClickListener;
        this.lstResults = lstResults;
        this.game = game;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final HistoryResult historyResult = lstResults.get(position);
        holder.tvDatePlayed.setText("-Date Played: " + historyResult.getDatePlayed());
        if (historyResult.getPlayedTime() == -1){
            holder.tvPlayedTime.setText("-Played Time: N/A");
        } else {
            holder.tvPlayedTime.setText("-Played Time : " + hmsTimeFormatter(historyResult.getPlayedTime()*1000));
        }
        holder.tvResultRow.setText("-Correct Rows: " + historyResult.getCorrectRow() + "/" + game.getGameSize().charAt(0));
        holder.tvResultColumn.setText("-Correct Columns: " + historyResult.getCorrectColumn() + "/" + game.getGameSize().charAt(2));
        holder.lnHistoryItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onHistoryItemClickListener.onHistoryItemClickListener(historyResult.getGameId(),historyResult.getDatePlayed());
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstResults.size();
    }

    private String hmsTimeFormatter(long milliseconds) {
        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(milliseconds),
                TimeUnit.MILLISECONDS.toMinutes(milliseconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliseconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)));
        return hms;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout lnHistoryItem;
        private TextView tvDatePlayed, tvPlayedTime, tvResultRow, tvResultColumn;

        public ViewHolder(View itemView) {
            super(itemView);
            lnHistoryItem = itemView.findViewById(R.id.ln_history_item);
            tvDatePlayed = itemView.findViewById(R.id.tv_date_played);
            tvPlayedTime = itemView.findViewById(R.id.tv_played_time);
            tvResultRow = itemView.findViewById(R.id.tv_result_row);
            tvResultColumn = itemView.findViewById(R.id.tv_result_column);
        }
    }
}
