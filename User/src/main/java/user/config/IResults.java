package user.config;

/**
 * 回传给客户端的对象接口。
 *
 * @param <T> 指定数据的类型
 */
public interface IResults<T> {
    /**
     * 获取代码值。
     *
     * @return 代码值
     */
    String getCode();

    /**
     * 设置代码值。
     *
     * @param code 新的代码值
     */
    void setCode(String code);

    /**
     * 获取代码值描述信息。
     *
     * @return 代码值描述信息
     */
    String getMsg();

    /**
     * 设置代码值描述信息。
     *
     * @param msg 新的代码值描述信息
     */
    void setMsg(String msg);

    /**
     * 获取数据。
     *
     * @return 数据
     */
    T getData();

    /**
     * 设置数据。
     *
     * @param data 新的数据
     */
    void setData(T data);
}