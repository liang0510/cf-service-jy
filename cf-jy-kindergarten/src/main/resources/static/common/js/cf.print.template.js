window['testTemplate'] = function (LODOP, item) {
  LODOP.PRINT_INIT('');
  LODOP.ADD_PRINT_TEXT(123, 87, 233, 119, item.test || "新加文本1");
  LODOP.ADD_PRINT_TEXT(165, 481, 100, 58, item.test || "新加文本2");
}

window['printTest1'] = function (LODOP, item) {
  LODOP.PRINT_INIT("");
  LODOP.ADD_PRINT_TEXT(76, 81, 100, 20, item.value);
  LODOP.ADD_PRINT_ELLIPSE(142, 73, 100, 60, 0, 1);
  LODOP.ADD_PRINT_TEXT(27, 374, 100, 20, item.label);
}

window['printTest2'] = function (LODOP, item) {
  LODOP.PRINT_INIT("");
  LODOP.ADD_PRINT_TEXT(101, 133, 100, 20, item.value);
  LODOP.ADD_PRINT_TEXT(264, 108, 100, 20, item.label);
}

window['printTest3'] = function (LODOP, item) {

    LODOP.PRINT_INIT("");
    LODOP.ADD_PRINT_TEXT(22,311,164,39,"客户订单统计");
    LODOP.SET_PRINT_STYLEA(0,"FontName","微软雅黑");
    LODOP.SET_PRINT_STYLEA(0,"FontSize",16);
    LODOP.SET_PRINT_STYLEA(0,"Bold",1);
    LODOP.ADD_PRINT_TABLE(84,49,900,900,customerScOrder(item));

}

function customerScOrder(item) {
    var str="";
    var list=item.dataSource;
    console.info(list.length);
    if(list && list.length>0){
        for(var i=0;i<list.length;i++){
           var itemObj=list[i];
            str+="<tr>\n" +
                "  <td>"+itemObj.name+"</td>\n" +
                "  <td>"+itemObj.classification+"</td>\n" +
                "<td>"+itemObj.grade+"</td>\n" +
                "  <td>"+itemObj.orderNum+"</td>\n" +
                "<td>"+itemObj.totalAmount+"</td>\n" +
                "</tr>\n" ;
        }
    }

    var html="<style> table,td,th {border: 1px solid black;border-style: solid;border-collapse: collapse}</style>\n" +
        "<table border=\"1\">\n" +
        "<COL><COL WIDTH=100>\n" +
        "<tr>\n" +
        "  <td width=\"40%\">客户名称</td>\n" +
        "  <td width=\"15%\">分类</td>\n" +
        "<td width=\"15%\">等级</td>\n" +
        "<td width=\"15%\">数量</td>\n" +
        "<td width=\"15%\">金额</td>\n" +
        "</tr>\n" +str +
        "</table>";
    console.info(html);
    return html;
}
window['buServiceBillPrinter'] = function (LODOP, item) {
    var obj=item.data;
    if(obj){
        LODOP.PRINT_INIT("服务工单打印");
        LODOP.PRINT_INITA("1cm","0.8cm","21cm","29.7cm","服务工单打印");
        LODOP.SET_PRINT_MODE("PRINT_NOCOLLATE",1);
        LODOP.ADD_PRINT_TEXT(30,40,146,20,"UFS/QP/2-B1/QR/01");
        LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
        LODOP.ADD_PRINT_TEXT(54,290,163,31,"现场服务记录单");
        LODOP.SET_PRINT_STYLEA(0,"FontSize",15);
        LODOP.SET_PRINT_STYLEA(0,"Bold",1);
        LODOP.ADD_PRINT_TEXT(95,40,100,25,obj.ctime);//记录时间
        LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
        LODOP.ADD_PRINT_TEXT(95,250,100,25,obj.cdate);//记录日期
        LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
        LODOP.ADD_PRINT_TEXT(95,500,150,25,obj.code);//服务单号
        LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
        LODOP.ADD_PRINT_TABLE("3.2cm","1cm","17cm","24cm",billTable(obj));
        //LODOP.ADD_PRINT_TEXT(900,40,625,25,"服务热线：0516-85861168                     服务监督及投诉电话：0516-85861208");
        LODOP.ADD_PRINT_TEXT(900,40,625,25,obj.priter_tel);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
        //LODOP.ADD_PRINT_TEXT(905,40,625,25,"\n在线服务网址：http://t.ufida.com.cn         注：本服务单由服务工程师录入CRM系统");
        LODOP.ADD_PRINT_TEXT(905,40,625,25,"\n"+obj.printer_URL);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
    }
}
function billTable(item) {
    var html="<style> table,td,th {border: 1px solid black;border-style: solid;border-collapse: collapse}</style><table border=\"1\">\n" +
        "<tr>\n" +
        "  <td width=\"165\">用户单位</td>\n" +
        "  <td colspan=\"3\">"+item.cusname+"</td>\n" +
        "</tr>\n" +
        "<tr>\n" +
        "  <td width=\"165\">联系人</td>\n" +
        "  <td width=\"165\">"+item.ccustperson+"</td>\n" +
        "  <td width=\"165\">联系电话</td>\n" +
        "  <td width=\"165\">"+item.ctel+"</td>\n" +
        "</tr>\n" +
        "<tr>\n" +
        "  <td width=\"165\">单位地址</td>\n" +
        "  <td colspan=\"3\">"+item.caddress+"</td>\n" +
        "</tr>\n" +
        "<tr>\n" +
        "  <td width=\"165\">乘车路线</td>\n" +
        "  <td colspan=\"3\">"+item.custLine+"</td>\n" +
        "</tr>\n" +
        "<tr>\n" +
        "  <td width=\"165\">产品版本</td>\n" +
        "  <td width=\"165\">"+item.cpdname+"</td>\n" +
        "  <td width=\"165\">加密锁号/序列号</td>\n" +
        "  <td width=\"165\">"+item.cproductno+"</td>\n" +
        "</tr>\n" +
        "<tr>\n" +
        "  <td width=\"165\" colspan=\"4\" height=\"90\" valign=\"top\" style=\"border-right:none;\">\n" +
        "    <div style=\"height: 85px;overflow: hidden;\"><span>故障现象：</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>"+item.cdetail+"</span><br></div>\n" +
        "   " +
        "   <div style=\"text-align: right;bottom: 0\"><span>热线工程师：</span><span>"+item.cmaker+"</span>" +
        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
        " <span>提交人：</span>"+item.jiedanPerson+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>\n" +
        "  </td>\n" +
        "</tr>\n" +
        "<tr>\n" +
        "  <td width=\"165\">预约时间：</td>\n" +
        "  <td width=\"165\">"+item.daskprocess+"</td>\n" +
        "  <td width=\"165\">变更预约时间：</td>\n" +
        "  <td width=\"165\"></td>\n" +
        "</tr>\n" +
        "<tr>\n" +
        "<td width=\"165\" colspan=\"2\">到达时间：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>月</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>日</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>时</span></td>\n" +
        "  <td width=\"165\" colspan=\"2\">离开时间：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>月</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>日</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>时</span>\n" +
        "  </td>\n" +
        "  </tr>\n" +
        "<tr>\n" +
        "  <td width=\"165\" colspan=\"4\" height=\"100\" valign=\"top\" style=\"border-right:none;\">\n" +
        "  <span>问题现象描述（用户演示描述的现象）：</span><br><span>"+item.cdescription+"</span><br>\n" +
        "  </td>\n" +
        "</tr>\n" +
        "<tr>\n" +
        "  <td colspan=\"4\">问题诊断及解决过程（请写详细步骤）</td>\n" +
        "</tr>\n" +
        " <tr>\n" +
        "  <td width=\"165\" colspan=\"4\" height=\"160\" valign=\"top\" style=\"border-right:none;\">\n" +
        "   <div  style=\"height: 160px;overflow: hidden;\"><span>"+item.cprocess+"</span></div>" +
        "   <div  style=\"text-align: right;bottom: 0\"><span>服务工程师签字：</span><span></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>日期：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></div>\n" +
        "  </td>\n" +
        "</tr>\n" +
        "<tr>\n" +
        "  <td colspan=\"4\">问题解决情况：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>□&nbsp;&nbsp;&nbsp;全部解决</span>\n" +
        "  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>□&nbsp;&nbsp;&nbsp;部分解决</span>\n" +
        "  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>□&nbsp;&nbsp;&nbsp;未解决</span>\n" +
        "  </td>\n" +
        "</tr>\n" +
        " <tr>\n" +
        "  <td width=\"165\" colspan=\"4\" height=\"80\" valign=\"top\" style=\"border-right:none;\">\n" +
        "   <span>您对我们服务的意见.建议：</span><br><span>"+item.cfeedback+"</span><br>\n" +
        "  <div  style=\"text-align: right;margin-top: 40\"> <span>用户签字盖章：</span><span></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  <span>日期：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span></span></div>\n" +
        "  </td>\n" +
        "</tr>\n" +
        "<tr>\n" +
        "  <td width=\"165\" colspan=\"4\"  valign=\"top\" >\n" +
        "   <span>上报总部产品支持&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;问题号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日期：</span>\n" +
        "   <br>\n" +
        "   <span>再派现场服务人员&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;再派服务记录单号：</span>\n" +
        "   <br>\n" +
        "   <span>执行完毕存档&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;□&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;值班经理签字：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日期：</span>\n" +
        "  </td>\n" +
        "</tr>\n" +
        "<tr>\n" +
        "  <td colspan=\"4\" height=\"35\"><span>产品到货：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
        "加密狗号：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
        " 收货人签字： &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日期：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>\n" +
        "  </td>\n" +
        "</tr>\n" +
        "\n" +
        "</table>";
    console.log(html);
    return html;
}