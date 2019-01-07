var colors = 'ff859d 61a2af 68c3d9 857282 d5b7f8 f6c7a0 81c784 80cbc4 4fc3f7 7986cb 7e57c2 ef9a9a 607d8b ff9e80'.split(' ');

var tags = $(".tags-box a");
for (var i = 0; i < tags.length; i++) {
  $(tags[i]).css('background', '#' + colors[Math.floor(Math.random() * colors.length)])
};

$('.menu-icon').click(function() {
	$('.sidebar-box').css('transform', 'translateX(0)');
})
$('.back-icon').click(function() {
	$('.sidebar-box').css('transform', 'translateX(-100%)');
})
if ($(window).width() < 780) {
	$('.sidebar-box').click(function() {
		$('.sidebar-box').css('transform', 'translateX(-100%)');
	})	
};

// 获取页面元素
var myElement = document.querySelector(".header-fixed");
// 创建 Headroom 对象，将页面元素传递进去
var headroom  = new Headroom(myElement, {
	"tolerance": 10,
	"offset": 55,
});
// 初始化
headroom.init();

// var page = 1, pageSize = 10, hasMore = true, loading = false;
// $('.loadmore').click(function(){
// 	if(hasMore && !loading){
// 		this.innerHTML = "加载中...";
// 		loading = true;
// 		$.ajax({
// 			url: '/api/article',
// 			method: 'get',
// 			data: {
// 				page: ++page,
// 				pageSize: pageSize
// 			},
// 			success: function(res){
// 				var html = [];
// 				if(res.data.list){
// 					var html = res.data.list.map(function(article, index){
// 						return (
// 							"<li>" +
// 							"<a href='/article/"+ article.articleId +"'>" +
// 							"<img  src='" + article.cover + "' alt='" + article.title + "' />" +
// 							"</a>" +
// 							"<h3>" +
// 							"<a href='/article/"+ article.articleId +"' class='title'>" + article.title + "</a>" +
// 							"</h3>" +
// 							"<div class='info'>" +
// 							"<a href='/category/" + article.categoryId + "' class='category'>" + article.categoryName + "</a>" +
// 							"<time>&nbsp;·&nbsp; <span>" + article.createTime + "</span>&nbsp;·&nbsp; <span>" + article.viewNum + "</span>  次查看</time>" +
// 							"</div>" +
// 							"</li>"
// 						)
// 					})
// 					console.log(html.join('\n'));
// 					$('.article-list').append(html.join('\n'));
// 					$('.loadmore').html("查看更多");
// 					loading = false;
// 				}
// 				if(res.data.list.length < pageSize){
// 					hasMore = false;
// 					loading = false;
// 					$('.loadmore').html("已没有更多内容");
// 				}
// 			}
// 		})

// 	}
// })

