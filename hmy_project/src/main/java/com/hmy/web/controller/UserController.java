package com.hmy.web.controller;


import com.hmy.web.entity.User;
import com.hmy.web.service.UserService;
import com.plxcc.servicebase.common.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import springfox.documentation.annotations.Cacheable;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hmy
 * @since 2020-08-06
 */
@RestController
@RequestMapping("/web/user")
public class UserController
{

    @Autowired
    UserService userService;

    /**
     * @return
     */
    @Cacheable(value = "userspace")
    @GetMapping("/get")
    @ApiOperation(value = "", tags = "获取用户列表")
    public Result getUser()
    {
        System.out.println("1");
        return Result.success().setData("userlist", userService.list());
    }

    /**
     * @param user
     * @return
     */
    @CachePut(value = "userspace")
    @PostMapping("/insert")
    @ApiOperation(value = "插入方法", tags = {"user"})
    public Result insert(@RequestBody User user)
    {
//        User user1 = new User();
//        user1.setUserName("hmy");
//        user1.setPassword("123456");
        userService.save(user);
        return Result.success().setData("data", user).setMsg("保存成功");
    }


}

