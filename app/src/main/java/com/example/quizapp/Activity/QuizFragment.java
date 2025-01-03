package com.example.quizapp.Activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.Model.Question;
import com.example.quizapp.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import com.example.quizapp.Activity.ResultFragement;



public class QuizFragment extends Fragment {

    private TextView optionBtn1, optionBtn2, optionBtn3, optionBtn4, questionTextView, timerTextView, qNo, totalQ;
    private Button finishBtn;
    private ImageView nextBtn, prevBtn;

    private CountDownTimer countDownTimer;

    //counter variables
    private int currentQuestionIndex = 0;
    private int score = 0;

    private static final long TIME_LIMIT = 90 * 60 * 1000; // 90 minutes in milliseconds

    //List of questions
    private List<Question> questions;

    //mark for attempted questions
    private List<Boolean> attemptedQuestions;

    //mark for selected options
    private List<String> selectedOptionsList;

    private String course, unit;
    private Boolean solutionMode = false;


    public QuizFragment() {

    }

    public QuizFragment(String course, String unit) {
        this.course = course;
        this.unit = unit;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startTimer();

        questionTextView = view.findViewById(R.id.questionText);
        optionBtn1 = view.findViewById(R.id.option1);
        optionBtn2 = view.findViewById(R.id.option2);
        optionBtn3 = view.findViewById(R.id.option3);
        optionBtn4 = view.findViewById(R.id.option4);
        nextBtn = view.findViewById(R.id.nxtBtn);
        prevBtn = view.findViewById(R.id.pBtn);
        timerTextView = view.findViewById(R.id.timerTextView);
        finishBtn = view.findViewById(R.id.finishButton);
        qNo = view.findViewById(R.id.Q_No);
        totalQ = view.findViewById(R.id.total_Q);

        //load questions based on subject and chapter in the list
        loadQuestions(course, unit);

        if (questions == null) {
            getActivity().finish();
        } else {

            if (savedInstanceState != null) {
                currentQuestionIndex = savedInstanceState.getInt("currentQuestionIndex", 0);
                attemptedQuestions = (List<Boolean>) savedInstanceState.getSerializable("attemptedQuestions");
                selectedOptionsList = savedInstanceState.getStringArrayList("selectedOptionsList");
            } else {
                attemptedQuestions = new ArrayList<>();
                selectedOptionsList = new ArrayList<>();
                for (int i = 0; i < questions.size(); i++) {
                    attemptedQuestions.add(false);
                    selectedOptionsList.add(null);
                }
            }

            Bundle args = getArguments();
            if (args != null) {
                solutionMode = args.getBoolean("solutionMode", false);
                if (solutionMode) {
                    selectedOptionsList = args.getStringArrayList("selectedOptionsList");
                }
            }

            totalQ.setText(String.valueOf(questions.size()));

            showQuestion(currentQuestionIndex);

            //creating a OnClickListener object
            View.OnClickListener optionClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (solutionMode) return;
                    TextView selectedButton = (TextView) v;
                    String selectedOption = selectedButton.getText().toString();
                    selectOption(selectedOption);

                    if (currentQuestionIndex < questions.size() - 1) {
                        currentQuestionIndex++;
                        showQuestion(currentQuestionIndex);
                    } else {
                        Toast.makeText(getContext(), "Quiz completed! Click On Finish for results", Toast.LENGTH_SHORT).show();
                    }
                }
            };

            //setting onClickListener to the buttons

            optionBtn1.setOnClickListener(optionClickListener);
            optionBtn2.setOnClickListener(optionClickListener);
            optionBtn3.setOnClickListener(optionClickListener);
            optionBtn4.setOnClickListener(optionClickListener);

            //Going to next question
            nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentQuestionIndex < questions.size() - 1) {
                        currentQuestionIndex++;
                        showQuestion(currentQuestionIndex);
                    } else {
                        Toast.makeText(getContext(), "No more questions", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            //Going to previous question
            prevBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentQuestionIndex > 0) {
                        currentQuestionIndex--;
                        showQuestion(currentQuestionIndex);
                    }
                }
            });

            //Going to Result Page
            finishBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(solutionMode){
                        getActivity().finish();
                    }
                    showResult();
                }
            });
        }
    }

    private void highlightCorrectOption(int currentQuestionIndex, String answer) {
        TextView correctOption = null;
        if (answer.equals(optionBtn1.getText().toString())) {
            correctOption = optionBtn1;
        } else if (answer.equals(optionBtn2.getText().toString())) {
            correctOption = optionBtn2;
        } else if (answer.equals(optionBtn3.getText().toString())) {
            correctOption = optionBtn3;
        } else if (answer.equals(optionBtn4.getText().toString())) {
            correctOption = optionBtn4;
        }

        if (correctOption != null) {
            correctOption.setBackgroundResource(R.color.correct_color);
        }

    }

    //display the current question
    private void showQuestion(int currentQuestionIndex) {
        Question question = questions.get(currentQuestionIndex);
        questionTextView.setText(question.getQuestion());
        optionBtn1.setText(question.getOptions()[0]);
        optionBtn2.setText(question.getOptions()[1]);
        optionBtn3.setText(question.getOptions()[2]);
        optionBtn4.setText(question.getOptions()[3]);
        qNo.setText(String.valueOf(currentQuestionIndex + 1));
        //Reset the button backgrounds
        optionBtn1.setBackgroundResource(R.color.btn_color);
        optionBtn2.setBackgroundResource(R.color.btn_color);
        optionBtn3.setBackgroundResource(R.color.btn_color);
        optionBtn4.setBackgroundResource(R.color.btn_color);

        //Highlight the selectedOption if it exists
        if (selectedOptionsList.get(currentQuestionIndex) != null) {
            if (selectedOptionsList.get(currentQuestionIndex).equals(optionBtn1.getText().toString())) {
                optionBtn1.setBackgroundResource(R.color.btn_selected_color);
            } else if (selectedOptionsList.get(currentQuestionIndex).equals(optionBtn2.getText().toString())) {
                optionBtn2.setBackgroundResource(R.color.btn_selected_color);
            } else if (selectedOptionsList.get(currentQuestionIndex).equals(optionBtn3.getText().toString())) {
                optionBtn3.setBackgroundResource(R.color.btn_selected_color);
            } else if (selectedOptionsList.get(currentQuestionIndex).equals(optionBtn4.getText().toString())) {
                optionBtn4.setBackgroundResource(R.color.btn_selected_color);
            }
        }

        if (solutionMode) {
            highlightCorrectOption(currentQuestionIndex, question.getAnswer());
        }
    }

    private void selectOption(String selectedOption) {
        //mark the selectedOption
        selectedOptionsList.set(currentQuestionIndex, selectedOption);
        //Mark the Question as attempted
        if (!attemptedQuestions.get(currentQuestionIndex)) {
            attemptedQuestions.set(currentQuestionIndex, true);
        }
        //Highlight the selected option
        showQuestion(currentQuestionIndex);
    }

    private void loadQuestions(@NonNull String subject, @NonNull String chapter) {
        //load questions based on subject and chapter
        questions = new ArrayList<>();
        String fileName = "questions/" + subject.toLowerCase() + "/" + chapter.toLowerCase() + ".csv";

        //questions/ajp/unit1.csv
        //reading the CSV file
        try (InputStream in = getContext().getAssets().open(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            //Reading Each Line of the CSV File

            String line;
            while ((line = reader.readLine()) != null) {

                //Parsing Each line of the CSV File

                String[] parts = line.split(",");
                if (parts.length == 6) { // Assuming there are 6 columns in the CSV file (question, option1, option2, option3, option4, answer)

                    //Creating a new Question object and adding it to the list
                    questions.add(new Question(parts[0], parts[5], new String[]{parts[1], parts[2], parts[3], parts[4]}));
                    // Assuming  constructor Question(String question, String answer, String[] options)

                }
            }

        } catch (IOException e) {
            Toast.makeText(getContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
            questions = null;
        }
    }

    private void showResult() {
        calculateScore();
        Bundle args = new Bundle();
        args.putInt("score", score);
        args.putInt("attempts", (int) attemptedQuestions.stream().filter(Boolean::booleanValue).count());
        args.putInt("totalQuestions", questions.size());
        args.putString("course", course);
        args.putString("unit", unit);
        args.putStringArrayList("selectedOptions", new ArrayList<>(selectedOptionsList));

        ResultFragement resultFragment = new ResultFragement();
        resultFragment.setArguments(args);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, resultFragment).commit();
    }

    private void calculateScore() {
        score = 0;
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            String selectedOption = selectedOptionsList.get(i);
            if (selectedOption != null && selectedOption.equals(question.getAnswer())) {
                score++;
            }
        }
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(TIME_LIMIT, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                String time = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                timerTextView.setText(time);
            }

            @Override
            public void onFinish() {
                showResult();
            }
        };
        countDownTimer.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}

