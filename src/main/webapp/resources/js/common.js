/**
 * 공통 모듈
 */
$.common = {
		// 서버측으로부터 전달받은 JSON 오브젝트에 에러메세지가 있는 경우 alert를 발생시키고 false를 리턴 시킴
		ajaxValidate : function(result, isAlert){
			var isAlert = typeof(isAlert) == "boolean" ? isAlert : true;
			
			//console.log(result);
			
			if(result.result){ result = result.result; }
			
			if(result.resultCode && result.resultCode != 1){
				// 인증 오류인 경우의 처리
				if(result.resultCode == 500 && result.loginUrl && result.redirectUrl){
					location.href = result.loginUrl + "?redirectUrl=" + result.redirectUrl;
					return false;
				}
				
				
				// message에 정보가 존재하는 경우
				if(result.message){
					if(isAlert){ alert(result.message); }
					return false;
				}
				
				// messageList에 정보가 존재하는 경우
				if(result.messageList){
					if(isAlert){ alert(result.messageList.join("\n")); }
					return false;
				}
				
				// 모든 메세지가 없는 경우에 대한 처리
				return false;
			}else{
				if(typeof result === 'string' && result.indexOf('loginForm') > -1){
					if(menuObj && $('#'+menuObj.defaultOption.bodyTargetId).length){
						$('#'+menuObj.defaultOption.bodyTargetId).empty();
						$('#'+menuObj.defaultOption.bodyTargetId).html(result);
						return false;
					}
				}
			}

			return true;
		},
		
		
		//폼 필수 값 체크
		validateForm:  function(options){
			var defaults = {
					form	: null
			};
			
			var form = $(this).get(0);
			if(form.tagName.toLowerCase() != "form"){  alert("FORM 오브젝트가 아닙니다.");  return false; }
			defaults.form = form;
			
			options = $.extend(true, defaults, $.common.validateFormDefaults, options);
			
			if(options.form == null){ alert("유효성 체크를 할 입력폼이 유효하지 않습니다."); return false; }
			
			// 유효성 체크 처리
			var f = $(options.form);
			var isValidate = true;
			options.chkResult = {};
			f.data("validate_options", options);
			
			
			f.find("input,select,textarea").each(function(idx){
				if(!$.common.validateElement(f, this)){ isValidate = false; }
			});
			
			// 유효성 체크 결과 노출
			if(!isValidate){ $.common.showMsg(f); }
			
			return isValidate;
			
		},
		
		// 해당 폼 구성 요소의 유효성 검사
		validateElement : function(target, e){
			var options = target.data("validate_options");
			var chkResult = options.chkResult;
			var rv = true;

			// 유효성 체크
			if($(e).hasClass("required")){
				if(!chkResult[e.name]){ chkResult[e.name] = {}; }
				chkResult[e.name]["required"] = "";
				isValidateElement = true;

				if(!$.common.checkRequired(e)){
					chkResult[e.name]["required"] = $.common.getMsg(options, "required", e);
					options.chkResult = chkResult;
					target.data("validate_options", options);
					rv = false;
				}
			}
			
			if($(e).hasClass("onlynum")){
				if(!chkResult[e.name]){ chkResult[e.name] = {}; }
				chkResult[e.name]["onlynum"] = "";
				isValidateElement = true;

				if(!$.common.checkOnlyNum(e)){
					chkResult[e.name]["onlynum"] = $.common.getMsg(options, "onlynum", e);
					options.chkResult = chkResult;
					target.data("validate_options", options);
					rv = false;
				}
				
				if($(e).hasClass("zeroCheck")){	
					if(rv && $.trim(e.value) == '0'){
						chkResult[e.name]["zeroCheck"] = $.common.getMsg(options, "zeroCheck", e);
						options.chkResult = chkResult;
						target.data("validate_options", options);
						rv = false;					
					}
				}
			}		
			return rv;
		}, 
		
		
		// 폼 유효성 체크 기본값들...
		validateFormDefaults : {
			msgType : "alert",				// 메세지 형식 [dialog|alert] 
			chkClass : ["required"],		// 이항목은 현재 미사용중임. 
			chkMsg : {
				"required"	: "[{0}]를 입력해 주세요.",				// title attribute로 대체함.
				"onlynum"	: "[{0}] 항목은 숫자만 입력이 가능합니다.",		// title attribute로 대체함.
	            "zeroCheck"	: "[{0}] 항목이 입력되지 않았습니다."		 	// title attribute로 대체함.
			}
		},
		
		// 필수 입력값 체크
		checkRequired : function(obj){
			var val = "";
	
			switch(obj.tagName.toLowerCase()){
				case "input" : 
					switch(obj.type.toLowerCase()){
						case "hidden"	: val = obj.value; break;
						case "text"		: val = obj.value; break;
						case "password"	: val = obj.value; break;
						case "radio"	: 
							val = $("input[name=" + obj.name + "]:checked").val() ? $("input[name=" + obj.name + "]:checked").val() : "";
							break;
						default :
							alert(obj.type.toLowerCase() + " 항목에 대한 정의가 필요함");
					}
					break;
				case "select" :		val = obj.value; break;
				case "textarea" :	val = obj.value; break;
				default :
					alert(obj.tagName.toLowerCase() + "항목에 대한 필수값 체크 로직이 누락되어 있음.");
					return false;
			}
			return !($.trim(val) == "");
		},
		
			// 숫자 입력 값 체크
	checkOnlyNum : function(obj){
		var val = "";

		switch(obj.tagName.toLowerCase()){
			case "input" : 
				switch(obj.type.toLowerCase()){
					case "text" : val = $.trim(obj.value); break; 
				}
				break;
			default : return true;
		}
		
		if(val == ""){ return true; }
		else{
			return !isNaN(val.replace(/[.]/g, ''));
		}
	}, 
		
		
		
		// 메세지 노출
		showMsg : function(target){
			$.common.showMsgAlert(target);
		}, 
		
		
		// 메세지 노출 - alert
		showMsgAlert : function(target){
			var options = target.data("validate_options");
			var chkResult = options.chkResult;

			for(var name in chkResult){
				for(var key in chkResult[name]){
					if(chkResult[name][key] != ""){
						alert(chkResult[name][key]);
						$("input[name=" + name + "]").focus();
						return;
					}
				}
			}
		},
		
		// 메세지 반환
		getMsg : function(options, key, obj){
			var msg = options.chkMsg[key];
			var title = $(obj).prop("title") == "" ? obj.name : $(obj).prop("title");

			switch(key){
				case "required" :
				case "zeroCheck":
				case "onlynum" : 
					msg = typeof($(obj).prop(key)) == "string" ? $(obj).prop(key) : msg.replace(/\{0\}/gi, title); 
					break;
			}

			return msg;
		},
		
		// 해당 폼의 값을 JSON으로 리턴함.
		serializeObject : function(){
			var o = {};
			var a = this.serializeArray();
			
			$.each(a, function() {
				if (o[this.name] !== undefined) {
					if (!o[this.name].push) {
						o[this.name] = [o[this.name]];
					}

					o[this.name].push(this.value || '');
				} else {
					o[this.name] = this.value || '';
				}
			});
			
			return o;
		}
};

(function($) {
	$.fn.validateForm		= $.common.validateForm;
	$.fn.serializeObject	= $.common.serializeObject;	
})(jQuery);


//ajax 글로벌 함수 등록 (로딩바 처리)
$(document).ready(function(){
	/*$(document).keydown(function (e) {
        return (e.which || e.keyCode) != 116;
    });*/
	$(document).ajaxStart(function(){console.log('ajaxStart ==================  '); loadingBarStart();});
	$(document).ajaxStop(function(){console.log('ajaxStop ==================  '); loadingBarStop();});
});


function loadingBarStart(){
	if($('div.body').length != 0){
		if($('#loadingBar').length == 0){
		    $('div.body').before('<div id="layerbox" style="display: block; position: fixed; width: 100%; height: 100%; z-index: 6000; background-color: #000;"></div>');   //레이어 박스 생성
		    $('#layerbox').css('opacity','0');                                 //레이어 배경색 투명도 조정
		    $('#layerbox').before('<div id="loadingBar" style="display: block;  position: absolute; z-index: 20000;">로딩중.....</div>');       //정보영역 레이어 박스 생성
		    
			var top	 = $(window).scrollTop()+($(window).height()-$('#loadingBar').outerHeight())/2 - 64;
			var left = ($(window).width()-$('#loadingBar').outerWidth())/2 - 64;	  
		
			$('#loadingBar').css('top',top);
			$('#loadingBar').css('left',left);			
		}
	}
}
	
function loadingBarStop(){
	if($('#loadingBar').length != 0){
		$('#layerbox').remove();
		$('#loadingBar').remove();
	}
}



