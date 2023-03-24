<template>
  <div>
    <el-input
      placeholder="输入关键字进行过滤"
      v-model="deptName">
    </el-input>

    <el-tree
      class="filter-tree"
      :data="data"
      :props="defaultProps"
      default-expand-all
      :filter-node-method="filterNode"
      ref="tree"
      @node-click="handleNodeClick"
    >
    </el-tree>
  </div>
</template>

<script>
import  sysDeptApi from "@/api/system/sysDept"
import  {handleTree} from "@/utils/powernode";
export default {
  name: "sys-user-left-tree",
  created() {
    this.loadAllDeptList()
  },
  watch: {
    deptName(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods: {
    filterNode(value, data) {
      if (!value) return true;
      return data.deptName.indexOf(value) !== -1;
    },

    loadAllDeptList() {
      sysDeptApi.getAllDeptList().then(res=>{
          this.data= handleTree(res.data,"deptId")
      })
    },
    handleNodeClick(data){
       // data.deptId
      this.$emit("reloadData",data.deptId)
    }


  },
  data(){
    return {
      deptName:  '',
      data:[],
      defaultProps: {
        children: 'children',
        label: 'deptName'
      }
    }
  }
}
</script>

<style scoped>

</style>
