<template>
  <div>
    <div class="header-container">
      <el-image
        style="width: 100px; height: 100px"
        :src="sysUser.avatar"
        fit="fill"
      />
    </div>
    <el-descriptions class="margin-top" title="详情查看" :column="3"  border>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          用户名
        </template>
        {{sysUser.userName}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          手机号
        </template>
        {{ sysUser.phone }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-location-outline"></i>
          用户类型
        </template>
        <el-tag type="success">{{ sysUser.userType==0?"超级用户":"系统用户" }}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-location-outline"></i>
          性别
        </template>
        <el-tag type="success">{{ sysUser.sex==0?"男":"女" }}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-tickets"></i>
          昵称
        </template>
         {{sysUser.nickName}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-office-building"></i>
          邮箱
        </template>
        <el-tag type="success">{{sysUser.email}}</el-tag>
      </el-descriptions-item>

<!--      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-office-building"></i>
          部门 {{sysUser.deptId}}
        </template>
        <template slot-scope="">
          <el-tag   v-for="dept in deptOptions" v-if="dept.deptId === sysUser.deptId" :type="success"  :key="dept">{{dept.deptName}}</el-tag>
        </template>

      </el-descriptions-item>-->


      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-office-building"></i>
          创建人
        </template>
        {{sysUser.createBy}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-office-building"></i>
          创建时间
        </template>
        {{sysUser.createTime}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-office-building"></i>
          更新人
        </template>
        {{sysUser.updateBy}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-office-building"></i>
          更新时间
        </template>
        {{sysUser.updateTime}}
      </el-descriptions-item>
    </el-descriptions>
  </div>
</template>

<script>
import sysUserApi  from "@/api/system/sysUser"
import sysDeptApi from "@/api/system/sysDept";
import {handleTree} from "@/utils/powernode";
export default {
  name: "sys-user-info",
  data(){
    return {
      sysUser:{

      },
      deptOptions:[]
    }
  },
  props: {
    activeId: {
      type: Number,
      default: undefined
    }
  },
  created() {
    this.loadAllDeptList()
  },
  watch:{
    activeId:{
      immediate:true,
      handler:function (newVal){
        sysUserApi.get(newVal).then(res=>{
           this.sysUser =  res.data
        })
      }
    }
  },
  methods:{
    loadAllDeptList(){
      sysDeptApi.getAllDeptList().then(res=>{
        this.deptOptions=handleTree(res.data,"deptId")
      })
    }
  }
}
</script>

<style scoped>
.header-container {
  text-align: center;
  margin-bottom: 15px;
}
</style>
