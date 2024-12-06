package com.akash.gupta.BackendStoreCRUD.services.impl;

import com.akash.gupta.BackendStoreCRUD.exceptions.BadApiRequestException;
import com.akash.gupta.BackendStoreCRUD.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
@Service
public class FileServiceImpl implements FileService {
 private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException{
        String originalFilename = file.getOriginalFilename();
        logger.info("FileName: {}" , originalFilename);
//        we are randomly genrating file name becausse file name can be of same type
        String filename = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));        

        String fileNameWithExtension = filename+extension;
    // 121.png
        String fullPathWithFileName = path + fileNameWithExtension;

        if(extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpeg")  || extension.equalsIgnoreCase(".jpg"))
        {

           // FILE SAVE
//           ek path ka object pass kar diya
            File folder = new File(path);


            if(!folder.exists()){
//               and then we created the folder here
               folder.mkdirs();

            }
           // file UPLODED
            Files.copy(file.getInputStream() , Paths.get(fullPathWithFileName));
           return fileNameWithExtension;

        }else{
            throw new BadApiRequestException("File with extension "+ extension + " not allowed");


        }


//        if we have abc.png then we are appending the uuid through last index of dot


    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {

        String  fullPath = path + File.separator + name;

        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;


    }
}
