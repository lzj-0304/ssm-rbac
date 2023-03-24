<template>
  <div>
    <el-card style="margin-top: 5px">
      <el-form :model="page" :inline="true" size="small">
        <el-form-item>
          <el-input v-model="page.params.dictName" placeholder="字典名称" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-select v-model="page.params.status" placeholder="请选择状态" clearable>
<!--            <el-option :value="0" label="启用"></el-option>
            <el-option :value="1" label="停用"></el-option>-->
            <el-option  v-for="dictData in statusOptions" :label="dictData.dictLabel" :value="dictData.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="small" @click="doSearch" icon="el-icon-search">搜索</el-button>
          <el-button type="warning" size="small" @click="doReset" icon="el-icon-refresh">重置</el-button>
          <el-button type="success" @click="toAddDialog" icon="el-icon-plus">添加</el-button>
          <el-button type="primary" @click="refreshCache" icon="el-icon-refresh">同步缓存</el-button>
        </el-form-item>
      </el-form>
    </el-card>


    <el-card style="margin-top: 5px">
      <el-table :data="dataPage.list" border stripe>
        <el-table-column prop="dictId" label="编号" align="center">
        </el-table-column>
        <el-table-column prop="dictName" label="字典名称" align="center"></el-table-column>
        <el-table-column prop="dictType" label="字典类型" align="center">

            <!--
               通过插槽 配置路由地址
            -->
            <template slot-scope="{row}">
                <router-link :to="'/system/dictData/index/'+row.dictType">{{row.dictType}}</router-link>
            </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" align="center">
          <template slot-scope="{row}">
<!--            <el-tag v-if="row.status==0" type="success">正常</el-tag>
            <el-tag v-else type="danger">停用</el-tag>-->
<!--              <el-tag   v-for="dictData in statusOptions"  v-if="dictData.dictValue == row.status"  :type="dictData.dictSkin"  >{{dictData.dictLabel}}</el-tag>-->
            <dict-tag :value="row.status" :data-list="statusOptions"></dict-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createBy" label="创建人" align="center"></el-table-column>
        <el-table-column prop="createTime" label="创建时间" align="center"></el-table-column>
        <el-table-column prop="remark" label="备注" align="center"></el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="{row}">
            <!--                <el-link  @click="toUpdate">修改</el-link>
                            <el-link  @click="toDelete">删除</el-link>-->

            <el-dropdown>
                <span class="el-dropdown-link">
                  <el-link type="primary">操作</el-link><i class="el-icon-arrow-down el-icon--right"></i>
                </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>
                  <el-button type="text" icon="el-icon-edit" @click="toUpdate(row)">修改</el-button>
                </el-dropdown-item>
                <el-dropdown-item>
                  <el-button type="text" icon="el-icon-delete" @click="toDelete(row)">删除</el-button>
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
      >

      </el-pagination>


    </el-card>


    <!--
       添加对话框
    -->
    <el-dialog :visible.sync="addDialog" v-if="addDialog" :title="title" center="center" style="width: 80%">
      <sys-dict-type-add @after="doSearch" @close="closeDialog"></sys-dict-type-add>
    </el-dialog>


    <el-dialog :visible.sync="uploadDialog" v-if="uploadDialog" :title="title" center="center" style="width: 80%">
      <sys-dict-type-update :active-id="activeId" @after="doSearch" @close="closeDialog"></sys-dict-type-update>
    </el-dialog>


  </div>

</template>

<script>
import sysDictTypeApi from "@/api/system/sysDictType"
import SysDictTypeAdd from "./sys-dict-type-add"
import SysDictTypeUpdate from "./sys-dict-type-update"
import sysDictDataApi from "@/api/system/sysDictData"
export default {
  name: "sys-dict-type-list",
  components: {
    SysDictTypeAdd,
    SysDictTypeUpdate
  },
  created() {
    // 加载 指定类型下的字典数据
    this.loadDictDataByDictType()
    this.doSearch()
  },
  data() {
    return {
      page: {
        pageNumber: 1,
        pageSize: 10,
        params: {
          dictName: undefined,
          status: undefined
        }
      },
      dataPage: {
        list: [],
        totalCount: 0,
        totalPage: 0
      },
      addDialog: false,
      uploadDialog: false,
      title: "",
      activeId: undefined,
      // 记录状态
      statusOptions:[]
    }
  },
  methods: {
    loadDictDataByDictType(){
      sysDictDataApi.getDictDataByDictType("sys_status_type").then(res=>{
          this.statusOptions = res.data
      })
    },
    doSearch() {
      sysDictTypeApi.getByPage(this.page).then(res => {
        // res  === result
        console.log(res)
        this.dataPage.list = res.data.list
        this.dataPage.totalCount = res.data.totalCount
        this.dataPage.totalPage = res.data.totalPage
        console.log(this.dataPage)
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
    doReset() {
      this.page = {
        pageNumber: 1,
        pageSize: 10
      }
    },
    toAddDialog() {
      this.addDialog = true
      this.title = "添加字典类型"
    },
    toUpdate(row) {
      this.uploadDialog = true
      this.title = "字典类型更新"
      // 给 activeId 赋值 传给子组件
      this.activeId = row.dictId
    },
    toDelete(row) {
      this.$confirm("确定删除【" + row.dictName + "】" + "记录?", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        sysDictTypeApi.delete(row.dictId).then(res => {
          this.$message.success(res.msg)
          this.doSearch()
        })
      })
    },
    closeDialog() {
      this.addDialog = false
      this.uploadDialog = false
      this.title = undefined
    },
    refreshCache(){
      sysDictTypeApi.refreshCache().then(res=>{
         this.$message.success(res.msg)
      })
    }
  }
}
</script>

<style scoped>

</style>
