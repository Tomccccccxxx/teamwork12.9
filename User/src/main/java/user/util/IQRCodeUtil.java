package user.util;

import java.io.IOException;

/**
 * 二维码生成工具接口。
 */
public interface IQRCodeUtil {

    /**
     * 根据提供的URL生成Base64编码的二维码图像字符串。
     *
     * @param url 要编码为二维码的URL或文本。
     * @return Base64编码的PNG格式二维码图像字符串。
     * @throws IOException 如果读取或写入图像时发生错误。
     */
    String createQRCode(String url) throws IOException;
}