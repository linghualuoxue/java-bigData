$(function () {
    initTable();
});

function initTable() {

    $("#regixTable").jqGrid({
        url:basePath+"/regix/getPage.do",
        mtype:"post",
        styleUI: 'Bootstrap',
        datatype:"json",
        altRows : true,
        altclass : "jqgrid_alt_row",
        rownumbers : true,
        height:220,
        autowidth:true,
        viewsortcols : [ true, "vertical", true ],
        colModel:[
            {
                label:"类型",
                name:"type",
                width: 160,
                sortable:false/*,
                formatter:function (cellvalue,options,rowObject) {
                    alert(rowObject);
                    return rowObject.name;
                }*/
            }, {
                label:"规则",
                name:"rule",
                width:160,
                sortable:false/*,
                formatter:function (cellvalue,options,rowObject) {
                    return rowObject.age;
                }*/
            }
        ],
        rowNum:10,
        rowList:[10,20,30],
        pager:"#pageTable",
        viewrecords : true,
        jsonReader : {
            root: "rows",   // json中代表实际模型数据的入口
            page: "page",   // json中代表当前页码的数据
            total: "total", // json中代表页码总数的数据
            records: "records", // json中代表数据行总数的数据
            repeatitems: false
        },
        gridComplete : function() {
        }
    }).navGrid("#pageTable", { search: false, edit: true, add: true, del: true,
        addfunc:openDialog4Adding,
        editfunc:openDialog4Updating,
        delfunc:openDialog4Deleting
     });
    //配置对话框
    $("#consoleDlg").dialog({
        autoOpen:false,
        modal:true      //设置对话框为模态对话框
    });
}

var openDialog4Adding = function() {
    var consoleDlg = $("#consoleDlg");
//    var dialogButtonPanel = consoleDlg.siblings(".ui-dialog-buttonpane");
    consoleDlg.find("input").removeAttr("disabled").val("");
//    dialogButtonPanel.find("button:not(:contains('取消'))").hide();
//    dialogButtonPanel.find("button:contains('新增')").show();
    consoleDlg.dialog({
        title:"新增",
        resizable:false,
        width:450,
        buttons:{
            "取消":function(){
                $("#consoleDlg").dialog("close");
            },
            "新增":addItem
        }
    });
    consoleDlg.dialog("open");
};

var openDialog4Updating = function() {
    var consoleDlg = $("#consoleDlg");
    //   var dialogButtonPanel = consoleDlg.siblings(".ui-dialog-buttonpane");
    consoleDlg.find("input").removeAttr("disabled");
    /*    dialogButtonPanel.find("button:not(:contains('取消'))").hide();
     dialogButtonPanel.find("button:contains('修改')").show();    */

    consoleDlg.dialog({
        title:"修改",
        resizable:false,
        width:480,
        buttons:{
            "取消":function(){
                $("#consoleDlg").dialog("close");
            },
            "修改":editItem
        }
    });

    loadSelectedRowData();
    consoleDlg.dialog("open");

};

var openDialog4Deleting = function() {
    var consoleDlg = $("#consoleDlg");
    //   var dialogButtonPanel = consoleDlg.siblings(".ui-dialog-buttonpane");
    consoleDlg.find("input").attr("disabled", true);
    /*
     dialogButtonPanel.find("button:not(:contains('取消'))").hide();
     dialogButtonPanel.find("button:contains('删除')").show();
     consoleDlg.dialog("option", "title", "delete record");
     */

    consoleDlg.dialog({
        title:"删除",
        resizable:false,
        width:480,
        buttons:{
            "取消":function(){
                $("#consoleDlg").dialog("close");
            },
            "删除":deleteItem
        }
    });
    loadSelectedRowData();
    consoleDlg.dialog("open");
};

var loadSelectedRowData = function(){
    //2016-03-18 当前选中的行
    var selectedRowId = $("#regixTable").jqGrid("getGridParam", "selrow");
    //获得当前行各项属性
    var rowData = $("#regixTable").jqGrid("getRowData",selectedRowId);

    if (!selectedRowId) {
        alert("请先选择需要编辑的行!");
        return false;
    } else {
        var consoleDlg = $("#consoleDlg");
        consoleDlg.find("#type").val(rowData.type);
        consoleDlg.find("#rule").val(rowData.rule);
    }
};

var addItem = function(){
    var consoleDlg = $("#consoleDlg");

    var type = $.trim(consoleDlg.find("#type").val());
    var rule = $.trim(consoleDlg.find("#rule").val());
    var params = {
        "type" : type,
        "rule" : rule
    };

    $.ajax({
        url:basePath+"/regix/saveOrUpdate",
        data : params,
        dataType : "json",
        cache : false,
        success : function(response, textStatus) {
            /* alert("id123-->" + response.id + "; message-->" + response.message); */
            if (response!=null) {
                $("#regixTable").jqGrid('setGridParam',{}).trigger("reloadGrid");
                consoleDlg.dialog("close");
                alert("添加成功!");
            }else{
                alert("添加失败!");
            }
        },
        error : function(textStatus, e) {
           // alert("系统ajax交互错误: " + textStatus);

            $("#regixTable").jqGrid('setGridParam',{}).trigger("reloadGrid");
            consoleDlg.dialog("close");
            alert("添加成功!");
        }
    });
};

var editItem = function(){
    var consoleDlg = $("#consoleDlg");
    var type = $.trim(consoleDlg.find("#type").val());
    var rule = $.trim(consoleDlg.find("#rule").val());
    var params = {
        "type" : type,
        "rule" : rule
    };

    $.ajax({
        url:basePath+"/regix/saveOrUpdate",
        data : params,
        dataType : "json",
        cache : false,
        success : function(response, textStatus) {
//          alert("id-->" + response.id + "; message-->" + response.message);
            consoleDlg.dialog("close");
            $("#regixTable").jqGrid('setGridParam',{}).trigger("reloadGrid");
            alert("修改成功")
        },
        error : function(textStatus, e) {
            consoleDlg.dialog("close");
            $("#regixTable").jqGrid('setGridParam',{}).trigger("reloadGrid");
            alert("修改成功")
        }
    });
};

var deleteItem = function() {
    var consoleDlg = $("#consoleDlg");
    var id = $.trim(consoleDlg.find("#selectId").val());
    var type = $.trim(consoleDlg.find("#type").val());


    var params = {
        "type": type
    };

    $.ajax({
        url: basePath+"/regix/deleteType",
        data: params,
        dataType: "json",
        cache: false,
        success: function (response, textStatus) {
//          alert("id-->" + response.id + "; message-->" + response.message);

            consoleDlg.dialog("close");
            $("#regixTable").jqGrid('setGridParam',{}).trigger("reloadGrid");
            alert("删除成功!");
        },
        error: function (textStatus, e) {
          //  alert("系统ajax交互错误: " + textStatus);
            consoleDlg.dialog("close");
            $("#regixTable").jqGrid('setGridParam',{}).trigger("reloadGrid");
            alert("删除成功!");

        }
    });
}