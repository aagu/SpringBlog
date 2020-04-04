package com.aagu.blog.service;

import com.aagu.blog.Common.ServerResponse;
import com.aagu.blog.Models.Resource;
import com.aagu.blog.Views.ResourceVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface FileService {
    List<Resource> getAll();
    List<Resource> getByType(String type);
    ResourceVO getByPage(Integer page);
    ServerResponse upload(MultipartFile file);
    File download(Integer id);
    ServerResponse rename(Integer id);
    ServerResponse delete(Integer id);
    void updateRes(Resource resource);
}
