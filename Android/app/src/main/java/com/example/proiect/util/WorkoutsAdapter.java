package com.example.proiect.util;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.proiect.R;
import com.example.proiect.WorkoutsDetails;

import java.util.ArrayList;

public class WorkoutsAdapter extends ArrayAdapter<WorkoutsDetails> {

    public WorkoutsAdapter(Context context, ArrayList<WorkoutsDetails> workoutsDetails)
    {
        super(context, 0, workoutsDetails);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.workoutslist, parent, false
            );
        }

       WorkoutsDetails workoutsDetails = getItem(position);

        TextView workouttypeTextView = (TextView) listItemView.findViewById(R.id.worktype);
        workouttypeTextView.setText(workoutsDetails.getWorkouttype());

        TextView workoutlevelTextView = (TextView) listItemView.findViewById(R.id.worklevel);
        workoutlevelTextView.setText(workoutsDetails.getWorkoutlevel());

        TextView workoutexerciseTextView = (TextView) listItemView.findViewById(R.id.workexercise);
        workoutexerciseTextView.setText(workoutsDetails.getWorkoutexercise());

        TextView musclegroupTextView = (TextView) listItemView.findViewById(R.id.musclegroup);
        musclegroupTextView.setText(workoutsDetails.getMusclegroup());

        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time);
        timeTextView.setText(Integer.toString(workoutsDetails.getWorkouttime()));

        TextView nrSetsTextView = (TextView) listItemView.findViewById(R.id.nrsets);
        nrSetsTextView.setText(Integer.toString(workoutsDetails.getSets()));

        TextView nrRepsTextView = (TextView) listItemView.findViewById(R.id.nrreps);
        nrRepsTextView.setText(Integer.toString(workoutsDetails.getReps()));


        return listItemView;
    }


}
