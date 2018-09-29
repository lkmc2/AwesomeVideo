package com.lin.utils;



/**
 * 通用Mapper配置接口
 * Mapper接口：（基本的增、删、改、查方法）
 * MySqlMapper：针对MySQL的额外补充接口，支持批量插入
 */
public interface MyMapper<T> {
    // 特别注意，该接口不能被扫描到，否则会出错
}
