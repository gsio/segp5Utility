//requireJS 기본 설정 부분
requirejs.config({
    baseUrl: 'js', // 'js' 라는 폴더를 기본 폴더로 설정한다. 
    paths:{
        //뒤에 js 확장자는 생략한다.
        //'text': '../lib/require/text',
    	'google_map_api' : 'http://maps.googleapis.com/maps/api/js?key=AIzaSyDCBYBnJzA2MCziYxAK8QtD8zDvzTta-Vg&signed_in=false'
    	,'jquery1.12.4' : 'jquery-1.12.4.min'
    	,'jqueryplugin' : 'jquery.plugin'
    	,'bootstrap' : 'bootstrap.min'
    	,'chartJs' : 'monitor/Chart'
    	,'markercluster' : 'monitor/markerclusterer'	
    	,'infobox' : 'monitor/infobox'	
    	,'totalmapgang' : 'monitor/total_map_gang'	
    
    },

/*
    shim:
    AMD 형식을 지원하지 않는 라이브러리의 경우 아래와 같이 shim을 사용해서 모듈로 불러올 수 있다.
    참고 : http://gregfranko.com/blog/require-dot-js-2-dot-0-shim-configuration/
*/
    shim:{
    	'jquery1.12.4':{
    		exports: '$'
    	},
    
    	'jqueryplugin':{
            deps: ['jquery']
    		
    	},
    	'bootstrap':{
    		deps: ['jquery']
    	},
    	'chartJs':{
    		deps: ['jquery']
    	},
    	'markercluster':{
    		deps: ['jquery']
    	},
    	'infobox':{
    		deps: ['jquery', 'google_map_api']
    	},
    	'totalmapgang':{
    		deps: ['jquery']
    	}
      /*  'angular':{
            deps: ['jquery'], //angular가 로드되기 전에 jquery가 로드 되어야 한다.
            exports:'angular' //로드된 angular 라이브러리는 angular 라는 이름의 객체로 사용할 수 있게 해준다
        }*/
    }
    // 모듈 위치 URL뒤에 덧붙여질 쿼리를 설정한다.
    // 개발 환경에서는 브라우저 캐시를 회피하기 위해 사용할 수 있고, 
    // 실제 서비스 환경이라면 ts값을 배포한 시간으로 설정하여 새로 캐시하게 할 수 있다.
    //,urlArgs : 'ts=' + (new Date()).getTime()
});
requirejs( ['jquery1.12.4','jqueryplugin','chartJs','bootstrap','markercluster','infobox','totalmapgang'],
	    function ($, jqueryplugin, chartJs, bootstrap, markercluster,infobox, totalmapgang) {
	   	$(document).ready(function () {
	     	
	 		//var jQuery = $; // for jquery
	 	
		 	checkAgent();//agent 세팅
			
			//setDivSize();//
			setInitFrame();//mobile여부에 따른 구조 초기화
			
			initMap();//google map 초기화
			
			if(MOBILE_TYPE == 0){
				try{
					chart1 = make_chart('chart_1','근로자',5, 2, chart_color[1]); //dfe2e7 배경색
					chart2 = make_chart('chart_2','장비',5, 2,chart_color[2]) ; //dfe2e7 배경색
					chart3 = make_chart('chart_3','취약개소',5, 2, chart_color[3]); //dfe2e7 배경색
				}catch(err){
					console.log(err);
				}
				window.setInterval(updateChartAll, 2000); //chart 갱신
			}
			
			 getSiteList(-1);//현장정보
			 window.setInterval(getSiteList(3), 5000);
		
			  
			// console.log('after : ' +  window.$);
			
		    $(window).on('load', function () {
		    	   /*if ($('div#media-320').css('display') == 'block') responsive = 1;
		    	    else if ($('div#media-768').css('display') == 'block') responsive = 2;
		    	    else if ($('div#media-1024').css('display') == 'block') responsive = 3;
		    	    else if ($('div#media-1025').css('display') == 'block') responsive = 0;
		    	    else responsive = 4;
		    	    */
		    	//    console.log(responsive);
		    });
		      
		    $(window).on('resize', function () {
		    	 /*  if ($('div#media-320').css('display') == 'block') responsive = 1;
		    	    else if ($('div#media-768').css('display') == 'block') responsive = 2;
		    	    else if ($('div#media-1024').css('display') == 'block') responsive = 3;
		    	    else if ($('div#media-1025').css('display') == 'block') responsive = 0;
		    	    else responsive = 4;
		    	    
		    	    console.log(responsive);*/
		    });
		    
		    $(window).resize(function(){	
		    	//totalmapgang.setDivSize();
		    	$('#monitorCont').height(window.innerHeight  * 0.9);
		    	//$('#modal-iframe').height(window.innerHeight * 0.85);
		    	$('.modal-dialog').height(window.innerHeight * 0.85);
		    
		    }).resize();

			 
			
	    });

	});
