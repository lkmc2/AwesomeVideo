package com.lin.controller;

import com.lin.utils.JsonResult;
import io.swagger.annotations.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author lkmc2
 * @date 2019/2/1
 * @description 视频控制器
 */
@Api(value = "视频相关业务的接口", tags = {"视频相关业务的Controller"})
@RestController
@RequestMapping("/video")
public class VideoController {

    @ApiOperation(value = "上传视频", notes = "上传视频的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "bgmId", value = "背景音乐id", dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "videoSeconds", value = "背景音乐播放长度", required = true, dataType = "double", paramType = "form"),
            @ApiImplicitParam(name = "videoWidth", value = "视频宽度", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "videoHeight", value = "视频高度", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "desc", value = "视频描述", dataType = "String", paramType = "form")
    })
    @PostMapping(value = "/upload", headers="content-type=multipart/form-data")
    public JsonResult upload(String userId, String bgmId, double videoSeconds,
                             int videoWidth, int videoHeight, String desc,
                             @ApiParam(value = "上传的视频", required = true) MultipartFile file) {
        if (StringUtils.isBlank(userId)) {
            return JsonResult.errorMsg("用户id不能为空");
        }

        // 数据库保存视频的路径
        String uploadPathDB = null;

        FileOutputStream out = null;
        InputStream in = null;
        try {
            // 上传文件数组不为空
            if (file != null) {
                String fileName = file.getOriginalFilename();
                if (StringUtils.isNoneBlank(fileName)) {
                    // 文件上传的最终保存路径
                    String finalVideoPath = String.format("F:/AwesomeVideoUpload/%s/video/%s", userId, fileName);
                    // 设置数据库保存的路径
                    uploadPathDB = String.format("/%s/video/%s", userId, fileName);

                    File outFile = new File(finalVideoPath);
                    if (outFile.getParentFile() != null && !outFile.getParentFile().isDirectory()) {
                        // 创建父文件夹
                        //noinspection ResultOfMethodCallIgnored
                        outFile.getParentFile().mkdirs();
                    }

                    out = new FileOutputStream(outFile);
                    in = file.getInputStream();

                    // 将上传文件的输入流写入服务器上传文件夹
                    IOUtils.copy(in, out);
                }
            } else {
                JsonResult.errorMsg("上传文件不能为空，上传失败！");
            }
        } catch (IOException e) {
            return JsonResult.errorMsg("上传文件失败！");
        } finally {
            // 关闭输入输出流
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }

        return JsonResult.ok();
    }

}
