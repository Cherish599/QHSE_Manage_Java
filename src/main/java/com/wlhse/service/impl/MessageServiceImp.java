package com.wlhse.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlhse.cache.JedisClient;
import com.wlhse.dao.MessageDao;
import com.wlhse.dto.outDto.UserIdOutDto;
import com.wlhse.entity.Message;
import com.wlhse.service.MessageService;
import com.wlhse.util.R;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class MessageServiceImp implements MessageService {

    @Resource
    MessageDao messageDao;
    @Resource
    JedisClient jedisClient;

    //TODO 安全体系发送消息
    @Override
    public R senMessageInInputCheckApprove(int sourceId, int tableId, String elementId) {
        return null;
    }

    @Override
    public R getReceiveMessageList(HttpServletRequest request, int pageNum) {
        R r=new R();
        int userId = getUserId(request);
        PageHelper.startPage(10,pageNum);
        List<Message> messageReceiveList = messageDao.getMessageReceiveList(userId);
        r.put("data",new PageInfo<>(messageReceiveList));
        return r;
    }

    @Override
    @Transactional
    public R readMessage(HttpServletRequest request, int messageId) {
        R r=new R();
        int userId = getUserId(request);
        Message messageDetail = messageDao.getMessageDetail(messageId);
        if (messageDetail.getReceiverId()!=userId&&messageDetail.getSenderId()!=userId){
            r.put("data","不要看不是你的东西哦");
        }
        else {
            r.put("data",messageDetail);
            Message message=new Message();
            message.setStatus("已读");
            messageDao.updateMessage(message);
        }
        return r;
    }

    @Override
    public R getReceiveMessageCnt(HttpServletRequest request) {
        int userId = getUserId(request);
        int receiveMessageNum = messageDao.getReceiveMessageNum(userId);
        R r=new R();
        r.put("data",receiveMessageNum);
        return r;
    }

    @Override
    public R getReceiver(String companyCode,String moduleCode) {
        R r=new R();
        List<UserIdOutDto> userIdOutDtos=messageDao.getReceiver(companyCode,moduleCode);
        r.put("data",userIdOutDtos);
        return r;
    }




    private int getUserId(HttpServletRequest request){
        try {
            String token = request.getHeader("Authorization");
            if (StringUtils.isNotBlank(token))
                // System.out.println(jedisClient.get(token));
                return Integer.parseInt(jedisClient.hGetAll(token).get("userId"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
