package com.qibao.qqrebot.listener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Random;

import javax.print.attribute.standard.Severity;

import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.beans.types.KeywordMatchType;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.qibao.qqrebot.bean.City;
import com.qibao.qqrebot.bean.Weather;
import com.qibao.qqrebot.util.QibaoUtil;

@Beans
public class GroupMsgListenerDemo {

    @Filter(keywordMatchType = KeywordMatchType.TRIM_CONTAINS, value = "天气")
    @Listen(MsgGetTypes.groupMsg)
    public void groupMsg(GroupMsg msg, MsgSender sender) throws IOException {
        String send_str = "你查nm的天气呢？劳资会写吗？";
        String str = msg.getMsg();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // System.out.println(str);
        String cityName = str.substring(0, str.length() - 2);
        City city = QibaoUtil.getCityByName(cityName);
        String cityid = city.getCity_ID().substring(2);
        StringBuilder sb = QibaoUtil.getWeatherJson(cityid);
        Weather weather = QibaoUtil.formatJson(sb);
        send_str = "城市：" + weather.getCity() + " " + "时间：" + sdf.format(weather.getDate()) + " " + weather.getWeek()
                + " 天气：" + weather.getWea() + " 气温：" + weather.getTem() + " 空气质量：" + weather.getAir_level();
        sender.SENDER.sendGroupMsg(msg.getGroup(), send_str);

    }

    // qCool air 无法发送图片 建议更换pro版，图片路径问题暂定
    // CQ码解释是 图片在酷Q目录/data/image/下
    @Filter(value = "来张图片")
    @Listen(MsgGetTypes.groupMsg)
    public void send_image(GroupMsg msg, MsgSender sender) throws IOException {
        // String filePath = "src/static/images/";
        String imageName = "1.jpg";
        // Random r = new Random(5);
        // String imagePath = filePath + imageId;
        // System.out.println(r.toString());

        // String cqCode_image = CQCodeUtil.build().getCQCode_image();
        // imagePath);
        // cq机器人码
        // String cqCode = "[CQ:image,file=1.jpg]";

        CQCode c = CQCodeUtil.build().getCQCode_Image(imageName);
        sender.SENDER.sendGroupMsg(msg.getGroup(), c.toString());

    }

    // 本模块实现随机嘴臭和指定(at)喷人，同时对触发条件进行模糊匹配
    @Filter(keywordMatchType = KeywordMatchType.TRIM_CONTAINS, value = "喷")
    @Listen(MsgGetTypes.groupMsg)
    public void zuiChou(GroupMsg msg, MsgSender sender) throws IOException {
        // System.out.println(msg.getMsg());
        // 喷他[CQ:at,qq=2213487614]
        // 喷[CQ:at,qq=2213487614]
        String sendMsg, penMsg = "";
        String groupMsg = msg.getMsg();

        String filePath = "classpath:shadiaoapp.txt";
        Random random = new Random();
        int lineNumber = random.nextInt(108) + 1;
        penMsg = QibaoUtil.getLine(filePath, lineNumber);
        if (groupMsg.length() <= 5) {
            sendMsg = penMsg;
            sender.SENDER.sendGroupMsg(msg.getGroup(), sendMsg);
        } else if (groupMsg.length() > 13) {
            CQCode at = CQCodeUtil.build().getCQCode_At(QibaoUtil.getPenAtQQ(groupMsg));
            sendMsg = at.toString() + penMsg;
            sender.SENDER.sendGroupMsg(msg.getGroup(), sendMsg);
        }

        // if (groupMsg.length() > 13) {
        // CQCode at = CQCodeUtil.build().getCQCode_At(QibaoUtil.getPenAtQQ(groupMsg));
        // sendMsg = at.toString() + penMsg;
        // } else {
        // sendMsg = penMsg;
        // }
    }
}