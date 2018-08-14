package com.example.saleh.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by saleh on 06/06/18.
 */

public class NewsAdapter extends ArrayAdapter<News> {public NewsAdapter(Context context, List<News> news) {
    super(context, 0, news);
}
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.new_list_item, parent, false);
        }
        News currentNew = getItem(position);
        TextView titleview = (TextView) listItemView.findViewById(R.id.title_view);
        String title = currentNew.getTitle();
        titleview.setText(title);
        String section = currentNew.getSection();
        TextView sectionview =(TextView) listItemView.findViewById(R.id.section_view) ;
        sectionview.setText(section);
        String date = currentNew.getTimeInMilliseconds();
        TextView dateview = (TextView) listItemView.findViewById(R.id.date_view);
        dateview.setText(date);
        TextView authorview = (TextView) listItemView.findViewById(R.id.author_view);
        String author = currentNew.getAuthor();
        authorview.setText(author);

        return listItemView;
    }



}

