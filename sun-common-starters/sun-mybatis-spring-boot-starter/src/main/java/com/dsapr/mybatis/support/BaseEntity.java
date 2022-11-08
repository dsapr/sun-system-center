package com.dsapr.mybatis.support;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    @Setter(AccessLevel.PROTECTED)
    private Long id;

    @TableField(value = "created_time", fill = FieldFill.INSERT)
    @Setter(AccessLevel.PROTECTED)
    private LocalDateTime createdTime;

    @TableField(value = "updated_time", fill = FieldFill.UPDATE)
    @Setter(AccessLevel.PROTECTED)
    private LocalDateTime updatedTime;

    @Version
    @TableField(value = "version")
    @Setter(AccessLevel.PRIVATE)
    private Integer version;

}
