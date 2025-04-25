<template>
  <div class="home-view">
    <!-- 轮播浮窗 -->
    <el-carousel trigger="click" :interval="5000" arrow="always" class="carousel-wrapper">
      <el-carousel-item
        v-for="(item, index) in carouselItems"
        :key="index"
        @click="goToPage(item.link)"
      >
        <el-card
          class="carousel-card"
          :body-style="{ padding: '20px', width: '70%', height: '300px' }"
        >
          <div class="carousel-card-content">
            <div class="carousel-card-text">
              <h3>{{ item.title }}</h3>
              <p>{{ item.subtitle }}</p>
            </div>
            <img :src="item.image" alt="banner" class="carousel-card-image" />
          </div>
        </el-card>
      </el-carousel-item>
    </el-carousel>

    <!-- 功能展示区 -->
    <div class="new-cards">
      <h2>请选择服务</h2>
      <div class="card-container">
        <el-card
          v-for="(card, index) in newCards"
          :key="index"
          class="new-card"
          @click="goToPage(card.link)"
        >
          <div class="new-card-content">
            <img :src="card.image" alt="card" class="new-card-image" />
            <div class="new-card-text">
              <h3>{{ card.title }}</h3>
            </div>
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

import authImage1 from '@/assets/images/auth-1.png'
import authImage2 from '@/assets/images/auth-2.png'
import authImage3 from '@/assets/images/auth-3.png'
import newCardImage1 from '@/assets/images/auth-1.png'
import newCardImage2 from '@/assets/images/auth-1.png'
import newCardImage3 from '@/assets/images/auth-1.png'

// 轮播项的数据
const carouselItems = ref([
  {
    title: '会议室预订',
    subtitle: '',
    image: authImage1,
    link: '/',
  },
  {
    title: '会议室管理',
    subtitle: '',
    image: authImage2,
    link: '/',
  },
  {
    title: '设备管理',
    subtitle: '',
    image: authImage3,
    link: '/',
  },
])

// 功能卡片,后续再改
const newCards = ref([
  {
    title: '设备管理',
    image: newCardImage1,
    link: '/equipments',
  },
  {
    title: '预订会议室',
    image: newCardImage2,
    link: '/',
  },
  {
    title: '会议室',
    image: newCardImage3,
    link: '/rooms',
  },
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

.new-cards {
  margin-top: 40px;
}

.card-container {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
}

.new-card {
  width: 30%;
  cursor: pointer;
  box-shadow: 0 4px 10px rgb(0, 0, 0);
  border-radius: 10px;
  overflow: hidden;
}

.new-card-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 20px;
}

.new-card-image {
  width: 100%;
  max-width: 150px;
  height: auto;
  border-radius: 8px;
}

.new-card-text {
  text-align: center;
  margin-top: 10px;
}

.new-card-text h3 {
  font-size: 18px;
  font-weight: bold;
}
</style>
