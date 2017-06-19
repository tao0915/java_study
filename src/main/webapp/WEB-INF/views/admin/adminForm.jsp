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
        	<input type="text"  name="TEL_NO"  value=""  maxlength="12" class="text"/>
        </td>
        <th>휴대폰번호</th>
        <td class="input-wrap">
        	<input type="text"  name="MOBILE_NO"  value=""  maxlength="12" class="text"/>
        </td>
      </tr>

      <tr>
        <th>email</th>
        <td class="input-wrap" colspan="3">
        	<input type="text"  name="EMAIL"  value=""  maxlength="20" class="text"/>
        </td>
      </tr>

      <tr>
        <th>권한그룹</th>
        <td class="input-wrap" colspan="3">
        	<span class="radio-w">
	        	<c:forEach items="${authList }"  var="i">
					<c:choose>
						<c:when test="${!empty data['AUTH_ID'] and  data['AUTH_ID'] eq i.AUTH_ID}"><c:set var="checked" >checked="checked"</c:set></c:when>
						<c:otherwise><c:set var="checked"  value=""/></c:otherwise>
					</c:choose>
			            <label <c:if test="${not empty checked}">class="on"</c:if>>
			            	<input type="radio"  value="${i.AUTH_ID }"  name="AUTH_ID"  ${checked }  class="required"  title="권한그룹"/><c:out value="${i.AUTH_NM }"/>
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
		<a class="btn-type4" href="/admin" onclick="">저장</a>
	</div>
</div>

<script type="text/javascript">
	function adminSave(){
		if(!$('#frm').validateForm()){ return false; }

		if($('#idChkYn').val() === 'N') {
			alert('아이디 중복 체크를 해주셔야 합니다.');
			return false;
		}
	}

	function saveSuccess(){
		alert('저장되었습니다. \n목록 페이지로 이동하겠습니다.');
	}

    //아이디 중복 체크
	function adminIdChk(){
		//TODO
	}

    //id 중복 체크 성공시
	function callback(data){
		alert('사용가능한 아이디 입니다.');
		$('#idChkYn').val('Y');
	}
</script>
