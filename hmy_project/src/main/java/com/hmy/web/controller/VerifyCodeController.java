package com.hmy.web.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.plxcc.servicebase.common.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
public class VerifyCodeController
{
    @Autowired
    @Qualifier("getDefaultKaptcha")
    private DefaultKaptcha captchaProducer;

    @ApiOperation(value = "获得验证码图片 src='/web/verify/Img'",tags = {"verify"})
    @GetMapping("/web/verify/img")
    public void getImg(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) throws IOException
    {
        byte[] captchaOutputStream = null;
        ByteArrayOutputStream imgOutputStream = new ByteArrayOutputStream();
        try
        {
            String verifyCode = captchaProducer.createText();
            //将验证码存在session中
            httpServletRequest.getSession().setAttribute("verifyCode", verifyCode);
            BufferedImage challenge = captchaProducer.createImage(verifyCode);
            ImageIO.write(challenge, "jpg", imgOutputStream);
        } catch (IllegalArgumentException e)
        {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        captchaOutputStream = imgOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaOutputStream);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    @ApiOperation(value = "校验用户验证码",tags = {"verify"})
    @GetMapping("/web/verify/valid")
    public Result validate(@RequestParam String verifyCode, HttpSession session)
    {
        if (!verifyCode.isEmpty()){
            if (verifyCode.equals(session.getAttribute("verifyCode"))){
                return Result.success().setMsg("验证成功");
            }
        }
        //验证失败后清除session中保存的验证码 需前端刷新验证码
        session.removeAttribute("verifyCode");
        return Result.fail().setMsg("验证失败");
    }
}
