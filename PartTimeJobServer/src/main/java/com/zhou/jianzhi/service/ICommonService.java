package com.zhou.jianzhi.service;

import com.zhou.jianzhi.entity.vo.LoginUserInfoVO;


/**
 *
 *
 * @author au
 */
public interface ICommonService {

    /**
    * 根据用户id查询该登录用户所有信息
    *
    * @param id
    * @return
    */
    public LoginUserInfoVO selectLoginUserInfoByLoginUserId(Long id) throws Exception;


}
