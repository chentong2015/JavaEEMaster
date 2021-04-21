package com.ctong.main.mapper;

import com.ctong.main.model.Blog;
import com.ctong.main.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

// 映射器是一些绑定映射语句的接口: 使用Java注解将"实体类型"和"SQL语句"进行映射
public interface BlogMapper {

    // 对于此注解中复杂的SQL语句，最好使用XML来完成映射
    @Select("SELECT * FROM blog WHERE id = #{id}")
    Blog selectBlog(int id);

    //  ${column}会被直接替换，而#{value}会使用 ? 预处理
    @Select("select * from user where ${column} = #{value}")
    User findByColumn(@Param("column") String column, @Param("value") String value);
}
