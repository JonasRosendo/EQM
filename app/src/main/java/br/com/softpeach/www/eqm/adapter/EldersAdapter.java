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

/**
 * Created by jonas on 16/02/2018.
 */

//Custom Adapter para manipular a lista de Élderes
public class EldersAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Elder> elderList;
    DatabaseHelper db;
    public EldersAdapter(Context context, ArrayList<Elder> elderList) {
        this.context = context;
        this.elderList = elderList;
    }

    @Override
    public int getCount() {
        return elderList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.elderList.get(position);
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
        if (row == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.add_elders_list_item, parent, false);

            holder = new ViewHolder();
            holder.elderName = row.findViewById(R.id.txv_elderListName);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        db = new DatabaseHelper(this.context);
        elderList = (ArrayList<Elder>) db.getAllElders();

        holder.elderName.setText(elderList.get(position).getFirst_name() + " " + elderList.get(position).getMiddle_name() + " " + elderList.get(position).getLast_name());

        return row;
    }

    static class ViewHolder
    {
        TextView elderName;
    }
}
