package user.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 回传给客户端的对象。
 *
 * @param <T> 指定数据的类型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Results<T> implements IResults<T> {
    /**
     * 代码值
     */
    private String code;

    /**
     * 代码值描述信息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    /**
     * 构造只有代码值和描述信息的返回对象。
     *
     * @param code 代码值
     * @param msg  代码值描述信息
     */
    public Results(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}