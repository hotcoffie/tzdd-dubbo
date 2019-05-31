package com.ttit.common.utils;

import com.ttit.common.entity.Attachment;
import com.ttit.common.exceptions.BusinessException;
import com.ttit.common.exceptions.NotExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Description:
 *
 * @author 小谢
 * Date: 2019/5/3113:54
 */
@Slf4j
public class FileUploadUtils {
    public static Attachment uploud(MultipartFile webFile, String fileUploadPath, String userId) {
        //1.有效性验证
        if (webFile.isEmpty()) {
            throw new NotExistException("上传了空文件");
        }

        //2.上传文件
        String fileName = webFile.getOriginalFilename();
        String attaId = UuidUtils.generate();
        File dir = new File(fileUploadPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File localFile = new File(dir, attaId);
//        try {
//            localFile.createNewFile();
//        } catch (IOException e) {
//            log.error("附件上传失败！", e);
//            throw new BusinessException("附件上传失败！");
//        }
        try (
                InputStream in = webFile.getInputStream();
                FileOutputStream out = new FileOutputStream(localFile)
        ) {

            localFile.createNewFile();
            FileCopyUtils.copy(in, out);
        } catch (IOException e) {
            log.error("附件上传失败！", e);
            throw new BusinessException("附件上传失败！");
        }
        String path = fileUploadPath + attaId;
        return new Attachment(attaId, fileName, webFile.getSize(), path, userId);
    }
}
