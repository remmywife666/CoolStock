package com.example.administrator.coolstock.fragement;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.coolstock.MainActivity;
import com.example.administrator.coolstock.R;


public class HomeFragment extends Fragment {

    private MainActivity mainActivity;
    private TextView btnss;
    @Nullable
    @Override
    // 创建该Fragment的视图
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        this.mainActivity = (MainActivity) getActivity();
        return inflater.inflate(R.layout.fragment_home_frament, container, false);
    }
    @Override
    // 当Activity的onCreate方法返回时调用
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnss = (TextView) getView().findViewById(R.id.main_btn1);
        btnss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.switchFragment("cateFragment");
            }
        });
    }
}