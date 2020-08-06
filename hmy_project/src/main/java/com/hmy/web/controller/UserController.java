package com.hmy.web.controller;


import com.hmy.web.entity.User;
import com.hmy.web.service.UserService;
import com.plxcc.servicebase.common.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.*;


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
     *只缓存一个列表，所以key值使用默认规则
     * todo 可用#{'字符串'}? 待测试
     */
    @Cacheable(value = "userListSpace")
    @GetMapping("/get")
    @ApiOperation(value = "获取所有用户列表", tags = "user")
    public Result getUser()
    {
        return Result.success().setData("userlist", userService.list());
    }



    /**
     * @param user
     * @return
     */
    @CacheEvict(value = "userListSpace",allEntries = true)
    @PostMapping("/insert")
    @ApiOperation(value = "插入一个用户", tags = {"user"})
    public Result insert(@RequestBody User user)
    {
        userService.save(user);
        return Result.success().setData("data", user).setMsg("保存成功");
    }

    @Cacheable(value = "userspace",key = "#id")
    @ApiOperation(value = "获取用户by id", tags = {"user"})
    @GetMapping("/getUserById")
    public Result getUserById(@RequestParam String id)
    {
        User user = userService.getById(id);
        if (user!=null){
            return Result.success().setMsg("获取成功").setData("user",user);
        }
        return Result.fail();
    }

    /**
     *用户更新时清除用户缓存和用户列表缓存
     */
    @Caching(evict = {
            @CacheEvict(value = "userspace",key = "#user.id"),
            @CacheEvict(value = "userListSpace",allEntries = true)})
    @PostMapping("/updateUser")
    @ApiOperation(value = "更新用户", tags = {"user"})
    public Result updateUser(@RequestBody User user)
    {
        boolean update = userService.update(user, null);
        if (update){
            return Result.success().setMsg("更新成功");
        }
        return Result.fail();
    }
}

