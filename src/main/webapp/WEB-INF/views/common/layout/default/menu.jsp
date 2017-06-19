<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta name="robots" content="noindex,follow">
		<title>menuTest Page</title>
		
		<script type="text/javascript" src="/resources/js/jquery/treeviewDnD/jquery/jquery.js"></script>
		<script type="text/javascript" src="/resources/js/jquery/treeviewDnD/jquery/jquery-ui.custom.js"></script>
		<script type="text/javascript" src="/resources/js/jquery/treeviewDnD/jquery/jquery.cookie.js"></script>
		
		<link rel="stylesheet" href="/resources/js/jquery/treeviewDnD/src/skin/ui.dynatree.css" />
		<script type="text/javascript" src="/resources/js/jquery/treeviewDnD/src/jquery.dynatree.js"></script>
		
		<link rel="stylesheet" href="/resources/js/jquery/treeviewDnD/doc/prettify.css" />
		<script type="text/javascript" src="/resources/js/jquery/treeviewDnD/doc/prettify.js"></script>
<!-- 		<link rel="stylesheet" href="/resources/js/jquery/treeviewDnD/doc/sample.css" /> -->
<!-- 		<script type="text/javascript" src="/resources/js/jquery/treeviewDnD/doc/sample.js"></script> -->
		<script type="text/javascript" src="/resources/js/common.js"></script>
		
		<script type="text/javascript">
			$(function(){
				$("#tree").dynatree({
					initAjax: {
						url : "/admin/menu/treeViewCall.json",
					},
					callback : function(){
						console.log('seung callback       ================          ');	
					}
					, dnd: {
						preventVoidMoves: true, // Prevent dropping nodes 'before self', etc.
						onDragStart: function(node) {
							/** This function MUST be defined to enable dragging for the tree.
							 *  Return false to cancel dragging of node.
							 */
							return true;
						},
						onDragEnter: function(node, sourceNode) {
							/** sourceNode may be null for non-dynatree droppables.
							 *  Return false to disallow dropping on node. In this case
							 *  onDragOver and onDragLeave are not called.
							 *  Return 'over', 'before, or 'after' to force a hitMode.
							 *  Return ['before', 'after'] to restrict available hitModes.
							 *  Any other return value will calc the hitMode from the cursor position.
							 */
							// Prevent dropping a parent below another parent (only sort
							// nodes under the same parent)
							if(node.parent !== sourceNode.parent){
								return false;
							}
							// Don't allow dropping *over* a node (would create a child)
							return ["before", "after"];
						}
						, onDrop: function(node, sourceNode, hitMode, ui, draggable) {
							/** This function MUST be defined to enable dropping of items on
							 *  the tree.
							**/
							sourceNode.move(node, hitMode);
							
							var dataMenuIds = [];
							for(key in node.getParent().getChildren()){
								//console.log("node.getParent().getChildren() >>>>>" + node.getParent().getChildren()[key].data.MENU_ID);
								dataMenuIds.push(node.getParent().getChildren()[key].data.MENU_ID);
							}
							
							$.ajax({
								url			: "/admin/menu/menuChange.json",
								dataType	: "json", 
								type		: "POST", 
								data		: { 
									'menuIds' : dataMenuIds.join(',')
									, 'menuUpId' : node.data.MENU_UP_ID
								}, 
								success		: function(data, textStatus, jqXHR){
									// 결과값 처리
									if($.common.ajaxValidate(data)){}
								},
								error : function(){
									alert('에러');
								}
							});
						}
					}
					, onClick : function(dtnode, event){
						if ( dtnode.data.isFolder) {
							dtnode.toggleSelect();
							/*if(!dtnode.bExpanded){
								dtnode.toggleSelect();	
							}else{
								return false;
							}*/
						} 
						
						$("input[name='radioChk']").attr("checked", false);
						for(key in dtnode.data){								
							if($('input[name='+key+']').length){
								if(dtnode.data[key] == 'NULL'){
									$('input[name='+key+']').val('');
								}else{
									$('input[name='+key+']').val(dtnode.data[key]);	
								}
								
								if ( key == "MENU_DEPTH" ) {
									if ( Number(dtnode.data[key]) > 2 ) {
										$("input[name='radioChk']:input[value='addChildren']").attr("disabled", true);
									} else {
										$("input[name='radioChk']:input[value='addChildren']").attr("disabled", false);
									}
								}
								if ( key == "USE_YN" ) {
									console.log("key(USE_YN) >>>>>>>>>>>>>>>" + key + "<>" + dtnode.data[key]);
									if ( dtnode.data[key]) {
										if(dtnode.data[key] == 'Y'){
											$("input[name='USE_YN']:eq(0)").prop("checked",true);
										}else{
											$("input[name='USE_YN']:eq(1)").prop("checked",true);
										}
									}
								}
							}
							
							if($('#'+key+'_TD').length){
								$('#'+key+'_TD').html(dtnode.data[key]);
							}
						}
						$("#addArea").show();
						selectNode['modify'] = dtnode;
						selectNode['addChildren'] = dtnode;
						selectNode['add'] = dtnode.getParent();
					}
				});
			});
			var selectNode = {};
			function fnSubmit () {
				var sendDepth;
				if ( $(":radio[name='radioChk']:checked").length < 1 ) {
					alert("종류 선택해라");
					return false;
				} else {
					if ( $("input[name='radioChk']:checked").val() == "addChildren" ) {
						sendDepth = Number($("input[name='MENU_DEPTH']").val()) + 1;
						$("#sendRadioChk").val($(":radio[name='radioChk']:checked").val());
						$("input[name='MENU_DEPTH']").val(sendDepth);
						$("input[name='MENU_UP_ID']").val($("input[name='MENU_ID']").val());
					}
				}
				if ( $(":radio[name='USE_YN']:checked").length < 1 ) {
					alert("사용여부 선택해라");
					return false;
				} else {
					
				}
				if ( $("input[name='MENU_NM']").val() == "" ) {
					alert("메뉴명 입력해라");
					return false;
				}
				if ( $("input[name='MENU_URL']").val() == "" ) {
					alert("메뉴url 입력해라");
					return false;
				}
				
				$.ajax({
					url			: "/admin/menu/treeAdd.json",
					dataType	: "json", 
					type		: "POST", 
					data		: $("#frm").serialize(),
					success		: function(data, textStatus, jqXHR){
						if(selectNode[$('input[name=radioChk]:checked').val()] && data){
							//data['title'] = data.MENU_NM;
							if ( $('input[name=radioChk]:checked').val() == "modify" ) {
								for ( key in data ) {
									if(key == 'MENU_NM'){
										selectNode[$('input[name=radioChk]:checked').val()].setTitle(data[key]);
									}
									selectNode[$('input[name=radioChk]:checked').val()].data[key]=data[key];
								}
							} else {
								data['title'] = data.MENU_NM;
								selectNode[$('input[name=radioChk]:checked').val()].addChild(data);
							}
						}
					},
					error : function(){
						alert('에러');
					}
				});
			}
		</script>
	</head>
	<body>
		<div id="tree"></div>
		
		<div id="addArea" style="display:none;">
			<form id="frm" name="frm" method="post">
				<input type="hidden" name="MENU_ID" />
				<input type="hidden" name="MENU_UP_ID" />
				<input type="hidden" name="MENU_DEPTH" />
				<input type="hidden" name="DISP_NO" />
				<input type="hidden" name="sendRadioChk" />
			<table>			
				<tr>
					<th>종류</th>
					<td>
						<!-- <input type="radio" name="radioChk" value="modify" data-area="inputArea">수정
						<input type="radio" name="radioChk" value="add" data-area="inputArea">추가
						<input type="radio" name="radioChk" value="addChildren" data-hidearea="inputArea">하위 추가 -->
						<input type="radio" name="radioChk" value="modify">수정
						<input type="radio" name="radioChk" value="add">추가
						<input type="radio" name="radioChk" value="addChildren">하위 추가
					</td>
				</tr>
				<tr>
					<th>사용여부</th>
					<td>
						<input type="radio" name="USE_YN" value="Y">사용
						<input type="radio" name="USE_YN" value="N">미사용
					</td>
				</tr>	
				<tr>
					<th>메뉴ID</th>
					<td id="MENU_ID_TD"></td>
				</tr>
				<tr>
					<th>상위메뉴ID</th>
					<td id="MENU_UP_ID_TD"></td>
				</tr>
				<tr>
					<th>메뉴뎁스</th>
					<td id="MENU_DEPTH_TD"></td>
				</tr>
				<tr>
					<th>메뉴명</th>
					<td><input type="text" name="MENU_NM" /></td>
				</tr>
				<tr>
					<th>메뉴url</th>
					<td><input type="text" name="MENU_URL" /></td>
				</tr>
				<tr>
					<td>
						<button onclick="fnSubmit();return false;">저장</button>
					</td>
				</tr>			
			</table>
			</form>		
		</div>
	</body>
</html>
