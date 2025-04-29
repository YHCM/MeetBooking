<template>
  <div>
    <h3>我的订单</h3>

    <!-- 订单表格 -->
    <el-table :data="currentPageData" v-loading="loading" style="width: 100%" border stripe>
      <el-table-column prop="orderId" label="订单ID" />
      <el-table-column prop="roomId" label="会议室ID" />
      <el-table-column prop="bookingDate" label="预定日期" />
      <el-table-column prop="price" label="价格" />
      <el-table-column prop="startHour" label="开始时间" />
      <el-table-column prop="endHour" label="结束时间" />
      <el-table-column prop="status" label="订单状态" />

      <el-table-column label="操作" width="220">
        <template #default="{ row }">
          <el-button size="small" type="info" @click="viewOrderDetails(row.orderId)">
            查看详情
          </el-button>

          <el-button
              v-if="row.status === 'PENDING'"
              size="small"
              type="success"
              @click="payOrder(row.orderId)"
              :loading="loading"
          >
            支付
          </el-button>

          <el-button
              v-if="row.status === 'PENDING'"
              size="small"
              type="danger"
              @click="cancelOrder(row.orderId)"
              :loading="loading"
          >
            取消
          </el-button>
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

    <!-- 订单详情弹框 -->
    <el-dialog v-model="detailsDialogVisible" title="订单详情" @close="resetDetailsForm">
      <el-form :model="orderDetails" ref="detailsForm" label-width="120px">
        <el-form-item label="订单ID">{{ orderDetails.orderId }}</el-form-item>
        <el-form-item label="会议室ID">{{ orderDetails.roomId }}</el-form-item>
        <el-form-item label="预定日期">{{ orderDetails.bookingDate }}</el-form-item>
        <el-form-item label="价格">{{ orderDetails.price }}</el-form-item>
        <el-form-item label="开始时间">{{ orderDetails.startHour }}</el-form-item>
        <el-form-item label="结束时间">{{ orderDetails.endHour }}</el-form-item>
        <el-form-item label="订单状态">{{ orderDetails.status }}</el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="resetDetailsForm">关闭</el-button>
      </span>
    </el-dialog>
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

// 用户信息
const userInfo = computed(() => userStore.userInfo)

// 订单数据
const orderList = ref([]) // 订单列表
const loading = ref(false) // 加载状态
const orderDetails = ref({}) // 订单详情
const detailsDialogVisible = ref(false) // 订单详情弹框显示
const pagination = ref({
  currentPage: 1,
  pageSize: 10,
  total: 0,
})
const currentPageData = computed(() => orderList.value.slice((pagination.value.currentPage - 1) * pagination.value.pageSize, pagination.value.currentPage * pagination.value.pageSize))

// 获取订单数据
const getOrders = async () => {
  loading.value = true
  try {
    const response = await http.get(`/orders?userId=${userInfo.value.userId}`)
    orderList.value = response.data || []
    pagination.value.total = orderList.value.length
  } catch (error) {
    console.error('获取订单数据失败', error)
    ElMessage.error('获取订单列表失败')
  } finally {
    loading.value = false
  }
}

// 查看订单详情
const viewOrderDetails = (orderId) => {
  const order = orderList.value.find(order => order.orderId === orderId)
  if (order) {
    orderDetails.value = { ...order }
    detailsDialogVisible.value = true
  }
}

// 支付订单
const payOrder = (orderId) => {
  console.log(`订单ID: ${orderId}`)
}

// 取消订单
const cancelOrder = (orderId) => {
  console.log(`订单ID: ${orderId}`)
}

// 分页处理
const handleCurrentChange = (page) => {
  pagination.value.currentPage = page
}

const handleSizeChange = (size) => {
  pagination.value.pageSize = size
}

// 初始化数据
onMounted(() => {
  getOrders()
})
</script>

<style scoped>
.el-table {
  margin-top: 20px;
}
</style>
