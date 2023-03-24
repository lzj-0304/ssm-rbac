<template>
  <div>
    <el-card style="margin-top: 5px">
      <el-form :model="queryParams" :inline="true">
        <el-form-item label="部门名称">
          <el-input v-model="queryParams.deptName" placeholder="请输入部门名称" clearable></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="queryParams.status" clearable>
            <el-option v-for="dictData in statusOptions" :label="dictData.dictLabel"
                       :value="dictData.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="doSearch"> 搜索</el-button>
          <el-button type="warning" icon="el-icon-refresh" @click="doReset"> 重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>


    <!--
       row-key  每条记录唯一标识 deptId
       default-expand-all: 表格是否展开
       tree-props: 描述表数据格式
           children:描述子记录
    -->
    <el-card style="margin-top: 5px">
      <div>
        <el-button type="primary" icon="el-icon-plus" @click="doAddDialog">添加</el-button>
        <el-button type="warning" icon="el-icon-sort" @click="doExpandAll">折叠/展开</el-button>
      </div>
      <el-table
        :data="deptList"
        row-key="deptId"
        border
        :default-expand-all="isExpandAll"
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}" style="margin-top: 5px"
        v-if="refreshTable"
      >
        <el-table-column
          prop="deptName"
          label="部门名称"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="orderNum"
          label="排序"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="leader"
          label="负责人"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="phone"
          label="电话"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="email"
          label="邮箱"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="status"
          label="状态"
          align="center"
        >
          <template slot-scope="{row}">
            <dict-tag :value="row.status" :data-list="statusOptions"></dict-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="{row}">
            <el-dropdown>
                <span class="el-dropdown-link">
                  <el-link type="primary">操作</el-link><i class="el-icon-arrow-down el-icon--right"></i>
                </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>
                  <el-button type="text" icon="el-icon-plus" @click="doAddDialog(row)">新增</el-button>
                </el-dropdown-item>
                <el-dropdown-item>
                  <el-button type="text" icon="el-icon-edit" @click="doUpdateDialog(row)">修改</el-button>
                </el-dropdown-item>
                <el-dropdown-item>
                  <el-button type="text" icon="el-icon-delete" @click="doDeleteSysDept(row)">删除</el-button>
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </template>
        </el-table-column>

      </el-table>
    </el-card>


    <!--
       部门添加对话框
    -->
    <el-dialog :visible.sync="addDialog" v-if="addDialog" :title="title" center="center" width="50%">
      <sys-dept-add @after="doSearch" @close="closeDialog" :parent-id="parentId"></sys-dept-add>
    </el-dialog>



    <!--
        部门更新对话框
    -->
    <el-dialog :visible.sync="updateDialog" v-if="updateDialog" :title="title" center="center" width="50%">
      <sys-dept-update @after="doSearch" @close="closeDialog" :active-id="activeId"></sys-dept-update>
    </el-dialog>

  </div>
</template>

<script>
import sysDictDataApi from "@/api/system/sysDictData"
import sysDeptApi from "@/api/system/sysDept"
import {handleTree} from "@/utils/powernode";
import SysDeptAddTest from "./sys-dept-add-test"
import SysDeptAdd from "./sys-dept-add"
import SysDeptUpdate from "./sys-dept-update"

export default {
  name: "sys-dept-list",
  components: {
    SysDeptAddTest,
    SysDeptAdd,
    SysDeptUpdate
  },
  data() {
    return {
      deptList: [],
      statusOptions: [],
      queryParams: {
        deptName: undefined,
        status: undefined
      },
      isExpandAll: false,
      // 控制dom 渲染
      refreshTable: true,
      addDialog: false,
      title: undefined,
      parentId: undefined,
      updateDialog: false,
      activeId: undefined
    }
  },
  created() {
    // 加载 指定类型下的字典数据
    this.loadDictDataByDictType()
    this.doSearch()
  },
  methods: {
    loadDictDataByDictType() {
      sysDictDataApi.getDictDataByDictType("sys_status_type").then(res => {
        this.statusOptions = res.data
      })
    },
    doSearch() {
      sysDeptApi.getAllDeptList(this.queryParams).then(res => {
        this.deptList = handleTree(res.data, "deptId")
      })
    },
    doReset() {
      this.queryParams = {}
    },
    doExpandAll() {
      // 清楚原始 dom (表格)
      this.refreshTable = false
      this.isExpandAll = !this.isExpandAll
      this.$nextTick(() => {
        // 重新渲染表格
        this.refreshTable = true
      })
    },
    doAddDialog(row) {
      this.addDialog = true
      this.title = "添加部门"
      this.parentId = row.deptId
    },
    closeDialog() {
      this.addDialog = false
      this.updateDialog = false
      this.title = undefined
    },
    doUpdateDialog(row) {
      this.updateDialog = true
      this.title = "更新部门"
      this.activeId = row.deptId
    },
    doDeleteSysDept(row) {
      this.$confirm("确定删除【" + row.deptName + "】记录?", "记录删除", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(res => {
        sysDeptApi.delete(row.deptId).then(res => {
          this.$message.success(res.msg)
          this.doSearch()
        })
      })
    }
  }
}
</script>

<style scoped>

</style>
