package com.kxwon.mydevice;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by KXwon on 2016/5/25.
 */
public class MyDialog {
    /**
     * 通用对话框
     */
    private static Dialog dialog = null;

    public static void showDialog(Context ctx, String title, String content, final IDialog mDialog){
        View dialogView = LayoutInflater.from(ctx).inflate(R.layout.layout_dialog, null);
        LinearLayout layout = (LinearLayout) dialogView.findViewById(R.id.catelayout);// 加载布局

        TextView dialogTitle = (TextView) dialogView.findViewById(R.id.title);
        TextView dialogText = (TextView) dialogView.findViewById(R.id.content);

        dialogTitle.setText(title);
        dialogText.setText(content);

        Button btn_confirm = (Button) dialogView.findViewById(R.id.dialog_confirm);
        Button btn_cancel = (Button) dialogView.findViewById(R.id.dialog_cancel);

        btn_confirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                setOnClickListener(mDialog);
                dialog.dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });

        dialog = new Dialog(ctx,R.style.MyDialog);
        dialog.setContentView(layout, new LinearLayout.LayoutParams(500, LinearLayout.LayoutParams.WRAP_CONTENT));
        dialog.setCancelable(true);
        dialog.show();
    }

    public static void setOnClickListener(IDialog idialog){
        idialog.confirm();
    }

    public interface IDialog {
        void confirm();
    }
}
