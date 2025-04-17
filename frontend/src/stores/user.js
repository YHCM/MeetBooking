import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
  const isLoggedIn = ref(false)
  const userInfo = ref({
    username: '',
    companyName: '',
    phoneNumber: '',
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
          username: response.data.username,
          companyName: response.data.companyName || '未知',
          phoneNumber: response.data.phoneNumber || '未知',
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
        username: '',
        companyName: '',
        phoneNumber: '',
      }
    } catch (error) {
      console.log('服务器异常：', error)
    }
  }

  return { isLoggedIn, userInfo, getUserInfo, logout }
})
