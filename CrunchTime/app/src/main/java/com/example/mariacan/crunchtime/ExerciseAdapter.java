package com.example.mariacan.crunchtime;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by mariacan on 2/4/16.
 */
public class ExerciseAdapter extends BaseAdapter{

    Context context;
    boolean enteredInfo = false;
    String[] items = new String[]{"Pushup", "Situp", "Squats", "Leg-lift", "Plank",
            "Jumping Jacks", "Pullup", "Cycling", "Walking", "Jogging", "Swimming", "Stair-Climbing"};

    int[] images = new int[]{R.drawable.pushup, R.drawable.situp, R.drawable.squats,
            R.drawable.leglift, R.drawable.plank, R.drawable.jumpingjacks, R.drawable.pullup,
            R.drawable.cycling, R.drawable.walking, R.drawable.jogging, R.drawable.swimming,
            R.drawable.stairs};
    String[] exerciseType = new String[]{"reps", "reps", "reps", "mins", "mins", "mins", "reps",
            "mins", "mins", "mins", "mins", "mins"};
    int[] equivalentExercises = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    Bitmap[] imageBitmaps;

    public ExerciseAdapter(Context context){
        this.context = context;
        this.imageBitmaps = generateBitmap(2, images, context);
    }

    @Override
    public int getCount() {
        if (!enteredInfo){
            return 0;
        }
        return items.length;
    }

    @Override
    public String getItem(int position) {
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.exercisebox, null);
        }
        ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
        TextView exercise = (TextView) convertView.findViewById(R.id.exercise);
        TextView amount = (TextView) convertView.findViewById(R.id.amount);

        icon.setImageBitmap(imageBitmaps[position]);
        exercise.setText(items[position]);
        amount.setText(Integer.toString(equivalentExercises[position]) + " " + exerciseType[position]);
        return convertView;
    }

    private Bitmap[] generateBitmap(final int sampleSize, int[] images, Context context){


        Bitmap[] map = new Bitmap[images.length];
        for (int i = 0; i < images.length; i++){
            final BitmapFactory.Options bitmapOptions=new BitmapFactory.Options();
            bitmapOptions.inDensity=sampleSize;
            bitmapOptions.inTargetDensity=1;
            final Bitmap bMap =  BitmapFactory.decodeResource(context.getResources(), images[i], bitmapOptions);
            bMap.setDensity(Bitmap.DENSITY_NONE);
            map[i] = bMap;
        }
        return map;
    }

    public void setEnteredInfo(boolean bool){
        this.enteredInfo = bool;
    }

    public void updateValues(int[] values){
        equivalentExercises = values;
        this.notifyDataSetChanged();
    }

}
