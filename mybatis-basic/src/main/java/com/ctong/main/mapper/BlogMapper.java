package com.ctong.main.mapper;

import com.ctong.main.model.Blog;
import org.apache.ibatis.annotations.Select;

// 映射器是一些绑定映射语句的接口
// 使用Java注解将"实体类型"和"SQL语句"进行映射
public interface BlogMapper {

    // 对于此注解中复杂的SQL语句，最好使用XML来完成映射
    @Select("SELECT * FROM blog WHERE id = #{id}")
    Blog selectBlog(int id);
}
