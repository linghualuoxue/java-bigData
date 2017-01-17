package com.bj.sxt.common;

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


    /**
     * 事件名称
     */
    public static final String LOG_COLUMN_NAME_EVENT_NAME = "en";

    /**
     * 会员id
     */
    public static final String LOG_COLUMN_NAME_MEMBER_ID = "u_mid";

    /**
     * 服务器时间
     */
    public static final String LOG_COLUMN_NAME_SERVER_TIME = "s_time";
    /**
     * 日志收集端的版本信息
     */
    public static final String LOG_COLUMN_NAME_VERSION = "ver";
    /**
     * 用户唯一标示
     */
    public static final String LOG_COLUMN_NAME_UUID = "u_ud";

    /**
     * 会话ID
     */
    public static final String LOG_COLUMN_NAME_SESSION_ID = "u_sd";
    /**
     * 客户端时间
     */
    public static final String LOG_COLUMN_NAME_CLIENT_TIME = "c_time";

    /**
     * 语言
     */
    public static final String LOG_COLUMN_NAME_LANGUAGE = "1";

    /**
     * 浏览器user agent 参数
     */
    public static final String LOG_COLUMN_NAME_USER_AGENT = "b_iev";

    /**
     * 浏览器分辨率大小
     */
    public static final String LOG_COLUMN_NAME_RESOLUTON = "b_rst";
    /**
     * 定义platform
     */
    public static final String LOG_COLUMN_NAME_PLATFORM = "pl";

    /**
     * 当前url
     */
    public static final String LOG_COLUMN_NAME_CURRENT_URL ="p_url";

    /**
     * 前一个页面的url
     */
    public static final String LOG_COLUMM_NAME_REFERRER_URL ="p_ref";
    /**
     * 当前页面的title
     */
    public static final String LOG_COLUMN_NAME_TITLE = "tt";
    /**
     * 订单id
     */
    public static final String LOG_COLUMN_NAME_ORDER_ID = "oid";
    /**
     * 订单名称
     */
    public static final String LOG_COLUMN_NAME_ORDER_NAME = "on";
    /**
     * 订单金额
     */
    public static final String LOG_COLUMN_NAME_ORDER_CURRENCY_AMOUNT = "cua";
    /**
     * 订单货币类型
     */
    public static final String LOG_COLUMN_NAME_ORDER_CURRENCY_TYPE = "cut";
    /**
     * category 名称
     */
    public static final String LOG_COLUMN_NAME_EVENT_CATEGORY = "ca";
    /**
     * action 名称
     */
    public static final String LOG_COLUMN_NAME_EVENT_ACTION = "ac";
    /**
     * kv前缀
     */
    public static final String LOG_COLUMN_NAME_EVENT__KV_START = "kv_";
    /**
     * 操作系统名称
     */
    public static final String LOG_COLUMN_NAME_OS_NAME = "os";
    /**
     * 操作系统版本
     */
    public static final String LOG_COLUMN_NAME_OS_VERSION = "os_v";
    /**
     * 浏览器名称
     */
    public static final String LOG_COLUMN_NAME_BROWSER_NAME = "browser";
    /**
     * 浏览器版本
     */
    public static final String LOG_COLUMN_NAME_BROWSER_VERSION = "browser_v";
    /**
     * ip地址解析的所属国家
     */
    public static final String LOG_COLUMN_NAME_COUNTRY = "country";
    /**
     * ip地址解析的所属省份
     */
    public static final String LOG_COLUMN_NAME_PROVINEC= "provice";

    /**
     * ip地址所解析的所属城市
     */
    public static final String LOG_COLUMN_NAME_CITY = "city";
}
