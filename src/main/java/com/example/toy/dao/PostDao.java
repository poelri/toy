package com.example.toy.dao;

import com.example.toy.dto.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PostDao {

    public void insertPost(Post post) throws Exception;


}
