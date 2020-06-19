package com.csj.weathering.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csj.weathering.R;
import com.csj.weathering.data.Weather;

import java.util.List;
import java.util.Locale;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.MenuViewHolder> {
    class MenuViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView price;
        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.textViewName);
            price=itemView.findViewById(R.id.textViewPrice);
        }
    }
    public interface OnListItemListener{
        public void onItemClick(Weather menu);
    }

    private OnListItemListener listener;
    private List<Weather> weathers;

    public WeatherAdapter(OnListItemListener listener) {this.listener = listener;}
    public void setMenus(List<Weather> weathers){this.weathers = weathers;}

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather,parent,false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        Weather weather = weathers.get(position);
        holder.itemView.setOnClickListener(v -> listener.onItemClick(weather));
        holder.name.setText(menu.name);
        holder.price.setText(String.format(Locale.KOREA,"%,d원",menu.price));
    }

    @Override
    public int getItemCount() { return weathers==null?0:weathers.size();}


}
