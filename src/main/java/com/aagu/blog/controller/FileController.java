package com.aagu.blog.controller;

import com.aagu.blog.Common.ServerResponse;
import com.aagu.blog.Services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/file")
public class FileController {
    private static Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ServerResponse upload(@RequestParam("file")MultipartFile file) {
        return fileService.upload(file);
    }

    @DeleteMapping("/delete")
    public ServerResponse delete(@RequestParam("id")Integer id) {
        return fileService.delete(id);
    }
}
