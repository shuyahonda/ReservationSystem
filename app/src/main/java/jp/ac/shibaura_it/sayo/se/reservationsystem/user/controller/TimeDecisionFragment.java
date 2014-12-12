package jp.ac.shibaura_it.sayo.se.reservationsystem.user.controller;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeDecisionFragment extends Fragment {


    public TimeDecisionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_time_decision, container, false);
        ButterKnife.inject(this,view);
        return view;
    }

    @OnClick(R.id.decisionButton)
    public void OnClick(View view) {
        Intent intent = new Intent(getActivity(),ReserveCompleteActivity.class);
        startActivity(intent);
        Log.d("確定","test");
    }
}
