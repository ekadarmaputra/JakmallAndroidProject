package com.example.ekadarmaputra.jakmall_androidproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ekadarmaputra.jakmall_androidproject.Model.JokesList;
import com.example.ekadarmaputra.jakmall_androidproject.R;

import java.util.List;

public class MyJokesAdapter extends ArrayAdapter<JokesList>{
    List<JokesList> jokesList;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public MyJokesAdapter(Context context, List<JokesList> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        jokesList = objects;
    }

    public int getCount(){
        return jokesList.size();
    }

    @Override
    public JokesList getItem(int position) {
        return jokesList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.layout_row_view, parent, false);
            viewHolder = ViewHolder.create((RelativeLayout) view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        JokesList item = getItem(position);

        viewHolder.textViewJokes.setText(item.getJoke());

        return viewHolder.rootView;
    }

    private static class ViewHolder {
        public final RelativeLayout rootView;
        public final TextView textViewJokes;

        private ViewHolder(RelativeLayout rootView, TextView textViewJokes) {
            this.rootView = rootView;
            this.textViewJokes = textViewJokes;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            TextView textViewJokes = (TextView) rootView.findViewById(R.id.textViewJokes);
            return new ViewHolder(rootView, textViewJokes);
        }
    }
}
