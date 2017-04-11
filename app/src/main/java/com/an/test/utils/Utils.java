/**
 * @Title: Utils.java
 * @Package com.yanzhi.healthcare.utils
 * @Description: TODO(用一句话描述该文件做什么)
 * @author wangkun
 * @date 2014-8-27 下午5:00:39
 */
package com.an.test.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangkun
 * @ClassName: Utils
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2014-8-27 下午5:00:39
 */
public class Utils {

    public static final String USER_INFO_CONFIG = "user_info";
    public static final String USER_LOGIN_CONFIG = "user_login";

    public static String stringMD5(String input) {
        try {
            // 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            // 输入的字符串转换成字节数组
            byte[] inputByteArray = input.getBytes();
            // inputByteArray是输入字符串转换得到的字节数组
            messageDigest.update(inputByteArray);
            // 转换并返回结果，也是字节数组，包含16个元素
            byte[] resultByteArray = messageDigest.digest();
            // 字符数组转换成字符串返回
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }


    // 下面这个函数用于将字节数组换成成16进制的字符串

    public static String byteArrayToHex(byte[] byteArray) {
        // 首先初始化一个字符数组，用来存放每个16进制字符
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray = new char[byteArray.length * 2];
        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        // 字符数组组合成字符串返回
        return new String(resultCharArray);
    }


    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(17[6-8])|(18[0-9])|(14[5,7]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    private static Toast mToast = null;

    //吐司提示
    public static void MyToast(Context context, String str) {
        if (mToast == null) {
            mToast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(str);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    //"yyyy-MM-dd HH:mm:ss");
    public static String getStringDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dayString = sdf.format(date);
        return dayString;
    }

    public static String getCurrentTimeString() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss SSS");
        String dayString = sdf.format(date);
        return dayString;
    }

    /**
     * 得到流数据
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();

    }

    /**
     * 通过路径获取图片
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static byte[] getImage(String path) throws IOException {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");   //设置请求方法为GET
        conn.setReadTimeout(5 * 1000);    //设置请求过时时间为5秒
        InputStream inputStream = conn.getInputStream();   //通过输入流获得图片数据
        byte[] data = readInputStream(inputStream);     //获得图片的二进制数据
        return data;

    }


    public static void clearUserPassword(Context context, String userName) {
        try {
//            SharedPreferences preferencesD = context.getSharedPreferences(Utils.USER_INFO_CONFIG, Context.MODE_PRIVATE);
//            SharedPreferences.Editor editorD = preferencesD.edit();
//            Gson gson = new Gson();
//            String userStr = preferencesD.getString("user", "");
//            User user = gson.fromJson(userStr, new TypeToken<User>() {}.getType());
//            user.setPassword("");//把密码设置为空
//            String user_info = gson.toJson(user);
//            editorD.putString("user", user_info);
            SharedPreferences preferences = context.getSharedPreferences("user_login", Context.MODE_PRIVATE);
            SharedPreferences.Editor editorD = preferences.edit();
            editorD.putString("pwd", "");
            editorD.apply();
        } catch (Exception e) {

        }
    }


    /**
     * 拿取配置文件user_list
     *
     * @param context return listItems
     */
    public static List<Map<String, String>> getMasterUser_list(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Utils.USER_LOGIN_CONFIG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String user_list = preferences.getString("user_list", "");
        Gson gson = new Gson();
        List<Map<String, String>> listItems = new ArrayList<Map<String, String>>();
        if (!user_list.equals("")) {
            listItems = gson.fromJson(user_list, new TypeToken<List<Map<String, String>>>() {
            }.getType());
        }
        return listItems;
    }

    /**
     * 将新的用户lisetItem保存配置文件user_list
     *
     * @param context user
     */
    public static void saveUser(Context context, Map<String, String> lisetItem) {
        List<Map<String, String>> listItems = Utils.getMasterUser_list(context);
        SharedPreferences preferences = context.getSharedPreferences(Utils.USER_LOGIN_CONFIG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        if (listItems == null || listItems.size() == 0) {
            listItems.add(lisetItem);
        } else if (!(listItems.contains(lisetItem))) {
            for (Map<String, String> map : listItems) {
                if (map.get("user_account").equals(lisetItem.get("user_account")) && !map.get("user_pwd").equals(lisetItem.get("user_pwd"))) {
                    listItems.remove(map);
                    break;
                }

            }
            listItems.add(0, lisetItem);
        } else {
            for (Map<String, String> map : listItems) {
                if (map.get("user_account").equals(lisetItem.get("user_account")) && map.get("user_pwd").equals(lisetItem.get("user_pwd"))) {
                    listItems.remove(map);
                    listItems.add(0, lisetItem);
                    break;
                }
            }
        }
        if (listItems.size() > 5) {
            listItems.remove(listItems.size() - 1);
        }
        String user_list_new = gson.toJson(listItems);
        editor.putString("user_list", user_list_new);
        editor.commit();

    }

    public static void saveBitmap(Bitmap bm, String dir, String picName) {
        File f = new File(dir, picName);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * Convert hex string to byte[]
     *
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

//TODO  byte转int
/*    public static int byteToInt(byte[] arr) {
            int x = ((arr[a] & 0xFF) << 24)
                    | ((arr[a + 1] & 0xFF) << 16)
                    | ((arr[a + +2] & 0xFF) << 8)
                    | (arr[a + 3] & 0xFF);
        return x;
    }*/

    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * @param @param  date
     * @param @return 设定文件
     * @return Date    返回类型
     * @Title: getNowDay
     * @author wangkun
     * @Description: TODO 获取今天时间
     */
    public static Date getNowDay() {
        Calendar c = Calendar.getInstance();
        Date date = new Date();
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day);
        Date nowDay = c.getTime();
        return nowDay;
    }


    public static int[] hexStringToInt(String hex) {
        int len = (hex.length() / 2);
        char[] achar = hex.toCharArray();
        int[] result = new int[len];
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = toByte(achar[pos]) * 16 + toByte(achar[pos + 1]);
        }
        return result;
    }

    public static byte toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }

}
