package com.example.administrator.text1.ui.testCompressImg;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 功能描述：测试FileInputStream 与 BufferedInputStream 效率对比
 * Created by hzhm on 2016/8/16.
 */
public class TextFileOperator {

    private static final int BURRER_SIZE = 100;

    public static void copyFileStream(File src, File dest){
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;

        try {
            //初始化文件输入输出流（注：文件的输入流主要用于读取文件/文件的输出流主要用于写入文件）
            inputStream = new FileInputStream(src);
            outputStream = new FileOutputStream(dest);

            //实例化每次读取/写入的数据大小
            byte[] bytes = new byte[BURRER_SIZE];
            int copySize = 0 ;
            //每次读取时，并写入文件直到写入读取完成
            while ((copySize = inputStream.read(bytes)) > 0 ){
                outputStream.write(bytes,0,copySize);
                outputStream.flush();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void copyBufferedFileStream(File src, File dest){
        BufferedInputStream inputStream = null;
        BufferedOutputStream outputStream = null;

        try {
            inputStream = new BufferedInputStream(new FileInputStream(src));
            outputStream = new BufferedOutputStream(new FileOutputStream(dest));

            byte[] bytes = new byte[BURRER_SIZE];
            int copySize = 0;
            while ((copySize = inputStream.read(bytes)) > 0){
                outputStream.write(bytes);
                outputStream.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
