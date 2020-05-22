package com.example.redis.cache.constant;

public interface SecurityConstants {

    /**
     * 刷新
     */
    String REFRESH_TOKEN = "refresh_token";
    /**
     * 验证码有效期
     */
    int CODE_TIME = 60;
    /**
     * 验证码长度
     */
    String CODE_SIZE = "4";
    /**
     * 角色前缀
     */
    String ROLE = "ROLE_";
    /**
     * 前缀
     */
    String QINGDUO_PREFIX = "qingduo_";

    /**
     * oauth 相关前缀
     */
    String OAUTH_PREFIX = "oauth:";


    /**
     * 青朵项目的license  青朵教育
     */
    String QINGDUO_LICENSE = "made by qingduoedu";

    /**
     * 内部
     */
    String FROM_IN = "Y";

    /**
     * 标志
     */
    String FROM = "from";

    /**
     * OAUTH URL
     */
    String OAUTH_TOKEN_URL = "/oauth/token";

    /**
     * 手机号登录URL
     */
    String SMS_TOKEN_URL = "/mobile/token/sms";

    /**
     * 社交登录URL
     */
    String SOCIAL_TOKEN_URL = "/mobile/token/social";
    /**
     * 自定义登录URL
     */
    String MOBILE_TOKEN_URL = "/mobile/token/*";

    /**
     * 微信获取OPENID
     */
    String WX_AUTHORIZATION_CODE_URL = "https://api.weixin.qq.com/sns/oauth2/access_token" +
            "?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    String WX_USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo" +
            "?access_token=%s&openid=%s";

    /**
     * 码云获取token
     */
    String GITEE_AUTHORIZATION_CODE_URL = "https://gitee.com/oauth/token?grant_type=" +
            "authorization_code&code=%S&client_id=%s&redirect_uri=" +
            "%s&client_secret=%s";

    /**
     * 开源中国获取token
     */
    String OSC_AUTHORIZATION_CODE_URL = "https://www.oschina.net/action/openapi/token";

    /**
     * 码云获取用户信息
     */
    String GITEE_USER_INFO_URL = "https://gitee.com/api/v5/user?access_token=%s";

    /**
     * 开源中国用户信息
     */
    String OSC_USER_INFO_URL = "https://www.oschina.net/action/openapi/user?access_token=%s&dataType=json";

    /**
     * {bcrypt} 加密的特征码
     */
    String BCRYPT = "{bcrypt}";
    /**
     * sys_oauth_client_details 表的字段，不包括client_id、client_secret
     */
    String CLIENT_FIELDS = "client_id, CONCAT('{noop}',client_secret) as client_secret, resource_ids, scope, "
            + "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
            + "refresh_token_validity, additional_information, autoapprove";

    /**
     * JdbcClientDetailsService 查询语句
     */
    String BASE_FIND_STATEMENT = "select " + CLIENT_FIELDS
            + " from org_oauth_client_details";

    /**
     * 默认的查询语句
     */
    String DEFAULT_FIND_STATEMENT = BASE_FIND_STATEMENT + " order by client_id";

    /**
     * 按条件client_id 查询
     */
    String DEFAULT_SELECT_STATEMENT = BASE_FIND_STATEMENT + " where client_id = ?";

    /**
     * 资源服务器默认bean名称
     */
    String RESOURCE_SERVER_CONFIGURER = "resourceServerConfigurerAdapter";

    /**
     * 客户端模式
     */
    String CLIENT_CREDENTIALS = "client_credentials";

    /**
     * 用户ID字段
     */
    String DETAILS_USER_ID = "user_id";

    /**
     * 用户名字段
     */
    String DETAILS_USERNAME = "username";

    /**
     * 用户部门字段
     */
    String DETAILS_DEPT_ID = "dept_id";

    /**
     * 用户部门字段
     */
    String CHANNEL_ID = "channel_id";

    /**
     * 租户ID 字段
     */
    String SCHOOL_ID = "school_id";

    /**
     * 协议字段
     */
    String DETAILS_LICENSE = "license";

    /**
     * 激活字段 兼容外围系统接入
     */
    String ACTIVE = "active";

    /**
     * AES 加密
     */
    String AES = "aes";

    /**
     * 微信opendi
     */
    String OPEN_ID = "openid";

    /**
     * 微信返回的错误码
     */
    String ERROR_CODE = "errcode";

    /**
     * 微信appid
     */
    String APP_ID = "appid";

    /**
     * 微信secret
     */
    String APP_SECRET = "secret";

    String CLIENT_ID = "client_id";

    String CLIENT_SECRET = "client_secret";

    String WX_CODE_SUFFIX_URL = "/login/wx/code/webchat";
}
