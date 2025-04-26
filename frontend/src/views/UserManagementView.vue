<template>
  <div>
    <h3>用户管理</h3>
  </div>
  <div style="margin-bottom: 20px">
    <el-button type="primary" @click="showAddDialog('STAFF')">添加员工</el-button>
    <el-button type="primary" @click="showAddDialog('CLIENT')">添加客户</el-button>
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
  <!-- 添加用户对话框 -->
  <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px">
    <el-form :model="newUserForm" :rules="rules" ref="userFormRef" label-width="100px">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="newUserForm.username" />
      </el-form-item>
      <el-form-item label="公司名称" prop="companyName" v-if="newUserForm.role === 'CLIENT'">
        <el-input v-model="newUserForm.companyName" />
      </el-form-item>
      <el-form-item label="电话号码" prop="phoneNumber">
        <el-input v-model="newUserForm.phoneNumber" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="addUser" :loading="addingUser">确认</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { formatDate } from '@/utils/date'
import { useUserStore } from '@/stores/user'
import { handleResponse } from '@/utils/responseHandler'
import { usePagination } from '@/composables/usePagination'

const http = useApi()
const router = useRouter()
const userStore = useUserStore()
const userList = ref([])
const loading = ref(false)
const addingUser = ref(false)
const dialogVisible = ref(false)
const userFormRef = ref(null)

// 用户信息
const userInfo = computed(() => userStore.userInfo)
// 是否是管理员
const isAdmin = computed(() => userInfo.value.role === 'ADMIN')

const { pagination, handleCurrentChange, handleSizeChange, getCurrentPageData, updateTotal } =
  usePagination()

const currentPageData = computed(() => getCurrentPageData(userList.value))

// 新增用户的表单
const newUserForm = ref({
  username: '',
  password: '',
  role: '',
  companyName: '',
  phoneNumber: '',
})

// 表单验证规则
const rules = ref({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' },
  ],
  companyName: [{ required: true, message: '请输入公司名称', trigger: 'blur' }],
  phoneNumber: [
    { required: false, message: '请输入电话号码', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' },
  ],
})

// 对话框标题
const dialogTitle = computed(() => {
  return newUserForm.value.role === 'STAFF' ? '添加员工' : '添加客户'
})

// 显示添加用户对话框
const showAddDialog = (role) => {
  // 重置表单
  newUserForm.value = {
    username: '',
    password: '123456',
    role: role,
    companyName: '',
    phoneNumber: '',
  }

  dialogVisible.value = true
}

// 添加用户
const addUser = async () => {
  try {
    // 表单验证
    await userFormRef.value.validate()
    addingUser.value = true

    const payload = {
      username: newUserForm.value.username,
      password: newUserForm.value.password,
      role: newUserForm.value.role,
      phoneNumber: newUserForm.value.phoneNumber,
    }

    // 如果是客户，添加公司名称
    if (newUserForm.value.role === 'CLIENT') {
      payload.companyName = newUserForm.value.companyName
    }

    const response = await http.post(`/users/${payload.role}`, payload)
    handleResponse(response)

    // 添加成功，刷新列表
    if (response.data) {
      getUserList()
      // 关闭对话框
      dialogVisible.value = false
    }
  } catch (error) {
    console.error('添加用户失败：', error)
    ElMessage.error('添加用户失败')
  } finally {
    addingUser.value = false
  }
}

// 获取用户数据
const getUserList = async () => {
  // 检查用户权限
  if (!isAdmin.value) return

  loading.value = true
  try {
    const response = await http.get('/users')
    userList.value = response.data
    updateTotal(userList.value.length)
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
  if (checkPermission()) getUserList()
})
</script>

<style scoped>
.el-table {
  margin-top: 20px;
}
</style>
