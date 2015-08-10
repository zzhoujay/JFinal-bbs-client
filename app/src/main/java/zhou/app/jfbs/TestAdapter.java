package zhou.app.jfbs;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import zhou.app.jfbs.model.Text;

/**
 * Created by zzhoujay on 2015/8/10 0010.
 */
public class TestAdapter extends RecyclerView.Adapter<TestAdapter.Holder> {

    private List<Text> texts;

    public TestAdapter(@Nullable List<Text> texts) {
        this.texts = texts;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test, null);
        Holder holder = new Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.tv.setText(texts.get(position).text);
    }

    @Override
    public int getItemCount() {
        return texts == null ? 0 : texts.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        public TextView tv;

        public Holder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.item_test_text);
        }
    }

    public void setTexts(List<Text> texts) {
        this.texts = texts;
        notifyDataSetChanged();
    }
}
