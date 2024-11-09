<script setup>
import Header from "@/components/Header.vue";
import { onMounted } from 'vue';
import { useUserStore } from '@/store/UserStore';

const userStore = useUserStore();

onMounted(() => {
  userStore.restoreSession(); // 앱 시작 시 세션 복원
});
</script>

<template>
  <div>
    <Header /> 
    <router-view />
  </div>
</template>
