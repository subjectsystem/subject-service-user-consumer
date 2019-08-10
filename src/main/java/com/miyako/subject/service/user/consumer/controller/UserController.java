package com.miyako.subject.service.user.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.miyako.subject.commons.domain.TbCourse;
import com.miyako.subject.commons.domain.TbStudent;
import com.miyako.subject.dubbo.aop.MethodLog;
import com.miyako.subject.service.redis.api.RedisService;
import com.miyako.subject.service.redis.key.StudentKey;
import com.miyako.subject.service.user.api.TbUserService;
import org.apache.tomcat.util.descriptor.web.MessageDestinationRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * ClassName CourseController
 * Description //TODO
 * Author weila
 * Date 2019-08-07-0007 13:49
 */
@Controller
@RequestMapping(value = "/user")
public class UserController{

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Reference
    private TbUserService tbStudentService;
    @Reference
    private RedisService redisService;

    @Value("${page.size}")
    private Integer pageSize;

    private PageInfo<TbStudent> getPage(Model model, int page, int size){
        PageInfo<TbStudent> pageInfo = tbStudentService.page(page, size);
        logger.info("===>:pageInfo size "+ pageInfo.getSize());
        model.addAttribute("pageInfo", pageInfo);
        return pageInfo;
    }

    @GetMapping(value = "/list")
    @MethodLog(value = "UserController", operationType = "路径访问", operationName = "list")
    public String list(Model model) {
        logger.info("enter request mapping: /user/list");
        getPage(model, 1, pageSize);
        //for (TbStudent tbStudent : tbStudents) {
        //    logger.info(tbStudent.getName());
        //    // redisService.set(StudentKey.getById,tbStudent.getId().toString(),tbStudent);
        //}
        return "user/list";
    }

    @GetMapping(value = "/list/{page}")
    @MethodLog(value = "UserController", operationType = "路径访问", operationName = "list", operationArgs = "分页索引")
    public String list(Model model, @PathVariable("page") Integer page) {
        page = page==null?1:page;
        logger.info("enter request mapping: /user/list/"+ page);
        getPage(model, page, pageSize);
        model.addAttribute("currentIndex",page);
        return "user/list";
    }

    @GetMapping(value = "/details/{id}")
    @MethodLog(value = "UserController", operationType = "路径访问", operationName = "details", operationArgs = "用户id")
    public String details(Model model, @PathVariable("id") Integer id){
        TbStudent student = redisService.get(StudentKey.getById.getPrefix() + "->" + id, TbStudent.class);
        if(student ==null){
            student = tbStudentService.selectById(id);
            logger.info("database : "+ student.getId());
        }else{
            logger.info("redis cache: "+ student.getId());
        }
        model.addAttribute("tbStudent", student);
        return "user/details.html";
    }
}
