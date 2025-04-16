<template>
  <div class="login-container">
    <div class="login-card">
      <!-- 登录图片 -->
      <div class="login-image">
        <img :src="loginPic" alt="登录图片" />
      </div>
      <!-- 登录表单 -->
      <div class="login-form">
        <div class="form-header">
          <h3>登录会议室</h3>
          <span>没有账号？<RouterLink to="/register">去注册></RouterLink></span>
        </div>
        <el-form :model="loginRequest" label-width="auto">
          <el-form-item>
            <el-input placeholder="用户名" v-model="loginRequest.username" />
          </el-form-item>
          <el-form-item>
            <el-input
              placeholder="密码"
              type="password"
              show-password
              v-model="loginRequest.password"
              @keyup.enter.native="login"
            />
          </el-form-item>
          <el-form-item>
            <el-button class="submit-button" type="primary" @click="login">登录</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script setup>
import loginPic from '@/assets/images/login-pic.png'
import { useRouter } from 'vue-router'

const http = useApi()
const router = useRouter()

// 登录的请求体
const loginRequest = ref({
  username: '',
  password: '',
})

// 登录
const login = async () => {
  try {
    const result = await http.post('/auth/login', loginRequest.value)
    console.log(result.message)
    handleResponse(result)
  } catch (error) {
    console.error('服务器异常：', error)
  }
}

const RESPONSE_HANDLERS = {
  200: (message) => {
    showMessage(message, 'success')
    // 登陆成功后返回首页
    router.push('/')
  },
  500: (message) => showMessage(message, 'error'),
  default: (message) => showMessage(message, 'warning'),
}

const showMessage = (message, type) => {
  ElMessage({
    message,
    type,
    plain: true,
  })
}

// 处理了响应
const handleResponse = (response) => {
  const handler = RESPONSE_HANDLERS[response.code] || RESPONSE_HANDLERS.default
  handler(response.message)
}
</script>

<style scoped>
@import url('@/assets/style.css');

.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 40px 20px;
  height: calc(100% - 80px);
}

.login-card {
  display: flex;
  width: 825px;
  height: 100%;
  max-height: 420px;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.login-image {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f5f7fa;
  padding: 20px;
}

.login-image img {
  width: 100%;
  height: 100%;
  max-height: 400px;
  object-fit: contain;
}

.login-form {
  flex: 1;
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
  .login-card {
    flex-direction: column;
    max-height: none;
  }

  .login-image {
    padding: 20px;
    max-height: 200px;
  }

  .login-form {
    padding: 30px;
  }
}
</style>
