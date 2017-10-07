/**
 * 
 */
	var aHTML,selectid,updateid;
    $(document).ready(function () {
        $('.summernote').summernote({
            lang: 'zh-CN'
        });
        edit();
        $.ajax({
            url:"../Street_News.do",
            dataType:"text",
            success:function(result){
                var data = eval(result);
                for(var i=0;i<data.length;i++){
                	data[i].time = data[i].time.split(".")[0];
                }
                $.extend($.fn.bootstrapTable.columnDefaults, {
                    sortable: true
                });

                $('#exampleTablePagination').bootstrapTable({
                    data:data,
                    search: true,
                    pagination: true,
                    showRefresh: true,
                    showToggle: false,
                    showColumns: true,
                    showHeader:false,
                    iconSize: 'outline',
                    sortName:'time',
                    sortOrder:'desc',
                    pageList:[10,15,20],
                    undefinedText:"数据异常！",
                    toolbar: '#exampleTableEventsToolbar',
                    icons: {
                        refresh: 'glyphicon-repeat',
                        toggle: 'glyphicon-list-alt',
                        columns: 'glyphicon-list'
                    },
                    columns: [
                    {
                        field: 'title',
                        title: '新闻标题',
                        width:'500px',
                        cellStyle:function cellStyle(value, row, index, field) {
                            return {
                                css: {"border": "0px solid #e4eaec"}
                            };
                        },
                        formatter:function(value,row,index){
                        	var d = $(".fixed-table-container")[0];
                        	value = "<a style='text-overflow: ellipsis; white-space: nowrap; display: block; overflow: hidden; width: "+(d.scrollWidth*0.8)+"px;' title='"+value+"'>"+value+"</a>";
                        	return value;
                        }
                    }, 
                    {
                        field: 'time',
                        title: '发布时间',
                        width:'20%',
                        cellStyle:function cellStyle(value, row, index, field) {
                            return {
                                css: {"border": "0px solid #e4eaec", "width":"215px","text-align":"right" }
                            };
                        }
                    }
                    ]
                });
                $('#exampleTablePagination').on('click-row.bs.table', function (e, row, $element) {
                	selectid = row.iD;
                    $(".success").removeClass("success");
                    $element.addClass("success");
                    $("#delete").attr("disabled",false);
                    $("#update").attr("disabled",false);
                })
            },
            error:function(){
            	
            }
        });
    });
    function edit () {
    	$("#edit").attr("disabled",true);
    	$("#save").attr("disabled",false);
    	$("#upload").attr("disabled",true);
    	$("#News_title").attr("disabled",false);
    	$("#News_author").attr("disabled",false);
    	$("#News_time").attr("disabled",false);
    	$("#Isupdate").attr("disabled",true);
        $("#News_Edit").addClass("no-padding");
        $('.click2edit').summernote({
            lang: 'zh-CN',
            focus: true,
            height: 500
        });
    };
    function save () {
    	$("#edit").attr("disabled",false);
    	$("#save").attr("disabled",true);
    	$("#upload").attr("disabled",false);
    	$("#News_title").attr("disabled",true);
    	$("#News_author").attr("disabled",true);
    	$("#News_time").attr("disabled",true);
    	$("#Isupdate").attr("disabled",false);
        $("#News_Edit").removeClass("no-padding");
        aHTML = $('.click2edit').summernote("code"); //save HTML If you need(aHTML: array).
        $('.click2edit').summernote("destroy");
    };

    function uploading (){
    	var parttern = /\s*\S+/;
    	if(document.getElementById("News_title").value == null || document.getElementById("News_title").value == "" || !parttern.test(document.getElementById("News_title").value)){
    		swal("新闻标题不能为空！", "请填写标题后再提交！", "warning");
    		return;
    	}
    	$("#resultInfo").html('<div class="sk-spinner sk-spinner-fading-circle"><div class="sk-circle1 sk-circle"></div><div class="sk-circle2 sk-circle"></div><div class="sk-circle3 sk-circle"></div><div class="sk-circle4 sk-circle"></div><div class="sk-circle5 sk-circle"></div><div class="sk-circle6 sk-circle"></div><div class="sk-circle7 sk-circle"></div><div class="sk-circle8 sk-circle"></div><div class="sk-circle9 sk-circle"></div><div class="sk-circle10 sk-circle"></div><div class="sk-circle11 sk-circle"></div><div class="sk-circle12 sk-circle"></div></div> ');
        $("#onloading-modal").modal({backdrop:'static',keyboard:false});
        $.ajax({
        	type:"post",
            url:"../newsupload.do",
            data:{
                title:document.getElementById("News_title").value,
                author:document.getElementById("News_author").value,
                time:document.getElementById("News_time").value,
                content:aHTML
            },
            dataType:"text",
            success:function(result){
                if(result == '1') {
                	$("#resultInfo").html('<span class=\'glyphicon glyphicon-ok\' style="font-size:40px;color:green"></span><b style="margin-left: 20px;position: relative;font-size:18px;color:white;bottom: 12px;">新闻发布成功！<b>');
                	document.getElementById("News_title").value = null,
                	document.getElementById("News_author").value = null,
                	document.getElementById("News_time").value = null,
                	$('.click2edit').html("<br><p>在这里添加新闻内容。</p><br>"),
                	aHTML = null;
                	$.ajax({
                        url:"../Street_News",
                        dataType:"text",
                        success:function(result){
                            var data = eval(result);
                            for(var i=0;i<data.length;i++){
                            	data[i].time = data[i].time.split(".")[0];
                            }
                            $('#exampleTablePagination').bootstrapTable('load', data);
                        }
                	});
                }
                else if(result == '2') $("#resultInfo").html('<span class=\'glyphicon glyphicon-exclamation-sign\' style="font-size:40px;color:yellow"></span><b style="margin-left: 20px;position: relative;font-size:18px;color:white;bottom: 12px;">此新闻标题重复，发布失败！<b>');
                else $("#resultInfo").html('<span class=\'glyphicon glyphicon-exclamation-sign\' style="font-size:40px;color:yellow"></span><b style="margin-left: 20px;position: relative;font-size:18px;color:white;bottom: 12px;">上传出现异常，新闻发布失败！<b>');
                setTimeout('$(\"#onloading-modal\").modal(\'toggle\');',2000);
            },
            error:function(){
                 $("#resultInfo").html('<span class=\'glyphicon glyphicon-remove\' style="font-size:40px;color:red"></span><b style="margin-left: 20px;position: relative;font-size:18px;color:white;bottom: 12px;">连接服务器失败<b>');
                setTimeout('$(\"#onloading-modal\").modal(\'toggle\');',2000);
            }
        });
    };
    
    function newsdelete (){
    	swal({
            title: "您确定要删除这条信息吗",
            text: "删除后将无法恢复，请谨慎操作！",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "删除",
            closeOnConfirm: false
        }, function () {
        	$.ajax({
        		url:"../NewsDelete.do",
        		data:{id:selectid},
        		dataType:"text",
        		success:function(result){
        			if(result == "1"){
        				var data = $('#exampleTablePagination').bootstrapTable('getData');
        				for(var i=0;i<data.length;i++){
        					if(data[i].iD == selectid){
        						data.splice(i,1);
        						$('#exampleTablePagination').bootstrapTable('load', data);
        					}
        				}
        				var datas = $('#exampleTablePagination').bootstrapTable('getData');
        				swal("删除成功！", "您已经永久删除了这条新闻。", "success");
        				selectid = null;
        				$("#delete").attr("disabled",true);
        			}
        			else{
        				swal("删除失败！", "服务器异常，导致删除这条新闻失败。", "error");
        			}
        		},
        		error:function(e){
        			swal("删除失败！", "无法连接服务器，导致删除这条新闻失败。", "error");
        		}
        	});
            
        });
    };
    
    function update(){
    	save();
    	$.ajax({
            url:"../SingleNews.do",
            data:{ID:selectid},
            dataType:"text",
            success:function(result){
            	document.getElementById("News_title").value = result.substring(result.indexOf("\",\"title\":\"")+11,result.indexOf("\"}"));
            	document.getElementById("News_time").value = result.substring(result.indexOf("\",\"time\":\"")+10,result.indexOf("\",\"title\":\"")).split(".")[0];
            	document.getElementById("News_author").value = result.substring(result.indexOf("\"author\":")+10,result.indexOf("\",\"content\":\""));
            	$(".click2edit").html(result.substring(result.indexOf("\",\"content\":\"")+13,result.indexOf("\",\"iD\":\"")).replace(/\\"/g,"\"").replace(/\\n/g,""));
            	edit();
            	$("#upload").hide();
            	$("#Isupdate").show();
            	$("#Noupdate").show();
            	updateid = selectid;
            },
            error:function(e,q){
            	console.log(e.responseText);
            }
        });
    	/**/
    };
    
    function cancelupdate(){
    	save();
    	document.getElementById("News_title").value = null,
    	document.getElementById("News_author").value = null,
    	document.getElementById("News_time").value = null,
    	$('.click2edit').html("<br><p>在这里添加新闻内容。</p><br>"),
    	aHTML = null,
    	updateid=null;
    	edit();
    	$("#upload").show();
    	$("#Isupdate").hide();
    	$("#Noupdate").hide();
    };
    
    function Isupdate(){
    	swal({
            title: "您确定要修改这条信息吗",
            text: "修改后将无法恢复，请谨慎操作！",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "修改",
            closeOnConfirm: false
        }, function () {
        	$.ajax({
        		type:'post',
        		url:"../NewsUpdate.do",
        		data:{
        			id:updateid,
        			title:document.getElementById("News_title").value,
                    author:document.getElementById("News_author").value,
                    time:document.getElementById("News_time").value,
                    content:aHTML
        		},
        		dataType:'text',
        		success:function(result){
        			if(result == '1'){
        				cancelupdate();
        				selectid = null;
                        $(".success").removeClass("success");
                        $("#delete").attr("disabled",true);
                        $("#update").attr("disabled",true);
        				swal("修改成功！", "您已经修改了这条信息。", "success");	
        				$.ajax({
                            url:"../News.do",
                            dataType:"text",
                            success:function(result){
                                var data = eval(result);
                                for(var i=0;i<data.length;i++){
                                	data[i].time = data[i].time.split(".")[0];
                                }
                                $('#exampleTablePagination').bootstrapTable('load', data);
                            }
                    	});
        			}
        			else{
        				swal("修改失败！", "服务器异常，您修改这条新闻失败。", "error");
        			}	
        		},
        		error:function(e,m){
        			swal("修改失败！", "由于网络问题，您修改这条新闻失败。", "error");
        		}
        	});
        });
    };
    
    function laydateresize(mytargets){
        setTimeout(function(){
            var mytarget = mytargets;
            $("#laydate_box").css("width",mytarget.offsetWidth>240?mytarget.offsetWidth:240);
            $("#laydate_table").css("width",mytarget.offsetWidth>240?mytarget.offsetWidth:240);
        },10) 
    }