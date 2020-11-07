package com.chryl.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Scanner;

/**
 * Created by Chr.yl on 2020/11/7.
 *
 * @author Chr.yl
 */
public class KeyGenerator {

    private static String algorithm = null;
    private static int signatureLength = 0;


    public static void main(String[] args) {
        Scanner stdbin = new Scanner(System.in);
        String input = null;
        while (true) {
            if (algorithm == null) {
                System.out.println("请输入数字1或2,选择相应的秘钥对的生成算法:\n1-->RSA    2-->DSA");
                input = stdbin.next().trim();
                if (input.equals("1")) {
                    algorithm = "RSA";
                } else if (input.equals("2")) {
                    algorithm = "DSA";
                } else {
                    System.out.println("输入有误,重新输入");
                    continue;
                }
            }

            if (signatureLength == 0) {
                System.out.println("请输入数字1或2,选择相应的数字签名的长度\n1-->64位   2-->128位");
                input = stdbin.next().trim();
                if (input.equals("1")) {
                    signatureLength = 512;
                } else if (input.equals("2")) {
                    signatureLength = 1024;
                } else {
                    System.out.println("输入有误,重新输入");
                    continue;
                }
            }

            KeyPairGenerator pairGen;
            try {
                pairGen = KeyPairGenerator.getInstance(algorithm);
                SecureRandom secureRandom = new SecureRandom("ABCDEFGHIJK".getBytes());
                pairGen.initialize(signatureLength, secureRandom);
                KeyPair keyPair = pairGen.generateKeyPair();
                if (algorithm.equals("RSA")) {
                    generateRSAKey(keyPair);
                } else {
                    generateDSAKey(keyPair);
                }
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    private static void generateDSAKey(KeyPair keyPair) throws IOException {
        DSAPublicKey publicKey = (DSAPublicKey) keyPair.getPublic();
        DSAPrivateKey privateKey = (DSAPrivateKey) keyPair.getPrivate();

        FileOutputStream foutPub = new FileOutputStream("./public.cer");
        foutPub.write(publicKey.getEncoded());
        foutPub.close();
        System.out.println("生成公钥完毕");

        FileOutputStream foutPri = new FileOutputStream("./private.key");
        foutPri.write(privateKey.getEncoded());
        foutPri.close();
        System.out.println("生成私钥完毕");
    }

    private static void generateRSAKey(KeyPair keyPair) throws IOException {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        FileOutputStream foutPub = new FileOutputStream("./public.cer");
        foutPub.write(publicKey.getEncoded());
        foutPub.close();
        System.out.println("生成公钥完毕");

        FileOutputStream foutPri = new FileOutputStream("./private.key");
        foutPri.write(privateKey.getEncoded());
        foutPri.close();
        System.out.println("生成私钥完毕");
    }
}
