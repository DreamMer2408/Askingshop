package com.askingshop.upload.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName UploadService
 * @Description TODO
 * @Author wangs
 * @Date 2019/7/5 23:39
 * @Version 1.0
 **/
@Service
public class UploadService {

    private static final Logger logger= LoggerFactory.getLogger(UploadService.class);

    //支持的文件类型
    private static final List<String> suffixes= Arrays.asList("image/png","image/jpeg");

    @Autowired
    FastFileStorageClient storageClient;

    public String upload(MultipartFile file){
        logger.info("开始上传图片");
        try {
            //1.图片信息校验
            //1)校验文件类型
            String type=file.getContentType();
            if (!suffixes.contains(type)){
                logger.info("上传失败，文件类型不匹配：{}",type);
                return null;
            }
            //2)校验图片内容
            BufferedImage image= ImageIO.read(file.getInputStream());
            if (image==null){
                logger.info("上传失败，文件内容不符合要求");
                return null;
            }
            //2.将图片上传到fastDFS
            //1)获取图片后缀名
            String extension= StringUtils.substringAfterLast(file.getOriginalFilename(),".");
            //2)上传
            StorePath storePath=storageClient.uploadFile(file.getInputStream(),file.getSize(),extension,null);

            //3)拼接图片地址
            String url="http://192.168.136.135:91/"+storePath.getFullPath();
            logger.info("上传成功，图片路径：{}",url);
            return url;
        }catch (Exception e){
            logger.info(e.getMessage());
            return null;
        }
    }
}
