<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style type="text/css">
	.ellips-text{width:90%;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;-o-text-overflow:ellipsis;-ms-text-overflow:ellipsis;-moz-binding:url(/xe/ellipsis.xml#ellipsis)}
</style>

<%-- <c:set value="$('#listSize').val()" var="listSize"/> --%>

<%-- <c:set var="listSize">$('#listSize').val()</c:set> --%>

<%-- <c:if test="${empty success}"> --%>
<!-- <h1> -->
<!-- 	empty success -->
<!-- </h1> -->
<%-- 	<c:url var= "url" value = "http://localhost:8080/select"> --%>
<%--         <c:param name= "page" value = "1" /> --%>
<%--         <c:param name= "listSize" value = "3" /> --%>
<%-- 	</c:url>	 --%>

<%-- 	<c:redirect url="http://localhost:8080/select?page=1&listSize=3" /> --%>
<%-- </c:if> --%>

<h1 class="page-tit">운영자관리</h1>

<div class="search-set">
	<strong class="tit">회원검색</strong>
<!-- 	<form id="searchFrm" name="searchFrm" > -->
	<select class="select" id="select-item" name="select-item">
		<option value="all">전체</option>
		<option value="name">이름</option>
		<option value="id">아이디</option>
	</select>
    <input type="text" class="text" name="keyword" <c:if test="${!empty keyword}">value="${keyword}"</c:if>/>
    <a href="#none" class="btn-type1" onclick="searchMember();">검색</a>
    <span class="checkbox-w txt-1">
		<label class="on"><input type="checkbox" name="except">탈퇴회원 제외</label>
	</span>
<!-- 	</form> -->
</div>
<div class="result-wrap">
    <p class="total-feed">전체 : 
    	<em><c:out value="${result.totalCount}"></c:out></em>명 (일반회원 : 
    	<em><c:out value="${result.delNcount}"></c:out></em>명, 탈퇴회원 : 
    	<em><c:out value="${result.delYcount}"></c:out></em>명)</p>
    <div class="btn-wrap">
			<a class="btn-type4" href="excel?selectitem=${selectitem}&keyword=${keyword}">Excel 다운로드</a>
    </div>
</div>


<div>
	<div class="result-wrap">
		<p class="total-feed">
			<em><c:out value="${result.resultCount}"/></em> Page<em><c:out value="${result.page}"/></em> /<em><c:out value="${result.totalPage}"/></em>
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
				<th><span class="checkbox-w"><label id="check-all"><input id="check-all" type="checkbox"></label></span></th>
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
					<td><span class="checkbox-w">
						<label class="check-member" id="check-member">
							<input class="checkbox-a" name="checkbox" type="checkbox" value="${data.ADMIN_ID}">
						</label>
					</span></td>
					<td><c:out value="${result.resultCount - (result.page - 1) * result.listSize - s.index}" /></td>
					<td><a href="javascript:;" onclick="getForm('${data.ADMIN_ID}');"><c:out value="${data.ADMIN_ID }"/></a></td>
					<td><c:out value="${data.ADMIN_NM }"/></td>
					<td><c:out value="${data.TEL_NO }"/></td>
					<td><c:out value="${data.MOBILE_NO }"/></td>
					<td><a class="ellips-text" href="#" onclick="email_layer_open(this);"><c:out value="${data.EMAIL }"/></a></td>
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
			<a class="btn-type4" href="#" onclick="onClickDelete();">삭제</a>
			<a class="btn-type4" href="/adminForm" onclick="">수정</a>
			<a class="btn-type4" href="/adminForm" onclick="">등록</a>
		</div>
	</div>
	
	<div class="pagination" >
	    <c:choose>
		    <c:when test="${empty keyword}">
		    	<a href="select?page=1&listSize=${result.listSize}" class="first">첫페이지로 이동</a>
	    		<a href="select?page=${result.prevPage}&listSize=${result.listSize}" class="prev">이전 페이지로 이동</a>
		    </c:when>
		    <c:otherwise>
		    	<a href="select?page=1&listSize=${result.listSize}&selectitem=${selectitem}&keyword=${keyword}" class="first">첫페이지로 이동</a>
	    		<a href="select?page=${result.prevPage}&listSize=${result.listSize}&selectitem=${selectitem}&keyword=${keyword}" class="prev">이전 페이지로 이동</a>
		    </c:otherwise>
	    </c:choose>
	    
	    
	    <c:forEach var="i" begin="${result.beginPage}" end="${result.endPage}">
	    		<c:choose>
		    		<c:when test="${empty keyword}">
		    			<a href="select?page=${i}&listSize=${result.listSize}" id="page" 
	    					<c:if test="${result.page eq i}">class="on"</c:if>
	    				>${i}</a>
		    		</c:when>
		    		<c:otherwise>
		    			<a href="select?page=${i}&listSize=${result.listSize}&selectitem=${selectitem}&keyword=${keyword}" id="page" 
	    					<c:if test="${result.page eq i}">class="on"</c:if>
	    				>${i}</a>
		    		</c:otherwise>
	    		</c:choose>
	    		
	    </c:forEach>
	    
	     <c:choose>
		    <c:when test="${empty keyword}">
		    	<a href="select?page=${result.nextPage}&listSize=${result.listSize}" class="next">다음 페이지로 이동</a>
	    <a href="select?page=${result.totalPage}&listSize=${result.listSize}" class="last">마지막 페이지로 이동</a>
		    </c:when>
		    <c:otherwise>
		    	<a href="select?page=${result.nextPage}&listSize=${result.listSize}&selectitem=${selectitem}&keyword=${keyword}" class="next">다음 페이지로 이동</a>
	    <a href="select?page=${result.totalPage}&listSize=${result.listSize}&selectitem=${selectitem}&keyword=${keyword}" class="last">마지막 페이지로 이동</a>
		    </c:otherwise>
	    </c:choose>
	</div>
</div>

<%@ include file="../common/layout/email/email-layer.jsp"%>	


<script type="text/javascript">



	$(document).ready(function() {
		checkFirstConnect();
		onClickCheckbox();
		enterSearch();
	});
	
	function checkFirstConnect(){
		let first = ${first};
		if(first == true){
			console.log("isEmptyObject");
			location.href="select?page=1&listSize="+getElementValue('select[name=listSize]');
		}
	}
	
	function enterSearch(){
		$('input[name=keyword]').keypress(function(key){
			if(key.which == 13){
				searchMember();
				console.log("enterSearch");
			}
		});
	}

	function searchMember() {
// 		var listSize = ${result.listSize};
		var listSize = getElementValue('select[name=listSize]');
		// 		var selectitem = $('#select-item').val();
		var selectitem = getElementValue('#select-item');
		var keyword = getElementValue('input[name=keyword]');

		location.href = "select?page=1&listSize=" + listSize + "&selectitem="
				+ selectitem + "&keyword=" + keyword;
	}

	function onClickCheckbox() {
		$('.tbl-1 label').click(function() {

			if ($(this).attr("id") == "check-all") {
				onClickAllCheck($(this));
			}

			if ($(this).children('input').is(':checked')) {
				$(this).addClass('on');
			} else {
				$(this).removeClass('on');
			}
		});
	}

	function onClickAllCheck(tempThis) {
		if (tempThis.children('input').is(':checked')) {
			console.log("checked1");
			$(".check-member").addClass("on");
			$(".checkbox-a").prop('checked', true);
		} else {
			console.log("checked2");
			resetAllCheck();
		}
	}

	function resetAllCheck() {
		$(".check-member").removeClass('on');
		$(".checkbox-a").prop('checked', false);
	}

	function onClickDelete() {
		const result = confirm('정말 삭제 하시겠습니까?');

		if (!result) {
			return;
		}

		let data = {};

		data.checkArray = getCheckValue();

		if (data.checkArray.length == 0) {
			alert("선택한 대상이 없습니다.");
			return;
		}

		console.log(data);

		$.ajax({
					url : "/delete.json",
					dataType : "json",
					type : "POST",
					data : data,
					success : function(data, textStatus, jqXHR) {
						if ($.common.ajaxValidate(data)) {
							alert('삭제 성공');
							location.href = 'select?page=${result.page}&listSize=${result.listSize}';
						} else {
							alert('삭제 실패');
						}
					},
					error : function() {
						alert('에러가 발생 했습니다.');
					}
				});
	}

	function getCheckValue() {

		let checkboxValues = [];

		$('input[name=checkbox]:checked').each(function(i) {
			checkboxValues.push($(this).val());
		});

		return checkboxValues;
	}

	function email_layer_open(el) {
		console.log("email_layer_open");

		var temp = $('#pop-layer-email');
		var bg = temp.prev().hasClass('bg'); //dimmed 레이어를 감지하기 위한 boolean 변수

		if (bg) {
			$('#layer-email').show(); //'bg' 클래스가 존재하면 레이어가 나타나고 배경은 dimmed 된다. 
		} else {
			temp.show();
		}

		$("input[name=address]").val(el.text);

		// 화면의 중앙에 레이어를 띄운다.
		if (temp.outerHeight() < $(document).height())
			temp.css('margin-top', '-' + temp.outerHeight() / 2 + 'px');
		else
			temp.css('top', '0px');
		if (temp.outerWidth() < $(document).width())
			temp.css('margin-left', '-' + temp.outerWidth() / 2 + 'px');
		else
			temp.css('left', '0px');

		$('#layer-email #close-layer').on('click', function() {
			if (bg) {
				$('#layer-email').hide(); //'bg' 클래스가 존재하면 레이어를 사라지게 한다. 
			} else {
				temp.dhie();
			}

			$('#form-email')[0].reset();
		});

		$('#layer-email .bg').click(function(e) { //배경을 클릭하면 레이어를 사라지게 하는 이벤트 핸들러
			$('#layer-email').hide();
			e.preventDefault();

			$('#form-email')[0].reset();
		});
	}

	function sendEamil() {
		if (!$('#form-email').validateForm()) {
			return false;
		}

		$('#layer-sending').show();

		var temp = $('#pop-layer-sending');
		var bg = temp.prev().hasClass('bg'); //dimmed 레이어를 감지하기 위한 boolean 변수

		// 화면의 중앙에 레이어를 띄운다.
		if (temp.outerHeight() < $(document).height())
			temp.css('margin-top', '-' + temp.outerHeight() / 2 + 'px');
		else
			temp.css('top', '0px');
		if (temp.outerWidth() < $(document).width())
			temp.css('margin-left', '-' + temp.outerWidth() / 2 + 'px');
		else
			temp.css('left', '0px');

		sendEmailObj.sendEmailAction();
	}

	var sendEmailObj = {
		submitFlag : true,
		sendEmailAction : function() {

			var data = $('#form-email').serializeObject();

			console.log(data);

			$.ajax({
				url : "/sendEmail.json",
				dataType : "json",
				type : "POST",
				data : data,
				success : function(data, textStatus, jqXHR) {
					alert('메일 전송 완료');

					$('#layer-sending').hide();
					$('#form-email')[0].reset();
					$('#layer-email').hide();
				},
				error : function() {
					alert('메일 전송을 실패');
					$('#layer-sending').hide();
				}
			});
		}
	};
</script>
