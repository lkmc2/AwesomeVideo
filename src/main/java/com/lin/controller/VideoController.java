package com.lin.controller;

import com.lin.enums.VideoStatusEnum;
import com.lin.model.Bgm;
import com.lin.model.Video;
import com.lin.service.BgmService;
import com.lin.service.VideoService;
import com.lin.utils.JsonResult;
import com.lin.utils.FFmpegUtils;
import com.lin.utils.PagedResult;
import io.swagger.annotations.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
public class VideoController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoController.class);

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
        try {
            // 上传文件不为空
            if (file != null) {
                String fileName = file.getOriginalFilename();
                if (StringUtils.isNoneBlank(fileName)) {
                    // 文件上传的最终保存路径
                    finalVideoPath = String.format("F:/AwesomeVideoUpload/%s/video/%s", userId, fileName);
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

        // 合并视频和背景乐的工具
        FFmpegUtils ffmpegUtils = new FFmpegUtils(FFMPEG_EXE);

        // 判断bgmId是否为空，如果不为空，
        // 就查询bgm的信息，并且合并视频，生成新的视频
        if (StringUtils.isNotBlank(bgmId)) {
            Bgm bgm = bgmService.queryBgmById(bgmId);
            String mp3InputPath = FILE_BASE + bgm.getPath();
            String videoInputPath = finalVideoPath;

            String videoOutputName = UUID.randomUUID().toString() + ".mp4";

            // 设置数据库保存的路径
            uploadPathDB = String.format("/%s/video/%s", userId, videoOutputName);
            finalVideoPath = FILE_BASE + uploadPathDB;
            // 合并视频和背景音乐
            ffmpegUtils.mergeVideoAndBackgroundMusic(videoInputPath, mp3InputPath, videoSeconds, finalVideoPath);
        }
        // 生成视频缩略图
        ffmpegUtils.createVideoThumbnail(finalVideoPath);

        LOGGER.info("uploadPathDB = {}", uploadPathDB);
        LOGGER.info("coverPath = {}", uploadPathDB + ".jpg");
        LOGGER.info("finalVideoPath {}= ", finalVideoPath);

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
        video.setCoverPath(uploadPathDB + ".jpg");
        video.setCreateTime(new Date());

        // 保存视频到数据库，并返回视频id
        String videoId = videoService.saveVideo(video);

        return JsonResult.ok(videoId);
    }

    @ApiOperation(value = "分页和搜索查询视频列表", notes = "分页和搜索查询视频列表的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "video", value = "视频对象", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "isSaveRecord", value = "是否保存记录", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页数", required = true, dataType = "int", paramType = "query")
    })
    @PostMapping("/showAll")
    public JsonResult showAll(@RequestBody Video video, Integer isSaveRecord, Integer currentPage) {
        if (currentPage == null) {
            currentPage = 1;
        }

        PagedResult result = videoService.getAllVideos(video, isSaveRecord, currentPage, PAGE_SIZE);
        return JsonResult.ok(result);
    }

    @ApiOperation(value = "获取热搜词", notes = "获取热搜词的接口")
    @PostMapping("/hot")
    public JsonResult hot() {
        return JsonResult.ok(videoService.getHotWords());
    }

    @ApiOperation(value = "用户给视频点赞", notes = "用户给视频点赞的接口")
    @PostMapping("/userLike")
    public JsonResult userLike(String userId, String videoId, String videoCreatorId) {
        // 给视频点赞
        videoService.userLikeVideo(userId, videoId, videoCreatorId);
        return JsonResult.ok();
    }

    @ApiOperation(value = "用户给视频取消点赞", notes = "用户给视频取消点赞的接口")
    @PostMapping("/userUnLike")
    public JsonResult userUnLike(String userId, String videoId, String videoCreatorId) {
        // 给视频取消点赞
        videoService.userUnlikeVideo(userId, videoId, videoCreatorId);
        return JsonResult.ok();
    }

}
