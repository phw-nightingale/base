package xyz.frt.base.dao;

import org.apache.ibatis.annotations.Param;
import xyz.frt.base.model.BaseEntity;

import java.util.List;
import java.util.Map;

/**
 * @author phw
 * @date 5-3-2018
 * @description Mapper基类，定义基本的数据库操作方法
 * @param <T>
 */
public interface BaseMapper<T extends BaseEntity> {

    Integer insert(T item);

    Integer insertSelective(T item);

    T selectByPrimaryKey(Integer id);

    Integer deleteByPrimaryKey(Integer id);

    Integer deleteByConditions(@Param("map") Map<String, Object> conditions);

    List<T> selectAll();

    List<T> selectAllOrder(@Param("orderBy") String orderBy);

    List<T> selectAllOrderAndSort(@Param("orderBy") String orderBy, @Param("sort") String sort);

    Integer updateByPrimaryKeySelective(T item);

    Integer updateByPrimaryKey(T item);

    Integer updateByConditions(@Param("entry") Map<String, Object> entry, @Param("conditions") Map<String, Object> conditions);

    List<T> selectByConditions(@Param("map") Map<String, Object> conditions);

    List<T> selectByConditionsOrder(@Param("map") Map<String, Object> conditions, @Param("orderBy") String orderBy);

    List<T> selectByConditionsOrderAndSort(@Param("map") Map<String, Object> conditions, @Param("orderBy") String orderBy, @Param("sort") String sort);

    Integer selectCount();

    Integer selectCount(@Param("map") Map<String, Object> conditions);

    List<T> selectAllLike(@Param("map") Map<String, Object> conditions);

    List<T> selectAllLikeOrder(@Param("map") Map<String, Object> conditions, @Param("orderBy") String orderBy);

    List<T> selectAllLikeOrderAndSort(@Param("map") Map<String, Object> conditions, @Param("orderBy") String orderBy, @Param("sort") String sort);


}
