package com.example.simpleenglish;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class GrammarDetailActivity extends AppCompatActivity {

    ImageView imageViewGrammarImage;
    TextView textViewGrammarName;
    TextView textViewGrammarText;
    TextView textViewExample;
    TextView textViewExampleInRussian;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar_detail);
        imageViewGrammarImage=findViewById(R.id.imageViewGrammarImage);
        textViewGrammarName=findViewById(R.id.textViewGrammarName);
        textViewGrammarText=findViewById(R.id.textViewGrammarText);
        textViewExample=findViewById(R.id.textViewGrammarExample);
        textViewExampleInRussian=findViewById(R.id.textViewGrammarExampleInRussian);
        if (getIntent()!=null)
        {
            Picasso.get().load(getIntent().getStringExtra("image"))
                    .into(imageViewGrammarImage);
            textViewGrammarName.setText(getIntent().getStringExtra("name"));
            textViewGrammarText.setText(getIntent().getStringExtra("text"));
            textViewExample.setText(getIntent().getStringExtra("example"));
            textViewExampleInRussian.setText(getIntent().getStringExtra("example_in_russian"));
        }
    }
}
