function selectHoleSection(hole_id, section) {
	var input; 		
	input = confirm(section + '번 구역에 개구부 비콘을 배정하시겠습니까?');
	if(input){		
		 $.ajax({
			type : "POST",
			url : "./hole/update",
			data : {
				id : hole_id,
				section : section
			},
			cache : false,
			async : false,
			success : function(json,status) {
				alert('수정이 완료되었습니다.');								
				$('#__close_btn').click();
				window.location.reload();
			}
		}); 		
	}
	else{			
		alert('취소하었습니다');
	}
}

function cancelHoleBeacon(hole_id) {
	var input; 		
	input = confirm(hole_id + '번 개구부 할당을 취소하시겠습니까?');
	if(input){		
		 $.ajax({
			type : "POST",
			url : "./hole/unAssign",
			data : {
				id : hole_id
			},
			cache : false,
			async : false,
			success : function(json,status) {
				alert('배정 삭제가 완료되었습니다.');		
				window.location.reload();
			}
		}); 		
	}
	else{			
		alert('취소되었습니다');
	}
}

function updateHoleName(hole_id, name){
	var name;
	name = prompt('변경할 이름을 적어주세요');   
	
	$.ajax({
		type : "POST",
		url : "./hole/name/update",            
		data : {
			id : hole_id, 
			name : name
		},
		async: true,
		cache : false,         
		success : function(json, status){
			alert('이름 변경 완료되었습니다.');		
			window.location.reload();
		}
	});
}