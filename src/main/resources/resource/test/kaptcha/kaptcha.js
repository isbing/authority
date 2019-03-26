;
(function ($, window, document, undefined) {
    //����Beautifier�Ĺ��캯��
    var MyCaptcha = function (ele, opt) {
        this.$element = ele,
            this.defaults = {
                'url': '/captcha',
                'fontSize': '12px',
                'textDecoration': 'none'
            },
            this.options = $.extend({}, this.defaults, opt),
            this.clickPoint = [];
    }
    //����MyCaptcha�ķ���
    MyCaptcha.prototype = {
        imgclick: function (target, e) {
            if (this.clickPoint.length >= 3) {
                return;
            }
            var offsetX = e.offsetX;

            var offsetY = e.offsetY;
            var imgWidth = target.width;
            var imgHeight = target.height;
            console.log(imgWidth);
            var iWidth = 330; //ͼƬ���
            var iHeight = 160; //ͼƬ�߶�
            var x = offsetX * (iWidth / imgWidth);
            var y = offsetY * (iHeight / imgHeight);
            var point = {};
            point['x'] = x;
            point['y'] = y;
            this.clickPoint.push(point);

            console.log("array size" + this.clickPoint.length);

            //��ʾѡ���
            var html = '<div class="myCaptcha-point myCaptcha-point'+this.clickPoint.length+'"></div>';
            $(html).appendTo(this.$element.find('.myCaptcha-img-box')).css({"left":offsetX-13,"top":offsetY-25});
            if (this.clickPoint.length >= 3) {
                this.checkCaptcha();
            }
        },
        checkCaptcha: function () {
            //base64����
            var _this = this;
            var encClickPoint = $.base64.encode(JSON.stringify(this.clickPoint));
            console.log("encoded click point:" + encClickPoint);
            $.get("checkCaptcha", {"a": encClickPoint}, function (data) {

                if (data.status == "OK") {
                    _this.success();
                    console.log("��֤�ɹ�");
                } else {
                    _this.showTip("warning");
                    setTimeout(function(){_this.load();}, 800);
                    //_this.load();
                    console.log("��֤ʧ��");
                }

            }, "json");
        },
        init: function () {
            //����html
            var html = '<div class="myCaptcha-img-box">'
                + '<img class="myCaptcha-image" src="">'
                + '<div class="myCaptcha-btn-refresh" title="ˢ��"></div>'
                + '</div>'
                + '<div class="myCaptcha-tip-box normal"><span class="myCaptcha-tip-box tip-text"></span></div>'
                + '<div class="myCaptcha-tip-box success"><div class="success-icon"></div>��֤�ɹ�</div>'
                + '<div class="myCaptcha-tip-box warning"><div class="warning-icon"></div>��֤ʧ�ܣ�������</div>';

            this.$element.append(html);
            this.$element.addClass("myCaptcha");
            //����ͼƬ
            this.load();
            //����
            var _this = this;
            this.$element.find('.myCaptcha-image').click(function (e) {
                _this.imgclick(this, e);
            });
            //��ȡ��֤��
            this.$element.find('.myCaptcha-btn-refresh').click(function (e) {
                _this.load()
            });


            return this.$element;
        },
        load: function () {
            //��ʾˢ�°�ť
            this.$element.find('.myCaptcha-btn-refresh').show();
            this.showTip("normal");
            //ɾ��ѡ���ע
            this.$element.find('.myCaptcha-point').remove();

            this.clickPoint = [];
            var _this = this;
            $.get("/captcha", {}, function (data) {
                if (data.status == "OK") {
                    console.log("����:" + data.data);
                    //��ʾ��ʾ
                    var tip = "�����ε��:";
                    for (var i = 0; i < 3; i++) {
                        tip += "\"" + data.data[i] + "\" "
                    }

                    _this.$element.find(".tip-text:first").html(tip);
                    var img= _this.$element.find(".myCaptcha-image");
                    img.fadeOut("fast").attr("src", "/captchaImg?r=" + Math.random()).fadeIn("fast");
                } else {
                    console.log("��ȡͼƬ��֤��ʧ��!");
                }
            }, "json");
        },
        success:function(){

            //��֤�ɹ�
            //�����ɫ������ʾ��֤�ɹ�
            this.showTip("success");
            //��ˢ������
            this.$element.find('.myCaptcha-btn-refresh').hide();

        },
        showTip:function(type){
            var n = this.$element.find('.myCaptcha-tip-box.normal').hide();
            var s = this.$element.find('.myCaptcha-tip-box.success').hide();
            var w = this.$element.find('.myCaptcha-tip-box.warning').hide();
            if(type=='success'){
                s.fadeIn("slow");
            }else if(type=='warning'){
                w.fadeIn("slow");
            }else{
                n.fadeIn("slow");
            }
            return this;
        }

    }
    //�ڲ����ʹ��MyCaptcha����
    $.fn.MyCaptcha = function (options) {
        //����Beautifier��ʵ��
        var myCaptcha = new MyCaptcha(this, options);
        //�����䷽��
        return myCaptcha.init();
    }
})(jQuery, window, document);