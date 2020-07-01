package com.marcos.procrastinationrepelent;

import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import java.util.ArrayList;
import java.util.UUID;

public class TaskPagerActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private ArrayList<Task> mTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.view_pager);
        setContentView(mViewPager);
        mTasks = TaskLab.getInstance(this).getTasks();
        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm){
            @Override
            public int getCount(){
                return mTasks.size();
            }

            @Override
            public Fragment getItem(int pos){
                Task task = mTasks.get(pos);
                return TaskFragment.newInstance(task.getId());
            }

        });
        UUID taskId = (UUID) getIntent().getSerializableExtra(TaskFragment.EXTRA_TASK_ID);
        for(int i=0; i<mTasks.size(); i++){
            if(mTasks.get(i).getId().equals(taskId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            public void onPageScrollStateChanged(int state){ }
            public void onPageScrolled(int pos, float posOffset, int posOffsetPixels) { }
            public void onPageSelected(int pos){
                Task task = mTasks.get(pos);
                if(task.getTitle() != null){
                    setTitle(task.getTitle());
                }
            }
        });

    }
}
