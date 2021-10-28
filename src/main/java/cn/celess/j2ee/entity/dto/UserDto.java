package cn.celess.j2ee.entity.dto;

import cn.celess.j2ee.entity.db.User;

import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 2021/10/22
 * TODO:
 *
 * @author 禾几海
 */
@Data
public class UserDto implements BaseDto<UserDto, User> {
    private Integer id;

    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱验证状态
     */
    private Boolean emailStatus;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户的描述
     */
    private String description;

    /**
     * 最近的登录时间
     */
    private Date recentlyLandedDtStart;
    private Date recentlyLandedDtEnd;
    private Date recentlyLandedDatetime;

    /**
     * 展示的昵称
     */
    private String displayName;

    /**
     * 账户状态
     */
    private Integer status;

    /**
     * 更新日期
     */
    private Date updateDtStart;
    private Date updateDtEnd;
    private Date updateDatetime;

    /**
     * 创建日期
     */
    private Date createDtStart;
    private Date createDtEnd;
    private Date createDatetime;


    @Override
    public User toEntity() {
        User user = new User();
        BeanUtils.copyProperties(this, user);
        return user;
    }


    public static UserDto of(User user) {
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }
}
