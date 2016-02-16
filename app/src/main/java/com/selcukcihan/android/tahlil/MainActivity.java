package com.selcukcihan.android.tahlil;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements TestCredentialsDialogFragment.TestCredentialsDialogListener {

    private TestResultFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showTestCredentialsDialog();
    }

    public void showInFragment (List<TestResult> results) {
        mFragment = TestResultFragment.newInstance(results);
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.list, mFragment);
        fragmentTransaction.commit();
    }
    @Override
    public void onDialogPositiveClick(DialogFragment dialog, List<TestResult> results) {
        showInFragment(results);
    }

    public void simpleShow(List<TestResult> results) {
        ListView listView = (ListView) findViewById(R.id.list);

        // Defined Array values to show in ListView
        String[] values = new String[results.size()];
        for (int i = 0; i < values.length; i++) {
            values[i] = results.get(i).toString();
        }
        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
    }

    public void showTestCredentialsDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new TestCredentialsDialogFragment();
        dialog.show(getSupportFragmentManager(), "TestCredentialsDialogFragment");
    }
}
