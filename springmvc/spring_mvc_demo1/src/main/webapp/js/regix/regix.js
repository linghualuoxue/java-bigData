$(function () {
    initTable();
});

function initTable() {

    $("#regixTable").jqGrid({
        url:basePath+"/getPage.do",
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
                label:"姓名",
                name:"name",
                width: 160,
                sortable:false/*,
                formatter:function (cellvalue,options,rowObject) {
                    alert(rowObject);
                    return rowObject.name;
                }*/
            },{
                label:"id",
                name:"id",
                width: 160,
                hidden:true,
                sortable:false,
                /*,
                 formatter:function (cellvalue,options,rowObject) {
                 alert(cellvalue);
                 return rowObject.id;
                 }*/
            }, {
                label:"年龄",
                name:"age",
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
    }).navGrid("#pageTable", { search: false, edit: true, add: true, del: true });;
}