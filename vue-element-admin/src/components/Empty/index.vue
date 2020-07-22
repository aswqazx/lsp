<template>
  <div class="empty">
    <svg-icon icon-class="empty" style="width: 100%; height: 100px" />
    <span>暂无数据</span>
  </div>
</template>

<script>
export default {
  name: 'Empty',
  props: {},
  data() {
    return {}
  },
  created() {},
  mounted() {},
  methods: {}
}
</script>

<style lang="scss" scoped>
  .empty {
    margin: 20px 0 20px 0;
  }
</style>
