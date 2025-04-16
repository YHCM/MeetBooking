<script setup>
import { ref } from 'vue'

// 默认选择首页
const activeIndex = ref('1')
</script>

<template>
  <div class="main-content">
    <el-container>
      <el-header>
        <!-- 顶部导航栏 -->
        <el-menu :default-active="activeIndex" mode="horizontal">
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
          <el-menu-item index="3">
            <RouterLink to="/login">登陆</RouterLink>
          </el-menu-item>
          <el-menu-item index="4">
            <RouterLink to="/register">注册</RouterLink>
          </el-menu-item>
        </el-menu>
      </el-header>
      <el-main>
        <RouterView />
      </el-main>
    </el-container>
  </div>
</template>

<style scoped>
@import url('@/assets/style.css');

.el-container {
  height: 98vh;
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
</style>
