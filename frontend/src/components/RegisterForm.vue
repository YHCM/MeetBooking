<template>
  <div class="register-form">
    <div class="form-header">
      <span v-if="showLoginLink"> <RouterLink to="/login">❮去登陆</RouterLink> 已有账号？ </span>
      <h3>注册新用户</h3>
    </div>
    <el-form :model="registerRequest" @keyup.enter.native="register">
      <el-form-item>
        <el-input placeholder="用户名" v-model="registerRequest.username" />
      </el-form-item>
      <el-form-item>
        <el-input
          placeholder="密码"
          type="password"
          show-password
          v-model="registerRequest.password"
        />
      </el-form-item>
      <el-form-item>
        <el-input placeholder="确认密码" type="password" show-password v-model="confirmPassword" />
      </el-form-item>
      <el-form-item>
        <el-input placeholder="公司名称" v-model="registerRequest.companyName" />
      </el-form-item>
      <el-form-item>
        <el-input placeholder="电话号码" v-model="registerRequest.phoneNumber" />
      </el-form-item>
      <el-form-item>
        <el-button class="submit-button" type="primary" @click="register">注册</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
const props = defineProps({
  showLoginLink: {
    type: Boolean,
    default: true,
  },
})

const emit = defineEmits(['register-success', 'register-failure'])

const http = useApi()

// 登录的请求体
const registerRequest = ref({
  username: '',
  password: '',
  companyName: '',
  phoneNumber: '',
})

// 确认密码
const confirmPassword = ref('')

// 注册
const register = async () => {
  // 检验两次密码是否一致
  if (registerRequest.value.password !== confirmPassword.value) {
    showMessage('两次输入的密码不一致，请重新输入', 'error')
    return
  }

  // 检验密码强度
  if (!validatePasswordStrength(registerRequest.value.password)) {
    showMessage('密码强度不足，请使用至少8位的字母数字组合', 'error')
    return
  }

  try {
    const result = await http.post('/auth/register', registerRequest.value)
    console.log(result.message)
    handleResponse(result)
  } catch (error) {
    console.error('服务器异常：', error)
    emit('register-failure', error)
  }
}

// 密码强度检验
const validatePasswordStrength = (password) => {
  // 至少 8 位，包括字母和数字，不区分大小写
  const regex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/
  return regex.test(password)
}

const RESPONSE_HANDLERS = {
  200: (message) => {
    showMessage(message, 'success')
    emit('register-success', message)
  },
  500: (message) => {
    showMessage(message, 'error')
    emit('register-failure', message)
  },
  default: (message) => {
    showMessage(message, 'warning')
    emit('register-failure', message)
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
</script>

<style scoped>
@import url('@/assets/style.css');

.register-form {
  padding: 40px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  background-color: white;
}

.form-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
}

.form-header h3 {
  margin: 0;
  font-size: 18px;
  color: #303133;
}

.form-header span {
  color: #757575;
}

.form-header span a {
  color: #ff9843;
}

.submit-button {
  width: 100%;
  margin-top: 10px;
}

@media (max-width: 768px) {
  .login-form {
    padding: 30px;
  }
}
</style>
