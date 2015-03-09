package com.rajohns.judgecardx.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rajohns.judgecardx.R;

/**
 * Created by rajohns on 3/8/15.
 *
 */
public class CreateRequestAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private boolean showCreateOptions;
    private static final int NO_RESULTS_POSITION = 0;
    private static final int CREATE_PRIVATE_POSITION = 1;
    private static final int REQUEST_SCORECARD_POSITION = 2;

    public CreateRequestAdapter(Context context, boolean showCreateOptions) {
        this.inflater = ((Activity) context).getLayoutInflater();
        this.showCreateOptions = showCreateOptions;
    }

    @Override
    public int getCount() {
        if (this.showCreateOptions) {
            return 3;
        }
        else {
            return 1;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
        }

        TextView mainText = (TextView)convertView.findViewById(android.R.id.text1);
        TextView subText = (TextView)convertView.findViewById(android.R.id.text2);

        switch (position) {
            case NO_RESULTS_POSITION:
                mainText.setText("No Results");
                break;
            case CREATE_PRIVATE_POSITION:
                mainText.setText("Create Private Scorecard");
                break;
            case REQUEST_SCORECARD_POSITION:
                mainText.setText("Request Community Scorecard");
                break;
            default:
                break;
        }

        return convertView;
    }
}
