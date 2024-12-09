package user.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 抽象类实现了ICodeState接口，提供了默认构造方法。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractCodeState implements ICodeState {

    private String codeMsg;
    private Integer codeState;

    // 如果有更多公共逻辑，可以在这里添加
}