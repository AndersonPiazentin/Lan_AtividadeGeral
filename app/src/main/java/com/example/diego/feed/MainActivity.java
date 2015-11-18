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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class MainActivity extends Activity {
    EditText title, link, description;
    Button btnFetch, bntResult;
    private String finalUrl = "http://rss.uol.com.br/feed/noticias.xml";
    private HandleXML obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = (EditText) findViewById(R.id.edt_titulo);
        link = (EditText) findViewById(R.id.edt_link);
        description = (EditText) findViewById(R.id.edt_description);

        btnFetch = (Button) findViewById(R.id.bnt_fetch);
        bntResult = (Button) findViewById(R.id.bnt_result);
        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obj = new HandleXML(finalUrl);
                obj.fetchXML();

                while (obj.parsingComplete) ;

                Random rand = new Random();
                int n = rand.nextInt(4);
                Log.i("rand", "Numero " + n);

              String[] b = getData(obj.getDados().get(n));

                title.setText(b[0]);
                link.setText(b[1]);
                description.setText(b[2]);

//                title.setText(obj.getTitle());
//                link.setText(obj.getLink());
//                description.setText(obj.getDescription());
            }
        });

        bntResult.setOnClickListener(new View.OnClickListener() {
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

//    private String[] getData(HashMap<Integer, HashSet<String>> data){
//        String[] dados = new String[data.size()];
//
//        for (int i = 0; i < data.size(); i++){
//            HashSet<String> hash = data.get(i);
//            Iterator it = hash.iterator();
//            while (it.hasNext()) {
//                Map.Entry pair = (Map.Entry)it.next();
//                Log.i("Map", pair.getKey() + " = " + pair.getValue());
//                it.remove(); // avoids a ConcurrentModificationException
//            }
//        }
//
//    }

    private String[] getData(HashSet<String> data) {
        HashSet<String> res = new HashSet<String>();
        String[] dados = new String[3];
        Iterator it = data.iterator();
        int i = 0;
        while (it.hasNext()) {
            dados[i] = String.valueOf(it.next());
            i++;
        }

        return dados;
    }

}

