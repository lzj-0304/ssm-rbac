<template>
  <div>
    <el-form :model="form" ref="form" :rules="rules"  size="small" label-width="78px">
      <el-form-item label="角色名称"  prop="roleName">
        <el-input v-model="form.roleName" placeholder="请输入字典名称"></el-input>
      </el-form-item>
      <el-form-item label="排序"  prop="roleSort">
        <el-input-number v-model="form.roleSort" :min="1" ></el-input-number>
      </el-form-item>
      <el-form-item label="备注">
        <el-input type="textarea" placeholder="角色备注" v-model="form.remark"></el-input>
      </el-form-item>
      <el-form-item label="状态">
        <el-radio-group v-model="form.status">
          <el-radio v-for="dictData in statusOptions" :label="dictData.dictValue">{{dictData.dictLabel}}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="small" @click="doUpdateSysRole" >更新</el-button>
        <el-button type="warning" size="small" @click="doReset">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import sysDictDataApi  from "@/api/system/sysDictData"
import sysRoleApi from "@/api/system/sysRole"
export default {
  name: "sys-role-add",
  props:{
    activeId:{
      type:Number,
      default:undefined
    }
  },
  watch:{
    activeId:{
      immediate:true,
      handler:function (newVal,oldVal){
        sysRoleApi.getById(newVal).then(res=>{
            this.form= res.data
        })
      }
    }
  },
  created() {
    this.loadDictDataByDictType()
  },
  data(){
    return {
      form:{
        status:0
      },
      statusOptions:[],
      rules:{
        roleName:[
          {
            required:true,trigger:"blur",message:"请指定角色名称!"
          }
        ],
        roleSort:[
          {
            required:true,trigger:"blur",message:"请指定角色排序!"
          }
        ]
      }
    }
  },
  methods:{
    loadDictDataByDictType(){
      sysDictDataApi.getDictDataByDictType("sys_status_type").then(res=>{
        this.statusOptions = res.data
      })
    },
    doUpdateSysRole(){
        this.$refs["form"].validate(valid=>{
          if(valid){
               sysRoleApi.update(this.form).then(res=>{
                   this.$message.success(res.msg)
                   this.doReset()
                   this.$emit("after")
                   this.$emit("close")
               })
          }
        })
    },
    doReset(){
      this.form={
        status: 0
      }
    }
  }
}
</script>

<style scoped>

</style>
