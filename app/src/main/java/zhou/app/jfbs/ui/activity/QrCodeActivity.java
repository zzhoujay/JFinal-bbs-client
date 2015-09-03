package zhou.app.jfbs.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.google.zxing.Result;
import com.zhou.appinterface.ui.ToolbarActivity;
import com.zhou.appinterface.util.LogKit;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import zhou.app.jfbs.R;
import zhou.app.jfbs.ui.fragment.QrCodeFragment;

/**
 * Created by zzhoujay on 2015/8/25 0025.
 */
public class QrCodeActivity extends ToolbarActivity implements ZXingScannerView.ResultHandler {

    public static final String RESULT = "result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        quickFinish();
        getToolbar().setBackgroundColor(getResources().getColor(R.color.material_lightGreen_500));
        setContent(QrCodeFragment.newInstance(this));
    }

    @Override
    public void handleResult(Result result) {
        Intent intent = new Intent();
        intent.putExtra(RESULT, result.getText());
        LogKit.d("text", result.getText());
        setResult(RESULT_OK, intent);
        finish();
    }
}
