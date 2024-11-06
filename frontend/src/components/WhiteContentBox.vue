<template>
    <div class="page-content">
        <slot></slot>
    </div>
</template>
<style scoped>
.page-content {
    width: 80vw;
    height: auto; 
    background-color: #ffffff;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    justify-content: flex-start;
    padding-top: 5vh;
    padding-left: 5vw; 
    padding-right: 5vw;
    margin: 0 auto;
    margin-top: 20vh;
    box-sizing: border-box;
    position: relative;
}
</style>