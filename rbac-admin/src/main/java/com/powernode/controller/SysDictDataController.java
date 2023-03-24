package com.powernode.controller;

import com.powernode.common.Page;
import com.powernode.common.Result;
import com.powernode.domain.SysDictData;
import com.powernode.enums.ResultEnums;
import com.powernode.service.SysDictDataService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: Mr.T
 * @Date: 2023/3/9
 */
@RestController
@RequestMapping("sysDictData")
public class SysDictDataController extends BaseController {

    @Resource
    private SysDictDataService sysDictDataService;


    /**
     * 分页查询字典数据列表
     *
     * @param page
     * @return
     */
    @PostMapping("/getByPage")
    public Result<Page<SysDictData>> getByPage(@RequestBody Page page) {
        page = sysDictDataService.getByPage(page);
        return new Result(ResultEnums.SUCCESS.getCode(), "记录查询成功", page);
    }


    /**
     * 添加字典数据
     *
     * @param sysDictData
     * @return
     */
    @PostMapping("save")
    public Result save(@RequestBody SysDictData sysDictData) {
        sysDictData.setCreateBy(getUserName());
        sysDictData.setCreateTime(new Date());
        sysDictDataService.insert(sysDictData);
        return new Result(ResultEnums.SUCCESS, "记录添加成功");
    }


    /**
     * 更新字典数据
     *
     * @param sysDictData
     * @return
     */
    @PutMapping("update")
    public Result update(@RequestBody SysDictData sysDictData) {
        sysDictData.setUpdateBy(getUserName());
        sysDictData.setUpdateTime(new Date());
        sysDictDataService.updateByPrimaryKey(sysDictData);
        return new Result(ResultEnums.SUCCESS, "记录更新成功");
    }


    /**
     * 根据字典主键查询字典记录
     *
     * @param dictCode
     * @return
     */
    @GetMapping("get/{dictCode}")
    public Result<SysDictData> getById(@PathVariable Long dictCode) {
        return new Result<SysDictData>(ResultEnums.SUCCESS.getCode(), "记录查询成功", sysDictDataService.selectByPrimaryKey(dictCode));
    }


    /**
     * 删除记录
     *
     * @param dictCode
     * @return
     */
    @DeleteMapping("/delete/{dictCode}")
    public Result delete(@PathVariable Long dictCode) {
        sysDictDataService.deleteByPrimaryKey(dictCode);
        return new Result(ResultEnums.SUCCESS, "记录删除成功");
    }


    /**
     * 根据字典类型查询字典数据
     * @param dictType
     * @return
     */
    @GetMapping("/getDictDataByDictType/{dictType}")
    public Result getDictDataByDictType(@PathVariable String dictType){
        List<SysDictData> sysDictDataList= sysDictDataService.getDictDataByDictType(dictType);
        return new Result(ResultEnums.SUCCESS.getCode(),"记录查询成功",sysDictDataList);

    }


}
