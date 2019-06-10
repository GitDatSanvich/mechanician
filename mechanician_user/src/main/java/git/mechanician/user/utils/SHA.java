package git.mechanician.user.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @ClassName SHA
 * @Author GitDatSanvich
 * @Date 2019/6/9 20:08
 **/

public class SHA {
    private static final String KEY_SHA = "SHA";

    public String getResult(String inputStr) {
        BigInteger sha = null;
        byte[] inputData = inputStr.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(KEY_SHA);
            messageDigest.update(inputData);
            sha = new BigInteger(messageDigest.digest());
            System.out.println("SHA加密后:" + sha.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sha.toString();
    }
}
