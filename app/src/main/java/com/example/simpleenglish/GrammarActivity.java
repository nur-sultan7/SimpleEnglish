package com.example.simpleenglish;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class GrammarActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Grammar> grammarList;
    GrammarAdapter adapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);
        recyclerView=findViewById(R.id.recyclerView);
        progressBar=findViewById(R.id.progressBar);
        grammarList=new ArrayList<>();
        adapter = new GrammarAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemViewCacheSize(8);
        recyclerView.setAdapter(adapter);
        new LoadGrammar().execute();
    }
    private class LoadGrammar extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.setGrammarList(grammarList);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Grammer");
            List<ParseObject>  objectList = null;

            try {
                objectList = query.find();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (objectList!=null)
            {
                for (ParseObject parseObject: objectList)
                {
                    ParseFile imageFile =parseObject.getParseFile("image");
                    grammarList.add(new Grammar(parseObject.getString("name"),
                            parseObject.getString("in_russian"),
                            parseObject.getString("text"),
                            imageFile.getUrl()));
                }
            }
            return null;
        }
    }
}
