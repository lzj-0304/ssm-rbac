package com.powernode.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.powernode.common.Result;
import com.powernode.constants.CoreConstant;
import com.powernode.enums.ResultEnums;
import com.powernode.utils.ServletUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Author: Mr.T
 * @Date: 2023/3/7
 */
@RestController
public class CaptchaController {


    /**
     * 输出验证码图片到浏览器
     * @throws IOException
     */
    /*@GetMapping("/captcha/getCaptcha")
    public void getCaptcha(HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(160,50,4,30);
        System.out.println(captcha.getImageBase64Data());
        captcha.write(outputStream);
        outputStream.close();
    }*/



    @GetMapping(CoreConstant.CAPTCHA_URL)
    public Result getCaptcha(){
        HttpSession session = ServletUtils.getSession();
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(160,50,1,30);
        System.out.println("验证码--->"+captcha.getCode());
        // 将CircleCaptcha对象存入会话 方便后续对验证码进行校验
        session.setAttribute(CoreConstant.CAPTCHA_KEY,captcha);
        return new Result(ResultEnums.SUCCESS.getMsg(),captcha.getImageBase64Data());
    }

}
