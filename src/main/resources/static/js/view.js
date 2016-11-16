function DocinReaderPlayer() {
    this.config = {
        productId: 0,
        allPage: 0,
        flashUrl: ""
    };

    if (arguments.length == 1)
        var arg = arguments[0];
    for (var p in arg)
        this.config[p] = arg[p];

    this.preBtn = jQuery("#prePageButton"),
	this.nextBtn = jQuery("#nextPageButton");
    this.gotoBtn = jQuery("#gotoPageButton");
    this.btn_zoomIn = jQuery("#zoomInButton");
    this.btn_zoomOut = jQuery("#zoomOutButton");
    this.btn_fullscreen = jQuery(".fullscreen");
    this.oToolBar = jQuery(".reader-tools-bar");
    this.zoomFlag = 0;
    this.curPage = 1;
    this.toolHeight = 37;
    this.iWidth = [928, 1130];
    this.iHeight = [];
}

DocinReaderPlayer.prototype = {
    init: function () {
        var _this = this;
        _this.prePage();
        _this.nextPage();
        _this.jumpToPage();
        _this.fullscreen();
        _this.zoomIn();
        _this.zoomOut();
        jQuery(window).bind('scroll', function () {
            _this.setToolBarFix();
        });
    },
    gotoPage: function (pageno) {
        var _this = this;
        if (jQuery("#page_" + pageno).length > 0) {
            var curObjTop = $("#page_" + pageno).offset().top - _this.toolHeight;
            jQuery(window).scrollTop(curObjTop);
        }
    },
    jumpToPage: function () {
        var _this = this;
        _this.gotoBtn.bind('click', function () {
            var v = jQuery("#pageNumInput").val();
            _this.curPage = parseInt(v);
            if (_this.curPage > _this.config.allPage) {
                _this.curPage = _this.config.allPage;
                return;
            }
            else if (_this.curPage < 1) {
                _this.curPage = 1;
                return;
            }
            _this.gotoPage(_this.curPage);
            _this.pageButton(_this.curPage);
        });
    },
    prePage: function () {
        var _this = this;
        _this.preBtn.bind('click', function () {
            _this.curPage--;
            console.info(_this.curPage)
            if (_this.curPage < 1) {
                _this.curPage = 1;
                return;
            }
            _this.gotoPage(_this.curPage);
            _this.pageButton(_this.curPage);
        });
    },
    nextPage: function () {
        var _this = this;
        _this.nextBtn.bind('click', function () {
            _this.curPage++;
            if (_this.curPage > _this.config.allPage) {
                _this.curPage = _this.config.allPage;
                return;
            }
            _this.gotoPage(_this.curPage);
            _this.pageButton(_this.curPage);
        });
    },
    pageButton: function (pageno) {
        var _this = this;
        if (pageno) {
            if (_this.config.allPage == 1) {
                _this.preBtn.attr("class", "no-prev");
                _this.nextBtn.attr("class", 'no-next')
            }
            else if (pageno == 1) {
                _this.preBtn.attr("class", "no-prev");
                _this.nextBtn.attr("class", 'nextsmall');
            }
            else if (pageno == _this.config.allPage) {
                _this.preBtn.attr("class", "prevsmall");
                _this.nextBtn.attr("class", 'no-next')
            }
            else {
                _this.preBtn.attr("class", "prevsmall");
                _this.nextBtn.attr("class", 'nextsmall');
            }
        }
    },
    addFlash: function (pageno) {
        var _this = this;
        for (var p = pageno - 1; p <= pageno + 1; p++) {
            if (p != 0 && jQuery("div#swf_" + p).length > 0) {
                var flashvars = { productId: readerConfig.productId, pagenum: p - 1 }, params = { wmode: 'transparent' }, attributes = {};
                swfobject.embedSWF(readerConfig.flashUrl + pageno+".swf", "swf_" + pageno, '100%', '100%', "9.0.0", "", flashvars, params, attributes);
            }
        }
    },
    zoomOut: function () {//缩小
        var _this = this;
        if (_this.btn_zoomOut.length > 0) {
            _this.btn_zoomOut.bind('click', function () {
                if (_this.zoomFlag == 2)
                    _this.zoomInStyle(1);
                else if (_this.zoomFlag == 1)
                    _this.zoomInStyle(0);
            });
        }
    },
    zoomIn: function () {//放大
        var _this = this;
        if (_this.btn_zoomIn.length > 0) {
            _this.btn_zoomIn.bind('click', function () {
                if (_this.zoomFlag==0)
                    _this.zoomInStyle(1);
                else if (_this.zoomFlag == 1)
                    _this.zoomInStyle(2);
            });
        }
    },
    zoomInStyle: function (f) {
        var _this = this;
        if (f == 0) {//始初状态
            jQuery("#frscreen").attr("class", "fullscreen").attr("title", "全屏显示");
            jQuery(".content .mainpart").removeClass("full");
            jQuery("#mainPanel").removeClass("full");
            jQuery(".inner_page").attr("style", "");
            _this.btn_zoomOut.addClass("no-zooms").removeClass("zooms");
            _this.btn_zoomIn.addClass("zoomb").removeClass("no-zoomb");
            _this.gotoPage(_this.curPage);
            _this.zoomFlag = 0;
        }

        if (f == 1) {//半全屏
            var mainpart = jQuery(".content .mainpart");
            mainpart.removeClass("full");
            jQuery("#mainPanel").addClass("full");
            jQuery(".inner_page").css({ height: parseInt(1200 * (jQuery(".inner_page").width() / 858)) + 'px' });
            _this.btn_zoomOut.addClass("zooms").removeClass("no-zooms");
            _this.btn_zoomIn.addClass("zoomb").removeClass("no-zoomb");
            _this.gotoPage(_this.curPage);
            _this.zoomFlag = 1;
        }

        if (f == 2) {//全屏
            var mainpart = jQuery(".content .mainpart");
            mainpart.addClass("full");
            jQuery("#frscreen").attr("class", "escscreen").attr("title","退出全屏");
            jQuery("#mainPanel").addClass("full");
            jQuery(".inner_page").css({ height: parseInt(1200 * (jQuery(".inner_page").width() / 858)) + 'px' });
            _this.btn_zoomIn.addClass("no-zoomb").removeClass("zoomb");
            _this.btn_zoomOut.addClass("zooms").removeClass("no-zooms");
            _this.gotoPage(_this.curPage);
            _this.zoomFlag = 2;
        }
    },
    fullscreen: function () {
        var _this = this;
        _this.btn_fullscreen.bind('click', function () {
            if (jQuery("#frscreen").attr("class") == "fullscreen")
                _this.zoomInStyle(2);
            else //退出全屏
                _this.zoomInStyle(0);
        });
    },
    setToolBarFix: function () {
        var _this = this;
        if (!window.XMLHttpRequest) { return; }
        var st = jQuery(window).scrollTop();
        
    }
};
var log_preNum = 0;
var scrollFlag = true;

var docinReader = new DocinReaderPlayer(readerConfig);

(function ($) {
    $.fn.scrollLoading = function (options) {
        var defaults = {};
        var params = $.extend({}, defaults, options || {});
        params.cache = [];
        $(this).each(function () {
            //重组
            var data = { obj: $(this) };
            params.cache.push(data);
        });

        //动态显示数据
        var loading = function () {
            var st = $(window).scrollTop(), sth = st + $(window).height();
            // var ddd = [];//记录页码
            $.each(params.cache, function (i, data) {
                var o = data.obj, tag = data.tag, url = data.url;
                if (o) {
                    post = o.offset().top; posb = post + o.height();
                    var a = (post > st) && ((post - st) < $(window).height() / 2);
                    var b = (posb < sth) && ((posb - st) > $(window).height() / 2);
                    var c = (post < sth) && (posb > sth) && (docinReader.curPage == 1);
                    if (a || b || c) {
                        var t = i + 1;
                        if (log_preNum != t) {
                            docinReader.addFlash(t);
                            jQuery('#pageNumInput').val(t);
                            docinReader.curPage = t;
                            docinReader.pageButton(t);
                            log_preNum = t;
                        }
                    }
                }
            });
            scrollFlag = true;
            return false;
        };
        //事件触发
        //加载完毕即执行
        if (docinReader.curPage == 1) {
            loading();
        }
        //loading();
        //滚动执行
        $(window).bind("scroll", function () {
            if (scrollFlag == true) {
                setTimeout(function () { loading(); }, 100);
                scrollFlag = false;
            }
        });
    };
})(jQuery);


$(document).ready(function () {
	$(".scrollLoading").scrollLoading();
    docinReader.init();
});