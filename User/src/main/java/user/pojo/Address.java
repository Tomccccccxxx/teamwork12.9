package user.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 地址对象.

 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String urban;
    /**
     * 区（县）
     */
    private String area;
    /**
     * 街道（具体位置）
     */
    private String city;
}
