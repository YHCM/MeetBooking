import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
  const isLoggedIn = ref(false)
  // 用户数据，为了方便显示，有默认值
  const userInfo = ref({
    userId: 0,
    username: '未登录用户',
    role: 'guest',
    companyName: '未设置',
    phoneNumber: '未设置',
    createdAt: new Date(),
  })

  const http = useApi()

  const getUserInfo = async () => {
    try {
      const response = await http.get('/users/current')
      isLoggedIn.value = response.data !== null
      if (isLoggedIn.value) {
        userInfo.value = {
          // ...userInfo.value,
          // ...response.data
          userId: response.data.userId || 0,
          username: response.data.username || '未命名',
          role: response.data.role || 'client',
          companyName: response.data.companyName || '未设置',
          phoneNumber: response.data.phoneNumber || '未设置',
          createdAt: response.data.createdAt ? new Date(response.data.createdAt) : new Date(),
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
        role: 'guest',
        companyName: '未设置',
        phoneNumber: '未设置',
        createdAt: new Date(),
      }
    } catch (error) {
      console.log('服务器异常：', error)
    }
  }

  return { isLoggedIn, userInfo, getUserInfo, logout }
})
