package griffits.fvi.at.ua.currencyconverterminfin;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {
    private TextView usd, eur, rub;
    private Button converter;
    private EditText enter_et;
    private Spinner spinner_currency;
    private int index_array;
    String[] currencyData = {"USD", "EUR", "RUB"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    }

    // bind currencyData with spinner
    public void bindDataSpinner(){

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                currencyData);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);


        spinner_currency.setAdapter(adapter);

        spinner_currency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                index_array = position;
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
    }
}
