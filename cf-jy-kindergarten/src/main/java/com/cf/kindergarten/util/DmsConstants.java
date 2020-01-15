package com.cf.kindergarten.util;

/**
 * @Description :常用常量
 * @Author : xujian
 * @Creation Date : 2018/8/7 16:25
 * _________ ________________
 */

public class DmsConstants {
    //用户类型
    public static String GROUPMANAGER="001";//集团管理员
    public static String SUBSIDIARYMANAGER="002";//子公司管理员
    public static String STAFFMEMBER="003";//职员
    public static String CUSTOMER="004";//经销商
    public static String SUPPLIER="005";//供应商
    public static String MEMBER="006";//会员
    public static String CLERK="107";//经销商店员
    public static String DIRECT="108";//直营店店员

    //账户流水单据类型
    public static String TAR_RECHARGE="充值";
    public static String TAR_CONSUME="消费";
    public static String TAR_WITHDRAW="提现";
    public static String TAR_EXCHANGE="兑换";
    public static String TAR_REBATE="返利";
    public static String TAR_ADJUST="调整";

    //通用状态
    public static String UNVERIFY="0001";//待审核
    public static String VERIFY="0002";//已审核
    public static String CANCEL="0003";//作废
    public static String CLOSE="0004";//已关闭

    //角色ID
    public static String ROLEMEMBER="0552b659e51d41bf94bed082d4b12c8a";//角色会员ID
    public static String ROLECUSTOMER="fffcd66c593949829eab3ecc8264df92";//角色经销商ID
    public static String ROLECLERK="f89781480e0f47e9968ac722a072cddc";//角色经销商店员ID
    public static String CUSTOMERROLES="fffcd66c593949829eab3ecc8264df92";//客户角色
    public static String ORDINARYUSERS="20c6471ccb014733afa963c1c05b7d71";//普通用户角色-crm

    //动作
    public static String ADD="add";//新增动作
    public static String EDIT="edit";//修改动作
    public static String DELETE="delete";//删除动作

    //组织类型
    public static String ORG_SUPPLIER="001";//供应商
    public static String ORG_MANUFACTOR="002";//厂家
    public static String ORG_DISTRIBUTOR="003";//经销商
    //会员账户类型
    public static String PREPAYMENTS="2f3a74c7bb62405cac5fdf9e1fde44f3";//预付款
    public static String INTEGRAL="a2d6e85fdedb44b69c18a3bbff2e4e60";//积分

    //订单提交状态
    public static String UNVERIFY_SUB="0001";//未提交
    public static String VERIFY_SUB="0002";//已提交

    //订单付款状态
    public static String PAY_UNPAID="0001";//未付款
    public static String PAY_PAID="0002";//已付款

    //账户类型
    public static String ACCOUNTTYPE0001="0001"; //预付款账户
    public static String ACCOUNTTYPE0002="0002"; //经营补贴
    public static String ACCOUNTTYPE0003="0003"; //代理商推荐奖
    public static String ACCOUNTTYPE0004="0004"; //市场扶持费

    //客户账户类型
    public static String ACCOUNT_PREPAYMENTS="8d86936645da47a58adb25b53b145bd3";//预付款账户
    public static String ACCOUNT_FUCHI="6669c9a08a1e457686f03926744d399a";//市场扶持费
    public static String ACCOUNT_BUTIE="83ac19b2503d491e866cbbe7b3973ce3";//经营补贴
    public static String ACCOUNT_TUIJIAN="e427b194cabf459b8dc5b384217644b1";//代理商推荐奖

    public static String RESULT_FAIL = "0"; //失败
    public static String RESULT_SUCCESS = "1"; //成功

    public static  String CURRENCY="0001";//币别  人民币

    public static String RECHARGE="0002";//充值
    public static String DEDUCTION="0001";//扣款

    //退款状态
    public static String Refunds="0001";//待退款
    public static String Refunded="0002";//已退款
    //客户性质
    public static String iproperty_jingxiaoshang="0001";//经销商
    public static String iproperty_zhiyingdian="0002";//直营店
    //会员订单类型

    public static String ORDINARY_ORDER="0001";//普通订单
    public static String POINT_ORDER="0002";//积分订单
    public static String COMMON_ORDER="0004";//常规订单
    public static String OVER_ORDER="0005";//压货订单
    public static String PIKCUP_ORDER="0006";//提货订单
    public static String POINTGOOD_ORDER="0007";//商品兑换订单

    //功能码
    public static String PIKCUP_ORDER_FUN="0009021";//提货订单功能码
    public static String POINTGOOD_ORDER_FUN="0009023";//积分订单功能码
    public static String COMMON_ORDER_FUN="00090002";//常规订单、压货订单功能码
    //会员等级
    public static String ORDINARY_GRADE="70a4b9d337be4fa6bbbaa6279950acca";
    //客户性质
    public static String CUSTOMER_DEALER="0001";//经销商
    public static String CUSTOMER_DIRECT_TORE="0002";//直营店
    public static String CUSTOMER_E_COMMERCE="0003";//电商
    public static String CUSTOMER_MICRO_BUSINESS="0004";//微商
    //销售方式
    public static String METHOD_OF_SALES_ADVANCECHARGE="0003";//预付款
    public static String METHOD_OF_SALES_PAYAFTERVERIFY="0002";//货到付款
    public static String METHOD_OF_SALES_DIRECTSALE="0001";//直营店

    //微信支付状态
    public static String PAYS_STATUS_SUCCESS="SUCCESS";//成功
    public static String PAYS_STATUS_FAIL="FAIL";//失败

    //付款状态
    public static String ALREADYPAID="0002";//已付款
    public static String UNPAID="0001";//待付款

    public static String WECHAT_CUSTOMER="ec9cffb6cea24171ba356471fb0c37c4";//微商城专用经销商
    //支付方式
    public static String PAY_WECHAT="0003";//微信支付

    //微商城订单状态
    public static String PENDINGPAY="0001";//待付款
    public static String PENDINGDELIVERY="0002";//待发货
    public static String DELIVERED="0003";//已发货
    public static String COMPLETED="0004";//已签收
    public static String TRADECLOSED="0005";//交易关闭

    //微信默认模板ID
    //增宇
    //public static String TEMPLATE_ID="wDbWTEKWWWFTaSBHIr_dOpPVZKvBIMHvVijXU4Jbo7M";
    //麒麟
    public static String TEMPLATE_ID="pnfwnkATGYQdRN05kiP8Ii6FTe3f-d8HuPzP6o-kvUE";

    //默认积分账户
    public static String INTEGRALACCOUNT="a2d6e85fdedb44b69c18a3bbff2e4e60";
    //group_id
    public static String GROUP_ID="2359faa47ac342c98f4d0233a181c5a7";
    //findCode
    public static String FIND_CODE="0000100002";
    //会员资料功能码
    public static String ECMEMBERFUN="00100001";
    //上传状态
    public static String NOTUPLOADED="0001";
    public static String UPLOADED="0002";
    //流程状态
    //状态1、提交完成 4、处理未完成 5、处理完成 6、并发完成 7、撤回 8、驳回 9、弃审 10、终止
    public static String NODESTATE1="1";
    public static String NODESTATE4="4";
    public static String NODESTATE5="5";
    public static String NODESTATE6="6";
    public static String NODESTATE7="7";
    public static String NODESTATE8="8";
    public static String NODESTATE9="9";
    public static String NODESTATE10="10";
    //end
    //金额类型
    public static String AMOUNTTYPE0001="0001";//元
    public static String AMOUNTTYPE0002="0002";//万元

    //报表分组类型
    public static String GROUPTYPE0001="0001";//区域
    public static String GROUPTYPE0002="0002";//单位
    public static String GROUPTYPE0003="0003";//部门
    public static String GROUPTYPE0004="0004";//人员


    //提货订单提货状态
    public static String PICKUPGOODSSTATUS01="0001"; //未开始提货
    public static String PICKUPGOODSSTATUS02="0002"; //已部分提货
    public static String PICKUPGOODSSTATUS03="0003"; //已全部提货

    public static String CMAKEROUT = "迪润财务主管"; //制单人名称
    public static String IMAKEROUT = "20"; //制单人ID
    public static Integer IDWAREHOUSE = 1; //制单人ID

    //付款方式
    public static String PAYMETHOD_ONLINE="0001";  //线上，账户余额
    public static String PAYMETHOD_UNDERLINE="0002"; //线下



}
