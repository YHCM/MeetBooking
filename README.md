# MeetBooking - 会议室预订系统

## 🌟 快速开始

### 项目环境

- **后端**:
  - JDK 17+
  - Maven 3.6+
- **前端**:
  - Node.js 18+/20+
  - pnpm 8+
- **服务器**:
  - Nginx 1.18+ (可选)

### 1. 克隆项目

```bash
git clone https://github.com/YHCM/MeetBooking.git
cd MeetBooking
```

## 🛠️ 默认启动

### 后端服务

```bash
cd backend
mvn spring-boot:run
```

后端运行在: http://localhost:8080

### 前端服务

```bash
cd frontend
pnpm install
pnpm dev
```

前端运行在: http://localhost:5173

如果访问不了后端接口

修改 `/meetBooking/frontend/src/utils/useApi.js` 中的 `baseURL` 值为 `http://localhost:8080`

### 代码格式化

```bash
pnpm format
```

## 🚀 Nginx 方式启动

### Nginx 配置

1. 下载并安装 Nginx: [官方下载页面](https://nginx.org/en/download.html)
2. 启动 Nginx:

   **Linux/macOS:**

   ```bash
   nginx -c "$(pwd)/nginx.config"
   ```

   **Windows (CMD):**

   ```cmd
   nginx -c "%cd%\nginx.config"
   ```

   **Windows (PowerShell):**

   ```powershell
   nginx -c "$PWD\nginx.config"
   ```

3. 修改前端 API 配置:

   编辑 `/meetBooking/frontend/src/utils/useApi.js`，将 `baseURL` 的值改为 `/api`

4. 访问应用: http://localhost:3000

## 📁 项目结构

```
MeetBooking/
├── backend/          # Spring Boot 后端
├── frontend/         # Vue 前端
└── nginx.config      # Nginx 配置文件
```
