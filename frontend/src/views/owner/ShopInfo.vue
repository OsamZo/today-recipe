<script setup>
import '@/assets/css/reset.css';
import ReadShop from "@/views/owner/ReadShop.vue";
import UpdateShop from "@/views/owner/UpdateShop.vue";
import {ref} from "vue";

const props = defineProps({
  shopData: {
    type: Object,
    required: true
  },
  propsComponent: {
    type: String,
    required: true
  }

});

const emit = defineEmits(['update-product', 'update-shopInfo']);

const forwardData = () => {
  emit('update-product');
};

const receivedShopData = ref({
  shopTel: '',
  shopAddress: '',
  shopIntroduction: ''
});
const forwardShopData = async (inputShopData) => {
  receivedShopData.value = inputShopData;
  // console.log(receivedShopData.value.shopTel);
  await emit('update-shopInfo', receivedShopData);
  console.log(props.propsComponent);
  showComponent(props.propsComponent);
};

const forwardEvent = () => {
  emit('delete-shop');
}

const showedComponent = ref('readShop');

const showComponent = (component) => {
  // console.log(component);
  showedComponent.value = component;
};

</script>

<template>
  <div class="total-content">
    <div class="page-content-title">
      <div class="menu-title">매장 정보</div>
      <button
          class="update"
          @click="showComponent('updateShop')">수정하기</button>
    </div>

    <ReadShop
        v-if="showedComponent === 'readShop'"
        @update-product="forwardData"
        @delete-shop="forwardEvent"
        :shopData="props.shopData"
    />
    <UpdateShop
        v-if="showedComponent === 'updateShop'"
        @update-shopInfo="forwardShopData"
        @canceled-update="showComponent('readShop')"
        :shopData="props.shopData"
    />

  </div>

</template>

<style scoped>
.total-content {
  width: 100%;
}
.page-content-title {
  display: flex;
  padding-bottom: 12px;
  border-bottom: 1px solid var(--button-brown);
  justify-content: space-between;
  align-items: center;
  margin-bottom: 130px;
}
.menu-title {
  font-size: 30px;
}
.update {
  margin-right: 10px;
  width: 116px;
  height: 42px;
  color: var(--text-white);
  border-radius: 10px;
  background: var(--button-brown);
  display: flex;
  justify-content: center;
  align-items: center;
  border: none;
  cursor: pointer;
}

</style>