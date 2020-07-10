package com.qibao.qqrebot;

import com.forte.component.forcoolqhttpapi.CoolQHttpApp;
import com.forte.component.forcoolqhttpapi.CoolQHttpConfiguration;
import com.forte.qqrobot.SimpleRobotApplication;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;



/**
 * @author qibao 
 * qq机器人的启动类
 */

@SimpleRobotApplication(resources = "/conf.properties")
public class QQRunApplication implements CoolQHttpApp {

    @Override
    public void before(CoolQHttpConfiguration configuration) {
        // TODO Auto-generated method stub

    }

    @Override
    public void after(CQCodeUtil cqCodeUtil, MsgSender sender) {
        // TODO Auto-generated method stub

    }
    
}