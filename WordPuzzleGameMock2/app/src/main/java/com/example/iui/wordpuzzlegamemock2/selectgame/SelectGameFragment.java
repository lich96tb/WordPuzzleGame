package com.example.iui.wordpuzzlegamemock2.selectgame;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.iui.wordpuzzlegamemock2.R;
import com.example.iui.wordpuzzlegamemock2.data.AppDatabase;
import com.example.iui.wordpuzzlegamemock2.data.Game;
import com.example.iui.wordpuzzlegamemock2.history.FragmentHistory;
import com.example.iui.wordpuzzlegamemock2.playgame.MainPlayGame;
import com.example.iui.wordpuzzlegamemock2.module1.FragmentCreateCrossWord;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectGameFragment extends Fragment implements CompoundButton.OnCheckedChangeListener,
        View.OnClickListener, OnGameItemClickListener, OnGameItemLongClickListener {

    private LinearLayout lnBtnEdit, lnBtnDelete, lnBtnHistory;
    private FrameLayout flSelectGameContent;
    private RecyclerView rvGameTitle;
    private ImageView ivSelectGame, ivBack;
    private Spinner spnSelectGameSize;
    private CheckBox ckbNotPlayed;
    private LinearLayout transparentLayout;
    private CustomSpinnerAdapter customSpinnerAdapter;
    private GameTitleAdapter gameTitleAdapter;
    private List<Game> lstGames;
    private List<String> lstGameSize;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private static Game currGame;
    private Bundle bundle;

    public SelectGameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_game, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lnBtnEdit = view.findViewById(R.id.ln_btn_edit);
        lnBtnDelete = view.findViewById(R.id.ln_btn_delete);
        lnBtnHistory = view.findViewById(R.id.ln_btn_history);
        flSelectGameContent = view.findViewById(R.id.fl_select_game_content);
        ivSelectGame = view.findViewById(R.id.iv_select_game);
        ivBack = view.findViewById(R.id.iv_select_game_back);
        rvGameTitle = view.findViewById(R.id.rv_game_title);
        spnSelectGameSize = view.findViewById(R.id.spn_select_game_size);
        ckbNotPlayed = view.findViewById(R.id.ckb_not_played);

        lnBtnEdit.setOnClickListener(this);
        lnBtnDelete.setOnClickListener(this);
        lnBtnHistory.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        ckbNotPlayed.setOnCheckedChangeListener(this);
        setSpinnerItemClickedListenner(spnSelectGameSize);

        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        transparentLayout = new LinearLayout(getActivity());
        transparentLayout.setId(R.id.transparent_layout);
        transparentLayout.setBackgroundColor(Color.TRANSPARENT);
        transparentLayout.setOnClickListener(this);
        lstGames = AppDatabase.getAppDatabase(getActivity()).gameDao().getAllGame();
        lstGameSize = AppDatabase.getAppDatabase(getActivity()).gameDao().getGameSize();
        lstGameSize.add(0, "All");

        customSpinnerAdapter = new CustomSpinnerAdapter(getActivity(), lstGameSize);
        gameTitleAdapter = new GameTitleAdapter(lstGames, this, this);

        spnSelectGameSize.setAdapter(customSpinnerAdapter);

        rvGameTitle.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvGameTitle.setHasFixedSize(true);
        rvGameTitle.setAdapter(gameTitleAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_select_game_back:
                getActivity().onBackPressed();
                break;
            //Chuyển dữ liệu sang màn create game
            case R.id.ln_btn_edit:
                bundle = new Bundle();
                bundle.putLong("idGame", currGame.getGameId());
                FragmentCreateCrossWord fragmentCreateCrossWord = new FragmentCreateCrossWord();
                fragmentCreateCrossWord.setArguments(bundle);
                fragmentTransaction.replace(R.id.contaner_fragment, fragmentCreateCrossWord, "cross");
                fragmentTransaction.addToBackStack("cross");
                fragmentTransaction.commit();
                break;
            //Xoá dữ liệu của game
            case R.id.ln_btn_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setIcon(R.drawable.ic_delete_game_noti)
                        .setTitle("Notification")
                        .setMessage("Are you sure to delete this game?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                AppDatabase.getAppDatabase(getActivity()).resultDao().deleteResultByGameId(currGame.getGameId());
                                AppDatabase.getAppDatabase(getActivity()).clueDao().deleteClueByGameId(currGame.getGameId());
                                AppDatabase.getAppDatabase(getActivity()).gameDao().deleteGame(currGame);
                                lstGames.clear();
                                if (ckbNotPlayed.isChecked()){
                                    lstGames.addAll(AppDatabase.getAppDatabase(getActivity()).gameDao().getNotPlayedGame());
                                } else {
                                    String spinnerSelectedItem = spnSelectGameSize.getSelectedItem().toString();
                                    if (spinnerSelectedItem.equals("All")) {
                                        lstGames.addAll(AppDatabase.getAppDatabase(getActivity()).gameDao().getAllGame());
                                    } else {
                                        lstGames.addAll(AppDatabase.getAppDatabase(getActivity()).gameDao().getGameBySize(spinnerSelectedItem));
                                    }
                                }
                                gameTitleAdapter.notifyDataSetChanged();
                                dialogInterface.dismiss();
                                ivBack.setVisibility(View.VISIBLE);
                                ivSelectGame.setVisibility(View.VISIBLE);
                                lnBtnEdit.setVisibility(View.INVISIBLE);
                                lnBtnDelete.setVisibility(View.INVISIBLE);
                                lnBtnHistory.setVisibility(View.INVISIBLE);
                                flSelectGameContent.removeView(transparentLayout);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
                break;
            //Chuyển dữ liệu sang màn hình history
            case R.id.ln_btn_history:
                bundle = new Bundle();
                bundle.putLong("gameid", currGame.getGameId());
                FragmentHistory fragmentHistory = new FragmentHistory();
                fragmentHistory.setArguments(bundle);
                fragmentTransaction.replace(R.id.contaner_fragment, fragmentHistory, "history");
                fragmentTransaction.addToBackStack("history");
                fragmentTransaction.commit();
                break;
            case R.id.transparent_layout:
                ivBack.setVisibility(View.VISIBLE);
                ivSelectGame.setVisibility(View.VISIBLE);
                lnBtnEdit.setVisibility(View.INVISIBLE);
                lnBtnDelete.setVisibility(View.INVISIBLE);
                lnBtnHistory.setVisibility(View.INVISIBLE);
                flSelectGameContent.removeView(transparentLayout);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        lstGames.clear();
        if (isChecked) {
            lstGames.addAll(AppDatabase.getAppDatabase(getActivity()).gameDao().getNotPlayedGame());
        } else {
            String spinnerSelectedItem = spnSelectGameSize.getSelectedItem().toString();
            if (spinnerSelectedItem.equals("All")) {
                lstGames.addAll(AppDatabase.getAppDatabase(getActivity()).gameDao().getAllGame());
            } else {
                lstGames.addAll(AppDatabase.getAppDatabase(getActivity()).gameDao().getGameBySize(spinnerSelectedItem));
            }
        }
        gameTitleAdapter.notifyDataSetChanged();
    }

    /**
     * Sự kiện click vào 1 item game trên list chuyển sang màn hình chơi game
     * @param game
     */
    @Override
    public void onGameItemClick(Game game) {
        Bundle bundle = new Bundle();
        bundle.putLong("id",game.getGameId());
        MainPlayGame mainPlayGame = new MainPlayGame();
        mainPlayGame.setArguments(bundle);
        getFragmentManager().beginTransaction().add(R.id.contaner_fragment, mainPlayGame, "a").addToBackStack(null).commit();
    }

    /**
     * Sự kiện long click hiển thị options menu trên toolbar
     * @param game
     */
    @Override
    public void onGameItemLongClick(Game game) {
        ivBack.setVisibility(View.INVISIBLE);
        ivSelectGame.setVisibility(View.INVISIBLE);
        lnBtnEdit.setVisibility(View.VISIBLE);
        lnBtnDelete.setVisibility(View.VISIBLE);
        lnBtnHistory.setVisibility(View.VISIBLE);
        flSelectGameContent.addView(transparentLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        currGame = game;
    }

    private void setSpinnerItemClickedListenner(Spinner spnSelectGameSize) {
        spnSelectGameSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lstGames.clear();
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem.equals("All")) {
                    lstGames.addAll(AppDatabase.getAppDatabase(getActivity()).gameDao().getAllGame());
                } else {
                    lstGames.addAll(AppDatabase.getAppDatabase(getActivity()).gameDao().getGameBySize(selectedItem));
                }
                gameTitleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
