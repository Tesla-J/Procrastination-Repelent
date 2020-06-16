package com.marcos.procrastinationrepelent;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import androidx.fragment.app.ListFragment;
import android.view.View;
import java.util.ArrayList;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

public class TaskListFragment extends ListFragment {
    private ArrayList<Task> mTasks;
    private static final String TAG = "TaskListFragment";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Toast.makeText(this.getContext(), "Hmmm", Toast.LENGTH_LONG);
        getActivity().setTitle(R.string.task_list_title);
        mTasks = TaskLab.getInstance( getActivity() ).getTasks();
        ArrayAdapter<Task> adapter = new ArrayAdapter<Task>( getActivity(), android.R.layout.simple_list_item_1, mTasks);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Task t = (Task) ( getListAdapter() ).getItem(position);
        Log.d(TAG, t.getTitle() + " was clicked.");
    }
}
