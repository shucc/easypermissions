# EasyPermissions [![Build Status][1]][2]

对Google的[easypermissions](https://github.com/googlesamples/easypermissions)进行了一部分修改

## 配置

在项目的build.gradle中,添加:

```groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
在使用库的module中添加:
```groovy
	dependencies {
	    compile 'com.github.shucc:easypermissions:v0.4.0'
	}
```

## 使用

```java
public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    ...

    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void cameraTask() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            Toast.makeText(this, "TODO: Camera things", Toast.LENGTH_LONG).show();
        } else {
            //移除一个参数
            EasyPermissions.requestPermissions(this, RC_CAMERA_PERM, Manifest.permission.CAMERA);
        }
    }

    ...

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        switch (requestCode) {
            case RC_CAMERA_PERM:
                //可自定义布局,自定义布局中id必须使用指定的
                new AppSettingsDialog.Builder(this)
                        .setRequestCode(RC_CAMERA_PERM)
                        .setLayoutID(R.layout.dialog_perm_customize)
                        .setTitle(R.string.camera_perm_title)
                        .setRationale(R.string.camera_perm_rationale)
                        .build()
                        .show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //取消自定义的权限提示框后,以及从设置界面返回调用
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            //默认的requestCode
        } else {
            //自定义的requestCode
            switch (requestCode) {
                case RC_CAMERA_PERM:
                    ...
                default:
                    break;
            }
        }
    }
}
```
