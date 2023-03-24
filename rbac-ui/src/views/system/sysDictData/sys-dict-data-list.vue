<template>
  <div>
    <el-card style="margin-top: 5px">
      <el-form :model="page" :inline="true">
        <el-form-item label="标签">
          <el-input v-model="page.params.dictLabel" placeholder="请输入标签" clearable></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="page.params.status" clearable>
<!--            <el-option label="正常" :value="0"></el-option>
            <el-option label="停用" :value="1"></el-option>-->
            <el-option  v-for="dictData in statusOptions" :label="dictData.dictLabel" :value="dictData.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="doSearch"> 搜索</el-button>
          <el-button type="warning" icon="el-icon-refresh" @click="doReset"> 重置</el-button>
          <el-button type="primary" icon="el-icon-plus" @click="toAddDialog"> 添加</el-button>
        </el-form-item>
      </el-form>
    </el-card>


    <el-card style="margin-top: 5px">
      <el-table :data="dataPage.list" width="100%" border stripe>
        <el-table-column prop="dictCode" label="字典编码" align="center">
        </el-table-column>
        <el-table-column prop="dictLabel" label="字典标签" align="center">
        </el-table-column>
        <el-table-column prop="dictValue" label="字典键值" align="center">
        </el-table-column>
        <el-table-column prop="dictSort" label="排序" align="center">
        </el-table-column>
        <el-table-column prop="dictType" label="字典类型" align="center">
        </el-table-column>
        <el-table-column prop="dict" label="字典状态" align="center">
          <template slot-scope="{row}">
            <!--            <el-tag v-if="row.status==0" type="success">启用</el-tag>
                        <el-tag v-else type="error">停用</el-tag>-->
            <!--            <el-tag   v-for="dictData in statusOptions"  v-if="dictData.dictValue == row.status"  :type="dictData.dictSkin"  >{{dictData.dictLabel}}</el-tag>-->
            <dict-tag :value="row.status" :data-list="statusOptions"></dict-tag>
          </template>
        </el-table-column>
        <el-table-column prop="remark" label="备注" align="center">
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
                  <el-button type="text" icon="el-icon-delete" @click="toDeleteSysDictData(row)">删除</el-button>
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
    <el-dialog :visible.sync="addDialog" v-if="addDialog" :title="title" center="center" style="width: 85%">
      <sys-dict-data-add :dict-type="dictType" @after="doSearch" @close="closeDialog"></sys-dict-data-add>
    </el-dialog>


    <!--
       更新对话框
    -->
    <el-dialog :visible.sync="updateDialog" v-if="updateDialog" :title="title" center="center" style="width: 85%">
      <sys-dict-data-update :active-id="activeId" @after="doSearch" @close="closeDialog"></sys-dict-data-update>
    </el-dialog>


  </div>
</template>

<script>
import sysDictDataApi from "@/api/system/sysDictData"
import SysDictDataAdd from "./sys-dict-data-add"
import SysDictDataUpdate from "./sys-dict-data-update"

export default {
  name: "sys-dict-data-list",
  components: {
    SysDictDataAdd,
    SysDictDataUpdate
  },
  created() {
    this.doSearch()
    // 加载 指定类型下的字典数据
    this.loadDictDataByDictType()
  },
  data() {
    return {
      page: {
        pageNumber: 1,
        pageSize: 10,
        params: {
          dictType: undefined,
          status: undefined
        }
      },
      dataPage: {
        list: [],
        totalCount: 0,
        totalPage: 0
      },
      addDialog: false,
      title: undefined,
      dictType: undefined,
      updateDialog: false,
      activeId: undefined,
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
      this.page.params.dictType = this.$route.params.dictType
      sysDictDataApi.getByPage(this.page).then(res => {
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
          dictType: undefined,
          status: undefined
        }
      }
    },
    handlerCurrentChange(pageNumber) {
      this.page.pageNumber = pageNumber
      this.doSearch()
    },
    handlerSizeChange(pageSize) {
      this.page.pageSize = pageSize
      this.doSearch()
    },
    toAddDialog() {
      this.addDialog = true
      this.title = "添加字典数据"
      this.dictType = this.page.params.dictType
    },
    closeDialog() {
      this.addDialog = false
      this.updateDialog = false
      this.title = undefined
    },
    toUpdateDialog(row) {
      this.updateDialog = true
      this.title = "更新字典数据"
      this.activeId = row.dictCode
      console.log("打开更新对话框--->" + this.activeId)
    },
    toDeleteSysDictData(row) {
      this.$confirm("确定删除 【" + row.dictLabel + "】 记录?", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        sysDictDataApi.delete(row.dictCode).then(res => {
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
