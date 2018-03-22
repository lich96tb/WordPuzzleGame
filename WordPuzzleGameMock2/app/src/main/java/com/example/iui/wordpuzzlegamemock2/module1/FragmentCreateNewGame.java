package com.example.iui.wordpuzzlegamemock2.module1;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.example.iui.wordpuzzlegamemock2.utils.ViewModelMainActivity;

import br.com.sapereaude.maskedEditText.MaskedEditText;

/**
 * Created by IUI on 2/28/2018.
 */

public class FragmentCreateNewGame extends Fragment implements View.OnClickListener {
    private ImageView imgBack, imgNext, mTitleToolbar;
    private FragmentManager fragmentManager;
    private LinearLayout layoutTable;
    private EditText edtColum, edtRow;
    private int nColum = 0, nRow = 0;
    private ViewModelMainActivity viewModelMainActivity;
    private long idGame;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_new_game, container, false);
        layoutTable = (LinearLayout) view.findViewById(R.id.create_add_layout);
        imgBack = view.findViewById(R.id.create_ic_back);
        imgNext = view.findViewById(R.id.create_next_img);
        edtColum = view.findViewById(R.id.create_edt_colum);
        edtRow = view.findViewById(R.id.create_edt_row);
        mTitleToolbar = view.findViewById(R.id.imageView5);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mTitleToolbar.setBackgroundResource(R.drawable.text_create_new_game);
        imgBack.setBackgroundResource(R.drawable.ic_back_create);
        imgNext.setBackgroundResource(R.drawable.create_next_img);
        imgBack.setOnClickListener(this);
        imgNext.setOnClickListener(this);
        idGame = -1;
        edtColum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(edtColum.getText().toString())) {
                    layoutTable.removeAllViews();
                    nColum = Integer.parseInt(edtColum.getText().toString());
                    createTable(nColum, nRow);
                }
            }
        });
        edtRow.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(edtRow.getText().toString())) {
                    layoutTable.removeAllViews();
                    nRow = Integer.parseInt(edtRow.getText().toString());
                    createTable(nColum, nRow);
                }
            }
        });

        ConstraintLayout constraintLayout = view.findViewById(R.id.parent);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
            }
        });
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
                editText.setText("*");
                editText.setFocusable(false);
                editText.setFocusableInTouchMode(false);
                editText.setClickable(false);
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
                if (nColum != 0 && nRow != 0) {
                    viewModelMainActivity = ViewModelProviders.of(getActivity()).get(ViewModelMainActivity.class);
                    viewModelMainActivity.setColum(nColum);
                    viewModelMainActivity.setRow(nRow);
                    FragmentCreateCrossWord fragmentCreateCrossWord = new FragmentCreateCrossWord();
                    fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.contaner_fragment, fragmentCreateCrossWord, "cross");
                    transaction.addToBackStack("cross");
                    transaction.commit();
                    View view2 = getActivity().getCurrentFocus();
                    if (view2 != null) {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view2.getWindowToken(), 0);
                    }
                } else {
                    Toast.makeText(getActivity(), "Please insert complete to Row and Colum !", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
    public void hideKeyboard(){
        if (getActivity().getCurrentFocus() != null){
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }

    }
}
