<template>
  <div>
    <h3>注册管理</h3>
  </div>
  <div>
    <el-table :data="currentPageData" v-loading="loading" style="width: 100%" border stripe>
      <el-table-column prop="requestId" label="请求 ID" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="companyName" label="公司名称" />
      <el-table-column prop="phoneNumber" label="电话号码" />
      <el-table-column prop="createdAt" label="创建时间">
        <template #default="{ row }">
          {{ formatDate(row.createdAt) }}
        </template>
      </el-table-column>
      <el-table-column prop="requestStatus" label="创建时间">
        <template #default="{ row }">
          {{ statusMap[row.requestStatus] || row.requestStatus }}
        </template>
      </el-table-column>
      <el-table-column prop="processedAt" label="处理时间">
        <template #default="{ row }">
          {{ row.processedAt ? formatDate(row.processedAt) : null }}
        </template>
      </el-table-column>
      <el-table-column prop="processedBy" label="处理人 ID" />
      <el-table-column label="操作" width="160">
        <template #default="{ row }">
          <el-button size="small" type="success" :loading="loading" @click="approveRequest(row)">
            同意
          </el-button>
          <el-button size="small" type="danger" :loading="loading" @click="rejectRequest(row)">
            拒绝
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pagination.currentPage"
      :page-sizes="[10, 15, 20, 25]"
      :page-size="pagination.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pagination.total"
      style="margin-top: 20px; justify-content: flex-end"
    />
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { formatDate } from '@/utils/date'
import { useUserStore } from '@/stores/user'

const http = useApi()
const router = useRouter()
const userStore = useUserStore()
const requestList = ref([])
const loading = ref(false)

// 用户信息
const userInfo = computed(() => userStore.userInfo)
// 是否是管理员
const isAdmin = computed(() => userInfo.value.role === 'ADMIN')

// 分页参数
const pagination = ref({
  currentPage: 1,
  pageSize: 15,
  total: 0,
})

// 获取注册请求
const getRequestList = async () => {
  // 检查用户权限
  if (!isAdmin.value) return

  loading.value = true
  try {
    const response = await http.get('/requests')
    requestList.value = response.data
    pagination.value.total = requestList.value.length
  } catch (error) {
    console.error('服务器异常：', error)
    ElMessage.error('服务器异常')
  } finally {
    loading.value = false
  }
}

// 状态映射
const statusMap = {
  PENDING: '待处理',
  APPROVED: '已同意',
  REJECTED: '已拒绝',
}

// 同意用户注册请求
const approveRequest = async (request) => {
  try {
    request.loading = true
    const response = await http.patch(`/requests/${request.requestId}/approved`)
    console.log(response.data)

    // 处理响应
    handleResponse(response)

    if (!response.data) {
      return
    }

    // 更新数据
    const targetRequest = requestList.value.find((r) => r.requestId === request.requestId)
    if (targetRequest) {
      targetRequest.requestStatus = 'APPROVED'
      targetRequest.processedAt = formatDate(new Date())
      targetRequest.processedBy = userInfo.value.userId
    }
  } catch (error) {
    console.error('服务器异常：', error)
    ElMessage.error('服务器异常')
  } finally {
    request.loading = false
  }
}

// 拒绝用户注册请求
const rejectRequest = async (request) => {
  try {
    request.loading = true
    const response = await http.patch(`/requests/${request.requestId}/rejected`)
    console.log(response.data)

    // 处理响应
    handleResponse(response)

    if (!response.data) {
      return
    }

    // 更新数据
    const targetRequest = requestList.value.find((r) => r.requestId === request.requestId)
    if (targetRequest) {
      targetRequest.requestStatus = 'REJECTED'
      targetRequest.processedAt = formatDate(new Date())
      targetRequest.processedBy = userInfo.value.userId
    }
  } catch (error) {
    console.error('服务器异常：', error)
    ElMessage.error('服务器异常')
  } finally {
    request.loading = false
  }
}

const RESPONSE_HANDLERS = {
  200: (message) => {
    showMessage(message, 'success')
  },
  500: (message) => {
    showMessage(message, 'error')
  },
  default: (message) => {
    showMessage(message, 'warning')
  },
}

const showMessage = (message, type) => {
  ElMessage({
    message,
    type,
    plain: true,
  })
}

// 处理响应
const handleResponse = (response) => {
  const handler = RESPONSE_HANDLERS[response.code] || RESPONSE_HANDLERS.default
  handler(response.message)
}

// 计算当前页面数据
const currentPageData = computed(() => {
  const start = (pagination.value.currentPage - 1) * pagination.value.pageSize
  const end = start + pagination.value.pageSize
  return requestList.value.slice(start, end)
})

// 处理分页改变
const handleCurrentChange = (val) => {
  pagination.value.currentPage = val
}

const handleSizeChange = (val) => {
  pagination.value.pageSize = val
  pagination.value.currentPage = 1 // 回到第一页
}

// 检查权限并重定向
const checkPermission = () => {
  if (!isAdmin.value) {
    ElMessage.warning('您没有权限访问此页面')
    router.replace('/') // 重定向到首页或其他有权限的页面
    return false
  }
  return true
}

onMounted(() => {
  checkPermission()
  getRequestList()
})
</script>
