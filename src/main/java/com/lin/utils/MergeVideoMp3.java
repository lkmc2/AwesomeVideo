package com.lin.utils;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lkmc2
 * @date 2019/2/2
 * @description ffmpeg合并视频和音频工具
 */
public class MergeVideoMp3 {

    private String ffmpegExe; // ffmpeg.exe所在位置

    public MergeVideoMp3(String ffmpegExe) {
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
    public void convert(String videoInputPath, String mp3InputPath, double seconds, String videoOutputPath) throws IOException {
        // 去除原视频声音命令行：ffmpeg -i video.mp4 -c:v copy -an video-no-audio.mp4
        // 合并背景乐和视频代码：ffmpeg -i video-no-audio.mp4 -i 愉快.mp3 -t 10 -y movie.avi
        // -i 表示输入的文件；-t 表示最后生成的文件时间；-y 表示覆盖原文件

        // 去掉音轨的视频文件名
        String videoNoAudio = videoInputPath + "-no-audio.mp4";
        List<String> command = new ArrayList<>();

        command.add(ffmpegExe);
        command.add("-i");
        command.add(videoInputPath);

        command.add("-c:v");
        command.add("copy");

        command.add("-an");

        command.add(videoNoAudio);

        // 执行命令去掉原视频的音轨
        executeCommand(command);

        command.clear();
        command.add(ffmpegExe);
        command.add("-i");
        command.add(videoNoAudio);

        command.add("-i");
        command.add(mp3InputPath);

        command.add("-t");
        command.add(String.valueOf(seconds));

        command.add("-y");
        command.add(videoOutputPath);

        // 执行命令合并视频和背景乐
        executeCommand(command);

        // 删除去掉音轨的视频和原视频
        deleteVideo(videoNoAudio);
        deleteVideo(videoInputPath);
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
        for (String s : command) {
            System.out.print(s + " ");
        }
        System.out.println();

        // 执行命令行语句
        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = builder.start();

        InputStream errorStream = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
        }

        IOUtils.closeQuietly(bufferedReader);
        IOUtils.closeQuietly(inputStreamReader);
        IOUtils.closeQuietly(errorStream);
    }

    public static void main(String[] args) throws IOException {
        MergeVideoMp3 ffmpeg = new MergeVideoMp3("H:/ffmpeg/bin/ffmpeg.exe");
        ffmpeg.convert("F:/AwesomeVideoUpload/180930DRXM99CKKP/video/video.mp4",
                "F:/AwesomeVideoUpload/180930DRXM99CKKP/video/愉快.mp3",
                10,
                "F:/AwesomeVideoUpload/180930DRXM99CKKP/video/movie.avi");
    }

}
