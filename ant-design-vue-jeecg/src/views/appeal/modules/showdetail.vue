<template>
    <a-modal
      class="announcementCustomModal"
      :width="modelStyle.width"
      :visible="visible"
      :bodyStyle ="bodyStyle"
      @cancel="handleCancel"
      destroyOnClose
      :footer="null"
    >
      <template slot="title">
        <a-button icon="fullscreen" class="custom-btn" @click="handleClickToggleFullScreen"/>
      </template>
      <a-card class="daily-article" :loading="loading">
        <a-card-meta
          :title="'详情'"
          :description="'创建人：'+record.createBy +' 创建时间： ' + record.createTime">
        </a-card-meta>
        <a-divider />
        申诉产品id：<span v-html="record.productId" class="article-content"></span><br>
        申诉类型：<span v-html="record.adviceType" class="article-content"></span><br>
        申诉内容：<span v-html="record.content" class="article-content"></span><br><br>

        处理人：<span v-html="record.updateBy" class="article-content"></span><br>
        处理状态：{{dstatus(record)}}<br>
        处理时间：<span v-html="record.updateTime" class="article-content"></span><br>
        处理结果：<span v-html="record.result" class="article-content"></span>
      </a-card>
    </a-modal>
</template>

<script>
    export default {
        name: "showdetail",
        data() {
            return {
                record: {},
                visible: false,
                loading: false,
                labelCol: {
                    xs: { span: 24 },
                    sm: { span: 5 },
                },
                wrapperCol: {
                    xs: { span: 24 },
                    sm: { span: 16 },
                },
                bodyStyle:{
                    padding: "0",
                    height:(window.innerHeight*0.8)+"px",
                    "overflow-y":"auto",

                },
                modelStyle:{
                    width: '60%',
                    style: { top: '20px' },
                    fullScreen: false
                }
            }
        },
        methods: {
            handleClickToggleFullScreen() {
                let mode = !this.modelStyle.fullScreen
                if (mode) {
                    this.modelStyle.width = '100%'
                    this.modelStyle.style.top = '20px'
                } else {
                    this.modelStyle.width = '60%'
                    this.modelStyle.style.top = '50px'
                }
                this.modelStyle.fullScreen = mode
            },
            detail (record) {
                this.visible = true;
                this.record = record;
            },

            dstatus(record){
                if(record.processStatus=='0'){
                    return "未处理";
                }else if(record.processStatus=='1'){
                    return "已处理";
                }else{
                    return record.processStatus;
                }
            },

            handleCancel(e) {
                this.visible = false
            },
        }
    }
</script>

<style lang="less">
  .announcementCustomModal{
    .ant-modal-header {
      border: none;
      display: inline-block;
      position: absolute;
      z-index: 1;
      right: 56px;
      padding: 0;
      .ant-modal-title{
        .custom-btn{
          width: 56px;
          height: 56px;
          border: none;
          box-shadow: none;
        }
      }
    }
    .daily-article{
      border-bottom: 0;
    }
  }
</style>
<style scoped lang="less">
  .daily-article {
    .article-button {
      font-size: 1.2rem !important;
    }
    .ant-card-body {
      padding: 18px !important;
    }
    .ant-card-head {
      padding: 0 1rem;
    }
    .ant-card-meta {
      margin-bottom: 1rem;
    }
    .article-content {
      p {
        word-wrap: break-word;
        word-break: break-all;
        text-overflow: initial;
        white-space: normal;
        font-size: .9rem !important;
        margin-bottom: .8rem;
      }
    }
  }
</style>