package com.example.simpleenglish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import okhttp3.internal.http2.Header;

public class SentencesActivity extends AppCompatActivity {

    private FlexboxLayout constraintLayoutSentence;
    private FlexboxLayout constraintLayoutWords;
    private Toast toast;
    private Random random;
    private TextView textViewWord1;
    private TextView textViewWord2;
    private TextView textViewWord3;
    private TextView textViewWord4;
    private TextView textViewSentenceInRussian;
    private static String originalSentence="I going to school";
    private static String originalSentenceInRussian="Я иду в школу";
    private static String wrongWord1="go";
    private static String wrongWord2="by";
    private List<Sentence> listSentences;
    private static int currentSentence;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentence);
        toast=new Toast(this);
        random=new Random();
        constraintLayoutSentence=findViewById(R.id.constraintLayoutMakeSentence);
        constraintLayoutWords=findViewById(R.id.constraintLayoutWords);
        textViewSentenceInRussian=findViewById(R.id.textViewSentenceInRussian);
        constraintLayoutSentence.setOnDragListener(new MyDragListener());
        constraintLayoutWords.setOnDragListener(new MyDragListener());
        new LoadSentences().execute();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void generate()
    {
        currentSentence = random.nextInt(listSentences.size());
        Sentence sentence = listSentences.get(currentSentence);
        originalSentenceInRussian=sentence.getInRussian();
        originalSentence=sentence.getOriginal();
        wrongWord1=sentence.getWrongWord1();
        wrongWord2=sentence.getWrongWord2();
        textViewSentenceInRussian.setText(originalSentenceInRussian);
        List<String> words = new ArrayList<>(Arrays.asList(originalSentence.split(" ")));
        words.add(wrongWord1);
        words.add(wrongWord2);
        Collections.shuffle(words);
        for (String word: words)
        {
            TextView textViewWord = new TextView(this);
            textViewWord.setBackgroundResource(R.drawable.textview_rounded);
            textViewWord.setPadding(18,4,18,4);
            textViewWord.setTextSize(24);
            textViewWord.setClickable(true);
            textViewWord.setTypeface(textViewWord.getTypeface(), Typeface.BOLD);
            textViewWord.setOnTouchListener(new MyTouchListener());
            FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT,FlexboxLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,28,0,0);
            textViewWord.setLayoutParams(params);
            textViewWord.setText(word);
            constraintLayoutWords.addView(textViewWord);
        }
    }

    public void checkSentence(View view) {
        StringBuilder stringBuilder=new StringBuilder();
        for (int i=0;i<constraintLayoutSentence.getChildCount();i++)
        {
            TextView textView = (TextView) constraintLayoutSentence.getChildAt(i);
            stringBuilder.append(" ").append(textView.getText().toString());
        }
        if (stringBuilder.toString().trim().equals(originalSentence))
        {
            if (toast!=null)
            {
                toast.cancel();
                toast= Toast.makeText(SentencesActivity.this,getText(R.string.right) , Toast.LENGTH_SHORT);
                toast.show();
            }
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    listSentences.remove(currentSentence);
                    constraintLayoutWords.removeAllViews();
                    constraintLayoutSentence.removeAllViews();
                    generate();
                }
            },2000);
        }
        else
        {

            if (toast!=null)
            {
                toast.cancel();
                toast= Toast.makeText(SentencesActivity.this,getText(R.string.wrong) , Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    private static final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    class MyDragListener implements View.OnDragListener {


        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_EXITED:

                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    FlexboxLayout container = (FlexboxLayout) v;
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:

                default:
                    break;
            }
            return true;
        }
    }
    private class LoadSentences extends AsyncTask<Void,Void,List<Sentence>>
    {

        @Override
        protected void onPostExecute(List<Sentence> sentencesList) {
            super.onPostExecute(sentencesList);
            listSentences=sentencesList;
            Collections.shuffle(listSentences);
            generate();
        }

        @Override
        protected List<Sentence> doInBackground(Void... voids) {
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Sentences");
            List<ParseObject>  objectList = null;
            List<Sentence> sentenceList=new ArrayList<>();
            try {
                objectList = query.find();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (objectList!=null)
            {
                for (ParseObject parseObject: objectList)
                {
                    sentenceList.add(new Sentence(parseObject.getString("original"),
                            parseObject.getString("in_russian"),
                            parseObject.getString("wrong_word_1"),
                            parseObject.getString("wrong_word_2")));
                }
            }
            return sentenceList;
        }
    }
}
