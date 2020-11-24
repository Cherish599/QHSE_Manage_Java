package com.wlhse.controller;

import com.wlhse.service.MessageService;
import com.wlhse.util.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v3")
public class MessageController {

    @Resource
    MessageService messageService;


    @RequestMapping(value = "/getReceiveMessageList",method = RequestMethod.GET)
    public R getReceiveMessageList(HttpServletRequest request, Integer pageNum){
        return messageService.getReceiveMessageList(request,pageNum);
    }


    @RequestMapping(value = "/readMessage",method = RequestMethod.PUT)
    public R readMessage(HttpServletRequest request,Integer messageId){
        return messageService.readMessage(request,messageId);
    }

    @RequestMapping(value = "/getReceiveMessageCnt",method = RequestMethod.GET)
    public R getReceiveMessageCnt(HttpServletRequest request){
        return messageService.getReceiveMessageCnt(request);
    }

    //TODO 发送消息 明天做~~
    @RequestMapping(value = "/sendMessage/{tag}",method = RequestMethod.POST)
    public R sendMessage(@RequestParam("elementId") String elementId, @PathVariable("tag")Integer sourceId,@RequestParam("tableId")Integer tableId){
        return messageService.senMessageInInputCheckApprove(sourceId,tableId,elementId);
    }

    @RequestMapping(value = "/getReceiver",method = RequestMethod.GET)
    public R getReceiver(@RequestParam("companyCode")String companyCode,@RequestParam("moduleCode")String moduleCode,@RequestParam("type")Integer type){
        return messageService.getReceiver(companyCode,moduleCode);
    }
}
