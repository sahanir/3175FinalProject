package com.example.gradecalculatorapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class homePageAdapter extends BaseAdapter {
    List<String> studentSemesterDetails;

    public homePageAdapter(List<String> studentSemesterDetails) {
        this.studentSemesterDetails = studentSemesterDetails;
    }
    @Override
    public int getCount() {
        return studentSemesterDetails.size();
    }

    @Override
    public Object getItem(int i) {
        return studentSemesterDetails.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            view = layoutInflater.inflate(R.layout.homepagelayout,viewGroup,false);
        }
        TextView textViewInformation = view.findViewById(R.id.textViewCourseID);
        textViewInformation.setText(studentSemesterDetails.get(i));
        return view;
    }
}
