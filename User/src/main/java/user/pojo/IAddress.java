package user.pojo;

/**
 * 地址对象接口。
 */
public interface IAddress {
    /**
     * 获取省信息。
     *
     * @return 省信息
     */
    String getProvince();

    /**
     * 设置省信息。
     *
     * @param province 新的省信息
     */
    void setProvince(String province);

    /**
     * 获取市信息。
     *
     * @return 市信息
     */
    String getUrban();

    /**
     * 设置市信息。
     *
     * @param urban 新的市信息
     */
    void setUrban(String urban);

    /**
     * 获取区（县）信息。
     *
     * @return 区（县）信息
     */
    String getArea();

    /**
     * 设置区（县）信息。
     *
     * @param area 新的区（县）信息
     */
    void setArea(String area);

    /**
     * 获取街道（具体位置）信息。
     *
     * @return 街道（具体位置）信息
     */
    String getCity();

    /**
     * 设置街道（具体位置）信息。
     *
     * @param city 新的街道（具体位置）信息
     */
    void setCity(String city);
}