package jp.ac.shibaura_it.sayo.se.reservationsystem.user.controller;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.R;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.model.Reserve;

/**
 * A simple {@link Fragment} subclass.
 */
public class OneDayFragment extends Fragment {
    public RelativeLayout linearLayout;
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;


    public OneDayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one_day, container, false);
        ButterKnife.inject(view);
        this.linearLayout = (RelativeLayout)view.findViewById(R.id.linearLayout);
        return view;
    }

    public void addReserve(boolean isMyReserve, Reserve reserve) {
        Button button = new Button(getActivity());
        button.setText("テスとです");
        this.linearLayout.addView(button, new LinearLayout.LayoutParams(WC,WC));
        this.linearLayout.addView(new OnePlan(getActivity()));
    }

}
