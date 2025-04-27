<template>
  <div>
    <h3>会议室预订</h3>

    <!-- ───── 信息卡片 ───── -->
    <el-card class="condition-card" shadow="always">
      <template #header>
        <span>预订信息</span>
        <el-button
            type="primary"
            size="small"
            style="float: right"
            :disabled="!criteriaFilled"
            @click="restart"
        >
          重新选择
        </el-button>
      </template>

      <el-descriptions :column="2">
        <el-descriptions-item label="开始时间">{{ format(req.startTime) }}</el-descriptions-item>
        <el-descriptions-item label="结束时间">{{ format(req.endTime) }}</el-descriptions-item>
        <el-descriptions-item label="会议室类型">{{ typeMap[req.roomType] }}</el-descriptions-item>
        <el-descriptions-item label="参会人数">{{ req.attendance ?? '—' }}</el-descriptions-item>
        <el-descriptions-item label="所需设备">
          <template v-if="req.requiredDevices.length">
            <el-tag
                v-for="id in req.requiredDevices"
                :key="id"
                size="small"
                type="info"
                class="mr"
            >
              {{ id2Name[id] }}
            </el-tag>
          </template>
          <span v-else>—</span>
        </el-descriptions-item>
      </el-descriptions>
    </el-card>

    <!-- 符合条件会议室 -->
    <el-table
        :data="roomList"
        v-loading="loadingRooms"
        border
        stripe
        style="width: 100%; margin-top: 20px;"
    >
      <el-table-column prop="roomId" label="ID" width="80" />
      <el-table-column prop="roomName" label="会议室名称" />
      <el-table-column prop="capacity" label="座位数" width="100" />
      <el-table-column prop="roomType" label="类型" />
      <el-table-column label="操作" width="120">
        <template #default="{ row }">
          <el-button
              type="success"
              size="small"
              :disabled="!criteriaFilled"
              @click="bookRoom(row)"
          >
            预订
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 时间 -->
    <el-dialog v-model="dlgTime" title="选择使用时间" width="420px" :top="dialogTop">
      <el-form :model="req" label-width="100px">
        <el-form-item label="开始时间">
          <el-date-picker
              v-model="req.startTime"
              type="datetime"
              format="YYYY-MM-DD HH:mm"
              :disabled-date="dStart"
          />
        </el-form-item>
        <el-form-item label="结束时间">
          <el-date-picker
              v-model="req.endTime"
              type="datetime"
              format="YYYY-MM-DD HH:mm"
              :disabled-date="dEnd"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dlgTime = false">取消</el-button>
        <el-button type="primary" @click="nextToType">下一步</el-button>
      </template>
    </el-dialog>

    <!-- 类型 -->
    <el-dialog v-model="dlgType" title="会议室类型" width="340px" :top="dialogTop">
      <el-radio-group v-model="req.roomType">
        <el-radio-button label="CLASSROOM">教室型</el-radio-button>
        <el-radio-button label="ROUND_TABLE">圆桌型</el-radio-button>
      </el-radio-group>
      <template #footer>
        <el-button @click="dlgType = false">取消</el-button>
        <el-button type="primary" @click="nextToAttendance">下一步</el-button>
      </template>
    </el-dialog>

    <!-- 人数 -->
    <el-dialog v-model="dlgAtt" title="参会人数" width="340px" :top="dialogTop">
      <el-form :model="req" label-width="100px">
        <el-form-item label="人数">
          <el-input-number v-model="req.attendance" :min="1" :max="500" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dlgAtt = false">取消</el-button>
        <el-button type="primary" @click="nextToDevice">下一步</el-button>
      </template>
    </el-dialog>

    <!-- 设备 -->
    <el-dialog v-model="dlgDev" title="所需设备" width="420px" :top="dialogTop">
      <el-form :model="req" label-width="100px">
        <el-form-item label="设备">
          <el-checkbox-group v-model="req.requiredDevices">
            <el-checkbox
                v-for="opt in equipmentOptions"
                :key="opt.equipmentId"
                :label="opt.equipmentId"
            >
              {{ opt.equipmentName }}
            </el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dlgDev = false">取消</el-button>
        <el-button type="primary" @click="finish">完成</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
const http = useApi()

// 格式化时间
const pad2 = n => String(n).padStart(2, '0')
const format = d =>
    d
        ? `${d.getFullYear()}-${pad2(d.getMonth() + 1)}-${pad2(d.getDate())} `
        + `${pad2(d.getHours())}:${pad2(d.getMinutes())}`
        : '—'

const typeMap = { CLASSROOM: '教室型', ROUND_TABLE: '圆桌型' }

const equipmentOptions = ref([])
const id2Name = reactive({})

const req = reactive({
  startTime: null,
  endTime: null,
  roomType: 'CLASSROOM',
  attendance: null,
  requiredDevices: []
})

// 弹窗控制
const dlgTime = ref(false)
const dlgType = ref(false)
const dlgAtt  = ref(false)
const dlgDev  = ref(false)
const criteriaFilled = ref(false)

// 房间列表
const roomList = ref([])
const loadingRooms = ref(false)

const dialogTop = '30vh'
const dStart = d => d.getTime() < Date.now()
const dEnd   = d => req.startTime && d.getTime() <= req.startTime.getTime()

// 步骤切换
function nextToType () {
  if (!req.startTime || !req.endTime)
    return ElMessage.warning('请选择完整时间')
  dlgTime.value = false
  dlgType.value = true
}
function nextToAttendance () {
  dlgType.value = false
  dlgAtt.value = true
}
function nextToDevice () {
  if (!req.attendance)
    return ElMessage.warning('请输入参会人数')
  dlgAtt.value = false
  dlgDev.value = true
}

async function finish () {
  dlgDev.value = false
  criteriaFilled.value = true
  await fetchRooms()
}

// 筛选房间
async function fetchRooms () {
  loadingRooms.value = true
  try {
    const s = new Date(req.startTime)
    const e = new Date(req.endTime)
    const body = {
      date: `${s.getFullYear()}-${pad2(s.getMonth() + 1)}-${pad2(s.getDate())}`,
      startTime: s.getHours(),
      endTime: e.getHours(),
      attendance: req.attendance,
      requiredEquipments: req.requiredDevices,
      roomType: req.roomType
    }

    const res = await http.post('/rooms/search', body)
    roomList.value = res.data || []
  } catch (err) {
    console.error(err)
    ElMessage.error('获取会议室失败')
    roomList.value = []
  } finally {
    loadingRooms.value = false
  }
}

const bookRoom = row => ElMessage.success(`已选择预订：${row.roomName}(ID:${row.roomId})`)

// 重新选择
function restart () {
  criteriaFilled.value = false
  roomList.value = []
  Object.assign(req, {
    startTime: null,
    endTime: null,
    roomType: 'CLASSROOM',
    attendance: null,
    requiredDevices: []
  })
  dlgTime.value = true
}

// 初始化
onMounted(async () => {
  try {
    const res = await http.get('/equipment')
    equipmentOptions.value = res.data || []
    equipmentOptions.value.forEach(e => { id2Name[e.equipmentId] = e.equipmentName })
  } catch (e) {
    console.error(e)
    ElMessage.error('设备加载失败')
  }
  dlgTime.value = true
})
</script>

<style scoped>
.condition-card { margin-bottom: 20px; }
.mr { margin-right: 4px; }
.el-dialog .dialog-footer { text-align: right; }
</style>
