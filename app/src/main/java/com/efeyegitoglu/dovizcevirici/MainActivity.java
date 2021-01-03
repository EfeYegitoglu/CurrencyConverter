package com.efeyegitoglu.dovizcevirici;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView tryText;
    TextView cadText;
    TextView usdText;
    TextView chfText;
    TextView jpyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tryText=findViewById(R.id.tryText);
        usdText=findViewById(R.id.usdTxt);
        cadText=findViewById(R.id.cadText);
        chfText=findViewById(R.id.chfText);
        jpyText=findViewById(R.id.jpyText);

    }

    public void getRates(View view){

        DownloadData downloadData=new DownloadData();
        try {
            String url="http://data.fixer.io/api/latest?access_key=33e34c562aeeae635052584d1ebe9999&format=1";
            downloadData.execute(url);


        }catch (Exception e){

        }


    }

    private  class DownloadData extends AsyncTask<String,Void,String> {


        @Override
        protected String doInBackground(String... strings) {

            String result="";
            URL url;
            HttpURLConnection httpURLConnection;

            try {

                url=new URL(strings[0]);
                httpURLConnection=(HttpURLConnection) url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream);

                int data=inputStreamReader.read();

                while (data>0){

                    char character=(char) data;
                    result += character;
                    data=inputStreamReader.read();


                }

                return result;

            }catch (Exception e){
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

               // System.out.println("alnan data: "+s);

            try {
                JSONObject jsonObject=new JSONObject(s);
                String rates=jsonObject.getString("rates");
                JSONObject jsonObject1=new JSONObject(rates);

                String turkishLira=jsonObject1.getString("TRY");
                tryText.setText("TRY: " +turkishLira);

                String cad=jsonObject1.getString("CAD");
                cadText.setText("CAD: " +cad);


            }catch (Exception e){

            }
        }
    }
}
