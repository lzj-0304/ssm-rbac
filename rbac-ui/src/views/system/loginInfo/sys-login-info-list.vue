<template>
  <div class="app-container">
    <!-- 搜索表单开始 -->
    <el-card class="search-form">
      <el-form :model="page.params" ref="queryForm" size="small" :inline="true">
        <el-form-item prop="userName">
          <el-input
            v-model="page.params.userName"
            placeholder="请输入用户名称"
            clearable
            style="width: 240px;"
            @keyup.enter.native="search"
          />
        </el-form-item>
        <el-form-item prop="status">
          <el-select
            v-model="page.params.status"
            placeholder="登录状态"
            clearable
            style="width: 240px"
          >
            <el-option
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-date-picker
            v-model="page.params.dateRange"
            value-format="yyyy-MM-dd HH:mm:ss"
            type="datetimerange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          ></el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search"  size="mini"
                     @click="search">搜索
          </el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="page.params={}">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <!-- 搜索表单结束 -->
    <el-card style="margin-top: 3px">
      <el-button
        type="danger"
        plain
        icon="el-icon-delete"
        size="small"
        :disabled="multiple"
        @click="toBatchDelete"
      >删除
      </el-button>
      <el-button
        type="danger"
        plain
        icon="el-icon-delete"
        size="small"
        @click="handleClean"
      >清空
      </el-button>
      <!-- 菜单组结束 -->
      <!-- 数据表格开始 -->
      <div style="margin-top: 3px">
        <el-table ref="tables" v-loading="loading" border
                  stripe :data="dataPage.list" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center"/>
          <el-table-column label="访问编号" align="center" prop="infoId"/>
          <el-table-column label="用户名称" align="center" prop="userName" :show-overflow-tooltip="true"/>
          <el-table-column label="登录地址" align="center" prop="ipAddr" width="130" :show-overflow-tooltip="true"/>
          <el-table-column label="登录地点" align="center" prop="loginLocation" :show-overflow-tooltip="true"/>
          <el-table-column label="浏览器" align="center" prop="browser" :show-overflow-tooltip="true"/>
          <el-table-column label="操作系统" align="center" prop="os" :show-overflow-tooltip="true"/>
          <el-table-column label="登录状态" align="center" prop="status">
            <template slot-scope="{row}">
              <dict-tag :value="row.status" :data-list="statusOptions"></dict-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作信息" align="center" prop="msg" :show-overflow-tooltip="true"/>
          <el-table-column label="登录日期" align="center" prop="loginTime" sortable="custom" width="180"/>
          <el-table-column align="center" label="操作" fixed="right">
            <template slot-scope="{row}">
              <el-button type="text" @click="toDelete(row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <el-pagination
          class="pagination"
          :current-page="page.pageNumber"
          :page-sizes="[10,20,30,50]"
          :page-size="page.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="dataPage.totalCount"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
      <!-- 数据表格结束 -->
    </el-card>
  </div>
</template>

<script>
import sysLoginInfoApi from "@/api/system/sysLoginInfo";
import sysDictDataApi  from "@/api/system/sysDictData"
export default {
  name: "Logininfor",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非多个禁用
      multiple: true,
      page: {
        pageNumber: 1,
        pageSize: 10,
        params: {}
      },
      dataPage: {
        list: [],
        totalCount: 0,
        totalPage: 0
      },
      //状态数据
      statusOptions: []
    };
  },
  created() {
    this.getByPage()
    sysDictDataApi.getDictDataByDictType("sys_login_status").then(res => {
      this.statusOptions = res.data;
    })
  },
  methods: {
    handleSizeChange(val) {
      this.page.pageSize = val
      this.getByPage()
    },
    handleCurrentChange(val) {
      this.page.pageNumber = val
      this.getByPage()
    },
    /** 多选框选中数据 */
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.infoId)
      this.multiple = !selection.length
    },
    search() {
      this.page.pageNumber = 1
      this.getByPage()
    },
    getByPage() {
      this.loading = true
      if (this.page.params.dateRange){
        this.page.params['startTime']=this.page.params.dateRange[0];
        this.page.params['endTime']=this.page.params.dateRange[1];
      }
      sysLoginInfoApi.getByPage(this.page).then(res => {
        this.dataPage.list = res.data.list
        this.dataPage.totalCount = res.data.totalCount
        this.dataPage.totalPage = res.data.totalPage
        this.loading = false
      })
    },
    toDelete(row) {
      this.$confirm('此操作会删除【' + row.userName + '】该登陆数据，是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        sysLoginInfoApi.deleteById(row.infoId).then(res => {
          this.$message.success(res.msg)
          this.getByPage()
        })
      }).catch(() => {
      })
    },
    toBatchDelete() {
      this.$confirm('此操作会删除选中的所有日志，是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        sysLoginInfoApi.batchDelete(this.ids).then(res => {
          this.$message.success(res.msg)
          this.getByPage()
        })
      }).catch(() => {
      })
    },
    /** 清空按钮操作 */
    handleClean() {
      this.$confirm('是否确认清空所有登录日志数据项?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        sysLoginInfoApi.cleanLogininfor().then(res => {
          this.$message.success(res.msg)
          this.getByPage()
        })
      }).catch(() => {
      })
    }
  }
};
</script>
