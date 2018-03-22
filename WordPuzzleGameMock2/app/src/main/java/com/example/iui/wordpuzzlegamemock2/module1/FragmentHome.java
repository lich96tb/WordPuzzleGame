package com.example.iui.wordpuzzlegamemock2.module1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.iui.wordpuzzlegamemock2.R;
import com.example.iui.wordpuzzlegamemock2.history.FragmentHistory;
import com.example.iui.wordpuzzlegamemock2.selectgame.SelectGameFragment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by IUI on 2/28/2018.
 */

public class FragmentHome extends Fragment implements View.OnClickListener {
    private Button btnCreateNewGame, btnSelectGame;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_layout, container, false);
        btnCreateNewGame = view.findViewById(R.id.home_btn_create);
        btnSelectGame = view.findViewById(R.id.btn_select_game);
        fragmentManager = getActivity().getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        btnCreateNewGame.setOnClickListener(this);
        btnSelectGame.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_btn_create:
                FragmentCreateNewGame fragmentCreateNewGame = new FragmentCreateNewGame();
                transaction.replace(R.id.contaner_fragment, fragmentCreateNewGame, "create");
                transaction.addToBackStack("create");
                transaction.commit();
                break;
            case R.id.btn_select_game:
                SelectGameFragment selectGameFragment = new SelectGameFragment();
                transaction.replace(R.id.contaner_fragment, selectGameFragment, "selectgame");
                transaction.addToBackStack("selectgame");
                transaction.commit();
                break;

        }
    }
}
