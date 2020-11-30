package com.example.gradecalculatorapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class editCourseAdapter extends BaseAdapter {

    List<String> gradesInfo;

    public editCourseAdapter(List<String> gradesInfo) {
        this.gradesInfo = gradesInfo;
        Log.d("gradesInfo",this.gradesInfo.toString());
    }

    @Override
    public int getCount() {
        return gradesInfo.size();
    }

    @Override
    public Object getItem(int i) {
        return gradesInfo.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            view = layoutInflater.inflate(R.layout.editcourse,viewGroup,false);
        }

        TextView textViewCriteriaValue = view.findViewById(R.id.textViewCriteriaValue);
        TextView textViewWeightageValue = view.findViewById(R.id.textViewWeightageValue);
        TextView textViewTotalMarksValue = view.findViewById(R.id.textViewTotalMarksValue);
        TextView textViewMarksObtainedValue = view.findViewById(R.id.textViewMarksObtainedValue);
        TextView textViewAcheivedPercentageValue = view.findViewById(R.id.textViewAcheivedPercentageValue);
        TextView textViewGradeID = view.findViewById(R.id.textViewGradeID);


            String[] displayingIntentSplit = gradesInfo.get(i).split(";");

            Log.d("displayingIntentSplit",gradesInfo.get(i));
            textViewCriteriaValue.setText(displayingIntentSplit[0]);
            textViewWeightageValue.setText(displayingIntentSplit[1]);
            textViewTotalMarksValue.setText(displayingIntentSplit[2]);
            textViewMarksObtainedValue.setText(displayingIntentSplit[3]);
            textViewAcheivedPercentageValue.setText(displayingIntentSplit[4]);
            textViewGradeID.setText(displayingIntentSplit[5]);

        return view;
    }
}
