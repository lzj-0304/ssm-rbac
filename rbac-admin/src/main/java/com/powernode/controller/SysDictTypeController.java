package com.powernode.controller;

import com.powernode.common.Page;
import com.powernode.common.Result;
import com.powernode.domain.SysDictType;
import com.powernode.enums.ResultEnums;
import com.powernode.service.SysDictTypeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *  字典类型模块管理
 */
@RestController
@RequestMapping("sysDictType")
public class SysDictTypeController extends BaseController{

    @Resource
    private SysDictTypeService sysDictTypeService;


    /**
     *
     * 分页查询字典类型数据
     * 前端传参
     *    {
     *        "pageNumber":1
     *        "pageSize":10,
     *        "params":{
     *            "dictName":"",  // 字典类型名称
     *            "dictType":""  // 字典类型
     *            "status":0|1    字典类型状态
     *        }
     *    }
     *
     *
     * @param page
     * @return
     */
    @PostMapping("/getByPage")
    public Result getSysDictTypeByPage(@RequestBody Page page){
        page = sysDictTypeService.getSysDictTypeByPage(page);
        return new Result(ResultEnums.SUCCESS.getMsg(),page);
    }


    /**
     * 添加字典类型
     * @param sysDictType
     * @return
     */
    @PostMapping("save")
    public Result save(@RequestBody SysDictType sysDictType){
        sysDictType.setCreateBy(getUserName());
        sysDictType.setCreateTime(new Date());
        // 控制字典名称不能重复
        /**
         * 根据字典名称到数据库查询字典记录
         *   存在  重复
         *   不存在 合法
         */

        sysDictTypeService.insertSelective(sysDictType);
        return new Result(ResultEnums.SUCCESS,"字典记录添加成功");
    }


    /**
     * 记录更新
     * @param sysDictType
     * @return
     */
    @PutMapping("update")
    public Result update(@RequestBody SysDictType sysDictType){
        sysDictType.setUpdateBy(getUserName());
        sysDictType.setUpdateTime(new Date());
        /**
         *   控制字典名称不能重复
         *   根据字典名称到数据库查询字典记录
         *       存在:
         *          如果为自己  合法
         *          如果是其他记录  不合法   sysDictType.getDictId.equals(数据库记录.dictId)=false
         *       不存在: 合法
         */
        sysDictTypeService.updateByPrimaryKey(sysDictType);
        return new Result(ResultEnums.SUCCESS,"记录更新成功");
    }


    /**
     * 删除记录
     * @param dictId
     * @return
     */
    @DeleteMapping("/delete/{dictId}")
    public Result delete(@PathVariable Long dictId){
        /**
         * 企业中删除操作 实际执行的是更新
         *   status 0--->1
         *   如果删除的为主表  字表下存在记录 不允许删除
         */
        sysDictTypeService.deleteByPrimaryKey(dictId);
        return new Result(ResultEnums.SUCCESS,"记录删除成功");
    }


    /**
     * 查询字典类型记录
     * @param dictId
     * @return
     */
    @GetMapping("/get/{dictId}")
    public Result getById(@PathVariable Long dictId){
        SysDictType sysDictType = sysDictTypeService.selectByPrimaryKey(dictId);
        return new Result(ResultEnums.SUCCESS.getCode(),"记录查询成功",sysDictType);
    }


    /**
     * 查询所有字典类型记录
     * @return
     */
    @GetMapping("getAllDictType")
    public Result getAllSysDictType(){
       List<SysDictType> sysDictTypes = sysDictTypeService.getAllDictType();
       return new Result(ResultEnums.SUCCESS.getCode(),"记录查询成功",sysDictTypes);
    }



    @GetMapping("refreshCache")
    public Result refreshCache(){
        /**
         *   引入缓存
         *     字典数据 变化频率较低  每次获取字典数据 没有必要到数据库来查询  可以使用缓存来缓存字典数据
         *        如果客户端需要某个字典类型数据 后端提供一个接口 接收字典类型值 根据字典类型值到缓存查找即可
         *   刷新缓存   实际上就是将数据库字典数据查出来放入缓存
         */
        sysDictTypeService.refreshCache();
        return new Result(ResultEnums.SUCCESS,"缓存同步成功");
    }



}
