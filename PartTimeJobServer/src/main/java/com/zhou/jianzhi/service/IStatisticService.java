package com.zhou.jianzhi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhou.jianzhi.common.AjaxResult;
import com.zhou.jianzhi.entity.dto.SchoolDTO;
import com.zhou.jianzhi.entity.po.School;

import java.util.List;
import java.util.Map;


/**
 * SchoolService接口
 *
 * @author au
 */
public interface IStatisticService extends IService<School> {

    public List<Map<String, Object>> selectStatistic();

    public List<Map<String, Object>> getGwtd();


}
