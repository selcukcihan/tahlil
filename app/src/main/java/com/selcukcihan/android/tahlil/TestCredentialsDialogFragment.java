package com.selcukcihan.android.tahlil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

import java.util.List;

/**
 * Created by Selcuk on 16.2.2016.
 */
public class TestCredentialsDialogFragment extends DialogFragment implements HttpPerformingTask.HttpPerformingTaskListener {

    @Override
    public void onCompleted(List<TestResult> results) {
        mListener.onDialogPositiveClick(this, results);
    }

    /* The activity that creates an instance of this dialog fragment must
        * implement this interface in order to receive event callbacks.
        * Each method passes the DialogFragment in case the host needs to query it. */
    public interface TestCredentialsDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, List<TestResult> results);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    private TestCredentialsDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (TestCredentialsDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_testcredentials, null))
                // Add action buttons
                .setPositiveButton(R.string.query, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        HttpPerformingTask task = new HttpPerformingTask();
                        task.attach(TestCredentialsDialogFragment.this);

                        task.execute("32536157248", "7632888");

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        TestCredentialsDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
