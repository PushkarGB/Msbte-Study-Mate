package com.example.quizapp.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.quizapp.R;
import com.example.quizapp.databinding.FragmentResultBinding;

import java.util.ArrayList;
import java.util.List;

public class ResultFragement extends Fragment {
    FragmentResultBinding binding;

    private int score,attempts,totalQuestions;
   private String course,unit;

    private ArrayList<String> selectedOptions;

    public ResultFragement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResultBinding.inflate(getLayoutInflater());

        Bundle args = getArguments();
        if (args != null) {
             score = args.getInt("score");
             attempts = args.getInt("attempts");
             totalQuestions = args.getInt("totalQuestions");
            course = args.getString("course");
             unit = args.getString("unit");
            selectedOptions = args.getStringArrayList("selectedOptions");
            String correctStr = score + " Correct";
            String incorrectStr = (attempts - score) + " Wrong";
            binding.correctCount.setText(correctStr);
            binding.resScore.setText(String.valueOf(score));
            binding.resTotalQ.setText(String.valueOf(totalQuestions));
            binding.resAttempts.setText(String.valueOf(attempts));
            binding.inCorrectCount.setText(incorrectStr);

        }


        binding.restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new QuizFragment(course, unit)).commit();
            }
        });

        binding.solutionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuizFragment reviewFragment = new QuizFragment(course, unit);
                Bundle reviewArgs = new Bundle();
                reviewArgs.putBoolean("solutionMode", true);
                reviewArgs.putStringArrayList("selectedOptionsList", selectedOptions);
                reviewFragment.setArguments(reviewArgs);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, reviewFragment).commit();
            }
        });
        binding.exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              getActivity().finish();
            }
        });
        return binding.getRoot();

    }
}