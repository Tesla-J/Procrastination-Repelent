package com.marcos.procrastinationrepelent;

import androidx.fragment.app.Fragment;

public class TaskListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new TaskListFragment();
    }
}
