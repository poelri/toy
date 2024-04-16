package com.example.toy.service;

import com.example.toy.dao.PostDao;
import com.example.toy.dto.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;



    public void insertPost(Post post) throws Exception {
        postDao.insertPost(post);
    }


}
