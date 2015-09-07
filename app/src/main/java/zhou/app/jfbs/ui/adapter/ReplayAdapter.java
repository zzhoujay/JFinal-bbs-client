package zhou.app.jfbs.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import zhou.app.jfbs.model.Reply;

/**
 * Created by zzhoujay on 2015/9/5 0005.
 */
public class ReplayAdapter extends RecyclerView.Adapter<TopicAdapter.Holder>{

    private List<Reply> replies;

    @Override
    public TopicAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(TopicAdapter.Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return replies==null?0:replies.size();
    }

    public static class Holder extends RecyclerView.ViewHolder{

        public Holder(View itemView) {
            super(itemView);
        }
    }
}
