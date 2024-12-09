package user.config;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 失败状态实现。
 */
@Getter
public class FailCodeState extends AbstractCodeState implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public FailCodeState() {
        super("失败", -1);
    }
}