package cn.celess.j2ee.ex;

import cn.celess.j2ee.constant.UserConstant;
import cn.celess.j2ee.entity.CommonResponseData;
import cn.celess.j2ee.entity.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

/**
 * 2021/10/24
 * TODO:
 *
 * @author 禾几海
 */
@ControllerAdvice
public class ExHandler {

    public static final Logger logger = LoggerFactory.getLogger(ExHandler.class);

    @Autowired
    HttpServletRequest request;

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseData<Object> handle(Exception e) {
        //自定义错误
        if (e instanceof ResponseException) {
            ResponseException exception = (ResponseException) e;
            ResponseData<Object> data = exception.getResponse();
            logger.debug("返回了自定义的exception,[code={},msg={},result={}]", data.getCode(), e.getMessage(), data.getData());
            return data;
        }
        //请求路径不支持该方法
        if (e instanceof HttpRequestMethodNotSupportedException) {
            logger.debug("遇到请求路径与请求方法不匹配的请求，[msg={}，path:{},method:{}]", e.getMessage(), request.getRequestURL(), request.getMethod());
            return ResponseData.of(UserConstant.ERROR.getCode(), e.getMessage(), null);
        }
        //数据输入类型不匹配
        if (e instanceof MethodArgumentTypeMismatchException) {
            logger.debug("输入类型不匹配,[msg={}]", e.getMessage());
            return ResponseData.of(UserConstant.PARAMETERS_ERROR.getCode(), "数据输入有问题，请修改后再访问", null);
        }
        //数据验证失败
        if (e instanceof BindException) {
            logger.debug("数据验证失败,[msg={}]", e.getMessage());
            return ResponseData.of(UserConstant.PARAMETERS_ERROR.getCode(), "数据输入有问题，请修改", null);
        }
        //数据输入不完整
        if (e instanceof MissingServletRequestParameterException) {
            logger.debug("数据输入不完整,[msg={}]", e.getMessage());
            return ResponseData.of(UserConstant.PARAMETERS_ERROR.getCode(), "数据输入不完整,请检查", null);
        }

        e.printStackTrace();
        return ResponseData.of(UserConstant.ERROR.getCode(), "服务器出现未知错误", null);
    }


}
