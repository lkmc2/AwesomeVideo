package com.lin.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "search_records")
public class SearchRecords {
    @Id
    private String id;
    private String content; // 搜索的内容
}