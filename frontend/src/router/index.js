import { createWebHistory, createRouter } from 'vue-router'

import HomeView from '@/views/HomeView.vue'
import AboutView from '@/views/AboutView.vue'
import LoginView from '@/views/LoginView.vue'
import ProfileView from '@/views/ProfileView.vue'
import RegisterView from '@/views/RegisterView.vue'
import UserManagementView from '@/views/UserManagementView.vue'
import RegistrationRequestsView from '@/views/RegistrationRequestsView.vue'
import RoomManagementView from '@/views/RoomManagementView.vue'
import EquipmentManagementView from '@/views/EquipmentManagementView.vue'
import RoomView from '@/views/RoomView.vue'
import ReserveRoomView from '@/views/ReserveRoomView.vue'
import MyOrderView from '@/views/MyOrderView.vue'
import RefundManagementView from '@/views/RefundManagementView.vue'
import MyRefundView from '../views/MyRefundView.vue'

const routes = [
  { path: '/', component: HomeView },
  { path: '/about', component: AboutView },
  { path: '/login', component: LoginView },
  { path: '/register', component: RegisterView },
  { path: '/users', component: UserManagementView },
  { path: '/requests', component: RegistrationRequestsView },
  { path: '/profile', component: ProfileView },
  { path: '/rooms/manage', component: RoomManagementView },
  { path: '/equipments', component: EquipmentManagementView },
  { path: '/rooms', component: RoomView },
  { path: '/reserve', component: ReserveRoomView },
  { path: '/orders', component: MyOrderView },
  { path: '/admin/refunds', component: RefundManagementView },
  { path: '/refunds', component: MyRefundView },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
