<template>
  <div class="login-container">
    <div class="login-card">
      <!-- 登录图片 -->
      <div class="login-image">
        <img :src="picture" alt="登录图片" />
      </div>
      <!-- 登录表单 -->
      <LoginForm @login-success="handleLoginSuccess" class="login-form" />
    </div>
  </div>
</template>

<script setup>
import LoginForm from '@/components/LoginForm.vue'
import picture from '@/assets/images/auth-4.png'
import { useRouter } from 'vue-router'

const router = useRouter()

// 登陆成功后跳转到首页
const handleLoginSuccess = () => {
  router.push('/')
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

.login-form {
  flex: 0.9;
}

.login-image img {
  width: 100%;
  height: 100%;
  max-height: 400px;
  object-fit: contain;
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
}
</style>
