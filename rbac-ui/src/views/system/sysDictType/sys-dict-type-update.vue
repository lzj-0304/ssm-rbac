<template>
  <div>
    <el-form :model="form" :rules="rules" ref="form" label-width="78px">
      <el-form-item label="字典名称" prop="dictName">
        <el-input v-model="form.dictName" placeholder="请输入字典名称"></el-input>
      </el-form-item>
      <el-form-item label="字典类型" prop="dictType">
        <el-input v-model="form.dictType" placeholder="请输入字典类型"></el-input>
      </el-form-item>
      <el-form-item label="备注">
        <el-input type="textarea" placeholder="备注" v-model="form.remark"></el-input>
      </el-form-item>
      <el-form-item label="状态">
        <el-radio-group v-model="form.status">
<!--          <el-radio :label="0">正常</el-radio>
          <el-radio :label="1">停用</el-radio>-->
          <el-radio v-for="dictData in statusOptions" :label="dictData.dictValue">{{dictData.dictLabel}}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="doUpdateSysDictType">更新</el-button>
        <el-button type="warning" @click="doReset">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import sysDictTypeApi from "@/api/system/sysDictType"
import sysDictDataApi from "@/api/system/sysDictData";

export default {
  name: "sys-dict-type-update",
  props: {
    // 父组件传来的 字典类型 id
    activeId: {
      default: undefined,
      type: Number
    }
  },
  created() {
    this.loadDictDataByDictType()
  },
  watch: {
    activeId: {
      immediate: true,
      handler: function (newVal, oldVal) {
        sysDictTypeApi.getById(this.activeId).then(res => {
          this.form = res.data
        })
      }
    }
  },
  data() {
    return {
      form: {
        status: 0
      },
      rules: {
        dictName: [
          {
            required: true, trigger: "blur", message: "字典名称不能为空!"
          }
        ],
        dictType: [
          {
            required: true, trigger: "blur", message: "字典类型不能为空!"
          }
        ]
      },
      statusOptions:[]
    }
  },
  methods: {
    loadDictDataByDictType(){
      sysDictDataApi.getDictDataByDictType("sys_status_type").then(res=>{
        this.statusOptions = res.data
      })
    },
    doUpdateSysDictType() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          sysDictTypeApi.update(this.form).then(res => {
            this.$message.success(res.msg)
            this.$emit("after")
            this.$emit("close")
            this.doReset()
          })
        }
      })
    },
    doReset() {
      this.form={
        status:0
      }
    }
  }
}
</script>

<style scoped>

</style>
