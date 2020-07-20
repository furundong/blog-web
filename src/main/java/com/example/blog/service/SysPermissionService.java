package com.example.blog.service;


import com.example.blog.dao.SysPermissionDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限表(SysPermission)表服务接口
 *
 * @author makejava
 * @since 2020-04-10 10:17:00
 */
@Service
public class SysPermissionService {

    @Resource
    SysPermissionDao sysPermissionDao;

    public List<String> selectListByPath(String requestUrl) {
        //如果这个url有带参数的，那么将它截取
        if (requestUrl.indexOf('?')>0) {
            requestUrl  = requestUrl.substring(0,requestUrl.indexOf('?'));
        }
        return sysPermissionDao.findByUrl(requestUrl);
    }
}
