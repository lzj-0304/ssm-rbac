<template>
  <div>
    <el-form :model="form" ref="form" :rules="rules" size="small" label-width="78px">
      <el-form-item label="上级部门" style="width: 80%">
        <!--             <el-input v-model="form.parentId"></el-input>-->
        <treeselect v-model="form.parentId" :options="deptList" :normalizer="normalizer" :show-count="true"
                    placeholder="上级部门"/>
      </el-form-item>
      <el-row>
        <el-col :span="12">
          <el-form-item label="部门名称" prop="deptName">
            <el-input v-model="form.deptName" style="width: 60%"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="显示排序" prop="orderNum">
            <el-input-number v-model="form.orderNum" :min="1" :max="100"></el-input-number>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="负责人" prop="leader">
            <el-input v-model="form.leader" style="width: 60%"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="联系电话" prop="phone">
            <el-input v-model="form.phone" style="width: 50%"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="邮箱">
            <el-input v-model="form.email" style="width: 60%"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="部门状态">
            <el-radio-group v-model="form.status">
              <el-radio v-for="dictData in statusOptions" :label="dictData.dictValue">{{ dictData.dictLabel }}
              </el-radio>
            </el-radio-group>

          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item>
        <el-button type="primary" @click="toAddSysDept"> 添加</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import sysDictDataApi from "@/api/system/sysDictData"
import Treeselect from "@riophae/vue-treeselect"
import "@riophae/vue-treeselect/dist/vue-treeselect.css"
import sysDeptApi from "@/api/system/sysDept"
import {handleTree} from "@/utils/powernode";

export default {
  name: "sys-dept-add",
  props:{
    parentId:{
      type:Number,
      default:undefined
    }
  },
  watch:{
    parentId:{
      immediate:true,
      handler:function (newVal,oldVal){
         this.form.parentId=newVal
      }
    }
  },
  components: {
    Treeselect
  },
  created() {
    this.loadDictDataByDictType()
    this.loadDeptList()
  },
  data() {
    return {
      form: {
        status: 0
      },
      statusOptions: [],
      rules: {
        deptName: [
          {
            required: true, trigger: "blur", message: "部门名称不能为空!"
          }
        ],
        orderNum: [
          {
            required: true, trigger: "blur", message: "请指定排序值!"
          }
        ],
        leader: [
          {
            required: true, trigger: "blur", message: "请指定部门负责人!"
          }
        ],
        phone: [
          {
            required: true, trigger: "blur", message: "请指定联系电话!"
          }
        ]
      },
      deptList: []
    }
  },
  methods: {
    loadDictDataByDictType() {
      sysDictDataApi.getDictDataByDictType("sys_status_type").then(res => {
        this.statusOptions = res.data
      })
    },
    loadDeptList() {
      sysDeptApi.getAllDeptList().then(res => {
        this.deptList = handleTree(res.data, "deptId")
        console.log(this.deptList)
      })
    },
    normalizer(node) {
      return {
        id: node.deptId,
        label: node.deptName,
        children: node.children
      }
    },
    toAddSysDept() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          sysDeptApi.save(this.form).then(res => {
            this.$message.success(res.msg)
            this.$emit("after")
            this.$emit("close")
            this.form = {
              status: 0
            }
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
