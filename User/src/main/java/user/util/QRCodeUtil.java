package user.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;

/**
 * 二维码生成工具类。
 * @version: 1.0
 * @since： 1.8
 */
@Component
public class QRCodeUtil {


    /**
     * 解码
     */
    static final Base64.Decoder decoder = Base64.getDecoder();
    /**
     * 编码
     */
    static final Base64.Encoder encoder = Base64.getEncoder();

    static final String text = "字串文字";

    /**
     * 生成 Base64 二维码
     */
    public static String createQRCode(String url) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix bitMatrix = writer.encode(url, BarcodeFormat.QR_CODE, 200, 200);
//            HashMap<EncodeHintType, Object> map = new HashMap<>();
//            BitMatrix bitMatrix = writer.encode(url, BarcodeFormat.QR_CODE, 200, 200,map);
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            ImageIO.write(bufferedImage,"png",os);

            // 添加图片标识
//            return "data:image/png;base64," + Base64Util.encode(os.toByteArray());
//            new Base64.Encoder().encode(os.toByteArray());

//            final byte[] textByte = text.getBytes(StandardCharsets.UTF_8);
//
//            //编码
//            final String encodedText = encoder.encodeToString(textByte);
//            System.out.println(encodedText);
////解码
//            System.out.println(new String(decoder.decode(encodedText), "UTF-8"));

            return "data:image/png;base64," + encoder.encodeToString(os.toByteArray());
        } catch (WriterException e) {
            e.printStackTrace();
        }finally {
            os.close();
        }
        return null;
    }
}
