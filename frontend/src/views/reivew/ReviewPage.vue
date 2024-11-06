<template>
  <div class="page-content">
    <ShopCard />
  </div>
</template>

<script>
import ShopCard from '@/components/ShopCard.vue';

export default {
  components: {
    ShopCard
  }
};
</script>

<style scoped>
.page-content {
  padding: 20px;
}
</style>
