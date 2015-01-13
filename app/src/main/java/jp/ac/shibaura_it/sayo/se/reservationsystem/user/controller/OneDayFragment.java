package jp.ac.shibaura_it.sayo.se.reservationsystem.user.controller;


import android.graphics.Rect;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.R;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.model.Reserve;

/**
 * A simple {@link Fragment} subclass.
 */
public class OneDayFragment extends Fragment {
    /*
    public RelativeLayout linearLayout;
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private View zeroLine;
    private ScrollView scrollView;
    */
    public OneDayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one_day, container, false);
        ButterKnife.inject(view);
        /*
        this.zeroLine = (View)view.findViewById(R.id.zeroLine);
        this.scrollView = (ScrollView)view.findViewById(R.id.scrollView);
        this.linearLayout = (RelativeLayout)view.findViewById(R.id.linearLayout);
        */
        return view;
    }
    /*
    public void addReserve(boolean isMyReserve, Reserve reserve) {
        LayoutInflater factory = LayoutInflater.from(getActivity());
        View view = factory.inflate(R.layout.view_one_reserve, null);

        Rect rectInGlobal = new Rect();
        zeroLine.getGlobalVisibleRect(rectInGlobal);
        Log.v("ViewRectInGlobal", "rectInGloabal=" + rectInGlobal);

        int[] location = new int[2];
        zeroLine.getLocationInWindow(location);
        view.setX(zeroLine.getTop());

        view.setTop(zeroLine.getTop());
        view.setY(zeroLine.getLeft());
        view.setLeft(zeroLine.getLeft());


        Log.v("座標", String.format("%d , %d",rectInGlobal.top, rectInGlobal.left));

        this.scrollView.


        this.linearLayout.addView(view);
    }
    */
}
