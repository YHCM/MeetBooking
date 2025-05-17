import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
// import vueDevTools from 'vite-plugin-vue-devtools'
import path from 'path'

// 配置 element-plus 自动导入
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'

// https://cn.vite.dev/config/
export default defineConfig({
  server: {
    allowedHosts: true,
  },
  plugins: [
    vue(),
    // vueDevTools(),
    AutoImport({
      resolvers: [ElementPlusResolver()],
      imports: [
        {
          '@/composables/useApi': ['useApi'], // 自动导入 useApi
        },
      ],
    }),
    Components({
      resolvers: [ElementPlusResolver()],
    }),
  ],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, 'src'),
    },
  },
})
