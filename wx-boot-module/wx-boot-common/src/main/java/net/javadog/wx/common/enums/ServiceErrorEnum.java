package net.javadog.wx.common.enums;

import lombok.Getter;

/**
 * @Description: 业务枚举类
 * @Author: hdx
 * @Date: 2022/1/30 9:12
 * @Version: 1.0.0
 */
@Getter
public enum ServiceErrorEnum implements AbstractBaseExceptionEnum {

    // 业务错误
    ROAST_EXIST_ERROR(5001, "雄所见略同,此公司名称已存在!"),
    ;

    /**
     * 错误码
     */
    private final Integer resultCode;

    /**
     * 错误描述
     */
    private final String resultMsg;

    ServiceErrorEnum(Integer resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }
}
