import { ref } from 'vue'

export function usePagination(defautPageSize = 15) {
  const pagination = ref({
    currentPage: 1,
    pageSize: defautPageSize,
    total: 0,
  })

  // 处理页面变化
  const handleCurrentChange = (val) => {
    pagination.value.currentPage = val
  }

  // 处理页面数据大小变化
  const handleSizeChange = (val) => {
    pagination.value.pageSize = val
    pagination.value.currentPage = 1
  }

  // 获取当前页面数据
  const getCurrentPageData = (dataList) => {
    const start = (pagination.value.currentPage - 1) * pagination.value.pageSize
    const end = start + pagination.value.pageSize
    return dataList.slice(start, end)
  }

  // 获取数据的总量
  const updateTotal = (total) => {
    pagination.value.total = total
  }

  return {
    pagination,
    handleCurrentChange,
    handleSizeChange,
    getCurrentPageData,
    updateTotal,
  }
}
