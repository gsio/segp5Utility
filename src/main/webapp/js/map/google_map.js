let map;
let markerCluster;
let markers = [];
let markersAmount;
let infoWindow;

// 마커 좌표 리스트
let latlng_list =  [
                {lat: 37.51932559999999, lng: 126.8371642},
                {lat: 37.484085, lng: 126.782803},
                {lat: 37.484085, lng: 126.882803},
                {lat: 36.51932559999999, lng: 127.8371642},
                {lat: 36.484085, lng: 127.782803},
                {lat: 36.484085, lng: 127.882803}
            ]

// 구글맵 초기화 
function initMap() {
    const myLatLng = {
        lat: 37,
        lng: 126
    }
    
    map = new google.maps.Map(
        document.getElementById("map"), {
            center: myLatLng,
            scrollwhell: false,
            zoom: 6,
            minZoom: 6,
            restriction: {
                latLngBounds: {
                	north: 43,
                	south: 33.1,
                	east: 131.87,
                	west: 124.19,
                },
            },
        }
    );
    
	infoWindow = new google.maps.InfoWindow({
	    content: "",
	    disableAutoPan: true,
	});
    
    const locationButton = document.createElement("button");
    locationButton.classList.add("my-location");
    map.controls[google.maps.ControlPosition.LEFT_BOTTOM].push(locationButton);
    
    locationButton.addEventListener("click", () => {
        if (navigator.geolocation) {
        	navigator.geolocation.getCurrentPosition(
        		(position) => {
        			const pos = {
        				lat: position.coords.latitude,
        				lng: position.coords.longitude,
        			};
        			
        			let myPosition = new google.maps.Marker({
        				position: pos,  
        				map        			
        			});
        			
        		  	infoWindow.setContent('내 위치');
        		  	infoWindow.open(map, myPosition);
        			map.panTo(pos);
        			map.setCenter(pos);        			
        			
        			refreshMap();
        		
        		},
        		() => {
        			handleLocationError(true, infoWindow, map.getCenter());
        		}
        	);
        } 
        else {
        	handleLocationError(false, infoWindow, map.getCenter());
        }
    });
    
    refreshMap();
}

// 마커 생성함수
function createMarkers() {	
	
	const labels = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    for (let i = 0; i < latlng_list.length; i++) {
    	let label = labels[i % labels.length];
    	let location = latlng_list[i].lat + "," + latlng_list[i].lng;
        let mker = new google.maps.Marker({
            position: latlng_list[i],
            label,
            map,
            animation: google.maps.Animation.DROP,
        })        
     
        mker.addListener("click", () => {
        	map.panTo(mker.position);
        	infoWindow.setContent(location);
        	infoWindow.open(map, mker);
        });        

        markers.push(mker);
    }
    
    // 클러스터링
    markerCluster = new MarkerClusterer(map, markers,{
        imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m',
        gridSize: 100
    });
}

function refreshMap() {
    if (markerCluster instanceof MarkerClusterer) {
        // Clear all clusters and markers
        markerCluster.clearMarkers()
    }
    markers = [];
    createMarkers();
}
