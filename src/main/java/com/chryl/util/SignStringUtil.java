package com.chryl.util;

/**
 * Created by Chr.yl on 2020/11/7.
 *
 * @author Chr.yl
 */
public class SignStringUtil {

    public static byte[] subArray(byte[] arr, int beginIndex, int endIndex) {
        byte[] result = new byte[endIndex - beginIndex];
        for (int i = beginIndex; i < endIndex; i++) {
            result[i - beginIndex] = arr[i];
        }
        return result;
    }


    public static byte[] subArray(byte[] arr, int beginIndex) {
        return subArray(arr, beginIndex, arr.length);
    }

    public static byte[] appendDataCheckField(byte[] data, byte[] dataCheckField) {
        int dataLength = data.length;
        int dataCheckFieldLength = dataCheckField.length;

        byte[] wholeData = new byte[dataLength + dataCheckFieldLength];

        for (int i = 0; i < dataLength; i++) {
            wholeData[i] = data[i];
        }
        for (int i = 0; i < dataCheckFieldLength; i++) {
            wholeData[i + dataLength] = dataCheckField[i];
        }
        return wholeData;
    }

}
