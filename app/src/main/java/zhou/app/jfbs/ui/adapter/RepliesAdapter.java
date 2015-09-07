package zhou.app.jfbs.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import zhou.app.jfbs.R;
import zhou.app.jfbs.model.Reply;
import zhou.app.jfbs.util.TimeKit;

/**
 * Created by zzhoujay on 2015/9/5 0005.
 */
public class RepliesAdapter extends RecyclerView.Adapter<RepliesAdapter.Holder>{

    private List<Reply> replies;

    @Override
    public RepliesAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reply,null);
        Holder holder=new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RepliesAdapter.Holder holder, int position) {
        Reply reply=replies.get(position);

        Picasso.with(holder.icon.getContext()).load(reply.avatar).placeholder(R.drawable.ic_iconfont_tupian)
                .error(R.drawable.ic_iconfont_tupian).into(holder.icon);
        holder.content.setText(reply.content);
        holder.time.setText(TimeKit.format(reply.inTime));
        holder.name.setText(reply.nickName);
    }

    @Override
    public int getItemCount() {
        return replies==null?0:replies.size();
    }

    public static class Holder extends RecyclerView.ViewHolder{

        public ImageView icon;
        public TextView name,content,time;

        public Holder(View itemView) {
            super(itemView);
            icon= (ImageView) itemView.findViewById(R.id.icon);
            name= (TextView) itemView.findViewById(R.id.name);
            content= (TextView) itemView.findViewById(R.id.content);
            time= (TextView) itemView.findViewById(R.id.time);
        }
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
        notifyDataSetChanged();
    }
}
