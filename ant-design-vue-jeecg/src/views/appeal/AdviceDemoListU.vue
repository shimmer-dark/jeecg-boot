<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :md="6" :sm="8">
            <a-form-item label="创建人id">
              <a-input placeholder="请输入创建人id" v-model="queryParam.createBy"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="申诉产品id">
              <a-input placeholder="请输入申诉产品id" v-model="queryParam.productId"></a-input>
            </a-form-item>
          </a-col>
        <template v-if="toggleSearchStatus">
        <a-col :md="6" :sm="8">
            <a-form-item label="申诉类型">
              <a-input placeholder="请输入申诉类型" v-model="queryParam.adviceType"></a-input>
            </a-form-item>
          </a-col>
<!--          <a-col :md="6" :sm="8">-->
<!--            <a-form-item label="创建日期">-->
<!--              <a-date-picker-->
<!--                v-model="queryParam.createTime"-->
<!--                showTime-->
<!--                format="YYYY-MM-DD HH:mm:ss"-->
<!--                placeholder="请输入创建日期"-->
<!--                style="width: 100%" />-->
<!--            </a-form-item>-->
<!--          </a-col>-->
<!--          <a-col :md="6" :sm="8">-->
<!--            <a-form-item label="申诉内容">-->
<!--              <a-input placeholder="请输入申诉内容" v-model="queryParam.content"></a-input>-->
<!--            </a-form-item>-->
<!--          </a-col>-->

          <a-col :md="6" :sm="8">
            <a-form-item label="处理状态">
              <a-select v-model="queryParam.processStatus" placeholder="请选择处理状态" default-value="0">
                <a-select-option v-for="(item, key) in dealstatus" :key="key" :value="item.value">
                  <span style="display: inline-block;width: 100%" :title=" item.text || item.label ">
                    {{ item.text || item.label }}
                  </span>
                </a-select-option>
<!--                <a-select-option value="0">未处理</a-select-option>-->
<!--                <a-select-option value="1">正在处理</a-select-option>-->
<!--                <a-select-option value="2">处理完成</a-select-option>-->
              </a-select>
            </a-form-item>
          </a-col>

          <!--            <a-form-item label="处理状态">-->
          <!--              <a-input placeholder="请输入处理状态" v-model="queryParam.processStatus"></a-input>-->
          <!--            </a-form-item>-->
          <a-col :md="6" :sm="8">
            <a-form-item label="处理人id">
              <a-input placeholder="请输入处理人id" v-model="queryParam.updateBy"></a-input>
            </a-form-item>
          </a-col>

<!--          <a-col :md="6" :sm="8">-->
<!--            <a-form-item label="处理日期">-->
<!--              <a-date-picker-->
<!--                showTime-->
<!--                format="YYYY-MM-DD HH:mm:ss"-->
<!--                placeholder="请输入处理日期"-->
<!--                v-model="queryParam.updateTime" style="width: 100%" />-->
<!--            </a-form-item>-->
<!--          </a-col>-->

<!--          <a-col :md="6" :sm="8">-->
<!--            <a-form-item label="处理结果">-->
<!--              <a-input placeholder="请输入处理结果" v-model="queryParam.result"></a-input>-->
<!--            </a-form-item>-->
<!--          </a-col>-->


          </template>
          <a-col :md="6" :sm="8" >
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" v-has="'add'" type="primary" icon="plus">新增</a-button>
      <a-button @click="handleProcessed" type="primary">已处理</a-button>
      <a-button @click="handleUntreated" type="primary">未处理</a-button>
<!--      <a-button type="primary" icon="download" @click="handleExportXls('测试2')">导出</a-button>-->
<!--      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">-->
<!--        <a-button type="primary" icon="import">导入</a-button>-->
<!--      </a-upload>-->
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <span slot="action" slot-scope="text, record">
          <a v-has="'test:hello'" @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" v-has="'test:hello'" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                  <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <adviceDemo-modal ref="modalForm" @ok="modalFormOk"></adviceDemo-modal>
    <showdetail ref="modalForm2" @ok="modalFormOk2"></showdetail>
  </a-card>
</template>

<script>
  import AdviceDemoModal from './modules/AdviceDemoModal'
  import showdetail from './modules/showdetail'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import {initDictOptions, filterDictText} from '@/components/dict/JDictSelectUtil'
  import {getAction} from '@/api/manage'

  export default {
    name: "AdviceDemoList",
    mixins:[JeecgListMixin],
    components: {
      AdviceDemoModal,
      showdetail
    },
    data () {
      return {
        dealstatus:[],
        description: '测试2管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
           },
		   {
            title: '创建人id',
            align:"center",
            dataIndex: 'createBy'
           },
		   {
            title: '申诉产品id',
            align:"center",
            dataIndex: 'productId'
           },
		   {
            title: '申诉类型',
            align:"center",
            dataIndex: 'adviceType'
           },
		   {
            title: '创建日期',
            align:"center",
            dataIndex: 'createTime'
           },
		   {
            title: '申诉内容',
            align:"center",
            dataIndex: 'content'
           },
		   {
            title: '处理状态',
            align:"center",
            dataIndex: 'processStatus',
           customRender:(text, record, index) => {
                return filterDictText(this.dealstatus,text);
               // if(text=='0'){
               //     return "未处理";
               // }else if(text=='1'){
               //     return "正在处理";
               // }else if(text=='2'){
               //     return "处理完成";
               // }else{
               //     return text;
               // }


               // switch(text)
               // {
               //     case '0': {return "未处理";}
               //     case '1': {return "正在处理";}
               //     case '2': {return "处理完成";}
               // }
           }
           },
		   {
            title: '处理人id',
            align:"center",
            dataIndex: 'updateBy'
           },
		   {
            title: '处理日期',
            align:"center",
            dataIndex: 'updateTime'
           },
		   {
            title: '处理结果',
            align:"center",
            dataIndex: 'result'
           },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
		url: {
          list: "/test2/adviceDemo/user-list",
          delete: "/test2/adviceDemo/delete",
          deleteBatch: "/test2/adviceDemo/deleteBatch",
          exportXlsUrl: "test2/adviceDemo/exportXls",
          importExcelUrl: "test2/adviceDemo/importExcel",
       },
    }
  },

      created(){
          this.initDictConfig();
      },
  computed: {
    importExcelUrl: function(){
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    }
  },
    methods: {
        initDictConfig() {
            //初始化字典 - 处理状态
            initDictOptions('deal_status').then((res) => {
                if (res.success) {
                    this.dealstatus = res.result;
                }
            });
        },
        handleProcessed: function() {
            var params ={processStatus:'1'};//查询条件
            this.loading = true;
            getAction(this.url.list, params).then((res) => {
                if (res.success) {
                    this.dataSource = res.result.records;
                    this.ipagination.total = res.result.total;
                }
                if(res.code===510){
                    this.$message.warning(res.message)
                }
                this.loading = false;
            })
        },

        handleUntreated: function() {
            var params ={processStatus:'0'};//查询条件
            this.loading = true;
            getAction(this.url.list, params).then((res) => {
                if (res.success) {
                    this.dataSource = res.result.records;
                    this.ipagination.total = res.result.total;
                }
                if(res.code===510){
                    this.$message.warning(res.message)
                }
                this.loading = false;
            })
        },

        handleDetail: function (record) {
            // getAction(this.url.detail,{createBy:record.createBy}).then((res)=>{
            //     if(res.success){
            //         this.loadData();
            //     }
            // });
            this.$refs.modalForm2.detail(record);
        },

        handleOk(e) {
            this.confirmLoading = true;
        },
        handleCancel(e) {
            console.log('Clicked cancel button');
            this.visible = false
        },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>