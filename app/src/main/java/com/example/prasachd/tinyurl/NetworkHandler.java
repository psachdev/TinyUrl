package com.example.prasachd.tinyurl;

import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;

import java.io.IOException;

/**
 * Created by prasachd on 2/5/16.
 */
public class NetworkHandler extends AsyncTask<Void,Void,String> {
    String url;
    AndroidHttpClient androidHttpClient;
    MainActivity context;

    public NetworkHandler(MainActivity context, String url) {
        super();
        this.url=url;
        this.androidHttpClient=null;
        this.context=context;
    }

    @Override
    protected String doInBackground(Void... params) {
        this.androidHttpClient = AndroidHttpClient.newInstance("");
        try {
            Uri uri = new Uri.Builder().scheme("http").
                    authority("tinyurl.com").
                    path("api-create.php").
                    appendQueryParameter("url", url).build();
            String url = uri.toString();
            HttpGet httpGet = new HttpGet(url);
            JSONResponseHandler jsonResponseHandler = new JSONResponseHandler();
            return this.androidHttpClient.execute(httpGet, jsonResponseHandler);
        }catch(Exception e){
            return "";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(this.androidHttpClient!=null){
            androidHttpClient.close();
            androidHttpClient=null;
        }
        if(s!=null & s.length()>0){
            this.context.addResultToCache(url, s);
            this.context.addShowTinyUrlFragment(s);
        }
    }

    public class JSONResponseHandler implements ResponseHandler<String>{
        @Override
        public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
            String response = new BasicResponseHandler().handleResponse(httpResponse);
            return response;
        }
    }
}
