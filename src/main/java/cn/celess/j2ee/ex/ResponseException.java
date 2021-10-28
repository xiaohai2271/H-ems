package cn.celess.j2ee.ex;

import cn.celess.j2ee.entity.ResponseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 2021/10/22
 * TODO:
 *
 * @author 禾几海
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseException extends RuntimeException{
    private ResponseData<Object> response;
}
