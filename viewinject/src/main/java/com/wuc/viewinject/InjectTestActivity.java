package com.wuc.viewinject;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wuc.viewinject.annotation.ContentView;
import com.wuc.viewinject.utils.InjectUtils;
import com.wuc.viewinject.annotation.ViewInject;
import com.wuc.viewinject.utils.MD5;

/**
 * @author wuc
 * @date 2018/5/29
 */
@ContentView(R.layout.activity_inject_test)
public class InjectTestActivity extends AppCompatActivity {

    @ViewInject(R.id.btn)
    private Button btn;
    @ViewInject(R.id.textView)
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectUtils.inject(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSignature();
            }
        });
    }

    private void getSignature() {
        Signature[] rawSignature = getRawSignature(this, BuildConfig.APPLICATION_ID);

        for (Signature signature : rawSignature) {
            String messageDigest = MD5.getMD5String(signature.toByteArray());
            textView.setText(messageDigest);
        }
    }

    private Signature[] getRawSignature(Context context, String packageName) {
        if ((packageName == null) || (packageName.length() == 0)) {
            return null;
        }
        PackageManager localPackageManager = context.getPackageManager();
        PackageInfo localPackageInfo;
        try {
            localPackageInfo = localPackageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            if (localPackageInfo == null) {
                return null;
            }
        } catch (PackageManager.NameNotFoundException localNameNotFoundException) {
            return null;
        }
        return localPackageInfo.signatures;
    }

}
