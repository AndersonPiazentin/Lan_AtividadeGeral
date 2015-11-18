package com.example.diego.feed;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class HandleXML {
    private HashMap<Integer, HashSet<String>> dados = new HashMap<Integer, HashSet<String>>();
    private HashSet<String> hashSet;
    private String title = "title";
    private int i = 0;
    private int controle = 0;
    private String link = "link";
    private String description = "description";
    private String urlString = null;
    private XmlPullParserFactory xmlFactoryObject;
    public volatile boolean parsingComplete = true;

    public HandleXML(String url) {
        this.urlString = url;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public HashMap<Integer, HashSet<String>> getDados(){
        return this.dados;
    }

    public void parseXMLAndStoreIt(XmlPullParser myParser) {
        int event;
        String text = null;

        try {
            event = myParser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();
                if(controle == 0){
                    hashSet = new HashSet<String>();
                }

                switch (event) {
                    case XmlPullParser.START_TAG:
                        break;

                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:

                        if (name.equals("title")) {
                            hashSet.add(text);
                            controle++;
                        } else if (name.equals("link")) {
                            hashSet.add(text);
                            controle++;
                        } else if (name.equals("pubDate")) {
                            hashSet.add(text);
                            controle++;
                        }

                        if(controle > 2){
                            dados.put(i, hashSet);
                            i++;
                            controle=0;
                        }

                        break;
                }

                event = myParser.next();

            }

            parsingComplete = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchXML() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);

                    // Bora lá!
                    conn.connect();
                    InputStream stream = conn.getInputStream();

                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myparser = xmlFactoryObject.newPullParser();

                    myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    myparser.setInput(stream, null);

                    parseXMLAndStoreIt(myparser);
                    stream.close();
                } catch (Exception e) {

                }
            }
        });
        thread.start();
    }
}