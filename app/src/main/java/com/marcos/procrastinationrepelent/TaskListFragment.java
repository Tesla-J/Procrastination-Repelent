package com.marcos.procrastinationrepelent;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import androidx.fragment.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
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
        TaskAdapter adapter = new TaskAdapter(mTasks);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Task t = ((TaskAdapter) getListAdapter() ).getItem(position);
        Intent i = new Intent(getActivity(), TaskPagerActivity.class);
        i.putExtra(TaskFragment.EXTRA_TASK_ID, t.getId());
        startActivity(i);
    }

    @Override
    public void onResume(){
        super.onResume();
        ((TaskAdapter)getListAdapter()).notifyDataSetChanged();
    }

    private class TaskAdapter extends ArrayAdapter<Task>{
        public TaskAdapter(ArrayList<Task> tasks){
            super(getActivity(), 0, tasks);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            //if weren't given a layout, inflate one
            if(convertView == null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_task, null);
            }
            //configure the view for the task
            Task c = getItem(position);

            TextView taskTitle = (TextView) convertView.findViewById(R.id.task_title_view);
            taskTitle.setText(c.getTitle());
            TextView taskDate = (TextView) convertView.findViewById(R.id.task_date_view);
            taskDate.setText(c.getFormatedDate());
            CheckBox isDoneCheckBox = (CheckBox) convertView.findViewById(R.id.is_done_checkbox);
            isDoneCheckBox.setChecked(c.isDone());

            return convertView;
        }
    }
}
