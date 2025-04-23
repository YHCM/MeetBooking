<template>
  <div class="profile-container">
    <h2>个人中心</h2>
    <el-form :model="userInfo" label-width="120px" class="profile-form">
      <!-- 用户名 -->
      <el-form-item label="用户名">
        <el-input v-model="userInfo.username" disabled />
      </el-form-item>

      <!-- 公司名称 -->
      <el-form-item label="公司名称">
        <el-input v-model="userInfo.companyName" />
      </el-form-item>

      <!-- 电话号码 -->
      <el-form-item label="电话号码">
        <el-input v-model="userInfo.phoneNumber" />
      </el-form-item>

      <!-- 原密码 -->
      <el-form-item label="原密码">
        <el-input v-model="oldPassword" type="password" placeholder="请输入原密码" />
      </el-form-item>

      <!-- 新密码 -->
      <el-form-item label="新密码">
        <el-input v-model="newPassword" type="password" placeholder="请输入新密码" />
      </el-form-item>

      <!-- 确认密码 -->
      <el-form-item label="确认密码">
        <el-input v-model="confirmPassword" type="password" placeholder="请确认新密码" />
      </el-form-item>

      <!-- 保存按钮 -->
      <el-form-item>
        <el-button type="primary" @click="updateProfile">保存修改</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()
const userInfo = ref({ ...userStore.userInfo }) // 获取当前用户信息
const oldPassword = ref('') // 原密码
const newPassword = ref('') // 新密码
const confirmPassword = ref('') // 确认密码

// 更新用户信息
const updateProfile = async () => {
  if (newPassword.value !== confirmPassword.value) {
    ElMessage.error('新密码和确认密码不一致')
    return
  }

  try {
    const response = await userStore.updateUserInfo(
      userInfo.value,
      oldPassword.value,
      newPassword.value,
    )
    if (response.code === 200) {
      ElMessage.success('信息更新成功')
    } else {
      ElMessage.error(response.message || '更新失败')
    }
  } catch (error) {
    ElMessage.error('更新失败')
  }
}
</script>

<style scoped>
.profile-container {
  width: 60%;
  margin: 20px auto;
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
}

h2 {
  text-align: center;
  font-size: 24px;
  margin-bottom: 20px;
  color: #333;
}

.profile-form {
  max-width: 600px;
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 0 12px rgba(0, 0, 0, 0.05);
}

.el-form-item {
  margin-bottom: 20px;
}

.el-input {
  background-color: #f6f6f6;
  border-radius: 5px;
  border: 1px solid #dcdfe6;
}

.el-input:hover {
  border-color: #409eff;
}

.el-input[type='password'] {
  width: 100%;
}

.el-button {
  width: 100%;
  background-color: #409eff;
  color: white;
  border-radius: 5px;
}

.el-button:hover {
  background-color: #66b1ff;
  border-color: #66b1ff;
}

.el-button.primary {
  background-color: #409eff;
  border-color: #409eff;
  transition: background-color 0.3s ease;
}

.el-button.primary:hover {
  background-color: #66b1ff;
  border-color: #66b1ff;
}
</style>
