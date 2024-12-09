package user.config;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 查询结果为空状态实现。
 */
@Getter
public class NullCodeState extends AbstractCodeState implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public NullCodeState() {
        super("查询为空", 1000);
    }
}