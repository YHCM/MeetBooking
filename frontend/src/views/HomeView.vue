<template>
  <div class="home-view">
    <div class="carousel-section">

      <!-- 左侧公告卡片 -->
      <el-card class="notice-card">
        <div class="title-with-line">公告</div>
        <ul>
          <li> 欢迎！</li>
          <li>本次对主页进行了一点修改。</li>
        </ul>
      </el-card>

      <!-- 中间轮播浮窗 -->
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

      <!-- 右侧快速入口卡片 -->
      <el-card class="guide-card">
        <h3>快速入口</h3>
        <ul class="guide-list">
          <li v-for="(guide, index) in guideLinks" :key="index" @click="goToPage(guide.link)">
            <span>{{ guide.title }}</span>
            <span class="arrow">➔</span>
          </li>
        </ul>
      </el-card>

    </div>

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

const newCards = ref([
  {
    title: '设备管理',
    image: newCardImage1,
    link: '/equipments',
  },
  {
    title: '预订会议室',
    image: newCardImage2,
    link: '/reserve',
  },
  {
    title: '会议室',
    image: newCardImage3,
    link: '/rooms',
  },
])

// 快速入口词条
const guideLinks = ref([
  { title: '预订会议厅', link: '/reserve' },
  { title: '查看会议室', link: '/rooms' },
  { title: '设备管理', link: '/equipments' },
  { title: '我的预订', link: '/' },
  { title: '个人中心', link: '/profile' },
  { title: '关于我们', link: '/' },
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

.carousel-section {
  display: flex;
  justify-content: center;
  align-items: flex-start;
  gap: 20px;
  margin: 20px auto;
  max-width: 1200px;
}

.notice-card {
  width: 180px;
  height: 260px;
  padding: 20px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  //justify-content: center;
}

.notice-card h3 {
  font-size: 25px;
  font-weight: bold;
  text-align: center;
  margin-bottom: 20px;
  margin-top: 0;
}

.notice-card ul {
  list-style: none;
  padding: 0;
}

.notice-card li {
  font-size: 14px;
  margin-bottom: 8px;
}

.carousel-wrapper {
  flex: 1;
  max-width: 800px;
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

.guide-card {
  width: 200px;
  height: 260px;
  padding: 20px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.guide-card h3 {
  margin-bottom: 10px;
  font-size: 20px;
  font-weight: bold;
  margin-top: 0;
  //text-align: center;
}

.guide-list {
  list-style: none;
  padding: 0;
}

.guide-list li {
  font-size: 14px;
  margin-bottom: 10px;
  cursor: pointer;
  color: #000000;
  display: flex;
  align-items: center;
  justify-content: space-between;
  transition: all 0.2s ease;
}

.guide-list li:hover {
  text-decoration: underline;
}

.guide-list .arrow {
  font-size: 16px;
  margin-left: 5px;
  transition: transform 0.3s;
}

.guide-list li:hover .arrow {
  transform: translateX(3px); 
}

.title-with-line {
  position: relative;
  text-align: center;
  font-size: 22px;
  font-weight: bold;
  color: #333;
  margin-bottom: 20px;
  margin-top: 0;
}

.title-with-line::before,
.title-with-line::after {
  content: "";
  position: absolute;
  top: 50%;
  width: 30%;
  height: 1px;
  background: #ccc;
}

.title-with-line::before {
  left: 0;
}

.title-with-line::after {
  right: 0;
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
