<template>
  <div>
    <el-form :model="form" ref="form" :rules="rules" size="small" label-width="100px">
      <el-form-item label="上级菜单" style="width: 80%">
        <treeselect v-model="form.parentId" :options="menuList" :normalizer="normalizer" :show-count="true"
                    placeholder="上级菜单"/>
      </el-form-item>
      <el-form-item label="菜单类型">
        <el-radio-group v-model="form.menuType">
          <el-radio :label="0">目录</el-radio>
          <el-radio :label="1">菜单</el-radio>
          <el-radio :label="2">按钮</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="菜单图标" v-if="form.menuType !=2">
          <el-popover
            placement="bottom-start"
            width="460"
            trigger="click"
            @show="$refs['iconSelect'].reset()"
          >
            <IconSelect ref="iconSelect" @selected="selected"/>
            <el-input slot="reference" v-model="form.icon" placeholder="点击选择图标" readonly>
              <svg-icon
                v-if="form.icon"
                slot="prefix"
                :icon-class="form.icon"
                class="el-input__icon"
                style="height: 32px;width: 16px;"
              />
              <i v-else slot="prefix" class="el-icon-search el-input__icon"/>
            </el-input>
          </el-popover>
        </el-form-item>


      <el-row>
        <el-col :span="16">
          <el-form-item label="菜单名称" prop="menuName">
            <el-input v-model="form.menuName"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="显示排序" prop="orderNum">
            <el-input-number v-model="form.orderNum" :min="1" :max="100"></el-input-number>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="12">
          <el-form-item prop="path" v-if="form.menuType !=2">
            <span slot="label">
                <el-tooltip content="访问的路由地址，如：`user`，如外网地址需内链访问则以`http(s)://`开头" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                路由地址
              </span>
            <el-input v-model="form.path"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item v-if="form.menuType==1">
            <span slot="label">
                <el-tooltip content="访问的组件路径，如：`system/user/index`，默认在`views`目录下" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                组件路径
              </span>
            <el-input v-model="form.component"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item v-if="form.menuType==2">

            <el-input v-model="form.perms"></el-input>
            <span slot="label">
                <el-tooltip content="控制器中定义的权限字符，如：@PreAuthorize(`@ss.hasPermi('system:user:list')`)" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                权限字符
              </span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="路由参数" v-if="form.menuType==1">
            <span slot="label">
                <el-tooltip content='访问路由的默认传递参数，如：`{"id": 1, "name": "ry"}`' placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                路由参数
              </span>
            <el-input v-model="form.query"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="显示状态" v-if="form.menuType !=2">
            <el-radio-group v-model="form.visible">
              <el-radio v-for="dictData in statusOptions" :label="dictData.dictValue">{{ dictData.dictLabel }}
              </el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="菜单状态" v-if="form.menuType !=2">
            <el-radio-group v-model="form.visible">
              <el-radio v-for="dictData in visiableOptions" :label="dictData.dictValue">{{ dictData.dictLabel }}
              </el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item>
        <el-button type="primary" @click="toUpdateSysMenu">更新</el-button>
        <el-button type="warning" icon="el-icon-refresh" @click="toReset">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import Treeselect from "@riophae/vue-treeselect"
import "@riophae/vue-treeselect/dist/vue-treeselect.css"
import {handleTree} from "@/utils/powernode"
import sysMenuApi from "@/api/system/sysMenu"
import sysDictDataApi from "@/api/system/sysDictData"
import IconSelect from "@/components/IconSelect";

export default {
  name: "sys-menu-update",
  props: {
    activeObj: {
      type: Object,
      default: null
    }
  },
  watch: {
    activeObj: {
      immediate: true,
      handler: function (newVal, oldVal) {
        let temp = newVal
        this.form = JSON.parse(JSON.stringify(temp))
      }
    }
  },
  components: {
    Treeselect,
    IconSelect
  },
  created() {
    this.getMenuMC()
    sysDictDataApi.getDictDataByDictType("sys_status_type").then(res => {
      this.statusOptions = res.data
    })
    sysDictDataApi.getDictDataByDictType("sys_show_hide").then(res => {
      this.visiableOptions = res.data
    })
  },
  data() {
    return {
      form: {
        status: 0,
        visible: 0,
        menuType: 0,
        icon: "404"
      },
      menuList: [],
      statusOptions: [],
      visiableOptions: [],
      rules: {
        menuName: [
          {
            required: true, trigger: "blur", message: "请指定菜单名称!"
          }
        ],
        orderNum: [
          {
            required: true, trigger: "blur", message: "请指定排序!"
          }
        ],
        path: [
          {
            required: true, trigger: "blur", message: "请指定路由地址!"
          }
        ]
      }
    }
  },
  methods: {
    // 选择图标
    selected(name) {
      this.form.icon = name;
    },
    getMenuMC() {
      sysMenuApi.getMenuMC().then(res => {
        let dataList = handleTree(res.data, "menuId")
        let topMenu = [
          {
            "menuId": 0,
            "menuName": "主类目",
            children: []
          }
        ]
        topMenu[0].children = dataList
        this.menuList = topMenu
      })
    },
    normalizer(node) {
      return {
        id: node.menuId,
        label: node.menuName,
        children: node.children
      }
    },
    toUpdateSysMenu() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          sysMenuApi.update(this.form).then(res => {
            this.$message.success(res.msg)
            this.$emit("after")
            this.$emit("close")
            this.toReset()
          })
        }
      })
    },
    toReset() {
      this.form = {
        status: 0,
        visible: 0,
        menuType: 0
      }
    }
  }

}
</script>

<style scoped>

</style>
