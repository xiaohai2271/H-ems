package cn.celess.j2ee.controller;

import cn.celess.j2ee.constant.UserConstant;
import cn.celess.j2ee.entity.BasePageRequest;
import cn.celess.j2ee.entity.ResponseData;
import cn.celess.j2ee.entity.db.User;
import cn.celess.j2ee.entity.dto.UserDto;
import cn.celess.j2ee.ex.ResponseException;
import cn.celess.j2ee.service.UserService;
import cn.celess.j2ee.util.UserContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

/**
 * 2021/10/22
 * TODO:
 *
 * @author 禾几海
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public ResponseData<UserDto> login(@RequestBody UserDto userDto) {
        return ResponseData.of(userService.login(userDto));
    }


    @PostMapping("/registration")
    public ResponseData<UserDto> registration(@RequestBody UserDto userDto) {
        return ResponseData.of(userService.registration(userDto));
    }

    /**
     * 新增员工信息
     *
     * @param userDto 员工信息
     * @return 员工信息
     */
    @PostMapping("/save")
    public ResponseData<UserDto> save(@RequestBody UserDto userDto) {
        if (UserContext.get() == null) {
            throw new ResponseException(ResponseData.of(UserConstant.NOT_LOGIN));
        }
        return ResponseData.of(userService.insert(userDto));
    }

    /**
     * 删除员工信息
     *
     * @param ids 待删除员工信息id
     * @return true/false
     */
    @PostMapping("/delete")
    public ResponseData<Boolean> delete(@RequestBody List<Integer> ids) {
        if (UserContext.get() == null) {
            throw new ResponseException(ResponseData.of(UserConstant.NOT_LOGIN));
        }
        return ResponseData.of(userService.delete(ids));
    }

    /**
     * 更新员工信息
     *
     * @param userDto 员工信息
     * @return 员工信息
     */
    @PostMapping("/update")
    public ResponseData<UserDto> update(@RequestBody UserDto userDto) {
        if (UserContext.get() == null) {
            throw new ResponseException(ResponseData.of(UserConstant.NOT_LOGIN));
        }
        return ResponseData.of(userService.update(userDto));
    }

    /**
     * 分页获取员工信息
     *
     * @param maps 查询条件
     * @return 员工信息
     */
    @PostMapping("/users")
    public ResponseData<Page<User>> users(@RequestBody Map<Object, Object> maps) {
        if (UserContext.get() == null) {
            throw new ResponseException(ResponseData.of(UserConstant.NOT_LOGIN));
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        UserDto userDto = objectMapper.convertValue(maps, UserDto.class);
        BasePageRequest<UserDto> pageRequest = objectMapper.convertValue(maps, BasePageRequest.class);
        pageRequest.setData(userDto);
        return ResponseData.of(userService.query(pageRequest));
    }


}
