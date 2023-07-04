//https://wiki.videolan.org/VLC_command-line_help/
/* actions */

function doGo(targetObj, targetURL , width, height)
{

 var vlc = document.getElementById(targetObj);

    var options = new Array(":vout-filter=deinterlace", ":deinterlace-mode=linear");
    
    if(typeof vlc.playlist == 'undefined') return;
    
    //--avcodec-hw=<string> -disable //https://www.youtube.com/watch?v=N3Nf7Zjm3wQ
   // var options = new Array(":vout-filter=deinterlace", ":deinterlace-mode=linear", ":network-caching=300", ":swscale-mode=0", "--sout-transcode-vb=10","dash-buffersize=0");
	if (vlc) {
		vlc.style.width = '100%';
		vlc.style.height = '20vh';
	}
	
    vlc.playlist.clear();
    vlc.playlist.add(targetURL, "live", ":network-caching=300");
  //  vlc.playlist.add(targetURL);
	//vlc.playlist.add(targetURL, "live", ":network-caching=10000");//bitrate 조절
    vlc.playlist.play();
   /*  if( monitorTimerId == 0 )
    {
	monitor();
    } */
}
/*function vlc_pause(targetURL, targetObj)
{
    var vlc = document.getElementById(targetObj);
    var options = new Array(":vout-filter=deinterlace", ":deinterlace-mode=linear");
    vlc.playlist.clear();
    //vlc.playlist.add(targetURL, null, options);
    vlc.playlist.add(targetURL);
    vlc.playlist.pause();
   
};*/

function vlc_pause(targetObj)
{
    var vlc = document.getElementById(targetObj);
 
    vlc.playlist.pause();
   
};

function vlc_start(targetObj, targetURL)
{
	
    var vlc = document.getElementById(targetObj);
    

	//console.log(targetObj + ' : ' + vlc.playlist);
	console.log(new Date() + targetObj + '- isplaying :' + vlc.playlist.isPlaying);
    if(vlc.playlist.isPlaying){//세팅이 된상태면 isPlaying true
    	vlc.playlist.play();	
    }else{
		console.log(new Date() + targetObj + ' = [doGO start] ');
    	doGo(targetObj, targetURL);
    }
    
   
};













/**jwplayer**/


function playerJWPlayer(div_id, src){
	var playerInstance = jwplayer(div_id);
	playerInstance.setup({
	    file: src,
	    width: "100%", //350 * 300 비율로 하면 로고좀숨겨짐?
		height: "100%",
		primary: "html",
		autostart:'true',
		logo: {
			/* file: 'images/kepco/status_bar.png',
			link: '', */
			hide : 'true',
			position : 'top-left'
			
		},
		bitrate:50
	});
}

function playerJWPlayer_6(div_id, src){
	jwplayer(div_id).setup({
		file: src ,
		width: "100%", //350 * 300 비율로 하면 로고좀숨겨짐?
		height: "100%",
		primary: "html",
		controlbar:'none',
		autostart:'true',
		mute : 'true',
		bitrate : 500,
		showicons : 'true',
		logo: {
			/* file: 'images/kepco/status_bar.png',
			link: '', */
			hide : 'true',
			position : 'top-left'
			
		},
		//bufferlength:'2',
	});
	
	
}




