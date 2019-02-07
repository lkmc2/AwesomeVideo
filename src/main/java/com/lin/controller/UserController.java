package com.lin.controller;

import com.lin.model.User;
import com.lin.model.UserReport;
import com.lin.model.vo.PublisherVideoVo;
import com.lin.service.UserService;
import com.lin.utils.JsonResult;
import com.lin.model.vo.UserVo;
import io.swagger.annotations.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author lkmc2
 * @date 2019/1/31
 * @description 用户控制器
 */
@Api(value = "用户相关业务的接口", tags = {"用户相关业务的Controller"})
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户上传头像", notes = "用户上传头像的接口")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "query")
    @PostMapping(value = "/uploadFace", headers="content-type=multipart/form-data")
    public JsonResult uploadFace(String userId, @ApiParam(value = "上传的图片", required = true) MultipartFile file) {
        if (StringUtils.isBlank(userId)) {
            return JsonResult.errorMsg("用户id不能为空");
        }

        // 数据库保存头像的路径
        String uploadPathDB = null;

        FileOutputStream out = null;
        InputStream in = null;
        try {
            // 上传文件数组不为空
            if (file != null) {
                String fileName = file.getOriginalFilename();
                if (StringUtils.isNoneBlank(fileName)) {
                    // 文件上传的最终保存路径
                    String finalFacePath = String.format("F:/AwesomeVideoUpload/%s/face/%s", userId, fileName);
                    // 设置数据库保存的路径
                    uploadPathDB = String.format("/%s/face/%s", userId, fileName);

                    File outFile = new File(finalFacePath);
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

        User user = new User();
        user.setId(userId);
        user.setFaceImage(uploadPathDB);
        // 更新用户信息
        userService.updateUserInfo(user);

        return JsonResult.ok(uploadPathDB);
    }

    @ApiOperation(value = "查询用户信息", notes = "查询用户信息的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fanId", value = "粉丝id", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/query")
    public JsonResult query(String userId, String fanId) {
        if (StringUtils.isBlank(userId)) {
            return JsonResult.errorMsg("用户id不能为空");
        }

        // 查询用户信息
        User userInfo = userService.queryUserInfo(userId);
        UserVo userVo = new UserVo();
        // 将用户信息复制到Vo对象
        BeanUtils.copyProperties(userInfo, userVo);

        // 设置该用户是否被关注
        userVo.setFollow(userService.queryIfFollow(userId, fanId));

        return JsonResult.ok(userVo);
    }

    @ApiOperation(value = "查询发布者信息", notes = "查询发布者信息的接口")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "loginUserId", value = "登陆用户id", required = true, dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "videoId", value = "视频id", required = true, dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "publishUserId", value = "发布者id", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/queryPublisher")
    public JsonResult queryPublisher(String loginUserId, String videoId, String publishUserId) {
        if (StringUtils.isBlank(publishUserId)) {
            return JsonResult.errorMsg("发布者id不能为空");
        }

        // 1.查询视频发布者的信息
        User userInfo = userService.queryUserInfo(publishUserId);
        UserVo publisher = new UserVo();
        // 将用户信息复制到Vo对象
        BeanUtils.copyProperties(userInfo, publisher);

        // 2.查询当前登陆者和顺逆的点赞关系
        boolean userLikeVideo = userService.isUserLikeVideo(loginUserId, videoId);

        // 发布者与视频VO
        PublisherVideoVo publisherVideoVo = new PublisherVideoVo();
        publisherVideoVo.setPublisher(publisher); // 设置视频发布者
        publisherVideoVo.setUserLikeVideo(userLikeVideo); // 设置用户是否给该视频点赞

        return JsonResult.ok(publisherVideoVo);
    }

    @ApiOperation(value = "举报用户", notes = "举报用户的接口")
    @PostMapping(value = "/reportUser")
    public JsonResult reportUser(@RequestBody UserReport userReport) {
        // 保存举报信息
        userService.reportUser(userReport);
        return JsonResult.ok("举报成功…有你平台更美好");
    }

}
