package com.example.user.airtickets.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.user.airtickets.R;
import com.example.user.airtickets.api.retrofit.ServerApi;

public class PostBookingDialogFragment extends DialogFragment {

    private PostBookingDialogListener listener;

    public interface PostBookingDialogListener {

        public void onDialogPositiveClick(DialogFragment dialog);

        public void onDialogNegativeClick(DialogFragment dialog);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (PostBookingDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setTitle("Окно заказа")
                .setMessage("Укажите номер вашей карты")
                .setView(R.layout.post_booking_dialog)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onDialogPositiveClick(PostBookingDialogFragment.this);
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onDialogNegativeClick(PostBookingDialogFragment.this);
                    }
                })
                .create();
    }
}
