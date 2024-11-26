package user.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 代码状态枚举类。

 */
public enum MyCodeState{
    /**
     * 成功
     */
    SUCCESS("成功", 0),
    /**
     * 失败
     */
    FAIL("失败", -1),
    /**
     * 查询的结果为空。
     */
    NULL("查询为空", 1000),
    EXISTING("订单已存在", 2223),
    DATABASEERROR("数据库出现错误" ,3366);

    /**
     * 代码状态
     */
    private String codeMsg;
    private Integer codeState;

    MyCodeState() {
    }

    /**
     * 构造枚举
     * @param codeState：代码状态。
     */
    MyCodeState(Integer codeState) {
        this.codeState = codeState;
    }

    MyCodeState(String codeMsg, Integer codeState) {
        this.codeMsg = codeMsg;
        this.codeState = codeState;
    }


    public Integer getCodeState() {
        return codeState;
    }

    public void setCodeState(Integer codeState) {
        this.codeState = codeState;
    }

    public String getCodeMsg() {
        return codeMsg;
    }

    public void setCodeMsg(String codeMsg) {
        this.codeMsg = codeMsg;
    }
}
