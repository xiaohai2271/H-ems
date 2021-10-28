package cn.celess.j2ee.util;

import cn.celess.j2ee.entity.db.User;

/**
 * 2021/10/22
 * TODO:
 *
 * @author 禾几海
 */
public class UserContext {
    public static final ThreadLocal<User> userLocal = new ThreadLocal<>();

    public static User set(User user) {
        if (user == null) {
            throw new IllegalArgumentException("员工信息不可为空");
        }
        User old = userLocal.get();
        userLocal.set(user);
        return old;
    }

    public static User get() {
        return userLocal.get();
    }

    public static void clear() {
        userLocal.set(null);
    }
}
