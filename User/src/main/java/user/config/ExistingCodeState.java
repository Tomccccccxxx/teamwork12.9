package user.config;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 订单已存在状态实现。
 */
@Getter
public class ExistingCodeState extends AbstractCodeState implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public ExistingCodeState() {
        super("订单已存在", 2223);
    }
}