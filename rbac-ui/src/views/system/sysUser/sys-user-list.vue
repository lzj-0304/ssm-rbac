<template>
  <div class="app-container">
    <el-row :gutter="5">

      <!--部门数据-->
      <el-col :span="4" :xs="24">
        <el-card>
            <sys-user-left-tree  @reloadData="doSearch"></sys-user-left-tree>
        </el-card>
      </el-col>


      <!--用户数据-->
      <el-col :span="20" :xs="24">
        <!-- 搜索栏开始 -->
        <el-card class="search-form">
          <el-form :inline="true" :model="page.params" size="small">
            <el-form-item>
              <el-input v-model="page.params.userName" clearable placeholder="请输入用户名"/>
            </el-form-item>
            <el-form-item>
              <el-input v-model="page.params.nickName" clearable placeholder="请输入昵称"/>
            </el-form-item>
            <el-form-item>
              <el-select v-model="page.params.status" clearable placeholder="请选择状态">
                <el-option v-for="dict in statusOptions" :key="dict.dictValue" :value="dict.dictValue"
                           :label="dict.dictLabel"/>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" @click="doSearch(null)">查询</el-button>
              <el-button type="warning" icon="el-icon-refresh" @click="doReset">重置</el-button>
              <el-button type="primary" size="small"  icon="el-icon-plus"  @click="toAddDialog" v-permission="['system:user:add']">添加</el-button>
              <el-button type="primary" size="small"  icon="el-icon-plus"  @click="toGpt">gtp测试</el-button>
            </el-form-item>
          </el-form>
        </el-card>

        <!-- 搜索栏结束 -->
        <el-card style="margin-top: 3px">
          <!-- 操作按钮组 -->

          <!-- 操作按钮组结束 -->
          <!-- 数据表格开始 -->
          <el-table border :data="dataPage.list" stripe style="width: 100%;margin-top: 3px">
            <!--            <el-table-column align="center" prop="userId" label="编号"/>-->
            <el-table-column align="center" prop="userName" label="账号"/>
            <el-table-column align="center" prop="nickName" label="昵称"/>
            <el-table-column align="center" prop="email" label="邮箱" width="180px"/>
            <el-table-column align="center" prop="phone" label="电话" width="150px"/>
            <el-table-column align="center" prop="sex" label="性别">
              <template slot-scope="{row}">
                <dict-tag :value="row.sex" :data-list="sexOptions"></dict-tag>
              </template>
            </el-table-column>
            <el-table-column align="center" prop="header" label="所在部门" width="150px">
              <template slot-scope="{row}">
                <div v-for="dept in deptList" v-if="row.deptId==dept.deptId">{{ dept.deptName }}</div>
              </template>
            </el-table-column>
            <el-table-column align="center" prop="status" label="状态">
              <template slot-scope="{row}">
                <dict-tag :value="row.status" :data-list="statusOptions"></dict-tag>
              </template>
            </el-table-column>
            <el-table-column align="center" label="操作" fixed="right">
              <template slot-scope="{row}">
                <el-dropdown class="handle-button">
              <span class="el-dropdown-link">
                <el-link icon="el-icon-s-operation" type="primary">操作<i class="el-icon-arrow-down el-icon--right"/>
                </el-link>
              </span>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item >
                      <el-button type="text" icon="el-icon-document"  @click="toUserInfo(row)" v-permission="['system:user:info']">详情</el-button>
                    </el-dropdown-item>
                    <el-dropdown-item>
                      <el-button type="text" icon="el-icon-edit"  @click="toUpdateDialog(row)" v-permission="['system:user:update']">修改</el-button>
                    </el-dropdown-item>
                    <el-dropdown-item v-if="row.userId!=1">
                      <el-button type="text" icon="el-icon-delete" @click.native="deleteById(row)" v-permission="['system:user:remove']">删除</el-button>
                    </el-dropdown-item>
                    <el-dropdown-item>
                      <el-button type="text"  icon="el-icon-edit" @click="toSelectRoleDialog(row)">分配角色</el-button>
                    </el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </template>
            </el-table-column>
          </el-table>
          <!-- 分页组件开始 -->
          <div style="margin-top: 3px">
            <el-pagination
              :current-page="page.pageNumber"
              :page-sizes="[10, 20, 30, 50]"
              :page-size="10"
              background
              layout="total, sizes, prev, pager, next, jumper"
              :total="dataPage.totalCount"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
            />
          </div>
          <!-- 分页组件结束 -->
        </el-card>
      </el-col>


    </el-row>


    <!--
         添加对话框
      -->
    <el-dialog :visible.sync="addDialog" v-if="addDialog" :title="title" center="center" style="width: 85%">
      <sys-user-add  @after="doSearch" @close="closeDialog"></sys-user-add>
    </el-dialog>

    <el-dialog :visible.sync="updateDialog" v-if="updateDialog" :title="title" center="center" style="width: 85%">
      <sys-user-update  :active-id="activeId"  @after="doSearch" @close="closeDialog"></sys-user-update>
    </el-dialog>


    <el-dialog :visible.sync="userInfoDialog" v-if="userInfoDialog" :title="title" center="center" >
      <sys-user-info  :active-id="activeId"   @close="closeDialog"></sys-user-info>
    </el-dialog>


    <el-dialog :visible.sync="selectRoleDialog" v-if="selectRoleDialog" :title="title" center="center" >
      <sys-user-select-role  :active-id="activeId"   @close="closeDialog"></sys-user-select-role>
    </el-dialog>

    <el-dialog :visible.sync="testDialog" v-if="testDialog" :title="title" center="center" >
      <sys-user-test ></sys-user-test>
    </el-dialog>


  </div>
</template>

<script>
import sysUserApi from "@/api/system/sysUser"
import sysDictDataApi  from "@/api/system/sysDictData"
import  sysDeptApi from "@/api/system/sysDept"
import SysUserLeftTree  from "./sys-user-left-tree"
import sysRoleApi from "@/api/system/sysRole"
import  SysUserAdd from "./sys-user-add"
import  SysUserUpdate  from "./sys-user-update"
import   SysUserInfo from "./sys-user-info"
import  SysUserSelectRole from "./sys-user-select-role"
import  SysUserTest from "./sys-user-test"
import  sysMenuApi from "@/api/system/sysMenu"
export default {
  name: "sys-user-list",
  components:{
    SysUserLeftTree,
    SysUserAdd,
    SysUserUpdate,
    SysUserInfo,
    SysUserSelectRole,
    SysUserTest
  },
  created() {
    this.loadDictDataByDictType()
    this.loadDictDataSexByDictType()
    this.loadAllDeptList()
    this.doSearch()
    sysMenuApi.getRouters().then(res=>{
      console.log(res.data)
    })
  },
  data() {
    return {
      page: {
        pageNumber: 1,
        pageSize: 10,
        params: {
          userName: undefined,
          nickName: undefined,
          status: undefined,
          deptId:undefined
        }
      },
      dataPage: {
        list: [],
        totalCount: 0,
        totalPage: 0
      },
      statusOptions:[],
      sexOptions:[],
      deptList:[],
      addDialog:false,
      updateDialog:false,
      userInfoDialog:false,
      selectRoleDialog:false,
      testDialog:false,
      title:undefined,
      activeId:undefined
    }
  },
  methods: {
    loadAllDeptList(){
      sysDeptApi.getAllDeptList().then(res=>{
        this.deptList= res.data
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
    doSearch(deptId) {
      if(deptId !=null && deptId !=undefined){
        this.page.params.deptId=deptId
      }
      sysUserApi.getByPage(this.page).then(res => {
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
          userName: undefined,
          nickName: undefined,
          status: undefined,
          deptId: undefined
        }
      }
    },
    handleSizeChange(pageSize){
      this.page.pageSize=pageSize
      this.doSearch()
    },
    handleCurrentChange(pageNumber){
      this.page.pageNumber=pageNumber
      this.doSearch()
    },
    deleteById(row){
      this.$confirm("确定删除【" + row.userName + "】记录?", {
        confirmButtonText: "确定",
        cancelButtonText: "取消"
      }).then(res => {
        sysUserApi.delete(row.userId).then(res=>{
          this.$message.success(res.msg)
          this.doSearch()
        })
      })
    },
    toAddDialog(){
      this.addDialog=true
      this.title="添加用户"
    },
    closeDialog(){
      this.addDialog=false
      this.updateDialog=false
      this.userInfoDialog=false
      this.selectRoleDialog=false
      this.title=undefined
    },
    toUpdateDialog(row){
      this.updateDialog=true
      this.title="添加用户"
      this.activeId=row.userId
    },
    toUserInfo(row){
      this.userInfoDialog=true
      this.title="用户【"+row.userName+"】详情"
      this.activeId = row.userId
    },
    toSelectRoleDialog(row){
      this.selectRoleDialog=true
      this.title="用户【"+row.userName+"】角色分配"
      this.activeId = row.userId
    },
    toGpt(){
      this.testDialog=true
      this.title="testGpt"
    }


  }
}
</script>

<style scoped>

</style>
