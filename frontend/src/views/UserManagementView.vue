<template>
  <div>
    <h3>用户管理</h3>
  </div>
  <div>
    <el-table :data="currentPageData" v-loading="loading" style="width: 100%" border stripe>
      <el-table-column prop="userId" label="用户 ID" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="role" label="用户身份">
        <template #default="{ row }">
          {{ roleMap[row.role] || row.role }}
        </template>
      </el-table-column>
      <el-table-column prop="companyName" label="公司名称" />
      <el-table-column prop="phoneNumber" label="电话号码" />
      <el-table-column prop="createdAt" label="创建时间">
        <template #default="{ row }">
          {{ formatDate(row.createdAt) }}
        </template>
      </el-table-column>
      <el-table-column prop="isActive" label="状态" width="120">
        <template #default="{ row }">
          <el-tag :type="row.isActive ? 'success' : 'danger'">
            {{ row.isActive ? '正常' : '冻结' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="processedAt" label="处理时间">
        <template #default="{ row }">
          {{ row.processedAt ? formatDate(row.processedAt) : null }}
        </template>
      </el-table-column>
      <el-table-column prop="processedBy" label="处理人 ID" />
      <el-table-column label="操作" width="120">
        <template #default="{ row }">
          <el-button
            size="small"
            :type="row.isActive ? 'danger' : 'success'"
            @click="toggleUserStatus(row)"
            :loading="loading"
          >
            {{ row.isActive ? '冻结' : '解冻' }}
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
import { handleResponse } from '@/utils/responseHandler'

const http = useApi()
const router = useRouter()
const userStore = useUserStore()
const userList = ref([])
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

// 获取用户数据
const getUserList = async () => {
  // 检查用户权限
  if (!isAdmin.value) return

  loading.value = true
  try {
    const response = await http.get('/users')
    userList.value = response.data
    pagination.value.total = userList.value.length
  } catch (error) {
    console.error('服务器异常：', error)
    ElMessage.error('服务器异常')
  } finally {
    loading.value = false
  }
}

// 角色映射
const roleMap = {
  ROOT: '超级管理员',
  ADMIN: '管理员',
  STAFF: '员工',
  CLIENT: '客户',
}

// 切换用户状态
const toggleUserStatus = async (user) => {
  try {
    user.loading = true
    const response = await http.patch(`/users/${user.userId}/active`)
    console.log(response.message)

    // 处理响应
    handleResponse(response)

    if (!response.data) {
      return
    }

    // 更新数据
    const targetUser = userList.value.find((u) => u.userId === user.userId)
    if (targetUser) {
      targetUser.isActive = !targetUser.isActive
      targetUser.processedAt = formatDate(new Date())
      targetUser.processedBy = userInfo.value.userId
    }
  } catch (error) {
    console.error('服务器异常：', error)
    ElMessage.error('服务器异常')
  } finally {
    user.loading = false
  }
}

// 计算当前页面数据
const currentPageData = computed(() => {
  const start = (pagination.value.currentPage - 1) * pagination.value.pageSize
  const end = start + pagination.value.pageSize
  return userList.value.slice(start, end)
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
  getUserList()
})
</script>

<style scoped>
.el-table {
  margin-top: 20px;
}
</style>
