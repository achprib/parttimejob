package com.zhou.jianzhi.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author au
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class JobLabelDTO extends PageParam {

    private static final long serialVersionUID = 1L;


    private Integer id;

    /**
     * 标签名
     */
    private String name;


}
