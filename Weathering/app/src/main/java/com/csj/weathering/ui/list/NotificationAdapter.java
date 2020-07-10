package com.csj.weathering.ui.list;

import android.app.Notification;
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
import com.csj.weathering.data.Information;
import com.csj.weathering.data.Weather;

import java.util.List;
import java.util.Locale;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    class NotificationViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView content;
        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
                title=itemView.findViewById(R.id.textViewTitle);
                content=itemView.findViewById(R.id.textViewContent);
        }
    }
    public interface OnListItemListener{
        public void onItemClick(Information notification);
    }

    private OnListItemListener listener;
    private List<Information> notifications;

    public NotificationAdapter(OnListItemListener listener) {this.listener = listener;}
    public void setInformations(List<Information> notifications){this.notifications = notifications;}

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification,parent,false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Information notification = notifications.get(position);
        String title = notification.title;
        String content = notification.content;
        if(title.equals("") || title == null) title = "죄송합니다\n타이틀이 없네요..";
        if(content.equals("")|| content == null) content = "죄송합니다\n알려드릴 내용이..여기 있었는데!";
        holder.title.setText(title);
        holder.content.setText(content);
    }

    @Override
    public int getItemCount() {
        return notifications==null?0:notifications.size();
    }
}
