package com.lin.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
public class Bgm implements Serializable {
    @Id
    private String id;
    private String author;
    private String name;
    private String path; // 播放地址
}