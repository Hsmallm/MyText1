package com.example.administrator.text1.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by HanTuo on 16/7/27.
 * (注：1、有些调用saveAsync（）写入缓存的：因为有些文件过于太大，解析的时间过长，所以要用异步线程，减轻主线程的压力
 *      2、saveAsync(A，B)带两参的写入缓存：是为了解决文件名称的重复，特别是一些变量级别文件：String、Map<String,String></>)
 *      3、这里初始化文件的相关路径DEFAULT_DIR的方法init,是在ThApplication里面进行的，也就是使其在程序刚开始运行时就进行初始化、具有与整个应用同样的生命周期
 */
public class ObjCacheUtil {
    private static File DEFAULT_DIR;

    public static void init(Context context) {
        DEFAULT_DIR = context.getExternalFilesDir("ObjectCache");
    }

    public static void saveAsync(final Callback<Boolean> callback, @NonNull final File file, @NonNull final Object obj) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final boolean success = save(file, obj);
                if (null != callback)
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onResult(success);
                        }
                    });
            }
        }).start();
    }

    public static void saveAsync(final Callback<Boolean> callback, @NonNull final String name, @NonNull final Object obj) {
        saveAsync(callback, getFileFromObj(obj, name), obj);
    }

    public static void saveAsync(final Callback<Boolean> callback, @NonNull final Object obj) {
        saveAsync(callback, getFileFromObj(obj), obj);
    }

    public static void saveAsync(@NonNull File file, @NonNull Object obj) {
        saveAsync(null, file, obj);
    }

    public static void saveAsync(@NonNull String name, @NonNull Object obj) {
        saveAsync(null, name, obj);
    }

    public static void saveAsync(@NonNull Object obj) {
        saveAsync((Callback<Boolean>) null, obj);
    }

    /**
     * 保存/写入缓存
     * 返回ture:表示成功；false表示失败
     * @param file
     * @param obj
     * @return
     */
    public static boolean save(@NonNull File file, @NonNull Object obj) {
        try {
            File file1 = file.getParentFile();
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            //实例化写入的新的文件路径及其格式
            File tmpFile = new File(file.getPath() + ".tmp");
            ///-----文件的缓存操作
            //得到文件的输出流
            FileOutputStream fileOutputStream = new FileOutputStream(tmpFile);
            String string = new Gson().toJson(obj);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(string.getBytes());
            bufferedOutputStream.flush();
            fileOutputStream.close();
            //文件缓存成功并重命名
            return tmpFile.renameTo(file);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean save(@NonNull String name, @NonNull Object obj) {
        return save(getFileFromObj(obj, name), obj);
    }

    public static boolean save(@NonNull Object obj) {
        return save(getFileFromObj(obj), obj);
    }

    public static <T> void getAsync(final Callback<T> callback, @NonNull final File file) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Type type = callback.getClass().getGenericInterfaces()[0];
                type = ((ParameterizedType) type).getActualTypeArguments()[0];
                final T t = (T) get(file, type);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResult(t);
                    }
                });
            }
        }).start();
    }

    public static <T> void getAsync(final Callback<T> callback, String name) {
        getAsync(callback, getFile(callback, name));
    }

    public static <T> void getAsync(final Callback<T> callback) {
        getAsync(callback, getFile(callback));
    }


    private static <T> File getFile(Callback<T> callback) {
        return getFile(callback, null);
    }

    private static <T> File getFile(Callback<T> callback, String name) {
        Type type = callback.getClass().getGenericInterfaces()[0];
        type = ((ParameterizedType) type).getActualTypeArguments()[0];
        String fileName = null;
        if (type instanceof Class) {
            fileName = ((Class) type).getName();
        } else {
            fileName = "Collection-" + ((Class)((ParameterizedType) type).getActualTypeArguments()[0]).getName();
        }
        if (name != null) fileName += name;
        Log.e("FileName", fileName);
        return new File(DEFAULT_DIR, fileName);
    }

    public static File getFileFromObj(Object object) {
        return getFileFromObj(object, null);
    }

    /**
     * 创建文件存储路径
     * @param object
     * @param name
     * @return
     */
    public static File getFileFromObj(Object object, String name) {
        String fileName = null;
        if (object instanceof Iterable) {
            Iterable iterable = (Iterable) object;
            fileName = "Collection-" + iterable.iterator().next().getClass().getName();
        } else if (object instanceof Class) {
            fileName = ((Class) object).getName();
        } else {
            fileName = object.getClass().getName();
        }
        if (null != name) {
            fileName += name;
        }
        Log.e("FileName", fileName);
        return new File(DEFAULT_DIR, fileName);
    }

    /**
     * 获取/读取缓存
     * @param file
     * @param clazz
     * @param <T>
     * @return
     */
    public static
    @Nullable
    <T extends Object> T get(File file, Type clazz) {
        try {
            if (file.exists()) {
                //实例化文件输入流对象
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                //将数据转化为byte数组、并进行读取
                byte[] bytes = new byte[bufferedInputStream.available()];
                bufferedInputStream.read(bytes);
                //将数据转化为String
                String string = new String(bytes);
                //在将String转化为相应的Class对象
                T t = new Gson().fromJson(string, clazz);
                return t;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static
    @Nullable
    <T extends Object> T get(String name, Class<T> clazz) {
        return get(getFileFromObj(clazz, name), clazz);
    }

    public static
    @Nullable
    <T extends Object> T get(Class<T> clazz) {
        return get(getFileFromObj(clazz), clazz);
    }

    public interface Callback<T> {
        void onResult(T t);
    }

    /**
     * 清除缓存
     * （注：只需设置其缓存内容为“”，但是其最终缓存得也是“”；但是在最终反序列化为相应得对象时，对象为null）
     * @param obj 指定得缓存文件对象
     */
    public static void clear(@NonNull Object obj) {
        clear(getFileFromObj(obj), "");
    }

    public static boolean clear(@NonNull File file, @NonNull Object obj) {
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            File tmpFile = new File(file.getPath() + ".tmp");
            FileOutputStream fileOutputStream = new FileOutputStream(tmpFile);
            String string = new Gson().toJson(obj);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(string.getBytes());
            bufferedOutputStream.flush();
            fileOutputStream.close();
            return tmpFile.renameTo(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
