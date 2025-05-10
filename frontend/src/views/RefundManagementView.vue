<template>
  <div>
    <h3>退款申请管理</h3>

    <el-table :data="refundList" v-loading="loading" style="width: 100%" border stripe>
      <el-table-column prop="refundId" label="退款ID" width="100" />
      <el-table-column prop="orderId" label="订单（点击查看详情）" >
        <template #default="{ row }">
          <el-button
              @click="viewOrderDetails(row.order)"
              plain
          >{{ row.orderId }}</el-button>
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
      <el-table-column label="操作" width="200">
        <template #default="{ row }">
          <el-button
              size="small"
              type="success"
              :disabled="row.requestStatus !== 'PENDING'"
              @click="handleApprove(row.refundId)"
          >批准</el-button>
          <el-button
              size="small"
              type="danger"
              :disabled="row.requestStatus !== 'PENDING'"
              @click="handleReject(row.refundId)"
          >拒绝</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 订单详情弹窗 -->
    <el-dialog
        v-model="detailsDialogVisible"
        :title="`订单详情 #${orderDetails.orderId || ''}`"
        @close="detailsDialogVisible = false"
        width="480px"
        :top="'20vh'"
    >
      <el-descriptions column="1" border size="default" style="margin-bottom: 12px;">
        <el-descriptions-item label="提交者">{{ orderDetails.userName }}</el-descriptions-item>
        <el-descriptions-item label="会议室">{{ orderDetails.roomName }}</el-descriptions-item>
        <el-descriptions-item label="预定日期">{{ orderDetails.bookingDate }}</el-descriptions-item>
        <el-descriptions-item label="时间段">
          {{ formatHour(orderDetails.startHour) }} - {{ formatHour(orderDetails.endHour) }}
        </el-descriptions-item>
        <el-descriptions-item label="价格">
          <span style="color: #67C23A; font-weight: bold;">￥{{ orderDetails.price }}</span>
        </el-descriptions-item>
      </el-descriptions>

      <template #footer>
        <el-button @click="detailsDialogVisible = false">关闭</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
const http = useApi()

const refundList = ref([])
const loading = ref(false)

const detailsDialogVisible = ref(false)
const orderDetails = ref({})

const statusMap = {
  PENDING: '待审批',
  APPROVED: '已批准',
  REJECTED: '已拒绝'
}
const statusColorMap = {
  PENDING: 'warning',
  APPROVED: 'success',
  REJECTED: 'danger'
}

// 加载数据
const fetchRefunds = async () => {
  loading.value = true
  try {
    const res = await http.get('/refunds')
    refundList.value = res.data || []
  } catch (e) {
    ElMessage.error('获取退款申请失败')
  } finally {
    loading.value = false
  }
}

// 审批函数
const handleApprove = async (refundId) => {
  try {
    await ElMessageBox.confirm('确定要批准该退款申请？', '确认批准', {
      confirmButtonText: '批准',
      cancelButtonText: '取消',
      type: 'warning',
    })
    const res = await http.patch(`/refunds/${refundId}/approve`)
    if (res && res.code === 200) {
      ElMessage.success('已批准')
      fetchRefunds()
    } else {
      ElMessage.error(res.message || '操作失败')
    }
  } catch (err) {
    if (err !== 'cancel') ElMessage.error('请求失败')
  }
}

const handleReject = async (refundId) => {
  try {
    await ElMessageBox.confirm('确定要拒绝该退款申请？', '确认拒绝', {
      confirmButtonText: '拒绝',
      cancelButtonText: '取消',
      type: 'danger',
    })
    const res = await http.patch(`/refunds/${refundId}/reject`)
    if (res && res.code === 200) {
      ElMessage.success('已拒绝')
      fetchRefunds()
    } else {
      ElMessage.error(res.message || '操作失败')
    }
  } catch (err) {
    if (err !== 'cancel') ElMessage.error('请求失败')
  }
}

const formatDateTimeShort = (str) => {
  if (!str) return ''
  const date = new Date(str)
  const pad = (n) => String(n).padStart(2, '0')
  return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())} ${pad(date.getHours())}:${pad(date.getMinutes())}`
}

const formatHour = (hour) => hour !== null ? `${hour}:00` : '-'

const viewOrderDetails = (order) => {
  if (order) {
    orderDetails.value = { ...order }
    detailsDialogVisible.value = true
  }
}

onMounted(() => {
  fetchRefunds()
})
</script>

<style scoped>
h3 {
  margin-bottom: 20px;
}
</style>
