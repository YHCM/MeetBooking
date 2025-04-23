import { defineStore } from 'pinia'
import { ref } from 'vue'
import { useRouter } from 'vue-router'

export const useUserStore = defineStore(
  'user',
  () => {
    const router = useRouter()

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
      } catch (error) {
        console.log('服务器异常：', error)
      } finally {
        // 无论实际是否退出成功，清空前端的用户数据，返回首页
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
        // 返回首页
        router.push('/')
      }
    }

    // 更新用户信息
    const updateUserInfo = async (updatedUserInfo, oldPassword, newPassword) => {
      try {
        // 先验证原密码是否正确
        const passwordCheckResponse = await http.post('/', { oldPassword })
        if (passwordCheckResponse.code !== 200) {
          throw new Error('原密码错误')
        }

        // 如果原密码正确，更新用户信息
        const response = await http.put('/users/update', {
          userInfo: updatedUserInfo,
          newPassword, // 新密码
        })
        if (response.code === 200) {
          // 更新本地存储的用户信息
          userInfo.value = { ...userInfo.value, ...updatedUserInfo }
          return response
        } else {
          throw new Error(response.message || '更新失败')
        }
      } catch (error) {
        console.error('更新用户信息失败：', error)
        return { code: 500, message: error.message || '更新失败' }
      }
    }

    return { isLoggedIn, userInfo, getUserInfo, logout, updateUserInfo }
  },
  {
    persist: true,
  },
)
