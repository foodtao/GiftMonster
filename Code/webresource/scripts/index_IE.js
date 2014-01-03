/* Turn Around */
$('.rec-product').mouseenter(function() {
	$(this).children('.back').animate({
		marginLeft: '180px'
	},300).end().children('.front').animate({
		marginLeft: '0'
	},300);
}).mouseleave(function() {
	$(this).children('.front').animate({
		marginLeft: '-180px'
	},300).end().children('.back').animate({
		marginLeft: '0'
	},300);
});;