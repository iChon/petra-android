package com.wuc.petra.viewinject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.wuc.petra.R;
import com.wuc.petra.util.SignatureUtils;
import com.wuc.viewinject.reflect.ViewInjectReflect;
import com.wuc.viewinject.reflect.annotation.ContentView;
import com.wuc.viewinject.reflect.annotation.OnClick;
import com.wuc.viewinject.reflect.annotation.ViewInject;

/**
 * @author wuc
 * @date 2018/5/29
 */
@ContentView(R.layout.activity_inject_test)
public class InjectTestReflectActivity extends AppCompatActivity {

    @ViewInject(R.id.textView)
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInjectReflect.inject(this);
    }

    @OnClick(R.id.btn)
    void onClick(View v) {
        if (v.getId() == R.id.btn) {
            String signature = SignatureUtils.getSignature(this);
            textView.setText(signature);
        }
    }

}
