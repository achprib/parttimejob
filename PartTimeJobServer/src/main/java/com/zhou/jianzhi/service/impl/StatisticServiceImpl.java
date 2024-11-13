package com.zhou.jianzhi.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhou.jianzhi.common.AjaxResult;
import com.zhou.jianzhi.entity.dto.SchoolDTO;
import com.zhou.jianzhi.entity.po.School;
import com.zhou.jianzhi.mapper.SchoolMapper;
import com.zhou.jianzhi.mapper.StatisticMapper;
import com.zhou.jianzhi.service.ISchoolService;
import com.zhou.jianzhi.service.IStatisticService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * schoolService业务层处理
 *
 * @author au
 */
@Service
@Transactional
public class StatisticServiceImpl extends ServiceImpl<StatisticMapper, School> implements IStatisticService {

    @Resource
    private StatisticMapper statisticMapper;

    @Override
    public List<Map<String, Object>> selectStatistic() {
        return statisticMapper.selectStatistic();
    }

    @Override
    public List<Map<String, Object>> getGwtd() {
        return statisticMapper.getGwtd();
    }

}








