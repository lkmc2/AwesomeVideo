package com.lin.model;

import lombok.Data;

import javax.persistence.*;

@Data
public class Bgm {
    @Id
    private String id;
    private String author;
    private String name;
    private String path; // 播放地址
}