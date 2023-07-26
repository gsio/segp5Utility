<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>

<script type="text/javascript" src="js/map/google_map.js"></script>
<script src="https://unpkg.com/@google/markerclustererplus@4.0.1/dist/markerclustererplus.min.js"></script>

<script>

	$(document).ready(function() {
	
	
		
	});

</script>

<style>	

.map_wrapper {
		min-height: 500px;
	}

	#map {
		margin: 0 auto;
		width: 100%;
	    max-width: 100%;
	    height: 100%;
	    margin-top: 40px;
	    margin-bottom: 30px;
	    border: 1px solid rgba(0,0,0,.2);
	    box-shadow: 0 0 20px rgba(0,0,0,.2);
	    box-sizing: border-box;
	    position: relative;
	}
	
	.my-location {
		background-color: #fff;
	    border: 0;
	    border-radius: 2px;
	    box-shadow: 0 1px 4px -1px rgba(0, 0, 0, 0.3);
	    margin: 10px;
	    padding: 0 0.5em;
	    font: 400 18px Roboto, Arial, sans-serif;
	    overflow: hidden;
	    height: 40px;
	    width: 40px;
	    cursor: pointer;
	    background-image: url(./images/icons/ic_my_location.png) !important;
	    background-size: 100%;
	    background-repeat: no-repeat;
	}
	
	#map .gm-style > div:nth-child(15), #map .gm-style > div:nth-child(17) {
		display: none !important;
	}
	
</style>

<div id="content-wrapper">
	<div id="content_title" class="content-item">테스트</div>
	<div id="map"></div>
	<input id="refreshMap" type="button" value="Refresh" style="display:none" /><br>
	
</div> <!-- content-wrapper END -->

<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAAOvcGtJyyjj093KMYxvt_ZwyycaxceZA&callback=initMap"></script>



<%@ include file="IncludeBottom.jsp"%>