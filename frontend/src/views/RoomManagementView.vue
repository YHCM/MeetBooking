<template>
    <div>
        <h3>会议室管理</h3>
    </div>
    <div>
        <el-table :data="currentPageData" v-loading="loading" style="width: 100%" border stripe>
            <el-table-column prop="roomId" label="ID" />
            <el-table-column prop="roomName" label="会议室名称" />
            <el-table-column prop="roomType" label="类型">
                <template #default="{ row }">
                    {{ typeMap[row.roomType] || row.roomType }}
                </template>
            </el-table-column>
            <el-table-column prop="capacity" label="座位数" />
            <el-table-column prop="basePrice" label="基础价格" />
            <el-table-column prop="roomStatus" label="状态">
                <template #default="{ row }">
                    {{ statusMap(row.roomStatus) || row.roomStatus }}
                </template>
            </el-table-column>
            <el-table-column label="操作" width="160">
                <template #default="{ row }">
                    <el-button size="small" type="warning" :loading="loading">
                        修改
                    </el-button>
                    <el-button size="small" type="danger" :loading="loading">
                        删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
            :current-page="pagination.currentPage" :page-sizes="[10, 15, 20, 25]" :page-size="pagination.pageSize"
            layout="total, sizes, prev, pager, next, jumper" :total="pagination.total"
            style="margin-top: 20px; justify-content: flex-end" />
    </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { formatDate } from '@/utils/date'
import { useUserStore } from '@/stores/user'
import { handleResponse } from '@/utils/responseHandler'
import { usePagination } from '@/composables/usePagination'

const http = useApi()
const router = useRouter()
const userStore = useUserStore()
const rooms = ref([])
const loading = ref(false)

const { pagination, handleCurrentChange, handleSizeChange, getCurrentPageData, updateTotal } = usePagination()

const currentPageData = computed(() => getCurrentPageData(rooms.value))

// 用户信息
const userInfo = computed(() => userStore.userInfo)
const isAdmin = computed(() => userInfo.value.role === 'ADMIN')

// 枚举类型映射
const typeMap = {
    CLASSROOM: '教室型',
    ROUND_TABLE: '圆桌型',
}
const statusMap = (status) => {
    return status ? '可用' : '不可用'
}

const getRooms = async () => {
    loading.value = true
    try {
        const response = await http.get('/rooms')
        rooms.value = response.data
        updateTotal(rooms.value.length)
    } catch (error) {
        console.error('服务器异常：', error)
        ElMessage.error('服务器异常')
    } finally {
        loading.value = false
    }
}

const checkPermission = () => {
    if (!isAdmin.value) {
        ElMessage.warning('您没有权限访问此页面')
        router.replace('/')
        return false
    }
    return true
}

onMounted(() => {
    if (checkPermission())
        getRooms()
})

</script>

<style scoped>
.el-table {
    margin-top: 20px;
}
</style>
