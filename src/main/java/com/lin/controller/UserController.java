package com.lin.controller;

import com.lin.model.User;
import com.lin.service.UserService;
import com.lin.utils.JsonResult;
import com.lin.utils.MD5Utils;
import com.lin.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "file", required = true, dataType = "file", paramType = "upload")
    })
    @PostMapping("/uploadFace")
    public JsonResult uploadFace(String userId, @RequestParam("file") MultipartFile[] files) throws Exception {
        // 上传文件数组不为空
        if (ArrayUtils.isNotEmpty(files)) {
            FileOutputStream out = null;
            InputStream in = null;

            String fileName = files[0].getOriginalFilename();
            if (StringUtils.isNoneBlank(fileName)) {
                // 文件上传的最终保存路径
                String finalFacePath = String.format("F:/AwesomeVideoUpload/%s/face/%s", userId, fileName);
                // 设置数据库保存的路径
                String uploadPathDB = String.format("/%s/face/%s", userId, fileName);

                File outFile = new File(finalFacePath);
                if (outFile.getParentFile() != null && !outFile.getParentFile().isDirectory()) {
                    // 创建父文件夹
                    boolean mkdirsSuccess = outFile.getParentFile().mkdirs();
                    if (!mkdirsSuccess) {
                        return JsonResult.errorException("上传文件失败！");
                    }
                }

                out = new FileOutputStream(outFile);
                in = files[0].getInputStream();

                // 将上传文件的输入流写入服务器上传文件夹
                IOUtils.copy(in, out);
            }

            // 关闭输入输出流
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
            return JsonResult.ok();
        }
        return JsonResult.errorMsg("上传文件不能为空，上传失败！");
    }

}
