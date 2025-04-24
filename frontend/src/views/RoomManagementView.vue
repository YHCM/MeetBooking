<template>
  <div>
    <h3>会议室管理</h3>
  </div>
  <div>
    <el-table :data="currentPageData" v-loading="loading" style="width: 100%" border stripe>
      <el-table-column prop="roomId" label="ID" />
      <el-table-column prop="roomName" label="会议室名称">
        <template #default="{ row }">
          <el-input v-if="row.roomId === editingRoom.roomId" v-model="editingRoom.roomName" />
        </template>
      </el-table-column>
      <el-table-column prop="roomType" label="类型">
        <template #default="{ row }">
          <el-button v-if="row.roomId === editingRoom.roomId" @click="changeEditingRoomType" plain>
            {{ typeMap[editingRoom.roomType] || editingRoom.roomType }}
          </el-button>
          <span v-else>
            {{ typeMap[row.roomType] || row.roomType }}
          </span>
        </template>
      </el-table-column>
      <el-table-column prop="capacity" label="座位数">
        <template #default="{ row }">
          <el-input-number
            v-if="row.roomId === editingRoom.roomId"
            v-model="editingRoom.capacity"
            :min="0"
            controls-position="right"
          />
        </template>
      </el-table-column>
      <el-table-column prop="basePrice" label="基础价格">
        <template #default="{ row }">
          <el-input-number
            v-if="row.roomId === editingRoom.roomId"
            v-model="editingRoom.basePrice"
            :min="0"
            :precision="2"
            controls-position="right"
          />
        </template>
      </el-table-column>
      <el-table-column prop="roomStatus" label="状态">
        <template #default="{ row }">
          <el-tag :type="row.roomStatus ? 'success' : 'danger'">
            {{ statusMap(row.roomStatus) || row.roomStatus }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="160">
        <template #default="{ row }">
          <span v-if="row.roomId === editingRoom.roomId">
            <el-button size="small" type="success" :loading="loading" @click="save">
              保存
            </el-button>
            <el-button size="small" type="info" @click="resetEditingId"> 取消 </el-button>
          </span>
          <span v-else>
            <el-button size="small" type="warning" @click="editingRoom = { ...row }">
              修改
            </el-button>
            <el-button
              size="small"
              type="danger"
              :loading="loading"
              @click="deleteRoom(row.roomId)"
            >
              删除
            </el-button>
          </span>
        </template>
      </el-table-column>
    </el-table>

    <el-table :data="[newRoom]" v-loading="loading" style="width: 100%" border stripe>
      <el-table-column prop="roomName" label="会议室名称">
        <el-input v-model="newRoom.roomName" placeholder="请输入新会议室的名称" />
      </el-table-column>
      <el-table-column prop="roomType" label="类型">
        <el-select v-model="newRoom.roomType" filterable>
          <el-option v-for="t in types" :key="t.key" :label="t.label" :value="t.key" />
        </el-select>
      </el-table-column>
      <el-table-column prop="capacity" label="座位数">
        <el-input-number v-model="newRoom.capacity" :min="0" controls-position="right" />
      </el-table-column>
      <el-table-column prop="basePrice" label="基础价格">
        <el-input-number
          v-model="newRoom.basePrice"
          :min="0"
          :precision="2"
          controls-position="right"
        />
      </el-table-column>
      <el-table-column label="操作" width="120">
        <el-button type="success" :loading="loading" @click="addRoom">添加</el-button>
      </el-table-column>
    </el-table>

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
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { handleResponse } from '@/utils/responseHandler'
import { usePagination } from '@/composables/usePagination'

const http = useApi()
const router = useRouter()
const userStore = useUserStore()
const rooms = ref([])
const editingRoom = ref({ roomId: 0 })
const defaultNewRoom = {
  roomName: '',
  roomType: 'CLASSROOM',
  capacity: 0,
  basePrice: 0,
}
const newRoom = ref({ ...defaultNewRoom })
const loading = ref(false)

const { pagination, handleCurrentChange, handleSizeChange, getCurrentPageData, updateTotal } =
  usePagination()

const currentPageData = computed(() => getCurrentPageData(rooms.value))

// 用户信息
const userInfo = computed(() => userStore.userInfo)
const isAdmin = computed(() => userInfo.value.role === 'ADMIN')

// 枚举类型映射
const typeMap = {
  CLASSROOM: '教室型',
  ROUND_TABLE: '圆桌型',
}
const statusMap = (status) => {
  return status ? '可用' : '不可用'
}
const types = [
  { key: 'CLASSROOM', label: '教室型' },
  { key: 'ROUND_TABLE', label: '圆桌型' },
]

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

const save = async () => {
  loading.value = true
  try {
    const response = await http.put('/rooms', editingRoom.value)
    handleResponse(response, {
      onSuccess: () => {
        resetEditingId()
        getRooms()
      },
    })
  } catch (error) {
    console.error('服务器异常：', error)
    ElMessage.error('服务器异常')
  } finally {
    loading.value = false
  }
}

const changeEditingRoomType = () => {
  editingRoom.value.roomType =
    editingRoom.value.roomType === 'CLASSROOM' ? 'ROUND_TABLE' : 'CLASSROOM'
}

const resetEditingId = () => {
  editingRoom.value.roomId = null
}

const addRoom = async () => {
  try {
    const response = await http.post(`/rooms`, newRoom.value)
    handleResponse(response, {
      onSuccess: () => {
        getRooms()
      },
    })
  } catch (error) {
    console.error('服务器异常：', error)
    ElMessage.error('服务器异常')
  }
}

const deleteRoom = (roomId) => {
  ElMessageBox.confirm('确定要删除该会议室吗？', '', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  })
    .then(async () => {
      try {
        const response = await http.delete(`/rooms/${roomId}`)
        handleResponse(response, {
          onSuccess: () => {
            getRooms()
          },
        })
      } catch (error) {
        console.error('服务器异常：', error)
        ElMessage.error('服务器异常')
      }
    })
    .catch(() => {})
}

const checkPermission = () => {
  if (!isAdmin.value) {
    ElMessage.warning('您没有权限访问此页面')
    router.replace('/')
    return false
  }
  return true
}

onMounted(() => {
  if (checkPermission()) getRooms()
})
</script>

<style scoped>
.el-table {
  margin-top: 20px;
}
</style>
