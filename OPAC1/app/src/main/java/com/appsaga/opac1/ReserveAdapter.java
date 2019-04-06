package com.appsaga.opac1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ReserveAdapter extends ArrayAdapter<Copies> {

    public ReserveAdapter(Context context, ArrayList<Copies> to_reserve) {
        super(context, 0,to_reserve);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View reserve_view = convertView;

        if (reserve_view == null) {
            reserve_view = LayoutInflater.from(getContext()).inflate(
                    R.layout.reserve_view, parent, false);
        }

        Copies current_copy = getItem(position);

        TextView accession = reserve_view.findViewById(R.id.accession);
        TextView code = reserve_view.findViewById(R.id.code);
        TextView type = reserve_view.findViewById(R.id.type);
        TextView status = reserve_view.findViewById(R.id.status);
        TextView reserved = reserve_view.findViewById(R.id.reserved);

        accession.setText(current_copy.getAccession());
        code.setText(current_copy.getCode());
        type.setText(current_copy.getType());
        status.setText(current_copy.getStatus());
        reserved.setText(current_copy.getReserved());

        return reserve_view;
    }
}
