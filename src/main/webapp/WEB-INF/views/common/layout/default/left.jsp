<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(document).ready(function() {		
		if($('#headerNav').length && $('#headerNav').find('li:eq(0)').length){
			menuObj.subAddLeftMenu($('#headerNav').find('li:eq(0)').find('a').data('menuid'),$('#headerNav').find('li:eq(0)').find('a').text());
		}	 	
	});
	
	
	//메뉴 객체
	var menuObj = {
			submitFlag : true,
			backUrl : location.href,
			backMenuId : null,
			defaultOption : {
								bodyTargetId : 'body',		// 반환한 페이지 정보를 넣을 타겟 id  
								callbackFun : null, 		// 반환 후 정보를 받을 콜백 메소드
								url : '/admin/goLocationMenu', // 호출 url	
								backUrl : null				// 세션이 끊겼을 경우 재로그인후 이동할 페이지
							},
			
			//메뉴id가 아닌 url로 호출시 body영역 변경(left 메뉴 이동을 제위한 body 영역 교체시 사용)
			goUrl : function(option){
				if(!option || typeof option != 'object' || !option.url ) alert('url은 필수 입니다.');
				
				option = $.extend({}, this.defaultOption, option);
				if(option.backUrl){ 
					this.backUrl = option.backUrl;
					this.backMenuId = null;	
				};
				
				this.sendAjax(option);
			},
			
			
			//선택된 메뉴에 대한 페이지 정보를 가져와 body영역에 넣음 (left 메뉴에서 클릭 시 사용)
			goLocationMenu : function(menuId, option){
				if(!menuId) { alert('메뉴 아이디는 필수 입니다.');  }
				
				if(!option || typeof option != 'object') option = {};
				
				option = $.extend({}, this.defaultOption, option);
				
				option.data = {};
				option.data['MENU_ID'] = menuId;
				this.backMenuId = menuId;				
				this.sendAjax(option);
			},
			
			//ajax 호출
			sendAjax : function(option){
				if(!option.url){  alert('url이 없습니다.'); }
				
				console.log(option);
				
				if(menuObj.submitFlag){
					menuObj.submitFlag = false;
					$.ajax({
						url			: option.url,
						dataType	: (option.dataType ? option.dataType : 'html') , 
						type		:  (option.type ? option.Type : 'post'), 
						data		: option.data, 
						success		: function(data, textStatus, jqXHR){
							menuObj.submitFlag = true;
							
							// 결과값 처리							
							if($.common.ajaxValidate(data)){
								if(option.callbackFun && typeof(option.callbackFun) === 'function'){
									option.data = data;
									option.callbackFun(option);
								}else{
									if($('#'+option.bodyTargetId).length){
										
										$('#'+option.bodyTargetId).empty();
										$('#'+option.bodyTargetId).html(data);
									}									
								}
							}
						},
						
						error : function(){
							menuObj.submitFlag = true;							
						}
					});							
				}
			},
			
			//선택된 메뉴에 대한 하위 목록 서버에 호출
			 subAddLeftMenu: function(id, name, obj){
				if($('#'+id).length && !name){
					this.goUrl({url :'/admin/getAuthSubMenuList', 
									data: { MENU_ID : id}, 
									currtMenuId : id,
									bodyTargetId : id, 
									dataType : 'json',
									callbackFun : menuObj.createLeftMenu});
				}else{
					if($(obj).length){			//헤더에서 대메뉴 클릭 시						
						if(!$(obj).parent().hasClass('active')){
							$(obj).parent().addClass('active').siblings().removeClass('active');	
						}
					}
					this.goUrl({url :'/admin/getAuthSubMenuList', 
						data: { MENU_ID : id},
						currtMenuId : id,
						bodyTargetId : "leftMenu", 
						dataType : 'json',
						menuNm : name,
						callbackFun : menuObj.createLeftMenu});					
				}
			}, 
			
			
			//왼쪽 메뉴 생성
			createLeftMenu : function(option){
				//console.log(option);
				var addLeftMenu = function(data){
					var subHtml = [];
					if(data){
						subHtml.push('<ul class="submenu">');
						for( idx in data){
							if(data[idx].MENU_DEPTH > 2 && idx == data.length -1) {
								subHtml.push('<li class=\"node'+data[idx].MENU_DEPTH+' last\">');
							} else {
								subHtml.push('<li class=\"node'+data[idx].MENU_DEPTH+'\">');	
							}
							
							 if(typeof data[idx].NOT_LEFT_YN === 'undefined'){								 
								 subHtml.push('<a class=\"depth'+data[idx].MENU_DEPTH+'\" onclick=\'menuObj.goLocationMenu("'+data[idx].MENU_ID+'");\'>'+data[idx].MENU_NM+'</a>');
							 }else{
								 subHtml.push('<a class=\"depth'+data[idx].MENU_DEPTH+'\" onclick=\'menuObj.subAddLeftMenu("'+data[idx].MENU_ID+'");\' id="'+data[idx].MENU_ID+'" >'+data[idx].MENU_NM+'<span class="extend">펼침</span></a>');
							 }
							 subHtml.push('</li>');
						}
						subHtml.push('</ul>');
					}
					return subHtml.join('');
				};
				
				if($('#'+option.bodyTargetId).length){
					var html = [];
					if(option.bodyTargetId == 'leftMenu'){
						$('#'+option.bodyTargetId).empty();
						html.push('<ul class="lnb">');
						html.push('<li><a class="depth1" onclick=\'menuObj.subAddLeftMenu("'+option.currtMenuId+'","'+option.menuNm+'");\'>'+option.menuNm+'</a>');
						html.push(addLeftMenu(option.data));
						html.push('</li></ul>');
						$('#'+option.bodyTargetId).html(html.join(''));
						
						menuObj.setMenuEvent($('#'+option.bodyTargetId));
					}else{						
						html.push(addLeftMenu(option.data));
						
						$('#'+option.bodyTargetId).next().remove();
						$('#'+option.bodyTargetId).after(html.join(''));
						menuObj.setMenuEventOneCall($('#'+option.bodyTargetId));	
					}
				}
			},
			
			//왼쪽 영역 변경 후 클릭 이벤트 재 셋팅
			setMenuEvent : function(obj){
				if(obj.length){
					obj.find('ul li>a').unbind();					
					obj.find('ul li>a').bind('click', function(e) {
				        var $this = $(this);
				        var $parent = $(this).parent();

				        if($parent.hasClass('on')){
				            $parent.removeClass('on');
				        } else {
				            $('.lnb .submenu li').removeClass('on');
				            $('.lnb .submenu li>a').next('ul').hide();
				            $parent.addClass('on');
				            $this.next('ul').show();
				        }
					});					
				}
			},
			
			//한번에 이벤트만 발생 시킴
			setMenuEventOneCall : function(obj){
				if(obj.length){
					var element = obj.parent('li');
					if(element.length && element.hasClass('on')){
						element.find('ul li:eq(0)').addClass('on');
						element.find('ul li:eq(0)>a').trigger('click');
						element.find('ul li').unbind();
						element.find('ul li').bind('click',function(){
							$(this).siblings().removeClass('on');
							$(this).addClass('on');
						});
					}
				}
			}
		};
</script>
<div id="leftMenu"></div>
