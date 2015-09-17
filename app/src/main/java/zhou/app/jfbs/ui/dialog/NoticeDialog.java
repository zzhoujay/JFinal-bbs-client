package zhou.app.jfbs.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.zhou.appinterface.util.Notifier;

import zhou.app.jfbs.R;

/**
 * Created by zhou on 15/9/11.
 */
public class NoticeDialog extends DialogFragment {

    public static final int NOTICE_CONFIRM = 0x123;

    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String CONFIRM = "confirm";

    private String title;
    private String content;
    private String confirm;
    private Notifier notifier;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            title = bundle.getString(TITLE);
            content = bundle.getString(CONTENT);
            confirm = bundle.getString(CONFIRM);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title).setMessage(content).setPositiveButton(confirm == null ? getString(R.string.confirm) : confirm, (v, i) -> {
            if (notifier != null) {
                notifier.notice(NOTICE_CONFIRM);
            }
        });
        return builder.create();
    }

    public void setNotifier(Notifier notifier) {
        this.notifier = notifier;
    }

    public static NoticeDialog newInstance(String title, String content, Notifier notifier) {
        NoticeDialog dialog = new NoticeDialog();
        dialog.setNotifier(notifier);
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putString(CONTENT, content);
        dialog.setArguments(bundle);
        return dialog;
    }

    public static NoticeDialog newInstance(String title, String content, String confirm, Notifier notifier) {
        NoticeDialog dialog = new NoticeDialog();
        dialog.setNotifier(notifier);
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putString(CONTENT, content);
        bundle.putString(CONFIRM, confirm);
        dialog.setArguments(bundle);
        return dialog;
    }
}
