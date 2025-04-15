# MeetBooking

会议室预订系统

## 运行项目

### 1. 克隆项目

```bash
git clone https://github.com/YHCM/MeetBooking.git
```

### 2. 运行后端服务

```bash
cd backend
mvn spring-boot:run
```

### 3. 运行前端服务

```bash
cd frontend
pnpm install
pnpm dev

# 格式化
pnpm format
```

### 4. 访问前端页面

浏览器打开 [http://localhost:5173](http://localhost:5173)

## nginx 运行项目

### 1. 安装 nginx

nginx 下载页面 [https://nginx.org/en/download.html](https://nginx.org/en/download.html)

### 2. 启动 nginx

#### 使用 bash

```bash
nginx -c "$(pwd)/nginx.config"
```

#### 使用 cmd

```bash
nginx -c "%cd%\nginx.config"
```

#### 使用 powershell

```bash
nginx -c "$PWD\nginx.config"
```

### 3. 修改前端请求路径

把 /meetBooking/frontend/src/main.js 中的 `axios.defaults.baseURL` 值替换为 `/api`

### 4. 启动项目

按照前面的方法启动项目

### 5. 访问前端页面

浏览器打开 [http://localhost:3000](http://localhost:3000)
