//ajax请求模板
$.ajax({
url:"workbench/activity/save.do",
data:{

},
type:"post",
dataType:"json",
success:function (data){
}
})

//获取系统时间，通过session域取得当前用户对象
String  createTime= DateTimeUtil.getSysTime();
String  createBy=((User)request.getSession().getAttribute("user")).getName();