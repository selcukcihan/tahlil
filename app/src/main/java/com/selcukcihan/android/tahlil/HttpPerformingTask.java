package com.selcukcihan.android.tahlil;

import android.app.Activity;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by SELCUKCI on 16.2.2016.
 */
public class HttpPerformingTask extends AsyncTask<String, Void, List<TestResult>> {
    public interface HttpPerformingTaskListener {
        public void onCompleted(List<TestResult> results);
    }

    private HttpPerformingTaskListener mListener;

    public void attach(HttpPerformingTaskListener listener) {
        mListener = listener;
    }

    private Exception mException;
    protected List<TestResult> doInBackground(String... params) {
        try {
            HashMap<String, String> postParams = new HashMap<String, String>();
            postParams.put("tbTCKimlikNo", params[0]);
            postParams.put("tbKayitNo", params[1]);

            HttpCommunicator communicator = new HttpCommunicator();

            return communicator.fetch("http://labim.ihs.gov.tr/labim/HastaTetkikSonucSorgulama.aspx",
                    "http://labim.ihs.gov.tr/labim/HastaTetkikSonucYazdir.aspx",
                    postParams);
        } catch (Exception ex) {
            mException = ex;
            return null;
        }
    }

    public Exception getException() { return mException; }

    protected void onPostExecute(List<TestResult> results) {
        mListener.onCompleted(results);
    }
}
