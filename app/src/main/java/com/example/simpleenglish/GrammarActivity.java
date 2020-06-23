package com.example.simpleenglish;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);
        recyclerView=findViewById(R.id.recyclerView);
        grammarList=new ArrayList<>();

    }
    private class LoadGrammar extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
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
