package cn.celess.j2ee.entity;

import cn.celess.j2ee.constant.UserConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 2021/10/22
 * TODO:
 *
 * @author 禾几海
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData<T> {
    private String code;
    private String msg;
    private Object data;

    public static <T> ResponseData<T> of(String code, String message, T data) {
        return new ResponseData<T>(code, message, data);
    }

    public static <T> ResponseData<T> of(T data) {
        return of(UserConstant.SUCCESS, data);
    }

    public static <T> ResponseData<T> of(UserConstant constant) {
        return of(constant, null);
    }

    public static <T> ResponseData<T> of(UserConstant constant, T data) {
        return of(constant.getCode(), constant.getMsg(), data);
    }
}
