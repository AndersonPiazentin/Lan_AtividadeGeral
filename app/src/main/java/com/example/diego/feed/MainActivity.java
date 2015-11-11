package com.example.diego.feed;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
    EditText title, link, description;
    Button b1, b2;
    private String finalUrl = "http://www.ragazzid.com.br/rss.xml";
    private HandleXML obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = (EditText) findViewById(R.id.editText);
        link = (EditText) findViewById(R.id.editText2);
        description = (EditText) findViewById(R.id.editText3);

        b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obj = new HandleXML(finalUrl);
                obj.fetchXML();

                while (obj.parsingComplete) ;
                title.setText(obj.getTitle());
                link.setText(obj.getLink());
                description.setText(obj.getDescription());
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, Second.class);
                startActivity(in);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

}