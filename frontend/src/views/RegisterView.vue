<template>
  <div class="register-container">
    <div class="register-card">
      <!-- 注册表单 -->
      <RegisterForm @register-success="handleRegisterSuccess" class="register-form" />
      <!-- 注册图片 -->
      <div class="register-image">
        <img :src="picture" alt="注册图片" />
      </div>
    </div>
  </div>
</template>

<script setup>
import RegisterForm from '@/components/RegisterForm.vue'
import picture from '@/assets/images/auth-1.png'
import { useRouter } from 'vue-router'

const router = useRouter()

// 注册提交后跳转到首页
const handleRegisterSuccess = () => {
  router.push('/')
}
</script>

<style scoped>
@import url('@/assets/style.css');

.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 40px 20px;
  height: calc(80vh - 80px);
}

.register-card {
  display: flex;
  width: 825px;
  height: 100%;
  max-height: 420px;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.register-image {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f5f7fa;
  padding: 20px;
}

.register-form {
  flex: 0.9;
}

.register-image img {
  width: 100%;
  height: 100%;
  max-height: 400px;
  object-fit: contain;
}

@media (max-width: 768px) {
  .register-card {
    flex-direction: column;
    max-height: none;
  }

  .register-image {
    padding: 20px;
    max-height: 200px;
  }
}
</style>
