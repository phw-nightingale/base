package xyz.frt.base.service;

import org.springframework.web.multipart.MultipartFile;
import xyz.frt.base.model.BaseEntity;
import xyz.frt.base.util.PageInfo;

import java.util.List;

public interface BaseService<T extends BaseEntity> {

    /**
     * 插入一条记录
     * @param item 记录
     * @return 记录
     */
    T insert(T item);

    /**
     * 插入记录集合
     * @param items 记录集合
     * @return 记录集合
     */
    List<T> insert(List<T> items);

    /**
     * 根据主键删除一条记录
     * @param id 主键
     * @return 被删除的记录
     */
    T deleteByPrimaryKey(Integer id);

    /**
     * 根据主键集合删除记录
     * @param ids 主键集合
     * @return 被删除的记录集合
     */
    List<T> deleteByPrimaryKey(List<Integer> ids);

    /**
     * 根据删除记录
     * @param example 条件
     * @return
     */
    List<T> deleteByConditions(T example);

    /**
     * 根据主键以及非空属性更新
     * @param item 条件
     * @return 更新后的记录
     */
    T updateByPrimaryKey(T item);

    /**
     * 根据主键以及非空属性更新列表
     * 不适合大批量操作
     * @param items 条件列表
     * @return 更新后的记录列表
     */
    List<T> updateByPrimaryKey(List<T> items);

    /**
     * 条件更新
     * @param values 目标值
     * @param example 条件
     * @return 更新后的记录列表
     */
    List<T> updateByConditions(T values, T example);

    /**
     * 根据条件加载一个对象
     * 若符合该条件的对象有一个或多个
     * 那么就返回null
     * @param example 条件
     * @return 单个记录
     */
    T load(T example);

    /**
     * 选择所有记录
     * @return 所有记录列表
     */
    List<T> selectAll();

    /**
     * 选择所有记录并排序
     * @param orderBy 排序列名
     * @return 排序后的记录列表
     */
    List<T> selectAll(String orderBy);

    /**
     * 选择所有记录并按照指定的顺序和列名排序
     * @param orderBy 排序列名
     * @param sort 顺序
     * @return 排序后的记录列表
     */
    List<T> selectAll(String orderBy, String sort);

    /**
     * 根据id返回一条记录
     * @param id 主键
     * @return 记录
     */
    T selectByPrimaryKey(Integer id);

    /**
     * 按照条件选择记录
     * @param example 条件
     * @return 记录列表
     */
    List<T> selectByConditions(T example);

    /**
     * 选择符合条件的记录并排序
     * @param example 条件
     * @param orderBy 列名
     * @return 记录列表
     */
    List<T> selectByConditions(T example, String orderBy);

    /**
     * 选择符合条件的记录并按照规定顺序排序
     * @param example 条件
     * @param orderBy 排序列名
     * @param sort 顺序
     * @return 记录列表
     */
    List<T> selectByConditions(T example, String orderBy, String sort);

    /**
     * 获取所有记录数量
     * @return 数量
     */
    Integer selectCount();

    /**
     * 获取符合条件的记录数量
     * @param example 条件
     * @return 数量
     */
    Integer selectCount(T example);

    /**
     * 获取所有分页记录
     * @param pageInfo 分页信息
     * @return 分页数据
     */
    PageInfo<T> selectAllPage(PageInfo pageInfo);

    /**
     * 对所有记录进行模糊查询
     * @param example 条件
     * @return 记录列表
     */
    List<T> selectAllLike(T example);

    /**
     * 对所有记录进行模糊查询并分页
     * @param pageInfo 分页信息
     * @param example 条件
     * @return 分页数据
     */
    PageInfo<T> selectAllPageLike(PageInfo pageInfo, T example);

    /**
     * 单文件上传
     * @param uploadPath 上传路径
     * @param file 上传文件
     * @return 文件url
     */
    String fileUpload(String uploadPath, MultipartFile file);

    /**
     * 多文件上传
     * @param uploadPath 上传路径
     * @param files 文件列表
     * @return 文件url列表
     */
    String[] filesUpload(String uploadPath, List<MultipartFile> files);

}
