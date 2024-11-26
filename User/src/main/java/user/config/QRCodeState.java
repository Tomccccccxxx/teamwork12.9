package user.config;

/**
 * 二维码状态 枚举类。

 */
public enum QRCodeState {
    FAIL("不存在",0)
    ,NOTSCAN("未扫描",1)
    ,SCANNED("已扫描",2)
    ,OVERDUE("已过期",3)
    ,SUCCESS("成功", 1024);

    /**
     * 二维码描述信息
     */
    private String qRCodeMsg;
    /**
     * 二维码状态
     */
    private Integer qRCodeState;

    QRCodeState() {
    }

    QRCodeState(String qRCodeMsg, Integer qRCodeState) {
        this.qRCodeMsg = qRCodeMsg;
        this.qRCodeState = qRCodeState;
    }


    public String getqRCodeMsg() {
        return qRCodeMsg;
    }

    public void setqRCodeMsg(String qRCodeMsg) {
        this.qRCodeMsg = qRCodeMsg;
    }

    public Integer getqRCodeState() {
        return qRCodeState;
    }

    public void setqRCodeState(Integer qRCodeState) {
        this.qRCodeState = qRCodeState;
    }
}
