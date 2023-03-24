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
            <el-input v-model="sysUser.userName" placeholder="请输入账号" clearable/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="昵称" prop="nickName">
            <el-input v-model="sysUser.nickName" placeholder="请输入昵称" clearable/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="sysUser.email" placeholder="请输入邮箱" clearable/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="sysUser.phone" placeholder="请输入手机号" clearable/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="性别">
            <el-radio-group v-model="sysUser.sex">
              <el-radio v-for="status in sexOptions" :key="status.dictValue" :label="status.dictValue">
                {{ status.dictLabel }}
              </el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="状态">
            <el-radio-group v-model="sysUser.status">
              <el-radio v-for="status in statusOptions" :key="status.dictValue" :label="status.dictValue">
                {{ status.dictLabel }}
              </el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item>
        <el-button type="primary" @click="updateSysUser">修改</el-button>
      </el-form-item>
    </el-form>

  </div>
</template>

<script>
import sysUserApi from "@/api/system/sysUser"
import sysDeptApi from "@/api/system/sysDept"
import Treeselect from "@riophae/vue-treeselect"
import "@riophae/vue-treeselect/dist/vue-treeselect.css"
import {handleTree} from "@/utils/powernode";
import sysDictDataApi from "@/api/system/sysDictData";

export default {
  name: "sys-user-update",
  components: {
    Treeselect
  },
  data() {
    return {
      sysUser: {},
      sexOptions: [],
      statusOptions: [],
      deptOptions: [],


      rules: {
        userName: [
          {
            required: true, trigger: "blur", message: "请指定用户名称!"
          }
        ],
        nickName: [
          {
            required: true, trigger: "blur", message: "请指定昵称!"
          }
        ],
        email: [
          {
            required: true, trigger: "blur", message: "请指定邮箱!"
          }
        ],
        phone: [
          {
            required: true, trigger: "blur", message: "请指定手机号!"
          }
        ]
      }

    }
  },
  props: {
    activeId: {
      type: Number,
      default: undefined
    }
  },
  created() {
    this.loadDictDataSexByDictType()
    this.loadDictDataByDictType()
    this.loadAllDeptList()
  },
  watch: {
    activeId: {
      immediate: true,
      handler: function (newVal, oldVal) {
        this.getById(newVal)
      }
    }
  },
  methods: {
    getById(userId) {
      sysUserApi.get(userId).then(res => {
        this.sysUser = res.data
      })
    },
    loadAllDeptList() {
      sysDeptApi.getAllDeptList().then(res => {
        this.deptOptions = handleTree(res.data, "deptId")
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
    updateSysUser() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          sysUserApi.update(this.sysUser).then(res => {
            this.$message.success(res.msg)
            this.$emit("after")
            this.$emit("close")
            this.sysUser = {}
          })
        }
      })

    }
  }

}
</script>

<style scoped>

</style>
