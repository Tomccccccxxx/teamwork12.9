package user.config;

/**
 * 工厂类用来创建各种状态对象。
 */
public class CodeStateFactory {

    public static ICodeState getSuccess() {
        return new SuccessCodeState();
    }

    public static ICodeState getFail() {
        return new FailCodeState();
    }

    public static ICodeState getNull() {
        return new NullCodeState();
    }

    public static ICodeState getExisting() {
        return new ExistingCodeState();
    }

    public static ICodeState getDatabaseError() {
        return new DatabaseErrorState();
    }
}