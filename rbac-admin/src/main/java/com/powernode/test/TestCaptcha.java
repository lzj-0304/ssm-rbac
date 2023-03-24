package com.powernode.test;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.ICaptcha;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.crypto.digest.DigestUtil;

/**
 * @Author: Mr.T
 * @Date: 2023/3/7
 */
public class TestCaptcha {


    public static void main(String[] args) {
        /*LineCaptcha captcha = CaptchaUtil.createLineCaptcha(100,50);
        captcha.write("d:\\test.png");*/

      /*  CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200,100,4,50);
        captcha.write("d:\\abc.png");
        System.out.println(captcha.getCode());
        System.out.println(captcha.verify("sgku"));*/
        System.out.println(DigestUtil.md5Hex("666"));

    }


}
