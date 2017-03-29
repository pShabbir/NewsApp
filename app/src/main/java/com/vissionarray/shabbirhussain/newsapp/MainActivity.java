package com.vissionarray.shabbirhussain.newsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView txt;
    ProgressDialog pd;
    String returnNews;
    JSONObject jsonObject,jsonObject1;
    JSONArray news;
    ArrayList<String> arr;
    int pos;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        new JsonTask().execute("https://newsapi.org/v1/articles?source=the-hindu&sortBy=latest&apiKey=899f63f5b7084937bccd7419bb8be942");




    }


    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();


        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }
                returnNews = new String(buffer.toString());
                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                 arr = new ArrayList<>();
                jsonObject=new JSONObject(result);
                 news = jsonObject.getJSONArray("articles");
                for (int i = 0; i < news.length(); i++) {
                     jsonObject1 = news.getJSONObject(i);
                    arr.add(jsonObject1.getString("title"));
                }

                //txt.setText(returnNews);

            } catch (Exception e) {
                e.printStackTrace();
            }

            ListView ls=(ListView)findViewById(R.id.listView);
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(MainActivity.this,R.layout.support_simple_spinner_dropdown_item,arr);
            ls.setAdapter(adapter);
            i=new Intent(MainActivity.this,Main2Activity.class);
            i.putExtra("test",result);


            ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //String pos=String.valueOf(position);
//                    Toast.makeText(MainActivity.this,pos,Toast.LENGTH_LONG).show();
                   pos=position;


                    i.putExtra("pos",pos);
                    startActivity(i);

                    //Animate
                   // overridePendingTransition( R.anim.slide_up_animation, R.anim.slide_down_animation );
                }
            });




        }

    }
}
