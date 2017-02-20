package pub.devrel.easypermissions;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

/**
 * Created by shucc on 17/2/20.
 * cc@cchao.org
 */
public class PermissionDialog extends AppCompatDialog {

    private TextView textTitle;
    private TextView textRotionale;
    private TextView textNegative;
    private TextView textPositive;

    private Context context;

    private String title;

    private String content;

    private String cancelText;

    private String okText;

    private OnClickListener negativeListener;

    private OnClickListener positiveListener;

    public PermissionDialog(Context context, String title, String content, String textNegative
            , String textPositive) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.title = title;
        this.content = content;
        this.cancelText = textNegative;
        this.okText = textPositive;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_perm);

        textTitle = (TextView) findViewById(R.id.dialog_perm_title);
        textRotionale = (TextView) findViewById(R.id.dialog_perm_rationale);
        textNegative = (TextView) findViewById(R.id.dialog_perm_negative);
        textPositive = (TextView) findViewById(R.id.dialog_perm_positive);

        textTitle.setText(title);
        textRotionale.setText(content);
        textNegative.setText(cancelText);
        textPositive.setText(okText);

        textNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (negativeListener == null) {
                    dismiss();
                } else {
                    negativeListener.onClick(PermissionDialog.this, DialogInterface.BUTTON_NEGATIVE);
                }
            }
        });
        textPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (positiveListener == null) {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                    intent.setData(uri);
                    context.startActivity(intent);
                    dismiss();
                } else {
                    positiveListener.onClick(PermissionDialog.this, DialogInterface.BUTTON_POSITIVE);
                }
            }
        });
    }

    public void setNegativeListener(final OnClickListener negativeListener) {
        this.negativeListener = negativeListener;
    }

    public void setPositiveListener(final OnClickListener positiveListener) {
        this.positiveListener = positiveListener;
    }

    public static class Builder {

        private Context context;

        private String title;

        private String content;

        private String cancelText;

        private String okText;

        private boolean cancelable;

        private OnClickListener negativeListener;

        private OnClickListener positiveListener;

        public Builder(Activity activity) {
            context = activity;
        }

        public Builder(Fragment fragment) {
            context = fragment.getContext();
        }

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setTitle(int titleID) {
            title = context.getString(titleID);
            return this;
        }

        public Builder setMessage(String content) {
            this.content = content;
            return this;
        }

        public Builder setMessage(int contentID) {
            content = context.getString(contentID);
            return this;
        }

        public Builder setNegativeButton(String cancelText, OnClickListener negativeListener) {
            this.cancelText = cancelText;
            this.negativeListener = negativeListener;
            return this;
        }

        public Builder setNegativeButton(int cancelTextID, OnClickListener negativeListener) {
            cancelText = context.getString(cancelTextID);
            this.negativeListener = negativeListener;
            return this;
        }

        public Builder setPositiveButton(String okText, OnClickListener positiveListener) {
            this.okText = okText;
            this.positiveListener = positiveListener;
            return this;
        }

        public Builder setPositiveButton(int okTextID, OnClickListener positiveListener) {
            okText = context.getString(okTextID);
            this.positiveListener = positiveListener;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public PermissionDialog create() {
            title = TextUtils.isEmpty(title) ? context.getString(R.string.title_settings_dialog) : title;
            content = TextUtils.isEmpty(content) ? context.getString(R.string.rationale_ask_again) : content;
            cancelText = TextUtils.isEmpty(cancelText) ? context.getString(R.string.dialog_perm_negative) : cancelText;
            okText = TextUtils.isEmpty(okText) ? context.getString(R.string.dialog_perm_positive) : okText;
            PermissionDialog permissionDialog = new PermissionDialog(context, title, content, cancelText, okText);
            permissionDialog.setCancelable(cancelable);
            if (cancelable) {
                permissionDialog.setCanceledOnTouchOutside(true);
            }
            permissionDialog.setNegativeListener(negativeListener);
            permissionDialog.setPositiveListener(positiveListener);
            return permissionDialog;
        }
    }
}