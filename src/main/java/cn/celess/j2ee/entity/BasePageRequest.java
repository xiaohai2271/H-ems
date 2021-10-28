package cn.celess.j2ee.entity;

import lombok.Data;

/**
 * 2021/10/22
 * TODO:
 *
 * @author 禾几海
 */
@Data
public class BasePageRequest<T> {
    private int page = 10;
    private int size = 1;
    private T data;
}
