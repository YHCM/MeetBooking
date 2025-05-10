<template>
  <div>
    <h3>我的订单</h3>

    <!-- 订单表格 -->
    <el-table :data="currentPageData" v-loading="loading" style="width: 100%" border stripe>
      <el-table-column prop="orderId" label="订单ID" />
      <el-table-column prop="roomName" label="会议室" />
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
          <el-tag
              :type="pendingRefundOrderIds.has(row.orderId) ? 'info' : getStatusColor(row.status)"
          >
            {{ pendingRefundOrderIds.has(row.orderId) ? '退款中' : formatStatus(row.status) }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="300">
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

          <el-button
              v-if="canRefund(row)"
              size="small"
              type="warning"
              @click="submitRefundRequest(row)"
          >
            退款
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
      <el-descriptions column="1" border size="default" style="margin-bottom: 12px;">
        <el-descriptions-item label="会议室">{{ orderDetails.roomName }}</el-descriptions-item>
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
const formatStatus = (status) => statusMap[status] || status
const getStatusColor = (status) => statusColorMap[status] || 'default'

const formatHour = (hour) => hour !== null ? `${hour}:00` : '-'
const userInfo = computed(() => useUserStore().userInfo)

const orderList = ref([])
const loading = ref(false)
const orderDetails = ref({})
const detailsDialogVisible = ref(false)
const pagination = ref({ currentPage: 1, pageSize: 10, total: 0 })
const currentPageData = computed(() =>
    orderList.value.slice(
        (pagination.value.currentPage - 1) * pagination.value.pageSize,
        pagination.value.currentPage * pagination.value.pageSize
    )
)

// ⏳ 正在退款的订单ID集合
const pendingRefundOrderIds = ref(new Set())

// 获取订单
const getOrders = async () => {
  loading.value = true
  try {
    const response = await http.get(`/orders?userId=${userInfo.value.userId}`)
    orderList.value = response.data || []
    pagination.value.total = orderList.value.length
  } catch (error) {
    ElMessage.error('获取订单列表失败')
  } finally {
    loading.value = false
  }
}

// 获取退款请求
const getRefundRequests = async () => {
  try {
    const response = await http.get(`/refunds?userId=${userInfo.value.userId}`)
    const refundList = response.data || []
    const pending = refundList.filter(r => r.requestStatus === 'PENDING').map(r => r.orderId)
    pendingRefundOrderIds.value = new Set(pending)
  } catch (e) {
    console.error('获取退款请求失败', e)
  }
}

// 详情
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
      getOrders()
    } else {
      ElMessage.error('订单支付失败，请稍后重试')
    }
  } catch {
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
      getOrders()
    } else {
      ElMessage.error('订单取消失败，请稍后重试')
    }
  } catch {
    ElMessage.error('取消订单失败，请稍后重试')
  } finally {
    loading.value = false
  }
}

// 退款条件判断
const canRefund = (order) => {
  if (order.status !== 'COMPLETED') return false
  const now = new Date()
  const bookingDate = new Date(order.bookingDate)
  bookingDate.setHours(order.startHour, 0, 0, 0)
  const hoursUntilStart = (bookingDate.getTime() - now.getTime()) / 3600000
  return hoursUntilStart >= 24 && !pendingRefundOrderIds.value.has(order.orderId)
}

// 退款申请
const submitRefundRequest = async (order) => {
  try {
    await ElMessageBox.confirm(
        `是否申请取消订单 ID:${order.orderId}？\n\n【退费规则】\n提前72小时退全款\n提前48小时退75%\n提前24小时退25%\n不足24小时不支持退款`,
        '申请退款确认',
        {
          confirmButtonText: '确认申请',
          cancelButtonText: '取消',
          type: 'warning',
        }
    )

    const res = await http.post('/refunds', { orderId: order.orderId })

    if (res) {
      ElMessage.success('退款申请已提交，等待审核')
      await getRefundRequests()
    } else {
      ElMessage.error(res.message || '提交失败')
    }
  } catch (err) {
    if (err !== 'cancel') {
      ElMessage.error('请求失败')
    }
  }
}

// 分页
const handleCurrentChange = (page) => { pagination.value.currentPage = page }
const handleSizeChange = (size) => { pagination.value.pageSize = size }

// 初始化
onMounted(async () => {
  await getOrders()
  await getRefundRequests()
})
</script>

<style scoped>
.el-table {
  margin-top: 20px;
}
</style>
