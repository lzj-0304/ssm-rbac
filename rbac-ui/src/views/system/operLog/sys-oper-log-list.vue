<template>
  <div class="app-container">
    <!-- 搜索表单开始 -->
    <el-card class="search-form">
      <el-form :model="page.params" ref="queryForm" size="small" :inline="true">
        <el-form-item prop="userName">
          <el-input
            v-model="page.params.title"
            placeholder="请输入模块标题"
            clearable
            style="width: 240px;"
            @keyup.enter.native="search"
          />
        </el-form-item>
        <el-form-item prop="status">
          <el-select
            v-model="page.params.businessType"
            placeholder="请选择业务类型"
            clearable
            style="width: 240px"
          >
            <el-option
              v-for="dict in operTypeOptions"
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
          <el-table-column label="编号" align="center" prop="operId"/>
          <el-table-column label="模块标题" align="center" prop="title" :show-overflow-tooltip="true"/>
          <el-table-column label="业务类型" align="center" prop="businessType">
            <template slot-scope="{row}">
              <dict-tag :value="row.businessType" :data-list="operTypeOptions"></dict-tag>
            </template>
          </el-table-column>
          <el-table-column label="请求方式" align="center" prop="requestMethod" width="130" :show-overflow-tooltip="true"/>
          <el-table-column label="操作人员" align="center" prop="operName" :show-overflow-tooltip="true"/>
          <el-table-column label="主机地址" align="center" prop="operIp" :show-overflow-tooltip="true"/>
          <el-table-column label="操作地点" align="center" prop="operLocation" :show-overflow-tooltip="true"/>
          <el-table-column label="操作状态" align="center" prop="status">
            <template slot-scope="{row}">
              <dict-tag :value="row.status" :data-list="commonOptions"></dict-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作时间" align="center" prop="operTime" sortable="custom" width="180"/>
          <el-table-column align="center" label="操作" fixed="right">
            <template slot-scope="{row}">
              <el-dropdown class="handle-button">
              <span class="el-dropdown-link">
                <el-link icon="el-icon-s-operation" type="primary">操作<i class="el-icon-arrow-down el-icon--right"/>
                </el-link>
              </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item>
                    <el-button
                      size="mini"
                      type="text"
                      icon="el-icon-info"
                      @click="toInfo(row)"
                    >查看</el-button>
                  </el-dropdown-item>
                  <el-dropdown-item>
                    <el-button
                      size="mini"
                      type="text"
                      icon="el-icon-delete"
                      @click="toDelete(row)"
                    >删除</el-button>
                  </el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
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

    <!-- 详情弹窗开始 -->
    <el-dialog
      :title="title"
      :visible.sync="infoDialog"
      v-if="infoDialog"
      width="70%"
    >
      <sys-oper-log-info :active-row="activeRow" :oper-type-options="operTypeOptions" :common-options="commonOptions"/>
    </el-dialog>
    <!-- 详情弹窗结束 -->
  </div>
</template>

<script>
import sysOperLogApi from "@/api/system/sysOperLog";
import sysOperLogInfo from "./sys-oper-log-info"
import  sysDictDataApi from "@/api/system/sysDictData"
export default {
  name: "OperLog",
  components:{
    sysOperLogInfo
  },
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
      //操作类型数据
      operTypeOptions: [],
      //状态数据
      commonOptions:[],
      // 控制详情弹窗展示
      infoDialog: false,
      //当前行的数据
      activeRow:undefined
    };
  },
  created() {
    this.getByPage()
    sysDictDataApi.getDictDataByDictType("sys_oper_type").then(res => {
      this.operTypeOptions = res.data;
    })
    sysDictDataApi.getDictDataByDictType("sys_common_status").then(res => {
      this.commonOptions = res.data;
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
      this.ids = selection.map(item => item.operId)
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
      sysOperLogApi.getByPage(this.page).then(res => {
        this.dataPage.list = res.data.list
        this.dataPage.totalCount = res.data.totalCount
        this.dataPage.totalPage = res.data.totalPage
        this.loading = false
      })
    },
    toDelete(row) {
      this.$confirm('此操作会删除【' + row.title + '】该操作数据，是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        sysOperLogApi.deleteById(row.operId).then(res => {
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
        sysOperLogApi.batchDelete(this.ids).then(res => {
          this.$message.success(res.msg)
          this.getByPage()
        })
      }).catch(() => {
      })
    },
    /** 清空按钮操作 */
    handleClean() {
      this.$confirm('是否确认清空所有操作日志数据项?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        sysOperLogApi.cleanOperLog().then(res => {
          this.$message.success(res.msg)
          this.getByPage()
        })
      }).catch(() => {
      })
    },
    //打开显示详情的弹出层
    toInfo(row){
      this.activeRow = row
      this.infoDialog = true
      this.title = "操作详情"
    }
  }
};
</script>
