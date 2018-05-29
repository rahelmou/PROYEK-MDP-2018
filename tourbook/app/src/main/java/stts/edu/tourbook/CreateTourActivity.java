package stts.edu.tourbook;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class CreateTourActivity extends AppCompatActivity {
    //Button btnPergi, btnPulang;
    //int tglPergi, blnPergi, thnPergi, tglPulang, blnPulang, thnPulang;
    EditText etPergi, etPulang;
    Button btnCreate;
    String tglPergi, tglPulang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tour);
       // btnPergi =

        //btnPulang = findViewById(R.id.btnPulang);
        btnCreate = findViewById(R.id.button3);
        etPergi = findViewById(R.id.editText5);
        etPulang = findViewById(R.id.editText6);
    }

    /*public void btnClick(View v){
        final Calendar c = Calendar.getInstance();
        tglPergi = c.get(Calendar.DAY_OF_MONTH);
        blnPergi = c.get(Calendar.MONTH);
        thnPergi = c.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int thn, int bln, int day) {
                tampung = (day + "/" + bln + "/" + thn).toString();
            },thnPergi, blnPergi, tglPergi);
            datePickerDialog.show()
        }

    }*/
    public void btnClick(View v){
        tglPergi = etPergi.getText().toString();
        tglPulang = etPulang.getText().toString();
        Intent i = new Intent(this,HomeActivity.class);
        startActivity(i);
    }
}
