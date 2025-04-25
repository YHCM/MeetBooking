<template>
  <el-table :data="equipments" v-loading="loading" border stripe>
    <el-table-column prop="equipmentName" label="设备名称" />
    <el-table-column prop="additionalPrice" label="额外价格" />
    <el-table-column label="操作">
      <template #default="{ row }">
        <el-button type="danger" @click="deleteEquipment(row)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>

  <el-table v-if="allEquipments" :data="[1]">
    <el-table-column label="新设备名称">
      <el-select v-model="newEquipmentId" filterable>
        <el-option
          v-for="e in allEquipments"
          :key="e.equipmentId"
          :label="e.equipmentName"
          :value="e.equipmentId"
        />
      </el-select>
    </el-table-column>
    <el-table-column label="操作">
      <el-button type="success" @click="addEquipment">添加</el-button>
    </el-table-column>
  </el-table>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue'
import { handleResponse } from '@/utils/responseHandler'

const http = useApi()

const loading = ref(false)
const equipments = ref([])
const allEquipments = ref([])
const newEquipmentId = ref(0)
const props = defineProps({
  room: Object,
})

const getAllEquipment = async () => {
  try {
    const response = await http.get('/equipment')
    allEquipments.value = response.data || []
    if (response.data) newEquipmentId.value = response.data[0].equipmentId
  } catch (error) {
    console.error('获取设备数据失败：', error)
    ElMessage.error('获取设备列表失败')
  }
}

const getEquipments = async (room) => {
  loading.value = true
  try {
    const response = await http.get(`/rooms/${room.roomId}/equipment`)
    equipments.value = response.data || []
  } catch (error) {
    console.error('服务器异常：', error)
    ElMessage.error('获取设备失败')
  } finally {
    loading.value = false
  }
}

const addEquipment = async () => {
  try {
    const body = { roomId: props.room.roomId, equipmentId: newEquipmentId.value }
    console.log(body)
    const response = await http.post(`/rooms/equipment`, body)
    handleResponse(response, {
      onSuccess: () => {
        getEquipments(props.room)
      },
    })
  } catch (error) {
    console.error('服务器异常：', error)
    ElMessage.error('添加设备失败')
  }
}

const deleteEquipment = async (equipment) => {
  ElMessageBox.confirm('确定要删除该会议室吗？', '', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  })
    .then(async () => {
      try {
        const body = { roomId: props.room.roomId, equipmentId: equipment.equipmentId }
        const response = await http.delete(`/rooms/equipment`, { data: body })
        handleResponse(response, {
          onSuccess: () => {
            getEquipments(props.room)
          },
        })
      } catch (error) {
        console.error('服务器异常：', error)
        ElMessage.error('删除设备失败')
      }
    })
    .catch(() => {})
}

onMounted(() => {
  getAllEquipment()
})

defineExpose({
  getEquipments,
})
</script>
