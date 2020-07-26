package com.marcos.procrastinationrepelent;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import androidx.fragment.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private boolean mIsSubtitleVisible;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.task_list_title);
        mTasks = TaskLab.getInstance( getActivity() ).getTasks();
        TaskAdapter adapter = new TaskAdapter(mTasks);
        setListAdapter(adapter);
        setRetainInstance(true);
        mIsSubtitleVisible = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v = super.onCreateView(inflater, parent, savedInstanceState);
        if(mIsSubtitleVisible){
            getActivity().getActionBar().setSubtitle(R.string.subtitle);
        }
        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Task t = ((TaskAdapter) getListAdapter() ).getItem(position);
        Intent i = new Intent(getActivity(), TaskPagerActivity.class);
        i.putExtra(TaskFragment.EXTRA_TASK_ID, t.getId());
        startActivity(i);
    }

    //Update the list if changed
    @Override
    public void onResume(){
        super.onResume();
        ((TaskAdapter)getListAdapter()).notifyDataSetChanged();
    }

    //Menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_task_list, menu);
        MenuItem subtitle = menu.findItem(R.id.menu_item_show_subtitle);
        if(mIsSubtitleVisible && (subtitle != null)){
            subtitle.setTitle(R.string.hide_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu_item_new_task:
                Task task = new Task();
                TaskLab.getInstance(getActivity()).addTask(task);
                Intent i = new Intent(getActivity(), TaskPagerActivity.class);
                i.putExtra(TaskFragment.EXTRA_TASK_ID, task.getId());
                startActivityForResult(i, 0);
                return true;
            case R.id.menu_item_show_subtitle:
                if(getActivity().getActionBar().getSubtitle() == null){
                    getActivity().getActionBar().setSubtitle(R.string.subtitle);
                    item.setTitle(R.string.hide_subtitle);
                    mIsSubtitleVisible = true;
                }else{
                    getActivity().getActionBar().setSubtitle(null);
                    item.setTitle(R.string.show_subtitle);
                    mIsSubtitleVisible = false;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //And adapter for the tasks
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
