package pub.devrel.easypermissions;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

class PermissionConfig {

    int positiveButton;
    int negativeButton;
    int requestCode;
    String[] permissions;

    PermissionConfig(@StringRes int positiveButton, @StringRes int negativeButton,
                     int requestCode,
                     @NonNull String[] permissions) {
        this.positiveButton = positiveButton;
        this.negativeButton = negativeButton;
        this.requestCode = requestCode;
        this.permissions = permissions;
    }
}
