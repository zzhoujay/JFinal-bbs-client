package zhou.app.jfbs;

import android.os.Bundle;

import com.zhou.appinterface.ui.ToolbarActivity;

public class MainActivity extends ToolbarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        quickFinish();
        getToolbar().setBackgroundColor(getResources().getColor(R.color.material_lightBlue_500));
        setContent(new TestFragment());

    }

}
