package com.selcukcihan.android.tahlil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by SELCUKCI on 16.2.2016.
 */
public class HttpPerformingTask extends AsyncTask<String, Void, List<TestResult>> {

    private ProgressDialog mDialog;
    private String mTCKN;
    private String mRegistrationCode;
    private String mNotFoundMessage;

    public interface HttpPerformingTaskListener {
        public void onCompleted(List<TestResult> results);
        public void onFailure(String message);
    }

    private TestCredentialsDialogFragment mListener;

    public void attach(TestCredentialsDialogFragment listener) {
        mListener = listener;
        mDialog = new ProgressDialog(listener.getContext());
    }

    private Exception mException;
    protected List<TestResult> doInBackground(String... params) {
        try {
            mTCKN = params[0];
            mRegistrationCode = params[1];
            HashMap<String, String> postParams = new HashMap<String, String>();
            postParams.put("tbTCKimlikNo", mTCKN);
            postParams.put("tbKayitNo", mRegistrationCode);

            HttpCommunicator communicator = new HttpCommunicator();

            return communicator.fetch("http://labim.ihs.gov.tr/labim/HastaTetkikSonucSorgulama.aspx",
                    "http://labim.ihs.gov.tr/labim/HastaTetkikSonucYazdir.aspx",
                    postParams);
        } catch (Exception ex) {
            mException = ex;
            return null;
        }
    }

    @Override
    protected void onPreExecute() {
        mNotFoundMessage = mDialog.getContext().getResources().getString(R.string.not_found);
        mDialog.setMessage(mDialog.getContext().getResources().getString(R.string.waiting));
        mDialog.show();
    }


    public Exception getException() { return mException; }

    @Override
    protected void onPostExecute(List<TestResult> results) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        if (results != null && results.size() > 0) {
            Collections.sort(results, new Comparator<TestResult>() {
                @Override
                public int compare(TestResult r1, TestResult r2) {
                    if (!r1.Normal() && !r2.Normal()) {
                        return r1.getName().compareTo(r2.getName());
                    } else if (!r2.Normal()) {
                        return 1;
                    } else if (!r1.Normal()) {
                        return -1;
                    }
                    else {
                        return r1.getName().compareTo(r2.getName());
                    }
                }
            });
            mListener.onCompleted(results);
        } else {
            if (results == null) {
                mListener.onFailure(mException.getLocalizedMessage());
            } else {
                mListener.onFailure(String.format(mNotFoundMessage, mTCKN, mRegistrationCode));
            }
        }
    }
}
