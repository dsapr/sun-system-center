package com.dsapr.codegen.processor.vo;

import com.dsapr.mybatis.support.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AbstractBaseVO {
    @Schema(
            title = "数据版本"
    )
    private int version;
    @Schema(
        title = "主键"
    )
    private Long id;
    @Schema(
            title = "创建时间"
    )
    private LocalDateTime createdTime;
    @Schema(
            title = "修改时间"
    )
    private LocalDateTime updatedTime;

    protected AbstractBaseVO(BaseEntity source) {
        this.setVersion(source.getVersion());
        this.setId(source.getId());
        this.setCreatedTime(source.getCreatedTime());
        this.setUpdatedTime(source.getUpdatedTime());
    }

    protected AbstractBaseVO() {
    }
}
