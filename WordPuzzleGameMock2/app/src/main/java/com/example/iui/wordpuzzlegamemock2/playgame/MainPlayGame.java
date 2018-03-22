package com.example.iui.wordpuzzlegamemock2.playgame;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iui.wordpuzzlegamemock2.R;
import com.example.iui.wordpuzzlegamemock2.data.AppDatabase;
import com.example.iui.wordpuzzlegamemock2.data.Clue;
import com.example.iui.wordpuzzlegamemock2.data.Game;
import com.example.iui.wordpuzzlegamemock2.data.Result;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainPlayGame extends Fragment {
    private long id;

    private String gameSize,dateplayed;
    private HorizontalScrollView layoutAdd;
    private ListView lv;
    private ArrayList<Play> arrayList;
    private ArrayList<Result> arrayListResult;
    private ArrayList<Clue> arrayListClue;
    private PlayGameAdapter adapter;
    private TextView mTextField, txtContent,txtTitle;
    private LinearLayout la;
    private LinearLayout lz;
    private LinearLayout lj,linear_note;
    private CountDownTimer Timer;
    private int numColum;
    private int numRow;
    private String contentResult,contentResult1;
    private long initialization;
    private long timePlayReult;
    private AppDatabase db;
    private EditText edt;
    private ImageView imgPlayGameAnswer;
    private Button btnFinish, btnBack;
    private String gameTitle,contentTable="";
    private long clueId = 1;
    private int coutnFinish = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_play_game, container, false);
        lv = view.findViewById(R.id.play_game_lv);
        txtContent = view.findViewById(R.id.txtContent);
        txtTitle=view.findViewById(R.id.txtTitle);
        mTextField = view.findViewById(R.id.mTextField);
        btnFinish = view.findViewById(R.id.play_game_btn_finish);
        btnBack = view.findViewById(R.id.play_game_btn_back);
        layoutAdd = view.findViewById(R.id.add_layout_play);
        imgPlayGameAnswer = view.findViewById(R.id.img_play_game_answer);
        linear_note=view.findViewById(R.id.linear_note);

        database();
        Event(view);
        model(view);
        if (dateplayed.equals("")==false){
            contentResult1=db.resultDao().getResultByDatePlayed(dateplayed).get(0).getPlayersAnswer();
            indexing1();
            initialization=-1;
        }else {
            indexing();
        }

        if (initialization != -1) {
            countDownTimer();
        } else {
            mTextField.setVisibility(View.GONE);
        }
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculte();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fb = getFragmentManager().findFragmentByTag("a");
                getFragmentManager().beginTransaction().remove(fb).commit();
            }
        });
        if (dateplayed.equals("")==false){
            checkReult(contentResult1);
        }
        return view;
    }

    private void indexing() {
        Log.e("noi dung", contentResult);
        la = (LinearLayout) layoutAdd.getChildAt(0);
        for (int i = 0; i < numRow; i++) {
            lz = (LinearLayout) la.getChildAt(i);
            for (int j = 0; j < numColum; j++) {
                lj = (LinearLayout) lz.getChildAt(j);
                edt = (EditText) lj.getChildAt(0);
                char t = contentResult.charAt(i * numColum + j);
                if (Character.toString(t).equals("*")) {
                    edt.setText("*");
                    edt.setEnabled(false);
                } else {
                    edt.setHint("");
                }

            }
        }
    }    private void indexing1() {
        Log.e("noi dung", contentResult1);
        la = (LinearLayout) layoutAdd.getChildAt(0);
        for (int i = 0; i < numRow; i++) {
            lz = (LinearLayout) la.getChildAt(i);
            for (int j = 0; j < numColum; j++) {
                lj = (LinearLayout) lz.getChildAt(j);
                edt = (EditText) lj.getChildAt(0);
                String t = String.valueOf(contentResult1.charAt(i * numColum + j));
                edt.setText(t);
            }
        }
    }

    //khoi tao csdl
    private void database() {
        id = getArguments().getLong("id");
        dateplayed=getArguments().getString("dateplayed","");
        Game dbgame;
        db = (AppDatabase) AppDatabase.getAppDatabase(getActivity());
        dbgame = db.gameDao().getContentGame(id).get(0);
        gameTitle = dbgame.getGameTitle();
        gameSize = dbgame.getGameSize();
        initialization = dbgame.getGameDuration();
        contentResult = dbgame.getGameAnswer();
        String[] str = gameSize.split("x");
        numRow = Integer.parseInt(str[0]);
        numColum = Integer.parseInt(str[1]);
        arrayListResult = new ArrayList<>();
        arrayListClue = new ArrayList<>();
        for (int i = 0; i < db.clueDao().getClueByGameId(id).size(); i++) {
            arrayListClue.add(db.clueDao().getClueByGameId(id).get(i));
        }
    }

    private void countDownTimer() {
        Timer = new CountDownTimer(initialization * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                timePlayReult = millisUntilFinished;
                mTextField.setText(abc(millisUntilFinished));
            }

            public void onFinish() {
                timePlayReult = (long) 0;
                calculte();
                mTextField.setText("done!");
            }
        };
        Timer.start();
    }

    // chuyen dinhj dang mini giay ve gio phut giay
    private String abc(long milliseconds) {
        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(milliseconds),
                TimeUnit.MILLISECONDS.toMinutes(milliseconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliseconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)));
        return hms;
    }

    private void Event(View view) {
        txtTitle.setText(gameTitle);
        arrayList = new ArrayList<>();
        for (int i = 1; i <= numRow; i++) {
            arrayList.add(new Play(R.drawable.row_instruction_img, "Row " + i, db.clueDao().getListRow(id, i).get(0).getClueContent()));
        }
        for (int i = 1; i <= numColum; i++) {
            arrayList.add(new Play(R.drawable.colum_instruction_img, "Colum " + i, db.clueDao().getListColum(id, i).get(0).getClueContent()));
        }
        Log.e("row", numRow + "x" + numColum + "");

        adapter = new PlayGameAdapter(getActivity(), R.layout.item_play_game, arrayList);
        lv.setAdapter(adapter);
        // su kien click vao item
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                la = (LinearLayout) layoutAdd.getChildAt(0);
                for (int j = 0; j < numRow; j++) {
                    lz = (LinearLayout) la.getChildAt(j);
                    for (int k = 0; k < numColum; k++) {
                        lj = (LinearLayout) lz.getChildAt(k);
                        if (k == i - numRow && i >= numRow) {
                            lj.setBackgroundColor(Color.BLUE);

                        } else if (i == j && i < numRow) {
                            lj.setBackgroundColor(Color.BLUE);
                        } else {
                            lj.setBackgroundResource(R.color.colorDeepAccent);
                        }
                    }
                }
                txtContent.setText(arrayList.get(i).getContent());
            }
        });

//set su kien cho phep keo listviw trong sc...
        lv.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

    }

    private void model(View view) {
/// khoi tao o tro choi
        LinearLayout linearLayoutVe = new LinearLayout(getActivity());
        linearLayoutVe.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayoutVe.setLayoutParams(params);
        linearLayoutVe.setBackgroundResource(R.drawable.frame_candy_img);
        linearLayoutVe.setPadding(40, 40, 40, 40);
        linearLayoutVe.setGravity(Gravity.CENTER);

        for (int i = 0; i < numRow; i++) {
            LinearLayout linearLayoutHo = new LinearLayout(getActivity());
            linearLayoutHo.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params1.gravity = Gravity.CENTER;
            linearLayoutHo.setLayoutParams(params1);
            for (int j = 0; j < numColum; j++) {
                View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.item_layout, linearLayoutHo, false);
                linearLayoutHo.addView(view1);
            }
            linearLayoutVe.addView(linearLayoutHo);
        }
        layoutAdd.addView(linearLayoutVe);
    }


    private void calculte() {
        String mergeContent = "";
        EditText edt;
        la = (LinearLayout) layoutAdd.getChildAt(0);
        for (int i = 0; i < numRow; i++) {
            lz = (LinearLayout) la.getChildAt(i);
            for (int j = 0; j < numColum; j++) {
                lj = (LinearLayout) lz.getChildAt(j);
                edt = (EditText) lj.getChildAt(0);
                mergeContent = mergeContent + edt.getText().toString();
            }
        }

        // kiem tra xem nhap du o chua
        if (mergeContent.length()!= contentResult.length() && (timePlayReult > 0 || initialization == -1)&&coutnFinish == 0) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
            dialog.setMessage("you want to finish ?");
            dialog.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            dialog.setNegativeButton("finish", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (initialization!=-1){
                        Timer.cancel();
                    }
                    String mergeContent = "";
                    EditText edt;
                    la = (LinearLayout) layoutAdd.getChildAt(0);
                    for (int l = 0; l < numRow; l++) {
                        lz = (LinearLayout) la.getChildAt(l);
                        for (int j = 0; j < numColum; j++) {
                            lj = (LinearLayout) lz.getChildAt(j);
                            edt = (EditText) lj.getChildAt(0);
                            mergeContent = mergeContent + edt.getText().toString();
                        }
                    }
                    checkReult(mergeContent);
//                    Fragment fb = getFragmentManager().findFragmentByTag("a");
//                    getFragmentManager().beginTransaction().remove(fb).commit();
                }
            });
            dialog.show();
        } else if (mergeContent.length() == contentResult.length() && (initialization == -1)) {
            checkReult(mergeContent);

        } else {
            if (initialization!=-1){
                Timer.cancel();
            }
            checkReult(mergeContent);
        }
    }

    private void checkReult(String chuoi) {
        linear_note.setVisibility(View.VISIBLE);
        imgPlayGameAnswer.setImageResource(R.drawable.answer);
        arrayList.clear();

        //them colum vao db
        for (int colum = 0; colum < numColum; colum++) {
            Boolean result_row = true;
            for (int i = 0; i < numRow; i++) {
                lz = (LinearLayout) la.getChildAt(i);
                for (int j = 0; j < numColum; j++) {
                    if (j == colum) {
                        lj = (LinearLayout) lz.getChildAt(j);
                        edt = (EditText) lj.getChildAt(0);
                        String text = edt.getText().toString();
                        String g = String.valueOf(contentResult.charAt(i * numColum + j));
                        if (g.equals(text) == false) {
                            result_row = false;
                        }
                    }
                }
            }
            clueId = colum + 1;
            arrayListResult.add(new Result(clueId, result_row, DateFormat.getDateTimeInstance().format(new Date()) + "", initialization - timePlayReult / 1000));
        }


        for (int i = 0; i < numRow; i++) {
            clueId = i;
            Boolean result_row = true;
            String k = "";
            lz = (LinearLayout) la.getChildAt(i);
            for (int j = 0; j < numColum; j++) {
                lj = (LinearLayout) lz.getChildAt(j);
                edt = (EditText) lj.getChildAt(0);
                chuoi = chuoi + edt.getText().toString();
                edt.setEnabled(false);
                String g = String.valueOf(contentResult.charAt(i * numColum + j));
                String b=edt.getText().toString();
                if (b.equals("")){
                    contentTable=contentTable+"_";
                }else {
                    contentTable=contentTable+b;
                }

                if (g.equals("*") == false) {
                    k = k + g;
                }
                if (g.equals(edt.getText().toString().trim())) {
                    lj.setBackgroundResource(R.color.truePlay);
                } else {
                    result_row = false;
                    lj.setBackgroundResource(R.color.falsePlay);
                }
            }
            clueId++;
            arrayListResult.add(new Result(clueId, result_row, DateFormat.getDateTimeInstance().format(new Date()) + "", initialization - timePlayReult / 1000));
            arrayList.add(new Play(R.drawable.row_instruction_img, "Row " + (i + 1), k));
        }
        //////////////////////////////////////////////
        if (coutnFinish == 0&&dateplayed.equals("")) {
            coutnFinish=1;
            for (int i = 0; i < arrayListResult.size(); i++) {
                for (int j = 0; j < arrayListClue.size(); j++) {
                    if (arrayListClue.get(j).getClueColumn() == arrayListResult.get(i).getClueId() && i < numColum) {
                        db.resultDao().insertResult(new Result(id, arrayListClue.get(j).getClueId(), arrayListResult.get(i).getPlayResult(), DateFormat.getDateTimeInstance().format(new Date()) + "", initialization - timePlayReult / 1000,contentTable));
                    } else if (arrayListClue.get(j).getClueRow() == arrayListResult.get(i).getClueId() && i >= numColum) {
                        db.resultDao().insertResult(new Result(id, arrayListClue.get(j).getClueId(), arrayListResult.get(i).getPlayResult(), DateFormat.getDateTimeInstance().format(new Date()) + "", initialization - timePlayReult / 1000,contentTable));
                    }
                }

            }
        }


        /////////
        for (int i = 0; i < numColum; i++) {
            lz = (LinearLayout) la.getChildAt(i);

            String k = "";
            for (int j = i; j < contentResult.length(); j = j + numColum) {
                String h = String.valueOf(contentResult.charAt(j));

                if (h.equals("*") == false) {
                    k = k + contentResult.charAt(j);
                }
            }
            arrayList.add(new Play(R.drawable.colum_instruction_img, "Colum " + (i + 1), k));
        }
        adapter.notifyDataSetChanged();
    }
    public void hideKeyboard(){
        if (getActivity().getCurrentFocus() != null){
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        LinearLayout linearLayout = view.findViewById(R.id.layout_playgame);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
            }
        });
    }
}
