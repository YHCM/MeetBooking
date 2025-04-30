<template>
  <div>
    <h3>会议室预订</h3>

    <!-- ───── 信息卡片 ───── -->
    <el-card class="condition-card" shadow="always">
      <template #header>
        <span>预订信息</span>
      </template>

      <el-descriptions :column="2">
        <el-descriptions-item label="日期">
          <el-date-picker v-model="req.date" type="date" format="YYYY-MM-DD" :disabled-date="dStart" />
        </el-descriptions-item>
        <el-descriptions-item label="会议时间">
          <el-input-number v-model="req.startTime" :min="0" :max="23">
            <template #suffix>时</template>
          </el-input-number>
          <span style="margin:0 5px">到</span>
          <el-input-number v-model="req.endTime" :min="req.startTime + 1" :max="24">
            <template #suffix>时</template>
          </el-input-number>
        </el-descriptions-item>
        <el-descriptions-item label="会议室类型">
          <el-radio-group v-model="req.roomType">
            <el-radio-button label="CLASSROOM">教室型</el-radio-button>
            <el-radio-button label="ROUND_TABLE">圆桌型</el-radio-button>
          </el-radio-group>
        </el-descriptions-item>
        <el-descriptions-item label="参会人数">
          <el-input-number v-model="req.attendance" :min="1" :max="500" />
        </el-descriptions-item>
        <el-descriptions-item label="所需设备">
          <template v-if="req.requiredDevices.length">
            <el-tag v-for="id in req.requiredDevices" :key="id" size="small" type="info" class="mr">
              {{ id2Name[id] }}
            </el-tag>
          </template>
          <span v-else>—</span>
          <el-button type="primary" size="small" style="margin-left: 7px;" @click="dlgDev = true">
            选择
          </el-button>
        </el-descriptions-item>
      </el-descriptions>
      <el-button type="primary" size="small" :disabled="!criteriaFilled" @click="start">
        确认
      </el-button>
    </el-card>

    <!-- 符合条件会议室 -->
    <el-table :data="roomList" v-loading="loadingRooms" border stripe style="width: 100%; margin-top: 20px;">
      <el-table-column prop="roomId" label="ID" width="80" />
      <el-table-column prop="roomName" label="会议室名称" />
      <el-table-column prop="capacity" label="座位数" width="100" />
      <el-table-column prop="roomType" label="类型">
        <template #default="{ row }">
          {{ typeMap[row.roomType] }}
        </template>
      </el-table-column>
      <el-table-column prop="price" label="预估价格">
        <template #default="{ row }">
          {{ row.price * (req.endTime - req.startTime) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="120">
        <template #default="{ row }">
          <el-button type="success" size="small" :disabled="!criteriaFilled" @click="bookRoom(row)">
            预订
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 设备 -->
    <el-dialog v-model="dlgDev" title="所需设备" width="420px" :top="dialogTop">
      <el-form :model="req" label-width="100px">
        <el-form-item label="设备">
          <el-checkbox-group v-model="req.requiredDevices">
            <el-checkbox v-for="opt in equipmentOptions" :key="opt.equipmentId" :label="opt.equipmentId">
              {{ opt.equipmentName }}
            </el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dlgDev = false">取消</el-button>
        <el-button type="primary" @click="dlgDev = false">完成</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
const http = useApi()
const router = useRouter()

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
  date: null,
  startTime: 8,
  endTime: 9,
  roomType: 'CLASSROOM',
  attendance: 1,
  requiredDevices: []
})

// 弹窗控制
const dlgDev  = ref(false)
const criteriaFilled = computed(() =>
  req.attendance && req.date && req.startTime && req.endTime && req.roomType
)

// 房间列表
const roomList = ref([])
const loadingRooms = ref(false)

const dialogTop = '30vh'
const dStart = d => d.getTime() < Date.now()

// 筛选房间
async function fetchRooms () {
  loadingRooms.value = true
  try {
    const body = {
      date: req.date,
      startTime: req.startTime,
      endTime: req.endTime,
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

const bookRoom = async (row) => {
  try {
    await ElMessageBox.confirm(
        `是否确认预订该会议室：${row.roomName}（ID:${row.roomId}）？`,
        '确认预订',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }
    );

    const orderRequest = {
      roomId: row.roomId,
      bookingDate: `${req.date.getFullYear()}-${pad2(req.date.getMonth() + 1)}-${pad2(req.date.getDate())}`,
      startHour: req.startTime,
      endHour: req.endTime
    };

    const res = await http.post('/orders', orderRequest);

    if (res) {
      ElMessage.success(`预订成功：${row.roomName}`);
      router.push('/orders');
    } else {
      ElMessage.error(res.message || '预订失败');
    }
  } catch (err) {
    if (err !== 'cancel') {
      console.error(err);
      ElMessage.error('请求失败，请稍后再试');
    }
  }
}

// 选择
async function start () {
  dlgDev.value = false
  await fetchRooms()
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
})
</script>

<style scoped>
.condition-card { margin-bottom: 20px; }
.mr { margin-right: 4px; }
.el-dialog .dialog-footer { text-align: right; }
</style>
