<template>
  <div>
    <el-tree
      :data="menuList"
      show-checkbox
      node-key="menuId"
      ref="tree"
      :props="defaultProps"
    >
    </el-tree>

    <div align="center">
      <el-button type="primary" icon="el-icon-plus" @click="toAddRoleMenus"> 权限分配</el-button>
    </div>

  </div>
</template>

<script>
import sysMenuApi from "@/api/system/sysMenu"
import {handleTree} from "@/utils/powernode";
import sysRoleApi from "@/api/system/sysRole"

export default {
  name: "sys-role-select-menu",
  props: {
    activeId: {
      type: Number,
      default: undefined
    }
  },
  watch:{
    activeId:{
      immediate:true,
      handler:function (newVal,oldVal){
        sysRoleApi.getCurrentRoleHasMenuIdsByRoleId(newVal).then(res=>{
          this.$refs.tree.setCheckedKeys(res.data,true)
        })
      }
    }
  },
  created() {
    this.loadAllMenuList()
  },
  data() {
    return {
      menuList: [],
      defaultProps: {
        children: 'children',
        label: 'menuName'
      },
      selectedMenuIds: []
    }
  },
  methods: {
    loadAllMenuList() {
      sysMenuApi.getAllMenuList().then(res => {
        this.menuList = handleTree(res.data, "menuId")
      })
    },
    toAddRoleMenus() {
      // 获取选中的菜单id
      this.selectedMenuIds = this.$refs.tree.getCheckedKeys();
      //获取半选的菜单id this.$refs.tree.getHalfCheckedKeys()
      this.selectedMenuIds.push(...this.$refs.tree.getHalfCheckedKeys())
      sysRoleApi.saveRoleMenu(this.activeId, this.selectedMenuIds).then(res => {
        this.$message.success(res.msg)
        this.$emit("after")
        this.$emit("close")
      })
    }
  }
}
</script>

<style scoped>

</style>
