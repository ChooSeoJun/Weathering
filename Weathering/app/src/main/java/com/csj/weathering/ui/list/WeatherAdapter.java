package com.csj.weathering.ui.list;

import android.net.Uri;
import android.os.Debug;
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

import java.util.List;
import java.util.Locale;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {
    class WeatherViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView temp;
        ImageView icon;
        public WeatherViewHolder(@NonNull View itemView) {
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

    public WeatherAdapter(OnListItemListener listener) {this.listener = listener;}
    public void setWeathers(List<Weather> weathers){this.weathers = weathers;}

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather,parent,false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        Weather weather = weathers.get(position);
//        holder.itemView.setOnClickListener(v -> listener.onItemClick(weather));
        holder.title.setText(String.format(Locale.KOREA,"%d일",weather.dateTime.getDayOfMonth()));
        holder.temp.setText(String.format(Locale.KOREA,"\n최고 %.1f | 최저 %.1f",weather.maxTemp,weather.minTemp));
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
