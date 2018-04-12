package com.sun.demo.service;

import com.sun.demo.mybatis.entity.Org;
import com.sun.demo.mybatis.mapper.OrgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther zhsun5@iflytek.com
 * @date 2018/1/8
 */
@Service
@CacheConfig(cacheNames = "orgService")
public class OrgService {
    @Autowired
    private OrgMapper orgMapper;

    @Cacheable(value="orgList",key="1")//在redis中开启key为orgList开头的存储空间
    public List queryOrg(){
        System.out.println("打印语句则没有走缓存");
        return orgMapper.queryOrg();
    }

    @CacheEvict(value="orgList",allEntries=true)//执行此方法的时候删除缓存
    public int insert(Org org){
        return orgMapper.insert(org);
    }
}
