package com.sun.demo.service;

import com.sun.demo.mybatis.entity.Org;
import com.sun.demo.mybatis.mapper.OrgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther zhsun5@iflytek.com
 * @date 2018/1/8
 */
@Service
public class OrgService {
    @Autowired
    private OrgMapper orgMapper;

    List<Org> queryOrg(){
        return orgMapper.queryOrg();
    }
}
