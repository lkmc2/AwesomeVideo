package com.lin.controller;

import com.lin.enums.VideoStatusEnum;
import com.lin.model.Bgm;
import com.lin.model.Video;
import com.lin.service.BgmService;
import com.lin.service.VideoService;
import com.lin.utils.JsonResult;
import com.lin.utils.MergeVideoMp3;
import io.swagger.annotations.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * @author lkmc2
 * @date 2019/2/1
 * @description 视频控制器
 */
@Api(value = "视频相关业务的接口", tags = {"视频相关业务的Controller"})
@RestController
@RequestMapping("/video")
public class VideoController {

    // 静态资源所在路径
    private static final String FILE_BASE = "F:/AwesomeVideoUpload";
    // ffmpeg所在路径
    private static final String FFMPEG_EXE = "H:/ffmpeg/bin/ffmpeg.exe";

    @Autowired
    private BgmService bgmService;

    @Autowired
    private VideoService videoService;

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
                             @ApiParam(value = "上传的视频", required = true) MultipartFile file) throws IOException {
        if (StringUtils.isBlank(userId)) {
            return JsonResult.errorMsg("用户id不能为空");
        }

        // 文件上传的最终保存路径
        String finalVideoPath = "";
        // 数据库保存的路径
        String uploadPathDB = String.format("/%s/video", userId);

        FileOutputStream out = null;
        InputStream in = null;
        File outFile = null; // 保存到服务器的文件
        try {
            // 上传文件不为空
            if (file != null) {
                String fileName = file.getOriginalFilename();
                if (StringUtils.isNoneBlank(fileName)) {
                    // 文件上传的最终保存路径
                    finalVideoPath = String.format("F:/AwesomeVideoUpload/%s/video/%s", userId, fileName);
                    // 设置数据库保存的路径
                    uploadPathDB = String.format("/%s/video/%s", userId, fileName);

                    outFile = new File(finalVideoPath);
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

        // 判断bgmId是否为空，如果不为空，
        // 就查询bgm的信息，并且合并视频，生成新的视频
        if (StringUtils.isNotBlank(bgmId)) {
            Bgm bgm = bgmService.queryBgmById(bgmId);
            String mp3InputPath = FILE_BASE + bgm.getPath();
            String videoInputPath = finalVideoPath;

            // 合并视频和背景乐的工具
            MergeVideoMp3 tool = new MergeVideoMp3(FFMPEG_EXE);
            String videoOutputName = UUID.randomUUID().toString() + ".mp4";

            // 设置数据库保存的路径
            uploadPathDB = String.format("/%s/video/%s", userId, videoOutputName);
            finalVideoPath = FILE_BASE + uploadPathDB;
            tool.convert(videoInputPath, mp3InputPath, videoSeconds, finalVideoPath);
        }
        System.out.println("uploadPathDB = " + uploadPathDB);
        System.out.println("finalVideoPath = " + finalVideoPath);

        // 保存视频信息到数据库
        Video video = new Video();
        video.setAudioId(bgmId);
        video.setUserId(userId);
        video.setVideoSeconds((float)videoSeconds);
        video.setVideoWidth(videoWidth);
        video.setVideoHeight(videoHeight);
        video.setVideoDesc(desc);
        video.setVideoPath(uploadPathDB);
        video.setStatus(VideoStatusEnum.SUCCESS.value);
        video.setCreateTime(new Date());

        // 保存视频到数据库，并返回视频id
        String videoId = videoService.saveVideo(video);

        return JsonResult.ok(videoId);
    }

    @ApiOperation(value = "上传视频封面", notes = "上传视频封面的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "videoId", value = "视频id", required = true, dataType = "String", paramType = "form")
    })
    @PostMapping(value = "/uploadCover", headers="content-type=multipart/form-data")
    public JsonResult uploadCover(String userId, String videoId,
                             @ApiParam(value = "视频封面", required = true) MultipartFile file) throws IOException {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(videoId)) {
            return JsonResult.errorMsg("用户id和视频id不能为空");
        }

        // 文件上传的最终保存路径
        String finalCoverPath = "";
        // 数据库保存的路径
        String uploadPathDB = String.format("/%s/video", videoId);

        FileOutputStream out = null;
        InputStream in = null;
        File outFile = null; // 保存到服务器的文件
        try {
            // 上传文件不为空
            if (file != null) {
                String fileName = file.getOriginalFilename();
                if (StringUtils.isNoneBlank(fileName)) {
                    // 文件上传的最终保存路径
                    finalCoverPath = String.format("F:/AwesomeVideoUpload/%s/video/%s", userId, fileName);
                    // 设置数据库保存的路径
                    uploadPathDB = String.format("/%s/video/%s", userId, fileName);

                    outFile = new File(finalCoverPath);
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
                JsonResult.errorMsg("上传封面不能为空，上传失败！");
            }
        } catch (IOException e) {
            return JsonResult.errorMsg("上传封面失败！");
        } finally {
            // 关闭输入输出流
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }

        // 更新视频封面路径信息
        videoService.updateVideo(videoId, uploadPathDB);

        return JsonResult.ok();
    }

}
