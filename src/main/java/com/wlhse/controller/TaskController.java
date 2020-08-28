package com.wlhse.controller;


import com.wlhse.dto.TaskDto;
import com.wlhse.service.TaskService;
import com.wlhse.util.R;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Description:
 * Author:Coco
 * create:2020-08-27 4:49 PM
 **/
@RestController("TaskController")
@RequestMapping("/api/v3")
public class TaskController {

    @Resource
    TaskService taskService;

    @RequestMapping("/createNewTask")
    public R createNewTask(@RequestBody(required = false) TaskDto taskDto){
        return taskService.createNewTask(taskDto);
    }

    @RequestMapping("/getTaskList")
    public R getTaskList(HttpServletRequest request){
        return taskService.getTaskByEmployeeId(request);
    }

    @RequestMapping("/receiveTask")
    public R  receiveTask(@RequestParam(value = "taskId",required = false)Integer taskId){
        return taskService.receiveTask(taskId);
    }

}
