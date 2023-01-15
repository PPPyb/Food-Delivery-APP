<!--responsive/right-window.vue-->
<template>
  <view>
    <!-- 这里将 /pages/detail/detail.nvue 页面作为一个组件使用 -->
    <!-- 路径 “/pages/detail/detail” 转为 “pages-detail-detail” 组件 -->
    <pages-pcmatch-leftnavbar-leftnavbar ref="detailPage"></pages-pcmatch-leftnavbar-leftnavbar>
  </view>
</template>

<script>
  export default {
    created(e) {
      //监听自定义事件，该事件由详情页列表的点击触发
      uni.$on('updateDetail', (e) => {
        // 执行 detailPage组件，即：/pages/detail/detail.nvue 页面的load方法
        this.$refs.detailPage.load(e.detail);
      })
    },
    onLoad() {},
    methods: {}
  }
</script>

