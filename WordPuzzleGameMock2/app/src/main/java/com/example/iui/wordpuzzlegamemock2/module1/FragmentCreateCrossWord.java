package com.example.iui.wordpuzzlegamemock2.module1;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.iui.wordpuzzlegamemock2.R;
import com.example.iui.wordpuzzlegamemock2.data.AppDatabase;
import com.example.iui.wordpuzzlegamemock2.data.Clue;
import com.example.iui.wordpuzzlegamemock2.data.Game;
import com.example.iui.wordpuzzlegamemock2.utils.ViewModelMainActivity;

import java.util.ArrayList;
import java.util.List;

import br.com.sapereaude.maskedEditText.MaskedEditText;

/**
 * Created by IUI on 2/28/2018.
 */

public class FragmentCreateCrossWord extends Fragment implements View.OnClickListener {
    private ImageView mBtnBack, mTitle, mBtnSave;
    private int nColum = 0, nRow = 0;
    private LinearLayout layoutTable;
    private ViewModelMainActivity viewModelMainActivity;
    private RecyclerView recyclerViewSussgest;
    private List<ModelItemRecyclerViewCrossWord> wordList;
    private EditText edtNameOfGame, edtTime;
    private long idGame, idGameModel = -1;
    private long durationTotalGame;
    private String gameAnswer = "";
    private AppDatabase appDatabase;
    private String answer;
    private boolean isSuggestsColum = true;
    private boolean isSuggestsRow = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appDatabase = AppDatabase.getAppDatabase(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_create_cross_word, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            idGameModel = bundle.getLong("idGame");
        }
        mBtnBack = view.findViewById(R.id.create_ic_back);
        mTitle = view.findViewById(R.id.imageView5);
        mBtnSave = view.findViewById(R.id.create_next_img);
        layoutTable = view.findViewById(R.id.add_layout);
        viewModelMainActivity = ViewModelProviders.of(getActivity()).get(ViewModelMainActivity.class);
        nColum = viewModelMainActivity.getColum();
        nRow = viewModelMainActivity.getRow();
        recyclerViewSussgest = view.findViewById(R.id.create_cross_rv);
        edtTime = (MaskedEditText) view.findViewById(R.id.create_edt_time);
        edtNameOfGame = view.findViewById(R.id.create_edt_name_of_game);
        mBtnSave = view.findViewById(R.id.create_next_img);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mBtnBack.setBackgroundResource(R.drawable.ic_back_create);
        mTitle.setBackgroundResource(R.drawable.create_cross_word_img);
        mBtnSave.setBackgroundResource(R.drawable.save_img);
        mBtnBack.setOnClickListener(this);
        createData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false);
        recyclerViewSussgest.setLayoutManager(layoutManager);
        AdapterRecyclerViewCrossWord adapterRecyclerViewCrossWord = new AdapterRecyclerViewCrossWord(wordList, getContext());
        recyclerViewSussgest.setAdapter(adapterRecyclerViewCrossWord);
        mBtnSave.setOnClickListener(this);
        edtTime.setOnClickListener(this);
        edtTime.setText("00:00:00");
        edtTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.toString().length() == 8) {
                    String s1 = String.valueOf(editable.toString().charAt(0) + "" + editable.toString().charAt(1));
                    String s2 = String.valueOf(editable.toString().charAt(3) + "" + editable.toString().charAt(4));
                    String s3 = String.valueOf(editable.toString().charAt(6) + "" + editable.toString().charAt(7));
                    if (s1.charAt(0) != ':' && s1.charAt(1) != ':' &&
                            s2.charAt(0) != ':' && s2.charAt(1) != ':' &&
                            s3.charAt(0) != ':' && s3.charAt(1) != ':') {
                        if (Integer.parseInt(s1) > 24) {
                            Toast.makeText(getContext(), "Max time is 24:00:00", Toast.LENGTH_SHORT).show();
                            edtTime.setText("24:00:00");
                            editable.replace(0, editable.toString().length() - 1, "24:00:00");
                            Log.d("SS", "afterTextChanged: " + editable.toString());

                        } else if (Integer.parseInt(s1) == 24) {
                            if (Integer.parseInt(s2) > 0 || Integer.parseInt(s3) > 0) {
                                Toast.makeText(getContext(), "Max time is 24:00:00", Toast.LENGTH_SHORT).show();
                                edtTime.setText("24:00:00");
                                editable.replace(0, editable.toString().length() - 1, "24:00:00");
                                Log.d("SS", "afterTextChanged: " + editable.toString());
                            }
                        } else if (Integer.parseInt(s1) < 24) {
                            if (Integer.parseInt(s2) > 59) {
                                if (Integer.parseInt(s3) > 59) {
                                    edtTime.setText(s1 + ":" + "59" + ":" + "59");
                                    Toast.makeText(getContext(), "Max min and second is 59", Toast.LENGTH_SHORT).show();
                                } else if (Integer.parseInt(s3) < 60) {
                                    edtTime.setText(s1 + ":" + "59" + ":" + s3);
                                    Toast.makeText(getContext(), "Max min is 59", Toast.LENGTH_SHORT).show();
                                }
                            } else if (Integer.parseInt(s2) < 60) {
                                if (Integer.parseInt(s3) > 59) {
                                    edtTime.setText(s1 + ":" + s2 + ":" + "59");
                                    Toast.makeText(getContext(), "Max second is 59", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }
            }
        });
        if (idGameModel != -1) {
            setDataEdit();
        } else {
            createTable(nColum, nRow);
        }
        ConstraintLayout constraintLayout = view.findViewById(R.id.layout_create_crossword1);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
            }
        });

    }

    private void setDataEdit() {
        Game game = appDatabase.gameDao().findById(idGameModel);
        edtNameOfGame.setText(game.getGameTitle());
        String time;
        if (game.getGameDuration() == 0 || game.getGameDuration() == -1) {
            time = "00:00:00";
        } else {
            time = pastDuration(game.getGameDuration());
        }
        edtTime.setText(time);
        answer = game.getGameAnswer();
        String size1 = game.getGameSize();
        nRow = Integer.parseInt(String.valueOf(size1.charAt(0)));
        nColum = Integer.parseInt(String.valueOf(size1.charAt(2)));
        createTable(nColum, nRow);
        List<Clue> clueList = appDatabase.clueDao().getClueByGameId(idGameModel);
        wordList = new ArrayList<>();
        for (int i = 0; i < clueList.size(); i++) {
            Clue clue = clueList.get(i);
            int img = 0;
            int colum = 0;
            if (clue.getClueColumn() != -1) {
                img = R.drawable.colum_instruction_img;
                colum = clue.getClueColumn();
            } else {
                img = R.drawable.row_instruction_img;
                colum = clue.getClueRow();
            }
            String contentClude = clue.getClueContent();
            if (clue.getClueContent().equals("No Suggestion")){
                contentClude = "";
            }
            ModelItemRecyclerViewCrossWord crossWord = new ModelItemRecyclerViewCrossWord(img, colum, contentClude);
            wordList.add(crossWord);
        }
        AdapterRecyclerViewCrossWord adapterRecyclerViewCrossWord = new AdapterRecyclerViewCrossWord(wordList, getContext());
        recyclerViewSussgest.setAdapter(adapterRecyclerViewCrossWord);
    }

    private String pastDuration(long gameDuration) {
        String s1 = "";
        int h = (int) (gameDuration / 3600);
        int m = (int) (gameDuration % 3600) / 60;
        int s = (int) (gameDuration % 3600) % 60;
        if (h == 24) {
            s1 = "24:00:00";
        } else if (h < 24 && h > 9) {
            if (m < 60 && m > 9) {
                if (s > 9) {
                    s1 = h + ":" + m + ":" + s;
                } else if (s < 10 && s > 0) {
                    s1 = h + ":" + m + ":0" + s;
                } else if (s == 0) {
                    s1 = h + ":" + m + ":00";
                }
            } else if (m < 10) {
                if (s > 9) {
                    s1 = h + ":0" + m + ":" + s;
                } else if (s == 0) {
                    s1 = h + ":0" + m + ":00";
                } else if (s < 10 && s > 0) {
                    s1 = h + ":0" + m + ":0" + s;
                }
            }
        } else if (h < 10 && h > 0) {
            if (m < 60 && m > 9) {
                if (s > 9) {
                    s1 = "0" + h + ":" + m + ":" + s;
                } else if (s == 0) {
                    s1 = "0" + h + ":" + m + ":00";
                } else if (s < 10 && s > 0) {
                    s1 = "0" + h + ":" + m + ":0" + s;
                }
            } else if (m < 10) {
                if (s > 9) {
                    s1 = "0" + h + ":0" + m + ":" + s;
                } else if (s == 0) {
                    s1 = "0" + h + ":0" + m + ":00";
                } else if (s < 10 && s > 0) {
                    s1 = "0" + h + ":0" + m + ":0" + s;
                }
            }
        } else if (h == 0) {
            s1 = "00:" + m + ":" + s;
            if (m < 60 && m > 9) {
                if (s > 9) {
                    s1 = "00:" + m + ":" + s;
                } else if (s < 10 && s > 0) {
                    s1 = "00:" + m + ":0" + s;
                } else if (s == 0) {
                    s1 = "00:" + m + ":0" + s;
                }
            } else if (m < 10) {
                if (s > 9) {
                    s1 = "00:0" + m + ":" + s;
                } else if (s == 0) {
                    s1 = "00:0" + m + ":00";
                } else if (s < 10 && s > 0) {
                    s1 = "00:0" + m + ":0" + s;
                }
            }
        }

        return s1;
    }

    private void createData() {
        wordList = new ArrayList<>();
        if (nColum == 1 && nRow == 1) {
            wordList.add(new ModelItemRecyclerViewCrossWord(R.drawable.row_instruction_img, 1));
        } else {
            for (int i = 1; i <= nColum; i++) {
                wordList.add(new ModelItemRecyclerViewCrossWord(R.drawable.colum_instruction_img, i));
            }
            for (int j = 1; j <= nRow; j++) {
                wordList.add(new ModelItemRecyclerViewCrossWord(R.drawable.row_instruction_img, j));
            }
        }

    }

    private void createTable(int row, int colum) {
        LinearLayout linearLayoutVe = new LinearLayout(getContext());
        linearLayoutVe.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayoutVe.setLayoutParams(params);

        for (int i = 0; i < colum; i++) {
            LinearLayout linearLayoutHo = new LinearLayout(getContext());
            linearLayoutHo.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params1.gravity = Gravity.CENTER;
            linearLayoutHo.setLayoutParams(params1);

            for (int j = 0; j < row; j++) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.item_layout, linearLayoutHo, false);
                EditText editText = view.findViewById(R.id.edt_table);
                if (answer != null) {
                    char t = answer.charAt(i * nColum + j);
                    if (Character.toString(t).equals("*")) {
                        editText.setHint("*");
                    } else {
                        editText.setText(t + "");
                    }
                }

                linearLayoutHo.addView(view);
            }
            linearLayoutVe.addView(linearLayoutHo);
        }
        layoutTable.addView(linearLayoutVe);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create_ic_back:
                getActivity().onBackPressed();
                break;
            case R.id.create_next_img:
                isSuggestsColum = true;
                isSuggestsRow = true;
                checkSussgestsTheoHang();
                checkSussgestsTheoCot();
                if (isSuggestsColum && isSuggestsRow) {
                    if (!TextUtils.isEmpty(edtNameOfGame.getText().toString())) {
                        Game isGameName = appDatabase.gameDao().findByName(edtNameOfGame.getText().toString());
                        if (isGameName != null && idGameModel == -1) {
                            Toast.makeText(getContext(), "NameOfGame is Duplicate . Plase create new NameOfGame", Toast.LENGTH_SHORT).show();
                        } else {
                            if (edtTime.getText().toString().length() == 8) {
                                saveGame();
                                saveClue();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction transaction = fragmentManager.beginTransaction();
                                FragmentHome fragmentHome = (FragmentHome) fragmentManager.findFragmentByTag("home");
                                transaction.replace(R.id.contaner_fragment, fragmentHome);
                                transaction.commit();
                                View view2 = getActivity().getCurrentFocus();
                                if (view2 != null) {
                                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(view2.getWindowToken(), 0);
                                }
                            } else {
                                edtTime.requestFocus();
                                Toast.makeText(getContext(), "Time wrong format , please input time format 00:00:00", Toast.LENGTH_SHORT).show();
                            }

                        }
                    } else {
                        edtNameOfGame.requestFocus();
                        Toast.makeText(getContext(), "NameOfGame is Empty . Plase input Name of game", Toast.LENGTH_SHORT).show();
                    }
                } else {

                }


                break;
            case R.id.create_edt_time:
                edtTime.setSelection(edtTime.getText().toString().length());
                break;
        }
    }

    private void saveClue() {
        if (idGameModel != -1) {
           updateClude();
            Toast.makeText(getContext(), "Update Complete", Toast.LENGTH_SHORT).show();
        }else {
            insertClude();
            Toast.makeText(getContext(), "Insert Complete", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateClude() {
        List<Clue> clueList = appDatabase.clueDao().getClueByGameId(idGameModel);
        for (int i = 0; i < wordList.size(); i++) {
            long idClude = clueList.get(i).getClueId();
            Clue clue = new Clue();
            clue.setClueId(idClude);
            clue.setGameId(idGame);
            ModelItemRecyclerViewCrossWord crossWord = wordList.get(i);
            if (crossWord.getImgTable() == R.drawable.colum_instruction_img) {
                clue.setClueColumn(crossWord.getmNum());
                clue.setClueRow(-1);
            } else if (crossWord.getImgTable() == R.drawable.row_instruction_img) {
                clue.setClueRow(crossWord.getmNum());
                clue.setClueColumn(-1);
            }
            if (!TextUtils.isEmpty(crossWord.getmSuggestion())) {
                clue.setClueContent(crossWord.getmSuggestion());
            } else {
                clue.setClueContent("No Suggestion");
            }
            appDatabase.clueDao().updateClue(clue);
        }
    }

    private void insertClude() {
        for (int i = 0; i < wordList.size(); i++) {
            Clue clue = new Clue();
            clue.setGameId(idGame);
            ModelItemRecyclerViewCrossWord crossWord = wordList.get(i);
            if (crossWord.getImgTable() == R.drawable.colum_instruction_img) {
                clue.setClueColumn(crossWord.getmNum());
                clue.setClueRow(-1);
            } else if (crossWord.getImgTable() == R.drawable.row_instruction_img) {
                clue.setClueRow(crossWord.getmNum());
                clue.setClueColumn(-1);
            }
            if (!TextUtils.isEmpty(crossWord.getmSuggestion())) {
                clue.setClueContent(crossWord.getmSuggestion());
            } else {
                clue.setClueContent("No Suggestion");
            }
            appDatabase.clueDao().insertClue(clue);
        }
    }

    private void saveGame() {
        Game game = new Game();
        game.setGameSize(nRow + "x" + nColum);
        game.setGameTitle(edtNameOfGame.getText().toString());
        durationTotalGame = getDurationGame();
//        Toast.makeText(getContext(), ""+durationTotalGame, Toast.LENGTH_SHORT).show();
        game.setGameDuration(durationTotalGame);
        gameAnswer = String.valueOf(getGameAnswer());
        game.setGameAnswer(gameAnswer);
        if (idGameModel != -1) {
            idGame = idGameModel;
            game.setGameId(idGameModel);
            appDatabase.gameDao().updateGame(game);
        } else {
            idGame = appDatabase.gameDao().insertObj(game);
            Toast.makeText(getContext(), "Save Complete !", Toast.LENGTH_SHORT).show();
        }
    }

    private StringBuilder getGameAnswer() {
        StringBuilder s = new StringBuilder("");
        LinearLayout linearLayoutVe = (LinearLayout) layoutTable.getChildAt(0);
        for (int i = 0; i < nRow; i++) {
            LinearLayout linearLayoutHo = (LinearLayout) linearLayoutVe.getChildAt(i);
            for (int j = 0; j < nColum; j++) {
                View view = linearLayoutHo.getChildAt(j);
                EditText editText = view.findViewById(R.id.edt_table);
                if (TextUtils.isEmpty(editText.getText().toString())) {
                    s.append("*");
                }
                s.append(editText.getText().toString());
            }
        }
        return s;
    }

    private long getDurationGame() {
        long totalTime = -1;

        if (!TextUtils.isEmpty(edtTime.getText().toString())) {
            String s = edtTime.getText().toString();
            int s1 = Integer.parseInt(s.charAt(0) + "" + s.charAt(1));
            int s2 = Integer.parseInt(s.charAt(3) + "" + s.charAt(4));
            int s3 = Integer.parseInt(s.charAt(6) + "" + s.charAt(7));
//            Toast.makeText(getContext(), ""+s1+""+s2+""+s3, Toast.LENGTH_SHORT).show();
            totalTime = (s1 * 3600) + (s2 * 60) + s3;
            if (totalTime == 0) {
                totalTime = -1;
            }
        }

        return totalTime;
    }

    public void hideKeyboard() {
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }

    }

    public void checkSussgestsTheoHang() {
        if (nColum != 1 && nRow != 1) {
            LinearLayout linearLayoutVe = (LinearLayout) layoutTable.getChildAt(0);
            for (int i = 0; i < nRow; i++) {
                LinearLayout linearLayoutHo = (LinearLayout) linearLayoutVe.getChildAt(i);
                StringBuilder s = new StringBuilder("");
                for (int j = 0; j < nColum; j++) {
                    View view = linearLayoutHo.getChildAt(j);
                    EditText editText = view.findViewById(R.id.edt_table);
                    if (editText.getText().toString().equals("*")) {
                        s.append("");
                    } else {
                        s.append(editText.getText().toString());
                    }
                }
                if (!s.toString().equals("")) {
                    for (int u = 0; u < wordList.size(); u++) {
                        if (TextUtils.isEmpty(wordList.get(u).getmSuggestion()) &&
                                wordList.get(u).getImgTable() == R.drawable.row_instruction_img && wordList.get(u).getmNum() == (i + 1)) {
                            Toast.makeText(getContext(), "Hang " + (i + 1) + " Can goi y", Toast.LENGTH_SHORT).show();
                            isSuggestsRow = false;
                        }
                    }
                }
            }
        } else if (nRow == 1 && nColum == 1) {
            String s = "";
            LinearLayout linearLayoutVe = (LinearLayout) layoutTable.getChildAt(0);
            LinearLayout linearLayoutHo = (LinearLayout) linearLayoutVe.getChildAt(0);
            View view = linearLayoutHo.getChildAt(0);
            EditText editText = view.findViewById(R.id.edt_table);
            if (!TextUtils.isEmpty(editText.getText().toString())) {
                if (editText.getText().toString().equals("*")) {
                    s = "";
                } else {
                    s = editText.getText().toString();
                }
            }
            if (s.equals("") && TextUtils.isEmpty(wordList.get(0).getmSuggestion())) {
                isSuggestsRow = true;
            } else if (TextUtils.isEmpty(wordList.get(0).getmSuggestion()) && !s.equals("")) {
                Toast.makeText(getContext(), "Moi ban dien goi y", Toast.LENGTH_SHORT).show();
                isSuggestsRow = false;
            } else {
                isSuggestsRow = true;
            }
        }


    }

    public void checkSussgestsTheoCot() {
        if (nColum != 1 && nRow != 1) {
            gameAnswer = String.valueOf(getGameAnswer());
            for (int r = 0; r < nColum; r++) {
                StringBuilder s = new StringBuilder("");
                for (int o = r; o < gameAnswer.length(); o += nColum) {
                    if (gameAnswer.charAt(o) == '*') {
                        s.append("");
                    } else {
                        s.append(gameAnswer.charAt(o));
                    }


                }
                if (!s.toString().equals("")) {
//                    List<Boolean> booleans = new ArrayList<>();
                    for (int i = 0; i < wordList.size(); i++) {
                        boolean b = true;
                        if (wordList.get(i).getImgTable() == R.drawable.colum_instruction_img
                                && wordList.get(i).getmNum() == (r + 1)
                                && TextUtils.isEmpty(wordList.get(i).getmSuggestion())) {
                            Toast.makeText(getContext(), "Cot " + (i + 1) + " Can goi y", Toast.LENGTH_SHORT).show();
                           isSuggestsColum = false;
                            Log.d("SSSS", "checkSussgestsTheoCot: "+isSuggestsColum);
                        }
                    }

                }
            }
        } else {
            isSuggestsColum = true;
        }
    }

}
