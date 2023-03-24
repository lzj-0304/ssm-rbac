<template>
  <div>
    <el-form :model="form" ref="form" :rules="rules"  size="small" label-width="78px">
      <el-form-item label="标题"  prop="noticeTitle">
        <el-input v-model="form.noticeTitle" placeholder="公告标题"></el-input>
      </el-form-item>
      <el-form-item label="公告类型">
        <el-select v-model="form.noticeType"  placeholder="选择通知类型">
          <el-option v-for="dictData in noticeOptions" :label="dictData.dictLabel"
                     :value="dictData.dictValue"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="内容" prop="noticeContent">
        <el-input type="textarea" placeholder="公告内容" v-model="form.noticeContent"></el-input>
      </el-form-item>
      <el-form-item label="备注">
        <el-input type="textarea" placeholder="公告备注" v-model="form.remark"></el-input>
      </el-form-item>
      <el-form-item label="状态">
        <el-radio-group v-model="form.status">
          <el-radio v-for="dictData in statusOptions" :label="dictData.dictValue">{{dictData.dictLabel}}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="small" @click="doUpdateSysNotice" >更新</el-button>
        <el-button type="warning" size="small" @click="doReset">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import sysDictDataApi from "@/api/system/sysDictData";
import sysNoticeApi  from "@/api/system/sysNotice"
import sysRoleApi from "@/api/system/sysRole";
export default {
  name: "sys-notice-update",
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
        sysNoticeApi.getById(newVal).then(res=>{
          this.form= res.data
        })
      }
    }
  },
  created() {
    sysDictDataApi.getDictDataByDictType("sys_notice_type").then(res=>{
      this.noticeOptions=res.data
    })

    sysDictDataApi.getDictDataByDictType("sys_status_type").then(res=>{
      this.statusOptions=res.data
    })
  },
  data(){
    return {
      noticeOptions:[],
      statusOptions:[],
      form:{
        status:0
      },
      rules:{
        noticeTitle:[
          {
            required:true,trigger:"blur",message:"请指定标题!"
          }
        ],
        noticeContent:[
          {
            required:true,trigger:"blur",message:"请指定内容!"
          }
        ]
      }
    }
  },
  methods:{
    doUpdateSysNotice(){
      this.$refs["form"].validate(valid=>{
        if(valid){
          sysNoticeApi.update(this.form).then(res=>{
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
      status:0
    }
}
  }
}
</script>

<style scoped>

</style>
