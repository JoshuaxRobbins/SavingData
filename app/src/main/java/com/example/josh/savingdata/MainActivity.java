package com.example.josh.savingdata;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.josh.savingdata.model.Person;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {

    private EditText etSharedPref;
    private TextView tvSharedPref;
    private EditText etPersonName;
    private EditText etPersonAge;
    private EditText etPersonGender;
    private PersonDatabase personDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
        personDatabase = new PersonDatabase((getApplicationContext()));

        ArrayAdapter<String> personAdapter = new ArrayAdapter<>()(this,android.R.layout.simple_list_item_1, new ArrayList<String>());

    }

    private void bindViews() {
        etSharedPref = findViewById(R.id.etSharedPref);
        tvSharedPref = findViewById(R.id.tvSharedPref);

        etPersonName = findViewById(R.id.etPersonName);
        etPersonAge = findViewById(R.id.etPersonAge);
        etPersonGender = findViewById(R.id.etPersonGender);
        ListView lvPerson = findViewById(R.id.lvPerson);
    }


    public void onSharedPreferences(View view) {

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch(view.getId()){
            case R.id.btnSaveData:
                editor.putString("edittext",etSharedPref.getText().toString());
                editor.apply();
                Toast.makeText(this, "Saved",Toast.LENGTH_SHORT).show();

                break;
            case R.id.btnGetData:
                String etValue = sharedPreferences.getString("edittext", "Default String");
                tvSharedPref.setText(etValue);
                Toast.makeText(this,"Data Retrieved", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void onSQLiteDatabase(View view) {
        String personName = etPersonName.getText().toString();
        String personAge = etPersonAge.getText().toString();
        String personGender = etPersonGender.getText().toString();

        Person person = new Person(personName,personAge,personGender);

        switch(view.getId()){
            case R.id.btnSavePerson:
                long rowId = personDatabase.savePerson(person);
                break;
            case R.id.btnGetAllPerson:
                for(Person person1 : personDatabase.getPeople()){
                    Log.d("meh", "onSQLiteDatabase: " + person1);
                }
                break;
        }
    }
}
