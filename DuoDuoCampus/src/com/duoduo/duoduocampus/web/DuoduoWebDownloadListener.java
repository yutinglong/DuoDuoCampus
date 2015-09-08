package com.duoduo.duoduocampus.web;

import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import com.duoduo.duoduocampus.R;
import com.duoduo.duoduocampus.profile.views.MessageBoxDialog;
import com.duoduo.duoduocampus.utils.DToast;
import com.tencent.smtt.sdk.DownloadListener;


public class DuoduoWebDownloadListener implements DownloadListener {

    private Activity mContext;

    public DuoduoWebDownloadListener(Activity context) {
        mContext = context;
    }

    @Override
    public void onDownloadStart(final String paramString1,
                                String paramString2, String paramString3, String paramString4,
                                long paramLong) {
        if (!mContext.isFinishing()) {
            MessageBoxDialog.Builder builder = new MessageBoxDialog.Builder(
                    mContext, 2);
            builder.setMessageAndStyle(
                    "即将通过系统默认浏览器打开下载链接：\n" + paramString1,
                    R.style.MessageBoxMessageStyle)
                    .setOtherButton("确定", R.style.DoneButtonStyle, 0,
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    Intent intent = new Intent();
                                    intent.setAction("android.intent.action.VIEW");
                                    Uri content_url = Uri
                                            .parse(paramString1);
                                    intent.setData(content_url);
                                    List<ResolveInfo> list = mContext
                                            .getPackageManager()
                                            .queryIntentActivities(
                                                    intent,
                                                    PackageManager.MATCH_DEFAULT_ONLY);
                                    if (list.size() > 0) {
                                        mContext.startActivity(intent);
                                    } else {
                                        DToast.toastShort("本机未安装相关应用！");
                                    }
                                    dialog.dismiss();
                                }
                            })
                    .setCancelButton(R.string.sync_btn_cancle,
                            R.style.CancelButtonStyle, 0, null).create()
                    .show();

        }

    }

}
