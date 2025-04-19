import { ref } from 'vue'

export function usePagination(defautPageSize = 15) {
  const pagination = ref({
    currentPage: 1,
    pageSize: defautPageSize,
    total: 0,
  })

  const handleCurrentChange = (val) => {
    pagination.value.currentPage = val
  }

  const handleSizeChange = (val) => {
    pagination.value.pageSize = val
    pagination.value.currentPage = 1
  }

  const getCurrentPageData = (dataList) => {
    const start = (pagination.value.currentPage - 1) * pagination.value.pageSize
    const end = start + pagination.value.pageSize
    return dataList.slice(start, end)
  }

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
