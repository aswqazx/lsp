<template>
  <div class="app-container">
    <el-card shadow="always" class="cardSearchForm">
      <el-form label-position="right" :model="listQuery" label-width="80px">
        <el-row :gutter="10">
          <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="6">
            <el-form-item label="流程名称">
              <el-input v-model="listQuery.lcmc" placeholder="请输入" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="6">
            <el-form-item label="任务名称">
              <el-input v-model="listQuery.dbmc" placeholder="请输入" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="6">
            <el-form-item label="开始时间">
              <el-date-picker
                v-model="searchTime"
                type="daterange"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
              />
            </el-form-item>
          </el-col>
          <transition-group name="el-fade-in-linear">
            <el-col v-show="showBut" key="telephone" :xs="24" :sm="12" :md="8" :lg="6" :xl="6">
              <el-form-item label="电话">
                <el-input v-model="listQuery.telephone" placeholder="请输入" />
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
        <span>我的代办列表</span>
        <el-popover
          placement="bottom"
          title="列设置"
          width="150"
          trigger="click"
        >
          <el-checkbox v-model="showColProcessDefinitionName" @change="showColProcessDefinitionName !== showColProcessDefinitionName">流程名称</el-checkbox>
          <el-checkbox v-model="showColTaskName" @change="showColTaskName !== showColTaskName">任务名称</el-checkbox>
          <el-checkbox v-model="showColStartUserId" @change="showColStartUserId !== showColStartUserId">申请人</el-checkbox>
          <el-checkbox v-model="showColPriority" @change="showColPriority !== showColPriority">优先级</el-checkbox>
          <el-checkbox v-model="showColState" @change="showColState !== showColState">流程状态</el-checkbox>
          <el-checkbox v-model="showColCreateTime" @change="showColCreateTime !== showColCreateTime">开始时间</el-checkbox>
          <el-checkbox v-model="showColEndTime" @change="showColEndTime !== showColEndTime">完成时间</el-checkbox>
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
        row-key="id"
        :header-cell-style="{background:'#eef1f6',color:'#606266'}"
      >
        <empty slot="empty" />

        <el-table-column v-if="showColProcessDefinitionName" label="流程名称" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.processDefinitionName }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="showColTaskName" label="任务名称" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.taskName }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="showColStartUserId" label="申请人" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.startUserId }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="showColPriority" label="优先级" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.priority != null" :type="scope.row.priority | yxjTagFilter">
              {{ scope.row.priority | yxjFilter }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column v-if="showColState" label="流程状态" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.state | statusFilter }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="showColCreateTime" label="开始时间" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.createTime }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="showColEndTime" label="完成时间" align="endTime">
          <template slot-scope="scope">
            <span>{{ scope.row.endTime }}</span>
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
    <lctAddOrUpdate ref="lctAddOrUpdate" @afterLctAddOrUpdate="afterLctAddOrUpdate" />
  </div>
</template>

<script>
import waves from '@/directive/waves'
import Pagination from '@/components/Pagination'
import { getWdybList, lcslsc } from '@/api/gzl'
import empty from '@/components/Empty'
import lctAddOrUpdate from './components/LctAddOrUpdate'

export default {
  name: 'CamundaWdyb',
  components: { Pagination, empty, lctAddOrUpdate },
  directives: { waves },
  filters: {
    statusFilter(status) {
      const statusMap = {
        'ACTIVE': '执行中',
        'SUSPENDED': '已暂停',
        'COMPLETED': '已完成',
        'EXTERNALLY_TERMINATED': '外部终止',
        'INTERNALLY_TERMINATED': '内部终止'
      }
      return statusMap[status]
    },
    yxjTagFilter(status) {
      const statusMap = {
        0: 'success',
        50: 'success',
        100: 'danger'
      }
      return statusMap[status]
    },
    yxjFilter(status) {
      const statusMap = {
        0: '普通',
        50: '普通',
        100: '紧急'
      }
      return statusMap[status]
    }
  },
  props: {},
  data() {
    return {
      searchTime: undefined,
      list: undefined,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        lcmc: undefined,
        dbmc: undefined,
        startTime: undefined,
        endTime: undefined,
        dbr: undefined
      },
      lckgQuery: {
        processDefinitionId: undefined,
        sfkq: true
      },
      deleteQuery: {
        deploymentId: undefined,
        key: undefined
      },
      showBut: false,
      showText: '展开',
      showColProcessDefinitionName: true,
      showColTaskName: true,
      showColStartUserId: true,
      showColPriority: true,
      showColState: true,
      showColCreateTime: true,
      showColEndTime: true
    }
  },
  created() {
    this.listQuery.dbr = this.$store.getters.name
    this.getList()
  },
  mounted() {

  },
  methods: {
    onReset() {
      this.listQuery.page = 1
      this.listQuery.name = undefined
      this.searchTime = undefined
      this.getList()
    },
    onSearch() {
      this.listQuery.startTime = this.searchTime[0]
      this.listQuery.endTime = this.searchTime[1]
      this.listQuery.page = 1
      this.getList()
    },
    onAdd() {
      this.$refs.lctAddOrUpdate.dialogStatus = 'create'
      this.$refs.lctAddOrUpdate.getDetailsFn()
    },
    onEdit(row) {
      this.$refs.lctAddOrUpdate.temp = row
      this.$refs.lctAddOrUpdate.dialogStatus = 'update'
      this.$refs.lctAddOrUpdate.getDetailsFn()
    },
    onDelete({ $index, row }) {
      this.$confirm('您确认要删除该信息吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async() => {
        this.deleteQuery.deploymentId = row.deploymentId
        this.deleteQuery.key = row.key
        await lcslsc(this.deleteQuery)
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
      const { data, total } = await getWdybList(this.listQuery)
      this.list = data
      this.total = total
      this.listLoading = false
    },
    afterLctAddOrUpdate() {
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
