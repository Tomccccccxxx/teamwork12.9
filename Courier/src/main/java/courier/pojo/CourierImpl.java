package courier.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 快递员 实现类。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CourierImpl implements Courier {

    @lombok.Getter
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private String orderId;
    private String userPhone;
    private String itemDesc;
    private String thisSite;
    private String targetSite;
    private Date date;
    private String orderState;

    @Override
    public String getDate() {
        return dateFormat.format(date);
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }

}