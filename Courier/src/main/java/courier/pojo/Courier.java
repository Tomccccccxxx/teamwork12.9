package courier.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 快递员 类。

 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Courier {

    /**
     * 订单号
     */
    private String orderId;
    /**
     * 用户手机号
     */
    private String userPhone;
    /**
     * 描述信息
     */
    private String itemDesc;
    /**
     * 当前站点
     */
    private String thisSite;
    /**
     * 目标站点
     */
    private String targetSite;
    /**
     * 下单时间
     */
    private Date date;
    /**
     * 订单状态
     */
    private String orderState;


    /**
     * 格式化订单对象
     */
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");


    /**
     * 获取格式化之后的日期。
     * @return
     *      返回格式化之后的日期信息。
     */
    public String getDate() {
        return dateFormat.format(date);
    }

    /**
     * 设置日期。
     * @param date：给定传进来的日期进行获取时间日期对象。
     */
    public void setDate(Date date) {
        this.date = date;
    }
}
