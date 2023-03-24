package com.powernode.controller;

import com.powernode.common.Result;
import com.powernode.domain.SysDept;
import com.powernode.enums.ResultEnums;
import com.powernode.enums.StateEnums;
import com.powernode.service.SysDeptService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: Mr.T
 * @Date: 2023/3/10
 */
@RestController
@RequestMapping("sysDept")
public class SysDeptController extends BaseController{


    @Resource
    private SysDeptService sysDeptService;


    @GetMapping("listDept")
    public Result getAllSysDeptList(SysDept sysDept){
      List<SysDept> sysDeptList =   sysDeptService.listDept(sysDept);
      return new Result(ResultEnums.SUCCESS.getCode(),"记录查询成功",sysDeptList);
    }


    @PostMapping("save")
    public Result save(@RequestBody SysDept sysDept){
        sysDept.setCreateBy(getUserName());
        sysDept.setCreateTime(new Date());
        sysDept.setDelFlag(StateEnums.NOT_DELETED.getCode());
        sysDeptService.insertSelective(sysDept);
        return new Result(ResultEnums.SUCCESS,"记录添加成功");
    }

    @PutMapping("update")
    public Result update(@RequestBody SysDept sysDept){
        sysDept.setUpdateBy(getUserName());
        sysDept.setUpdateTime(new Date());
        sysDeptService.updateByPrimaryKey(sysDept);
        return new Result(ResultEnums.SUCCESS.getCode(),"记录更新成功");
    }


    @DeleteMapping("/delete/{deptId}")
    public Result delete(@PathVariable Long deptId){
        /**
         * 如果存在子部门  不允许删除
         */
        Long count = sysDeptService.countChildrenByParentId(deptId);
        if(count>0){
            return new Result(ResultEnums.ERROR.getCode(),"存在子记录，不允许删除!");
        }
        // 更新部门删除字段 0--》1
        SysDept sysDept = sysDeptService.selectByPrimaryKey(deptId);
        sysDept.setDelFlag(StateEnums.DELETED.getCode());
        sysDeptService.updateByPrimaryKey(sysDept);
        //sysDeptService.deleteByPrimaryKey(deptId);
        return new Result(ResultEnums.SUCCESS.getCode(),"记录删除成功");
    }


    @GetMapping("get/{deptId}")
    public Result getById(@PathVariable Long deptId){
        SysDept sysDept = sysDeptService.selectByPrimaryKey(deptId);
        return new Result(ResultEnums.SUCCESS.getCode(), "记录查询成功", sysDept);
    }




}
