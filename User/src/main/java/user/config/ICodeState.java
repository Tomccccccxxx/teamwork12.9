package user.config;

/**
 * 接口定义了代码状态的行为。
 */
public interface ICodeState {

    /**
     * 获取状态码
     *
     * @return 状态码
     */
    Integer getCodeState();

    /**
     * 获取状态消息
     *
     * @return 状态消息
     */
    String getCodeMsg();
}