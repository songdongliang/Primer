package com.sdl.primer.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by 11870 on 2017/2/15.
 */

public class CloseableUtil {

    public static void close(Closeable closeable){
        if(closeable != null){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
