window['testAction'] = function(form) {
  return form;
}
/**
 *客户价格发布修改参考折扣改变子表折扣和调整价
 * @param form
 * @returns {*}
 */
window['customerPriceAction'] = function(form) {
  var referenceDiscount=form.referenceDiscount;
    if(referenceDiscount&&referenceDiscount>0){
      var list = form['cs_customer_price_goodssubtable'];
      if(list && list.length>0){
         var item="";
         for(var i=0;i<list.length;i++){
             list[i].discount=referenceDiscount;
             list[i].adjustPrice=accMul(referenceDiscount,list[i].salesPrice);
         }
      }
    }
    return form;
}

/*
订单更换用户删除子表和收货信息
 */
window['clearOrders'] = function(form) {
    console.info(form);
    form['consignee']='';
    form['receivingAddress']='';
    form['goodsAmount']='';
    form['discountOfamount']='';
    form['totalAmount']='';
    form['sc_orders']=[];
    return form;
}

/*
退货单切换原订单和非原订单清空子表
 */
window['clearReturnOrders'] = function(form) {
    console.info(form);
    form['returnQuantity']='';
    form['goodsAmount']='';
    form['sc_return_goodss']=[];
    return form;
}

/*
终端退货单切换原订单和非原订单清空子表
 */
window['clearEcMemberReturnOrders'] = function(form) {
    console.info(form);
    form['total']='';
    form['totalAmount']='';
    form['ec_return_goodss']=[];
    return form;
}

/**
 * 会员订单子表计算
 */
window['ecMemberOrderPrice'] = function(form) {
        var list = form['ec_member_orders'];
        var discount = form['discount'];
        var sumMoney = 0;//折后金额
        var quantity = 0;
        var total = 0;
        var i=form["sysFormulaIndex"];
        if(list && list.length>0&&list.length>i){
                if(list[i].standardPrice){
                    if(list[i].quantity ){
                        list[i].price=accMul(list[i].standardPrice,discount);//折后单价
                        list[i].discountAmount=accMul(list[i].price,list[i].quantity);//折后金额
                        list[i].auxiliaryQuantity=accDiv(list[i].quantity,list[i].rate);
                    }
                }
        }
        /*form.money=sumMoney;//折后金额
        form.totalAmount=quantity;//总数量
        form.total=total;//总金额*/

    return form;
}
/**
 * 修改折扣、叠加优惠、装修优惠触发
 * @param form
 * @returns {*}
 */
window['scOrderPrice'] = function(form) {

        var list = form['sc_orders'];
        var i=form["sysFormulaIndex"]
        var goodsAmount=0;//商品总金额
        var orderAmount=0;//订单金额
        var totalAmount=0;//实付总金额
        var discountOfamount=0;//优惠总金额
        var overlayOfferTotal=0;//叠加优惠
        var decorationOfferTotal=0;//装修优惠
        var freight=0;//运费
        if(list && list.length>0&&list.length>i){
            freight=form.freight
            var discount=isNullTo0(list[i].discount);//折扣
            var overlayOffer=isNullTo0(list[i].overlayOffer);//叠加优惠
            var decorationOffer=isNullTo0(list[i].decorationOffer);//装修优惠
            var discountAmount=isNullTo0(list[i].discountAmount);//优惠金额
            var realPrice=isNullTo0(list[i].realPrice);//实付价
            var payAmount=isNullTo0(list[i].payAmount);//实付总金额
            var quantity=isNullTo0(list[i].quantity);//数量
            var standardAmount=isNullTo0(list[i].standardAmount);//标准金额
            var standardPrice=isNullTo0(list[i].standardPrice);//标准价
            realPrice=accMul(accMul(standardPrice,discount),0.01);//实付价
            payAmount=accMul(realPrice,quantity);
            list[i].discountAmount=accSub(standardAmount,payAmount);//优惠金额
            /*payAmount=accSub(payAmount,overlayOffer);
            payAmount=accSub(payAmount,decorationOffer);*/
            list[i].payAmount=payAmount;
            list[i].realPrice=realPrice;
            for(var a=0;a<list.length;a++){
                goodsAmount=accAdd(goodsAmount,list[a].standardAmount);
                orderAmount=accAdd(orderAmount,list[a].payAmount);
                discountOfamount=accAdd(discountOfamount,list[a].discountAmount);
                /*decorationOfferTotal=accAdd(decorationOfferTotal,list[a].decorationOffer);
                overlayOfferTotal=accAdd(overlayOfferTotal,list[a].overlayOffer);*/
            }
            totalAmount=accAdd(orderAmount,freight);
            form.goodsAmount=goodsAmount;
            form.orderAmount=orderAmount;
            form.discountOfamount=discountOfamount;
            form.totalAmount=totalAmount;
            /*form.decorationOffer=decorationOfferTotal;
            form.overlayOffer=overlayOfferTotal;*/
        }


    return form;
}

/**
 * 修改实付价算折扣
 * @param form
 * @returns {*}
 */
window['scOrderPriceDiscount'] = function(form) {

    var list = form['sc_orders'];
    var i=form["sysFormulaIndex"]

    if(list && list.length>0&&list.length>i){
        var quantity=isNullTo0(list[i].quantity);//数量
        var standardAmount=isNullTo0(list[i].standardAmount);//标准金额
        var standardPrice=isNullTo0(list[i].standardPrice);//标准价
        var realPrice=isNullTo0(list[i].realPrice);//实付价
        var discount=accMul(accDiv(realPrice,standardPrice),100);//折扣
        var payAmount=accMul(realPrice,quantity);
        list[i].discountAmount=accSub(standardAmount,payAmount);//优惠金额
        list[i].payAmount=payAmount;
        list[i].discount=discount.toFixed(2);

    }


    return form;
}
/**
 * 计算客商资产金额
 * @param form
 * @returns {*}
 */
window['custProduct'] = function(form) {

    var list = form['cs_custproductdetail'];
    var i=form["sysFormulaIndex"];
    var fsum="0";//标准金额
    var orderfsum="0";//成交金额
    console.log(list.length);
    if(list && list.length>0&&list.length>i){
        var quantity=isNullTo0(list[i].quantity);//数量
        var standardAmount=isNullTo0(list[i].basicMoney);//标准金额
        var standardPrice=isNullTo0(list[i].vdef2);//标准价
        var realPrice=isNullTo0(list[i].vdef1);//实付价
        var discount=accMul(accDiv(realPrice,standardPrice),100);//折扣
        var payAmount=accMul(realPrice,quantity);
        list[i].basicMoney=accMul(standardPrice,quantity);//
        list[i].realMoney=payAmount;
        list[i].discount=discount.toFixed(2);
        for(var a=0;a<list.length;a++){
            fsum=accAdd(fsum,list[a].basicMoney);
            console.log("jiben"+list[a].basicMoney);
            orderfsum=accAdd(orderfsum,list[a].realMoney);
            console.log("shiji"+list[a].realMoney);
        }
    }
    var orderdiscout=accMul(accDiv(orderfsum,fsum),100);//折扣
    orderdiscout=orderdiscout.toFixed(2);
    form['fsum']=fsum;
    form['orderfsum']=orderfsum;
    form['discount']=orderdiscout;
    return form;
}

window['custProduct2'] = function(form) {
    var list = form['cs_custproductdetail'];
    var i=form["sysFormulaIndex"];
    var fsum="0";//标准金额
    var orderfsum="0";//成交金额
    if(list && list.length>0&&list.length>i){
        var quantity=isNullTo0(list[i].quantity);//数量
        var realMoney=isNullTo0(list[i].realMoney);//实付金额
        var standardPrice=isNullTo0(list[i].vdef2);//标准价
        var realPrice=accDiv(realMoney,quantity).toFixed(2);//实付价
        var discount=accMul(accDiv(realPrice,standardPrice),100);//折扣
        var payAmount=accMul(realPrice,quantity);
        list[i].vdef1=realPrice;
        list[i].discount=discount.toFixed(2);
        for(var a=0;a<list.length;a++){
            fsum=accAdd(fsum,list[a].basicMoney);
            orderfsum=accAdd(orderfsum,list[a].realMoney);
        }
    }
    var orderdiscout=accMul(accDiv(orderfsum,fsum),100);//折扣
    orderdiscout=orderdiscout.toFixed(2);
    form['fsum']=fsum;
    form['orderfsum']=orderfsum;
    form['discount']=orderdiscout;
    return form;
}



window['viporderprice'] = function(form) {
    var fsum = form['ec_member_orders'][form['sysFormulaIndex']]['discountAmount'];
    var quantity = form['ec_member_orders'][form['sysFormulaIndex']]['quantity'];
    form['ec_member_orders'][form['sysFormulaIndex']]['price'] = fsum / quantity;
    return form;
};
//默认联系人只能一个
window['defaultContact'] = function(form) {
    var cs_contacts = form["cs_contact"];
    var index= form["sysFormulaIndex"];
    if(cs_contacts && cs_contacts.length>0){
        for (var i = 0;i<cs_contacts.length;i++){
            if(i != index){
                cs_contacts[i].ynDefault='0';
            }
        }
    }
    return form;
};
//默认地址只能一个
window['defaultAddress'] = function(form) {
    var addresses = form["cs_receivingaddress"];
    var index= form["sysFormulaIndex"];
    if(addresses && addresses.length>0){
        for (var i = 0;i<addresses.length;i++){
            if(i != index){
                addresses[i].ynDefault='0';
            }
        }
    }
    return form;
};

//默认地址只能一个
window['defaultMemberAddress'] = function(form) {
    var addresses = form["ec_memberaddress"];
    var index= form["sysFormulaIndex"];
    if(addresses && addresses.length>0){
        for (var i = 0;i<addresses.length;i++){
            if(i != index){
                addresses[i].ynDefault='0';
            }
        }
    }
    return form;
};

//选择服务类型后更新服务工单编码
window['updateServiceBillCode'] = function(form) {
    var serviceType = form.processStyle;
    var codes = form.code;
    if(codes.length > 12) {
        var i = codes.indexOf("2");
        codes = codes.substring(i,codes.length);
    }
    var co = "";
    if("0001" == serviceType) {
        co = "DH";
    }
    if("0002" == serviceType) {
        co = "YC";
    }
    if("0003" == serviceType) {
        co = "XC";
    }
    var newCode = co + codes;
    form.code = newCode;
    return form;
};

//计算费用报销单据数及金额
window['CalOaCostSubmit'] = function(form) {
    var invoiceCount = 0;//总单据数
    var costMoney = 0;//总报销金额
    var sumMoney = 0;//费用总额
    var ocsa = form["oa_cost_submit_applys"]==undefined?new Array():form["oa_cost_submit_applys"];//费用申请单
    var ocsb = form["oa_cost_submit_bills"]==undefined?new Array():form["oa_cost_submit_bills"];//费用报销来源单
    var ocst = form["oa_cost_submit_travel"]==undefined?new Array():form["oa_cost_submit_travel"];//差旅费用
    var ocso = form["oa_cost_submit_other"]==undefined?new Array():form["oa_cost_submit_other"];//其他费用
    if(ocsb.length>0){
        for (var i = 0;i<ocsb.length;i++){
            costMoney = accAdd(costMoney,isNullTo0(ocsb[i].costMoney));
        }
    }
    /*if(ocsa.length>0){
        for (var i = 0;i<ocsa.length;i++){
            costMoney = accSub(costMoney,isNullTo0(ocsa[i].costMoney));
        }
    }*/
    if(ocst.length > 0) {
        for (var i = 0;i<ocst.length;i++){
            ocst[i].totalAmount = accAdd(accAdd(ocst[i].accommodationMoney,ocst[i].allowanceMoney),ocst[i].trafficAmount);
            invoiceCount = accAdd(invoiceCount,isNullTo0(ocst[i].invoiceCount));
            sumMoney = accAdd(sumMoney,isNullTo0(ocst[i].totalAmount));
        }
    }
    if(ocso.length>0){
        for (var i = 0;i<ocso.length;i++){
            invoiceCount = accAdd(invoiceCount,isNullTo0(ocso[i].invoiceCount));
            sumMoney = accAdd(sumMoney,isNullTo0(ocso[i].costMoney));
        }
    }
    form.oa_cost_submit_travel = ocst;
    form.invoiceCount = invoiceCount;
    form.costMoney = costMoney;
    form.sumMoney = sumMoney;
    return form;
};

function dateFormat(value){
    return value.getFullYear() + "-" +  dataLeftCompleting(value.getMonth()+1) + "-" + dataLeftCompleting(value.getDate());
}
function dateMonthSub(value1,value2){
    return (value1.getFullYear()-value2.getFullYear())*12+(value1.getMonth()-value2.getMonth());
}

function dataLeftCompleting(value){
    return parseInt(value) < 10 ? "0" + value : value;
}

//开始结束时间验证及人天自动计算--实施管理
window['calImplBeginEndDate'] = function(form) {
    var bds = ["planBeginTime","planBeginDate","beginDate"];
    var eds = ["planEndTime","planEndDate","endDate"];
    var wds = ["planDays","planDays","planDays"];
    var i = 0;
    for(var j=0;j<bds.length;j++){
        if(form[bds[j]]!=undefined){
            i=j;
            break;
        }
    }
    var beginDate = new Date(form[bds[i]].replace(/-/g,"/"));
    var endDate = new Date(form[eds[i]].replace(/-/g,"/"));
    var workdays = 0;
    if(isNullTo0(beginDate)!=0 && isNullTo0(endDate)!=0){
        if(beginDate>endDate){
            window.message.error("结束日期不得早于开始日期");
            form[eds[i]] = "";
        }else{
            workdays = parseInt((endDate.getTime()-beginDate.getTime())/(1000*60*60*24));
        }
    }
    form[wds[i]] = isNaN(workdays)?0:(workdays*8);
    return form;
}

//目标明细计算公式(全自动)
window['defaultTargetsAuto'] = function(form) {
    var goals = form["bu_goals"]==undefined?new Array():form["bu_goals"];
    var goalValue = isNullTo0(form["goalValue"])+"";//基础指标
    var testGoal = isNullTo0(form["testGoal"])+"";//考核指标
    var sportGoal = isNullTo0(form["sportGoal"])+"";//冲刺指标
    var type = form["type"];//目标类型 年 半年 季 月
    var totalPercent = 100;
    var goalsLen = 0;//子表总条数
    var goalsSpace = 0;//子表月份间隔
    //日期计算
    var beginDate = new Date(form["beginDate"].replace(/-/g,"/"));//开始日期
    form.targetYear = beginDate.getFullYear();
    var endDate = new Date(form["beginDate"].replace(/-/g,"/"));//结束日期
    endDate.setFullYear(endDate.getFullYear()+1);
    endDate.setDate(endDate.getDate()-1);
    form.endDate = dateFormat(endDate);
    //计算子表所需条数
    switch(type){
        case "0001"://年
            goalsLen = 1;
            break;
        case "0002"://半年
            goalsLen = 2;
            break;
        case "0003"://季度
            goalsLen = 4;
            break;
        case "0004"://月
            goalsLen = 12;
            break;
        default:
            goalsLen = 0;
    }
    //调整增减子表条数
    if(goals.length!=goalsLen){
        goals = new Array() ;
        for(var i = 0;i<goalsLen;i++){
            var goalExp = new Object();
            goals.push(goalExp);
        }
    }
    if(goals && goals.length>0){
        goalsSpace = 12/goalsLen ;
        //计算子表年份 及 总比率为100
        for (var i = 0;i<goals.length;i++){
            goals[i].assessment=(i+1)+"M";
            if(!goals[i].proportion){
                goals[i].proportion = "0";
            }
            if(i!=(goals.length-1)){
                totalPercent = accSub(totalPercent,parseInt(goals[i].proportion));
            }
            //计算子表开始时间
            var beginDateSub = new Date(form["beginDate"].replace(/-/g,"/"));
            beginDateSub.setMonth(beginDateSub.getMonth()+goalsSpace*i);
            while (dateMonthSub(beginDateSub,beginDate)>goalsSpace*i){
                beginDateSub.setDate(beginDateSub.getDate()-1);
            }
            goals[i].beginDate = dateFormat(beginDateSub);
        }
        goals[goals.length-1].proportion = totalPercent;
        //计算子表各指标数值
        for (var i = 0;i<goals.length;i++){
            if(goalValue){
                goals[i].baseValue=accMul(goals[i].proportion,goalValue)/100;
            }
            if(testGoal){
                goals[i].assValue=accMul(goals[i].proportion,testGoal)/100;
            }
            if(sportGoal){
                goals[i].sprintValue=accMul(goals[i].proportion,sportGoal)/100;
            }
            //计算子表结束时间
            if(i>0){
                var endDateSub = new Date(goals[i].beginDate.replace(/-/g,"/"));
                endDateSub.setDate(endDateSub.getDate()-1);
                goals[i-1].endDate = dateFormat(endDateSub);
            }
        }
        goals[goals.length-1].endDate = form.endDate;
    }
    form.bu_goals = goals;
    return form;
};

//目标变更明细计算公式(全自动)
window['defaultTargetsChangeAuto'] = function(form) {
    var goals = form["bu_goals_change"]==undefined?new Array():form["bu_goals_change"];
    var goalValue = isNullTo0(form["goalValue"])+"";//基础指标
    var testGoal = isNullTo0(form["testGoal"])+"";//考核指标
    var sportGoal = isNullTo0(form["sportGoal"])+"";//冲刺指标
    var type = form["type"];//目标类型 年 半年 季 月
    var totalPercent = 100;
    var goalsLen = 0;//子表总条数
    var goalsSpace = 0;//子表月份间隔
    //日期计算
    var beginDate = new Date(form["beginDate"].replace(/-/g,"/"));//开始日期
    form.targetYear = beginDate.getFullYear();
    var endDate = new Date(form["beginDate"].replace(/-/g,"/"));//结束日期
    endDate.setFullYear(endDate.getFullYear()+1);
    endDate.setDate(endDate.getDate()-1);
    form.endDate = dateFormat(endDate);
    //计算子表所需条数
    switch(type){
        case "0001"://年
            goalsLen = 1;
            break;
        case "0002"://半年
            goalsLen = 2;
            break;
        case "0003"://季度
            goalsLen = 4;
            break;
        case "0004"://月
            goalsLen = 12;
            break;
        default:
            goalsLen = 0;
    }
    //调整增减子表条数
    if(goals.length!=goalsLen){
        goals = new Array() ;
        for(var i = 0;i<goalsLen;i++){
            var goalExp = new Object();
            goals.push(goalExp);
        }
    }
    if(goals && goals.length>0){
        goalsSpace = 12/goalsLen ;
        //计算子表年份 及 总比率为100
        for (var i = 0;i<goals.length;i++){
            goals[i].assessment=(i+1)+"M";
            if(!goals[i].proportion){
                goals[i].proportion = "0";
            }
            if(i!=(goals.length-1)){
                totalPercent = accSub(totalPercent,parseInt(goals[i].proportion));
            }
            //计算子表开始时间
            var beginDateSub = new Date(form["beginDate"].replace(/-/g,"/"));
            beginDateSub.setMonth(beginDateSub.getMonth()+goalsSpace*i);
            while (dateMonthSub(beginDateSub,beginDate)>goalsSpace*i){
                beginDateSub.setDate(beginDateSub.getDate()-1);
            }
            goals[i].beginDate = dateFormat(beginDateSub);
        }
        goals[goals.length-1].proportion = totalPercent;
        //计算子表各指标数值
        for (var i = 0;i<goals.length;i++){
            if(goalValue){
                goals[i].baseValue=accMul(goals[i].proportion,goalValue)/100;
            }
            if(testGoal){
                goals[i].assValue=accMul(goals[i].proportion,testGoal)/100;
            }
            if(sportGoal){
                goals[i].sprintValue=accMul(goals[i].proportion,sportGoal)/100;
            }
            //计算子表结束时间
            if(i>0){
                var endDateSub = new Date(goals[i].beginDate.replace(/-/g,"/"));
                endDateSub.setDate(endDateSub.getDate()-1);
                goals[i-1].endDate = dateFormat(endDateSub);
            }
        }
        goals[goals.length-1].endDate = form.endDate;
    }
    form.bu_goals_change = goals;
    return form;
};

/*清空客户资产切换产品后的模块子表*/
window['clearCustproductdetail'] = function(form) {
    console.info(form);
    form['cs_custproductdetail']=[];
    return form;
}

//计算合同明细的折扣后金额 hh
window['culculateDiscountMoney'] = function(form) {
    var details = form["bu_sale_contract_detail"];
    var index= form["sysFormulaIndex"];
    var detail = details[index];
    if(!detail){return form;}
    var contractMoney = form["contractMoney"];
    var standardMoney = accMul(detail.quantity,detail.standardPrice);
    var bu_sale_contract_before_promote_detail = form["bu_sale_contract_before_promote_detail"];
    var promoteFormula = detail.promoteFormula;
    var promoteValue = isNullTo0(detail.promoteValue);
    var module = detail.partDetail;
    if(promoteFormula && promoteFormula.length>0){
        standardMoney = getNewMoneyByFormulaAndBeforePromoteDetail(
            promoteFormula,promoteValue,standardMoney,module,bu_sale_contract_before_promote_detail);
    }
    detail.standardQuotedMoney = standardMoney;
    contractMoney = accSub(contractMoney,detail.money);
    var money = accMul(detail.discount,standardMoney);
    money = accMul(money,"0.01");
    detail.money =  money;
    contractMoney = accAdd(contractMoney,money);
    detail.unInvoiceMoney = accSub(money,detail.invoiceMoney);
    form["contractMoney"] = contractMoney;
    form["unInvoiceMoney"] = accSub(contractMoney,form["invoiceMoney"]);
    form["leftMoney"] = accSub(contractMoney,form["receivedMoney"]);
    return form;
};

//计算报价明细的折扣报价 hh
window['culculateDiscountMoneyForQuote'] = function(form) {
    var details = form["bu_quoted_price_detail"];
    var index= form["sysFormulaIndex"];
    var detail = details[index];
    if(!detail){return form;}
    var discountQuotePrice = form["discountQuotePrice"];
    var standardQuotePrice = form["standardQuotePrice"];
    var standardMoney = detail.standardQuotedPrice;
    var promoteFormula = detail.promoteFormula;
    var promoteValue = isNullTo0(detail.promoteValue);
    var module = detail.productPart;
    var bu_quote_before_promote_detail = form["bu_quote_before_promote_detail"];
    if(promoteFormula && promoteFormula.length>0){
        var oldStandardMoney = accMul(detail.standardPrice,detail.quantity);
        standardQuotePrice = accSub(standardQuotePrice,standardMoney);
        standardMoney = getNewMoneyByFormulaAndBeforePromoteDetail(
            promoteFormula,promoteValue,oldStandardMoney,module,bu_quote_before_promote_detail);
        detail.standardQuotedPrice = standardMoney;
        standardQuotePrice = accAdd(standardQuotePrice,standardMoney);
        form["standardQuotePrice"] = standardQuotePrice;
    }
    discountQuotePrice = accSub(discountQuotePrice,detail.discountQuotedPrice);
    var money = accMul(detail.discount,standardMoney);
    money = accDiv(money,100);
    detail.discountQuotedPrice =  money;
    discountQuotePrice = accAdd(discountQuotePrice, money);
    form["discountQuotePrice"] = discountQuotePrice;
    var totalDiscount = accDiv(discountQuotePrice,standardQuotePrice);
    totalDiscount = accMul(totalDiscount,100);
    totalDiscount = Math.round(totalDiscount * 100) / 100;
    form["discount"] = totalDiscount;
    return form;
};

function getNewMoneyByFormulaAndBeforePromoteDetail(formula,formulaValue,oldMoney,module,beforePromoteDetail){
    if(!beforePromoteDetail || beforePromoteDetail.length<1) return oldMoney;
    var tempObj = null;
    for(var i=0;i<beforePromoteDetail.length;i++){
        if(module === beforePromoteDetail[i].module){
            tempObj = beforePromoteDetail[i];
            break;
        }
    }
    if(tempObj == null){
        return oldMoney;
    }else{
        if(formula === "0001"){//差价
            return oldMoney-tempObj.basicMoney;
        }else if(formula === "0002"){//百分比
            var newMoney = accMul(oldMoney,formulaValue);
            return accMul(newMoney,"0.01");
        }else if(formula === "0003"){//抵扣
            return accSub(oldMoney,formulaValue);
        }else{
            return oldMoney;
        }
    }
}

//hh 开票时，子表金额大于未开票金额时，将金额改为开票金额
window['restrictInvoiceDetailMoney'] = function(form) {
    var details = form["bu_invoice_detail"];
    var index= form["sysFormulaIndex"];
    var detail = details[index];
    var contractDetailsId = detail.contractDetailsId;
    if(contractDetailsId!=null && contractDetailsId.length>0){
        var unInvoiceMoney = parseFloat(isNullTo0(detail.unInvoiceMoney));
        var money = parseFloat(isNullTo0(detail.money));
        var quantity = parseFloat(isNullTo0(detail.quantity));
        if(unInvoiceMoney<money){
            form["invoiceMoney"] = accAdd(form["invoiceMoney"]-money,unInvoiceMoney);
            detail.price = accDiv(unInvoiceMoney,quantity);
            detail.money = unInvoiceMoney;
        }
    }
    return form;
};

//计算开票税额 hh
window['calculateTaxMoney'] = function(form) {
    var details = form["bu_invoice_detail"];
    var index= form["sysFormulaIndex"];
    var detail = details[index];
    var money = parseFloat(isNullTo0(detail.money));
    var taxRate = parseFloat(isNullTo0(detail.taxRate));
    var taxMoney = accMul(money,accDiv(taxRate,100));
    detail.taxMoney = taxMoney;
    return form;
};
//更改客户时清空开票子表及开票金额 hh
window['clearDetailsForInvoice'] = function(form) {
    form["invoiceMoney"] = 0.0;
    form["bu_invoice_detail"] = [];
    return form;
};

//工作及计划更改项目类型时清空项目名称及项目阶段 hh
window['clearProjectAndStageWhenTypeChange'] = function(form) {
    console.log("1111");
    form["projectId"] = "";
    form["projectStage"] = "";
    return form;
};
//工作及计划更改客户时清空项目名称及项目阶段，清空客户人员，部门及职务，联系方式 hh
window['clearProjectAndStageWhenCustomerChange'] = function(form) {
    form["projectId"] = "";
    form["projectStage"] = "";
    form["customerPerson"] = "";
    form["deptAndJob"] = "";
    form["contactMethod"] = "";
    return form;
};
//报价更换客户时，清空与客户相关数据 hh
window['clearRelatedInfoWhenCustomerChangeForQuote'] = function(form) {
    form["customerPerson"] = "";
    /*form["customerDept"] = "";
    form["productClass"] = "";
    form["standardQuotePrice"] = "0";
    form["discount"] = "0";
    form["discountQuotePrice"] = "0";
    form["bu_quoted_price_detail"] = [];
    form["custproductId"] = "";
    form["bu_quote_before_promote_detail"] = [];*/
    return form;
};
//报价更换报价类型时，清空与升级资产相关信息 hh
window['clearRelatedInfoWhenQuoteTypeChangeForQuote'] = function(form) {
    form["custproductId"] = "";
    form["bu_quote_before_promote_detail"] = [];
    return form;
};
//报价更换升级资产时，清空与升级资产明细 hh
window['clearRelatedInfoWhenCustproductChangeForQuote'] = function(form) {
    form["bu_quote_before_promote_detail"] = [];
    return form;
};
//合同更换客户时，清空与客户相关数据 hh
window['clearRelatedInfoWhenCustomerChangeForContract'] = function(form) {
    form["standardQuotePrice"] = "0";
    form["discount"] = "100";
    form["contractMoney"] = "0";
    form["leftMoney"] = "0";
    form["receivedMoney"] = "0";
    form["invoiceMoney"] = "0";
    form["unInvoiceMoney"] = "0";
    form["bu_sale_contract_detail"] = [];
    form["__cfvb"] = "";
    form["quoteId"] = "";
    form["__cfva"] = "";
    form["opportunityId"] = "";
    form["clueId"] = "";
    form["custproductId"] = "";
    form["productName"] = "";
    //form["bu_sale_contract_before_promote_detail"] = [];
    return form;
};
//合同更换合同类型时，清空与升级资产相关信息 hh
window['clearRelatedInfoWhenContractTypeChangeForContract'] = function(form) {
    form["custproductId"] = "";
    //form["bu_sale_contract_before_promote_detail"] = [];
    return form;
};
//合同更换升级资产时，清空与升级资产明细 hh
window['clearRelatedInfoWhenCustproductChangeForContract'] = function(form) {
    form["bu_sale_contract_before_promote_detail"] = [];
    return form;
};

//客户管理服务记录回写
window['dateilFw'] = function(form) {
    var details = form["cs_custserverinfo"];

    if(details!=null && details.length>0){
       // form["serverType"] =details[details.length-1].serverType;//服务类型
        form["changeType"] = details[details.length-1].changeType;//服务收费类型
        form["changeStatus"] = details[details.length-1].changeStatus;//收费状态
        form["changeMoney"] = details[details.length-1].changeMoney;//收费金额
        form["endTime"] = details[details.length-1].endTime;//服务到期日
        //form["serialNo"] = details[details.length-1].serialNo;//服务序列号
        //form["svsPerson"] = details[details.length-1].personId;//服务人员
       // form["svsDepartment"] = details[details.length-1].departmentId;//服务部门
        //form["serverCompany"] = details[details.length-1].serverCompany;//服务单位
        //form["serverArea"] = details[details.length-1].serverArea;//服务区域
    }
    return form;
};

//客户管理服务变更回写主表
window['custserveForCustomer'] = function(form) {
    var details = form["cs_custserve"];

    if(details!=null && details.length>0){
        form["serverType"] =details[details.length-1].serverType;//服务类型
        form["serialNo"] = details[details.length-1].proSN;//产品序列号
        form["svsPerson"] = details[details.length-1].person;//服务人员
        form["svsDepartment"] = details[details.length-1].department;//服务部门
        form["serverCompany"] = details[details.length-1].companyId;//服务单位
        form["serverArea"] = details[details.length-1].serverArea;//服务区域
        form["useStatus"] = details[details.length-1].useStatus;//产品使用状态
    }
    return form;
};

//客户管理产品信息回写
window['dateilProduct'] = function(form) {
    var details = form["cs_use_productinfo"];
    if(details!=null && details.length>0){
        form["productManufacturer"] =details[details.length-1].productManufacturer;//产品
        form["useStartTime"] =details[details.length-1].useStartTime;//产品使用时间
        form["useDept"] =details[details.length-1].useDept;//产品使用部门
        form["useStatus"] =details[details.length-1].useStatus;//产品使用状态
    }
    return form;
};

//客户资产服务收费记录回写
window['dateilFwsf'] = function(form) {
    var details = form["cs_custproductserver"];
    if(details!=null && details.length>0){
        form["groupserver"] =details[details.length-1].serverType;//服务类型
        form["chargeType"] = details[details.length-1].chargeType;//服务收费类型
        form["chargeStatus"] = details[details.length-1].serverStatus;//收费状态
        //form["servicecharge"] = details[details.length-1].money;//收费金额
        form["serverend"] = details[details.length-1].endTime;//服务到期日
        form["serverno"] = details[details.length-1].serverNo;//服务序列号
    }
    return form;
};

//客户资产服务收费记录回写
window['dateilFwbg'] = function(form) {
    var details = form["cs_custproductchange"];
    if(details!=null && details.length>0){
        form["companyId"] =details[details.length-1].companyId;//服务归属单位
        form["departmentId"] =details[details.length-1].departmentId;//服务归属部门
        form["personId"] =details[details.length-1].personId;//服务归属人员
    }
    return form;
};
//职员工作简历排序
function sortPerson_resume(a,b){
    if(a.changeTime<b.changeTime){
        return -1;
    }else if(a.changeTime>b.changeTime){
        return 1;
    }
}
//职员回写-处理工作简历
window['personResumeWriteBack'] = function(form) {
    console.info(form);
    var person_resume = form["person_resume"];
    console.info(person_resume);
    if(person_resume && person_resume.length > 0){
        person_resume.sort(sortPerson_resume);
        var resume = person_resume[person_resume.length-1];
        console.info(resume);
        form.corpId=resume.corpName;
        form.deptCode=resume.deptName;
        form.deptId=resume.deptName;
        form.serverArea=resume.serverArea;//服务区域
        form.serverProduct=resume.productId;//服务产品
        form.jobId=resume.job;
        form.postId=resume.post;
        form.title=resume.title;
        form.rank=resume.rank;
    }
    return form;
}
//职员教育经历排序
function sortPerson_education(a,b){
    if(a.endDate<b.endDate){
        return -1;
    }else if(a.endDate>b.endDate){
        return 1;
    }
}
//职员回写-教育经历
window['personeducationWriteBack'] = function(form) {
    console.info(form);
    var person_education = form["person_education"];
    console.info(person_education);
   if(person_education && person_education.length > 0){
       person_education.sort(sortPerson_education);
        var education = person_education[person_education.length-1];
        console.info(education);
        form.qualifications=education.qualifications;//学历
    }
    return form;
}
//职员劳动合同排序
function sortPerson_person_contract(a,b){
    if(a.endDate<b.endDate){
        return -1;
    }else if(a.endDate>b.endDate){
        return 1;
    }
}
//职员回写-劳动合同
window['personecontractWriteBack'] = function(form) {
    console.info(form);
    //处理劳动合同
    var person_contract = form["person_contract"];
    console.info(person_contract);
    if(person_contract && person_contract.length > 0){
        person_contract.sort(sortPerson_person_contract);
        var contract = person_contract[person_contract.length-1];
        console.info(contract);
        form.contractEnd=contract.endDate;
        form.contractPeriod=contract.term;
    }
    return form;
}


//服务工单子表配件信息，根据是否收费和折扣计算出收费金额
window['serviceBillsDiscountMoney'] = function(form) {
    var details = form["bu_servicebills"];
    var index = form["sysFormulaIndex"];
    var detail = details[index];
    var standardMoney = detail.standardMoney;//标准金额
    var charge = detail.charge;//是否收费
    var dicount = detail.dicount;//折扣
    if(dicount > 0 && dicount <= 1) {
        if (charge == "1" || charge == 1) {
            detail.chargeMoney = accMul(standardMoney, dicount);
        } else {
            detail.chargeMoney = 0;
        }
    } else {
        detail.chargeMoney = 0;
    }
    return form;
};
//职员档案回写合同期限
window['setPersonTerm'] = function(form) {
    var details = form["person_contract"];
    if(details != null && details.length > 0){
        var begin = details[details.length-1].beginDate;
        var end = details[details.length-1].endDate;
        if(begin != "" && end != "") {
            var beginDate = new Date(begin.replace(/-/g, "/"));
            var endDate = new Date(end.replace(/-/g, "/"));
            var term = getMonthDiff(beginDate, endDate);
            details[details.length - 1].term = term;
        }
    }
    return form;
};
//教育经历变更回写主表
window['educationForCustperson'] = function(form) {
    var details = form["cs_custpersoneducation"];

    if(details!=null && details.length>0){
        form["academicDegree"] =details[details.length-1].degree;//学位
        form["education"] = details[details.length-1].education;//学历
    }
    return form;
};
/**
 * 报价管理升级产品计算折后价
 * @param form
 * @returns {*}
 */
window['quoteDiscountQuotePriceForUpgrade'] = function(form) {
    var detail = form["bu_quote_upgrade_promote_detail"];
    if(detail.length<=0){return form;}
    //upgradeDiscountPrice  升级产品折后价总额
    //upgradeStandard  升级产品标准价总额
    //newStandard 新购产品标准价
    //newDiscountPrice 新购产品折后价
    //var discountQuotePrice = form["discountQuotePrice"];//报价
    //var standardQuotePrice = form["standardQuotePrice"];//标准价
    var upgradeDiscountPrice = 0;//
    var upgradeStandard = 0;//
    var newStandard = form["newStandard"];//
    var newDiscountPrice = form["newDiscountPrice"];//
    for(var i=0;i<detail.length;i++){
        var realMoney = detail[i].quotationStandard;//标准价
        var quotationStandard = detail[i].quotationStandard;//报价标准报价
        var cdiscount = accDiv(detail[i].discount,100);
        var discountPrice = accMul(quotationStandard,cdiscount);
        detail[i].discountPrice=discountPrice;//子表折后价
        upgradeDiscountPrice=accAdd(upgradeDiscountPrice,discountPrice);
        upgradeStandard=accAdd(upgradeStandard,realMoney);
    }
    form["upgradeDiscountPrice"]=upgradeDiscountPrice;
    form["upgradeStandard"]=upgradeStandard;
    form["discountQuotePrice"]=accAdd(newDiscountPrice,upgradeDiscountPrice);
    form["standardQuotePrice"]=accAdd(newStandard,upgradeStandard);
    return form;
}
/**
 * 报价管理新购产品计算折后价
 * @param form
 * @returns {*}
 */
window['quoteDiscountQuotePriceForNew'] = function(form) {
    var detail = form["bu_quoted_price_detail"];
    if(detail.length<=0){return form;}
    //upgradeDiscountPrice  升级产品折后价总额
    //upgradeStandard  升级产品标准价总额
    //newStandard 新购产品标准价
    //newDiscountPrice 新购产品折后价
    var discountQuotePrice = 0;//报价
    var upgradeDiscountPrice=form["upgradeDiscountPrice"];//升级产品标准价总额
    for(var i=0;i<detail.length;i++){
        var standardQuotedPrice = detail[i].standardQuotedPrice;//行标准报价
        var cdiscount = accDiv(detail[i].discount,100);
        var discountQuotedPriceChild = accMul(standardQuotedPrice,cdiscount);
        detail[i].discountQuotedPrice=discountQuotedPriceChild;//子表折后价
        discountQuotePrice=accAdd(discountQuotePrice,discountQuotedPriceChild);
    }
    form["newDiscountPrice"]=discountQuotePrice;
    form["discountQuotePrice"]=accAdd(discountQuotePrice,upgradeDiscountPrice);

    return form;
}
/**
 * 客户资产计算折后价
 * @param form
 * @returns {*}
 */
window['custProductOrderfsum'] = function(form) {
    var discount=form.discount;
    var fsum=form.fsum;
    form["orderfsum"]=accMul(accMul(discount,fsum),0.01);
    return form;
}
/**
 * 销售合同升级产品计算折后价
 *
 * @param form
 * @returns {*}
 */
window['calculateContractMoneyForUpgrade'] = function(form) {
   /* var discount=form.discount;
    var standardQuotePrice=form.standardQuotePrice;//标准金额
    form["contractMoney"]=accMul(accMul(discount,standardQuotePrice),0.01);//合同金额
    //回款金额 receivedMoney
    form["leftMoney"]=accSub(form.contractMoney,form.receivedMoney); //应收款金额
    //开票金额invoiceMoney
    form["unInvoiceMoney"]=accSub(form.contractMoney,form.invoiceMoney); //未开票金额
    return form;*/
    var detail = form["bu_sale_contract_upgrade_promote_detail"];
    if(detail.length<=0){return form;}
    //upgradeDiscountPrice  升级产品折后价总额
    //upgradeStandard  升级产品标准价总额
    //newStandard 新购产品标准价
    //newDiscountPrice 新购产品折后价
    var discountQuotePrice = 0;//报价
    var newStandard=form["newStandard"];//新购产品标准价总额
    var newDiscountPrice=form["newDiscountPrice"];//新购产品折扣价总额
    var standardQuotePrice=0;
    for(var i=0;i<detail.length;i++){
        var realMoney = detail[i].upgradeStandard;//标准价
        var upgradeStandard = detail[i].upgradeStandard;//升级报价
        var cdiscount = accDiv(detail[i].discount,100);
        var discountQuotedPriceChild = accMul(upgradeStandard,cdiscount);
        detail[i].discountPrice=discountQuotedPriceChild;//子表折后价
        discountQuotePrice=accAdd(discountQuotePrice,discountQuotedPriceChild);
        standardQuotePrice=accAdd(standardQuotePrice,realMoney);
    }
    form["standardQuotePrice"]=accAdd(standardQuotePrice,newStandard);
    form["upgradeDiscountPrice"]=discountQuotePrice;
    form["upgradeStandard"]=standardQuotePrice;

    var contractMoney=accAdd(discountQuotePrice,newDiscountPrice);
    form["contractMoney"]=contractMoney;
    //回款金额 receivedMoney
    form["leftMoney"]=accSub(contractMoney,form.receivedMoney); //应收款金额
    //开票金额invoiceMoney
    form["unInvoiceMoney"]=accSub(contractMoney,form.invoiceMoney); //未开票金额
    return form;
}
/**
 * 销售合同新购产品计算折后价
 * @param form
 * @returns {*}
 */
window['calculateContractMoneyForNew'] = function(form) {
    var detail = form["bu_sale_contract_detail"];
    if(detail.length<=0){return form;}
    //upgradeDiscountPrice  升级产品折后价总额
    //upgradeStandard  升级产品标准价总额
    //newStandard 新购产品标准价
    //newDiscountPrice 新购产品折后价
    var discountQuotePrice = 0;//报价
    var upgradeDiscountPrice=form["upgradeDiscountPrice"];//升级产品折扣价总额
    for(var i=0;i<detail.length;i++){
        var standardQuotedMoney = detail[i].standardQuotedMoney;//行标准价
        var cdiscount = accDiv(detail[i].discount,100);
        var discountQuotedPriceChild = accMul(standardQuotedMoney,cdiscount);
        detail[i].money=discountQuotedPriceChild;//子表折后价
        discountQuotePrice=accAdd(discountQuotePrice,discountQuotedPriceChild);
    }
    form["newDiscountPrice"]=discountQuotePrice;
    var contractMoney=accAdd(discountQuotePrice,upgradeDiscountPrice);
    form["contractMoney"]=contractMoney;
    //回款金额 receivedMoney
    form["leftMoney"]=accSub(contractMoney,form.receivedMoney); //应收款金额
    //开票金额invoiceMoney
    form["unInvoiceMoney"]=accSub(contractMoney,form.invoiceMoney); //未开票金额
    return form;
}
/**
 * 商机管理预计成交金额计算子表合同折扣、合同金额
 * @param form
 * @returns {*}
 */
window['OPPOestimateDealMoney'] = function(form) {
    var detail = form["bu_oppo_product"]==undefined?new Array():form["bu_oppo_product"];
    if(detail != null && detail.length > 0) {
        var estimateDealMoney = form.estimateDealMoney;//预计成交金额
        var discountAmountMain = form.discountAmount;//折扣金额
        var discountMain = accDiv(estimateDealMoney, discountAmountMain);
        for (var i = 0; i < detail.length; i++) {
            var buyQuote = detail[i].buyQuote;//购买报价
            var saleDiscount = accMul(detail[i].saleDiscount, discountMain);//合同折扣
            detail[i].contractDiscount = saleDiscount.toFixed(10);//合同折扣
            var cdiscount = accDiv(detail[i].contractDiscount, 100);
            var contractAmount = accMul(buyQuote, cdiscount);//子表合同金额
            detail[i].contractAmount = contractAmount.toFixed(10);//子表合同金额
        }
    }
    return form;
};
window['QuoteDiscountQuotePrice'] = function(form) {
    var detail = form["bu_quote_product"]==undefined?new Array():form["bu_quote_product"];
    if(detail != null && detail.length > 0) {
        var estimateDealMoney = form.discountQuotePrice;//报价金额
        var discountAmountMain = form.discountAmount;//折扣金额
        var discountMain = accDiv(estimateDealMoney, discountAmountMain);
        for (var i = 0; i < detail.length; i++) {
            var buyQuote = detail[i].buyQuote;//购买报价
            var saleDiscount = accMul(detail[i].saleDiscount, discountMain);//合同折扣
            detail[i].contractDiscount = saleDiscount.toFixed(10);//合同折扣
            var cdiscount = accDiv(detail[i].contractDiscount, 100);
            var contractAmount = accMul(buyQuote, cdiscount);//子表合同金额
            detail[i].contractAmount = contractAmount.toFixed(10);//子表合同金额
        }
    }
    return form;
};
window['ContractMoneyPrice'] = function(form) {
    var detail = form["bu_contract_product"]==undefined?new Array():form["bu_contract_product"];
    if(detail != null && detail.length > 0) {
        var estimateDealMoney = form.contractMoney;//合同金额
        var discountAmountMain = form.discountAmount;//折扣金额
        var discountMain = accDiv(estimateDealMoney, discountAmountMain);
        for (var i = 0; i < detail.length; i++) {
            var buyQuote = detail[i].buyQuote;//购买报价
            var saleDiscount = accMul(detail[i].saleDiscount, discountMain);//合同折扣
            detail[i].contractDiscount = saleDiscount.toFixed(10);//合同折扣
            var cdiscount = accDiv(detail[i].contractDiscount, 100);
            var contractAmount = accMul(buyQuote, cdiscount);//子表合同金额
            detail[i].contractAmount = contractAmount.toFixed(10);//子表合同金额
        }
    }
    return form;
};
/**
 * 商机管理计算折后金额
 * @param form
 * @returns {*}
 */
window['OppoDiscountAmount'] = function(form) {
    var detail = form["bu_oppo_product"];
    if(detail.length<=0){return form;}
    var discountAmountMain = 0;//主表折后金额
    for(var i=0;i<detail.length;i++){
        var discountAmount = detail[i].discountAmount;//折后金额
        var saleDiscount = accDiv(detail[i].saleDiscount,100);
        var discountAmount = accMul(detail[i].buyQuote,saleDiscount);
        detail[i].discountAmount=discountAmount;//子表折后金额
        discountAmountMain=accAdd(discountAmountMain,discountAmount);
    }
    form["discountAmount"]=discountAmountMain;
    return form;
};
window['QuoteDiscountAmount'] = function(form) {
    var detail = form["bu_quote_product"];
    if(detail.length<=0){return form;}
    var discountAmountMain = 0;//主表折后金额
    for(var i=0;i<detail.length;i++){
        var discountAmount = detail[i].discountAmount;//折后金额
        var saleDiscount = accDiv(detail[i].saleDiscount,100);
        var discountAmount = accMul(detail[i].buyQuote,saleDiscount);
        detail[i].discountAmount=discountAmount;//子表折后金额
        discountAmountMain=accAdd(discountAmountMain,discountAmount);
    }
    form["discountAmount"]=discountAmountMain;
    return form;
};
window['ContractDiscountAmount'] = function(form) {
    var detail = form["bu_contract_product"];
    if(detail.length<=0){return form;}
    var discountAmountMain = 0;//主表折后金额
    for(var i=0;i<detail.length;i++){
        var discountAmount = detail[i].discountAmount;//折后金额
        var saleDiscount = accDiv(detail[i].saleDiscount,100);
        var discountAmount = accMul(detail[i].buyQuote,saleDiscount);
        detail[i].discountAmount=discountAmount;//子表折后金额
        discountAmountMain=accAdd(discountAmountMain,discountAmount);
    }
    form["discountAmount"]=discountAmountMain;
    return form;
};
/**
 * 入库单修改折扣、单价、折扣金额、数量计算实际金额
 * @param form
 * @returns {*}
 * @constructor
 */
window['calculateInStockForPrice'] = function(form) {
    var discount = form.discount;//折扣
    var standardAmountMain = 0;//	    标准金额
    var purchasePrice = 0;//	    进货金额
    var stocksList = form["sc_instocks"]==undefined?new Array():form["sc_instocks"];
    for(var i=0;i<stocksList.length;i++){
        var inQuantity=stocksList[i].inQuantity;//入库数量
        var standardAmount=stocksList[i].standardAmount;//标准单价
        var realPrice=stocksList[i].realPrice;//单价
        var payAmount=stocksList[i].payAmount;//金额
        realPrice=accMul(accMul(discount,0.01),standardAmount);
        if(stocksList[i].ynAllow=="1"){
            payAmount=accMul(inQuantity-1,realPrice);
            standardAmount=accMul(inQuantity-1,standardAmount);

        }else{
            payAmount=accMul(inQuantity,realPrice);
            standardAmount=accMul(inQuantity,standardAmount);
        }
        standardAmountMain=accAdd(standardAmountMain,standardAmount);
        stocksList[i].realPrice=realPrice;
        stocksList[i].payAmount=payAmount;
    }

    purchasePrice=accMul(accMul(discount,0.01),standardAmountMain);
    form["standardAmount"]=standardAmountMain;
    form["purchasePrice"]=purchasePrice;
    return form;
}
/**
 *入库单修改子表价格
 * @param form
 * @returns {*}
 */
window['calculateInStockForPayAmount'] = function(form) {
    var discount = form.discount;//折扣
    var standardAmountMain = 0;//	    标准金额
    var purchasePrice = 0;//	    进货金额
    var stocksList = form["sc_instocks"]==undefined?new Array():form["sc_instocks"];
    for(var i=0;i<stocksList.length;i++){
        var inQuantity=stocksList[i].inQuantity;//入库数量
        var standardAmount=stocksList[i].standardAmount;//标准单价
        var payAmount=stocksList[i].payAmount;//金额
        purchasePrice=accAdd(payAmount,purchasePrice);
        var realPrice="0";//单价
        if(stocksList[i].ynAllow=="1"){//单价
            realPrice=accDiv(payAmount,inQuantity-1);
            standardAmount=accMul(inQuantity-1,standardAmount);
        }else{
            realPrice=accDiv(payAmount,inQuantity);
           // payAmount=accMul(inQuantity,realPrice);
            standardAmount=accMul(inQuantity,standardAmount);
        }
        standardAmountMain=accAdd(standardAmountMain,standardAmount);
        stocksList[i].realPrice=realPrice;
        //stocksList[i].payAmount=payAmount;
    }

    //purchasePrice=accMul(accMul(discount,0.01),standardAmountMain);
    form["standardAmount"]=standardAmountMain;
    form["purchasePrice"]=purchasePrice;
    discount=accMul(accDiv(purchasePrice,standardAmountMain),"100");
    form["discount"]=discount.toFixed(2);
    return form;
}
/**
 * 其他销售修改单价、折扣、数量计算实际金额
 * @param form
 * @returns {*}
 */
window['calculateBuOthersaleForPrice'] = function(form) {
    var saleAmountMain = 0;//	    销售金额
    var standardAmount = 0;//	    标准金额

    var bu_othersales = form["bu_othersales"]==undefined?new Array():form["bu_othersales"];
    for(var i=0;i<bu_othersales.length;i++){
        var quantity=bu_othersales[i].quantity;//数量
        var price=bu_othersales[i].price;//单价
        var amount=bu_othersales[i].amount;//金额
        var dicount = bu_othersales[i].dicount;//折扣
        var saleAmount=bu_othersales[i].saleAmount;//销售金额
        amount=accMul(quantity,price);//金额
        saleAmount=accMul(accMul(dicount,0.01),amount);
        saleAmountMain=accAdd(saleAmountMain,saleAmount);
        standardAmount = accAdd(standardAmount,amount);
        bu_othersales[i].amount=amount;
        bu_othersales[i].saleAmount=saleAmount;
    }
    form["standardAmount"]=standardAmount;
    form["saleAmount"]=saleAmountMain;//	    销售金额
    form["discount"]=accMul(accDiv(saleAmountMain,standardAmount),100);
    form["receiveAmount"]=accSub(saleAmountMain,form["returnAmount"]);//	    应收款
    form["unInvoiceAmount"]=accSub(saleAmountMain,form["invoiceAmount"]);//	    未开票金额
    return form;
}

/**
 * 出库单修改折扣、单价、折扣金额、数量计算实际金额
 * @param form
 * @returns {*}
 * @constructor
 */
window['calculateOutStockForPrice'] = function(form) {
    var discount = form.discount;//折扣
    var standardAmountMain = 0;//标准金额
    var stocksList = form["sc_outstocks"]==undefined?new Array():form["sc_outstocks"];
    for(var i=0;i<stocksList.length;i++){
        var outQuantity=stocksList[i].outQuantity;//出库数量
        var standardAmount=stocksList[i].standardAmount;//标准单价
        var realPrice=stocksList[i].realPrice;//单价
        var payAmount=stocksList[i].payAmount;//金额
        realPrice=accMul(accMul(discount,0.01),standardAmount);
        if(stocksList[i].ynAllow=="1"){//单价
            payAmount=accMul(outQuantity-1,realPrice);
            standardAmount=accMul(outQuantity-1,standardAmount);
        }else{
            payAmount=accMul(outQuantity,realPrice);
            standardAmount=accMul(outQuantity,standardAmount);
        }
        standardAmountMain=accAdd(standardAmountMain,standardAmount);
        stocksList[i].realPrice=realPrice;
        stocksList[i].payAmount=payAmount;
    }
    var purchasePrice=accMul(accMul(discount,0.01),standardAmountMain);
    form["standardAmount"]=standardAmountMain;
    form["purchasePrice"]=purchasePrice;
    form["salePrice"]=purchasePrice;
    return form;
}
/**
 *出库单修改子表价格
 * @param form
 * @returns {*}
 */
window['calculateOutStockForPayAmount'] = function(form) {
    var discount = form.discount;//折扣
    var standardAmountMain = 0;//	    标准金额
    var purchasePrice = 0;//	    进货金额
    var stocksList = form["sc_outstocks"]==undefined?new Array():form["sc_outstocks"];
    for(var i=0;i<stocksList.length;i++){
        var outQuantity=stocksList[i].outQuantity;//出库数量
        var standardAmount=stocksList[i].standardAmount;//标准单价
        var payAmount=stocksList[i].payAmount;//金额
        purchasePrice=accAdd(payAmount,purchasePrice);
        var realPrice="0";//单价
        if(stocksList[i].ynAllow=="1"){//单价
            realPrice=accDiv(payAmount,outQuantity-1);
            standardAmount=accMul(outQuantity-1,standardAmount);
        }else{
            realPrice=accDiv(payAmount,outQuantity);
            standardAmount=accMul(outQuantity,standardAmount);
        }
        standardAmountMain=accAdd(standardAmountMain,standardAmount);
        stocksList[i].realPrice=realPrice;
    }

    form["standardAmount"]=standardAmountMain;
    form["purchasePrice"]=purchasePrice;
    form["salePrice"]=purchasePrice;
    discount=accMul(accDiv(purchasePrice,standardAmountMain),"100");
    form["discount"]=discount.toFixed(2);
    return form;
}

/**
 * @description 入库单修改进行金额时，主表折扣进行调整，子表单价与金额进行调整
 * @author lc
 * @param form
 * @return
 * @date 2019年4月10日15:49:36
 */
window['calculateInStockForPurchasePrice'] = function(form) {
    var purchasePrice = form.purchasePrice;//进货金额
    var standardAmount = form.standardAmount;//标准金额
    var discount = accMul(accDiv(purchasePrice,standardAmount),100);
    discount = discount.toFixed(4);
    form["discount"] = discount;
    var stocksList = form["sc_instocks"]==undefined?new Array():form["sc_instocks"];
    for(var i=0;i<stocksList.length;i++){
        var inQuantity = stocksList[i].inQuantity;//入库数量
        var standardAmount = stocksList[i].standardAmount;//标准单价
        var realPrice = accMul(accMul(discount,0.01),standardAmount);//单价
        var payAmount = accMul(realPrice,inQuantity);//金额
        stocksList[i].realPrice = realPrice.toFixed(4);
        stocksList[i].payAmount = payAmount.toFixed(4);
    }
    return form;
};
window['getCodeSetCode'] = function(form) {
    var code = form.code;
    form["sortCode"] = code;
    return form;
};
window['setCheckBoxMutex'] = function(form) {
    var personList = form["cs_custperson"]==undefined?new Array():form["cs_custperson"];
    var index = form["sysFormulaIndex"];
    for(var i=0;i<personList.length;i++){
        var keycontact = personList[i].keycontact;
        if(keycontact == 1) {
            if(i != index){
                personList[i].keycontact = '0';
            }
        }
    }
    for(var i=0;i<personList.length;i++){
        var keycontact = personList[i].keycontact;
        if(keycontact == 1) {
            form["custMainContact"] = personList[i].name;
            form["custMainContactTel"] = personList[i].mobile;
        }
    }
    return form;
};
/**
 * 客户资产，服务折扣、成交金额计算标准服务金额
 */
window['getProductServiceCharge'] = function(form) {
    var serverDiscount = form.serverDiscount;
    var fsum = form.fsum;
    form["servicecharge"] = accMul(accMul(serverDiscount,fsum),0.01);
    return form;
};
window['calculateContractMoneyForOtherProduct'] = function(form) {
    var detail = form["bu_sale_contract_otherproduct"];
    if(detail.length<=0){return form;}
    var amounts = 0;
    var disamounts = 0;
    for(var i=0;i<detail.length;i++){
        var amount = detail[i].amount;
        var discount = detail[i].discount;
        var cdiscount = accDiv(discount,100);
        var discountQuotedPriceChild = accMul(amount,cdiscount);
        detail[i].discountAmount = discountQuotedPriceChild;
        amounts = accAdd(amounts,amount);
        disamounts = accAdd(disamounts,discountQuotedPriceChild);
    }
    form["standardQuotePrice"] = amounts;
    form["contractMoney"] = disamounts;
    var dis = accMul(accDiv(disamounts,amounts),100);
    form["discount"] = dis;
    //回款金额 receivedMoney
    form["leftMoney"] = accSub(disamounts,form.receivedMoney); //应收款金额
    //开票金额invoiceMoney
    form["unInvoiceMoney"] = accSub(disamounts,form.invoiceMoney); //未开票金额
    return form;
};
window['costCustomerClean'] = function(form) {
    form['oa_cost_submit_bills']=[];
    form['oa_cost_submit_travel']=[];
    form['oa_cost_submit_other']=[];
    return form;
};
window['setUnClosureMoney'] = function(form) {
    var receivedMoney = form.receivedMoney;
    form["closureMoney"] = '0';
    form["unClosureMoney"] = receivedMoney;
    return form;
};
window['setServerPriceForCustProduct'] = function(form) {
    var standardQuotePrice = form.standardQuotePrice;
    var discount = form.discount;
    var cdiscount = accDiv(discount,100);
    var serverPrice = accMul(standardQuotePrice,cdiscount);
    form["serverPrice"] = serverPrice;
    return form;
};
window['setServerPriceForContract'] = function(form) {
    var standardQuotePrice = form.standardQuotePrice;
    var discounts = form.discounts;
    var cdiscount = accDiv(discounts,100);
    var serverPrice = accMul(standardQuotePrice,cdiscount);
    form["serverPrice"] = serverPrice;
    return form;
};
window['setServerDiscountAmount'] = function(form) {
    var serverPrice = form.serverPrice;
    var serverDiscount = form.serverDiscount;
    var cdiscount = accDiv(serverDiscount,100);
    var serverDiscountAmount = accMul(serverPrice,cdiscount);
    form["serverDiscountAmount"] = serverDiscountAmount;
    return form;
};
window['setLeftMoneyForCostApply'] = function(form) {
    var applyMoney = form.applyMoney;
    var submitMoney = form.submitMoney;
    var leftMoney = accSub(applyMoney,submitMoney);
    form["leftMoney"] = leftMoney;
    return form;
};
/********************************下面写工具***************************************************/
function getMonthDiff(startDate,endDate){
    var startMonth = startDate.getMonth();
    var endMonth = endDate.getMonth();
    var intervalMonth = (endDate.getFullYear()*12+endMonth) - (startDate.getFullYear()*12+startMonth);
    return intervalMonth;
}


//两个时间比较大小，返回小的
function compareDate(checkStartDate, checkEndDate) {
    var arys1 = new Array();
    var arys2 = new Array();
    if(checkStartDate != null && checkEndDate != null) {
        arys1=checkStartDate.split('-');
        var sdate=new Date(arys1[0],parseInt(arys1[1]-1),arys1[2]);
        arys2=checkEndDate.split('-');
        var edate=new Date(arys2[0],parseInt(arys2[1]-1),arys2[2]);
        if(sdate > edate) {
            return edate;
        }  else {
            return sdate;
        }
    }
}
/*************************************************下面是工具代码，业务代码请写在上面*******************************************************/
//说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
//调用：accAdd(arg1,arg2)
//返回值：arg1加上arg2的精确结果
function accAdd(arg1,arg2) {
    arg1 = isNullTo0(arg1);
    arg2 = isNullTo0(arg2);
    var r1, r2, m;
    try {
        r1 = arg1.toString().split(".")[1].length;
    } catch (e) {
        r1 = 0;
    }
    try {
        r2 = arg2.toString().split(".")[1].length;
    } catch (e) {
        r2 = 0;
    }
    m = Math.pow(10, Math.max(r1, r2));
    return (arg1 * m + arg2 * m) / m;
}
//说明：javascript的减法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的减法结果。
//调用：accSub(arg1,arg2)
//返回值：arg1减上arg2的精确结果
function accSub(arg1,arg2) {
    arg1 =  isNullTo0(arg1);
    arg2 = isNullTo0(arg2);
    return accAdd(arg1, -arg2);
}
//说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。
//调用：accMul(arg1,arg2)
//返回值：arg1乘以arg2的精确结果
function accMul(arg1,arg2) {
    arg1 = isNullTo0(arg1);
    arg2 = isNullTo0(arg2);
    var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
    try {
        m += s1.split(".")[1].length;
    } catch (e) {
    }
    try {
        m += s2.split(".")[1].length;
    } catch (e) {
    }
    return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
}

//说明：javascript的除法结果会有误差，在两个浮点数相除的时候会比较明显。这个函数返回较为精确的除法结果。
//调用：accDiv(arg1,arg2)
//返回值：arg1除以arg2的精确结果
function accDiv(arg1,arg2) {
    arg1 = isNullTo0(arg1);
    arg2 = isNullTo0(arg2);
    var t1 = 0, t2 = 0, r1, r2;
    try {
        t1 = arg1.toString().split(".")[1].length;
    } catch (e) {
    }
    try {
        t2 = arg2.toString().split(".")[1].length;
    } catch (e) {
    }
    with (Math) {
        r1 = Number(arg1.toString().replace(".", ""));
        r2 = Number(arg2.toString().replace(".", ""));
        return (r1 / r2) * pow(10, t2 - t1);
    }
}

/**
 * 判断空等于0
 * @param variable1
 * @returns {*}
 */
function  isNullTo0(variable1) {
    if (variable1 == null || variable1 == undefined || variable1 == ''){
        return 0;
    }else{
        return variable1;
    }
}

