package com.aagu.blog.service.impl;

import com.aagu.blog.common.Const;
import com.aagu.blog.common.ServerResponse;
import com.aagu.blog.controller.FileController;
import com.aagu.blog.Dao.ResourceDao;
import com.aagu.blog.Models.Resource;
import com.aagu.blog.service.FileService;
import com.aagu.blog.view.ResourceVO;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    private static Logger logger = LoggerFactory.getLogger(FileController.class);

    @Value("${file.path}")
    private String filePath;

    @Autowired
    private ResourceDao resourceDao;

    @Override
    public List<Resource> getAll() {
        return resourceDao.getAll();
    }

    @Override
    public List<Resource> getByType(String type) {
        String selector = null;
        if (type.equalsIgnoreCase("img")) selector = "img";
        else if(type.equalsIgnoreCase("doc")) selector = "doc";
        else if (type.equalsIgnoreCase("arh")) selector = "arh";
        if (selector == null) return null;
        return resourceDao.getByType(selector);
    }

    @Override
    public ResourceVO getByPage(Integer page) {
        ResourceVO resourceVO = new ResourceVO();
        resourceVO.setResources(resourceDao.getByPage((page-1)*Const.RESOURCE_PAGE_LEN, Const.RESOURCE_PAGE_LEN));
        resourceVO.setPage(page);
        return resourceVO;
    }

    @Override
    public ServerResponse upload(MultipartFile file) {
        String type = file.getContentType();
        type = type.split("/")[0];
        logger.info(type);
        File dest = null;
        switch (type) {
            case "image":
                dest = new File(filePath+"/image/"+file.getOriginalFilename());
                break;
            default:
                break;
        }
        if (dest != null) {
            try {
                FileUtils.copyInputStreamToFile(file.getInputStream(), dest);
                resourceDao.insert(type, "/"+type+"/"+file.getOriginalFilename(), type+file.getName());
                return ServerResponse.createBySuccess();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ServerResponse.createError();
    }

    @Override
    public File download(Integer id) {
        return null;
    }

    @Override
    public ServerResponse rename(Integer id) {
        return null;
    }

    @Override
    public ServerResponse delete(Integer id) {
        return null;
    }

    @Override
    public void updateRes(Resource resource) {
        resourceDao.updateStatus(resource.getId(), resource.getStatus());
    }
}
