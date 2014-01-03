/* Product Picture */
var switchPic = function(event) {
	var imageUrlS = event.target.src,
		imageUrlL = imageUrlS.slice(0, -8) + '260260.jpg';
	$('.big-pic img')[0].src = imageUrlL;
	$('.small-pic-list a').removeClass('current');
	$(this).addClass('current');
}
$('.small-pic-list a').bind('click', switchPic);