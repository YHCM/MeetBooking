<template>
  <h1>主页</h1>
  <div class="home-view">
    <!-- 轮播浮窗 -->
    <el-carousel trigger="click" :interval="5000" arrow="always" class="carousel-wrapper">
      <el-carousel-item v-for="(item, index) in carouselItems" :key="index" @click="goToPage(item.link)">
        <el-card class="carousel-card" :body-style="{ padding: '20px', width: '70%', height: '300px' }">
          <div class="carousel-card-content">
            <!-- 标题居中显示 -->
            <div class="carousel-card-text">
              <h3>{{ item.title }}</h3>
              <p>{{ item.subtitle }}</p>
            </div>
            <!-- 放大图片 -->
            <img :src="item.image" alt="banner" class="carousel-card-image" />
          </div>
        </el-card>
      </el-carousel-item>
    </el-carousel>
  </div>
</template>


<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

import authImage1 from '@/assets/images/auth-1.png'
import authImage2 from '@/assets/images/auth-2.png'
import authImage3 from '@/assets/images/auth-3.png'


// 轮播项的数据
const carouselItems = ref([
  {
    title: "会议厅预订",
    subtitle: "",
    image: authImage1,
    link: "/"
  },
  {
    title: "会议厅管理",
    subtitle: "",
    image: authImage2,
    link: "/"
  },
  {
    title: "设备管理",
    subtitle: "",
    image: authImage3,
    link: "/"
  }
])

const router = useRouter()

// 跳转到不同页面
const goToPage = (link) => {
  router.push(link)
}
</script>

<style scoped>
.home-view {
  padding: 20px;
}

.carousel-wrapper {
  width: 100%;
  max-width: 800px;
  margin: 20px auto;
  position: relative;
}

.carousel-card {
  background-color: #7ebfe6;
  border-radius: 10px;
  overflow: hidden;
  cursor: pointer;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  box-shadow: 0 4px 10px rgb(0, 0, 0);
}

.carousel-card-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.carousel-card-text {
  text-align: center;
  margin-bottom: 10px;
}

.carousel-card-text h3 {
  font-size: 24px;
  font-weight: bold;
}

.carousel-card-text p {
  font-size: 16px;
  color: #666;
}

.carousel-card-image {
  width: 100%;
  max-width: 180px;
  height: auto; 
  border-radius: 8px;
  margin-top: 10px;
}
</style>
