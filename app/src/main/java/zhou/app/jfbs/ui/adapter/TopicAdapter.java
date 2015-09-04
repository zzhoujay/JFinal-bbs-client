package zhou.app.jfbs.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

import zhou.app.jfbs.R;
import zhou.app.jfbs.model.Topic;

/**
 * Created by zzhoujay on 2015/8/28 0028.
 */
public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.Holder> {

    private List<Topic> topics;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic, null);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Topic topic = topics.get(position);

        Picasso.with(holder.icon.getContext()).load(topic.avatar).placeholder(R.drawable.ic_iconfont_tupian)
                .error(R.drawable.ic_iconfont_tupian).into(holder.icon);
        holder.title.setText(topic.title);
        holder.content.setText(topic.content);
        holder.time.setText(format.format(topic.modifyTime == null ? topic.inTime : topic.modifyTime));
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

        public Holder(View itemView) {
            super(itemView);

            icon = (ImageView) itemView.findViewById(R.id.icon);
            title = (TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.content);
            reply = (TextView) itemView.findViewById(R.id.reply);
            view = (TextView) itemView.findViewById(R.id.view);
            time = (TextView) itemView.findViewById(R.id.time);
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
        } else {
            this.topics.addAll(topics);
        }
    }
}
