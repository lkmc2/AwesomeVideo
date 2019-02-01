package com.lin.controller;

import com.lin.model.User;
import com.lin.service.UserService;
import com.lin.utils.JsonResult;
import com.lin.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "query")
    @PostMapping(value = "/query")
    public JsonResult query(String userId) {
        if (StringUtils.isBlank(userId)) {
            return JsonResult.errorMsg("用户id不能为空");
        }

        // 查询用户信息
        User userInfo = userService.queryUserInfo(userId);
        UserVo userVo = new UserVo();
        // 将用户信息复制到Vo对象
        BeanUtils.copyProperties(userInfo, userVo);

        return JsonResult.ok(userVo);
    }

}
