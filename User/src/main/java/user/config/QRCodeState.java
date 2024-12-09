package user.config;

/**
 * 二维码状态接口。
 */
public interface QRCodeState {
    /**
     * 获取二维码描述信息。
     *
     * @return 二维码描述信息
     */
    String getQRCodeMsg();

    /**
     * 设置二维码描述信息。
     *
     * @param qRCodeMsg 新的二维码描述信息
     */
    void setQRCodeMsg(String qRCodeMsg);

    /**
     * 获取二维码状态码。
     *
     * @return 二维码状态码
     */
    Integer getQRCodeState();

    /**
     * 设置二维码状态码。
     *
     * @param qRCodeState 新的二维码状态码
     */
    void setQRCodeState(Integer qRCodeState);
}