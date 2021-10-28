package cn.celess.j2ee.constant;

import lombok.Getter;

import javax.management.loading.MLetContent;

/**
 * 2021/10/22
 * TODO:
 *
 * @author 禾几海
 */
@Getter
public enum UserConstant {

    SUCCESS("SU00", "success"),
    FAILURE("FU00", "success"),
    ERROR("EU00", "success"),

    LOGIN_FAILED("EU11", "用户名或者密码不正确"),
    NOT_LOGIN("EU12", "还未登录"),
    USER_EXIST("EU21", "账户已存在"),
    USER_NOT_EXIST("EU31", "账户不存在"),
    EMAIL_PWD_LOST("EU81", "邮箱或密码缺失"),
    PARAMETERS_ERROR("EU99", "参数错误");


    /**
     * （S|F|E） + (模块)  + code
     * 1 + 1 + 2;
     */
    private final String code;
    private final String msg;

    UserConstant(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
