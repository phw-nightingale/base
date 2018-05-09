package xyz.frt.base.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.web.multipart.MultipartFile;
import xyz.frt.base.dao.BaseMapper;
import xyz.frt.base.model.BaseEntity;
import xyz.frt.base.service.BaseService;
import xyz.frt.base.util.BaseUtils;
import xyz.frt.base.util.PageInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

    public abstract BaseMapper<T> getMapper();

    @Override
    public T insert(T item) {
        if (BaseUtils.isNullOrEmpty(item)) {
            return null;
        }
        if (getMapper().insertSelective(item) == 0) {
            return null;
        }
        return getMapper().selectByPrimaryKey(item.getId());
    }

    @Override
    public List<T> insert(List<T> items) {
        if (BaseUtils.isNullOrEmpty(items)) {
            return null;
        }
        List<T> results = new ArrayList<>();
        for (T item: items) {
            if (getMapper().insertSelective(item) == 0) {
                System.out.println("Insert item " + item.toString() + " failed...");
                continue;
            }
            results.add(getMapper().selectByPrimaryKey(item.getId()));
        }
        return results;
    }

    @Override
    public T deleteByPrimaryKey(Integer id) {
        if (BaseUtils.isNullOrEmpty(id)) {
            return null;
        }
        T item = getMapper().selectByPrimaryKey(id);
        if (getMapper().deleteByPrimaryKey(id) == 0) {
            return null;
        }
        return item;
    }

    @Override
    public List<T> deleteByPrimaryKey(List<Integer> ids) {
        if (BaseUtils.isNullOrEmpty(ids)) {
            return null;
        }
        List<T> results = new ArrayList<>();
        for (Integer id: ids) {
            T item = getMapper().selectByPrimaryKey(id);
            if (BaseUtils.isNullOrEmpty(item)) {
                System.out.println("Item of id " + id + " is didn't exist...");
                continue;
            }
            results.add(item);
            getMapper().deleteByPrimaryKey(id);
        }
        return results;
    }

    @Override
    public List<T> deleteByConditions(T example) {
        Map<String, Object> conditions = BaseUtils.object2ConditionMap(example);
        if (BaseUtils.isNullOrEmpty(conditions)) {
            return null;
        }
        List<T> results = getMapper().selectByConditions(conditions);
        if (BaseUtils.isNullOrEmpty(results)) {
            return null;
        }
        if (getMapper().deleteByConditions(conditions) == 0) {
            return null;
        }
        return results;
    }

    @Override
    public T updateByPrimaryKey(T item) {
        if (BaseUtils.isNullOrEmpty(item)) {
            return null;
        }
        if (BaseUtils.isNullOrEmpty(item.getId())) {
            return null;
        }
        if (getMapper().updateByPrimaryKeySelective(item) == 0) {
            return null;
        }
        return getMapper().selectByPrimaryKey(item.getId());
    }

    @Override
    public List<T> updateByPrimaryKey(List<T> items) {
        if (BaseUtils.isNullOrEmpty(items)) {
            return null;
        }
        List<T> results = new ArrayList<>();
        for (T item: items) {
            if (BaseUtils.isNullOrEmpty(item.getId())) {
                System.out.println("Can't get id of item in list...");
                continue;
            }
            if (getMapper().updateByPrimaryKeySelective(item) == 0) {
                System.out.println("Update item of id " + item.getId() + " failed...");
                continue;
            }
            results.add(getMapper().selectByPrimaryKey(item.getId()));
        }
        return results;
    }

    @Override
    public List<T> updateByConditions(T values, T example) {
        Map<String, Object> valueMap = BaseUtils.object2ConditionMap(values);
        Map<String, Object> conditions = BaseUtils.object2ConditionMap(example);
        if (BaseUtils.isNullOrEmpty(valueMap) || BaseUtils.isNullOrEmpty(conditions)) {
            return null;
        }
        List<T> oldList = getMapper().selectByConditions(conditions);
        if (BaseUtils.isNullOrEmpty(oldList)) {
            return null;
        }
        if (getMapper().updateByConditions(valueMap, conditions) == 0) {
            return null;
        }
        List<T> newList = new ArrayList<>();
        for (T item: oldList) {
            newList.add(getMapper().selectByPrimaryKey(item.getId()));
        }
        return newList;
    }

    @Override
    public T load(T item) {
        Map<String, Object> conditions = BaseUtils.object2ConditionMap(item);
        if (BaseUtils.isNullOrEmpty(conditions)) {
            return null;
        }
        List<T> items = getMapper().selectByConditions(conditions);
        if (BaseUtils.isNullOrEmpty(items) && items.size() != 1) {
            return null;
        }
        return items.get(0);
    }

    @Override
    public List<T> selectAll() {
        return getMapper().selectAll();
    }

    @Override
    public List<T> selectAll(String orderBy) {
        if (BaseUtils.isNullOrEmpty(orderBy)) {
            return null;
        }
        return getMapper().selectAllOrder(orderBy);
    }

    @Override
    public List<T> selectAll(String orderBy, String sort) {
        if (BaseUtils.isNullOrEmpty(orderBy) && BaseUtils.isNullOrEmpty(sort)) {
            return null;
        }
        return getMapper().selectAllOrderAndSort(orderBy, sort);
    }

    @Override
    public T selectByPrimaryKey(Integer id) {
        if (BaseUtils.isNullOrEmpty(id)) {
            return null;
        }
        return getMapper().selectByPrimaryKey(id);
    }

    @Override
    public List<T> selectByConditions(T example) {
        Map<String, Object> conditions = BaseUtils.object2ConditionMap(example);
        if (BaseUtils.isNullOrEmpty(conditions)) {
            return null;
        }
        return getMapper().selectByConditions(conditions);
    }

    @Override
    public List<T> selectByConditions(T example, String orderBy) {
        Map<String, Object> conditions = BaseUtils.object2ConditionMap(example);
        if (BaseUtils.isNullOrEmpty(conditions)) {
            return null;
        }
        return getMapper().selectByConditionsOrder(conditions, orderBy);
    }

    @Override
    public List<T> selectByConditions(T example, String orderBy, String sort) {
        Map<String, Object> conditions = BaseUtils.object2ConditionMap(example);
        if (BaseUtils.isNullOrEmpty(conditions)) {
            return null;
        }
        if (!(sort.equalsIgnoreCase("asc") || sort.equalsIgnoreCase("desc"))) {
            System.out.println("Sort string must be equal to 'asc' or 'desc'...");
        }
        return getMapper().selectByConditionsOrderAndSort(conditions, orderBy, sort);
    }

    @Override
    public Integer selectCount() {
        return getMapper().selectCount();
    }

    @Override
    public Integer selectCount(T example) {
        Map<String, Object> conditions = BaseUtils.object2ConditionMap(example);
        if (BaseUtils.isNullOrEmpty(conditions)) {
            return null;
        }
        return getMapper().selectCount(conditions);
    }

    @Override
    public PageInfo<T> selectAllPage(PageInfo pageInfo) {
        if (BaseUtils.isNullOrEmpty(pageInfo)) {
            return null;
        }
        Integer page = pageInfo.getPage();
        Integer limit = pageInfo.getLimit();
        String orderBy = pageInfo.getOrderBy();
        String sort = pageInfo.getSort();
        List<T> data = new ArrayList<>();
        PageHelper.startPage(page, limit);
        if (BaseUtils.isNullOrEmpty(page)) {
            pageInfo.setPage(1);
        }
        if (BaseUtils.isNullOrEmpty(limit)) {
            pageInfo.setLimit(10);
        }
        if (BaseUtils.isNullOrEmpty(orderBy) && BaseUtils.isNullOrEmpty(sort)) {
            data = getMapper().selectAll();
        }
        if (!BaseUtils.isNullOrEmpty(orderBy) && BaseUtils.isNullOrEmpty(sort)) {
            data = getMapper().selectAllOrder(orderBy);
        }
        if (!BaseUtils.isNullOrEmpty(orderBy) && !BaseUtils.isNullOrEmpty(sort)) {
            data = getMapper().selectAllOrderAndSort(orderBy, sort);
        }
        Integer totalCount = getMapper().selectCount();
        Integer totalPage = totalCount / limit;
        return new PageInfo<>(page, totalPage, limit, totalCount, data);
    }

    @Override
    public List<T> selectAllLike(T example) {
        Map<String, Object> conditions = BaseUtils.object2ConditionMap(example);
        if (BaseUtils.isNullOrEmpty(conditions)) {
            return null;
        }
        return getMapper().selectAllLike(conditions);
    }

    @Override
    public PageInfo<T> selectAllPageLike(PageInfo pageInfo, T example) {
        Map<String, Object> conditions = BaseUtils.object2ConditionMap(example);
        if (BaseUtils.isNullOrEmpty(conditions)) {
            return null;
        }
        Integer page = pageInfo.getPage();
        Integer limit = pageInfo.getLimit();
        String orderBy = pageInfo.getOrderBy();
        String sort = pageInfo.getSort();
        List<T> data = new ArrayList<>();
        PageHelper.startPage(page, limit);
        if (BaseUtils.isNullOrEmpty(page)) {
            pageInfo.setPage(1);
        }
        if (BaseUtils.isNullOrEmpty(limit)) {
            pageInfo.setLimit(10);
        }
        if (BaseUtils.isNullOrEmpty(orderBy) && BaseUtils.isNullOrEmpty(sort)) {
            data = getMapper().selectAllLike(conditions);
        }
        if (!BaseUtils.isNullOrEmpty(orderBy) && BaseUtils.isNullOrEmpty(sort)) {
            data = getMapper().selectAllLikeOrder(conditions, orderBy);
        }
        if (!BaseUtils.isNullOrEmpty(orderBy) && !BaseUtils.isNullOrEmpty(sort)) {
            data = getMapper().selectAllLikeOrderAndSort(conditions, orderBy, sort);
        }
        Integer totalCount = getMapper().selectCount();
        Integer totalPage = totalCount / limit;
        return new PageInfo<>(page, totalPage, limit, totalCount, data);
    }

    @Override
    public String fileUpload(String uploadPath, MultipartFile file) {
        if (BaseUtils.isNullOrEmpty(file)) {
            return null;
        }
        String originalName = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + originalName.substring(originalName.lastIndexOf("."), originalName.length());
        try {
            File uploadFile = new File(uploadPath, fileName);
            if (uploadFile.exists()) {
                fileUpload(uploadPath, file);
                return null;
            }
            if (!uploadFile.getParentFile().exists()) {
                if (uploadFile.getParentFile().mkdirs()) {
                    return null;
                }
            }
            file.transferTo(uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return uploadPath + File.separator + fileName;
    }

    @Override
    public String[] filesUpload(String uploadPath, List<MultipartFile> files) {
        return new String[0];
    }
}
