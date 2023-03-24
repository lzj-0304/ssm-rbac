<template>
  <div class="app-container">
    <el-descriptions class="margin-top" :column="3"  border>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-warning-outline"></i>
          日志主键
        </template>
        {{ activeRow.operId }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-s-help"></i>
          模块标题
        </template>
        {{ activeRow.title }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-more"></i>
          业务类型
        </template>
        <dict-tag :value="activeRow.businessType" :data-list="operTypeOptions"></dict-tag>
      </el-descriptions-item>

      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-s-flag"></i>
          请求方式
        </template>
        <el-tag size="small">
          {{activeRow.requestMethod}}
        </el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          操作人员
        </template>
        <el-tag size="small">
          {{activeRow.operName}}
        </el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-s-order"></i>
          请求URL
        </template>
        <el-tag size="small">
          {{activeRow.operUrl}}
        </el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-s-data"></i>
          主机地址
        </template>
        {{activeRow.operIp}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-position"></i>
          操作地点
        </template>
        {{activeRow.operLocation}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-chat-dot-square"></i>
          请求参数
        </template>
        {{activeRow.operParam}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-chat-line-square"></i>
          返回参数
        </template>
        {{activeRow.jsonResult}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-cherry"></i>
          操作状态
        </template>
        <dict-tag :value="activeRow.status" :data-list="commonOptions"></dict-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-error"></i>
          错误消息
        </template>
        {{activeRow.errorMsg}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-date"></i>
          操作时间
        </template>
        {{activeRow.operTime}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-s-tools"></i>
          方法名称
        </template>
        {{ activeRow.method }}
      </el-descriptions-item>
    </el-descriptions>
  </div>
</template>

<script>
import sysUserApi from '@/api/system/sysUser'
export default {
  props: {
    activeRow: {
      type: Object,
      default: null
    },
    operTypeOptions:{
      type: Array,
      default: []
    },
    commonOptions:{
      type: Array,
      default:[]
    }
  },
  data() {
    return {
      // 回显用户信息对象
      sysUser: {}
    }
  },
  methods: {

  }
}
</script>

<style scoped>
.header-container {
  text-align: center;
  margin-bottom: 15px;
}
</style>
