package zhou.app.jfbs.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import zhou.app.jfbs.R;
import zhou.app.jfbs.model.Notification;
import zhou.app.jfbs.model.NotificationResult;
import zhou.app.jfbs.util.TimeKit;

/**
 * Created by zhou on 15/9/11.
 * 通知列表适配器
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.Holder>{

    private List<Notification> olderNotifications;
    private List<Notification> newNotifications;

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification,null);
        Holder holder=new Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Notification notification;
        boolean read;
        if(position>=newNotifications.size()){
            read=true;
            notification=olderNotifications.get(position-newNotifications.size());
        }else {
            read=false;
            notification=newNotifications.get(position);
        }

        holder.name.setText(notification.nickName);
        holder.action.setText(notification.massage);
        holder.more.setText(notification.title);
        holder.state.setText(read?"已读":"未读");
        holder.time.setText(TimeKit.formatSimple(notification.inTime));
        holder.state.setTextColor(holder.state.getResources().getColor(read?R.color.material_grey_400:R.color.material_red_400));
    }

    @Override
    public int getItemCount() {
        return (olderNotifications==null?0:olderNotifications.size())+(newNotifications==null?0:newNotifications.size());
    }

    public static class Holder extends RecyclerView.ViewHolder{

        public TextView name,action,more,state,time;

        public Holder(View itemView) {
            super(itemView);
            name= (TextView) itemView.findViewById(R.id.name);
            action= (TextView) itemView.findViewById(R.id.action);
            more= (TextView) itemView.findViewById(R.id.more);
            state= (TextView) itemView.findViewById(R.id.state);
            time= (TextView) itemView.findViewById(R.id.time);
        }
    }

    public void setNotifications(NotificationResult notifications){
        if(notifications!=null){
            this.olderNotifications=notifications.oldMessages;
            this.newNotifications=notifications.notifications;
            notifyDataSetChanged();
        }
    }
}
