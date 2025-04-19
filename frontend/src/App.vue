<template>
  <el-config-provider :locale="zhCn">
    <MainView />
  </el-config-provider>
</template>

<script setup>
import MainView from '@/views/MainView.vue'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
</script>
