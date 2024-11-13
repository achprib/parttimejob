package com.zhou.jianzhi.entity.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author au
 */
@Data
public class MonthRangeDTO {
    List<Date> monthArray;
    String remark;
}
