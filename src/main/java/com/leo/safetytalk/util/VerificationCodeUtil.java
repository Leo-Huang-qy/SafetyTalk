package com.leo.safetytalk.util;

import java.util.Random;

public class VerificationCodeUtil {

    /**
     * 生成指定位数的随机数字验证码
     *
     * @param length 验证码长度
     * @return 生成的验证码
     */
    public static String generateDigitCode(int length) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}