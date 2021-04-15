package com.example.proiect;

import android.os.Parcel;
import android.os.Parcelable;

public class WorkoutsDetails implements Parcelable {


    private String workouttype;
    private String workoutlevel;
    private String workoutexercise;
    private String musclegroup;
    private int workouttime;
    private int sets;
    private int reps;


    public WorkoutsDetails(String workouttype, String workoutlevel, String workoutexercise, String musclegroup, int workouttime, int sets, int reps) {
        this.workouttype = workouttype;
        this.workoutlevel = workoutlevel;
        this.workoutexercise = workoutexercise;
        this.musclegroup = musclegroup;
        this.workouttime = workouttime;
        this.sets = sets;
        this.reps = reps;
    }

    protected WorkoutsDetails(Parcel in) {
        workouttype = in.readString();
        workoutlevel = in.readString();
        workoutexercise = in.readString();
        musclegroup = in.readString();
        workouttime = in.readInt();
        sets = in.readInt();
        reps = in.readInt();
    }

    public static final Creator<WorkoutsDetails> CREATOR = new Creator<WorkoutsDetails>() {
        @Override
        public WorkoutsDetails createFromParcel(Parcel in) {
            return new WorkoutsDetails(in);
        }

        @Override
        public WorkoutsDetails[] newArray(int size) {
            return new WorkoutsDetails[size];
        }
    };

    public String getWorkouttype() {
        return workouttype;
    }

    public void setWorkouttype(String workouttype) {
        this.workouttype = workouttype;
    }

    public String getWorkoutlevel() {
        return workoutlevel;
    }

    public void setWorkoutlevel(String workoutlevel) {
        this.workoutlevel = workoutlevel;
    }

    public String getWorkoutexercise() {
        return workoutexercise;
    }

    public void setWorkoutexercise(String workoutexercise) {
        this.workoutexercise = workoutexercise;
    }

    public String getMusclegroup() {
        return musclegroup;
    }

    public void setMusclegroup(String musclegroup) {
        this.musclegroup = musclegroup;
    }

    public int getWorkouttime() {
        return workouttime;
    }

    public void setWorkouttime(int workouttime) {
        this.workouttime = workouttime;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    @Override
    public String toString() {
        return "Workout Details ---> " +
                " Type: '" + workouttype + '\'' +
                ", Level: '" + workoutlevel + '\'' +
                ", Workout Name: '" + workoutexercise + '\'' +
                ", Muscle Group: '" + musclegroup + '\'' +
                ", Time: " + workouttime +
                ", Nb. of sets: " + sets +
                ", Nb. of reps: " + reps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(workouttype);
        dest.writeString(workoutlevel);
        dest.writeString(workoutexercise);
        dest.writeString(musclegroup);
        dest.writeInt(workouttime);
        dest.writeInt(sets);
        dest.writeInt(reps);
    }
}
