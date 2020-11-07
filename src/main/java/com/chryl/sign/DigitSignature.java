package com.chryl.sign;

import com.chryl.util.SignStringUtil;

import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by Chr.yl on 2020/11/7.
 *
 * @author Chr.yl
 */
public class DigitSignature {

    public static String keyAlgorithm = "RSA";//请求秘钥算法名称
    public static String signAlgorithm = "SHA1WithRSA";//签名算法
    public static String signLength = "128";//签名宽敞度
    //    public static String keyLoc = "/Users/chryl/develop/work_Spaces/idea_Project/chr-sign/";//证书路径,注意后面有 /
    public static String keyLoc = "./";//证书路径,注意后面有 /

    //验签
    public static boolean verify(byte[] data) {
        try {
            //签名域:signatureData
            byte[] signatureData = SignStringUtil.subArray(data, data.length - Integer.parseInt(signLength));
            //数据域:msgData
            byte[] msgData = SignStringUtil.subArray(data, 0, data.length - Integer.parseInt(signLength));
            //获取公钥
            PublicKey publicKey = generatePublicKey();
            //获取签名算法
            Signature sign = Signature.getInstance(signAlgorithm);
            //初始化公钥
            sign.initVerify(publicKey);
            //更新需要验签的数据域
            sign.update(msgData);
            //验签
            return sign.verify(signatureData);
        } catch (Exception e) {
            System.out.println("验签失败");
        }
        return false;
    }

    //加签
    public static byte[] sign(byte[] data) {
        try {
            //获取私钥
            PrivateKey privateKey = generatePrivateKey();
            //获取签名
            Signature sign = Signature.getInstance(signAlgorithm);
            //初始化私钥
            sign.initSign(privateKey);
            //更新要加签的数据域
            sign.update(data);
            //加签
            return sign.sign();
        } catch (Exception e) {
            System.out.println("验签失败");
        }
        return null;
    }

    //获取数据域+签名域 中的数据域信息
    public static byte[] msgDataSplit(byte[] data) {
        return SignStringUtil.subArray(data, 0, data.length - Integer.parseInt(signLength));
    }

    //公钥
    private static PublicKey generatePublicKey() throws Exception {
        FileInputStream fin;
        try {
            fin = new FileInputStream(keyLoc + "public.cer");
            byte[] encodedPubKey = new byte[fin.available()];
            fin.read(encodedPubKey);
            fin.close();

            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encodedPubKey);

            KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
            PublicKey publicKey = keyFactory.generatePublic(pubKeySpec);
            return publicKey;
        } catch (Exception e) {
            System.out.println("获取数字签名公钥信息出错");
            throw e;
        }
    }

    //私钥
    private static PrivateKey generatePrivateKey() throws Exception {
        FileInputStream fin;
        try {
            fin = new FileInputStream(keyLoc + "private.key");
            byte[] encodedPrivate = new byte[fin.available()];
            fin.read(encodedPrivate);
            fin.close();

            PKCS8EncodedKeySpec priKeySpec = new PKCS8EncodedKeySpec(encodedPrivate);

            //返回转换指定算法的private/public 关键字的 KeyFactory 对象
            KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
            PrivateKey privateKey = keyFactory.generatePrivate(priKeySpec);
            return privateKey;
        } catch (Exception e) {
            System.out.println("获取数字签名私钥信息出错");
            throw e;
        }
    }


    public static void main(String[] args) throws Exception {

        String orgDate = "01231231231232131313131312111111112323";
        byte[] orgDateBytes = orgDate.getBytes();//元数据

        byte[] sign = sign(orgDateBytes);//签名

        byte[] wholeData = SignStringUtil.appendDataCheckField(orgDateBytes, sign);

        System.out.println(verify(wholeData));


        show22();
    }


    public static void show22() throws Exception {
        byte[] da = "23124889asd3".getBytes();

        PrivateKey privateKey = generatePrivateKey();
        PublicKey publicKey = generatePublicKey();

        /**
         *
         */
        Signature sign = Signature.getInstance(signAlgorithm);
        sign.initSign(privateKey);
        sign.update(da);
        byte[] signDate = sign.sign();

        /**
         *
         */
        Signature verif = Signature.getInstance(signAlgorithm);
        verif.initVerify(publicKey);
        verif.update(da);
        boolean verify = verif.verify(signDate);


        System.out.println(verify);


    }
}
