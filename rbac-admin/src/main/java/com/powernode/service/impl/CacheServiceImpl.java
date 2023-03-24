package com.powernode.service.impl;

import com.powernode.domain.SysDictData;
import com.powernode.domain.SysDictType;
import com.powernode.enums.StateEnums;
import com.powernode.mapper.SysDictDataMapper;
import com.powernode.mapper.SysDictTypeMapper;
import com.powernode.service.CacheService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Mr.T
 * @Date: 2023/3/9
 */
@Service
public class CacheServiceImpl  implements CacheService {

    @Resource
    private SysDictTypeMapper sysDictTypeMapper;

    @Resource
    private SysDictDataMapper sysDictDataMapper;

    private static Map<String,List<SysDictData>> caches = new HashMap<>();


    @Override
    public void loadDictTypeData() {
        /**
         *   引入缓存
         *     字典数据 变化频率较低  每次获取字典数据 没有必要到数据库来查询  可以使用缓存来缓存字典数据
         *        如果客户端需要某个字典类型数据 后端提供一个接口 接收字典类型值 根据字典类型值到缓存查找即可
         *   刷新缓存   实际上就是将数据库字典数据查出来放入缓存
         *        将每个字典类型的字典记录存入一个特殊的数据结构中  后续使用时从这个数据结构取即可
         *           sys_user_sex---->List<SysDictData>
         *           sys_notice_type--->List<SysDictData>
         *           sys_login_status--->List<SysDictData>
         *          这个数据结构选着 Map<String,List<SysDictData>>
         *
         */
        List<SysDictType> sysDictTypeList= sysDictTypeMapper.getAllDictType(StateEnums.ENABLED.getCode());
        for (SysDictType sysDictType : sysDictTypeList) {
           List<SysDictData> sysDictDataList=  sysDictDataMapper.getDictDataByDictType(sysDictType.getDictType());
           caches.put(sysDictType.getDictType(),sysDictDataList);
        }
    }

    @Override
    public List<SysDictData> getDictDataByDictType(String dictType) {
        /**
         * 根据字典类型获取字典数据
         *   如果caches 存在 直接获取即可
         *   如果caches 不存在(添加 修改 删除 在没有执行缓存同步时 可能caches不存在对应记录)
         *  查找顺序
         *     先从缓存查
         *        缓存存在 直接返回
         *        缓存不存在
         *     到数据库查找
         *         找到对应记录后 返回记录  同时将查到的记录放入caches
         */
        List<SysDictData> result = caches.get(dictType);
        if(!CollectionUtils.isEmpty(result)){
            return result;
        }
        // caches 缓存不存在 从数据库查找
        result = sysDictDataMapper.getDictDataByDictType(dictType);
        setCaches(dictType,result);
        return result;
    }

    private void setCaches(String dictType,List<SysDictData> result) {
        if(!CollectionUtils.isEmpty(result)){
            // 将数据放入缓存
            caches.put(dictType,result);
        }
    }
}
