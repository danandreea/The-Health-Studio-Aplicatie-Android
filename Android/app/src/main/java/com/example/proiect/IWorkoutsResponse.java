package com.example.proiect;

import java.util.ArrayList;

public interface IWorkoutsResponse {

    void onSuccess(ArrayList<WorkoutsDetails> workouts);
    void onFailure(int errorCode, Throwable error);
}
