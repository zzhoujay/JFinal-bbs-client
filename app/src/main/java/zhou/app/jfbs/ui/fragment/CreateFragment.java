package zhou.app.jfbs.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.zhou.appinterface.callback.LoadCallback;
import com.zhou.appinterface.data.DataManager;
import com.zhou.appinterface.util.LogKit;

import java.util.List;

import zhou.app.jfbs.App;
import zhou.app.jfbs.R;
import zhou.app.jfbs.model.Reply;
import zhou.app.jfbs.model.Section;
import zhou.app.jfbs.model.Topic;
import zhou.app.jfbs.ui.activity.TopicDetailActivity;
import zhou.app.jfbs.util.NetworkKit;

/**
 * Created by zhou on 15/9/13.
 */
public class CreateFragment extends Fragment {

    private static final int ID_DONE = 0x123456;

    private EditText title, content;
    private Spinner type;
    private boolean isTopic;
    private MenuItem done;
    private String token;
    private List<Section> sections;
    private String topicId;
    private String quote;
    private LoadCallback<Reply> replyCallback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            isTopic = bundle.getBoolean(Topic.TOPIC, false);
            token = bundle.getString(App.TOKEN);
            topicId = bundle.getString(Topic.TOPIC_ID);
        }
        setHasOptionsMenu(true);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create, container, false);
        initView(view);
        if (isTopic) {
            DataManager.getInstance().get(App.SAVE_SECTIONS, sections -> {
                if (sections != null && sections instanceof List) {
                    try {
                        List<Section> ss = (List<Section>) sections;
                        this.sections = ss;
                        setType(ss);
                    } catch (Exception e) {
                        LogKit.d("cast", "CreateFragment", e);
                    }
                }
            });
        }
        return view;
    }

    private void initView(View view) {
        title = (EditText) view.findViewById(R.id.title);
        type = (Spinner) view.findViewById(R.id.spinner);
        content = (EditText) view.findViewById(R.id.content);

        View head = view.findViewById(R.id.head);

        if (isTopic) {
            head.setVisibility(View.VISIBLE);
            title.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    checkState();
                }
            });
        }

        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkState();
            }
        });

    }

    private void setType(List<Section> sections) {
        if (isTopic) {
            int size = sections.size();
            String[] types = new String[size];
            for (int i = 0; i < size; i++) {
                types[i] = sections.get(i).name;
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, types);
            type.setAdapter(adapter);
        }
    }

    private void checkState() {
        boolean flag=isTopic ? !TextUtils.isEmpty(title.getText()) && !TextUtils.isEmpty(content.getText()) : !TextUtils.isEmpty(content.getText());
        done.setEnabled(flag);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        done = menu.add(0, ID_DONE, 0, R.string.done);
        done.setIcon(R.drawable.menu_done_background);
        done.setEnabled(false);
        done.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case ID_DONE:
                if (isTopic) {
                    if (sections != null) {
                        NetworkKit.createTopic(token, title.getText().toString(), sections.get(type.getSelectedItemPosition()).tab, content.getText().toString(), null, result -> {
                            if (result.isSuccessful()) {
                                Toast.makeText(getActivity(), R.string.success_create_topic, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), TopicDetailActivity.class);
                                intent.putExtra(Topic.TOPIC, (Parcelable) result.detail);
                                startActivity(intent);
                                getActivity().finish();
                            } else {
                                Toast.makeText(getActivity(), result.description, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(getActivity(), R.string.error_sections_null, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    NetworkKit.createReply(token, topicId, content.getText().toString(), quote, result -> {
                        if (result.isSuccessful()) {
                            Toast.makeText(getActivity(), R.string.success_create_reply, Toast.LENGTH_SHORT).show();
                            if (replyCallback != null) {
                                replyCallback.loadComplete(result.detail.reply);
                            }
                            getActivity().finish();
                        } else {
                            Toast.makeText(getActivity(), result.description, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public void setReplyCallback(LoadCallback<Reply> replyCallback) {
        this.replyCallback = replyCallback;
    }

    public static CreateFragment newInstance(boolean isTopic, String token, String topicId) {
        CreateFragment fragment = new CreateFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(Topic.TOPIC, isTopic);
        bundle.putString(App.TOKEN, token);
        bundle.putString(Topic.TOPIC_ID, topicId);
        fragment.setArguments(bundle);
        return fragment;
    }
}
