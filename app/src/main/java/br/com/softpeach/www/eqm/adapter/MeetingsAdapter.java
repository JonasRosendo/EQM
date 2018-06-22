package br.com.softpeach.www.eqm.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.softpeach.www.eqm.R;
import br.com.softpeach.www.eqm.helper.DatabaseHelper;
import br.com.softpeach.www.eqm.model.Elder;
import br.com.softpeach.www.eqm.model.Meetings;

/**
 * Created by jonas on 20/02/2018.
 */

//Custom Adapter para manipular a lista de Reuniões
public class MeetingsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Meetings> meetingsArrayList;

    public MeetingsAdapter(Context context, ArrayList<Meetings> meetingsArrayList) {
        this.context = context;
        this.meetingsArrayList = meetingsArrayList;
    }


    @Override
    public int getCount() {
        return meetingsArrayList.size();
    }

    @Override
    public Meetings getItem(int position) {
        return meetingsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row;
        row = convertView;
        ViewHolder holder;
        if(row == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.meetings_list_items, parent, false);

            holder = new ViewHolder();
            holder.txv_meetingsDate = row.findViewById(R.id.txv_meetingListDate);
            row.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) row.getTag();
        }

        DatabaseHelper db = new DatabaseHelper(context);
        meetingsArrayList = (ArrayList<Meetings>) db.getAllMeetings();

        holder.txv_meetingsDate.setText(meetingsArrayList.get(position).getDay() + "/" + meetingsArrayList.get(position).getMonth() + "/" + meetingsArrayList.get(position).getYear());
        return row;
    }

    private static class ViewHolder
    {
        TextView txv_meetingsDate;
    }
}
