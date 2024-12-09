package user.util;

import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

@Component
public class AddressUtilsImpl implements IAddressUtils {

    @Override
    public String getInnetIp() throws SocketException {
        Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ip = null;
        while (netInterfaces.hasMoreElements()) {
            NetworkInterface ni = netInterfaces.nextElement();
            Enumeration<InetAddress> addresses = ni.getInetAddresses();
            while (addresses.hasMoreElements()) {
                ip = addresses.nextElement();
                if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {
                    return ip.getHostAddress();
                }
            }
        }
        throw new SocketException("无法找到有效的内网IP地址");
    }

    @Override
    @Deprecated
    public String getAddresses(String content, String encoding) throws UnsupportedEncodingException {
        // Implementation of the deprecated method can be kept or removed based on requirements.
        // For now, it's just a placeholder to fulfill the interface contract.
        return "该方法已被废弃，请使用其他方式获取IP地址解析";
    }
}