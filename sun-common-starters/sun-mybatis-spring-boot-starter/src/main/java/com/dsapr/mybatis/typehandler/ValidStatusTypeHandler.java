package com.dsapr.mybatis.typehandler;

import com.dsapr.common.constants.ValidStatus;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// 使用纯注解方式，这里需要配置 MappedTypes 注解，不然会不使用对应处理器
// 从而导致给类型处理器不会生效，并出现`No enum constant...`异常等情况
@MappedTypes({ValidStatus.class})
@MappedJdbcTypes(value = JdbcType.INTEGER)
public class ValidStatusTypeHandler implements TypeHandler<ValidStatus> {
    // 设置参数，在数据库中存储的应该为状态码，这个方法用来对数据库进行操作时，给数据库传递枚举参数时传递的是状态码
    @Override
    public void setParameter(PreparedStatement ps, int i, ValidStatus validStatus, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, validStatus.getCode());
    }

    //用来查询操作，直接利用结果集返回，columnName为列名
    @Override
    public ValidStatus getResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        return ValidStatus.of(code).orElse(null);
    }

    //用来查询操作，直接利用结果集返回，columnIndex是列索引
    @Override
    public ValidStatus getResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        return ValidStatus.of(code).orElse(null);
    }

    //用来存储过程的查询操作，直接利用结果集返回，columnIndex是列索引
    @Override
    public ValidStatus getResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        return ValidStatus.of(code).orElse(null);
    }
}
