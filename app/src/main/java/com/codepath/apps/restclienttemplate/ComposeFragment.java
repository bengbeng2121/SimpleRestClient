package com.codepath.apps.restclienttemplate;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by taq on 3/11/2016.
 */

public class ComposeFragment extends DialogFragment implements NoticeDialog.NoticeDialogListener {

    private final int MAX_CHAR = 140;

    @BindView(R.id.ivClose)
    ImageView ivClose;

    @BindView(R.id.etTweet)
    EditText etTweet;

    @BindView(R.id.tvRemainChar)
    TextView tvRemainChar;

    @BindView(R.id.btnTweet)
    Button btnTweet;

    public interface ComposeFragmentListener {
        void onFinish(String status);
    }

    public static ComposeFragment newInstance() {
        Bundle args = new Bundle();
        ComposeFragment fragment = new ComposeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ComposeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_compose, container);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
    }

    private void setupViews() {
        etTweet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btnTweet.setEnabled(etTweet.getText().toString().length() > 0);
                int remainChars = MAX_CHAR - etTweet.getText().length();
                tvRemainChar.setText(String.valueOf(remainChars));
                // TODO set color liện tục vậy có ảnh hưởng performance không?
                if (remainChars < 0) {
                    etTweet.setTextColor(Color.RED);
                    tvRemainChar.setTextColor(Color.RED);
                } else {
                    etTweet.setTextColor(Color.BLACK);
                    tvRemainChar.setTextColor(Color.BLACK);
                }
            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etTweet.getText().toString().isEmpty()) {
                    // TODO chuyển sang dùng MaterialDialog
                    FragmentManager fragmentManager = getFragmentManager();
                    NoticeDialog noticeDialog = NoticeDialog.newInstance("Are you sure?");
                    noticeDialog.setTargetFragment(ComposeFragment.this, 300);
                    noticeDialog.show(fragmentManager, "notice_dialog");
                } else {
                    dismiss();
                }
            }
        });

        tvRemainChar.setText(String.valueOf(MAX_CHAR));
        btnTweet.setEnabled(false);
        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etTweet.getText().length() > MAX_CHAR) {
                    new MaterialDialog.Builder(getContext())
                            .content("Can not post the tweet. Your content is more 140 characters.")
                            .positiveText("Got it")
                            .show();
                    return;
                }
                ComposeFragmentListener listener = (ComposeFragmentListener) getActivity();
                listener.onFinish(etTweet.getText().toString());
                dismiss();
            }
        });
    }

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
        super.onResume();
    }

    @Override
    public void onClickOk() {
        dismiss();
    }
}
