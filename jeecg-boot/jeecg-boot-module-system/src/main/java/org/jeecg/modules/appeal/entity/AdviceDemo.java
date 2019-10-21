package org.jeecg.modules.appeal.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 测试2
 * @Author: jeecg-boot
 * @Date:   2019-09-25
 * @Version: V1.0
 */
@Data
@TableName("advice_demo")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="advice_demo对象", description="测试2")
public class AdviceDemo {

	/**主键*/
	@TableId(type = IdType.UUID)
	@ApiModelProperty(value = "主键")
	private java.lang.String id;
	/**创建人id*/
	@Excel(name = "创建人id", width = 15)
	@ApiModelProperty(value = "创建人id")
	private java.lang.String createBy;
	/**申诉产品id*/
	@Excel(name = "申诉产品id", width = 15)
	@ApiModelProperty(value = "申诉产品id")
	private java.lang.String productId;
	/**申诉类型*/
	@Excel(name = "申诉类型", width = 15)
	@ApiModelProperty(value = "申诉类型")
	private java.lang.String adviceType;
	/**创建日期*/
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建日期")
	private java.util.Date createTime;
	/**申诉内容*/
	@Excel(name = "申诉内容", width = 15)
	@ApiModelProperty(value = "申诉内容")
	private java.lang.String content;
	/**处理状态*/
	@Excel(name = "处理状态", width = 15)
	@ApiModelProperty(value = "处理状态")
	private java.lang.String processStatus;
	/**处理人id*/
	@Excel(name = "处理人id", width = 15)
	@ApiModelProperty(value = "处理人id")
	private java.lang.String updateBy;
	/**处理日期*/
	@Excel(name = "处理日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "处理日期")
	private java.util.Date updateTime;
	/**处理结果*/
	@Excel(name = "处理结果", width = 15)
	@ApiModelProperty(value = "处理结果")
	private java.lang.String result;
}
