package com.wuc.petra.viewinject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.wuc.petra.R;
import com.wuc.petra.util.SignatureUtils;
import com.wuc.viewinject.annotation.ContentView;
import com.wuc.viewinject.annotation.OnClick;
import com.wuc.viewinject.annotation.ViewInject;
import com.wuc.viewinject.apt.ViewInjectApt;

/**
 * @author wuc
 * @date 2018/6/20
 */
@ContentView(R.layout.activity_inject_test)
public final class InjectTestAptActivity extends AppCompatActivity {

    @ViewInject(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInjectApt.inject(this);
    }

    @OnClick(R.id.btn)
    void onClick(View v) {
        if (v.getId() == R.id.btn) {
            String signature = SignatureUtils.getSignature(this);
            textView.setText(signature);
        }
    }


}
