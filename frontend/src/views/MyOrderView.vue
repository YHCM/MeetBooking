<template>
  <div>
    <h3>我的订单</h3>

    <!-- 订单表格 -->
    <el-table :data="currentPageData" v-loading="loading" style="width: 100%" border stripe>
      <el-table-column prop="orderId" label="订单ID" />
      <el-table-column prop="roomId" label="会议室ID" />
      <el-table-column prop="bookingDate" label="预定日期" />
      <el-table-column prop="price" label="价格" />
      <el-table-column label="开始时间">
        <template #default="{ row }">
          {{ formatHour(row.startHour) }}
        </template>
      </el-table-column>
      <el-table-column label="结束时间">
        <template #default="{ row }">
          {{ formatHour(row.endHour) }}
        </template>
      </el-table-column>
      <el-table-column label="订单状态">
        <template #default="{ row }">
          <el-tag :type="getStatusColor(row.status)">
            {{ formatStatus(row.status) }}
          </el-tag>
        </template>
      </el-table-column>

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

    <!-- 订单详情弹窗 -->
    <el-dialog
        v-model="detailsDialogVisible"
        :title="`订单详情 #${orderDetails.orderId || ''}`"
        width="480px"
        @close="resetDetailsForm"
        :top="'20vh'"
    >
      <el-descriptions
          column="1"
          border
          :size="'default'"
          style="margin-bottom: 12px;"
      >
        <el-descriptions-item label="会议室ID">{{ orderDetails.roomId }}</el-descriptions-item>
        <el-descriptions-item label="预定日期">{{ orderDetails.bookingDate }}</el-descriptions-item>
        <el-descriptions-item label="时间段">
          {{ formatHour(orderDetails.startHour) }} - {{ formatHour(orderDetails.endHour) }}
        </el-descriptions-item>
        <el-descriptions-item label="价格">
          <span style="color: #67C23A; font-weight: bold;">￥{{ orderDetails.price }}</span>
        </el-descriptions-item>
        <el-descriptions-item label="订单状态">
          <el-tag :type="getStatusColor(orderDetails.status)">
            {{ formatStatus(orderDetails.status) }}
          </el-tag>
        </el-descriptions-item>
      </el-descriptions>

      <template #footer>
        <el-button @click="detailsDialogVisible = false">关闭</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useUserStore } from '@/stores/user'
const http = useApi()

const statusMap = {
  PENDING: '待支付',
  COMPLETED: '已完成',
  CANCELLED: '已取消',
  REFUNDED: '已退款'
}

const statusColorMap = {
  PENDING: 'warning',
  COMPLETED: 'success',
  CANCELLED: 'info',
  REFUNDED: 'danger'
}

// 用户信息
const userInfo = computed(() => useUserStore().userInfo)

// 格式化展示的时间
const formatHour = (hour) => {
  return hour !== null && hour !== undefined ? `${hour}:00` : '-'
}

// 格式化订单状态
const formatStatus = (status) => {
  return statusMap[status] || status
}

const getStatusColor = (status) => {
  return statusColorMap[status] || 'default'
}

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

// 获取用户订单数据
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
const payOrder = async (orderId) => {
  loading.value = true
  try {
    const response = await http.patch(`/orders/${orderId}/pay`)
    if (response) {
      ElMessage.success('订单支付成功')
      getOrders() // 刷新订单列表
    } else {
      ElMessage.error('订单支付失败，请稍后重试')
    }
  } catch (error) {
    console.error('支付订单失败', error)
    ElMessage.error('支付订单失败，请稍后重试')
  } finally {
    loading.value = false
  }
}

// 取消订单
const cancelOrder = async (orderId) => {
  loading.value = true
  try {
    const response = await http.patch(`/orders/${orderId}/cancel`)
    if (response) {
      ElMessage.success('订单取消成功')
      getOrders() // 刷新订单列表
    } else {
      ElMessage.error('订单取消失败，请稍后重试')
    }
  } catch (error) {
    console.error('取消订单失败', error)
    ElMessage.error('取消订单失败，请稍后重试')
  } finally {
    loading.value = false
  }
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
