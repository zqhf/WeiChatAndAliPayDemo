package cn.mrdear.pay.alipay;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2018-05-29
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AliPayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016091300505441";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDGL8zD8CqRDLxwsA5QKVZqdsyKVIwHF4y2rclIpl5W5BB3caO7oV9f1i/MsLzAsSqHIsuqaidcYQ/bjwEn1Q2nCUcf2VA3HndC2yAj97uD65VMBYDFKvealWyvSfTtJIi4lnObXxJdPDv+4WbrxMgIBdohz/pGON82gAPzcMmZOb8Xxn3JqRe55Pjy6h1IaAPCar3TCqDMfLzrGo03OYr01Tu+aDVk/d32MDW2IbjEXaVrJHCqbIqfri3z6Ny8UisD3FAmb5YnWGscZub/Ese6abPORRzpLuzGxgBUTtPvPy5qfvqxOFaUh2Q+kiZxYobhFCH88yF82J0ddpPiM1i7AgMBAAECggEBAJWcjBX2fgvV84OWRJfbPPNOD4b14GeSLZXSh7sibOFndc7VqOcCIX+1r4v0d+l3VUWCzQEu9dvVIiV7RUIOoinQ5TJz2QxjIWEFDrRYVeR2udQT62vszdKSruN9Dzrec5/1Y/yMvs5HpHr7Kbkeams0D8GqwaK1WDxKU7E5Gddi3mihXiD0X8p7MupAmP2T7xcwm75Jw7OGkfUIIzNDvvZ00aZALuCc5LvsCZHE8UTdIjMbvon5vs9hVzuAQMsyg1LAPxwusIOajlxiBl6WUZOXTwIrHZzzyEdbvg6KNfePSht7zhNIxPPCd5ud7VGZXhQmOaRE7SIOmFU+a4M+Q4ECgYEA5hJQddDAfpUH4VRfw9klHKxdkFQQWufDkCaijZKC/yfR5Z4Ukr8OhTP4VdJo1XzzXLx5RCRjQB8Zk7t7Z4SY/UmxU+VqsPtva2Uw9CHmIUpfS2VB078f087h4gfbgOZHTmdn7r1XSkTi/6cqgWeXfIdU0PT9N51JlEN+fmhLqGECgYEA3IWXcZdY4S2VJmkh5vG0PXO11rn3/w8hsNd/9Kfv4K5kUK1fUDnFDeQGJqAEazkEjzLAZrohi3Ip6aoSOIDx+HR9vvnhzG+rRJ+6Js/nUnzzkzyNrq+1GO5unsyRb9uaSBYvJCHgHY9Xq3B5jcF4v3VTU8ATDHmNI4LOl2MmJpsCgYAurvSok00b4j2RGT+9S6heIxNxH72Z1IE4FL7THzF5eKj8gJEIHcBiguMW9vTSrOHspUmt6/w/nI/iDB7EKIGqk3Np5Jk7hCldj1DZmK9Lff3MHKW4w4U/wzDOXm3Jv/AkFpNePAkkRsoQuejEDTMNz6AELlHbIQ03CmL2X09KwQKBgQCB3b6hK5MxEQoME8/eVKtjoTgOG3jb4Upi/ZYiO6A9vhXfmSpPcaYFlyq0JnpTtsFjn2zFUooct//M1tZiJjXZrqi7BieeoIjJIhObdcZxwpojNH7vyBQreYb1x91CdZ43I7FhYv28CsWDdfpvq+dzQSHwaWYOa6rdpdQKPV6jTwKBgFaDOtJtAfWjaPOICUGhpg3pFTxCmVFWsKK2YuYYDxxkqj2L96v9sVTXmymMFWd9hTu+bhC1RxBl3hYkyKMTmWs2JZgroDFwpg4RNRnN3zQ2XstFSZy2zw95WsNTevch7UlBf3gjQFlMiTT3JY2OC+noiAVKozOJ7f6yFB+9IIAV";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtqr5LqYeUWPHhvH3fKcblxc1TacHCuB5IMsyvG+BDBSd969zq41juHHMENZ2UM1tGdrx1wdJmdKNTUAGCDKR1W3LlTJs1ZeBGew4e8KHT4v2AZPRzw5W6ua/lIG7nGmkUoTE04of1ZScNsBBIvbcfC07HWagLawIZyW5FrvRAAHE6OfefogLLKhv2a3hdQFyabxwRMiyaUW6LNK7xXPU/P1JzKnuN0Rcu5jB0jWLZXFuknZp/miUddsXVYs3rGF5tASN3h7JskCuijs/qj1tyF/1HnbpILQA2g+aRjg2zjgo50Bzqiy1qIrVu5h/msEiyfx/Wm3oQillfMbwOQ9lEwIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://www.aifengx.club/provider03/notify_url";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://www.aifengx.club/provider03/return_url";
	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	//public static String notify_url = "http://www.aifengx.club/provider03/pay/notify_url";//PC支付时

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	//public static String return_url = "http://www.aifengx.club/provider03/pay/return_url";//PC支付时
	//授权回调地址
	public static String auth_return_url = "http://www.aifengx.club/provider03/auth_return_url";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝日志路径
	public static String log_path = "d:\\log";

//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

