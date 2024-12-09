package user.util;

import java.io.UnsupportedEncodingException;
import java.net.SocketException;

/**
 * IP 地址工具接口。
 */
public interface IAddressUtils {

    /**
     * 获取本机的内网IP地址。
     *
     * @return 内网IP地址
     * @throws SocketException 如果获取网络接口失败
     */
    String getInnetIp() throws SocketException;

    /**
     * 获取指定IP地址的位置信息（已弃用）。
     *
     * @param content   请求的参数 格式为：ip=192.168.1.101
     * @param encoding 服务器端请求编码。如GBK,UTF-8等
     * @return 解析后的地址信息
     * @throws UnsupportedEncodingException 如果解码失败
     */
    @Deprecated
    String getAddresses(String content, String encoding) throws UnsupportedEncodingException;
}