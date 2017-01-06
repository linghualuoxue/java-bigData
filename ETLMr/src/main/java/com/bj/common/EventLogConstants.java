package com.bj.common;

/**定义日志收集的参数名称和hbase表的列簇名称
 * Created by user on 2017/1/5.
 */
public class EventLogConstants {

    public static enum EventEnum{
        LAUNCH(1,"launch event","e_l"),//launch事件，表示第一次访问
        PAGEVIEW(2,"page view event","e_pv"),//页面浏览事件
        CHARGERREQUEST(3,"charge request event","e_crt"),//订单生成事件
        CHARGESUCCESS(4,"charge success event","e_cs"),//订单成功支付事件
        CHARGEREFUND(5,"charge refund event","e_cr"),//订单退款事件
        EVENT(6,"event duration event","e_e");//事件

        public final int id;//Id 唯一标识
        public final String name;//名称
        public final String alias;//别名

        private EventEnum(int id,String name,String alias){
            this.id = id;
            this.name = name;
            this.alias = alias;
        }

        public static EventEnum valueOfAlias(String alias){
            for (EventEnum eventEnum : values()) {
                if(eventEnum.alias.equals(alias)){
                    return eventEnum;
                }
            }
            return null;
        }
    }

    /**
     * 表名称
     */
    public static final String HBASE_NAME_EVENT_LOGS = "event_logs";

    /**
     * 列簇名称
     */
    public static final String EVENT_LOGS_FAMILY_NAME = "info";

    /**
     * 日志分割符
     */
    public static final String LOG_SEPARTION = "\\^A";
    /**
     * 用户ip地址
     */
    public static final String LOG_COLUMN_NAME_IP = "ip";

}
