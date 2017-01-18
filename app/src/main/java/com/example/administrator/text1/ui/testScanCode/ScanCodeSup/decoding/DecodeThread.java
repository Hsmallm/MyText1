/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.administrator.text1.ui.testScanCode.ScanCodeSup.decoding;

import android.os.Handler;
import android.os.Looper;

import com.example.administrator.text1.ui.testScanCode.MipcaActivityCapture;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.ResultPointCallback;

import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

/**
 * This thread does all the heavy lifting of decoding the images.
 * 功能描述：这个线程解码图像
 */
final class DecodeThread extends Thread {

  public static final String BARCODE_BITMAP = "barcode_bitmap";
  private final MipcaActivityCapture activity;
  private final Hashtable<DecodeHintType, Object> hints;
  private Handler handler;
  /**
   * 前面定义了一个CountDownLatch类型变量，该变量为一个倒计数用的锁。用法挺简单，如代码中，先new CountDownLatch（1），计数值为1，
   * handlerInitLatch.countDown(), 开始倒数。handlerInitLatch.await() 若计数值没有变为0，则一直阻塞。直到计数值为0后，
   * 才return handler，因此在调用getHandler时不会返回null的handler。
   */
  private final CountDownLatch handlerInitLatch;//到计数的锁

  DecodeThread(MipcaActivityCapture activity,
               Vector<BarcodeFormat> decodeFormats,
               String characterSet,
               ResultPointCallback resultPointCallback) {

    this.activity = activity;
    handlerInitLatch = new CountDownLatch(1);//到计数的锁

    hints = new Hashtable<DecodeHintType, Object>(3);

    if (decodeFormats == null || decodeFormats.isEmpty()) {
    	 decodeFormats = new Vector<BarcodeFormat>();
    	 decodeFormats.addAll(DecodeFormatManager.ONE_D_FORMATS);
    	 decodeFormats.addAll(DecodeFormatManager.QR_CODE_FORMATS);
    	 decodeFormats.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);
    }
    
    hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);

    if (characterSet != null) {
      hints.put(DecodeHintType.CHARACTER_SET, characterSet);
    }

    hints.put(DecodeHintType.NEED_RESULT_POINT_CALLBACK, resultPointCallback);
  }

  Handler getHandler() {
    try {
      handlerInitLatch.await();//阻塞先等handler被初始化了才能返回结果。改计数锁即等countdown--
    } catch (InterruptedException ie) {
      // continue?
    }
    return handler;
  }

  /**
   * 这就是自己创建一个工作线程，为其分配一个消息队列，消息循环的简单迅速办法。
   */
  @Override
  public void run() {
    Looper.prepare();//来创建消息队列
    handler = new DecodeHandler(activity, hints);//附于该线程的handler对象
    handlerInitLatch.countDown();//启动到计数，countdown-1 变成0；
    Looper.loop();//进入消息循环（注：这个loop()循环不会立马返回，需要自己主动调用Looper.myLooper().quit()才会返回）
  }

}
