package com.miyako.subject.service.user.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.miyako.subject.commons.domain.TbCourse;
import com.miyako.subject.commons.domain.TbStudent;
import com.miyako.subject.service.user.api.TbUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping(value = "/list")
    public String list(Model model) {
        logger.info("enter request mapping: /user/list");

        List<TbStudent> tbStudents = tbStudentService.selectAll();
        model.addAttribute("tbStudents", tbStudents);
        for (TbStudent tbStudent : tbStudents) {
            System.out.println(tbStudent.getName());
        }
        return "user/list";
    }
}
