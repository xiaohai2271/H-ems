package cn.celess.j2ee.entity.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
/**
 * zh_user
 *
 * @author
 */
@Table(name = "zh_user")
@Data
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;

    /**
     * 密码
     */
    @JsonIgnore
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
    private Date updateDatetime;

    /**
     * 创建日期
     */
    private Date createDatetime;

    private static final long serialVersionUID = 1L;
}