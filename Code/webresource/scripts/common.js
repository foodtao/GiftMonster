var isChildOf = function(parentEl, el, container) {
		if(parentEl == el) {
			return true;
		}
		if(parentEl.contains) {
			return parentEl.contains(el);
		}
		if(parentEl.compareDocumentPosition) {
			return !!(parentEl.compareDocumentPosition(el) & 16);
		}
		var prEl = el.parentNode;
		while(prEl && prEl != container) {
			if (prEl == parentEl)
				return true;
			prEl = prEl.parentNode;
		}
		return false;
	},
/* Go To Top */
	scrollHandle = function() {
		var scrollTop = +(document.documentElement.scrollTop || document.body.scrollTop);
		if(scrollTop > 300) {
			$('.go-top').animate({
				opacity: 1
			},100);
		}
		else {
			$('.go-top').animate({
				opacity: 0
			},100)
		}
	},
	goTopHandle = function() {
		document.documentElement.scrollTop = document.body.scrollTop = 0;
	},
/* End */
/* Assortment */
	showAssortment = function(event) {
		if(!isChildOf($('.assortment')[0], event.target)) {
			$('.assortment').animate({
				left: '-300px'
			},200);
		}
		else if(event.target.className == 'btn-assortment' || isChildOf($('.btn-assortment')[0], event.target)) {
			$('.assortment').animate({
				left: $('.assortment')[0].style.left == '-300px' ? 0 : '-300px'
			},200);
		}
		else {
			$('.assortment').animate({
				left: 0
			},200);
		}
	},
	showSecondAsso = function() {
		for(var i = 0; i < $('.pro-second-list').length; i++) {
			if($('.pro-second-list')[i].style.display != 'none') {
				$('.pro-second-list').eq(i).animate({
					height: 'toggle'
				},200)
			}
		}
		if($(this).next()[0].style.display == 'none') {
			$(this).next().animate({
				height: 'toggle'
			},200)
		}
	},
/* End */
/* Search Hover Mark */
	showSearchMark = function(event) {
		var pageFlip = $(this).find($('.page-flip'));
		if(event.target.nodeName.toLowerCase() == 'img') {
			pageFlip.animate({
				width: '50px',
				height: '52px'
			}, 200);
		}
		else if(event.target.className == 'page-flip') {
			pageFlip.css({ 'width': '50px', 'height': '52px' })
		}
		else {
			pageFlip.animate({
				width: 0,
				height: 0
			}, 200);
		}
	},
	hideSearchMark = function(event) {
		var pageFlip = $(this).find($('.page-flip'));
		pageFlip.animate({
			width: 0,
			height: 0
		}, 200);
	};
/* End */
$('.assortment')[0].style.left = '-300px';
$(window).bind({
	scroll: scrollHandle,
	click: showAssortment
});
$('.go-top').bind('click', goTopHandle);
$('.pro-first-class').bind('click', showSecondAsso);
$('.search-list li').bind('mouseover', showSearchMark).bind('mouseleave', hideSearchMark);