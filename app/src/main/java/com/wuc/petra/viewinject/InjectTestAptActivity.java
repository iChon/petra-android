package com.wuc.petra.viewinject;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.wuc.petra.BuildConfig;
import com.wuc.petra.R;
import com.wuc.petra.util.MD5;
import com.wuc.viewinject.annotation.ContentView;
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

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSignature();
            }
        });
    }

//    @OnClick(R.id.btn)
//    void onClick(View v) {
//        if (v.getId() == R.id.btn) {
//            getSignature();
//        }
//    }

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
