<template>
  <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" :before-close="handleClose">
    <el-form ref="dataForm" :model="temp" label-position="left" label-width="100px" style="width: 400px; margin-left:50px;">
      <el-form-item label="名称" prop="name">
        <el-input v-model="temp.name" placeholder="请输入" />
      </el-form-item>
      <el-form-item label="上级名称" prop="username">
        <TreeSelect
          ref="deptTreeSelect"
          :value="valueId"
          :props="deptTreeSelectProps"
          :options="deptTreeSelectOptions"
          @getValue="getValue($event)"
        />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="handleClose">
        取消
      </el-button>
      <el-button type="primary" @click="postData">
        确定
      </el-button>
    </div>
  </el-dialog>
</template>

<script>
import waves from '@/directive/waves'
import { userAddOrUpdate } from '@/api/user'
import TreeSelect from '@/components/TreeSelect'
import { getDeptList } from '@/api/dept'
export default {
  name: 'UserAddOrUpdate',
  components: { TreeSelect },
  directives: { waves },
  data() {
    return {
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '添加'
      },
      temp: {
        id: undefined,
        name: undefined,
        preId: undefined,
        preName: undefined
      },
      deptTreeSelectProps: {
        value: 'id',
        label: 'name',
        children: 'children'
      },
      valueId: undefined,
      deptTreeSelectOptions: undefined,
      deptQuery: {
        name: undefined
      }
    }
  },
  methods: {
    resetTemp() {
      this.temp = {
        id: undefined,
        name: undefined,
        preId: undefined,
        preName: undefined
      }
      this.$nextTick(() => {
        this.$refs.deptTreeSelect.clearHandle()
      })
    },
    getDetailsFn() {
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
        this.getTreeDept()
        this.$refs.deptTreeSelect.valueId = this.temp.preId
        this.$refs.deptTreeSelect.valueTitle = this.temp.preName
      })
    },
    handleClose() {
      this.dialogFormVisible = false
      this.resetTemp()
    },
    async postData() {
      await userAddOrUpdate(this.temp)
      this.resetTemp()
      this.$emit('afterDeptAddOrUpdate')
      this.dialogFormVisible = false
      this.$notify({
        title: '成功',
        message: '成功',
        type: 'success',
        duration: 2000
      })
    },
    async getTreeDept() {
      const { data } = await getDeptList(this.deptQuery)
      this.deptTreeSelectOptions = data
    },
    getValue(value) {
      this.valueId = value
      console.log(this.valueId)
    }
  }
}
</script>

<style scoped>

</style>
