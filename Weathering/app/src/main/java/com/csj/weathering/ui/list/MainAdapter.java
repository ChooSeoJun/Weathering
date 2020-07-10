package com.csj.weathering.ui.list;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.csj.weathering.R;
import com.csj.weathering.data.Weather;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
    class MainViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView temp;
        ImageView icon;
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
                title=itemView.findViewById(R.id.textViewTitle);
                temp=itemView.findViewById(R.id.textViewTemperature);
                icon=itemView.findViewById(R.id.imageViewIcon);
        }
    }
    public interface OnListItemListener{
        public void onItemClick(Weather weather);
    }

    private OnListItemListener listener;
    private List<Weather> weathers;

    public MainAdapter(OnListItemListener listener) {this.listener = listener;}
    public void setWeathers(List<Weather> weathers){this.weathers = weathers;}

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day_weather,parent,false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        Weather weather = weathers.get(position);
//        holder.itemView.setOnClickListener(v -> listener.onItemClick(weather));
        holder.title.setText(String.format(Locale.KOREA,"%d:00",weather.dateTime.getHour()));
        holder.temp.setText(String.format(Locale.KOREA,"\n%.1f℃",weather.temp));
        String imageUrl = "http://openweathermap.org/img/wn/" + weather.icon + "@4x.png";
        Log.i("[ImageUrl]",imageUrl);
        Glide.with(holder.itemView.getContext()).load(imageUrl).into(holder.icon);
    }

    @Override
    public int getItemCount() {
//        Log.i("[Weathers Count]", String.valueOf(weathers.size()));
        return weathers==null?0:weathers.size();
    }
}
