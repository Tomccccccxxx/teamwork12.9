package user.config;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 成功状态实现。
 */
@Getter
public class SuccessCodeState extends AbstractCodeState implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public SuccessCodeState() {
        super("成功", 0);
    }
}