package griffits.fvi.at.ua.currencyconverterminfin;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

public class MainActivity extends Activity {
    private static final String LOG_DEBUG = "MainActivity" ;
    private TextView usd, eur, uan;
    private Button converter;
    private EditText enter_et;
    private Spinner spinner_currency;
    private int index_arrayCurrencyData;
    private double inputValue;

    String[] currencyData = {"USD", "EUR", "UAN"};
    String[] results = new String[10];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_DEBUG, "onCreate");
        init();
        bindDataSpinner();

    }

    private void init(){
        usd = (TextView)findViewById(R.id.usd_tv);
        eur = (TextView)findViewById(R.id.eur_tv);
        uan = (TextView)findViewById(R.id.frn_tv);
        converter = (Button)findViewById(R.id.button_converter);
        enter_et = (EditText)findViewById(R.id.enter_number);
        spinner_currency = (Spinner)findViewById(R.id.spinner_currency);
        Log.d(LOG_DEBUG, "init");
    }

    // bind currencyData with spinner
    public void bindDataSpinner(){

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                currencyData);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner_currency.setAdapter(adapter);

        // Listener for spinner
        spinner_currency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                index_arrayCurrencyData = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void btnConverter(View v){
        usd.setText("Waite...");
        eur.setText("Waite...");
        uan.setText("Waite...");
        Log.d(LOG_DEBUG, "onClick btn converter");

        String enterValue = enter_et.getText().toString().trim();
        if (enterValue.length() > 0 ){  //&& enterValue.equals(".")
            inputValue = Double.parseDouble(enterValue);
            Log.d(LOG_DEBUG, "btn converter input value = " + inputValue);
           new calculate().execute();
            Log.d(LOG_DEBUG, "start calculate ");
        }
    }

    public class calculate extends AsyncTask<String, String, String[]>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(LOG_DEBUG, "start method  onPreExecute");
        }

        @Override
        protected void onPostExecute(String[] strings) {
            double usdValue, eurValue;
            usdValue = Double.parseDouble(results[0]);
            eurValue = Double.parseDouble(results[1]);
            DecimalFormat decimalFormat = new DecimalFormat("#.##");

            if (usdValue == 0 && eurValue == 0){
                Toast.makeText(getApplicationContext(), "data update", Toast.LENGTH_SHORT).show();
            }else if (index_arrayCurrencyData == 0){
               // USD to USD
               usd.setText(""+(inputValue * 1));
                // USD to UAN
               uan.setText(""+(inputValue * usdValue));
               // USD to EUR
               eur.setText(""+decimalFormat.format((usdValue/eurValue)*inputValue));

           } else if (index_arrayCurrencyData == 1){
               // EUR to USD
                usd.setText(""+decimalFormat.format((eurValue/usdValue)*inputValue));
               // EUR to EUR
                eur.setText(""+(inputValue*1));
               // EUR to UAN
               uan.setText(""+(eurValue*inputValue));
           } else if (index_arrayCurrencyData == 2){
                //UAN to USD
                usd.setText(""+(decimalFormat.format(inputValue/usdValue)));
                //UAN to EUR
                eur.setText(""+(decimalFormat.format(inputValue/eurValue)));
                //UAN to UAN
                uan.setText(""+(inputValue*1));
           }
            Log.d(LOG_DEBUG, "finish method  onPostExecute");
        }

        @Override
        protected String[] doInBackground(String... params) {
            Log.d(LOG_DEBUG, "start method  doInBackground");
                String uRl;
                try {
                    uRl = getJson("http://api.minfin.com.ua/nbu/b03af6a10c910fb83692634f77f046021a210bcf");

                    JSONObject jsonObject = new JSONObject(uRl);

                    JSONObject usdJSON = jsonObject.getJSONObject("usd");
                    JSONObject eurJSON = jsonObject.getJSONObject("eur");
                    results[0] = usdJSON.getString("ask");
                    results[1] = eurJSON.getString("ask");

                    Log.d(LOG_DEBUG, "results " + " usd: "+results[0]+"\n"  +"EUR "+results[1]);
                } catch (JSONException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            return results;
        }
    }


    public String getJson(String url) throws ClientProtocolException,IOException {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        InputStream content = entity.getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(content));
        String con;
        while ((con = reader.readLine())!= null){
            builder.append(con);
        }

        return builder.toString();
    }
}
