package com.wlhse.service.impl;

import com.wlhse.cache.JedisClient;
import com.wlhse.dao.QHSETaskDao;
import com.wlhse.dto.TaskDto;
import com.wlhse.dto.outDto.TaskOutDto;
import com.wlhse.service.TaskService;
import com.wlhse.util.R;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Author:Coco
 * create:2020-08-27 4:51 PM
 **/
@Service
public class TaskServiceImp implements TaskService {

    @Resource
    QHSETaskDao taskDao;
    @Resource
    JedisClient jedisClient;
    @Override
    public R createNewTask(TaskDto taskDto) {
        int i = taskDao.insertNewTask(taskDto);
        if (i==1){
            return R.ok();
        }
        return  R.error("下达任务失败");
    }

    @Override
    public R getTaskByEmployeeId(HttpServletRequest request) {
        R r=new R();
        String token = request.getHeader("Authorization");
        Map<String, String> map = jedisClient.hGetAll(token);
        int employeeId = Integer.valueOf(map.get("employeeId"));
        List<TaskOutDto> allTaskByEmployeeId = taskDao.getAllTaskByEmployeeId(employeeId);
        r.put("data",allTaskByEmployeeId);
        return r;
    }

    @Override
    public R receiveTask(int taskId) {
        int i = taskDao.receiveTask(taskId);
        if (i==1){
            return R.ok();
        }
        return R.error("接收失败");
    }
}
