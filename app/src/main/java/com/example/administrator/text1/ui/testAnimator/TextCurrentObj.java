package com.example.administrator.text1.ui.testAnimator;

import android.animation.TypeEvaluator;

/**
 * 功能描述：自定义一个实现了TypeEvaluator接口对象TextCurrentObj
 * 在这个对象中不断获取当前颜色currentColor（注：颜色，是由红、绿、蓝三种颜色进行调配的,所以....）
 * Created by HM on 2016/5/26.
 */
public class TextCurrentObj implements TypeEvaluator {

    private int currentRed = -1;
    private int currentGreen = -1;
    private int currentBlue = -1;

    private String currentColor;

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        //获取开始颜色、结束颜色
        String startColor = (String) startValue;
        String endColor = (String) endValue;
        //分别定义三个色值的开始和结束时的色值状态
        int startRed = Integer.parseInt(startColor.substring(1, 3), 16);
        int startGreen = Integer.parseInt(startColor.substring(3, 5), 16);
        int startBlue = Integer.parseInt(startColor.substring(5, 7), 16);
        int endRed = Integer.parseInt(endColor.substring(1, 3), 16);
        int endGreen = Integer.parseInt(endColor.substring(3, 5), 16);
        int endBlue = Integer.parseInt(endColor.substring(5, 7), 16);
        //设置这三个色值刚开始时的色值状态
        if (currentRed == -1) {
            currentRed = startRed;
        }
        if (currentGreen == -1) {
            currentGreen = startGreen;
        }
        if (currentBlue == -1) {
            currentBlue = startBlue;
        }
        //分别获取这三个色值的从开始到结束时的色值差
        int diffRed = Math.abs(endRed - startRed);
        int diffGreen = Math.abs(endGreen - startGreen);
        int diffBlue = Math.abs(endBlue - startBlue);
        //最终的颜色色值差为这三个色值差只和
        int diffColor = diffRed + diffGreen + diffBlue;
        //获取当前这三个的色值
        if (currentRed != endRed) {
            currentRed = getCurrentColor(startRed, endRed, diffColor, 0, fraction);
        }
        if (currentGreen != endGreen) {
            currentGreen = getCurrentColor(startGreen, endGreen, diffColor, diffRed, fraction);
        }
        if (currentBlue != endBlue) {
            currentBlue = getCurrentColor(startBlue, endBlue, diffColor, diffRed + diffGreen, fraction);
        }

        //获取当前颜色（注：当前颜色为这三个颜色之和）
        currentColor = "#" + getHexString(currentRed) + getHexString(currentGreen) + getHexString(currentBlue);
        return currentColor;
    }

    /**
     * 根据fraction值来计算当前的颜色。
     * @param startColor
     * @param endColor
     * @param diffColor
     * @param offSet
     * @param fraction
     * @return
     */
    private int getCurrentColor(int startColor, int endColor, int diffColor, int offSet, float fraction) {
        int currentColor;
        //如果开始的色值比结束时大
        if (startColor > endColor) {
            currentColor = (int) (startColor - (fraction * diffColor - offSet));
            //最后将当前色值与最终色值做大小比较，取最小
            if (currentColor < endColor) {
                currentColor = endColor;
            }
        } else {
            currentColor = (int) (startColor + (fraction * diffColor - offSet));
            //最后将当前色值与最终色值做大小比较，取最小
            if (currentColor > endColor) {
                currentColor = endColor;
            }
        }
        return currentColor;
    }

    /**
     * 将10进制颜色值转换成16进制。
     * @param value
     * @return
     */
    private String getHexString(int value){
        String hexString = Integer.toHexString(value);
        if(hexString.length() == 1){
            hexString = "0" + hexString;
        }
        return hexString;
    }
}
