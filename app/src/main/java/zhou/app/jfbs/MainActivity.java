package zhou.app.jfbs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import zhou.app.jfbs.ui.activity.QrCodeActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        /*NetworkKit.sections(result -> {
            List<Section> sections = result.detail;
            LogKit.d("sections", sections);
        });

        NetworkKit.index(1, "gs", 20, result -> {
            TopicPage page = result.detail;
            LogKit.d("page", page);
        });*/

        /*NetworkKit.topic("9debda43e7294e5c9c1026e92e5e27ad", result -> {
            TopicWithReply topicWithReply = result.detail;
            LogKit.d("topicWithReply", topicWithReply);
        });*/

        /*NetworkKit.userInfo("f9fc3ec0c8a145b1ad7ca28563fea066", result -> {
            UserResult user = result.detail;
            LogKit.d("user", user);
        });

        startActivity(new Intent(this, QrCodeActivity.class));*/

    }

    public void qrCode(View view) {
        startActivityForResult(new Intent(this, QrCodeActivity.class), 12);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 12 && resultCode == RESULT_OK) {
            String result = data.getStringExtra(QrCodeActivity.RESULT);
            String[] rs = result.split("@||@");
            if (rs.length == 2) {

            } else {
                Toast.makeText(this, R.string.error_qr_code, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
