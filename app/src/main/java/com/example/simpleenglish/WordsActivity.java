package com.example.simpleenglish;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class WordsActivity extends AppCompatActivity {

    private TextView textViewTimer;
    private boolean end_of_time_red_zone;
    private boolean isEndGame;
    private Toast toast;
    private List<Word> wordList;
    private TextView textViewAnswer1;
    private TextView textViewAnswer2;
    private TextView textViewWordInRussian;
    private TextView textViewPoints;
    private ImageView imageViewWordImage;
    private List<TextView> textViewListAnswers;
    private static int rightAnswerId;
    private static int wrongAnswerId;
    private static int rightAnswerTextViewId;
    private static int wrongAbswerTextViewId;
    private static Word rightAnswer;
    private static Word wrongAnswer;
    private Random random;
    private ProgressBar progressBar;
    private List<Integer> answeredWordsIds;
    private static int preWord;
    private static boolean isLast;
    private static int countAnswers;
    private static int countRightAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);
        textViewTimer=findViewById(R.id.textViewTimer);
        random=new Random();
        end_of_time_red_zone=false;
        isEndGame=false;
        toast=new Toast(this);
        wordList=new ArrayList<>();
        textViewAnswer1=findViewById(R.id.textViewAnswer1);
        textViewAnswer2=findViewById(R.id.textViewAnswer2);
        textViewPoints=findViewById(R.id.textViewPoints);
        textViewAnswer1.setText(null);
        textViewAnswer2.setText(null);
        textViewWordInRussian=findViewById(R.id.textViewWordInRussian);
        textViewWordInRussian.setText(null);
        textViewListAnswers=new ArrayList<>();
        textViewListAnswers.add(textViewAnswer1);
        textViewListAnswers.add(textViewAnswer2);
        imageViewWordImage=findViewById(R.id.imageViewWordImage);
        progressBar=findViewById(R.id.progressBarWord);
        progressBar.setVisibility(View.VISIBLE);
        answeredWordsIds=new ArrayList<>();
        isLast=false;
        countAnswers=0;
        countRightAnswers=0;


        CountDownTimer countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int currentTime = ((int) millisUntilFinished / 1000)+1;
                textViewTimer.setText(getTime(currentTime));
                if (currentTime<10 && !end_of_time_red_zone)
                {
                    textViewTimer.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                    end_of_time_red_zone=true;
                }
            }

            @Override
            public void onFinish() {
                isEndGame=true;
                textViewTimer.setText(getString(R.string.title_timer));
                if (toast!=null)
                {
                    toast.cancel();
                    toast= Toast.makeText(getApplicationContext(),getText(R.string.time_is_out) , Toast.LENGTH_SHORT);
                    toast.show();
                }

                /*Intent intent  = new Intent(getApplicationContext(), EndGameActivity.class);
                intent.putExtra("points",countRightAnswers);
                startActivity(intent);
                MainActivity.this.finish();*/

            }
        };
        countDownTimer.start();
        new LoadWords().execute();
    }
    @SuppressLint("DefaultLocale")
    public void updetePoints() {
        textViewPoints.setText(String.format("%d/%d", countRightAnswers, countAnswers));
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void makeWordTest()
    {
        progressBar.setVisibility(View.VISIBLE);
        textViewAnswer1.setText(null);
        textViewAnswer2.setText(null);
        imageViewWordImage.setImageDrawable(null);
        makeRightAnswer();
        makeWrongAnswer();
        Picasso.get()
                .load(rightAnswer.getImage())
                .into(imageViewWordImage);
        textViewWordInRussian.setText(rightAnswer.getIn_russian());
        rightAnswerTextViewId=random.nextInt(textViewListAnswers.size());
        textViewListAnswers.get(rightAnswerTextViewId).setText(rightAnswer.getIn_english());
        do {
            wrongAbswerTextViewId=random.nextInt(textViewListAnswers.size());
        }while (wrongAbswerTextViewId==rightAnswerTextViewId);
        textViewListAnswers.get(wrongAbswerTextViewId).setText(wrongAnswer.getIn_english());
        progressBar.setVisibility(View.GONE);

    }
    private void makeRightAnswer()
    {
        do {
            rightAnswerId = random.nextInt(wordList.size());
        }while (answeredWordsIds.contains(rightAnswerId) || rightAnswerId==preWord);
        rightAnswer=wordList.get(rightAnswerId);
    }
    private void makeWrongAnswer()
    {
        do {
            wrongAnswerId=random.nextInt(wordList.size());
        }while (rightAnswerId==wrongAnswerId);
        wrongAnswer=wordList.get(wrongAnswerId);
    }

    public String getTime(int seconds)
    {
        int minutes = seconds/60;
        int secs = seconds%60;
        return String.format(getString(R.string.time_format),minutes,secs);
    }

    public void checkAnswer(View view) {
        final TextView textView = (TextView) view;
        int tag = Integer.parseInt((String) textView.getTag());
        countAnswers++;
        if (tag==rightAnswerTextViewId)
        {
            textView.setBackgroundResource(R.drawable.textview_rounded_rught_answer);
            answeredWordsIds.add(rightAnswerId);
            countRightAnswers++;
        }
        else
        {
            textView.setBackgroundResource(R.drawable.textview_rounded_wrong_answer);
        }
        preWord=rightAnswerId;
        updetePoints();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                textView.setBackgroundResource(R.drawable.textview_rounded);

                if ((rightAnswerId==preWord && isLast))
                {
                    if (toast!=null)
                    {
                        toast.cancel();
                        toast= Toast.makeText(getApplicationContext(),getText(R.string.time_is_out) , Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                else {
                    if (wordList.size()-answeredWordsIds.size()==1)
                    {
                        isLast=true;
                    }
                    makeWordTest();
                }
            }
        }, 1000);
    }


    @SuppressLint("StaticFieldLeak")
    private class LoadWords extends AsyncTask<Void,Void, List<Word>>
    {
        @Override
        protected void onPostExecute(List<Word> words) {
            super.onPostExecute(words);
                wordList.addAll(words);
            makeWordTest();
        }

        @Override
        protected List<Word> doInBackground(Void... voids) {
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Words");
           List<ParseObject>  objectList = null;
           List<Word> wordList=new ArrayList<>();
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
                    wordList.add(new Word(imageFile.getUrl(),
                            parseObject.getString("in_english"),
                            parseObject.getString("in_russian")));
                }
            }
            return wordList;
        }
    }
}
