<template>
  <div class="app-container">
    <el-card shadow="always" class="cardSearchForm">
      <el-form label-position="right" :model="listQuery" label-width="80px">
        <el-row :gutter="10">
          <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="6">
            <el-form-item label="姓名">
              <el-input v-model="listQuery.name" placeholder="请输入" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="6">
            <el-form-item label="用户名">
              <el-input v-model="listQuery.username" placeholder="请输入" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="6">
            <el-form-item label="性别">
              <el-select v-model="listQuery.sex" placeholder="请选择">
                <el-option label="男" value="1" />
                <el-option label="女" value="2" />
              </el-select>
            </el-form-item>
          </el-col>
          <transition-group name="el-fade-in-linear">
            <el-col v-show="showBut" key="telephone" :xs="24" :sm="12" :md="8" :lg="6" :xl="6">
              <el-form-item label="电话">
                <el-input v-model="listQuery.telephone" placeholder="请输入" />
              </el-form-item>
            </el-col>
            <el-col v-show="showBut" key="sex" :xs="24" :sm="12" :md="8" :lg="6" :xl="6">
              <el-form-item label="状态">
                <el-select v-model="listQuery.sex" placeholder="请选择">
                  <el-option label="正常" value="1" />
                  <el-option label="异常" value="2" />
                </el-select>
              </el-form-item>
            </el-col>
          </transition-group>
          <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="6">
            <el-form-item>
              <el-button @click="onReset">重置</el-button>
              <el-button type="primary" @click="onSearch">查询</el-button>
              <el-button type="text" @click="showMore">{{ showText }}</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-card>
    <el-card shadow="always" class="cardBody">
      <div slot="header">
        <span>用户列表</span>
        <el-popover
          placement="bottom"
          title="列设置"
          width="150"
          trigger="click"
        >
          <el-checkbox v-model="showColName" @change="showColName !== showColName">姓名</el-checkbox>
          <el-checkbox v-model="showColUsername" @change="showColUsername !== showColUsername">用户名</el-checkbox>
          <el-checkbox v-model="showColSex" @change="showColSex !== showColSex">性别</el-checkbox>
          <el-checkbox v-model="showColTelephone" @change="showColTelephone !== showColTelephone">电话</el-checkbox>
          <el-checkbox v-model="showColDeptName" @change="showColDeptName !== showColDeptName">单位</el-checkbox>
          <el-checkbox v-model="showColCreateTime" @change="showColCreateTime !== showColCreateTime">创建时间</el-checkbox>
          <el-checkbox v-model="showColStatus" @change="showColStatus !== showColStatus">状态</el-checkbox>
          <i slot="reference" class="el-icon-setting" style="float: right; margin: 0 5px" />
        </el-popover>
        <el-tooltip effect="dark" content="刷新" placement="top">
          <i class="el-icon-refresh-right" style="float: right;  margin: 0 5px" @click="onRefresh" />
        </el-tooltip>
        <el-button style="float: right; margin: -8px 5px" type="primary" icon="el-icon-plus" @click="onAdd">新建</el-button>

      </div>
      <el-table
        v-loading="listLoading"
        :data="list"
        fit
        highlight-current-row
        style="width: 100%;"
        :header-cell-style="{background:'#eef1f6',color:'#606266'}"
      >
        <empty slot="empty" />

        <el-table-column v-if="showColName" label="姓名" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.name }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="showColUsername" label="用户名" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.username }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="showColSex" label="性别" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.sex |sexFilter }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="showColTelephone" label="电话" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.telephone }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="showColDeptName" label="单位" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.deptName }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="showColCreateTime" label="创建时间" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.createTime }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="showColStatus" label="状态" align="center">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status | statusTagFilter">
              {{ scope.row.status | statusFilter }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button type="text" @click="onEdit(scope.row)">
              修改
            </el-button>
            <el-divider direction="vertical" />
            <el-button type="text" @click="onDelete(scope)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />
    </el-card>
    <userAddOrUpdate ref="userAddOrUpdate" @afterUserAddOrUpdate="afterUserAddOrUpdate" />
  </div>
</template>

<script>
import waves from '@/directive/waves'
import Pagination from '@/components/Pagination'
import { getUserList, userDelete } from '@/api/user'
import empty from '@/components/Empty'
import userAddOrUpdate from './components/UserAddOrUpdate'

export default {
  name: 'SysUser',
  components: { Pagination, empty, userAddOrUpdate },
  directives: { waves },
  filters: {
    statusTagFilter(status) {
      const statusMap = {
        1: 'success',
        2: 'danger'
      }
      return statusMap[status]
    },
    statusFilter(status) {
      const statusMap = {
        1: '正常',
        2: '异常'
      }
      return statusMap[status]
    },
    sexFilter(status) {
      const statusMap = {
        1: '男',
        2: '女'
      }
      return statusMap[status]
    }
  },
  props: {},
  data() {
    return {
      list: undefined,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        name: undefined,
        sex: undefined,
        username: undefined,
        telephone: undefined
      },
      showBut: false,
      showText: '展开',
      showColName: true,
      showColUsername: true,
      showColSex: true,
      showColTelephone: true,
      showColDeptName: true,
      showColCreateTime: true,
      showColStatus: true
    }
  },
  created() {
    this.getList()
  },
  mounted() {

  },
  methods: {
    onReset() {
      this.listQuery.page = 1
      this.listQuery.name = undefined
      this.listQuery.username = undefined
      this.listQuery.telephone = undefined
      this.getList()
    },
    onSearch() {
      this.listQuery.page = 1
      this.getList()
    },
    onAdd() {
      this.$refs.userAddOrUpdate.dialogStatus = 'create'
      this.$refs.userAddOrUpdate.getDetailsFn()
    },
    onEdit(row) {
      console.info(row)
      this.$refs.userAddOrUpdate.temp = row
      this.$refs.userAddOrUpdate.dialogStatus = 'update'
      this.$refs.userAddOrUpdate.getDetailsFn()
    },
    onDelete({ $index, row }) {
      this.$confirm('您确认要删除该信息吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async() => {
        await userDelete({ id: row.id })
        this.list.splice($index, 1)
        this.$message({
          title: '成功',
          message: '删除成功!',
          type: 'success',
          duration: 2000
        })
      }).catch(err => { console.error(err) })
    },
    onRefresh() {
      this.listQuery.page = 1
      this.getList()
    },
    showMore() {
      if (this.showBut) {
        this.showBut = false
        this.showText = '展开'
      } else {
        this.showBut = true
        this.showText = '收起'
      }
    },
    async getList() {
      this.listLoading = true
      const { data, total } = await getUserList(this.listQuery)
      this.list = data
      this.total = total
      this.listLoading = false
    },
    afterUserAddOrUpdate() {
      this.getList()
    }
  }
}
</script>

<style lang="scss">
  .cardSearchForm {
    .el-card__body {
      padding: 18px 18px 0 18px;
      .el-select {
        width: 100%;
      }
    }
  }
  .cardBody {
    margin-top: 10px;
    .el-card__body {
      padding: 0;
      .el-table__empty-text {
        line-height: 20px;
      }
    }
  }
</style>
