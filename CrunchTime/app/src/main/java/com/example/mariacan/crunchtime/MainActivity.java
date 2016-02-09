package com.example.mariacan.crunchtime;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String exercise;
    int weight = 0;
    int amount = 0;
    int caloriesBurned = 0;
    ExerciseAdapter exerciseAdapter;


    String[] items = new String[]{"Pushup", "Situp", "Squats", "Leg-lift", "Plank",
            "Jumping Jacks", "Pullup", "Cycling", "Walking", "Jogging", "Swimming", "Stair-Climbing"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        exerciseAdapter = new ExerciseAdapter(this);
        Spinner dropdown = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                exercise = items[position];
                refresh();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        GridView boxes = (GridView)findViewById(R.id.gridview);
        boxes.setAdapter(exerciseAdapter);

        EditText exerciseAmount = (EditText)findViewById(R.id.editText);
        exerciseAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && !s.toString().equals("")) {
                    amount = Integer.parseInt(s.toString());
                } else{
                    amount = 0;
                }
                refresh();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        EditText userWeight = (EditText)findViewById(R.id.editText2);
        userWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && !s.toString().equals("")) {
                    weight = Integer.parseInt(s.toString());
                } else {
                    weight = 0;
                }
                refresh();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    private void refresh(){
        TextView repsOrMins = (TextView)findViewById(R.id.textView);
        repsOrMins.setText(getTextFromExercise(exercise));
        TextView calories = (TextView)findViewById(R.id.textView4);
        if (exercise != null && exercise != "" && amount != 0 && weight != 0){
            caloriesBurned = getCaloriesBurned(exercise, amount, weight);
            calories.setText(Integer.toString(caloriesBurned));
            exerciseAdapter.setEnteredInfo(true);
            int[] equivalentExercises = calcEquivalentExercises(caloriesBurned, weight);
            exerciseAdapter.updateValues(equivalentExercises);
            exerciseAdapter.notifyDataSetChanged();
        } else{
            caloriesBurned = 0;
            calories.setText(Integer.toString(caloriesBurned));
            exerciseAdapter.setEnteredInfo(false);
            exerciseAdapter.notifyDataSetChanged();
        }


    }

    private String getTextFromExercise(String exercise){
        switch (exercise){
            case "Pushup":
                return "reps";
            case "Situp":
                return "reps";
            case "Squats":
                return "reps";
            case "Leg-lift":
                return "mins";
            case "Plank":
                return "mins";
            case "Jumping Jacks":
                return "mins";
            case "Pullup":
                return "reps";
            case "Cycling":
                return "mins";
            case "Walking":
                return "mins";
            case "Jogging":
                return "mins";
            case "Swimming":
                return "mins";
            case "Stair-Climbing":
                return "mins";
            default:
                return "mins";
        }
    }

    private int getHeartRate(String exercise){
        int heartRate = 0;
        switch (exercise){
            case "Pushup":
                heartRate = 100;
                break;
            case "Situp":
                heartRate = 100;
                break;
            case "Squats":
                heartRate = 100;
                break;
            case "Leg-lift":
                heartRate = 100;
                break;
            case "Plank":
                heartRate = 100;
                break;
            case "Jumping Jacks":
                heartRate = 110;
                break;
            case "Pullup":
                heartRate = 100;
                break;
            case "Cycling":
                heartRate = 140;
                break;
            case "Walking":
                heartRate = 80;
                break;
            case "Jogging":
                heartRate = 110;
                break;
            case "Swimming":
                heartRate = 120;
                break;
            case "Stair-Climbing":
                heartRate = 140;
                break;
            default:
                heartRate = 100;
                break;
        }

        return heartRate;
    }

    private int getCaloriesBurned(String exercise, int amount, int weight){
        int heartRate = getHeartRate(exercise);
        double calories = ((-55.0969 + (0.6309 * heartRate) + (0.1988 * weight) +
                (0.2017 * 45))/4.184) * 0.06 * amount;
        return (int) calories;
    }

    public int[] calcEquivalentExercises(int calories, int weight){
        int[] toReturn = new int[12];
        int pushup = getAmountOfExercise("Pushup", calories, weight);
        toReturn[0] = pushup;
        int situp = getAmountOfExercise("Situp", calories, weight);
        toReturn[1] = situp;
        int squats = getAmountOfExercise("Squat", calories, weight);
        toReturn[2] = squats;
        int legLift = getAmountOfExercise("Leg-lift", calories, weight);
        toReturn[3] = legLift;
        int plank = getAmountOfExercise("Plank", calories, weight);
        toReturn[4] = plank;
        int jumpingJacks = getAmountOfExercise("Jumping Jacks", calories, weight);
        toReturn[5] = jumpingJacks;
        int pullup = getAmountOfExercise("Pullup", calories, weight);
        toReturn[6] = pullup;
        int cycling = getAmountOfExercise("Cycling", calories, weight);
        toReturn[7] = cycling;
        int walking = getAmountOfExercise("Walking", calories, weight);
        toReturn[8] = walking;
        int jogging = getAmountOfExercise("Jogging", calories, weight);
        toReturn[9] = jogging;
        int swimming = getAmountOfExercise("Swimming", calories, weight);
        toReturn[10] = swimming;
        int stairClimbing = getAmountOfExercise("Stair-Climbing", calories, weight);
        toReturn[11] = stairClimbing;

        return toReturn;
    }

    private int getAmountOfExercise(String exercise, int calories, int weight){
        int heartRate = getHeartRate(exercise);
        if (exercise.equals(this.exercise)){
            return this.amount;
        }
        double amount = calories / (((-55.0969 + (0.6309 * heartRate) + (0.1988 * weight) +
                (0.2017 * 45))/4.184) * 0.06);
        return (int) amount;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
