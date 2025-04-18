<template>
  <div>
    <h3>用户管理</h3>
  </div>
  <div>
    <el-table :data="currentPageData" v-loading="loading" style="width: 100%" border stripe>
      <el-table-column prop="userId" label="用户 ID" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="role" label="用户身份" />
      <el-table-column prop="companyName" label="公司名称" />
      <el-table-column prop="phoneNumber" label="电话号码" />
      <el-table-column prop="createdAt" label="创建时间" />
      <el-table-column prop="userId" label="用户 ID" />
      <el-table-column prop="isActive" label="状态" />
    </el-table>

    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pagination.currentPage"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pagination.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pagination.total"
      style="margin-top: 20px; justify-content: flex-end"
    />
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'

const http = useApi()
const userList = ref([])
const loading = ref(false)

// 分页参数
const pagination = ref({
  currentPage: 1,
  pageSize: 25,
  total: 0,
})

// 获取用户数据
const getUserList = async () => {
  loading.value = true
  try {
    const response = await http.get('/users')
    userList.value = response.data
    pagination.value.total = userList.value.length
  } catch (error) {
    console.error('服务器异常：', error)
  } finally {
    loading.value = false
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

onMounted(() => {
  getUserList()
})
</script>
