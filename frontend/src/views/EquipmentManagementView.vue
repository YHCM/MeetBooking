<template>
  <div>
    <h3>设备管理</h3>

    <!-- 新增设备按钮 -->
    <el-button type="primary" @click="openAddDialog">新增设备</el-button>

    <!-- 设备表格 -->
    <el-table :data="currentPageData" v-loading="loading" style="width: 100%" border stripe>
      <el-table-column prop="equipmentId" label="ID" />
      <el-table-column prop="equipmentName" label="设备名称">
        <template #default="{ row }">
          <el-input v-if="row.equipmentId === editingEquipment.equipmentId" v-model="editingEquipment.equipmentName" />
        </template>
      </el-table-column>
      <el-table-column prop="description" label="描述">
        <template #default="{ row }">
          <el-input v-if="row.equipmentId === editingEquipment.equipmentId" v-model="editingEquipment.description" />
        </template>
      </el-table-column>
      <el-table-column prop="additionalPrice" label="额外价格">
        <template #default="{ row }">
          <el-input-number v-if="row.equipmentId === editingEquipment.equipmentId" v-model="editingEquipment.additionalPrice" :min="0" :precision="2" controls-position="right" />
        </template>
      </el-table-column>
      <el-table-column label="操作" width="160">
        <template #default="{ row }">
          <span v-if="row.equipmentId === editingEquipment.equipmentId">
            <el-button size="small" type="success" :loading="loading" @click="saveEquipment">保存</el-button>
            <el-button size="small" type="info" @click="resetEditingEquipment">取消</el-button>
          </span>
          <span v-else>
            <el-button size="small" type="warning" @click="editEquipment(row)">修改</el-button>
            <el-button size="small" type="danger" @click="deleteEquipment(row.equipmentId)" :loading="loading">删除</el-button>
          </span>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
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

    <!-- 新增设备弹框 -->
    <el-dialog v-model="addDialogVisible" title="新增设备" @close="resetAddForm">
      <el-form :model="newEquipment" ref="addForm" label-width="120px">
        <el-form-item label="设备名称" :rules="[{ required: true, message: '请输入设备名称', trigger: 'blur' }]">
          <el-input v-model="newEquipment.equipmentName" />
        </el-form-item>
        <el-form-item label="描述" :rules="[{ required: true, message: '请输入设备描述', trigger: 'blur' }]">
          <el-input v-model="newEquipment.description" />
        </el-form-item>
        <el-form-item label="额外价格" :rules="[{ required: true, message: '请输入额外价格', trigger: 'blur' }]">
          <el-input v-model="newEquipment.additionalPrice" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="resetAddForm">取消</el-button>
        <el-button type="primary" @click="addEquipment">确认添加</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useApi } from '@/composables/useApi'
import { usePagination } from '@/composables/usePagination'

const http = useApi()

// 设备数据
const equipmentList = ref([]) // 设备列表
const loading = ref(false) // 加载状态
const editingEquipment = ref({ equipmentId: null, equipmentName: '', description: '', additionalPrice: 0 })
const newEquipment = ref({
  equipmentName: '',
  description: '',
  additionalPrice: null
})
const addDialogVisible = ref(false)

// 分页设置
const { pagination, handleCurrentChange, handleSizeChange, getCurrentPageData, updateTotal } = usePagination()
const currentPageData = computed(() => getCurrentPageData(equipmentList.value))

// 获取设备数据
const getEquipment = async () => {
  loading.value = true
  try {
    const response = await http.get('/equipment')
    equipmentList.value = response.data || []
    updateTotal(equipmentList.value.length)
  } catch (error) {
    console.error('获取设备数据失败', error)
    ElMessage.error('获取设备列表失败')
  } finally {
    loading.value = false
  }
}

// 删除设备
const deleteEquipment = async (equipmentId) => {
  try {
    await ElMessageBox.confirm('确认删除该设备吗?', '删除设备', {
      confirmButtonText: '删除',
      cancelButtonText: '取消',
      type: 'warning'
    })
    const response = await http.delete(`/equipment/${equipmentId}`)
    if (response.code === 204) {
      ElMessage.success('设备删除成功')
      getEquipment()
    } else {
      ElMessage.error('设备删除失败')
    }
  } catch (error) {
    ElMessage.info('取消删除设备')
  }
}

// 编辑设备
const editEquipment = (row) => {
  editingEquipment.value = { ...row }  
}

// 保存修改的设备
const saveEquipment = async () => {
  loading.value = true
  try {
    const response = await http.put('/equipment', editingEquipment.value)
    if (response.code === 200) {
      ElMessage.success('设备修改成功')
      resetEditingEquipment()
      getEquipment()  // 刷新设备列表
    } else {
      ElMessage.error('设备修改失败')
    }
  } catch (error) {
    ElMessage.error('设备修改失败')
  } finally {
    loading.value = false
  }
}

// 取消编辑
const resetEditingEquipment = () => {
  editingEquipment.value = { equipmentId: null, equipmentName: '', description: '', additionalPrice: 0 }
}

// 打开新增设备弹窗
const openAddDialog = () => {
  addDialogVisible.value = true
}

// 重置新增设备表单
const resetAddForm = () => {
  newEquipment.value = {
    equipmentName: '',
    description: '',
    additionalPrice: null
  }
}

// 新增设备
const addEquipment = async () => {
  try {
    const response = await http.post('/equipment', newEquipment.value)
    if (response.code === 201) {
      ElMessage.success('设备添加成功')
      addDialogVisible.value = false
      getEquipment()  // 刷新设备列表
    } else {
      ElMessage.error('设备添加失败')
    }
  } catch (error) {
    ElMessage.error('设备添加失败')
  }
}

// 初始化数据
onMounted(() => {
  getEquipment()
})
</script>

<style scoped>
.el-table {
  margin-top: 20px;
}
</style>
