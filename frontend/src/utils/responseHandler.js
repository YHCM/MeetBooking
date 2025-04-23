export function handleResponse(response, options = {}) {
  const { code, message } = response
  const { onSuccess, onError } = options

  // 通过状态码，确定消息的类型
  const type =
    code >= 200 && code < 300 ? 'success' : code >= 400 && code < 500 ? 'warning' : 'error'

  ElMessage({
    message,
    type,
    plain: true,
  })

  // 根据状态码触发回调
  if (code >= 200 && code <= 300) {
    onSuccess && onSuccess(message)
  } else {
    onError && onError(message)
  }
}
