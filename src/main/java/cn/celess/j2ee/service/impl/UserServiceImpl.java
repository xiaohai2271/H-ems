package cn.celess.j2ee.service.impl;

import cn.celess.j2ee.constant.CommonConstant;
import cn.celess.j2ee.constant.UserConstant;
import cn.celess.j2ee.dao.UserDao;
import cn.celess.j2ee.entity.BasePageRequest;
import cn.celess.j2ee.entity.ResponseData;
import cn.celess.j2ee.entity.db.User;
import cn.celess.j2ee.entity.dto.UserDto;
import cn.celess.j2ee.ex.ResponseException;
import cn.celess.j2ee.service.UserService;
import cn.celess.j2ee.util.AesEncryptUtils;
import cn.celess.j2ee.util.UserContext;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 2021/10/22
 * TODO:
 *
 * @author 禾几海
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;
    @Resource
    HttpServletRequest request;

    @Override
    public UserDto login(UserDto userDto) {
        User queryUser = new User();
        queryUser.setEmail(userDto.getEmail());
        queryUser.setPassword(userDto.getPassword());
        // 处理用户密码
        dealPassword(queryUser);

        User user = userDao.findOne(Example.of(queryUser)).orElseThrow(() -> {
            throw new ResponseException(ResponseData.of(UserConstant.LOGIN_FAILED));
        });
        UserContext.set(user);

        request.getSession(true).setAttribute("user", user);

        user.setPassword(null);
        return UserDto.of(user);
    }

    @Override
    public UserDto registration(UserDto userDto) {
        User queryUser = new User();
        queryUser.setEmail(userDto.getEmail());


        User user = userDao.findOne(Example.of(queryUser)).orElseGet(() -> {
            queryUser.setStatus(1);
            queryUser.setEmailStatus(false);
            queryUser.setDescription(userDto.getDescription());
            queryUser.setDisplayName(userDto.getDisplayName());

            queryUser.setPassword(userDto.getPassword());

            // 处理用户密码
            dealPassword(queryUser);

            userDao.save(queryUser);
            // todo: 发送邮件
            return null;
        });
        if (user != null) {
            throw new ResponseException(ResponseData.of(UserConstant.USER_EXIST));
        }
        queryUser.setRecentlyLandedDatetime(new Date());
        userDao.save(queryUser);

        queryUser.setPassword(null);
        return UserDto.of(queryUser);
    }

    /**
     * 新增员工信息
     *
     * @param userDto 员工信息
     * @return 员工信息
     */
    @Override
    public UserDto insert(UserDto userDto) {
        User queryUser = new User();
        queryUser.setEmail(userDto.getEmail());
        User user = userDao.findOne(Example.of(queryUser)).orElseGet(() -> {
            User entity = userDto.toEntity();
            if (entity.getStatus() == null) {
                entity.setStatus(1);
            }
            dealPassword(entity);
            entity.setCreateDatetime(new Date());
            userDao.save(entity);
            return null;
        });
        if (user != null) {
            throw new ResponseException(ResponseData.of(UserConstant.USER_EXIST));
        }
        queryUser.setPassword(null);
        return UserDto.of(queryUser);
    }

    /**
     * 删除员工信息
     *
     * @param ids 员工id
     * @return 删除员工信息
     */
    @Override
    public Boolean delete(List<Integer> ids) {
        userDao.deleteAllByIdInBatch(ids);
        return true;
    }

    /**
     * 更新员工信息
     *
     * @param userDto 员工信息
     * @return 员工信息
     */
    @Override
    public UserDto update(UserDto userDto) {
        User user = userDao.findById(userDto.getId()).orElseThrow(() -> {
            throw new ResponseException(ResponseData.of(UserConstant.USER_NOT_EXIST));
        });
        if (userDto.getEmailStatus() != null) {
            user.setEmailStatus(userDto.getEmailStatus());
        }
        if (Strings.isNotEmpty(userDto.getAvatar())) {
            user.setAvatar(userDto.getAvatar());
        }
        if (Strings.isNotEmpty(userDto.getPassword())) {
            user.setPassword(userDto.getPassword());
        }
        if (Strings.isNotEmpty(userDto.getDescription())) {
            user.setDescription(userDto.getDescription());
        }
        if (Strings.isNotEmpty(userDto.getDisplayName())) {
            user.setDisplayName(userDto.getDisplayName());
        }
        if (userDto.getStatus() != null) {
            user.setStatus(userDto.getStatus());
        }
        user.setUpdateDatetime(new Date());
        userDao.save(user);
        return UserDto.of(user);
    }

    /**
     * 分页查询员工信息
     *
     * @param pageRequest 员工信息request
     * @return 员工信息
     */
    @Override
    public Page<User> query(BasePageRequest<UserDto> pageRequest) {
        User user = pageRequest.getData().toEntity();
        if (user.getEmailStatus() == null) {
            user.setEmailStatus(null);
        }
        if (Strings.isBlank(user.getAvatar())) {
            user.setAvatar(null);
        }
        if (Strings.isBlank(user.getPassword())) {
            user.setPassword(null);
        }
        if (Strings.isBlank(user.getDescription())) {
            user.setDescription(null);
        }
        if (Strings.isBlank(user.getDisplayName())) {
            user.setDisplayName(null);
        }
        if (Strings.isBlank(user.getEmail())) {
            user.setEmail(null);
        }
        if (user.getStatus() == null) {
            user.setStatus(null);
        }


        ExampleMatcher matching = ExampleMatcher.matching();
        matching = matching.withMatcher("description", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("display_name", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<User> example = Example.of(user, matching);
        Pageable pageable = PageRequest.of(pageRequest.getPage() - 1, pageRequest.getSize(), Sort.by(Sort.Direction.ASC, "id"));
        Page<User> userPage = userDao.findAll(example, pageable);
        userPage.forEach(u -> u.setPassword(null));
        return userPage;
    }

    private User dealPassword(User user) {
        if (user.getEmail() == null || user.getPassword() == null) {
            throw new ResponseException(ResponseData.of(UserConstant.EMAIL_PWD_LOST));
        }
        // TODO: 邮箱校验
        // 字符串处理
        user.setEmail(user.getEmail().trim().toLowerCase(Locale.ROOT));
        user.setPassword(user.getPassword().trim());
        // 得到原始密码
        String originPassword = AesEncryptUtils.decrypt(user.getPassword(), user.getEmail());
        // MD5(邮箱+原始密码+应用盐)
        String encrypt = DigestUtils.md5DigestAsHex((user.getEmail() + originPassword + CommonConstant.APPLICATION_SALT).getBytes(StandardCharsets.UTF_8));
        user.setPassword(encrypt);
        return user;
    }

    private User dealPassword(UserDto userDto) {
        return dealPassword(userDto.toEntity());
    }
}
