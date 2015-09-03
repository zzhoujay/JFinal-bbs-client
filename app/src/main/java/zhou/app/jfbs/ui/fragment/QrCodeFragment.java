package zhou.app.jfbs.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.Result;
import com.zhou.appinterface.util.LogKit;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import zhou.app.jfbs.R;

/**
 * Created by zzhoujay on 2015/8/25 0025.
 * 二维码扫描Fragment
 */
public class QrCodeFragment extends Fragment {

    private ZXingScannerView zXingScannerView;
    private ZXingScannerView.ResultHandler handle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_qr_code, container, false);
        zXingScannerView = (ZXingScannerView) v.findViewById(R.id.zxing);
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        zXingScannerView.setResultHandler(handle);
        zXingScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        zXingScannerView.stopCamera();
    }

    public void setHandle(ZXingScannerView.ResultHandler handle) {
        this.handle = handle;
    }

    public static QrCodeFragment newInstance(ZXingScannerView.ResultHandler handle) {
        QrCodeFragment qrCodeFragment = new QrCodeFragment();
        qrCodeFragment.setHandle(handle);
        return qrCodeFragment;
    }

}
