package jp.ac.shibaura_it.sayo.se.reservationsystem.user.view;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.zip.Inflater;

import jp.ac.shibaura_it.sayo.se.reservationsystem.user.R;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.model.Reserve;
import me.drakeet.materialdialog.MaterialDialog;


public class ReserveDetailDialog extends MaterialDialog {
    private Reserve reserve;

    private TextView requestDay;
    private TextView useDay;
    private TextView useTime;
    private TextView room;
    private TextView responsiblePerson;
    private TextView purpose;

    public ReserveDetailDialog(Context context, Reserve reserve) {
        super(context);
        this.reserve = reserve;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.reserve_detail_dialog, null);

        this.requestDay = (TextView)view.findViewById(R.id.resDialogRequestDay);
        this.useDay = (TextView)view.findViewById(R.id.resDialogUseDay);
        this.useTime = (TextView)view.findViewById(R.id.resDialogUseTime);
        this.room = (TextView)view.findViewById(R.id.resDialogRoom);
        this.responsiblePerson = (TextView)view.findViewById(R.id.resDialogResponsiblePerson);
        this.purpose = (TextView)view.findViewById(R.id.resDialogPurpose);

        initReserve(reserve);

        this.setContentView(view);
    }

    public void initReserve(Reserve reserve) {
        this.requestDay.setText(String.format("%d年%d月%02d日",
                this.reserve.getRequestDay().get(Calendar.YEAR),
                this.reserve.getRequestDay().get(Calendar.MONTH) + 1,
                this.reserve.getRequestDay().get(Calendar.DAY_OF_MONTH)));
        this.useDay.setText(String.format("%d年%d月%02d日",
                this.reserve.getStartTime().get(Calendar.YEAR),
                this.reserve.getStartTime().get(Calendar.MONTH) + 1,
                this.reserve.getStartTime().get(Calendar.DAY_OF_MONTH)));
        this.useTime.setText(String.format("%s 〜 %s", reserve.getStartTimeHourOnly(), reserve.getEndTimeHourOnly()));
        this.room.setText(reserve.getRoom());
        this.responsiblePerson.setText(reserve.getResponsiblePerson());
        this.purpose.setText(reserve.getPurpose());
    }
}
