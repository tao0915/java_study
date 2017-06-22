<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style type="text/css">
	.ellips-text{width:90%;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;-o-text-overflow:ellipsis;-ms-text-overflow:ellipsis;-moz-binding:url(/xe/ellipsis.xml#ellipsis)}
</style>


<h1 class="page-tit">운영자관리</h1>

<div>
	<div class="result-wrap">
		<p class="total-feed">
			<em><c:out value="${result.totalCount}"/></em> Page<em><c:out value="${result.page}"/></em> /<em><c:out value="${result.totalPage}"/></em>
		</p>
	<%@ include file="../common/include/selectListSizeArea.jsp"%>		
	</div>
	<table class="tbl-1">
		<colgroup>
			<col style="width: 4%">
			<col>
			<col>
			<col>
			<col>
			<col>
			<col>
			<col style="width: 8%">
			<col style="width: 8%">
		</colgroup>
		<thead>
			<tr>
				<th><span>번호</span></th>
				<th><span>아이디 </span></th>
				<th><span>이름</span></th>
				<th><span>전화번호</span></th>
				<th><span>휴대폰번호</span></th>
				<th><span>이메일</span></th>
				<th><span>사용여부</span></th>
				<th><span>등록일</span></th>
				<th><span>수정일</span></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${result.list }" var="data" varStatus="s">
				<tr>
					<td><c:out value="${result.totalCount - (result.page - 1) * result.listSize - s.index}" /></td>
					<td><a href="javascript:;" onclick="getForm('${data.ADMIN_ID}');"><c:out value="${data.ADMIN_ID }"/></a></td>
					<td><c:out value="${data.ADMIN_NM }"/></td>
					<td><c:out value="${data.TEL_NO }"/></td>
					<td><c:out value="${data.MOBILE_NO }"/></td>
					<td><a class="ellips-text" href="#"><c:out value="${data.EMAIL }"/></a></td>
					<td><c:out value="${data.USE_YN }"/></td>
					<td class="ellips-text"><c:out value="${data.CRE_DT }"/></td>
					<td class="ellips-text"><c:out value="${data.MOD_DT }"/></td>
				</tr>
			</c:forEach>			
			<c:if test="${empty result.list }">
				<tr>
					<td colspan="9">등록된 정보가 없습니다.</td>
				</tr>
			</c:if>
		</tbody>
	</table>
	<div class="form-wrap">
		<div class="btn-wrap">
			<a class="btn-type4" href="javascript:alert('삭제되었습니다');" onclick="">삭제</a>
			<a class="btn-type4" href="/adminForm" onclick="">수정</a>
			<a class="btn-type4" href="/adminForm" onclick="">등록</a>
		</div>
	</div>
	
	<div class="pagination" >
	    <a href="select?page=1&listSize=${result.listSize}" class="first">첫페이지로 이동</a>
	    <a href="select?page=${result.prevPage}&listSize=${result.listSize}" class="prev">이전 페이지로 이동</a>
	    
	    <c:forEach var="i" begin="${result.beginPage}" end="${result.endPage}">
	    		<a href="select?page=${i}&listSize=${result.listSize}" id="page" 
	    			<c:if test="${result.page eq i}">class="on"</c:if>
<%-- 	    			<c:if test="${empty result.page and i eq result.beginPage}">class="on"</c:if> --%>
	    		>${i}</a>
	    </c:forEach>
	    
	    <a href="select?page=${result.nextPage}&listSize=${result.listSize}" class="next">다음 페이지로 이동</a>
	    <a href="select?page=${result.lastPage}&listSize=${result.listSize}" class="last">마지막 페이지로 이동</a>
	</div>
</div>


<script type="text/javascript">
// let page = 1;
// let lastPage = ${result.lastPage};
// let nextPage = ${result.nextPage};
// let prevPage = ${result.prevPage};

// console.log("lastPage : "+lastPage);

$(document).ready(function(){
// 	$('.pagination a').removeClass("on");
// 	$('.pagination #pageNo').eq(pageNo-1).addClass("on");
// 	selectMember();
// 	onClickPage();
});
// 	function onClickPage(){
// 		$('.pagination a').click(function(){
// 			console.log("page : "+$(this).text());
			
// 			if($(this).attr('class') == "next"){
// 				page = nextPage;
// 			} else 
// 				if($(this).attr('class') == "prev"){
// 				var listSize = $("select[name=listSize]").val();
// 				page = prevPage;
				
// 			} else if($(this).attr('class') == "first"){
// 				page = 1;
// 			} else if($(this).attr('class') == "last"){
// 				page = lastPage;
// 			} else {
// 				page = $(this).text();
// 			}
			
// 		 	$('.pagination a').removeClass("on");
// 		 	$(this).addClass("on");
		 	
// 		 	selectMember();
// 		});
// // 	}
// 	function selectMember(){
// // 		 let data = $('#frm').serializeObject();
// // 		 data.pageNo = pageNo;
// 		 let listSize = $('select[name=listSize]').val();
// 		 location.href = "/select?page="+page+"&listSize="+listSize;
		 
		 
		 
// // 		 $.ajax({
// // 		    url : "/select.json",
// // 		    dataType : "json",
// // 		    type : "GET",
// // 		    data : data,
// // 		    success : function(data, textStatus, jqXHR){
// // 		    	if($.common.ajaxValidate(data)){
// // 		    		alert('success');
// // 		    	} else {
// // 		    		alert('알수 없는 오류가 발생 했습니다.');
// // 		    	}
// // 		    },
// // 		    error : function(){
// // 		    	alert('에러가 발생 했습니다.');
// // 		    }
// // 		 });
// 	}


</script>
