package cn.celess.j2ee.service;

import cn.celess.j2ee.entity.BasePageRequest;
import cn.celess.j2ee.entity.db.User;
import cn.celess.j2ee.entity.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 2021/10/22:
 * TODO:
 *
 * @author 禾几海
 */
@Service
public interface UserService {
    UserDto login(UserDto userDto);

    UserDto registration(UserDto userDto);

    UserDto insert(UserDto userDto);

    Boolean delete(List<Integer> ids);

    UserDto update(UserDto userDto);

    Page<User> query(BasePageRequest<UserDto> pageRequest);
}
