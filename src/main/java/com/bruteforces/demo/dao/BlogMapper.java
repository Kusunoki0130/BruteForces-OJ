package com.bruteforces.demo.dao;


import com.bruteforces.demo.entity.Blog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlogMapper {

    public List<Blog> getAll();

    public int addNewBlog(Blog blog);

    public Blog getBlogById(int blogId);

    public Blog getBlogByTitle(String blogTitle);

    public int deleteBlogById(int blogId);

}
