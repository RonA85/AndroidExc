package com.applicx.androidexercise.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.applicx.androidexercise.Cons;
import com.applicx.androidexercise.R;
import com.bumptech.glide.Glide;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CastProfileDialog extends DialogFragment implements DialogInterface.OnKeyListener {

    @BindView(R.id.iv_cast_profile)
    ImageView ivProfileImage;
    @BindView(R.id.tv_cast_name)
    TextView tvName;
    @BindView(R.id.tv_cast_character)
    TextView tvCharacter;
    public  final String TAG = getClass().getSimpleName();
    private static String profilePath,character,name;

    public static CastProfileDialog newInstance() {

        CastProfileDialog frag = new CastProfileDialog();
        frag.setStyle(DialogFragment.STYLE_NO_FRAME, 0);
        return frag;
    }

    public static void setCastDetails(String _profile, String _character, String _name) {
        profilePath = _profile;
        character = _character;
        name = _name;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Translucent_NoTitleBar);
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dView = inflater.inflate(R.layout.dialog_cast_profile, null);
        ButterKnife.bind(this,dView);
        Glide.with(this).load(Cons.BASE_PATH + profilePath).into(ivProfileImage);
        tvName.setText(name);
        tvCharacter.setText(character);
        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setView(dView).create();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        }
        dialog.setOnKeyListener(this);
        return dialog;
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }


    @Override
    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (i == KeyEvent.KEYCODE_BACK) {
            dismiss();
            return true;
        }
        return false;
    }
}
