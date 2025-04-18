import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
  const isLoggedIn = ref(false)
  // 用户数据，为了方便显示，有默认值
  const userInfo = ref({
    userId: 0,
    username: '未登录用户',
    role: 'CLIENT',
    companyName: '未设置',
    phoneNumber: '未设置',
    createdAt: new Date(),
    isActive: true,
    processedAt: new Date(),
    processedBy: 0,
  })

  const http = useApi()

  const getUserInfo = async () => {
    try {
      const response = await http.get('/users/current')
      isLoggedIn.value = response.data !== null
      if (isLoggedIn.value) {
        const data = response.data
        userInfo.value = {
          // ...userInfo.value,
          // ...response.data
          userId: data.userId || 0,
          username: data.username || '未命名',
          role: data.role || 'client',
          companyName: data.companyName || '未设置',
          phoneNumber: data.phoneNumber || '未设置',
          createdAt: data.createdAt ? new Date(data.createdAt) : new Date(),
          isActive: data.isActive,
          processedAt: data.processedAt ? new Date(data.processedAt) : new Date(),
          processedBy: data.processedBy || 0,
        }
      }
    } catch (error) {
      console.log('服务器异常：', error)
    }
  }

  const logout = async () => {
    try {
      await http.delete('/auth/logout')
      isLoggedIn.value = false
      userInfo.value = {
        userId: 0,
        username: '未登录用户',
        role: 'CLIENT',
        companyName: '未设置',
        phoneNumber: '未设置',
        createdAt: new Date(),
        isActive: true,
        processedAt: new Date(),
        processedBy: 0,
      }
    } catch (error) {
      console.log('服务器异常：', error)
    }
  }

  return { isLoggedIn, userInfo, getUserInfo, logout }
})
