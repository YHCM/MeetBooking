<template>
  <div class="login-form">
    <div class="form-header">
      <h3>登录会议室</h3>
      <span v-if="showRegisterLink">
        没有账号？ <RouterLink to="/register">去注册 ❯</RouterLink>
      </span>
    </div>
    <el-form :model="loginRequest" @keyup.enter.native="login">
      <el-form-item>
        <el-input placeholder="用户名" v-model="loginRequest.username" />
      </el-form-item>
      <el-form-item>
        <el-input
          placeholder="密码"
          type="password"
          show-password
          v-model="loginRequest.password"
        />
      </el-form-item>
      <el-form-item>
        <el-button class="submit-button" type="primary" @click="login">登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useUserStore } from '@/stores/user'

const http = useApi()
const userStore = useUserStore()

const props = defineProps({
  // 是否显示注册链接
  // <LoginForm :show-register-link="false" />
  showRegisterLink: {
    type: Boolean,
    default: true,
  },
})

// 通知父组件登陆状态
// <LoginForm
//   @login-success="handleLoginSuccess"
//   @login-failure="handleLoginFailure"
// />
const emit = defineEmits(['login-success', 'login-failure'])

// 登录的请求体
const loginRequest = ref({
  username: '',
  password: '',
})

// 登录
const login = async () => {
  try {
    const response = await http.post('/auth/login', loginRequest.value)
    console.log(response.message)
    // 如果登陆成功，保存用户信息
    if (response.data) {
      await userStore.getUserInfo()
    }
    handleResponse(response)
  } catch (error) {
    console.error('服务器异常：', error)
    ElMessage.error('服务器异常')
    emit('login-failure', error)
  }
}

const RESPONSE_HANDLERS = {
  200: (message) => {
    showMessage(message, 'success')
    emit('login-success', message)
  },
  500: (message) => {
    showMessage(message, 'error')
    emit('login-failure', message)
  },
  default: (message) => {
    showMessage(message, 'warning')
    emit('login-failure', message)
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

.login-form {
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
