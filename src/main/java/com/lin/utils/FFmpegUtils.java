package com.lin.utils;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author lkmc2
 * @date 2019/2/2
 * @description ffmpeg工具类
 */
public class FFmpegUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(FFmpegUtils.class);

    private String ffmpegExe; // ffmpeg.exe所在位置

    public FFmpegUtils(String ffmpegExe) {
        this.ffmpegExe = ffmpegExe;
    }

    /**
     * 将视频和音频合并成一个视频
     * @param videoInputPath 视频输入位置
     * @param mp3InputPath 音频输入位置
     * @param seconds 生成的视频时间
     * @param videoOutputPath 输出视频位置
     * @throws IOException 文件输出输出异常
     */
    public void mergeVideoAndBackgroundMusic(String videoInputPath, String mp3InputPath, double seconds, String videoOutputPath) throws IOException {
        // 去除原视频声音命令行：ffmpeg -i video.mp4 -c:v copy -an video-no-audio.mp4
        // 合并背景乐和视频代码：ffmpeg -i video-no-audio.mp4 -i 愉快.mp3 -t 10 -y movie.avi
        // 生成指定尺寸的缩略图：ffmpeg -i video.mp4 -ss 00:00:01 -vframes 1 -y test.jpg
        // -i 表示输入的文件；-t 表示最后生成的文件时间；-y 表示覆盖原文件；
        // -ss表示截图的开始时间，可以是1，也可以是00:00:01；-vframes表示帧数

        // 去掉音轨的视频文件名
        String videoNoAudio = videoInputPath + "-no-audio.mp4";

        List<String> command = Arrays.asList(ffmpegExe, "-i", videoInputPath, "-c:v", "copy", "-an", videoNoAudio);
        // 执行命令去掉原视频的音轨
        executeCommand(command);

        command = Arrays.asList(ffmpegExe, "-i", videoNoAudio, "-i", mp3InputPath, "-t", String.valueOf(seconds), "-y", videoOutputPath);
        // 执行命令合并视频和背景乐
        executeCommand(command);

        // 删除去掉音轨的视频和原视频
        deleteVideo(videoNoAudio);
        deleteVideo(videoInputPath);
    }

    /**
     * 生成视频缩略图
     * @param videoOutputPath 输出视频位置
     * @throws IOException 文件输出输出异常
     */
    public void createVideoThumbnail(String videoOutputPath) throws IOException {
        List<String> command = Arrays.asList(ffmpegExe, "-i", videoOutputPath, "-ss", "00:00:01", "-y", "-vframes", "1", videoOutputPath + ".jpg");
        // 执行命令生成视频缩略图
        executeCommand(command);
    }

    /**
     * 删除视频
     * @param filePath 文件路径
     */
    private void deleteVideo(String filePath) {
        File file = new File(filePath);

        // 删除上传服务器的临时文件
        if (file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.delete();
        }
    }

    /**
     * 在控制台中执行命令
     * @param command 命令列表
     * @throws IOException 输入输出异常
     */
    private void executeCommand(List<String> command) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (String str : command) {
            sb.append(str).append(" ");
        }
        LOGGER.info(sb.toString());

        // 执行命令行语句
        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = builder.start();

        InputStream errorStream = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
        }

        // 关闭输入流
        IOUtils.closeQuietly(bufferedReader);
        IOUtils.closeQuietly(inputStreamReader);
        IOUtils.closeQuietly(errorStream);
    }

    public static void main(String[] args) throws IOException {
        FFmpegUtils ffmpeg = new FFmpegUtils("H:/ffmpeg/bin/ffmpeg.exe");
        ffmpeg.mergeVideoAndBackgroundMusic("F:/AwesomeVideoUpload/180930DRXM99CKKP/video/video.mp4",
                "F:/AwesomeVideoUpload/180930DRXM99CKKP/video/愉快.mp3",
                10,
                "F:/AwesomeVideoUpload/180930DRXM99CKKP/video/movie.avi");
    }

}
