<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<style type="text/css">
	.layer {display:none; position:fixed; _position:absolute; top:0; left:0; width:100%; height:100%; z-index:100;}
	.layer .bg {position:absolute; top:0; left:0; width:100%; height:100%; background:#000; opacity:.5; filter:alpha(opacity=50);}
	.layer .pop-layer-email {display:block;}
	.layer .pop-layer-sending {display:block;}

	.pop-layer-email {display:none; position: absolute; top: 50%; left: 50%; width: 450px; height:500px;  background-color:#fff; border: 5px solid #3571B5; z-index: 10;}	
 	.pop-layer-email .pop-container {padding: 20px 25px;} 
	.pop-layer-email p.ctxt {color: #666; line-height: 25px;} 
	.pop-layer-email .btn-r {width: 100%; margin:2%; padding-top: 10px; text-align:right;}
	
	.pop-layer-sending {display:none; position: absolute; top: 50%; left: 50%; width: 120px; height:60px;  background-color:#fff; border: 5px solid #3571B5; z-index: 10;}	
 	.pop-layer-sending .pop-container {padding: 20px 25px;} 
	.pop-layer-sending p {color:#000;margin-top:-5px;font-size:20px;width:100px;} 
	
	a.cbtn {display:inline-block; height:25px; padding:0 14px 0; border:1px solid #304a8a; background-color:#3f5a9d; font-size:13px; color:#fff; line-height:25px;}	
 	a.cbtn:hover {border: 1px solid #091940; background-color:#1f326a; color:#fff;} 
	
	.list-email::after{content:'';display:block;clear:both; width:100%} 
 	.list-email li{float:left;width:100%;margin-left:0%;margin-top:2%;border-bottom:1px solid #DDD} 
 	.list-email li span{display:inline-block;width:12%;vertical-align:-webkit-baseline-middle} 
	.list-email li input{width:70%;height:27px;border:0px;outline:none}
	
	.list-email .content {width:100%;border:0px;height:300px;resize:none;outline:none} 
</style>
    
<div class="layer" id="layer-email">
	<div class="bg"></div>
	<div id="pop-layer-email" class="pop-layer-email">
		<div class="pop-container">
			<div class="pop-conts">
				<form id="form-email">
					<ul class="list-email">
						<li ><span>수신자 :</span><input type="text" name="address" class="required adres" 
						title="수신자" placeholder="예)test@sample.com"></li>
						<li ><span>제목 :</span><input type="text" name="subject" 
						class="required subject" title="제목"></li>
						<li><textarea class="required content" name="content" title="내용"></textarea></li>
					</ul>
				</form>
				<div class="btn-r">
					<a href="#" onclick="sendEamil();" class="cbtn" id="addMember">전송</a>
					<a href="#" class="cbtn" id="close-layer">닫기</a>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="layer" id="layer-sending">
	<div class="bg">
		<div id="pop-layer-sending" class="pop-layer-sending">
			<div class="pop-container">
				<div class="pop-conts">
					<p>전송중...</p>
				</div>
			</div>
		</div>
	</div>
</div>