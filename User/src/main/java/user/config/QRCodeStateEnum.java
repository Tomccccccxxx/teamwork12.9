package user.config;

import lombok.Getter;

/**
 * 二维码状态 枚举类。
 */
@Getter
public enum QRCodeStateEnum implements QRCodeState {
    FAIL("不存在", 0),
    NOTSCAN("未扫描", 1),
    SCANNED("已扫描", 2),
    OVERDUE("已过期", 3),
    SUCCESS("成功", 1024);

    private final String qRCodeMsg;
    private final Integer qRCodeState;

    QRCodeStateEnum(String qRCodeMsg, Integer qRCodeState) {
        this.qRCodeMsg = qRCodeMsg;
        this.qRCodeState = qRCodeState;
    }

    // 实现接口方法
    @Override
    public String getQRCodeMsg() {
        return this.qRCodeMsg;
    }

    @Override
    public void setQRCodeMsg(String qRCodeMsg) {

    }

    @Override
    public Integer getQRCodeState() {
        return this.qRCodeState;
    }

    @Override
    public void setQRCodeState(Integer qRCodeState) {

    }
}