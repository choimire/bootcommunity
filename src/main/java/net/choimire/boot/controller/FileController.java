package net.choimire.boot.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.choimire.boot.entity.FileEntity;
import net.choimire.boot.service.FileService;
import net.choimire.boot.util.FileUploadUtil;

import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class FileController {

    private final FileService fileService;
    private final String uploadPath = "C:\\mirechoi\\boot_works\\boot\\src\\main\\resources\\static\\upload";

    public FileController(FileService fileService){
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public Map<String, Object>fileUpload(
        @RequestParam("file")MultipartFile file,
        @RequestParam("fileKey")Long fileKey)
        throws IOException{
        FileEntity entity = FileUploadUtil.saveFile(fileKey,file,uploadPath);

        //db저장
        fileService.save(entity);
            System.out.println(entity.getNewFilename());
        Map<String,Object> data = new HashMap<>();
        data.put("fileId", entity.getId());
        data.put("nfilename", entity.getNewFilename());
        data.put("fileSize", entity.getFileSize());
        data.put("ext", entity.getExt());
        data.put("fileUrl", "/upload/" +entity.getNewFilename());
        return data;
  
    }
    
}
