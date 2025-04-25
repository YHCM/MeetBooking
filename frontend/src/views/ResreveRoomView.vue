<template>
  <div>
    <h3>会议室预订</h3>

    <!-- 第一个弹窗：选择使用时间 -->
    <el-dialog v-model="isTimeDialogVisible" title="选择使用时间" width="400px">
      <el-form :model="searchRequest" label-width="120px">
        <el-form-item label="开始时间">
          <el-date-picker
            v-model="searchRequest.startTime"
            type="datetime"
            placeholder="选择开始时间"
            :disabled-date="disabledStartDate"
          />
        </el-form-item>
        <el-form-item label="结束时间">
          <el-date-picker
            v-model="searchRequest.endTime"
            type="datetime"
            placeholder="选择结束时间"
            :disabled-date="disabledEndDate"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="isTimeDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="openAttendanceDialog">确定</el-button>
      </template>
    </el-dialog>

    <!-- 第二个弹窗：选择参会人数 -->
    <el-dialog v-model="isAttendanceDialogVisible" title="选择参会人数" width="400px">
      <el-form :model="searchRequest" label-width="120px">
        <el-form-item label="参会人数">
          <el-input-number
            v-model="searchRequest.attendance"
            :min="1"
            placeholder="请输入参会人数"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="isAttendanceDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="openDeviceDialog">确定</el-button>
      </template>
    </el-dialog>

    <!-- 第三个弹窗：选择所需设备 -->
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

// 客户填的预订数据
const searchRequest = ref({
  startTime: null,
  endTime: null,
  attendance: null,
  requiredDevices: [],
})

const isTimeDialogVisible = ref(false)
const isAttendanceDialogVisible = ref(false)
const isDeviceDialogVisible = ref(false)

// 禁用不可选日期
const disabledStartDate = (time) => {
  return time.getTime() < Date.now()
}

const disabledEndDate = (time) => {
  return time.getTime() <= searchRequest.value.startTime?.getTime()
}

// 进入人数弹窗
const openAttendanceDialog = () => {
  if (!searchRequest.value.startTime || !searchRequest.value.endTime) {
    ElMessage.warning('请选择完整的使用时间')
    return
  }
  isTimeDialogVisible.value = false
  isAttendanceDialogVisible.value = true
}

// 进入设备弹窗
const openDeviceDialog = () => {
  if (!searchRequest.value.attendance) {
    ElMessage.warning('请输入参会人数')
    return
  }
  isAttendanceDialogVisible.value = false
  isDeviceDialogVisible.value = true
}

// 提交预订
const submitBooking = () => {
  console.log('预定信息：', searchRequest.value)
  ElMessage.success('预订成功！')
  isDeviceDialogVisible.value = false
}

// 初始化时弹出第一个弹窗
onMounted(() => {
  isTimeDialogVisible.value = true
})
</script>

<style scoped>
.dialog-footer {
  text-align: right;
}
</style>
