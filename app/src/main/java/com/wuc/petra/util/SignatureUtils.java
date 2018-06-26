package com.wuc.petra.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import com.wuc.petra.BuildConfig;

/**
 * @author wuc
 * @date 2018/6/26
 */
public class SignatureUtils {

    public static String getSignature(Context context) {
        Signature[] rawSignature = getRawSignature(context, BuildConfig.APPLICATION_ID);
        if (rawSignature != null && rawSignature.length > 0) {
            return MD5.getMD5String(rawSignature[0].toByteArray());
        }
        return "";
    }

    private static Signature[] getRawSignature(Context context, String packageName) {
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
