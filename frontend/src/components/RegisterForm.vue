<template>
  <div class="register-form">
    <div class="form-header">
      <span v-if="showLoginLink"> <RouterLink to="/login">❮ 去登陆</RouterLink> 已有账号？ </span>
      <h3>注册新用户</h3>
    </div>
    <el-form
      :model="registerRequest"
      :rules="rules"
      ref="registerForm"
      @keyup.enter.native="register"
    >
      <el-form-item prop="username">
        <el-input placeholder="用户名" v-model="registerRequest.username" />
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          placeholder="密码"
          type="password"
          show-password
          v-model="registerRequest.password"
        />
      </el-form-item>
      <el-form-item prop="confirmPassword">
        <el-input
          placeholder="确认密码"
          type="password"
          show-password
          v-model="registerRequest.confirmPassword"
        />
      </el-form-item>
      <el-form-item prop="companyName">
        <el-input placeholder="公司名称" v-model="registerRequest.companyName" />
      </el-form-item>
      <el-form-item prop="phoneNumber">
        <el-input placeholder="电话号码" v-model="registerRequest.phoneNumber" />
      </el-form-item>
      <el-form-item>
        <el-button class="submit-button" type="primary" @click="register">注册</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { handleResponse } from '@/utils/responseHandler'

const http = useApi()

const props = defineProps({
  showLoginLink: {
    type: Boolean,
    default: true,
  },
})

const emit = defineEmits(['register-success', 'register-failure'])

const registerForm = ref(null)

// 登录的请求体
const registerRequest = ref({
  username: '',
  password: '',
  confirmPassword: '',
  companyName: '',
  phoneNumber: '',
})

// 密码强度检验
const validatePasswordStrength = (_, value, callback) => {
  if (!value) return callback(new Error('请输入密码'))
  const isValid = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/.test(value)
  callback(isValid ? undefined : new Error('密码强度不足，请使用至少8位的字母数字组合'))
}

// 确认密码验证
const validateConfirmPassword = (_, value, callback) => {
  if (!value) return callback(new Error('请再次输入密码'))
  callback(value === registerRequest.value.password ? undefined : new Error('两次输入的密码不一致'))
}

// 验证规则
const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度在3到20个字符之间', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { validator: validatePasswordStrength, trigger: 'blur' },
  ],
  confirmPassword: [
    { required: true, message: '请再次输入密码', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: 'blur' },
  ],
  // 公司名称和电话号码为非必填
  companyName: [{ max: 50, message: '公司名称不能超过50个字符', trigger: 'blur' }],
  phoneNumber: [{ pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }],
}

// 注册
const register = async () => {
  try {
    // 验证表单
    await registerForm.value.validate()

    // 检验两次密码是否一致
    if (registerRequest.value.password !== registerRequest.value.confirmPassword) {
      ElMessage.error('两次输入的密码不一致，请重新输入')
      return
    }

    const response = await http.post('/auth/register', registerRequest.value)
    console.log(response.message)
    handleResponse(response, {
      onSuccess: (message) => {
        emit('register-success', message)
      },
      onError: (message) => {
        emit('register-failure', message)
      },
    })
  } catch (error) {
    console.error('服务器异常：', error)
    ElMessage.error('服务器异常')
    emit('register-failure', error)
  }
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
