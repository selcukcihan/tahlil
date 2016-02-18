package com.selcukcihan.android.tahlil;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TestCredentialsDialogFragment.TestCredentialsDialogListener {

    private TestResultFragment mFragment;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = ((TextView) findViewById(R.id.explanation_text));
        mTextView.setMovementMethod(LinkMovementMethod.getInstance());

        if (savedInstanceState == null) {
            showTestCredentialsDialog();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem item = menu.findItem(R.id.menu_item_new_query);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_query:
                showTestCredentialsDialog();
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    public void showInFragment (TestResultCollection collection) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mFragment = TestResultFragment.newInstance(collection);
        fragmentTransaction.replace(R.id.list, mFragment);
        fragmentTransaction.commit();
    }
    @Override
    public void onDialogCompleted(DialogFragment dialog, TestResultCollection collection) {
        showInFragment(collection);
    }

    @Override
    public void onFailure(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER | Gravity.LEFT, 0, 0);
        toast.show();
        if (mFragment != null) {
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(mFragment);
            fragmentTransaction.commit();
        }
        mTextView.setVisibility(View.VISIBLE);
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
        if (mFragment == null) {
            mTextView.setVisibility(View.VISIBLE);
        }
    }

    public void showTestCredentialsDialog() {
        mTextView.setVisibility(View.INVISIBLE);
        DialogFragment dialog = new TestCredentialsDialogFragment();
        dialog.show(getSupportFragmentManager(), "TestCredentialsDialogFragment");
    }
}
