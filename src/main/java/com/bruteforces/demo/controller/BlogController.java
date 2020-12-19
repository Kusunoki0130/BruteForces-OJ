package com.bruteforces.demo.controller;

import com.bruteforces.demo.entity.Admin;
import com.bruteforces.demo.entity.Blog;
import com.bruteforces.demo.entity.Student;
import com.bruteforces.demo.service.BlogService;
import com.bruteforces.demo.utils.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;
import java.sql.Timestamp;

@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;

    // ClientEnd

    @GetMapping("/blogList")
    public String blogListPage(HttpServletRequest request, ModelAndView mv) {
        HttpSession session = request.getSession(true);
        Student stu = (Student)session.getAttribute("stu");
        if (stu==null) {
            return "redirect:/login";
        }
        List<Blog> blogList = blogService.getAll();
        request.setAttribute("blog", blogList);
        return "ClientEnd/Blog/blogList";
    }

    @GetMapping("/blog")
    public String blogPage(HttpServletRequest request, ModelAndView mv, @RequestParam int blogId) throws IOException {
        Blog blog = blogService.findBlogById(blogId);
        System.out.println("/blogSrc/"+ blog.getBlogSrc());
        Properties address = new Properties();
        address.load(new ClassPathResource("address.properties").getInputStream());
        String blogAddr = address.getProperty("blogAddr");
        String path = blogAddr + blog.getBlogSrc();
        InputStream inputStream = null;
        File rdfile = new File(path);
        try {
            inputStream = new FileInputStream(rdfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStreamReader treader = new InputStreamReader(inputStream);
        BufferedReader tBff = new BufferedReader(treader);
        try {
            String file = "";
            String FileReaderstr = "";
            int cnt = 0;
            while((FileReaderstr=tBff.readLine())!=null) {
                file += FileReaderstr + "\n" ;
            }
            blog.setBlogSrc(MarkdownUtils.markdownToHtmlExtensitons(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            tBff.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(blog.getBlogSrc());
        request.setAttribute("blog", blog);
        return "ClientEnd/Blog/blogDetail";
    }

    // AdminEnd

    @GetMapping("/admin/blogList")
    public String adminBlogListPage(HttpServletRequest request, ModelAndView mv) {
        HttpSession session = request.getSession(true);
        Admin admin = (Admin)session.getAttribute("admin");
        if (admin==null) {
            return "redirect:/admin/Login";
        }
        List<Blog> blogList = blogService.getAll();
        request.setAttribute("blog", blogList);
        return "AdminEnd/Blog/blogList";
    }

    @GetMapping("/admin/blog")
    public String adminblogPage(HttpServletRequest request, ModelAndView mv, @RequestParam int blogId) throws IOException {
        Blog blog = blogService.findBlogById(blogId);
        System.out.println("/blogSrc/"+ blog.getBlogSrc());
        Properties address = new Properties();
        address.load(new ClassPathResource("address.properties").getInputStream());
        String blogAddr = address.getProperty("blogAddr");
        String path = blogAddr + blog.getBlogSrc();
        InputStream inputStream = null;
        File rdfile = new File(path);
        try {
            inputStream = new FileInputStream(rdfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStreamReader treader = new InputStreamReader(inputStream);
        BufferedReader tBff = new BufferedReader(treader);
        try {
            String file = "";
            String FileReaderstr = "";
            int cnt = 0;
            while((FileReaderstr=tBff.readLine())!=null) {
                file += FileReaderstr + "\n" ;
            }
            blog.setBlogSrc(MarkdownUtils.markdownToHtmlExtensitons(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            tBff.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(blog.getBlogSrc());
        request.setAttribute("blog", blog);
        return "AdminEnd/Blog/blogDetail";
    }

    @GetMapping("/admin/addBlog")
    public String addBlogPage(HttpServletRequest request, ModelAndView mv) {
        return "AdminEnd/Blog/addBlog";
    }
    @PostMapping("/admin/addBlog")
    @ResponseBody
    public Map<String, Object> addBlog(HttpServletRequest request, ModelAndView mv, MultipartFile mdFile) throws IOException {
        Timestamp blogTime = new Timestamp(new Date().getTime());
        Properties address = new Properties();
        address.load(new ClassPathResource("address.properties").getInputStream());
        String blogAddr = address.getProperty("blogAddr");
        String blogTitle = mdFile.getOriginalFilename();
        String blogSrc = "/" + blogTitle;
        System.out.println(blogSrc);
        String path = blogAddr + "/" + blogTitle;
        File saveFile = new File(path);
        mdFile.transferTo(saveFile);
        int res = blogService.insertNewBlog(blogTitle, blogSrc, blogTime);
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("message", "上传成功");
        return json;
    }

    @PostMapping("/admin/deleteBlog")
    public String deleteBlog(HttpServletRequest request, ModelAndView mv, @RequestParam(value = "blogIds",required = true)List<Integer> blogIds) {
        for (int i : blogIds) {
            System.out.println(i);
            blogService.deleteBlogById(i);
        }
        return "redirect:/admin/blogList";
    }
}
