package zhou.app.jfbs.ui.adapter;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zhou.appinterface.callback.OnItemClickListener;
import com.zhou.appinterface.util.LogKit;

import java.text.SimpleDateFormat;
import java.util.List;

import zhou.app.jfbs.R;
import zhou.app.jfbs.model.Topic;
import zhou.app.jfbs.ui.activity.TopicDetailActivity;
import zhou.app.jfbs.util.TimeKit;

/**
 * Created by zzhoujay on 2015/8/28 0028.
 */
public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.Holder> {

    private List<Topic> topics;

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic, null);
        Holder holder = new Holder(view);
        holder.setOnItemClickListener(((item, position) -> {
            Intent intent=new Intent(item.getContext(), TopicDetailActivity.class);
            intent.putExtra(Topic.TOPIC, (Parcelable) topics.get(position));
            item.getContext().startActivity(intent);
        }));
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Topic topic = topics.get(position);

        Picasso.with(holder.icon.getContext()).load(topic.avatar).placeholder(R.drawable.ic_iconfont_tupian)
                .error(R.drawable.ic_iconfont_tupian).into(holder.icon);
        holder.title.setText(topic.title);
        holder.content.setText(topic.content);
        holder.time.setText(TimeKit.formatSimple(topic.modifyTime == null ? topic.inTime : topic.modifyTime));
        holder.reply.setText(String.format("%d回复", topic.replyCount));
        holder.view.setText(String.format("%d浏览", topic.view));
    }

    @Override
    public int getItemCount() {
        return topics == null ? 0 : topics.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        public ImageView icon;
        public TextView title, content, reply, view, time;

        private OnItemClickListener onItemClickListener;

        public Holder(View itemView) {
            super(itemView);

            icon = (ImageView) itemView.findViewById(R.id.icon);
            title = (TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.content);
            reply = (TextView) itemView.findViewById(R.id.reply);
            view = (TextView) itemView.findViewById(R.id.view);
            time = (TextView) itemView.findViewById(R.id.time);

            itemView.setOnClickListener(v -> {
                if(onItemClickListener!=null){
                    onItemClickListener.onItemClick(v,getAdapterPosition());
                }
            });
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
        notifyDataSetChanged();
    }

    public void addTopics(List<Topic> topics) {
        if (this.topics == null) {
            this.topics = topics;
            notifyDataSetChanged();
        } else {
            int start = this.topics.size();
            int count = topics.size();
            this.topics.addAll(topics);
            notifyItemRangeInserted(start, count);
        }
    }
}
