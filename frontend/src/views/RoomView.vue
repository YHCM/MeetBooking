<template>
  <div>
    <h3>会议室</h3>

    <!-- 会议室状态显示-->
    <div class="room-status-container">
      <div class="room-status" v-for="room in currentPageData" :key="room.roomId">
        <div class="room-name">{{ room.roomName }}</div>
        <div class="room-state">
          <el-tag :type="room.roomStatus ? 'success' : 'danger'">
            {{ room.roomStatus ? '可用' : '不可用' }}
          </el-tag>
        </div>
        <el-button
          size="small"
          :type="room.roomStatus ? 'danger' : 'success'"
          :loading="loading"
          @click="toggleRoomStatus(room.roomId)"
        >
          {{ room.roomStatus ? '设为不可用' : '设为可用' }}
        </el-button>
      </div>
    </div>

    <!-- 分页组件 -->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pagination.currentPage"
      :page-sizes="[10, 15, 20, 25]"
      :page-size="pagination.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pagination.total"
      style="margin-top: 20px; justify-content: flex-end"
    />
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { usePagination } from '@/composables/usePagination'
import { handleResponse } from '@/utils/responseHandler'

const http = useApi()
const rooms = ref([])
const loading = ref(false)

const { pagination, handleCurrentChange, handleSizeChange, updateTotal, getCurrentPageData } =
  usePagination()

// 获取会议室列表
const getRooms = async () => {
  loading.value = true
  try {
    const response = await http.get('/rooms')
    rooms.value = response.data
    updateTotal(rooms.value.length)
  } catch (error) {
    console.error('服务器异常：', error)
    ElMessage.error('服务器异常')
  } finally {
    loading.value = false
  }
}

const currentPageData = computed(() => getCurrentPageData(rooms.value))

// 切换会议室状态
const toggleRoomStatus = async (roomId) => {
  loading.value = true
  try {
    const response = await http.patch(`/rooms/${roomId}/status`)
    handleResponse(response)

    if (response.data) {
      getRooms()
    }
  } catch (error) {
    console.error('服务器异常：', error)
    ElMessage.error('服务器异常')
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  getRooms()
})
</script>

<style scoped>
.room-status-container {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  justify-content: center;
}

.room-status {
  flex: 0 0 calc(20% - 20px);   /* 每行 5 个 */
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  width: 160px;
  padding: 10px;
  border: 1px solid #2f2d2d;
  border-radius: 5px;
  background-color: #9bcde1;
  box-sizing: border-box;
}

.room-name {
  font-weight: bold;
  margin-bottom: 10px;
}

.room-state {
  margin-bottom: 10px;
}

.el-button {
  margin-top: 10px;
}
</style>
