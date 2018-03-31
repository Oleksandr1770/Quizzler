package com.londonappbrewery.quizzler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button trueButton;
    private Button falseButton;
    private TextView mTextView;
    private TextView mScoreTextView;
    private ProgressBar mProgressBar;
    private int mIndex = 0;
    private int mScore = 0;

    private TrueFalse[] mQuestionBank = new TrueFalse[] {
            new TrueFalse(R.string.question_1, true),
            new TrueFalse(R.string.question_2, true),
            new TrueFalse(R.string.question_3, true),
            new TrueFalse(R.string.question_4, true),
            new TrueFalse(R.string.question_5, true),
            new TrueFalse(R.string.question_6, false),
            new TrueFalse(R.string.question_7, true),
            new TrueFalse(R.string.question_8, false),
            new TrueFalse(R.string.question_9, true),
            new TrueFalse(R.string.question_10, true),
            new TrueFalse(R.string.question_11, false),
            new TrueFalse(R.string.question_12, false),
            new TrueFalse(R.string.question_13,true)
    };

    final int PROGRESS_BAR_INCREMENT = (int) Math.ceil(100f / mQuestionBank.length);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt("ScoreKey");
            mIndex = savedInstanceState.getInt("IndexKey");
        }
        else {
            mIndex = 0;
            mScore = 0;
        }

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        mTextView = findViewById(R.id.question_text_view);
        mScoreTextView = findViewById(R.id.score);
        mProgressBar = findViewById(R.id.progress_bar);

        mScoreTextView.setText("Score: " + mScore + "/" + mQuestionBank.length);
        mTextView.setText(mQuestionBank[mIndex].getId());

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                updateQuestion();
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                updateQuestion();
            }
        });
    }

    private void updateQuestion() {
        mIndex = (mIndex + 1) % mQuestionBank.length;

        mTextView.setText(mQuestionBank[mIndex].getId());
        mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
        mScoreTextView.setText("Score: " + mScore + "/" + mQuestionBank.length);

        if (mIndex == 0) {
            final AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Game over");
            alert.setCancelable(false);
            alert.setMessage("You scored " + mScore + " points!");
            alert.setNegativeButton("Close App", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mScore = 0;
                    mScoreTextView.setText("Score: " + mScore + "/" + mQuestionBank.length);
                    mProgressBar.setProgress(0);
                }
            });
            alert.show();
        }


    }

    private void checkAnswer(boolean userAnswer) {
        boolean correctAnswer = mQuestionBank[mIndex].getAnswer();
        if (userAnswer == correctAnswer) {
            Toast.makeText(getApplicationContext(), "Right!", Toast.LENGTH_SHORT).show();
            mScore = (mScore + 1) % mQuestionBank.length;
        }
        else {
            Toast.makeText(getApplicationContext(), "Wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("ScoreKey", mScore);
        outState.putInt("IndexKey", mIndex);
    }
}
