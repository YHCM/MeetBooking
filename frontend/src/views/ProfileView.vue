<template>
  <div class="profile-container">
    <el-card class="profile-card">
      <template #header>
        <div class="card-header">
          <span>个人信息</span>
          <el-button v-if="isClient" type="primary" @click="showEditDialog">编辑信息</el-button>
        </div>
      </template>

      <el-descriptions :column="1" border>
        <el-descriptions-item label="用户名">{{ userInfo.username }}</el-descriptions-item>
        <el-descriptions-item label="用户ID">{{ userInfo.userId }}</el-descriptions-item>
        <el-descriptions-item label="角色">{{ roleMap[userInfo.role] }}</el-descriptions-item>
        <el-descriptions-item label="公司名称">{{ userInfo.companyName }}</el-descriptions-item>
        <el-descriptions-item label="手机号码">{{ userInfo.phoneNumber }}</el-descriptions-item>
        <el-descriptions-item label="注册时间">{{
          formatDate(userInfo.createdAt)
        }}</el-descriptions-item>
      </el-descriptions>

      <div class="change-password-btn">
        <el-button type="warning" @click="showChangePasswordDialog">修改密码</el-button>
      </div>
    </el-card>

    <!-- 编辑信息对话框 -->
    <el-dialog v-model="editDialogVisible" title="编辑个人信息" width="500px">
      <el-form :model="editForm" ref="editFormRef" :rules="editRules" label-width="100px">
        <el-form-item label="用户名称：" prop="username">
          <el-input placeholder="用户名" v-model="editForm.username" />
        </el-form-item>
        <el-form-item label="公司名称：" prop="companyName">
          <el-input placeholder="公司名称" v-model="editForm.companyName" />
        </el-form-item>
        <el-form-item label="电话号码：" prop="phoneNumber">
          <el-input placeholder="电话号码" v-model="editForm.phoneNumber" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="editDialogVisible = false">取消</el-button>
          <el-button @click="updateUserInfo">确认</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 修改密码对话框 -->
    <el-dialog v-model="passwordDialogVisible" title="修改密码" width="500px">
      <el-form
        :model="passwordForm"
        ref="passwordFromRef"
        :rules="passwordRules"
        label-width="120px"
      >
        <el-form-item label="旧的密码：" prop="oldPassword">
          <el-input
            placeholder="旧的密码"
            type="password"
            show-password
            v-model="passwordForm.oldPassword"
          />
        </el-form-item>
        <el-form-item label="新的密码：" prop="newPassword">
          <el-input
            placeholder="新的密码"
            type="password"
            show-password
            v-model="passwordForm.newPassword"
          />
        </el-form-item>
        <el-form-item label="确认密码：" prop="confirmPassword">
          <el-input
            placeholder="确认密码"
            type="password"
            show-password
            v-model="passwordForm.confirmPassword"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="passwordDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="changePassword">确认</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { formatDate } from '@/utils/date'
import { handleResponse } from '@/utils/responseHandler'

const router = useRouter()
const http = useApi()
const userStore = useUserStore()

// 获取用户信息
onMounted(() => {
  if (isLogin.value) {
    userStore.getUserInfo()
  } else {
    ElMessage.warning('请登录')
    router.replace('/login')
  }
})

const userInfo = computed(() => userStore.userInfo)
const isClient = computed(() => userStore.userInfo.role === 'CLIENT')

// 是否登陆
const isLogin = computed(() => userStore.userInfo.userId !== 0)

// 修改信息的对话框相关
const editDialogVisible = ref(false)
const editFormRef = ref(null)
const editForm = ref({
  username: '',
  companyName: '',
  phoneNumber: '',
})

// 编辑信息验证规则
const editRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' },
  ],
  companyName: [
    { required: true, message: '请输入公司名称', trigger: 'blur' },
    { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' },
  ],
  phoneNumber: [
    { required: true, message: '请输入手机号码', trigger: 'blur' },
    {
      pattern: /^1[3-9]\d{9}$/,
      message: '请输入正确的手机号码格式',
      trigger: 'blur',
    },
  ],
}

// 初始化对话框信息
const showEditDialog = () => {
  editForm.value = {
    username: userInfo.value.username,
    companyName: userInfo.value.companyName,
    phoneNumber: userInfo.value.phoneNumber,
  }
  editDialogVisible.value = true
}

// 修改用户信息
const updateUserInfo = async () => {
  // 表单验证
  await editFormRef.value.validate()

  try {
    const response = await http.put('/users', {
      userId: userInfo.value.userId,
      ...editForm.value,
    })
    console.log(response.data)

    // 处理响应
    handleResponse(response)

    if (response.data) {
      // 修改成功，更新 userInfo
      userStore.getUserInfo()
      // 关闭弹窗
      editDialogVisible.value = false
    }
  } catch (error) {
    console.error('服务器异常：', error)
    ElMessage.error('服务器异常')
  }
}

// 修改密码相关
const passwordDialogVisible = ref(false)
const passwordFromRef = ref(null)
const passwordForm = ref({
  oldPassword: '',
  newPassword: '',
  confirmPassword: '',
})

// 密码强度检验
const validatePasswordStrength = (password) => {
  // 至少 8 位，包括字母和数字，不区分大小写
  const regex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/
  return regex.test(password)
}

const showChangePasswordDialog = () => {
  passwordForm.value = {
    oldPassword: '',
    newPassword: '',
    confirmPassword: '',
  }
  passwordDialogVisible.value = true
}

// 修改密码验证规则
const passwordRules = {
  oldPassword: [
    { required: true, message: '请输入旧密码', trigger: 'blur' },
    { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' },
  ],
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    {
      pattern: /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/,
      message: '至少 8 位，包括字母和数字',
      trigger: 'blur',
    },
  ],
  confirmPassword: [{ required: true, message: '请确认密码', trigger: 'blur' }],
}

// 修改密码
const changePassword = async () => {
  // 表单验证
  await passwordFromRef.value.validate()

  // 检查密码是否一致
  if (passwordForm.value.newPassword !== passwordForm.value.confirmPassword) {
    ElMessage.error('两次输入的密码不一致，请重新输入')
    return
  }

  // 检查密码强度
  if (!validatePasswordStrength(passwordForm.value.newPassword)) {
    ElMessage.error('密码强度不足，请使用至少8位的字母数字组合')
    return
  }

  try {
    const response = await http.patch('/users/password', {
      userId: userInfo.value.userId,
      oldPassword: passwordForm.value.oldPassword,
      newPassword: passwordForm.value.newPassword,
    })
    handleResponse(response)
    if (response.data) {
      passwordDialogVisible.value = false
    }
  } catch (error) {
    console.error('服务器异常：', error)
    ElMessage.error('服务器异常')
  }
}

// 角色映射
const roleMap = {
  ROOT: '超级管理员',
  ADMIN: '管理员',
  STAFF: '员工',
  CLIENT: '客户',
}
</script>

<style scoped>
.profile-container {
  max-width: 800px;
  margin: 20px auto;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.change-password-btn {
  margin-top: 20px;
  text-align: center;
}

.el-descriptions {
  margin-top: 20px;
}
</style>
