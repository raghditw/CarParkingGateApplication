package com.example.assignment2_it_it342;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etx_PN, etx_color, etx_make;
    TextView numbers;
    Button insert, delete, view;
    DBHelper DB;
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etx_PN = findViewById(R.id.etx_PN);
        etx_color = findViewById(R.id.etx_color);
        etx_make = findViewById(R.id.etx_make);
        numbers = findViewById(R.id.numbers);
        insert = findViewById(R.id.insert);
        delete = findViewById(R.id.delete);
        view = findViewById(R.id.view);
        DB = new DBHelper(this);
        counter = DB.getProfilesCount();
        numbers.setText(""+ counter);


        insert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String PNTXT = etx_PN.getText().toString();
                String colorTXT = etx_color.getText().toString();
                String makeTXT = etx_make.getText().toString();

                boolean CheckInsertData = DB.insertParkingData(PNTXT, colorTXT,makeTXT);
                if (CheckInsertData==true)
                    Toast.makeText(MainActivity.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
                counter = DB.getProfilesCount();
                numbers.setText(""+ counter);
            }
        });
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String PNTXT = etx_PN.getText().toString();
                boolean CheckDeletetData = DB.deleteParkingData(PNTXT);
                if (CheckDeletetData==true)
                    Toast.makeText(MainActivity.this, " Entry Deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, " Entry Not Deleted", Toast.LENGTH_SHORT).show();
                counter = DB.getProfilesCount();
                numbers.setText(""+ counter);
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DB.getdata();
                if(res.getCount()==0){
                    Toast.makeText(MainActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("etx_PN :" +res.getString(0)+"\n");
                    buffer.append("etx_color :" +res.getString(1)+"\n");
                    buffer.append("etx_make :" +res.getString(2)+"\n\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("User Entries");
                builder.setMessage(buffer.toString());
                builder.show();
            }

        });


    }
}