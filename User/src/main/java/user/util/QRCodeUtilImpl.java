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
import java.util.Base64;
import java.util.HashMap;

@Component
public class QRCodeUtilImpl implements IQRCodeUtil {

    private static final Base64.Encoder encoder = Base64.getEncoder();

    @Override
    public String createQRCode(String url) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            QRCodeWriter writer = new QRCodeWriter();
            HashMap<EncodeHintType, Object> hints = new HashMap<>();
            // You can add encoding hints here if needed
            BitMatrix bitMatrix = writer.encode(url, BarcodeFormat.QR_CODE, 200, 200, hints);
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            ImageIO.write(bufferedImage, "png", os);

            return "data:image/png;base64," + encoder.encodeToString(os.toByteArray());
        } catch (WriterException e) {
            throw new IOException("Error generating QR code", e);
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                // Log or handle exception as appropriate
            }
        }
    }
}