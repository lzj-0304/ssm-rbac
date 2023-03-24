<template>
  <div>
    <el-form :model="form" ref="form"  :rules="rules"  size="small" label-width="78px">
        <el-form-item label="字典名称" prop="dictName">
            <el-input v-model="form.dictName" placeholder="请输入字典名称"></el-input>
        </el-form-item>
        <el-form-item label="字典类型" prop="dictType">
            <el-input v-model="form.dictType" placeholder="请输入字典类型"></el-input>
        </el-form-item>
        <el-form-item label="备注">
           <el-input type="textarea" placeholder="请输入备注" v-model="form.remark"></el-input>
        </el-form-item>
       <el-form-item label="状态">
          <el-radio-group v-model="form.status">
<!--              <el-radio :label="0" >正常</el-radio>
              <el-radio :label="1" >停用</el-radio>-->
            <el-radio v-for="dictData in statusOptions" :label="dictData.dictValue">{{dictData.dictLabel}}</el-radio>
          </el-radio-group>
       </el-form-item>
      <el-form-item>
          <el-button type="primary" size="small" @click="doAddDictType">添加</el-button>
          <el-button type="warning" size="small" @click="doReset">重置</el-button>
      </el-form-item>
    </el-form>

  </div>
</template>

<script>
import  sysDictTypeApi from "@/api/system/sysDictType"
import sysDictDataApi from "@/api/system/sysDictData";
export default {
  name: "sys-dict-type-add",
  created() {
    this.loadDictDataByDictType()
  },
  data(){
    return {
      form:{
        status:0,
        dictName:undefined,
        dictType:undefined
      },
      rules:{
        dictName:[{
          required:true,trigger:"blur",message:"请输入字典名称"
        }],
        dictType:[
          {
            required:true,trigger:"blur",message:"请输入字典类型"
          }
        ]
      },
      // 记录状态
      statusOptions:[]
    }
  },
  methods:{
    loadDictDataByDictType(){
      sysDictDataApi.getDictDataByDictType("sys_status_type").then(res=>{
        this.statusOptions = res.data
      })
    },
    doAddDictType(){
      this.$refs["form"].validate(valid=>{
        if(valid){
            sysDictTypeApi.save(this.form).then(res=>{
                this.$message.success(res.msg)
                // 调用父组件查询方法
                this.$emit("after")
                // 关闭对话框
                this.$emit("close")
                this.doReset()
            })
        }
      })
    },
    doReset(){
      // 重置表单
      this.form={
        status:0
      }
    }
  }
}
</script>

<style scoped>

</style>
