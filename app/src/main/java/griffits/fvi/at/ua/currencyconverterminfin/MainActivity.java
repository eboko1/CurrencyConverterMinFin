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
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends Activity {
    private static final String LOG_DEBUG = "MainActivity" ;
    private TextView usd, eur, rub;
    private Button converter;
    private EditText enter_et;
    private Spinner spinner_currency;
    private int index_arrayCurrencyData;
    private double inputValue;

    String[] currencyData = {"USD", "EUR", "RUB"};


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
        rub = (TextView)findViewById(R.id.frn_tv);
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
        rub.setText("Waite...");
        Log.d(LOG_DEBUG, "onClick btn converter");

        String enterValue = enter_et.getText().toString().trim();
        if (enterValue.length() > 0 && enterValue.equals(".")){
            inputValue = Double.parseDouble(enterValue);
           // new calculate().execuate();
        }
    }

    public class calculate extends AsyncTask<String, String, String[]>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
        }

        @Override
        protected String[] doInBackground(String... params) {
            if (index_arrayCurrencyData == 0){
                String url_;
            }
            return new String[0];
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
