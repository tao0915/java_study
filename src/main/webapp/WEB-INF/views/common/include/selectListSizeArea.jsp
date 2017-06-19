<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${empty  currtUrl }"><c:set var="currtUrl">none</c:set></c:if>
<div class="select-wrap">
	<select class="select"  name="listSize" onchange="changeListSize(this);">
		<option value="10"  <c:if test="${param.listSize eq '10' }">selected="selected"</c:if>>10개씩 보기</option>
		<option value="20"  <c:if test="${param.listSize eq '20' }">selected="selected"</c:if>>20개씩 보기</option>
		<option value="30" <c:if test="${param.listSize eq '30' }">selected="selected"</c:if>>30개씩 보기</option>
		<option value="50" <c:if test="${param.listSize eq '50' }">selected="selected"</c:if>>50개씩 보기</option>
		<option value="100" <c:if test="${param.listSize eq '100' }">selected="selected"</c:if>>100개씩 보기</option>
	</select>
</div>	
<script type="text/javascript">
var currtUrl = '${currtUrl}';
function changeListSize(obj){	
	if(currtUrl == 'none'){
		alert('currtUrl로 지정된 프로퍼티가 없습니다.');
		return;	
	}
	
	var data = $('form[name=searchFrm]').serializeObject();
	data.listSize = $(obj).val();
	
	var option = {								
			url : '${currtUrl}',
			data : data,
			backUrl:'${backUrl}'
		};
	menuObj.goUrl(option);
}
</script>
