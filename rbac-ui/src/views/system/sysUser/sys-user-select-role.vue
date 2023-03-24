<template>
  <div>
    <el-table
      v-loading="loading"
      :data="allRoleList"
      ref="roleTable"
      border
      stripe
      style="width: 100%"
      @selection-change="handlerSelectionChange"
    >
      <el-table-column
        type="selection"
        align="center"
      />
      <el-table-column
        prop="roleId"
        label="角色编号"
        align="center"
      />
      <el-table-column
        prop="roleName"
        label="角色名"
        align="center"
      />
      <el-table-column
        prop="roleSort"
        align="center"
        label="排序"
      />
      <el-table-column
        prop="createTime"
        align="center"
        label="创建时间"
      />
    </el-table>
    <div style="text-align: center;margin-top: 10px">
      <el-button type="primary" icon="el-icon-plus" @click="saveUserRole">保存用户和角色关系</el-button>
    </div>
  </div>
</template>

<script>
import sysRoleApi from "@/api/system/sysRole"

export default {
  name: "sys-user-select-role",
  props: {
    activeId: {
      type: Number,
      default: undefined
    }
  },
  data() {
    return {
      loading: false,
      allRoleList: [],
      selections: []
    }
  },
  created() {
    this.loading = true
    sysRoleApi.getAllRoles().then(res => {
      this.allRoleList = res.data
      this.allRoleList.filter(row => {
        /**
         * 根据当前用户选中的角色id 控制行的选中状态
         */
         sysRoleApi.getRoleIdsByUserId(this.activeId).then(res=>{
             this.roleIds= res.data
             this.roleIds.filter(roleId=>{
                if(row.roleId == roleId){
                  // 控制当前row 选中
                  this.$refs["roleTable"].toggleRowSelection(row,true);
                }
             })
         })
      })
      this.loading=false
    })
  },
  methods: {
    saveUserRole() {
      let roleIds = []
      this.selections.filter(s => {
        roleIds.push(s.roleId)
      })
      sysRoleApi.saveUserRoles(this.activeId, roleIds).then(res => {
        this.$message.success(res.msg)
        this.$emit("close")
      })
    },
    handlerSelectionChange(selection) {
      // 存储最后一次点击的记录
      this.selections = selection
    }
  }
}
</script>

<style scoped>

</style>
