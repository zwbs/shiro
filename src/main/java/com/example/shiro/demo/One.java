package com.example.shiro.demo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class One {

    WebApplicationContext applicationContext;

    public static final String md5(String password, String salt){
        //加密方式
        String hashAlgorithmName = "MD5";
        //盐：相同密码使用不同的盐加密后的结果不同
        ByteSource byteSalt = ByteSource.Util.bytes(salt);
        //密码
        Object source = password;
        //加密次数
        int hashIterations = 2;
        SimpleHash result = new SimpleHash(hashAlgorithmName, source, byteSalt, hashIterations);
        return result.toString();
    }

    @Test
    public void test(){
        String pwd1 = md5("123456", "admin8d78869f470951332959580424d4bf4f");
        System.out.println(pwd1);        //密码：d3c59d25033dbf980d29554025c23a75
        String pwd2 = md5("123456", "abc0c23e95fd137ea96c4ef24366b7e6f1f");
        System.out.println(pwd2);        //密码：ae8bb0dd40e4eddeac081f8e31afdaed
    }

    @Test
    public void test02() throws NoSuchAlgorithmException {
        String admin = new Md5Hash("admin","123").toHex();
        System.out.println(admin);

        MessageDigest digest = MessageDigest.getInstance("MD5");

        digest.reset();
        digest.update("123".getBytes());

        byte[] bytes = digest.digest("admin".getBytes());

        String s = Hex.encodeToString(digest.digest(bytes));
        System.out.println(s);


        MessageDigest digest1 = MessageDigest.getInstance("MD5");

        byte[] digest2 = digest1.digest("123admin".getBytes());

        System.out.println(Hex.encodeToString(digest1.digest(digest2)));

    }

    @Test
    public void test03(){
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
    }

    @RequestMapping(value = "/getAllUrl", method = RequestMethod.GET)
    @ResponseBody
    public List getAllUrl() {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

//		List<String> urlList = new ArrayList<>();
//		for (RequestMappingInfo info : map.keySet()) {
//			// 获取url的Set集合，一个方法可能对应多个url
//			Set<String> patterns = info.getPatternsCondition().getPatterns();
//
//			for (String url : patterns) {
//				urlList.add(url);
//			}
//		}

        List<Map<String, String>> list = new ArrayList<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
            Map<String, String> map1 = new HashMap<>();
            RequestMappingInfo info = m.getKey();
            HandlerMethod method = m.getValue();
            PatternsRequestCondition p = info.getPatternsCondition();
            for (String url : p.getPatterns()) {
                map1.put("url", url);
            }
            map1.put("className", method.getMethod().getDeclaringClass().getName()); // 类名
            map1.put("method", method.getMethod().getName()); // 方法名
            RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
            for (RequestMethod requestMethod : methodsCondition.getMethods()) {
                map1.put("type", requestMethod.toString());
            }

            list.add(map1);
        }

        return list;
    }
}
