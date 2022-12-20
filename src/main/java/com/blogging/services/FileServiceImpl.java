package com.blogging.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile multipartFile) throws IOException {

        //File Name
        String fileName = multipartFile.getOriginalFilename();

        //Random File Name
        String uid = UUID.randomUUID().toString();
        String uniqueFileName = uid.concat(fileName.substring(fileName.lastIndexOf(".")));

        //Full Path
        String filePath = path + File.separator + uniqueFileName;

        //Create Folder if No Created
        File file = new File(path);
        if (!file.exists())
        {
            file.mkdir();
        }

        //File Copy
        Files.copy(multipartFile.getInputStream() , Paths.get(filePath));

        return uniqueFileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        InputStream is = new FileInputStream(fullPath);
        return is;
    }
}
