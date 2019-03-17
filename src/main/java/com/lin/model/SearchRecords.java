package com.lin.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "search_records")
public class SearchRecords implements Serializable {
    @Id
    private String id;
    private String content; // 搜索的内容
}