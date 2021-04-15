package com.example.proiect.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.proiect.DownloadWorkoutsManager;
import com.example.proiect.IWorkoutsResponse;
import com.example.proiect.R;
import com.example.proiect.util.WorkoutsAdapter;
import com.example.proiect.WorkoutsDetails;

import java.util.ArrayList;


public class WorkoutsFragment extends Fragment {
    public WorkoutsFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_workouts, container, false);



        final ListView workoutsListView= v.findViewById(R.id.list);


        DownloadWorkoutsManager.getInstance().getWorkoutsData(new IWorkoutsResponse() {
            @Override
            public void onSuccess(final ArrayList<WorkoutsDetails> workouts) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        WorkoutsAdapter adapter=new WorkoutsAdapter(getActivity(), workouts);
                        workoutsListView.setAdapter(adapter);
                        for(WorkoutsDetails workout: workouts)
                        {
                            Log.v("ProfileActivity",workout.toString());

                        }


                        workoutsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                WorkoutsDetails temp= workouts.get(position);
                                HomeFragment fragment=new HomeFragment();
                                Bundle args=new Bundle();
                                args.putParcelable("clickvalue", temp);

                                fragment.setArguments(args);

                               getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,fragment).commit();
                            }
                        });
                    }
                });

            }

            @Override
            public void onFailure(int errorCode, Throwable error) {
                Log.v("ProfileActivity", error+error.getLocalizedMessage());
            }


        });


        return v;
}
}