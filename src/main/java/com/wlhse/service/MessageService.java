package com.wlhse.service;

import com.wlhse.util.R;

import javax.servlet.http.HttpServletRequest;

public interface MessageService {

    //录入审核批准模块下发消息
    R senMessageInInputCheckApprove(int sourceId,int tableId,String elementId);

    //获取接收的消息列表
    R getReceiveMessageList(HttpServletRequest request,int pageNum);

    //阅读消息
    R readMessage(HttpServletRequest request,int messageId);


    //获取收到的消息的数量
    R getReceiveMessageCnt(HttpServletRequest request);

    R getReceiver(String companyCode,String moduleCode);
}
