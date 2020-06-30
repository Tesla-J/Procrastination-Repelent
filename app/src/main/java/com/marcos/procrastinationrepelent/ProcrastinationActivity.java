package com.marcos.procrastinationrepelent;

import androidx.fragment.app.Fragment;
import android.content.Intent;
import java.util.UUID;

public class ProcrastinationActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        UUID taskId = (UUID) getIntent().getSerializableExtra(TaskFragment.EXTRA_TASK_ID);
        return TaskFragment.newInstance(taskId);
    }
}
