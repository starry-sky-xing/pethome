package cn.itsource.basic.controller;

import cn.itsource.basic.utiles.FastDfsApiOpr;
import cn.itsource.basic.utiles.JsonResult;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/dfs")
public class FastDfsController {

    @PostMapping("/upload")
    public JsonResult upload(MultipartFile file){
        try {
            //获取文件后缀名
            String filename = file.getOriginalFilename();
            //huoqu获取后缀名
            String extension = FilenameUtils.getExtension(filename);
            String url = FastDfsApiOpr.upload(file.getBytes(), extension);
            return JsonResult.ResultObj(url);
        } catch (IOException e) {
            e.printStackTrace();
            return JsonResult.me(e.getMessage());
        }
    }
}
