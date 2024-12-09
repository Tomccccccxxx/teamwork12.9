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
public class Address implements IAddress {
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

    // Lombok 的 @Data 注解已经自动生成了 getter 和 setter 方法，
    // 因此这里不需要显式地重写接口中的方法。
}