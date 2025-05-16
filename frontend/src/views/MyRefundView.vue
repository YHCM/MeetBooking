<template>
  <div>
    <h3>我的退款申请</h3>

    <el-table :data="refundList" v-loading="loading" style="width: 100%" border stripe>
      <el-table-column prop="refundId" label="退款ID" width="100" />
      <el-table-column prop="orderId" label="订单（点击查看详情）">
        <template #default="{ row }">
          <el-button @click="viewOrderDetails(row.order)" plain>{{ row.orderId }}</el-button>
        </template>
      </el-table-column>
      <el-table-column prop="refundAmount" label="退款金额">
        <template #default="{ row }">￥{{ row.refundAmount }}</template>
      </el-table-column>
      <el-table-column prop="createdAt" label="申请时间">
        <template #default="{ row }">
          {{ formatDateTimeShort(row.createdAt) }}
        </template>
      </el-table-column>
      <el-table-column prop="requestStatus" label="状态">
        <template #default="{ row }">
          <el-tag :type="statusColorMap[row.requestStatus]">
            {{ statusMap[row.requestStatus] }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="processedAt" label="处理时间">
        <template #default="{ row }">
          {{ row.processedAt ? formatDate(row.processedAt) : null }}
        </template>
      </el-table-column>
      <el-table-column prop="processedBy" label="处理人 ID" />
    </el-table>

    <!-- 订单详情弹窗 -->
    <el-dialog
      v-model="detailsDialogVisible"
      :title="`订单详情 #${orderDetails.orderId || ''}`"
      @close="detailsDialogVisible = false"
      width="480px"
      :top="'20vh'"
    >
      <el-descriptions column="1" border size="default" style="margin-bottom: 12px">
        <el-descriptions-item label="会议室">{{ orderDetails.roomName }}</el-descriptions-item>
        <el-descriptions-item label="预定日期">{{ orderDetails.bookingDate }}</el-descriptions-item>
        <el-descriptions-item label="时间段">
          {{ formatHour(orderDetails.startHour) }} - {{ formatHour(orderDetails.endHour) }}
        </el-descriptions-item>
        <el-descriptions-item label="价格">
          <span style="color: #67c23a; font-weight: bold">￥{{ orderDetails.price }}</span>
        </el-descriptions-item>
      </el-descriptions>

      <template #footer>
        <el-button @click="detailsDialogVisible = false">关闭</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { formatDate } from '@/utils/date'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const http = useApi()
const userStore = useUserStore()

// 是否登陆
const isLogin = computed(() => userStore.userInfo.userId !== 0)

const refundList = ref([])
const loading = ref(false)

const detailsDialogVisible = ref(false)
const orderDetails = ref({})
const userInfo = computed(() => userStore.userInfo)

const statusMap = {
  PENDING: '待审批',
  APPROVED: '已批准',
  REJECTED: '已拒绝',
}
const statusColorMap = {
  PENDING: 'warning',
  APPROVED: 'success',
  REJECTED: 'danger',
}

// 加载数据
const fetchRefunds = async () => {
  loading.value = true
  try {
    const res = await http.get(`/refunds?userId=${userInfo.value.userId}`)
    refundList.value = res.data || []
  } catch (e) {
    ElMessage.error('获取退款申请失败')
  } finally {
    loading.value = false
  }
}

const formatDateTimeShort = (str) => {
  if (!str) return ''
  const date = new Date(str)
  const pad = (n) => String(n).padStart(2, '0')
  return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())} ${pad(date.getHours())}:${pad(date.getMinutes())}`
}

const formatHour = (hour) => (hour !== null ? `${hour}:00` : '-')

const viewOrderDetails = (order) => {
  if (order) {
    orderDetails.value = { ...order }
    detailsDialogVisible.value = true
  }
}

onMounted(() => {
  if (isLogin.value) {
    fetchRefunds()
  } else {
    ElMessage.warning('请登录')
    router.replace('/login')
  }
})
</script>

<style scoped>
h3 {
  margin-bottom: 20px;
}
</style>
