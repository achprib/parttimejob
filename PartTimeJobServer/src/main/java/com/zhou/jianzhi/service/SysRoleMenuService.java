package com.zhou.jianzhi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhou.jianzhi.entity.po.SysRoleMenu;

/**
 * @author shihh
 * @version 1.0
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {
    Boolean addRoleMenu(SysRoleMenu sysRoleMenu);
}
