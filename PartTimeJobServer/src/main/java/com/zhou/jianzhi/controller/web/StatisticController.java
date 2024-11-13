package com.zhou.jianzhi.controller.web;

import com.zhou.jianzhi.common.AjaxResult;
import com.zhou.jianzhi.entity.dto.SchoolDTO;
import com.zhou.jianzhi.entity.po.School;
import com.zhou.jianzhi.service.ISchoolService;
import com.zhou.jianzhi.service.IStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
* StatisticController
*
* @author au
*/
@RestController
@RequestMapping("/statistic")
public class StatisticController {
    @Autowired
    private IStatisticService statisticService;

    @GetMapping("/selectStatistic")
    public AjaxResult selectStatistic() {
        List<Map<String, Object>> school = statisticService.selectStatistic();
       if (school==null){
          return AjaxResult.error("查不到");
       }
          return AjaxResult.success(school);
    }

    @GetMapping("/getGwtd")
    public AjaxResult getGwtd() {
        List<Map<String, Object>> school = statisticService.getGwtd();
       if (school==null){
          return AjaxResult.error("查不到");
       }
          return AjaxResult.success(school);
    }



}
