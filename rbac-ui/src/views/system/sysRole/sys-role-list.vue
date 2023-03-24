<template>
  <div>
    <el-card style="margin-top: 5px">
      <el-form :model="page" :inline="true">
        <el-form-item label="角色名称">
          <el-input v-model="page.params.roleName" placeholder="请输入角色名称" clearable></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="page.params.status" clearable>
            <el-option v-for="dictData in statusOptions" :label="dictData.dictLabel"
                       :value="dictData.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="doSearch"> 搜索</el-button>
          <el-button type="warning" icon="el-icon-refresh" @click="doReset"> 重置</el-button>
          <el-button type="primary" icon="el-icon-plus" @click="doAddDialog"> 添加</el-button>
        </el-form-item>
      </el-form>
    </el-card>


    <el-card style="margin-top: 5px">
      <el-table :data="dataPage.list" width="100%" border stripe>
        <el-table-column prop="roleId" label="角色编码" align="center">
        </el-table-column>
        <el-table-column prop="roleName" label="角色名称" align="center">
        </el-table-column>
        <el-table-column prop="roleSort" label="角色顺序" align="center">
        </el-table-column>
        <el-table-column prop="remark" label="备注" align="center">
        </el-table-column>

        <el-table-column prop="status" label="角色状态" align="center">
          <template slot-scope="{row}">
            <dict-tag :value="row.status" :data-list="statusOptions"></dict-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createBy" label="创建人" align="center">
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" align="center">
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="{row}">
            <el-dropdown>
                <span class="el-dropdown-link">
                  <el-link type="primary">操作</el-link><i class="el-icon-arrow-down el-icon--right"></i>
                </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>
                  <el-button type="text" icon="el-icon-edit" @click="toUpdateDialog(row)">修改</el-button>
                </el-dropdown-item>
                <el-dropdown-item>
                  <el-button type="text" icon="el-icon-delete" @click="toDeleteSysRole(row)">删除</el-button>
                </el-dropdown-item>
                <el-dropdown-item>
                  <el-button type="text" icon="el-icon-delete" @click="toRoleSelectMenus(row)">角色菜单分配</el-button>
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        class="pagination"
        :page-size="page.pageSize"
        :page-sizes="[10,20,30,40]"
        :total="dataPage.totalCount"
        layout="total,sizes,prev,pager,next,jumper"
        @current-change="handlerCurrentChange"
        @size-change="handlerSizeChange"
      />
    </el-card>


     <!--
         添加对话框
      -->
      <el-dialog :visible.sync="addDialog" v-if="addDialog" :title="title" center="center" style="width: 85%">
        <sys-role-add  @after="doSearch" @close="closeDialog"></sys-role-add>
      </el-dialog>

    <!--
         更新对话框
      -->
    <el-dialog :visible.sync="updateDialog" v-if="updateDialog" :title="title" center="center" style="width: 85%">
      <sys-role-update   :active-id="activeId"  @after="doSearch" @close="closeDialog"></sys-role-update>
    </el-dialog>


    <el-dialog :visible.sync="selectMenuDialog" v-if="selectMenuDialog" :title="title" center="center" style="width: 85%;height: auto">
      <sys-role-select-menu-pro   :active-id="activeId"  @after="doSearch" @close="closeDialog" ></sys-role-select-menu-pro>
    </el-dialog>



  </div>

</template>

<script>
import sysDictDataApi from "@/api/system/sysDictData"
import sysRoleApi from "@/api/system/sysRole"
import SysRoleAdd  from "./sys-role-add"
import SysRoleUpdate  from "./sys-role-update"
//import  SysRoleSelectMenu from "./sys-role-select-menu"
import  SysRoleSelectMenuPro from "./sys-role-select-menu-pro"

export default {
  name: "sys-role-list",
  components:{
    SysRoleAdd,
    SysRoleUpdate,
    SysRoleSelectMenuPro
  },
  created() {
    this.loadDictDataByDictType()
    this.doSearch()
  },
  data() {
    return {
      page: {
        pageNumber: 1,
        pageSize: 10,
        params: {
          roleName: undefined,
          status: undefined
        }
      },
      statusOptions: [],
      dataPage: {
        list: [],
        totalCount: 0,
        totalPage: 0
      },
      addDialog:false,
      updateDialog:false,
      title:undefined,
      activeId:undefined,
      selectMenuDialog:false
    }
  },
  methods: {
    loadDictDataByDictType() {
      sysDictDataApi.getDictDataByDictType("sys_status_type").then(res => {
        this.statusOptions = res.data
      })
    },
    doSearch() {
      sysRoleApi.getByPage(this.page).then(res => {
        this.dataPage.list = res.data.list
        this.dataPage.totalCount = res.data.totalCount
        this.dataPage.totalPage = res.data.totalPage
      })
    },
    doReset() {
      this.page = {
        pageNumber: 1,
        pageSize: 10,
        params: {
          roleName: undefined,
          status: undefined
        }
      }
    },
    toUpdateDialog(row) {
      this.updateDialog=true
      this.title="角色【"+row.roleName+"】修改"
      this.activeId = row.roleId
    },
    toDeleteSysRole(row) {
      this.$confirm("确定删除【" + row.roleName + "】记录?", {
        confirmButtonText: "确定",
        cancelButtonText: "取消"
      }).then(res => {
        sysRoleApi.delete(row.roleId).then(res=>{
          this.$message.success(res.msg)
          this.doSearch()
        })

      })
    },
    handlerCurrentChange(pageNumber) {
      this.page.pageNumber = pageNumber
      this.doSearch()
    },
    handlerSizeChange(pageSize) {
      this.page.pageSize = pageSize
      this.doSearch()
    },
    doAddDialog(){
      this.addDialog=true
      this.title="角色添加"
    },
    closeDialog(){
      this.addDialog=false
      this.updateDialog=false
      this.selectMenuDialog=false
      this.title=undefined
    },
    // 角色菜单分配
    toRoleSelectMenus(row){
      this.selectMenuDialog=true
      this.title="角色【"+row.roleName+"】菜单权限分配"
      this.activeId = row.roleId
    }
  }
}
</script>

<style scoped>

</style>
