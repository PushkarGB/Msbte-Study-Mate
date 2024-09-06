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

public class ResultFragement extends Fragment {
    FragmentResultBinding binding;
    int score, attempts, totalQ;
    String course, unit;

    public ResultFragement() {
        // Required empty public constructor
    }

    public ResultFragement(int score, int count, int size, String course, String unit) {
        this.score = score;
        this.attempts = count;
        this.totalQ = size;
        this.course = course;
        this.unit = unit;
        Log.println(Log.DEBUG,"test" ,"Extras : " + score + " " + attempts + " " + totalQ + " " + course + " " + unit);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResultBinding.inflate(getLayoutInflater());

        String correctStr = score + " Correct";
        String incorrectStr = (attempts - score) + " Wrong";
        binding.correctCount.setText(correctStr);
        binding.resScore.setText(String.valueOf(score));
        binding.resTotalQ.setText(String.valueOf(totalQ));
        binding.resAttempts.setText(String.valueOf(attempts));
        binding.inCorrectCount.setText(incorrectStr);

        binding.restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new QuizFragment(course, unit)).commit();
            }
        });
        binding.resButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              getActivity().finish();
            }
        });
        return binding.getRoot();

    }
}