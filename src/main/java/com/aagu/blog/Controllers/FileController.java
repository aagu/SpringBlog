package com.aagu.blog.Controllers;

import com.aagu.blog.Common.ServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@RestController
@RequestMapping("/file")
public class FileController {
    private static Logger logger = LoggerFactory.getLogger(FileController.class);

    @Value("${file.path}")
    private String filePath;

    @PostMapping("/upload")
    public ServerResponse upload(@RequestParam("file")MultipartFile file) {
        String type = file.getContentType();
        type = type.split("/")[0];
        logger.info(filePath);
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
            if (!dest.getParentFile().exists()) dest.getParentFile().mkdirs();
            try {
                file.transferTo(dest);
                return ServerResponse.createBySuccess();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ServerResponse.createError();
    }
}
