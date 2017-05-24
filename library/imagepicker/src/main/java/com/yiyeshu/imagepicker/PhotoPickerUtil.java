package com.yiyeshu.imagepicker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

/**
 * Created by lhw on 2017/5/23.
 */
public class PhotoPickerUtil {
    private static final String TAG = "PhotoPickerUtil";
    private static PhotoPickerUtil instance;
    private PhotoPickerUtil(){

    }
    public static PhotoPickerUtil getInstance(){
        if (instance == null) {
            instance = new PhotoPickerUtil();
        }
        return instance;
    }


    public void start(Context context, PhotoCallback photoCallback){
        setBitmapCallBack(photoCallback);
        Intent intent=new Intent();
        intent.setClass(context,PhotoPickerActivity.class);
        context.startActivity(intent);
    }
    private PhotoCallback photoCallback;
    private void setBitmapCallBack(PhotoCallback photoCallback){
        this.photoCallback=photoCallback;
    }

    public void getBitmap(Bitmap mBitmap, String imagePath) {
        photoCallback.onSuccess(mBitmap,imagePath);
    }

}
