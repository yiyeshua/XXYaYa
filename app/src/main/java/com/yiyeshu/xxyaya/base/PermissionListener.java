package com.yiyeshu.xxyaya.base;

import java.util.List;

/**
 * Created by Administrator on 2017/1/4.
 */
public interface PermissionListener {
    /**权限都同意了*/
    void onGranted();
    /**权限没有通过**/
    void onDenied(List<String> deniedPermission);


}
