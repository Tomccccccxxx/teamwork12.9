package merchant.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MerchantOrderImpl implements MerchantOrder {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private String orderId;
    private String userPhone;
    private String itemDesc;
    private String targetSite;
    private Date date;
    private String orderState;

    @Override
    public String getDate() {
        return this.date != null ? dateFormat.format(date) : null;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }
}