<template>
   <div>
     <el-form ref="form" :model="sysUser" :rules="rules" label-width="80px" size="small">
       <el-row :gutter="20">
         <el-col :span="24">
           <el-form-item label="所在部门">
             <treeselect
               v-model="sysUser.deptId"
               :options="deptOptions"
               :normalizer="normalizer"
               :show-count="true"
               placeholder="选择所在部门"
             />
           </el-form-item>
         </el-col>
       </el-row>
       <el-row :gutter="20">
         <el-col :span="12">
           <el-form-item label="账号" prop="userName">
             <el-input v-model="sysUser.userName" placeholder="请输入账号" clearable />
           </el-form-item>
         </el-col>
         <el-col :span="12">
           <el-form-item label="昵称" prop="nickName">
             <el-input v-model="sysUser.nickName" placeholder="请输入昵称" clearable />
           </el-form-item>
         </el-col>
       </el-row>
       <el-row :gutter="20">
         <el-col :span="12">
           <el-form-item label="邮箱" prop="email">
             <el-input v-model="sysUser.email" placeholder="请输入邮箱" clearable />
           </el-form-item>
         </el-col>
         <el-col :span="12">
           <el-form-item label="手机号" prop="phone">
             <el-input v-model="sysUser.phone" placeholder="请输入手机号" clearable />
           </el-form-item>
         </el-col>
       </el-row>
       <el-row :gutter="20">
         <el-col :span="12">
           <el-form-item label="性别" >
             <el-radio-group v-model="sysUser.sex">
               <el-radio v-for="status in sexOptions" :key="status.dictValue" :label="status.dictValue">{{ status.dictLabel }}</el-radio>
             </el-radio-group>
           </el-form-item>
         </el-col>
         <el-col :span="12">
           <el-form-item label="状态" >
             <el-radio-group v-model="sysUser.status">
               <el-radio v-for="status in statusOptions" :key="status.dictValue" :label="status.dictValue">{{ status.dictLabel }}</el-radio>
             </el-radio-group>
           </el-form-item>
         </el-col>
       </el-row>
       <el-form-item>
         <el-button type="primary" @click="doAddSysUser">添加</el-button>
         <el-button type="warning" @click="doReset">重置</el-button>
       </el-form-item>
     </el-form>
   </div>
</template>

<script>
import  sysDictDataApi from "@/api/system/sysDictData"
import  sysDeptApi from "@/api/system/sysDept"
import Treeselect from "@riophae/vue-treeselect"
import "@riophae/vue-treeselect/dist/vue-treeselect.css"
import {handleTree} from "@/utils/powernode";
import sysRoleApi from "@/api/system/sysRole";
import  sysUserApi from "@/api/system/sysUser"
export default {
  name: "sys-user-add",
  components:{
    Treeselect
  },
  created() {
    this.loadDictDataSexByDictType()
    this.loadDictDataByDictType()
    this.loadAllDeptList()
  },
  data(){
    return {
      sysUser:{
          status:0,
          sex:0
      },
      sexOptions:[],
      statusOptions:[],
      deptOptions:[],
      rules:{
        userName:[
          {
            required:true,trigger:"blur",message:"请指定用户名称!"
          }
        ],
        nickName:[
          {
            required:true,trigger:"blur",message:"请指定昵称!"
          }
        ],
        email:[
          {
            required:true,trigger:"blur",message:"请指定邮箱!"
          }
        ],
        phone:[
          {
            required:true,trigger:"blur",message:"请指定手机号!"
          }
        ]
      }
    }

  },
  methods:{
    loadAllDeptList(){
      sysDeptApi.getAllDeptList().then(res=>{
        this.deptOptions=handleTree(res.data,"deptId")
      })
    },
    loadDictDataSexByDictType() {
      sysDictDataApi.getDictDataByDictType("sys_user_sex").then(res => {
        this.sexOptions = res.data
      })
    },
    loadDictDataByDictType() {
      sysDictDataApi.getDictDataByDictType("sys_status_type").then(res => {
        this.statusOptions = res.data
      })
    },
    normalizer(node) {
      return {
        id: node.deptId,
        label: node.deptName,
        children: node.children
      }
    },
    doAddSysUser(){
      this.$refs["form"].validate(valid=>{
        if(valid){
          sysUserApi.save(this.sysUser).then(res=>{
            this.$message.success(res.msg)
            this.doReset()
            this.$emit("after")
            this.$emit("close")
          })
        }
      })
    },
    doReset(){
      this.sysUser = {sex:0,status:0}
    }
  }
}
</script>

<style scoped>

</style>
