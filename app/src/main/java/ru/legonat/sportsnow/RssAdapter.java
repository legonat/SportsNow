package ru.legonat.sportsnow;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ru.legonat.sportsnow.RSS.RssItem;

public class RssAdapter extends BaseAdapter {

    private final List<RssItem> items;
    private final Context context;


    public RssAdapter(Context context, List<RssItem> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        String pDate;
        Date date;
        SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy kk:mm:ss Z", Locale.ENGLISH);
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.rss_item, null);
            holder = new ViewHolder();
            holder.itemTitle = (TextView) convertView.findViewById(R.id.itemTitle);
            convertView.setTag(holder);
            holder.itemDate = (TextView) convertView.findViewById(R.id.itemDate);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.itemTitle.setText(items.get(position).getTitle());// populating listView
        pDate=items.get(position).getDate();
        try {
            date=df.parse(pDate);
            pDate="" + DateUtils.getDateDifference(date);
        } catch (ParseException e) {
            Log.e("DATE PARSING", "Error parsing date..");
            pDate = "" ;
        }
        holder.itemDate.setText(pDate);
        return convertView;
    }

    static class ViewHolder {
        TextView itemTitle;
        TextView itemDate;
    }
}
