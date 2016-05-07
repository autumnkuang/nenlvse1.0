package com.guiguzi.common.enums;

public enum PayStatusEnum {

    ONEENUM(PayStatusEnum.ONE, "未支付"),
    TWOENUM(PayStatusEnum.TWO, "已取消"),
    THREEENUM(PayStatusEnum.THREE, "支付中"),
    FOURENUM(PayStatusEnum.FOUR, "已支付"),
    FIVEENUM(PayStatusEnum.FIVE, "支付失败"),
    SIXENUM(PayStatusEnum.SIX, "退款中"),
    SEVENENUM(PayStatusEnum.SEVEN, "退款失败"),
    EIGHTENUM(PayStatusEnum.EIGHT, "退款处理中"),
    NINEENUM(PayStatusEnum.NINE, "已发货"),
    TENNUM(PayStatusEnum.TEN, "已退款");

    /**
     * 1未支付，2已取消，3支付中，4已支付，5支付失败，6退款中，7退款失败，8退款处理中，9已发货,10已退款
     */
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int FOUR = 4;
    public static final int FIVE = 5;
    public static final int SIX = 6;
    public static final int SEVEN = 7;
    public static final int EIGHT = 8;
    public static final int NINE = 9;
    public static final int TEN = 10;

    private Integer status;
    private String text;

    private PayStatusEnum(Integer status, String text) {
        this.status = status;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Integer getStatus() {
        return status;
    }

    public static PayStatusEnum getType(Integer status){
        for (PayStatusEnum tt : PayStatusEnum.values()) {
            if (tt.getStatus().equals(status)){
                return tt;
            }
        }
        return null;
    }
}
