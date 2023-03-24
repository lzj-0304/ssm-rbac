<template>
  <div>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="name" label="姓名"></el-table-column>
      <el-table-column prop="age" label="年龄"></el-table-column>
      <el-table-column prop="gender" label="性别"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="primary" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :visible.sync="dialogVisible" title="编辑信息">
      <el-form :model="form" label-width="80px" ref="form">
        <el-form-item label="姓名" prop="name" :rules="[{ required: true, message: '请输入姓名' }]">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="年龄" prop="age" :rules="[{ required: true, message: '请输入年龄' }]">
          <el-input v-model="form.age"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender" :rules="[{ required: true, message: '请选择性别' }]">
          <el-radio-group v-model="form.gender">
            <el-radio label="male">男</el-radio>
            <el-radio label="female">女</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleSubmit">确 定</el-button>
      </div>
    </el-dialog>

    <el-button type="primary" @click="handleAdd">新增</el-button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tableData: [],
      dialogVisible: false,
      form: {
        name: '',
        age: '',
        gender: ''
      }
    }
  },
  mounted() {
    this.getTableData()
  },
  methods: {
    getTableData() {
      // 从后台 API 获取数据并赋值给 tableData
    },
    handleAdd() {
      this.dialogVisible = true
    },
    handleEdit(index, row) {
      this.form = { ...row }
      this.dialogVisible = true
    },
    handleDelete(index, row) {
      // 提交删除请求到后台 API，并删除对应行数据
    },
    handleSubmit() {
      // 提交表单数据到后台 API，并更新 tableData
      this.dialogVisible = false
      this.$refs.form.resetFields()
    }
  }
}
</script>
