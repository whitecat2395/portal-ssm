package com.leozhang.portalssm.controller;

import com.alibaba.fastjson.JSONObject;
import com.leozhang.portalssm.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RequestMapping("/file")
@Controller
public class FileController {

    /**
     * 这里填写自己的public文件夹路径
     */
    public static String fileFolder = "/Users/zhouzheng/Downloads/public/";

    @RequestMapping("/upload")
    @ResponseBody
    public Result upload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        //获取原始文件名
        String baseFileName = multipartFile.getOriginalFilename();
        //通过字符串截取最后一个.来获取文件后缀
        String ext = baseFileName.substring(baseFileName.lastIndexOf("."));
        //生成随机文件名防止同名文件覆盖
        String newFileName = UUID.randomUUID().toString()+ext;
        //创建文件对象
        System.out.println(newFileName);
        File f = new File(fileFolder+newFileName);
        //将文件转存到我们创建的静态资源文件夹中
        multipartFile.transferTo(f);
        //创建返回数据对象
        JSONObject data = new JSONObject();
        //保存文件的访问路径和文件名
        data.put("url","public/"+newFileName);
        data.put("name",newFileName);
        //通过Result对象将文件结果返回
        return Result.end(200,data,"上传成功",0);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(@RequestParam("url")String url){
        String fileName = url.substring(url.lastIndexOf("/")+1);
        String path = fileFolder+fileName;
        File f = new File(path);
        if(f.exists()){
            f.delete();
        }
        return Result.end(200,null,"删除成功",0);
    }
}
