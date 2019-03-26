Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
function diaplayTime(data) {

    var str = data;
    //将字符串转换成时间格式
    var timePublish = new Date(str);
    var timeNow = new Date();
    var minute = 1000 * 60;
    var hour = minute * 60;
    var day = hour * 24;
    var month = day * 30;
    var diffValue = timeNow - timePublish;
    var diffMonth = diffValue / month;
    var diffWeek = diffValue / (7 * day);
    var diffDay = diffValue / day;
    var diffHour = diffValue / hour;
    var diffMinute = diffValue / minute;

    if (diffValue < 0) {
        layer.msg("错误时间");
    }
    else if (diffMonth > 3) {
        result = timePublish.getFullYear() + "-";
        result += timePublish.getMonth() + "-";
        result += timePublish.getDate();
        alert(result);
    }
    else if (diffMonth > 1) {
        result = parseInt(diffMonth) + "月前";
    }
    else if (diffWeek > 1) {
        result = parseInt(diffWeek) + "周前";
    }
    else if (diffDay > 1) {
        result = parseInt(diffDay) + "天前";
    }
    else if (diffHour > 1) {
        result = parseInt(diffHour) + "小时前";
    }
    else if (diffMinute > 1) {
        result = parseInt(diffMinute) + "分钟前";
    }
    else {
        result = "刚刚发表";
    }
    return result;
}
function getCountry(countryName) {
    if (countryName == "") {
        return;
    }
    $.ajax({
        type: "POST",
        url: "/bizdict/country/getall",
        'headers': {
            'x-auth-token': $.cookie('token')
        },
        async: false,
        data: {
            size: 99999
        },
        datatype: "json",
        success: function (data) {
            if (data.code == '1003') {
                window.location = "/admin/signin.html";
            } else if (data.code == '200') {
                data = data.data;
                if (data != null) {
                    $("#" + countryName).empty();
                    for (var i = 0; i < data.content.length; i++) {
                        $("#" + countryName).append("<option value='" + data.content[i].id + "'>" + data.content[i].name + "</option>");
                    }
                }
            } else {
                layer.msg(data.message);
            }

        },
        beforeSend: function () {

        },
        complete: function (XMLHttpRequest, textStatus) {

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }
    });

}
//获取国家
function getCountryAll(countryName, provinceName, cityName, countyName) {
    if (countryName == "") {
        return;
    }
    $.ajax({
        type: "POST",
        url: "/bizdict/country/getall",
        'headers': {
            'x-auth-token': $.cookie('token')
        },
        async: false,
        data: {
            size: 99999
        },
        datatype: "json",
        success: function (data) {
            if (data.code == '1003') {
                window.location = "/admin/signin.html";
            } else if (data.code == '200') {
                data = data.data;
                if (data != null) {
                    $("#" + countryName).empty();
                    $("#" + countryName).append("<option value=''>请选择</option>");
                    for (var i = 0; i < data.content.length; i++) {
                        if (i == 0) {
                            getProvinceByCountryId(data.content[i].id, provinceName, cityName, countyName);
                        }
                        $("#" + countryName).append("<option value='" + data.content[i].id + "'>" + data.content[i].name + "</option>");
                    }
                }
            } else {
                layer.msg(data.message);
            }

        },
        beforeSend: function () {

        },
        complete: function (XMLHttpRequest, textStatus) {

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }
    });

}
//获取国家,通过json文件
function getAllCountryByJson(countryName, provinceName, cityName, countyName) {
    if (countryName == "") {
        return;
    }
    $.ajax({
        type: "POST",
        url: "/bizdict/country/getAllCountryByJson",
        'headers': {
            'x-auth-token': $.cookie('token')
        },
        async: false,
        data: {
            size: 99999
        },
        datatype: "json",
        success: function (data) {
            if (data.code == '1003') {
                window.location = "/admin/signin.html";
            } else if (data.code == '200') {
                data = data.data;
                if (data != null) {
                    $("#" + countryName).empty();
                    $("#" + countryName).append("<option value=''>请选择</option>");
                    for (var i = 0; i < data.length; i++) {
                        if (i == 0) {
                            getProvinceByCountryIdThroughJson(data[i].id, provinceName, cityName, countyName);
                        }
                        $("#" + countryName).append("<option value='" + data[i].id + "'>" + data[i].name + "</option>");
                    }
                }
            } else {
                layer.msg(data.message);
            }

        },
        beforeSend: function () {

        },
        complete: function (XMLHttpRequest, textStatus) {

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }
    });

}


//获取所有的国家,【新方法】
function getAllCountryV2(countryName) {
    if (countryName == "") {
        return;
    }
    $.ajax({
        type: "POST",
        url: "/bizdict/country/getAllCountryV2",
        'headers': {
            'x-auth-token': $.cookie('token')
        },
        async: false,
        data: {
            size: 99999
        },
        dataType: "json",
        success: function (data) {
            if (data.code == '1003') {
                window.location = "/admin/signin.html";
            } else if (data.code == '200') {
                data = data.data;
                if (data != null) {
                    $("#" + countryName).empty();
                    $("#" + countryName).append("<option value=''>请选择</option>");
                    // 三层 循环 取国家 节点
                    for(var i = 0; i < data.length; i++){
                        var children1 = data[i].children;
                        for(var j = 0; j < children1.length; j++){
                            var children2 = children1[j].children;
                            for(var k = 0; k < children2.length; k++){
                                $("#" + countryName).append("<option value='" + children2[k].id + "'>" +children2[k].name + "</option>");
                            }
                        }
                    }
                }
            } else {
                layer.msg(data.message);
            }
        },
    });
}

//获取 所有的 省份列表
function getAllShengV2(countryName){
    if (countryName == "") {
        return;
    }
    $.ajax({
        type: "POST",
        url: "/bizdict/country/getAllShengV2",
        'headers': {
            'x-auth-token': $.cookie('token')
        },
        async: false,
        data: {
            size: 99999
        },
        dataType: "json",
        success: function (data) {
            if (data.code == '1003') {
                window.location = "/admin/signin.html";
            } else if (data.code == '200') {
                data = data.data;
                if (data != null) {
                    // 三层 循环 取国家 节点
                    for(var i = 0; i < data.length; i++){
                        // 取第一层 即可
                        $("#" + countryName).append("<option value='" + data[i].id + "'>" +data[i].name + "</option>");
                    }
                }
            } else {
                layer.msg(data.message);
            }
        }
    });
}

// 获取广告组的初始化信息
function initAdGroup(name){
    if (name == "") {
        return;
    }
    $.ajax({
        type: "GET",
        url: "/v1/advertGroup/listAll",
        'headers': {
            'x-auth-token': $.cookie('token')
        },
        dataType: "json",
        success: function (data) {
            if (data.code == '1003') {
                window.location = "/admin/signin.html";
            } else if (data.code == '200') {
                data = data.data;
                if (data != null) {
                    $("#" + name).append("<option value='0'>请选择</option>");
                    for(var i = 0; i < data.length; i++){
                        $("#" + name).append("<option value='" + data[i].id + "'>" + data[i].desc + "</option>");
                    }
                }
            } else {
                layer.msg(data.message);
            }
        }
    });
}

//获取所有的国家,【新方法】
function getAllCountryV3(countryName) {
    if (countryName == "") {
        return;
    }
    $.ajax({
        type: "POST",
        url: "/bizdict/country/getAllCountryV2",
        'headers': {
            'x-auth-token': $.cookie('token')
        },
        async: false,
        data: {
            size: 99999
        },
        dataType: "json",
        success: function (data) {
            if (data.code == '1003') {
                window.location = "/admin/signin.html";
            } else if (data.code == '200') {
                data = data.data;
                if (data != null) {
                    // 三层 循环 取国家 节点
                    for(var i = 0; i < data.length; i++){
                        var children1 = data[i].children;
                        for(var j = 0; j < children1.length; j++){
                            var children2 = children1[j].children;
                            for(var k = 0; k < children2.length; k++){
                                $("#" + countryName).append("<option value='" + children2[k].id + "'>" +children2[k].name + "</option>");
                            }
                        }
                    }
                }
            } else {
                layer.msg(data.message);
            }
        },
    });
}

//初始化省份
function getProvinceByCountryId(countryId, provinceName, cityName, countyName) {
    if (provinceName == "") {
        return;
    }
    $.ajax({
        type: "POST",
        url: "/bizdict/province/getAllByCountryId/" + countryId,
        'headers': {
            'x-auth-token': $.cookie('token')
        },
        async: false,
        data: {
            size: 99999
        },
        datatype: "json",
        success: function (data) {
            if (data.code == '1003') {
                window.location = "/admin/signin.html";
            } else if (data.code == '200') {
                data = data.data;
                if (data != null) {
                    $("#" + provinceName).empty();
                    $("#" + provinceName).append("<option value=''>请选择省份</option>");
                    $("#" + cityName).empty();
                    $("#" + cityName).append("<option value=''>请选择城市</option>");
                    $("#" + countyName).empty();
                    $("#" + countyName).append("<option value=''>请选择区</option>");
                    for (var i = 0; i < data.content.length; i++) {
                        if (i == 0) {
                            getCityByProvinceId(data.content[i].id, cityName, countyName);
                        }
                        $("#" + provinceName).append("<option value='" + data.content[i].id + "'>" + data.content[i].name + "</option>");
                    }
                }
            } else {
                layer.msg(data.message);
            }

        },
        beforeSend: function () {

        },
        complete: function (XMLHttpRequest, textStatus) {

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }
    });
}
//初始化省份,通过json文件
function getProvinceByCountryIdThroughJson(countryId, provinceName, cityName, countyName) {
    if (provinceName == "") {
        return;
    }
    $.ajax({
        type: "POST",
        url: "/bizdict/province/getProvinceByCountryIdThroughJson/" + countryId,
        'headers': {
            'x-auth-token': $.cookie('token')
        },
        async: false,
        data: {
            size: 99999
        },
        datatype: "json",
        success: function (data) {
            if (data.code == '1003') {
                window.location = "/admin/signin.html";
            } else if (data.code == '200') {
                data = data.data;
                if (data != null) {
                    $("#" + provinceName).empty();
                    $("#" + provinceName).append("<option value=''>请选择省份</option>");
                    $("#" + cityName).empty();
                    $("#" + cityName).append("<option value=''>请选择城市</option>");
                    $("#" + countyName).empty();
                    $("#" + countyName).append("<option value=''>请选择区</option>");
                    for (var i = 0; i < data.length; i++) {
                        if (i == 0) {
                            getCityByProvinceId(data[i].id, cityName, countyName);
                        }
                        $("#" + provinceName).append("<option value='" + data[i].id + "'>" + data[i].name + "</option>");
                    }
                }
            } else {
                layer.msg(data.message);
            }

        },
        beforeSend: function () {

        },
        complete: function (XMLHttpRequest, textStatus) {

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }
    });
}
//初始化城市
function getCityByProvinceId(provinceId, cityName, countyName) {
    if (cityName == "") {
        return;
    }
    $.ajax({
        type: "POST",
        url: "/bizdict/city/getAllByProvinceId/" + provinceId,
        'headers': {
            'x-auth-token': $.cookie('token')
        },
        async: false,
        data: {
            size: 99999
        },
        datatype: "json",
        success: function (data) {
            if (data.code == '1003') {
                window.location = "/admin/signin.html";
            } else if (data.code == '200') {
                data = data.data;
                if (data != null) {
                    $("#" + cityName).empty();
                    $("#" + cityName).append("<option value=''>请选择城市</option>");
                    $("#" + countyName).empty();
                    $("#" + countyName).append("<option value=''>请选择区</option>");
                    for (var i = 0; i < data.content.length; i++) {
                        if (i == 0) {
                            getCountyByCityId(data.content[i].id, countyName)
                        }
                        $("#" + cityName).append("<option value='" + data.content[i].id + "'>" + data.content[i].name + "</option>");
                    }
                }
            } else {
                layer.msg(data.message);
            }

        },
        beforeSend: function () {

        },
        complete: function (XMLHttpRequest, textStatus) {

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }
    });
}
//初始化城市,通过json文件
function getCityByProvinceIdThroughJson(provinceId, cityName, countyName) {
    if (cityName == "") {
        return;
    }
    $.ajax({
        type: "POST",
        url: "/bizdict/city/getCityByProvinceIdThroughJson/" + provinceId,
        'headers': {
            'x-auth-token': $.cookie('token')
        },
        async: false,
        data: {
            size: 99999
        },
        datatype: "json",
        success: function (data) {
            if (data.code == '1003') {
                window.location = "/admin/signin.html";
            } else if (data.code == '200') {
                data = data.data;
                if (data != null) {
                    $("#" + cityName).empty();
                    $("#" + cityName).append("<option value=''>请选择城市</option>");
                    $("#" + countyName).empty();
                    $("#" + countyName).append("<option value=''>请选择区</option>");
                    for (var i = 0; i < data.length; i++) {
                        if (i == 0) {
                            getCountyByCityId(data[i].id, countyName)
                        }
                        $("#" + cityName).append("<option value='" + data[i].id + "'>" + data[i].name + "</option>");
                    }
                }
            } else {
                layer.msg(data.message);
            }

        },
        beforeSend: function () {

        },
        complete: function (XMLHttpRequest, textStatus) {

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }
    });
}
//初始化区
function getCountyByCityId(cityId, countyName) {
    if (countyName == "") {
        return;
    }
    $.ajax({
        type: "POST",
        url: "/bizdict/county/getAllByCityId/" + cityId,
        'headers': {
            'x-auth-token': $.cookie('token')
        },
        async: false,
        data: {
            size: 99999
        },
        datatype: "json",
        success: function (data) {
            if (data.code == '1003') {
                window.location = "/admin/signin.html";
            } else if (data.code == '200') {
                data = data.data;
                if (data != null) {
                    $("#" + countyName).empty();
                    $("#" + countyName).append("<option value=''>请选择区</option>");
                    for (var i = 0; i < data.content.length; i++) {
                        $("#" + countyName).append("<option value='" + data.content[i].id + "'>" + data.content[i].name + "</option>");
                    }
                }
            } else {
                layer.msg(data.message);
            }

        },
        beforeSend: function () {

        },
        complete: function (XMLHttpRequest, textStatus) {

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }
    });
}
//初始化区,通过json文件
function getCountyByCityIdThroughJson(cityId, countyName) {
    if (countyName == "") {
        return;
    }
    $.ajax({
        type: "POST",
        url: "/bizdict/county/getCountyByCityIdThroughJson/" + cityId,
        'headers': {
            'x-auth-token': $.cookie('token')
        },
        async: false,
        data: {
            size: 99999
        },
        datatype: "json",
        success: function (data) {
            if (data.code == '1003') {
                window.location = "/admin/signin.html";
            } else if (data.code == '200') {
                data = data.data;
                if (data != null) {
                    $("#" + countyName).empty();
                    $("#" + countyName).append("<option value=''>请选择区</option>");
                    for (var i = 0; i < data.length; i++) {
                        $("#" + countyName).append("<option value='" + data[i].id + "'>" + data[i].name + "</option>");
                    }
                }
            } else {
                layer.msg(data.message);
            }

        },
        beforeSend: function () {

        },
        complete: function (XMLHttpRequest, textStatus) {

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }
    });
}
//获取所有消息群组
function getAllGroup(id){
    $.ajax({
        type: "POST",
        url: "/blackList/getAll",
        'headers': {
            'x-auth-token': $.cookie('token')
        },
        async:  true ,
        dataType: "json",
        success: function (data) {
            data = data.data;
            if (data != null) {
                $("#" + id).empty();
                $("#" + id).append("<option selected disabled>请选择</option>");
                for (var i = 0; i < data.length; i++) {
                    $("#" + id).append("<option value='" + data[i].id + "'>" + data[i].name + "</option>");
                }
            }
        },
    });
}

// 获取订单的所有说明
function getAllDetailsForOrderPay(id) {
    $.ajax({
        type: "GET",
        url: "/pay-order/getDetailsForOrderPay",
        'headers': {
            'x-auth-token': $.cookie('token')
        },
        async: true,
        dataType: "json",
        success: function (data) {
            data = data.data;
            if (data != null) {
                $("#" + id).empty();
                $("#" + id).append("<option selected disabled>请选择</option>");
                for (var i = 0; i < data.length; i++) {
                    $("#" + id).append("<option value='" + data[i] + "'>" + data[i] + "</option>");
                }
            }
        },
    });
}


//获取全部技能
function getAllSkill(id, async) {
    $.ajax({
        type: "POST",
        url: "/bizdict/skill/getall",
        'headers': {
            'x-auth-token': $.cookie('token')
        },
        async: async == null ? true : async,
        data: {
            size: 99999
        },
        datatype: "json",
        success: function (data) {
            if (data.code == '1003') {
                window.location = "/admin/signin.html";
            } else if (data.code == '200') {
                data = data.data;
                if (data != null) {
                    $("#" + id).empty();
                    for (var i = 0; i < data.content.length; i++) {
                        $("#" + id).append('<div style="width: auto;float: left;overflow: auto;padding-right: 10px;"><div style="float: left;margin-top: 6px"><input data="' + data.content[i].name + '" type="checkbox" value="' + data.content[i].id + '" id="' + data.content[i].id + '"></div><label style="margin-top: 8px;margin-left: 3px">' + data.content[i].name + '</label></div>');
                    }
                }
            } else {
                layer.msg(data.message);
            }

        },
        beforeSend: function () {

        },
        complete: function (XMLHttpRequest, textStatus) {

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }
    });
}


function getSkill(id, async, pId, skillTypeId) {
    $.ajax({
        type: "POST",
        url: "/bizdict/skill/getAllBySkill",
        'headers': {
            'x-auth-token': $.cookie('token')
        },
        async: async == null ? true : async,
        data: {
            size: 99999,
            skillId: pId
        },
        async: false,
        datatype: "json",
        success: function (data) {
            if (data.code == '1003') {
                window.location = "/admin/signin.html";
            } else if (data.code == '200') {
                data = data.data;
                if (data != null) {
                    $("#" + id).empty();
                    for (var i = 0; i < data.content.length; i++) {
                        if (i == 0 && skillTypeId != "") {
                            getSkill(skillTypeId, async, data.content[i].id);
                        }
                        $("#" + id).append("<option title='" + data.content[i].name + "' value='" + data.content[i].id + "'>" + data.content[i].name + "</option>");
                    }
                }
            } else {
                layer.msg(data.message);
            }

        },
        beforeSend: function () {

        },
        complete: function (XMLHttpRequest, textStatus) {

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }
    });
}

//新增skill
function addSkill(id) {
    layer.prompt(function (val) {
        var skill = {};
        skill.name = val;
        $.ajax({
            type: "POST",
            url: "/bizdict/skill/create",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(skill),
            datatype: "json",
            'headers': {
                'x-auth-token': $.cookie('token')
            },
            success: function (data) {
                if (data.code == '1003') {
                    window.location = "/admin/signin.html";
                } else if (data.code == '200') {
                    data = data.data;
                    if (data != null) {
                        layer.msg("添加成功");
                        $("#" + id).append('<div style="width: auto;float: left;overflow: auto;padding-right: 10px;"><div style="float: left;margin-top: 6px"><input data="' + data.name + '" type="checkbox" value="' + data.id + '" id="' + data.id + '"></div><label style="margin-top: 8px;margin-left: 3px">' + data.name + '</label></div>');
                    }
                } else {
                    layer.msg(data.message);
                }

            },
            beforeSend: function () {

            },
            complete: function (XMLHttpRequest, textStatus) {

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("添加失败");
            }
        });
    });
}

//获取全部资质机构
function getAllAgent(id, async) {
    $.ajax({
        type: "POST",
        url: "/agent/getAgentListVo",
        'headers': {
            'x-auth-token': $.cookie('token')
        },
        data: {
            size: 99999
        },
        async: async == null ? true : async,
        datatype: "json",
        success: function (data) {
            if (data.code == '1003') {
                window.location = "/admin/signin.html";
            } else if (data.code == '200') {
                data = data.data;
                if (data != null) {
                    $("#" + id).empty();
                    $("#" + id).append("<option value=''>请选择</option>");
                    for (var i = 0; i < data.content.length; i++) {
                        $("#" + id).append("<option value='" + data.content[i].id + "'>" + data.content[i].chineseName + "</option>");
                    }

                }
            } else {
                layer.msg(data.message);
            }

        },
        beforeSend: function () {

        },
        complete: function (XMLHttpRequest, textStatus) {

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }
    });
}


//模块
function getAllFunction(id, async) {
    $.ajax({
        type: "POST",
        url: "/function/getall",
        'headers': {
            'x-auth-token': $.cookie('token')
        },
        data: {
            size: 99999
        },
        async: async == null ? true : async,
        datatype: "json",
        success: function (data) {
            if (data.code == '1003') {
                window.location = "/admin/signin.html";
            } else if (data.code == '200') {
                data = data.data;
                if (data != null) {
                    $("#" + id).empty();
                    $("#" + id).append("<option value=''>请选择</option>");
                    for (var i = 0; i < data.content.length; i++) {
                        $("#" + id).append("<option value='" + data.content[i].id + "'>" + data.content[i].name + "</option>");
                    }

                }
            } else {
                layer.msg(data.message);
            }

        },
        beforeSend: function () {

        },
        complete: function (XMLHttpRequest, textStatus) {

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }
    });
}
//模块



//将form转为AJAX提交
function ajaxSubmit(frm, fn) {
    var dataPara = getFormJson(frm);
    $.ajax({
        url: frm.action,
        type: frm.method,
        data: dataPara,
        success: fn
    });
}

//将form中的值转换为键值对。
function getFormJson(frm) {
    var o = {};
    var a = $(frm).serializeArray();
    $.each(a, function () {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });

    return o;
}



//初始化枚举字段
function getEnum(type, id, async) {
    $.ajax({
        type: "POST",
        url: "/enumdict/getDict",
        'headers': {
            'x-auth-token': $.cookie('token')
        },
        datatype: "json",
        async: async == null ? true : async,
        data: {
            dict: type,
            size: 99999
        },
        success: function (data) {
            if (data.code == '1003') {
                window.location = "/admin/signin.html";
            } else if (data.code == '200') {
                data = data.data;
                if (data != null) {
                    if (id == null) {
                        id = type;
                    }
                    $("#" + id).empty();
                    $("#" + id).append("<option selected disabled>请选择</option>");
                    for (var i = 0; i < data.length; i++) {
                        $("#" + id).append("<option value='" + data[i].k + "'>" + data[i].v + "</option>");
                    }
                }
            } else {
                layer.msg(data.message);
            }

        },
        beforeSend: function () {
        },
        complete: function (XMLHttpRequest, textStatus) {
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}

//查询所有客服
function getAllStaff(id) {
    $.ajax({
        type: "POST",
        url: "/staff/getall",
        'headers': {
            'x-auth-token': $.cookie('token')
        },
        async: false,
        datatype: "json",
        data: {
            size: 99999
        },
        success: function (data) {
            if (data.code == '1003') {
                window.location = "/admin/signin.html";
            } else if (data.code == '200') {
                data = data.data;
                if (data != null) {
                    $("#" + id).empty();
                    $("#" + id).append("<option value=''>无</option>");
                    for (var i = 0; i < data.content.length; i++) {
                        $("#" + id).append("<option value='" + data.content[i].id + "'>" + data.content[i].name + "</option>");
                    }
                }
            } else {
                layer.msg(data.message);
            }

        },
        beforeSend: function () {

        },
        complete: function (XMLHttpRequest, textStatus) {

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }
    });
}

//查询所有客服 不带权限和菜单等信息 只返回id、name等基本信息
function getAllStaffV2(id) {
    $.ajax({
        type: "POST",
        url: "/staffV2/getAll",
        'headers': {
            'x-auth-token': $.cookie('token')
        },
        async: false,
        datatype: "json",
        data: {
            size: 99999
        },
        success: function (data) {
            if (data.code == '1003') {
                window.location = "/admin/signin.html";
            } else if (data.code == '200') {
                data = data.data;
                if (data != null) {
                    $("#" + id).empty();
                    $("#" + id).append("<option value=''>无</option>");
                    for (var i = 0; i < data.content.length; i++) {
                        $("#" + id).append("<option value='" + data.content[i].id + "'>" + data.content[i].name + "</option>");
                    }
                }
            } else {
                layer.msg(data.message);
            }

        },
        beforeSend: function () {

        },
        complete: function (XMLHttpRequest, textStatus) {

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }
    });
}
//查询所有活动渠道
function getAllChannel(id) {
    $.ajax({
        type: "POST",
        url: "/bizdict/channel/getAll",
        'headers': {
            'x-auth-token': $.cookie('token')
        },
        async: false,
        datatype: "json",
        data: {
            size: 99999
        },
        success: function (data) {
            if (data.code == '1003') {
                window.location = "/admin/signin.html";
            } else if (data.code == '200') {
                data = data.data;
                if (data != null) {
                    $("#" + id).empty();
                    $("#" + id).append("<option value=''>不限</option>");
                    for (var i = 0; i < data.content.length; i++) {
                        $("#" + id).append("<option value='" + data.content[i].id + "'>" + data.content[i].name + "</option>");
                    }
                }
            } else {
                layer.msg(data.message);
            }

        },
        beforeSend: function () {

        },
        complete: function (XMLHttpRequest, textStatus) {

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }
    });
}


//初始化工作类别
function getJobCatagory(id, typeId) {
    $.ajax({
        type: "POST",
        url: "/bizdict/jobtype/getAllByJobTypeId",
        'headers': {
            'x-auth-token': $.cookie('token')
        },
        data: {
            size: 99999
        },
        async: false,
        datatype: "json",
        success: function (data) {
            if (data.code == '1003') {
                window.location = "/admin/signin.html";
            } else if (data.code == '200') {
                data = data.data;
                if (data != null) {
                    $("#" + id).empty();
                    $("#" + id).append("<option value=''>请选择</option>");
                    for (var i = 0; i < data.content.length; i++) {
                        if (i == 0) {
                            getJobType(data.content[i].id, typeId);
                        }
                        $("#" + id).append("<option value='" + data.content[i].id + "'>" + data.content[i].name + "</option>");
                    }
                }
            } else {
                layer.msg(data.message);
            }

        },
        beforeSend: function () {

        },
        complete: function (XMLHttpRequest, textStatus) {

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }
    });
}
//初始化工作类别二
function getJobType(jobTypeId, id) {
    $.ajax({
        type: "POST",
        url: "/bizdict/jobtype/getAllByJobTypeId/",
        'headers': {
            'x-auth-token': $.cookie('token')
        },
        data: {
            size: 99999,
            jobTypeId: jobTypeId
        },
        async: false,
        datatype: "json",
        success: function (data) {
            if (data.code == '1003') {
                window.location = "/admin/signin.html";
            } else if (data.code == '200') {
                data = data.data;
                if (data != null) {
                    $("#" + id).empty();
                    $("#" + id).append("<option value=''>请选择</option>");
                    for (var i = 0; i < data.content.length; i++) {
                        $("#" + id).append("<option value='" + data.content[i].id + "'>" + data.content[i].name + "</option>");
                    }
                }
            } else {
                layer.msg(data.message);
            }

        },
        beforeSend: function () {

        },
        complete: function (XMLHttpRequest, textStatus) {

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }
    });
}


//获取分类推荐
function getAllSuggestType(id, async) {
    $.ajax({
        type: "POST",
        async: false,
        url: "/suggestType/getAll",
        'headers': {
            'x-auth-token': $.cookie('token')
        },
        data: {
            size: 9999,
        },
        async: async == null ? true : async,
        datatype: "json",
        success: function (data) {
            if (data.code == '1003') {
                window.location = "/admin/signin.html";
            } else if (data.code == '200') {
                data = data.data;
                if (data != null) {
                    $("#" + id).empty();
                    $("#" + id).append('<option value="">请选择</option>');
                    for (var i = 0; i < data.content.length; i++) {
                        $("#" + id).append('<option value="' + data.content[i].id + '">' + data.content[i].name + '</option>');
                    }

                }
            } else {
                layer.msg(data.message);
            }

        },
        beforeSend: function () {

        },
        complete: function (XMLHttpRequest, textStatus) {

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }
    });
}


//上传文件
function uploadImage(id, showDIv, imgId) {
    //判断是否有选择上传文件
    var imgPath = $("#" + id).val();
    if (imgPath == "") {
        layer.msg("请选择上传图片！");
        return;
    }
    //判断上传文件的后缀名
    var strExtension = imgPath.substr(imgPath.lastIndexOf('.') + 1);
    if (strExtension != 'JPG' && strExtension != 'jpg' && strExtension != 'gif' && strExtension != 'GIF' && strExtension != 'png' && strExtension != 'PNG' && strExtension != 'bmp') {
        layer.msg("请选择图片文件");
        return;
    }
    $.ajaxFileUpload({
        url: "/file/singleSave",
        secureuri: false, //一般设置为false
        fileElementId: 'file',
        type: 'post',
        dataType: 'json', //返回值类型 一般设置为json
        data: {
            fileType: 'jobPic',
        },
        success: function (data, status) {
            if (data.code == '1003') {
                window.location = "/admin/signin.html";
            }
            if (data != null) {
                $("#" + showDIv).show();
                $("#" + imgId).attr("src", data.uri);
            }
        },
        error: function (data, status, e) {
            layer.msg("上传失败");
        }
    });

}

//项目优势
function getProjectAdvanced(type, id, async) {
    $.ajax({
        type: "POST",
        url: "/enumdict/getDict",
        'headers': {
            'x-auth-token': $.cookie('token')
        },
        async: async == null ? true : async,
        data: {
            dict: type,
            size: 99999,
        },
        async: false,
        datatype: "json",
        success: function (data) {
            if (data.code == '1003') {
                window.location = "/admin/signin.html";
            } else if (data.code == '200') {
                data = data.data;
                if (data != null) {
                    $("#" + id).empty();
                    for (var i = 0; i < data.length; i++) {
                        $("#" + id).append('<div class="pull-left m-r"><input value = ' + data[i].c + ' data = ' + data[i].k + ' type="checkbox">' + data[i].v + '</div>');
                    }
                }
            } else {
                layer.msg(data.message);
            }

        },
        beforeSend: function () {

        },
        complete: function (XMLHttpRequest, textStatus) {

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }
    });
}


//
function getIndustry(pcode, id, async) {
    $.ajax({
        type: "POST",
        url: "/bizdict/industry/agetAllByPcode",
        'headers': {
            'x-auth-token': $.cookie('token')
        },
        async: async == null ? true : async,
        data: {
            pcode: pcode,
            size: 99999,
        },
        async: false,
        datatype: "json",
        success: function (data) {
            if (data.code == '1003') {
                window.location = "/admin/signin.html";
            } else if (data.code == '200') {
                data = data.data;
                if (data != null) {
                    $("#" + id).empty();
                    for (var i = 0; i < data.content.length; i++) {
                        if (i == 0) {
                            getIndustry(data.content[i].code, "industryChildren", false);
                        }
                        $("#" + id).append('<option value="' + data.content[i].id + '" code="' + data.content[i].code + '">' + data.content[i].name + '</option>');
                    }
                }
            } else {
                layer.msg(data.message);
            }

        },
        beforeSend: function () {

        },
        complete: function (XMLHttpRequest, textStatus) {

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }
    });
}


function checkedStr(str) {
    str = str == 'undefined' ? "" : str;
    str = str == null ? "" : str;
    return str;
    //return str || ''
}
//判断是否是json
var isJson = function (obj) {
    var isjson = typeof(obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object object]" && !obj.length
    return isjson;
}
//判断是否是json数组
var isJsonArr = function (obj) {
    var isJsonArr = Object.prototype.toString.call(obj).toLowerCase() == "[object array]"
    return isJsonArr;
}
function isEmptyObject(e) {
    var t;
    for (t in e)
        return !1;
    return !0
}
//刷新当前页面
function refresh() {
    window.location.reload();
}

var getAgentByLocal = function () {
    if (window.localStorage) {
        return localStorage.getItem("agent");
    } else {
        return $.cookie('agent');
    }
}
var getAuthoritiesByLocal = function () {
    if (window.localStorage) {
        return localStorage.getItem("authorities");
    } else {
        return $.cookie('authorities');
    }
}

function parseDom(arg) {
    var objE = document.createElement("div");
    objE.innerHTML = arg;
    return objE.childNodes;

};
function getImageWidth(url, callback) {
    var img = new Image();
    img.src = url;

    // 如果图片被缓存，则直接返回缓存数据
    if (img.complete) {
        callback(img.width, img.height);
    } else {
        // 完全加载完毕的事件
        img.onload = function () {
            callback(img.width, img.height);
        }
    }

}

/*发送短信验证码*/

var countdown = 60;
function settime(obj) {
    if (countdown == 0) {
        obj.removeAttribute("disabled");
        obj.value = "免费获取验证码";
        countdown = 60;
        return;
    } else {
        obj.setAttribute("disabled", true);
        obj.value = "重新发送(" + countdown + ")";
        countdown--;
    }
    setTimeout(function () {
        settime(obj);
    }, 1000)
}

function sendSms(obj) {
    var pattern = /^1[1-9]\d{9}$/;
    var mobile = $("#username").val();
    if (mobile == null || !pattern.test(mobile)) {
        layer.msg('请输入正确的电话号码');
        return false;
    }
    settime(obj);
    $.ajax({
        type: "POST",
        url: "/sms/send",
        data: {
            mobileNo: encodeURIComponent(mobile),
            picVericode: $("#picVericode").val()
        },
        datatype: "json",
        success: function (data) {
            if (data.code == "200") {
                layer.msg('短信发送成功,请注意查收');
            } else if (data.code == "1013") {
                layer.msg(data.message);
                countdown = 0;
            } else {
                layer.msg(data.message);
            }

        },
        beforeSend: function () {

        },
        complete: function (XMLHttpRequest, textStatus) {

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            layer.msg('短信发送失败');
        }
    });
}

/*发送短信验证码*/


String.prototype.NoSpace = function () {
    return this.replace(/\s+/g, "");
}
