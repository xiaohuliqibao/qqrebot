package com.qibao.qqrebot;

import static org.mockito.Mockito.never;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.qibao.qqrebot.bean.Person;
import com.qibao.qqrebot.bean.Weather;
import com.qibao.qqrebot.util.QibaoUtil;

import org.junit.jupiter.api.Test;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.boot.test.context.SpringBootTest;

import ch.qos.logback.core.joran.conditional.ElseAction;

// @SpringBootTest
class DemoApplicationTests {

	// @Test
	// void contextLoads() {
	// // System.out.println("test");
	// }

	@Test
	void lombok() {
		Person person = new Person();
		person.setId(7);
		person.setQQ("2345098765");
		System.out.println(person.getQQ() + " " + person.getId());
	}

	@Test
	void random() {
		Random r = new Random(5);
		System.out.println(r.toString());
	}

	@Test
	void cqCode() {
		CQCode c = CQCodeUtil.build().getCQCode_Image("1.jpg");

		System.out.println(c);
	}

	@Test
	void getTXT() {
		String filePath = "src\\main\\resources\\shadiaoapp.txt";
		int lineNumber = 0;
		try {
			File f = new File(filePath);
			if (f.exists() && f.isFile()) {
				InputStreamReader iStreamReader = new InputStreamReader(new FileInputStream(f), "utf-8");
				BufferedReader br = new BufferedReader(iStreamReader);
				String lineTXT = null;
				// 打印指定一行
				for (int i = 0; i < lineNumber; i++) {
					lineTXT = br.readLine();
				}
				// 循环打印所有
				// while ((lineTXT = br.readLine()) != null) {
				// lineNumber++;
				// System.out.println(lineTXT);
				// }
				// lineTXT = br.readLine();
				System.out.println(lineTXT);
				// System.out.println(lineNumber);
				br.close();
			} else {
				System.out.println("文件不存在");
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("文件读取错误");
		}

	}

	@Test
	void testGetLine() {
		Random random = new Random();
		int lineNumber = random.nextInt(108) + 1;
		System.out.println(QibaoUtil.getLine("classpath:shadiaoapp.txt", lineNumber));
	}

	@Test
	void testGetAtQQ() {
		// 喷他[CQ:at,qq=2213487614]
		// 喷[CQ:at,qq=2213487614]
		String msg = "喷[CQ:at,qq=213487614]";
		// System.out.println(msg.substring(0, 12));
		// System.out.println(msg.substring(12, msg.length() - 1));
		if (msg.substring(0, 12).equals("喷他[CQ:at,qq=")) {
			msg = msg.substring(12, msg.length() - 1);
		} else if (msg.substring(0, 11).equals("喷[CQ:at,qq=")) {
			msg = msg.substring(11, msg.length() - 1);
		} else {
			msg = "你发的是个锤子!";
		}
		System.out.println(msg + "憨憨");
	}

	@Test
	void testWeatherApi() {
		String param = "";
		String cityId = "101190101";
		StringBuilder sb = new StringBuilder();
		InputStream is = null;
		BufferedReader br = null;
		PrintWriter out = null;
		try {
			// 接口地址
			String url = "https://tianqiapi.com/api?version=v6&appid=99836373&appsecret=NyAv8EAW&cityid=";
			param = url + cityId;
			URL uri = new URL(param);
			HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
			connection.setRequestMethod("POST");
			connection.setReadTimeout(5000);
			connection.setConnectTimeout(10000);
			connection.setRequestProperty("accept", "*/*");
			// 发送参数
			connection.setDoOutput(true);
			out = new PrintWriter(connection.getOutputStream());
			out.print("");
			out.flush();
			// 接收结果
			is = connection.getInputStream();
			br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String line;
			// 缓冲逐行读取
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			// System.out.println(sb.toString());
		} catch (Exception ignored) {
		} finally {
			// 关闭流
			try {
				if (is != null) {
					is.close();
				}
				if (br != null) {
					br.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (Exception ignored) {
			}
		}
		JSONObject jsonObject = JSONObject.parseObject(sb.toString());
		String city = jsonObject.getString("city");
		System.out.println(city);
	}

	@Test
	void whenJson_thanConvertToObjectCorrect() {
		Person person = new Person();
		person.setId(987);
		person.setQQ("123456789");
		String jsonObject = JSON.toJSONString(person);
		Person newPerson = JSON.parseObject(jsonObject, Person.class);

		JSONObject jsonObject2 = JSONObject.parseObject(jsonObject);
		System.out.println(jsonObject2);
		String id = jsonObject2.getString("id");

		System.out.println(id);
		System.out.println(jsonObject);
		System.out.println(newPerson.toString());

		// assertEquals(newPerson.getAge(), 0); // 如果我们设置系列化为 false
		// assertEquals(newPerson.getFullName(), listOfPersons.get(0).getFullName());
	}

	@Test
	void testStringToData() throws ParseException {
		SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		String time = "2020-06-21 14:11:43";
		String date = "2020-06-21";

		Date d1 = simpleDateFormatTime.parse(time);
		Date d2 = simpleDateFormat.parse(date);

		System.out.println(d1.getHours());
		System.out.println(d2.toString());
	}

	@Test
	void testWeather() {
		// 成都：101270101
		// 龙岩：101230701
		// 南京：101190101
		String cd_cityid = "101270101";
		String ly_cityid = "101230701";
		String nj_cityid = "101190101";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		StringBuilder stringBuffer = QibaoUtil.getWeatherJson(ly_cityid);
		Weather weather = QibaoUtil.formatJson(stringBuffer);
		String weatherInfo = "城市：" + weather.getCity() + " " + "时间：" + sdf.format(weather.getDate()) + " "
				+ weather.getWeek() + " 天气：" + weather.getWea() + " 空气质量：" + weather.getAir_level() + " 建议："
				+ weather.getAir_tips();
		System.out.println(weatherInfo);
		System.out.println(weather.toString());
	}

	// void
}
