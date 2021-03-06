package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.renren.common.utils.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统权限
 *
 * @author Shark
 * @email shark@126.com
 * @date 2019-07-19 12:54:22
 */
@Data
@TableName("sys_authorities")
public class SysAuthoritiesEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "ID", type = IdType.UUID)
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 中文名称
     */
    private String cnName;
    /**
     * SPRING权限名称
     */
    private String authorities;
    /**
     * 是否系统权限
     */
    private String isSys;

}
