<template>
  <div>
    <el-form :model="form" ref="form" :rules="rules"  size="small" label-width="78px">
      <el-form-item label="字典类型" >
        <el-input v-model="form.dictType" placeholder="请输入字典类型" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="数据标签"  prop="dictLabel">
        <el-input v-model="form.dictLabel" placeholder="请输入字典标签"></el-input>
      </el-form-item>
      <el-form-item label="键值">
        <el-input placeholder="请输入键值" v-model="form.dictValue"></el-input>
      </el-form-item>
      <el-form-item label="排序">
        <el-input v-model="form.dictSort" placeholder="排序"></el-input>
      </el-form-item>
      <el-form-item label="皮肤属性">
        <el-select v-model="form.dictSkin" placeholder="请选择皮肤">
          <el-option v-for="skin in skinOptions"   :label="skin.label" :value="skin.value" ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="备注">
        <el-input type="textarea" placeholder="请输入备注" v-model="form.remark"></el-input>
      </el-form-item>
      <el-form-item label="状态">
        <el-radio-group v-model="form.status">
<!--          <el-radio :label="0" >正常</el-radio>
          <el-radio :label="1" >停用</el-radio>-->
          <el-radio v-for="dictData in statusOptions" :label="dictData.dictValue">{{dictData.dictLabel}}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="small" @click="doAddSysDictData" >添加</el-button>
        <el-button type="warning" size="small" @click="doReset">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import sysDictDataApi from "@/api/system/sysDictData"
export default {
  name: "sys-dict-data-add",
  props:{
    dictType:{
      type:String,
      default:undefined
    }
  },
  created() {
    this.loadDictDataByDictType()
  },
  watch:{
    dictType:{
      immediate:true,
      handler:function (newVal,oldVal){
        this.form.dictType = newVal
      }
    }
  },
  data(){
    return {
      form:{
        status:0
      },
      skinOptions:[
        {
          "label":"成功",
          "value":"success"
        },
        {
          "label":"信息",
          "value":"info"
        },
        {
          "label":"禁用",
          "value":"error"
        }
      ],
      statusOptions:[],
      rules:{
        dictValue:[
          {
            required:true,trigger:"blur",message:"请指定键值!"
          }
        ],
        dictLabel:[{
          required:true,trigger:"blur",message:"请指定数据标签!"
        }]
      }
    }
  },
  methods:{
    loadDictDataByDictType(){
      sysDictDataApi.getDictDataByDictType("sys_status_type").then(res=>{
        this.statusOptions = res.data
      })
    },
    doAddSysDictData(){
        this.$refs["form"].validate(valid=>{
          if(valid){
               sysDictDataApi.save(this.form).then(res=>{
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
