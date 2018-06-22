package br.com.softpeach.www.eqm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.softpeach.www.eqm.R;
import br.com.softpeach.www.eqm.helper.DatabaseHelper;
import br.com.softpeach.www.eqm.model.Attendance;

/**
 * Created by jonas on 03/03/2018.
 */

// Custom adapter para manipular a lista de frequência
public class AttendanceAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Attendance> attendanceArrayList;
    private DatabaseHelper db;
    public AttendanceAdapter(Context context, ArrayList<Attendance> attendanceArrayList) {
        this.context = context;
        this.attendanceArrayList = attendanceArrayList;
    }

    @Override
    public int getCount() {
        return attendanceArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return attendanceArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row;
        row = convertView;
        ViewHolder holder;

        if(row == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.attendance_list_items, parent, false);

            holder = new ViewHolder();

            holder.txv_attendanceListElderId = row.findViewById(R.id.txv_attendanceListElderId);
            holder.txv_attendanceListMeetingId = row.findViewById(R.id.txv_attendanceListMeetingId);

            row.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) row.getTag();
        }

        db = new DatabaseHelper(context);
        attendanceArrayList = (ArrayList<Attendance>) db.getAllAttendances();

        holder.txv_attendanceListElderId.setText(String.valueOf(attendanceArrayList.get(position).getElder_id()));
        holder.txv_attendanceListMeetingId.setText(String.valueOf(attendanceArrayList.get(position).getMeeting_id()));

        return row;
    }

    static class ViewHolder
    {
        private TextView txv_attendanceListElderId;
        private TextView txv_attendanceListMeetingId;
    }
}
