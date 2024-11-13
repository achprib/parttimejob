package com.zhou.jianzhi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhou.jianzhi.entity.po.School;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author au
 */
public interface StatisticMapper extends BaseMapper<School> {

    @MapKey("")
    public List<Map<String, Object>> selectStatistic();

    @MapKey("")
    public List<Map<String, Object>> getGwtd();

}
