package net.choimire.boot.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import net.choimire.boot.entity.FileEntity;

public class FileUploadUtil {

    public static FileEntity saveFile(Long bid, MultipartFile file, String uploadDir)
            throws IOException {

        String oFilename = file.getOriginalFilename();
        String ext = oFilename.substring(oFilename.lastIndexOf(".") + 1).toLowerCase();
        String nFilename = UUID.randomUUID() + "." + ext;
        File targetFile = new File(uploadDir, nFilename);
        file.transferTo(targetFile);

        FileEntity fileEntity = new FileEntity();
        fileEntity.setBid(bid);
        fileEntity.setOldFilename(oFilename);
        fileEntity.setNewFilename(nFilename);
        fileEntity.setExt(ext);
        fileEntity.setFileSize(file.getSize());

        return fileEntity;
    }

}
