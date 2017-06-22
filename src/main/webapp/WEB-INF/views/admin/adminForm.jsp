<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form id="frm"  name="frm" method="post">
<input type="hidden" name="mode"  value="${mode }"/>

<div class="tit-wrap-1">
  <h2 class="tit-1">사용자 등록/수정</h2>
  <table class="tbl-2">
    <colgroup>
      	<col width="15%"/>
		<col width="35%"/>
		<col width="15%"/>
		<col width="35%"/>
    </colgroup>
    <tbody>
      <tr>
        <th>아이디</th>
        <td class="input-wrap">
        	<c:choose>
				<c:when test="${mode eq 'update'}">
					<input type="text"  name="ADMIN_ID"   value="${data['ADMIN_ID']}"   maxlength="20"  class="text required"   title="아이디"  readonly="readonly"/>
					<input type="hidden" id="idChkYn"  value="Y"/>
				</c:when>
				<c:otherwise>
					<input type="text"  name="ADMIN_ID"    maxlength="20"  class="text required"   title="아이디"/>
					<a href="javascript:;" onclick="adminIdChk();" class="btn-type1">중복체크</a>
					<input type="hidden" id="idChkYn"  value="N"/>
				</c:otherwise>
			</c:choose>
        </td>
        <th>비밀번호</th>
        <td class="input-wrap">
        	<input type="password"  name="ADMIN_PWD"   value=""   maxlength="20"  class="text required"  title="비밀번호"/>
        </td>
      </tr>


      <tr>
        <th>이름</th>
        <td class="input-wrap">
        	<input type="text"  name="ADMIN_NM"   value=""   maxlength="20"  class="text required" title="이름"/>
        </td>
        <th>조직명</th>
        <td class="input-wrap">
        	<input type="text"  name="ORG_NM"  value=""  maxlength="20" class="text"/>
        </td>
      </tr>

      <tr>
        <th>전화번호</th>
        <td class="input-wrap">
        	<input type="text"  name="TEL_NO"  value=""  maxlength="12" class="text onlynum" title="전화번호"/>
        </td>
        <th>휴대폰번호</th>
        <td class="input-wrap">
        	<input type="text"  name="MOBILE_NO"  value=""  maxlength="12" class="text onlynum" title="휴대폰 번호"/>
        </td>
      </tr>

      <tr>
        <th>email</th>
        <td class="input-wrap" colspan="3">
        	<input type="text"  name="EMAIL"  value=""  maxlength="30" class="text required" title="email"/>
        </td>
      </tr>
      <tr>
        <th>권한그룹</th>
        <td class="input-wrap" colspan="3">
        	<span class="radio-w">
	        	<c:forEach items="${authList}"  var="i">
					<c:choose>
						<c:when test="${!empty data['AUTH_ID'] and  data['AUTH_ID'] eq i.AUTH_ID}"><c:set var="checked" >checked="checked"</c:set></c:when>
						<c:otherwise><c:set var="checked"  value=""/></c:otherwise>
					</c:choose>
			            <label <c:if test="${not empty checked}">class="on"</c:if>>
			            	<input type="radio"  value="${i.AUTH_ID }"  name="AUTH_ID"  ${checked}  class="required"  title="권한그룹"/><c:out value="${i.AUTH_NM}"/>
						</label>
				</c:forEach>
			</span>

        </td>
      </tr>
    </tbody>
  </table>
</div>
</form>

<div class="form-wrap">
	<div class="btn-wrap">
		<a class="btn-type4" href="#" onclick="adminSave();">저장</a>
	</div>
</div>

<script type="text/javascript">
$(document).ready(function() {
	changeTextId();
	changeRadioButton();
});

	function adminSave(){
		if(!$('#frm').validateForm()){ return false; }
		console.log("idChkYn : "+$('#idChkYn').val());
		if($('#idChkYn').val() === 'N') {
			alert('아이디 중복 체크를 해주셔야 합니다.');
			return false;
		}
		
		insertMember();
	}
	
	 function insertMember(){
	    var data = $('#frm').serializeObject();
	    
	    $.ajax({
	    	url : "/insertMember.json",
	    	dataType : "json",
	    	type : "POST",
	    	data : data,
	    	success : function(data, textStatus, jqXHR){
	    		if($.common.ajaxValidate(data)){
	    			saveSuccess();
	    			location.href = data.redirectUrl;
	    		} else {
	    			alert('중복되는 아이디 이거나, 알수 없는 오류가 발생 했습니다.');
	    		}
	    	},
	    	error : function(){
	    		alert('에러가 발생 했습니다.');
	    	}
	    });
	}

	function saveSuccess(){
		alert('저장되었습니다. \n목록 페이지로 이동하겠습니다.');
	}

    //아이디 중복 체크
	function adminIdChk(){
    	if($('input[name=ADMIN_ID]').val() == ""){
    		alert("[아이디]를 입력해 주세요.");
    		return;
    	}
    	
    	var data = $('#frm').serializeObject();
    	
    	$.ajax({
    		url : "/idCheck.json",
    		dataType : "json",
    		type : "POST",
    		data : data,
    		success : function(data, textStatus, jqXHR){
    			//resultCode가 100이면 $.common.ajaxValidate(data) = false가 된다. (common.js참조)
    			if($.common.ajaxValidate(data)){
    				callback(data);
    			} else {
    				alert('중복되는 아이디가 있습니다. 다시 입력해 주세요.');
    			}
    		},
    		error : function(){
    			alert('에러가 발생 했습니다. 페이지를 새로고침해 주세요.');
    		}
    	});
	}

    //id 중복 체크 성공시
	function callback(data){
		alert('사용가능한 아이디 입니다.');
		$('#idChkYn').val('Y');
	}
    
    //id input의 text가 변경되면 중복 체크 안한걸로 변경
    function checkClear(){
    	console.log("idChkYn");
    	$('#idChkYn').val('N');
    }
    
    //text 모두 입력후 focus떠날때 호출
//     $('input[name=ADMIN_ID]').change(function(){
//     });
    
    //text 실시간으로 입력 할때 마다 호출
    //jquery.splendid.textchange.js를 추가하여 사용 IE9, IE8도 사용가능
    function changeTextId(){
    	$('input[name=ADMIN_ID]').on('textchange', function(){
    		checkClear();
    	});
    }
    
    function changeRadioButton(){
    	$('.radio-w label').on('click', function(){
    		console.log("changeRadioButton");
    		$('.radio-w label').removeClass("on");
    		$(this).addClass("on");
    	});
    }
    
   
    
</script>
