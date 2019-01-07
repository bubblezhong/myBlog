var bannerItems = $(".banner-item");
var time = 3000;
var len = bannerItems.length;
var indexNow = 0;
var itv;

for (var i = 0; i < len; i++) {
	$(".banner-nav").append('<div class="nav-item"></div>');
};
var navItems = $(".nav-item");
$(navItems[0]).addClass('nav-item-active');
itv = setInterval(autoRun, time);

function autoRun() {
	indexNow = (indexNow+1) % len;
	show(indexNow);
}

function show(index) {
	$(".banner-item").removeClass('banner-item-active');
	$(bannerItems[index]).addClass('banner-item-active');
	$(".nav-item").removeClass('nav-item-active');
	$(navItems[index]).addClass('nav-item-active');
}

$(".banner-item").mouseover(function() {
	clearInterval(itv);
})

$(".banner-item").mouseout(function() {
	itv = setInterval(autoRun, time);
})


