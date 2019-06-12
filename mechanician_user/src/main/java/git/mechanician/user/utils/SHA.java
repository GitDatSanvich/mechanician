package git.mechanician.user.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @ClassName SHA
 * @Author GitDatSanvich
 * @Date 2019/6/9 20:08
 * 我是一个用于密码加密级格式检验的工具类0.0
 **/

public class SHA {
    private static final String KEY_SHA = "SHA";

    public static String getResult(String inputStr) {
        BigInteger sha = null;
        byte[] inputData = inputStr.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(KEY_SHA);
            messageDigest.update(inputData);
            sha = new BigInteger(messageDigest.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sha.toString();
    }

    /**
     * 密码正则校验
     */
    public static String egPasswd(String pw) {

        if (!pw.matches("^.{6,16}$")) {
            return "密码长度限制在6-16位之间！";
        }

        if (pw.matches("^.*[\\s]+.*$")) {
            return "密码不能包含空格、制表符、换页符等空白字符！";
        }

//	    !str.matches("^.*(.)\\1{2,}+.*$")
        if (pw.matches("^(.)\\1+$")) {
            return "密码不能为完全相同的字符！";
        }

        boolean isContin = isContinuous(pw);
        if (isContin) {
            return "密码不能为连续的字符！";
        }
        if (!pw.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]+$")) {
            return "密码必须包含字母和数字";
        }

        return "";
    }

    /**
     * 密码连续性校验
     */
    public static boolean isContinuous(String pw) {
        char[] c = pw.toCharArray();
        boolean flag = true;
        for (int i = 1; i < pw.length(); i++) {
            if (Math.abs(c[i] - c[i - 1]) == 1) {
                continue;
            } else {
                flag = false;
                break;
            }
        }
        return flag;
    }

}
