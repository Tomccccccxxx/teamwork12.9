package user.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 订单对象

 */
@Data
@NoArgsConstructor
@AllArgsConstructor
//@ToString(exclude = {"date"})
public class Order {

    /***************** 用户基本信息 ******************************/
    /**
     * 用户编号
     */
    private String userId;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 用户手机
     */
    private String userPhone;
    /**
     * 用户地址
     */
    private Address address;

    /***************** 订单信息 ******************************/
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 订单生成时间。
     * 加入 static 的目的是解决和 lombok @ToString 注解冲突的问题。
     */
    private Date date;
    /**
     * 物品描述
     */
    private String itemDesc;
    /**
     * 当前站点。
     */
    private String thisSite;
    /**
     * 目标站点。
     */
    private String targetSite;

    /**
     * 格式化订单对象
     */
//    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



    /**
     * 获取格式化之后的日期。
     * @return
     *      返回格式化之后的日期信息。
     */
    public String getDate() {
        return
                date != null ?
                        dateFormat.format(date)
                        : null;
    }

    /**
     * 设置日期。
     * @param date：给定传进来的日期进行获取时间日期对象。
     */
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Order(" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", address=" + address +
                ", orderId='" + orderId + '\'' +
                ", date=" + this.getDate() +
                ", itemDesc='" + itemDesc + '\'' +
                ", thisSite='" + thisSite + '\'' +
                ", targetSite='" + targetSite + '\'' +
                ')';
    }
}
