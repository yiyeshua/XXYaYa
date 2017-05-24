package com.yiyeshu.imagepicker;

import android.graphics.Bitmap;

/**
 * Created by lhw on 2017/5/23.
 */
public interface PhotoCallback {
    //void onFail(String var1, Throwable var2, int var3);

    void onSuccess(Bitmap bitmap, String imagePath);
}
