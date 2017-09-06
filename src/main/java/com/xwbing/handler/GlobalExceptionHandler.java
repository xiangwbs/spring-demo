package com.xwbing.handler;

import com.alibaba.fastjson.JSONObject;
import com.xwbing.exception.BusinessException;
import com.xwbing.exception.PayException;
import com.xwbing.util.JSONObjResult;
import com.xwbing.util.RestMessage;
import com.xwbing.util.captcah.CaptchaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * 说明: GlobalExceptionHandler<br/>
 * 创建日期: 2017年3月21日 下午2:53:35 <br/>
 * 作者: xwb
 */
// 作用在所有注解了@RequestMapping的控制器的方法上
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理自定义业务异常
     *
     * @param request
     * @param ex
     * @return
     */
    // 拦截处理控制器里对应的异常。
    @ExceptionHandler(value = BusinessException.class)
    // 返回给页面200状态码
    @ResponseStatus(value = HttpStatus.OK)
    public JSONObject handlerBusinessException(HttpServletRequest request, Exception ex) {
        logger.error(ex.getMessage());
        RestMessage result = new RestMessage();
//        result.setSuccess(false);
        result.setMsg(ex.getMessage());
        return JSONObjResult.toJSONObj(result);
    }

    /**
     * 处理支付异常
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(value = PayException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public JSONObject handlerPayException(HttpServletRequest request, Exception ex) {
        logger.error(ex.getMessage());
        RestMessage result = new RestMessage();
//        result.setSuccess(false);
        result.setMsg(ex.getMessage());
        return JSONObjResult.toJSONObj(result);
    }

    /**
     * 处理验证码异常
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(value = CaptchaException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public JSONObject handlerCaptchaException(HttpServletRequest request, Exception ex) {
        logger.error(ex.getMessage());
        RestMessage result = new RestMessage();
//        result.setSuccess(false);
        result.setMsg(ex.getMessage());
        return JSONObjResult.toJSONObj(result);
    }

    /**
     * 处理Exception
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public JSONObject handlerException(HttpServletRequest request, Exception ex) {
        logger.error(ex.getMessage());
        return JSONObjResult.toJSONObj("系统异常,请联系管理员");
    }
}
