package com.example.springbootsecurity.exception;

import com.example.springbootsecurity.result.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: huang lang
 * @description: 全局异常捕获
 * @date: 2022/1/17 16:06
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {
    /**
     * 方法参数校验异常 Validate
     * 处理请求参数格式错误 @RequestParam上validate失败后抛出的异常是ConstraintViolationException
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public AjaxResult handleValidationException(HttpServletRequest request, ConstraintViolationException e) {
        log.error("异常:" + request.getRequestURI(), e);
        String collect = e.getConstraintViolations().stream().filter(Objects::nonNull)
                .map(cv -> cv.getPropertyPath() + ": " + cv.getMessage())
                .collect(Collectors.joining(", "));
        log.info("请求参数异常", collect);
        return AjaxResult.error(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    /**
     * Bean 校验异常 Validate
     * 处理请求参数格式错误 @RequestBody上validate失败后抛出的异常是MethodArgumentNotValidException异常
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public AjaxResult methodArgumentValidationHandler(HttpServletRequest request, MethodArgumentNotValidException e) {
        log.info("异常:" + request.getRequestURI(), e);
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        String collect = allErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(";"));
        return AjaxResult.error(collect, HttpStatus.BAD_REQUEST.value());
    }

    /**
     * 绑定异常
     * 处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public AjaxResult bindException(HttpServletRequest request, BindException e) {
        log.error("异常:" + request.getRequestURI(), e);
        Map<String, Object> map = new HashMap<>();
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        allErrors.stream().filter(Objects::nonNull).forEach(objectError -> {
            map.put("请求路径：" + request.getRequestURI() + "--请求参数：" +
                            (((FieldError) objectError).getField().toString()),
                    objectError.getDefaultMessage());
        });
        return AjaxResult.error(HttpStatus.BAD_REQUEST.value(), "请求参数绑定失败", map);
    }

    /**
     * 访问接口参数不全  @RequestParam注解
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public AjaxResult missingServletRequestParameterException(HttpServletRequest request,
                                                              MissingServletRequestParameterException e) {
        //错误信息
        String message = e.getMessage();
        //缺少的请求参数名称
        String parameterName = e.getParameterName();
        //缺少的请求参数类型
        String parameterType = e.getParameterType();
        log.error("异常:" + request.getRequestURI(), e);
        return AjaxResult.error(HttpStatus.BAD_REQUEST.value(), "参数不全，缺少类型为" +
                parameterType + "的" + parameterName + "请求参数，" + "该请求路径：" + request.getRequestURI() +
                "下的请求参数不全：" + message);
    }

    /**
     * HttpRequestMethodNotSupportedException 请求方式不支持异常
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public AjaxResult httpRequestMethodNotSupportedException(HttpServletRequest request,
                                                             HttpRequestMethodNotSupportedException e) {
        String method = e.getMethod();
        String[] supportedMethods = e.getSupportedMethods();
        log.error("异常:" + request.getRequestURI(), e);
        return AjaxResult.error(HttpStatus.BAD_REQUEST.value(), "请求方式不正确,只支持" + Arrays.toString(supportedMethods)
                + "请求，不支持" + method + "请求");
    }

    /**
     * 其他异常
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public AjaxResult otherException(HttpServletRequest request, Exception e) {
        log.error("异常:" + request.getRequestURI(), e);
        return AjaxResult.error(HttpStatus.BAD_REQUEST.value(), getExceptionDetail(e));
    }


    /**
     * 异常详情
     *
     * @param e
     * @return
     */
    private String getExceptionDetail(Exception e) {
        StringBuilder stringBuffer = new StringBuilder(e.toString() + "\n");
        StackTraceElement[] messages = e.getStackTrace();
        Arrays.stream(messages).filter(Objects::nonNull).forEach(stackTraceElement -> {
            stringBuffer.append(stackTraceElement.toString()).append("\n");
        });
        return stringBuffer.toString();
    }


}

/**
 * 首先说一下两个注解的区别:
 * 　　1.两者的所属的包是不同的
 * 　　　　@Valid属于javax.validation包下,是jdk给提供的
 * 　　　　@Validated是org.springframework.validation.annotation包下的,是spring提供的
 * 　　2.@Validated要比@Valid更加强大
 * 　　　　@Validated在@Valid之上提供了分组功能和验证排序功能
 * <p>
 * 两者在使用 @RequestBody 参数注解的情况下,两者抛出的都是 MethodArgumentNotValidException 异常。
 * 但是如果不在 controller 接口中加 @RequestBody 注解,两者抛出的则是 BindException 异常。
 *
 * @Validated：可以用在类型、方法和方法参数上。但是不能用在成员属性（字段）上
 * @Valid：可以用在方法、构造函数、方法参数和成员属性（字段）上 两者是否能用于成员属性（字段）上直接影响能否提供嵌套验证的功能。
 * 由于@Validated不能用在成员属性（字段）上，但是@Valid能加在成员属性（字段）上，而且@Valid类注解上也说明了它支持嵌套验证功能，那么我们能够推断出：@Valid加在方法参数时并不能够自动进行嵌套验证，而是用在需要嵌套验证类的相应字段上，来配合方法参数上@Validated或@Valid来进行嵌套验证。
 * ————————————————
 * 版权声明：本文为CSDN博主「花郎徒结」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/qq_27680317/article/details/79970590
 */