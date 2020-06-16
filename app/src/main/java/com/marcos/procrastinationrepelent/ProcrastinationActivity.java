package com.marcos.procrastinationrepelent;

import androidx.fragment.app.Fragment;

public class ProcrastinationActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new TaskFragment();
    }
}
