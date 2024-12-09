package user.config;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 数据库错误状态实现。
 */
@Getter
public class DatabaseErrorState extends AbstractCodeState implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public DatabaseErrorState() {
        super("数据库出现错误", 3366);
    }
}