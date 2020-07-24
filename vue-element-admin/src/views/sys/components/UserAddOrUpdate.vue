<template>
  <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" :before-close="handleClose">
    <el-form ref="dataForm" :model="temp" label-position="left" label-width="100px" style="width: 400px; margin-left:50px;">
      <el-form-item label="姓名" prop="name">
        <el-input v-model="temp.name" placeholder="请选择" />
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
export default {
  name: 'UserAddOrUpdate',
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
        name: undefined
      }
    }
  },
  methods: {
    resetTemp() {
      this.temp = {
        id: undefined,
        name: undefined
      }
    },
    getDetailsFn() {
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    handleClose() {
      this.dialogFormVisible = false
      this.resetTemp()
    },
    async postData() {
      await userAddOrUpdate(this.temp)
      this.resetTemp()
      this.$emit('afterUserAddOrUpdate')
      this.dialogFormVisible = false
      this.$notify({
        title: '成功',
        message: '添加成功',
        type: 'success',
        duration: 2000
      })
    }
  }
}
</script>

<style scoped>

</style>
