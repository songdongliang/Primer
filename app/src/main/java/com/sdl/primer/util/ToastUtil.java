package com.sdl.primer.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 11870 on 2017/1/12.
 */

public class ToastUtil {

    public static void showMessageShort(Context context, String msg){
        Toast.makeText(context,msg, Toast.LENGTH_SHORT).show();
    }

    public static void showMessageLong(Context context, String msg){
        Toast.makeText(context,msg, Toast.LENGTH_LONG).show();
    }
}
