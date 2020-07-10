package com.qibao.qqrebot.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qibao.qqrebot.bean.City;
import com.qibao.qqrebot.bean.Weather;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class QibaoUtil {

    private static final String cityJson = "classpath:city.json";

    /**
     * @author Qibao
     * @param filePath
     * @param lineNumber
     * @return String
     * @see 读取TxT文件中指定的一行,打包后springFromworke框架导致java.io.File类失效，重写文件读取方式。使用org.springframework.core.io库
     */
    public static String getLine(String filePath, int lineNumber) {
        String lineTXT = null;
        try {
            // File f = new File(filePath);
            InputStream in = getInputStream(filePath);
            if (in != null) {
                // InputStreamReader iStreamReader = new InputStreamReader(new
                // FileInputStream(f), "utf-8");
                InputStreamReader iStreamReader = new InputStreamReader(in);
                BufferedReader br = new BufferedReader(iStreamReader);
                // 打印指定一行
                for (int i = 0; i < lineNumber; i++) {
                    lineTXT = br.readLine();
                }
                // System.out.println(lineTXT);
                br.close();
            } else {
                System.out.println("文件读取错误");
            }

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("文件读取错误");
        }
        return lineTXT;
    }

    /**
     * @author Qibao
     * @param msg
     * @return Atqq
     * @see 只针对喷他[CQ:at,qq=2213487614]做了获取QQ号的处理，没有做更多的兼容。
     */

    private static final String PEN = "喷[CQ:at,qq=";
    private static final String PENTA = "喷他[CQ:at,qq=";

    public static String getPenAtQQ(String msg) {
        String qq = null;
        if (msg.substring(0, 12).equals(PENTA)) {
            qq = msg.substring(12, msg.length() - 1);
        } else if (msg.substring(0, 11).equals(PEN)) {
            qq = msg.substring(11, msg.length() - 1);
        } else {
            qq = "你发的是个锤子!";
        }
        return qq;
    }

    /**
     * @author Qibao
     * @param cityid
     * @return StringBuilder
     * @see 查询天气的工具，返回带有所有json信息的StringBuilder类
     */
    public static StringBuilder getWeatherJson(String cityid) {

        StringBuilder sb = new StringBuilder();
        InputStream is = null;
        BufferedReader br = null;
        PrintWriter out = null;
        try {
            // 接口地址
            String url = "https://tianqiapi.com/api?version=v6&appid=99836373&appsecret=NyAv8EAW&cityid=";
            URL uri = new URL(url + cityid);
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
        return sb;
    }

    /**
     * @author Qibao
     * @param sb
     * @return Weather
     * @see 格式化json中的数据，把数据放入Weather类中。
     */
    public static Weather formatJson(StringBuilder sb) {
        JSONObject jsonObject = JSONObject.parseObject(sb.toString());
        // 读取数据

        Weather weather = Weather.builder().air(jsonObject.getString("air"))
                .air_level(jsonObject.getString("air_level")).air_pm25(jsonObject.getString("air_pm25"))
                .air_tips(jsonObject.getString("air_tips")).city(jsonObject.getString("city"))
                .cityEn(jsonObject.getString("cityEn")).cityid(jsonObject.getString("cityid"))
                .country(jsonObject.getString("country")).countryEn(jsonObject.getString("countryEn"))
                .date(simlpDate(jsonObject.getString("date"))).humidity(jsonObject.getString("humidity"))
                .pressure(jsonObject.getString("pressure")).tem(jsonObject.getString("tem"))
                .tem1(jsonObject.getString("tem1")).tem2(jsonObject.getString("tem2"))
                .update_time(simlpDateTime(jsonObject.getString("update_time")))
                .visibility(jsonObject.getString("visibility")).wea(jsonObject.getString("wea"))
                .wea_img(jsonObject.getString("wea_img")).week(jsonObject.getString("week"))
                .win(jsonObject.getString("win")).win_meter(jsonObject.getString("win_meter"))
                .win_speed(jsonObject.getString("win_speed"))
                // alarm暂定
                .build();

        return weather;
    }

    /**
     * @author Qibao
     * @param date
     * @return
     * @see 将固定格式的string类型yyyy-MM-dd时间转化为Date类型
     */
    public static Date simlpDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        try {
            d = simpleDateFormat.parse(date);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        return d;
    }

    /**
     * @author Qibao
     * @param date
     * @return
     * @see 将固定格式的string类型yyyy-MM-dd HH:mm:ss时间转化为Date类型
     */
    public static Date simlpDateTime(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        try {
            d = simpleDateFormat.parse(date);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        return d;
    }

    /**
     * @author Qibao
     * @param cityName
     * @return City类
     * @see 根据城市的名称从json文件中获取城市的基本信息。最初是只想返回一个城市的ID够用就行，后来想做更多的测试就直接把能用的东西都收集起来了。
     */
    public static City getCityByName(String cityName) {
        City city = new City();
        StringBuilder sb = readJsonFile(cityJson);
        JSONObject jsonObject = JSONArray.parseObject(sb.toString());
        JSONArray cities = jsonObject.getJSONArray("city");
        for (int i = 0; i < cities.size(); i++) {
            if (cities.getJSONObject(i).getString("City_CN").equals(cityName)) {
                // System.out.println(cities.getJSONObject(i).getString("City_ID"));
                JSONObject city1 = cities.getJSONObject(i);
                city = City.builder().AD_code(city1.getString("AD_code"))
                        .Admin_district_CN(city1.getString("Admin_district_CN"))
                        .Admin_district_EN(city1.getString("Admin_district_EN")).City_CN(city1.getString("City_CN"))
                        .City_EN(city1.getString("City_EN")).City_ID(city1.getString("City_ID"))
                        .Country_CN(city1.getString("Country_CN")).Country_EN(city1.getString("Country_EN"))
                        .Country_code(city1.getString("Country_code")).Latitude(city1.getString("Latitude"))
                        .Longitude(city1.getString("Longitude")).Province_CN(city1.getString("Province_CN"))
                        .Province_EN(city1.getString("Province_EN")).build();
                return city;
            }
        }
        return city;
    }

    /**
     * @author Qibao
     * @param filePath
     * @return StringBuilder
     * @see 读取json文件中所有信息,按理来说txt也可以读取
     */
    public static StringBuilder readJsonFile(String filePath) {
        // File jsonfile = new File(filePath);
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            // FileInputStream fileInputStream = new FileInputStream(jsonfile);
            InputStream in = getInputStream(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(in, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return stringBuilder;
    }

    /**
     * @author 七宝
     * @param fileName
     * @return InputStream
     * @see 使用springframework.core.io来获取本地文件，返回字节流。
     */
    public static InputStream getInputStream(String fileName) {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(fileName);
        try {
            InputStream in = resource.getInputStream();
            return in;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }
}