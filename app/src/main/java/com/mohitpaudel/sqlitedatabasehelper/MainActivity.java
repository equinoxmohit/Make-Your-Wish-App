package com.mohitpaudel.sqlitedatabasehelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mohitpaudel.sqlitedatabasehelper.data.DatabaseHandler;
import com.mohitpaudel.sqlitedatabasehelper.model.MyWish;

public class MainActivity extends AppCompatActivity {

    private EditText title;
    private EditText content;
    private Button saveButton;
    private DatabaseHandler dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dba=new DatabaseHandler(MainActivity.this);


        title=(EditText) findViewById(R.id.titleEditText);
        content=(EditText) findViewById(R.id.wishEditText);
        saveButton=(Button) findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToDb();
            }
        });
    }

    private void saveToDb() {
        MyWish wish=new MyWish();
        wish.setTitle(title.getText().toString().trim());
        wish.setContent(content.getText().toString().trim());

        dba.addWishes(wish);
        dba.close();

        title.setText("");
        content.setText("");

       //Intent intent=new Intent(MainActivity.this, DisplayWishesActivity.class);
       //startActivity(intent);c

    }
}
