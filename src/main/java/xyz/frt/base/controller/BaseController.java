package xyz.frt.base.controller;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import xyz.frt.base.util.AppConst;
import xyz.frt.base.util.BaseUtils;
import xyz.frt.base.util.JsonResult;
import xyz.frt.base.util.PageInfo;
import xyz.frt.base.model.BaseEntity;
import xyz.frt.base.service.BaseService;

import javax.servlet.ServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author phw
 * @date 4-22-2018
 * @description
 * @param <T>
 */
public abstract class BaseController<T extends BaseEntity> {

    private Map<String, Object> dataMap;

    public abstract BaseService<T> getService();

    JsonResult findItems() {
        List<T> items = getService().selectAll();
        if (BaseUtils.isNullOrEmpty(items)) {
            JsonResult.error("Didn't have any record");
        }
        dataMap = new HashMap<>();
        dataMap.put(AppConst.KEY_DATA, items);
        return JsonResult.success("Success", dataMap);
    }

    JsonResult findItems(PageInfo info) {
        if (BaseUtils.isNullOrEmpty(info.getPage()) || BaseUtils.isNullOrEmpty(info.getLimit())) {
            return JsonResult.error("Get params error");
        }
        info = getService().selectAllPage(info);
        dataMap = new HashMap<>();
        dataMap.put(AppConst.KEY_PAGE_INFO, info);
        return JsonResult.success("Success", dataMap);
    }

    JsonResult findItemByPrimaryKey(Integer id) {
        T item = getService().selectByPrimaryKey(id);
        if (BaseUtils.isNullOrEmpty(item)) {
            JsonResult.error("No such record");
        }
        dataMap = new HashMap<>();
        dataMap.put(AppConst.KEY_DATA, item);
        return JsonResult.success("Success", dataMap);
    }

    /**
     * 根据主键更新非空的记录
     * @param item 包含id的记录
     * @return 更新后的记录
     */
    JsonResult updateItemByPrimaryKey(T item) {
        item = getService().updateByPrimaryKey(item);
        if (BaseUtils.isNullOrEmpty(item)) {
            return JsonResult.error("Upgrade error");
        }
        dataMap = new HashMap<>();
        dataMap.put(AppConst.KEY_DATA, item);
        return JsonResult.success("Upgrade successful", dataMap);
    }

    JsonResult upgradeItemByPrimaryKey(Integer id, T item) {
        if (BaseUtils.isNullOrEmpty(item)) {
            return JsonResult.error("Get params error");
        }
        item.setId(id);
        item = getService().updateByPrimaryKey(item);
        if (BaseUtils.isNullOrEmpty(item)) {
            return JsonResult.error("Upgrade error");
        }
        dataMap = new HashMap<>();
        dataMap.put(AppConst.KEY_DATA, item);
        return JsonResult.success("Upgrade successful", dataMap);
    }


    JsonResult filesUpload(ServletRequest req) {
        MultipartHttpServletRequest multiReq = (MultipartHttpServletRequest) req;
        String[] filesPath = getService().filesUpload(req.getServletContext().getRealPath("/upload"), multiReq.getFiles("file"));
        if (BaseUtils.isNullOrEmpty(filesPath)) {
            return JsonResult.error("Files upload error");
        }
        dataMap = new HashMap<>();
        for (int i = 0; i < filesPath.length; i++) {
            dataMap.put("file" + i, filesPath[i]);
        }
        return JsonResult.success("Success", dataMap);
    }

    JsonResult fileUpload(ServletRequest req, MultipartFile file) {
        if (BaseUtils.isNullOrEmpty(file)) {
            return JsonResult.error("File upload error");
        }
        String filePath = getService().fileUpload(req.getServletContext().getRealPath("/upload"), file);
        if (BaseUtils.isNullOrEmpty(filePath)) {
            return JsonResult.error("File upload error");
        }
        dataMap = new HashMap<>();
        dataMap.put(AppConst.KEY_DATA, filePath);
        return JsonResult.success("File upload successful", dataMap);
    }

    JsonResult addItem(T item) {
        if (BaseUtils.isNullOrEmpty(item)) {
            return JsonResult.error("Get item error");
        }
        item = getService().insert(item);
        if (BaseUtils.isNullOrEmpty(item)) {
            return JsonResult.error("Save failed");
        }
        dataMap = new HashMap<>();
        dataMap.put(AppConst.KEY_DATA, item);
        return JsonResult.success("Insert item successful", dataMap);
    }


    JsonResult deleteItemByPrimaryKey(Integer id) {
        T item = getService().deleteByPrimaryKey(id);
        if (BaseUtils.isNullOrEmpty(item)) {
            return JsonResult.error("Didn't remove any item");
        }
        dataMap = new HashMap<>();
        dataMap.put(AppConst.KEY_DATA, item);
        return JsonResult.success("Remove successful", dataMap);
    }

    JsonResult findItemsByConditions(T item) {
        List<T> items = getService().selectByConditions(item);
        if (BaseUtils.isNullOrEmpty(items)) {
            return JsonResult.warn("Didn't have any item");
        }
        dataMap = new HashMap<>();
        dataMap.put(AppConst.KEY_DATA, items);
        return JsonResult.success("Success", dataMap);
    }

}
