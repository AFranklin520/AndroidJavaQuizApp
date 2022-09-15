/*
 * Anthony Franklin
 * afranklin18@cnm.edu
 * Franklin_P1
 * 09/14/2022
 * MainActivity.java
 *
 * Simple True/False Quiz application
 * */

package com.cis2237.franklin_p1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.cis2237.franklin_p1.databinding.ActivityMainBinding;
import java.text.NumberFormat;


public class MainActivity extends AppCompatActivity {
    private TextView btnTrue;
    private TextView btnFalse;
    private TextView btnNext;
    private TextView btnPrev;
    private TextView txbQuestion;
    private TextView txbTitle;
    private TextView btnAgain;
    private TextView btnQuit;
    private Button btnDone;
    private Boolean[] answered_correctly;

    private int questionIndex;
    private int num_right;
    private int attempts;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        answered_correctly = new Boolean[20];
        questionIndex = 0;
        num_right = 0;
        attempts = 0;
        btnTrue = binding.btnTrue;
        btnFalse = binding.btnFalse;
        btnNext = binding.btnNext;
        btnPrev = binding.btnPrev;
        btnDone = binding.btnDone;
        txbQuestion = binding.txbQuestion;
        txbTitle = binding.txbTitle;
        btnAgain = binding.btnAgain;
        btnQuit = binding.btnQuit;
        btnQuit.setVisibility(View.GONE);
        btnAgain.setVisibility(View.GONE);
        btnPrev.setEnabled(false);

        //Anonymous event handler(s)
        binding.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txbQuestion.setText("");
                calcScore();
                again();
            }
        });

        binding.btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                attempts++;
                questionIndex++;
                if(attempts == 19){
                    calcScore();
                    again();
                }
                else updateQuestion();
            }
        });

        binding.btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                attempts++;
                questionIndex++;
                if(attempts == 19){
                    calcScore();
                    again();
                }
                else updateQuestion();

            }
        });

        binding.btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                questionIndex--;
                updateQuestion();
                if(answered_correctly[questionIndex] != null){
                    if(answered_correctly[questionIndex]){
                        txbQuestion.append(getResources().getString(R.string.rightSym));
                        btnTrue.setEnabled(false);
                        btnFalse.setEnabled(false);
                    }
                    else if(!answered_correctly[questionIndex]){
                        txbQuestion.append(getResources().getString(R.string.wrongSym));
                        btnTrue.setEnabled(false);
                        btnFalse.setEnabled(false);
                    }
                }
                else{
                    btnTrue.setEnabled(true);
                    btnFalse.setEnabled(true);
                }
            }
        });

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionIndex++;
                updateQuestion();
                if(answered_correctly[questionIndex] != null){
                    btnTrue.setEnabled(false);
                    btnFalse.setEnabled(false);
                }
                else{
                    btnTrue.setEnabled(true);
                    btnFalse.setEnabled(true);
                }
            }
        });

        binding.btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPrev.setVisibility(View.INVISIBLE);
                btnNext.setVisibility(View.INVISIBLE);
                btnTrue.setVisibility(View.INVISIBLE);
                btnFalse.setVisibility(View.INVISIBLE);
                btnAgain.setVisibility(View.GONE);
                btnQuit.setVisibility(View.GONE);
                showToast("Thanks for playing my quiz game!\nSee ya' next time!");
                (new Handler()).postDelayed(this::do_nothing, 50000);
                MainActivity.this.finish();
                System.exit(0);
            }

            private void do_nothing() {
                return;
            }
        });

        binding.btnAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPrev.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
                btnTrue.setVisibility(View.VISIBLE);
                btnFalse.setVisibility(View.VISIBLE);
                btnAgain.setVisibility(View.GONE);
                btnQuit.setVisibility(View.GONE);
                answered_correctly = new Boolean[20];
                questionIndex = 0;
                num_right = 0;
                attempts = 0;
                updateQuestion();
            }
        });
        showToast("Anthony Franklin\nP_1 => QuizApp");
        updateQuestion();
    }

    private void updateQuestion(){
        //Disable btnPrev and btnNext if they would be out of bounds
        if(questionIndex > 0) btnPrev.setEnabled(true);
        if(questionIndex == Question.questions.length -1) btnNext.setEnabled(false);
        else if(questionIndex < Question.questions.length -1) btnNext.setEnabled(true);
        //display next question in line.
        txbQuestion.setText(Question.questions[questionIndex].getTestResId());
    }

    private void checkAnswer( boolean answerClicked ){
        boolean answerCorrect = Question.questions[questionIndex].isAnswerTrue();
        if(answerCorrect == answerClicked){
            txbQuestion.append(getResources().getString(R.string.rightSym));
            answered_correctly[questionIndex] = true;
            num_right++;
            showToast("Correct, Great job!");
        }
        else{
            txbQuestion.append(getResources().getString(R.string.wrongSym));
            answered_correctly[questionIndex] = false;
            showToast("Ooh, good guess, but not quite right!");
        }
    }

    private void calcScore(){
        //Number formatter
        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMinimumFractionDigits(2);
        double raw_score = (double) num_right/attempts;
        char grade;
        if(raw_score >= .91) {
            grade = 'A';
        }
        else if(raw_score < .91 && raw_score >= .81){
            grade = 'B';
        }
        else if(raw_score < .81 && raw_score >= .71){
                grade = 'C';
        }
        else if(raw_score < .71 && raw_score >= .61){
            grade = 'D';
        }
        else grade = 'F';
        txbTitle.setText(String.format("Questions Answered: %d\nCorrect Responses: %d\nFinal Score: %s  => %c",attempts+1, num_right+1, percent.format(raw_score), grade));

        showToast(String.format("Final Grade: %c", grade));

        //correct/questionsAsked using BHT grades: 91- 100 A, 81 - 90 B, etc
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private void again(){
        txbQuestion.setText((CharSequence) "Would you like to play again?");
        btnPrev.setVisibility(View.INVISIBLE);
        btnNext.setVisibility(View.INVISIBLE);
        btnTrue.setVisibility(View.GONE);
        btnFalse.setVisibility(View.GONE);
        btnDone.setVisibility(View.GONE);
        btnAgain.setVisibility(View.VISIBLE);
        btnQuit.setVisibility(View.VISIBLE);
    }

}