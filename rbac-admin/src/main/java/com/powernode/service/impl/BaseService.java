package com.powernode.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

/**
 * @Author: Mr.T
 * @Date: 2023/3/9
 */
@Service
public class BaseService {

    public Page startPage(Integer pageNumber, Integer pageSize){
        return PageHelper.startPage(pageNumber, pageSize);
    }
}
