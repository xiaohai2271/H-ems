package cn.celess.j2ee.entity.dto;

/**
 * 2021/10/22
 * TODO:
 *
 * @author 禾几海
 */
public interface BaseDto<DTO, ENTITY> {

    ENTITY toEntity();
}
