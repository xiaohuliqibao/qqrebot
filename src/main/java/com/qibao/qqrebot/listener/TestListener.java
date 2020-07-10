package com.qibao.qqrebot.listener;

import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.beans.messages.msgget.PrivateMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.sender.MsgSender;

@Beans
public class TestListener {
    
    @Listen(MsgGetTypes.privateMsg)
    @Filter("hello.*")
    public void testListen1(PrivateMsg msg,MsgSender sender){
        System.out.println(msg);

        sender.SENDER.sendPrivateMsg(msg, msg.getMsg());
    }
}