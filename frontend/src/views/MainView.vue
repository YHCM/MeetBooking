<template>
  <div class="main-content">
    <el-container>
      <el-header class="header-container">
        <!-- 顶部导航栏 -->
        <div class="header-wrapper">
          <div class="header-content">
            <el-menu :default-active="activeIndex" mode="horizontal" class="flex-grow">
              <el-menu-item index="0">
                <RouterLink to="/">
                  <img class="logo" src="/logo.png" alt="logo" />
                </RouterLink>
              </el-menu-item>
              <el-menu-item index="1">
                <RouterLink to="/">首页</RouterLink>
              </el-menu-item>
              <el-menu-item index="2">
                <RouterLink to="/about">关于</RouterLink>
              </el-menu-item>
            </el-menu>

            <!-- 点击头像，弹出卡片 -->
            <el-popover
              placement="bottom-end"
              :width="200"
              popper-class="user-card-popover"
              :show-arrow="false"
            >
              <template #reference>
                <el-avatar :size="48" :src="avatar" class="cursor-pointer avatar-hover" />
              </template>

              <!-- 用户卡片 -->
              <div class="user-card">
                <!-- 未登录状态 -->
                <div v-if="!isLoggedIn" class="not-logged-in">
                  <h3>用户未登录</h3>
                  <div class="button-group">
                    <el-button type="success" @click="goToLogin">登陆</el-button>
                    <el-button type="default" @click="goToRegister">注册</el-button>
                  </div>
                </div>

                <!-- 已登录状态 -->
                <div v-else class="logged-in">
                  <div class="user-info">
                    <div class="user-details">
                      <h3>{{ userInfo.username }}</h3>
                      <p>公司名称：{{ userInfo.companyName }}</p>
                      <p>电话号码：{{ userInfo.phoneNumber }}</p>
                    </div>
                  </div>

                  <el-divider />

                  <div class="user-actions">
                    <el-button type="info" @click="goToProfile"> 个人中心 </el-button>
                    <el-button type="primary" @click="logout"> 退出登陆 </el-button>
                  </div>
                </div>
              </div>
            </el-popover>
          </div>
        </div>
      </el-header>
      <el-main class="main-wrapper">
        <RouterView />
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import avatar from '@/assets/images/avatar.svg'
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()

// 默认选择首页
const activeIndex = ref('1')
// 登陆状态
const isLoggedIn = computed(() => userStore.isLoggedIn)
// 用户信息
const userInfo = computed(() => userStore.userInfo)

// 导航方法
const goToLogin = () => {
  router.push('/login')
}
const goToRegister = () => {
  router.push('/register')
}
const goToProfile = () => {
  // router.push('/profile')
}

// 退出登陆
const logout = async () => {
  await userStore.logout()
}

// 挂载登陆检查
onMounted(() => {
  userStore.getUserInfo()
})
</script>

<style scoped>
@import url('@/assets/style.css');

.el-container {
  height: 98vh;
}
.header-container {
  padding: 0;
}
.header-wrapper {
  width: 100%;
  padding: 0 20px;
  box-sizing: border-box;
}
.header-content {
  display: flex;
  align-items: center;
  height: 50px;
  width: 75%;
  min-width: 1200px;
  margin: 0 auto;
  gap: 30px;
}
.main-wrapper {
  padding: 0 20px;
  width: 75%;
  min-width: 1200px;
  margin: 0 auto;
}
.flex-grow {
  flex-grow: 1;
}
/* 头像光标样式 */
.cursor-pointer {
  cursor: pointer;
}
.avatar-hover:hover {
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  transform: scale(1.05);
  transition: all 0.3s ease;
}
/* 用户卡片样式 */
.user-card {
  padding: 1px 2px;
}
.not-logged-in {
  text-align: center;
}
.not-logged-in h3 {
  margin: 0;
  margin-bottom: 8px;
}
.button-group {
  display: flex;
  justify-content: center;
  gap: 8px;
}
.logged-in .user-info {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 4px;
}
.user-details h3 {
  margin: 0 0 8px 0;
}
.user-details p {
  margin: 4px 0;
  color: #666;
  font-size: 14px;
}
.user-actions {
  display: flex;
  flex-direction: column;
  gap: 6px;
}
.user-actions .el-button {
  width: 100%;
  margin: 0;
  margin-bottom: 6px;
  box-sizing: border-box;
}
/* 调整顶部导航栏的高度 */
.el-menu--horizontal {
  --el-menu-horizontal-height: 50px;
}
/* 调整 logo 样式 */
.logo {
  width: 75px;
  vertical-align: middle;
}
/* 取消头像背景颜色 */
.el-avatar {
  --el-avatar-bg-color: none;
}
/* 弹出卡片样式 */
.user-card-popover {
  padding: 0 !important;
  border-radius: 8px !important;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15) !important;
}
</style>
