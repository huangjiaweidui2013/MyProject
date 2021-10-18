package com.example.springbootsecurity.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.utils
 * @className: KaptchaCodeUtil
 * @author: HuangLang
 * @description: 验证码比对
 * @date: 2021-07-28 上午 11:42
 */
public class KaptchaCodeUtil {

    /**
     * 将获取到的前端参数转为string类型
     *
     * @param request
     * @param key
     * @return
     */
    public static String getString(HttpServletRequest request, String key) {
        String result = request.getParameter(key);
        if (result != null) {
            result = result.trim();
        }
        if ("".equals(result)) {
            result = null;
        }
        return result;
    }

    /**
     * 验证码校验
     *
     * @param request
     * @return
     */
    public static boolean checkVerifyCode(HttpServletRequest request) {
        //获取生成的验证码
        String verifyCodeExpected =
                (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        //获取用户输入的验证码
        String verifyCodeActual = KaptchaCodeUtil.getString(request, "verifyCodeActual");
        if (verifyCodeActual == null || !verifyCodeActual.equals(verifyCodeExpected)) {
            return false;
        }
        return true;
    }

}
