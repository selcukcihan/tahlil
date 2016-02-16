package com.selcukcihan.android.tahlil;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by SELCUKCI on 16.2.2016.
 */
public class HttpStuffTask extends AsyncTask<String, Void, String> {
    private CookieManager mCookieManager;
    private static final String COOKIE_KEYS [] = {"__EVENTARGUMENT", "__VIEWSTATE", "__VIEWSTATEGENERATOR", "__EVENTVALIDATION"};
    private HashMap<String, String> mCookies;

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        mCookies.putAll(params);
        boolean first = true;
        for(Map.Entry<String, String> entry : mCookies.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    private void parseAndExtractASPStuff(String response) {
        mCookies = new HashMap<String, String>();
        Document document = Jsoup.parse(response);
        for (int i = 0; i < HttpStuffTask.COOKIE_KEYS.length; i++) {
            String cookieValue = document.select("#" + HttpStuffTask.COOKIE_KEYS[i]).attr("value");
            mCookies.put(HttpStuffTask.COOKIE_KEYS[i], cookieValue);
        }
        mCookies.put("__EVENTTARGET", "Button1");
    }

    private String performGetCall(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            //InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            return response.toString();
        } finally {
            urlConnection.disconnect();
        }
    }

    private String performPostCall(String requestURL, HashMap<String, String> postDataParams) {
        URL url;
        String response = "";
        try {
            if (mCookieManager == null) {
                mCookieManager = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
                CookieHandler.setDefault(mCookieManager);
            }
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();
            Map<String, List<String>> mp = conn.getHeaderFields();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response="";

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    protected String doInBackground(String... params) {
        try {
            /*
            URL url = new URL(params[0]);
            String COOKIES_HEADER = "Set-Cookie";
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            java.net.CookieManager msCookieManager = new java.net.CookieManager();

            Map<String, List<String>> headerFields = connection.getHeaderFields();
            List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);

            if (cookiesHeader != null) {
                for (String cookie : cookiesHeader) {
                    msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
                }
            }*/
            HashMap<String, String> postParams = new HashMap<String, String>();
            postParams.put("tbTCKimlikNo", "32536157248");
            postParams.put("tbKayitNo", "7632888");
            //performPostCall(params[0], postParams);
            String response = performGetCall(params[0]);
            parseAndExtractASPStuff(response);
            performPostCall(params[0], postParams);
            String response2 = performGetCall(params[1]);
            System.out.println("devam");
        } catch (Exception ex) {
            String str = ex.toString();
        }
        return null;
    }

    protected void onPostExecute(String feed) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }
}
