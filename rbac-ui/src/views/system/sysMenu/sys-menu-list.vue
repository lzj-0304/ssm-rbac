<template>
  <div>
    <el-card style="margin-top: 5px">
      <el-form :model="queryParams" :inline="true">
        <el-form-item>
          <el-input v-model="queryParams.menuName" placeholder="请输入菜单名称" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-select v-model="queryParams.status" clearable placeholder="请选中菜单状态">
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


    <el-card style="margin-top: 5px">
      <div>
        <el-button type="primary" icon="el-icon-plus" @click="toAddDialog(null)">添加</el-button>
        <el-button type="warning" icon="el-icon-sort" @click="doExpandAll">折叠/展开</el-button>
      </div>
      <el-table
        :data="menuList"
        row-key="menuId"
        border
        :default-expand-all="isExpandAll"
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}" style="margin-top: 5px"
        v-if="refreshTable"
      >
        <el-table-column
          prop="menuName"
          label="菜单名称"
          align="center"
          :show-overflow-tooltip="true"
        >
        </el-table-column>
        <el-table-column
          label="图标"
          align="center"
        >
          <template slot-scope="{row}">
            <svgicon :icon-class="row.icon"></svgicon>
          </template>
        </el-table-column>
        <el-table-column
          prop="orderNum"
          label="排序"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="perms"
          label="权限标识"
          align="center"
          :show-overflow-tooltip="true"
        >
        </el-table-column>
        <el-table-column
          prop="component"
          label="组件路径"
          align="center"
          :show-overflow-tooltip="true"
        >
        </el-table-column>
        <el-table-column
          prop="path"
          label="路由地址"
          align="center"
          :show-overflow-tooltip="true"
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
        <el-table-column
          prop="menuType"
          label="类型"
          align="center"
        >
          <template slot-scope="{row}">
            <el-tag :type=" row.menuType==0?'success':row.menuType==1?'info':'warning'   ">
              {{ row.menuType == 0 ? '目录' : row.menuType == 1 ? '菜单' : '权限' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="{row}">
            <el-dropdown>
                <span class="el-dropdown-link">
                  <el-link type="primary">操作</el-link><i class="el-icon-arrow-down el-icon--right"></i>
                </span>
              <el-dropdown-menu slot="dropdown">

                <el-dropdown-item v-if="row.menuType !=2">
                  <el-button type="text" icon="el-icon-plus" @click="toAddDialog(row)">新增</el-button>
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
       添加对话框
    -->
    <el-dialog :visible.sync="addDialog" v-if="addDialog" :title="title" center="center" style="width: 80%">
      <sys-menu-add @after="doSearch" @close="closeDialog" :active-obj="activeObj"></sys-menu-add>
    </el-dialog>


    <!--
      更新对话框
   -->
    <el-dialog :visible.sync="updateDialog" v-if="updateDialog" :title="title" center="center" style="width: 80%">
      <sys-menu-update @after="doSearch" @close="closeDialog" :active-obj="activeObj"></sys-menu-update>
    </el-dialog>

  </div>
</template>

<script>
import sysDictDataApi from "@/api/system/sysDictData"
import sysMenuApi from "@/api/system/sysMenu"
import {handleTree} from "@/utils/powernode";
import Svgicon from "@/components/SvgIcon/index"
import SysMenuAdd from "@/views/system/sysMenu/sys-menu-add";
import SysMenuUpdate from "@/views/system/sysMenu/sys-menu-update"

export default {
  name: "sys-menu-list",
  components: {
    SysMenuAdd,
    Svgicon,
    SysMenuUpdate
  },
  created() {
    this.loadDictDataByDictType()
    this.doSearch()
  },
  data() {
    return {
      statusOptions: [],
      queryParams: {
        menuName: undefined,
        status: undefined
      },
      menuList: [],
      isExpandAll: false,
      refreshTable: true,
      addDialog: false,
      title: undefined,
      activeObj: null,
      updateDialog: false
    }
  },
  methods: {
    loadDictDataByDictType() {
      sysDictDataApi.getDictDataByDictType("sys_status_type").then(res => {
        this.statusOptions = res.data
      })
    },
    doSearch() {
      sysMenuApi.getAllMenuList(this.queryParams).then(res => {
        this.menuList = handleTree(res.data, "menuId")
        console.log(this.menuList)
      })
    },
    doReset() {
      this.queryParams = {}
    },
    doExpandAll() {
      this.refreshTable = false
      this.isExpandAll = !this.isExpandAll
      this.$nextTick(() => {
        this.refreshTable = true
      })
    },
    toAddDialog(row) {
      this.addDialog = true
      this.title = "添加菜单"
      this.activeObj = row
    },
    closeDialog() {
      this.addDialog = false
      this.updateDialog=false
      this.title = undefined
    },
    doUpdateDialog(row) {
      this.updateDialog = true
      this.title = "菜单更新"
      this.activeObj = row
    },
    doDeleteSysDept(row) {
      this.$confirm("确定删除【" + row.menuName + "】记录?", "菜单删除", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(res => {
        sysMenuApi.delete(row.menuId).then(res => {
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
