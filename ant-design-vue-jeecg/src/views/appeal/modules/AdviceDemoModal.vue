<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">

    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item
          v-has="'audithidden'"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="创建人id">
          <a-input :disabled="isDisabledAuth('create')"  placeholder="请输入创建人id" v-decorator="['createBy', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="申诉产品id">
          <a-input :disabled="isDisabledAuth('create')"  placeholder="请输入申诉产品id" v-decorator="['productId', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="申诉类型">
          <a-input :disabled="isDisabledAuth('create')" placeholder="请输入申诉类型" v-decorator="['adviceType', {}]" />
        </a-form-item>

        <a-form-item
          v-has="'audithidden'"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="创建日期">
          <!--          <a-input :disabled="isDisabledAuth('test:can')" placeholder="请输入创建日期" v-decorator="['createTime', {}]" />-->
          <a-date-picker
            showTime
            format="YYYY-MM-DD HH:mm:ss"
            placeholder="请输入创建日期"
            @change="onChange"
            @ok="onOk"
            :disabled="isDisabledAuth('create')" v-decorator="['createTime', {}]" style="width: 100%" />
        </a-form-item>
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="创建日期">-->
<!--          <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'createTime', {}]" />-->
<!--        </a-form-item>-->
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="申诉内容">
          <a-input :disabled="isDisabledAuth('create')" placeholder="请输入申诉内容" v-decorator="['content', {}]" />
        </a-form-item>
        <a-form-item
          v-has="'audithidden'"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="处理状态">
          <a-select  placeholder="请选择处理状态"   v-decorator="['processStatus', {}]" default-value="0">
            <a-select-option v-for="(item, key) in dealstatus" :key="key" :value="item.value">
              <span style="display: inline-block;width: 100%" :title=" item.text || item.label ">
                {{ item.text || item.label }}
              </span>
            </a-select-option>
<!--            <a-select-option value="0">未处理</a-select-option>-->
<!--            <a-select-option value="1">正在处理</a-select-option>-->
<!--            <a-select-option value="2">处理完成</a-select-option>-->
          </a-select>
        </a-form-item>
<!--        <a-form-item-->
<!--          v-has="'audithidden'"-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="处理人id">-->
<!--          <a-input  :disabled="isDisabledAuth('audit')" placeholder="请输入处理人id" v-decorator="['updateBy', {}]" />-->
<!--        </a-form-item>-->

<!--        <a-form-item-->
<!--          v-has="'audithidden'"-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="处理日期">-->
<!--          &lt;!&ndash;          <a-input placeholder="请输入处理日期" v-decorator="['updateTime', {}]" />&ndash;&gt;-->
<!--          <a-date-picker-->
<!--            showTime-->
<!--            format="YYYY-MM-DD HH:mm:ss"-->
<!--            placeholder="请输入处理日期"-->
<!--            @change="onChange"-->
<!--            @ok="onOk"-->
<!--            :disabled="isDisabledAuth('audit')"-->
<!--            v-decorator="['updateTime', {}]" style="width: 100%" />-->
<!--        </a-form-item>-->
        <a-form-item
          v-has="'audithidden'"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="处理结果">
          <a-input placeholder="请输入处理结果"  v-decorator="['result', {}]" />
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { httpAction} from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"
  import { disabledAuthFilter } from "@/utils/authFilter"
  import {initDictOptions, filterDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: "AdviceDemoModal",
    data () {
      return {
        visible: false,
        dealstatus:[],
        title:"操作",
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
        },
        url: {
          add: "/test2/adviceDemo/add",
          edit: "/test2/adviceDemo/edit",
        },
      }
    },
      created(){
          this.initDictConfig();
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
        isDisabledAuth(code){
            return disabledAuthFilter(code);
        },

        onChange(value, dateString) {
            console.log('Selected Time: ', value);
            console.log('Formatted Selected Time: ', dateString);
        },
        onOk(value) {
            console.log('onOk: ', value);
        },

      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'createBy','productId','adviceType','content','processStatus','updateBy','result'))
		  //时间格式化
          this.form.setFieldsValue({createTime:this.model.createTime?moment(this.model.createTime):null})
          this.form.setFieldsValue({updateTime:this.model.updateTime?moment(this.model.updateTime):null})
        });
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            //时间格式化
            formData.createTime = formData.createTime?formData.createTime.format('YYYY-MM-DD HH:mm:ss'):null;
            formData.updateTime = formData.updateTime?formData.updateTime.format('YYYY-MM-DD HH:mm:ss'):null;

            console.log(formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })



          }
        })
      },
      handleCancel () {
        this.close()
      },


    }
  }
</script>

<style lang="less" scoped>

</style>