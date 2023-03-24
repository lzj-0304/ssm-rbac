<template>
  <div class="dashboard-container">
     <h1 align="center">欢迎使用动力节点权限管理系统</h1>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  components: { },
  data() {
    return {
      currentRole: 'adminDashboard'
    }
  },
  computed: {
    ...mapGetters([
      'roles'
    ])
  },
  created() {
    /*if (!this.roles.includes('admin')) {
      this.currentRole = 'editorDashboard'
    }*/
  }
}
</script>
