<template>
  <div>
    <el-card style="margin-top: 5px">
      <el-form :model="page" :inline="true">
        <el-form-item>
          <el-input v-model="page.params.noticeTitle" placeholder="通知标题" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-select v-model="page.params.noticeType" clearable placeholder="选择通知类型">
            <el-option v-for="dictData in noticeOptions" :label="dictData.dictLabel"
                       :value="dictData.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-select v-model="page.params.status" clearable placeholder="通知状态">
            <el-option v-for="dictData in statusOptions" :label="dictData.dictLabel"
                       :value="dictData.dictValue"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-date-picker
            v-model="page.params.startTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="开始日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-date-picker
            v-model="page.params.endTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="结束日期">
          </el-date-picker>
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
        <el-table-column prop="noticeId" label="公告编码" align="center">
        </el-table-column>
        <el-table-column prop="noticeTitle" label="标题" align="center">
        </el-table-column>
        <el-table-column label="公告类型" align="center">
          <template slot-scope="{row}">
            <el-tag v-for="temp in noticeOptions" v-if="temp.dictValue==row.noticeType" :type="temp.dictSkin">
              {{ temp.dictLabel }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="noticeContent" label="内容" align="center">
        </el-table-column>


        <el-table-column prop="status" label="状态" align="center">
          <template slot-scope="{row}">
            <dict-tag :value="row.status" :data-list="statusOptions"></dict-tag>
          </template>
        </el-table-column>

        <el-table-column prop="createBy" label="创建人" align="center">
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" align="center">
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
                  <el-button type="text" icon="el-icon-delete" @click="toDeleteSysNotice(row)">删除</el-button>
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
      <sys-notice-add @after="doSearch" @close="closeDialog"></sys-notice-add>
    </el-dialog>

    <!--
      添加对话框
   -->
    <el-dialog :visible.sync="updateDialog" v-if="updateDialog" :title="title" center="center" style="width: 85%">
      <sys-notice-update :active-id="activeId" @after="doSearch" @close="closeDialog"></sys-notice-update>
    </el-dialog>


  </div>
</template>

<script>
import sysDictDataApi from "@/api/system/sysDictData"
import sysNoticeApi from "@/api/system/sysNotice"
import SysNoticeAdd from "./sys-notice-add"
import SysNoticeUpdate from "./sys-notice-update"
import sysRoleApi from "@/api/system/sysRole";

export default {
  name: "sys-notice-list",
  components: {
    SysNoticeAdd,
    SysNoticeUpdate
  },
  data() {
    return {
      noticeOptions: [],
      statusOptions: [],
      page: {
        pageNumber: 1,
        pageSize: 10,
        params: {
          noticeTitle: undefined,
          status: undefined,
          noticeType: undefined,
          startTime: undefined,
          endTime: undefined
        }
      },
      dataPage: {
        list: [],
        totalCount: 0,
        totalPage: 0
      },
      addDialog: false,
      title: undefined,
      updateDialog: false,
      activeId: undefined
    }
  },
  created() {
    sysDictDataApi.getDictDataByDictType("sys_notice_type").then(res => {
      this.noticeOptions = res.data
    })

    sysDictDataApi.getDictDataByDictType("sys_status_type").then(res => {
      this.statusOptions = res.data
    })

    this.doSearch()
  },
  methods: {
    doSearch() {
      sysNoticeApi.getByPage(this.page).then(res => {
        this.dataPage.list = res.data.list
        this.dataPage.totalCount = res.data.totalCount
        this.dataPage.totalPage = res.data.totalPage
      })
    },
    toUpdateDialog(row) {
      this.updateDialog = true
      this.title = "修改【" + row.noticeTitle + "】公告"
      this.activeId = row.noticeId
    },
    toDeleteSysNotice(row) {
      this.$confirm("确定删除【" + row.noticeTitle + "】记录?", {
        confirmButtonText: "确定",
        cancelButtonText: "取消"
      }).then(res => {
        sysNoticeApi.delete(row.noticeId).then(res=>{
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
    doReset() {
      this.page = {
        pageNumber: 1,
        pageSize: 10,
        params: {
          noticeTitle: undefined,
          status: undefined,
          noticeType: undefined,
          startTime: undefined,
          endTime: undefined
        }
      }
    },
    doAddDialog() {
      this.addDialog = true
      this.title = "添加公告"

    },
    closeDialog() {
      this.addDialog = false
      this.updateDialog=false
      this.title = undefined
    }
  }
}
</script>

<style scoped>

</style>
