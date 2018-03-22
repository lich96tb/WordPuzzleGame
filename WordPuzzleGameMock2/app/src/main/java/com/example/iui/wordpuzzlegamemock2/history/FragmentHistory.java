package com.example.iui.wordpuzzlegamemock2.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.iui.wordpuzzlegamemock2.R;
import com.example.iui.wordpuzzlegamemock2.data.AppDatabase;
import com.example.iui.wordpuzzlegamemock2.data.Clue;
import com.example.iui.wordpuzzlegamemock2.data.Game;
import com.example.iui.wordpuzzlegamemock2.data.Result;
import com.example.iui.wordpuzzlegamemock2.playgame.MainPlayGame;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IUI on 3/2/2018.
 */

public class FragmentHistory extends Fragment implements OnHistoryItemClickListener {

    private TextView tvTotalTimePlayed;
    private RecyclerView rvHistory;
    private LinearLayout lnNotPlayed;
    private HistoryListAdapter historyListAdapter;
    private ArrayList<HistoryResult> lstHistoryResults;
    private Game game;
    private long gameId;
    private int timePlayed;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTotalTimePlayed = view.findViewById(R.id.tv_total_time_played);
        rvHistory = view.findViewById(R.id.rv_history);
        lnNotPlayed = view.findViewById(R.id.ln_not_played);
        gameId = getArguments().getLong("gameid");
        lstHistoryResults = getHistoryResultList();
        game = AppDatabase.getAppDatabase(getActivity()).gameDao().getGameById(gameId);
        timePlayed = AppDatabase.getAppDatabase(getActivity()).resultDao().timePlayedCount(gameId);
        tvTotalTimePlayed.setText(timePlayed + "");
        if (timePlayed == 0){
            rvHistory.setVisibility(View.INVISIBLE);
        } else {
            lnNotPlayed.setVisibility(View.INVISIBLE);
            historyListAdapter = new HistoryListAdapter(lstHistoryResults, game, this);
            rvHistory.setHasFixedSize(true);
            rvHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvHistory.setAdapter(historyListAdapter);
        }
    }

    @Override
    public void onHistoryItemClickListener(long gameId,String datePlayed) {
        Bundle bundle = new Bundle();
        bundle.putLong("id",gameId);
        bundle.putString("dateplayed",datePlayed);
        MainPlayGame mainPlayGame = new MainPlayGame();
        mainPlayGame.setArguments(bundle);
        getFragmentManager().beginTransaction().add(R.id.contaner_fragment, mainPlayGame, "a").addToBackStack(null).commit();
    }

    public ArrayList<HistoryResult> getHistoryResultList() {
        ArrayList<HistoryResult> historyResultList = new ArrayList<>();
        List<Clue> lstClues = AppDatabase.getAppDatabase(getActivity()).clueDao().getClueByGameId(gameId);
        List<String> lstDatePlayed = AppDatabase.getAppDatabase(getActivity()).resultDao().getDatePlay(gameId);
        for (int i = 0; i < lstDatePlayed.size(); i++) {
            int correctRowCount = 0;
            int correctColumnCount = 0;
            long playedTime = 0;
            List<Result> lstDatePlayedResult = AppDatabase.getAppDatabase(getActivity()).resultDao().getResultByDatePlayed(lstDatePlayed.get(i));
            for (int j = 0; j < lstDatePlayedResult.size(); j++) {
                playedTime = lstDatePlayedResult.get(j).getPlayedTime();
                for (int k = 0; k < lstClues.size(); k++) {
                    if (lstDatePlayedResult.get(j).getClueId() == lstClues.get(k).getClueId()) {
                        if (lstDatePlayedResult.get(j).getPlayResult() == true && lstClues.get(k).getClueRow() == -1) {
                            correctColumnCount++;
                        } else if (lstDatePlayedResult.get(j).getPlayResult() == true && lstClues.get(k).getClueColumn() == -1) {
                            correctRowCount++;
                        }
                    }
                }
            }
            historyResultList.add(new HistoryResult(gameId, lstDatePlayed.get(i), playedTime, correctRowCount, correctColumnCount));
        }
        return historyResultList;
    }
}
