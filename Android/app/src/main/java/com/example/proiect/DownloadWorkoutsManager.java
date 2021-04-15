package com.example.proiect;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DownloadWorkoutsManager {


    private static DownloadWorkoutsManager instance;

    private DownloadWorkoutsManager(){

    }

    public static DownloadWorkoutsManager getInstance(){
        if(instance==null)
        {
            instance=new DownloadWorkoutsManager();
        }
        return instance;
    }



    public void getWorkoutsData(final IWorkoutsResponse listener)
    {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://api.mocki.io/v1/a1bf0198");
                    HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                    InputStream stream=connection.getInputStream();
                    InputStreamReader reader=new InputStreamReader(stream);
                    BufferedReader bufferedReader=new BufferedReader(reader);
                    StringBuilder stringBuilder=new StringBuilder();
                    String line="";
                    while((line=bufferedReader.readLine() ) !=null)
                    {
                        stringBuilder.append(line);
                    }
                    bufferedReader.close();
                    reader.close();
                    stream.close();
                    Log.v("read_remote", stringBuilder.toString());
                    listener.onSuccess(parseWorkoutsJson(stringBuilder.toString()));

                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                    listener.onFailure(HttpURLConnection.HTTP_BAD_REQUEST, e);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    listener.onFailure(HttpURLConnection.HTTP_INTERNAL_ERROR, e);
                }

            }
        }).start();

    }

    private ArrayList<WorkoutsDetails> parseWorkoutsJson(String result)
    {
        ArrayList<WorkoutsDetails> workouts=new ArrayList<>();
        try
        {
            JSONObject resultJson=new JSONObject(result);

            //radacina
            JSONArray workoutsJson=resultJson.getJSONArray("fitness");
            Log.v("workoutsJson",workoutsJson.toString());


            for(int i=0;i<workoutsJson.length();i++)
            {
                JSONObject Object1=workoutsJson.getJSONObject(i);
                String worktype=Object1.getString("workouttype");
                String level=Object1.getString("level");

                JSONArray childArray = Object1.getJSONArray("about");
                for (int j=0;j<childArray.length();j++) {

                    JSONObject Object2=childArray.getJSONObject(j);
                    String exercisetype=Object2.getString("extype");
                    String musclegr=Object2.getString("musclegroup");

                    JSONArray childArray2 = Object2.getJSONArray("details");
                    for (int k=0;k<childArray2.length();k++) {

                        JSONObject Object3 = childArray2.getJSONObject(k);
                        int timeex=Object3.getInt("time");
                        int sets=Object3.getInt("nrSets");
                        int nrReps=Object3.getInt("nrReps");

                        WorkoutsDetails workout=new WorkoutsDetails(worktype, level, exercisetype, musclegr, timeex, sets, nrReps);
                        workouts.add(workout);
                    }

                }

            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return workouts;
    }

}