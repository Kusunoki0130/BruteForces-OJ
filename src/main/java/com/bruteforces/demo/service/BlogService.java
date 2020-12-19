package com.bruteforces.demo.service;

import com.bruteforces.demo.dao.BlogMapper;
import com.bruteforces.demo.entity.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class BlogService {

    @Autowired(required = false)
    private BlogMapper blogMapper;

    public List<Blog> getAll() {
        return blogMapper.getAll();
    }

    public int insertNewBlog(String blogTitle, String blogSrc, Timestamp blogTime) {
        Blog blog = new Blog(0, blogTitle, blogSrc, blogTime);
        return blogMapper.addNewBlog(blog);
    }

    public Blog findBlogById(int blogId) {
        return blogMapper.getBlogById(blogId);
    }

    public Blog findBlogByTitle(String blogTitle) {
        return blogMapper.getBlogByTitle(blogTitle);
    }

    public int deleteBlogById(int blogId) {
        return blogMapper.deleteBlogById(blogId);
    }
}
