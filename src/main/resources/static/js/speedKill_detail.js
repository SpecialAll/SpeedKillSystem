//JavaScript
var speedKill = {
    //封装相关秒杀的ajax的URL地址
    URL : {
        now : function () {
            return '/speedKill/time/now';
        },
        exposer : function (goodsId) {
            return '/speedKill/' + goodsId + '/exposer';
        },
        execution : function (goodsId, md5) {
            return '/speedKill/' +goodsId + '/' + md5 +'/execution';
        }
    },
    //验证手机号
    validatePhone : function (phone) {
        if (phone && phone.length == 11 && !isNaN(phone)){
            return true;
        }
        return false;
    },
    //处理秒杀逻辑
    handleSpeedKill : function (goodsId, node, money) {
        //获取秒杀地址，控制显示逻辑，执行秒杀
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
        $.post(speedKill.URL.exposer(goodsId), {}, function (result) {
            //在回调函数中执行交互逻辑
            if (result && result['success']){
                var exposer = result['data'];
                if(exposer['exposed']){
                    //开启秒杀
                    var md5 = exposer['md5'];
                    var killURL = speedKill.URL.execution(goodsId, md5);
                    console.log('KillURL' + killURL);
                    //one: 绑定一次点击事件
                    $('#killBtn').one('click', function () {
                        //执行秒杀操作
                        //禁用按钮
                        $(this).addClass('disabled');
                        //发送秒杀请求
                        $.post(killURL, {money: money}, function (result) {
                            if(result && result['success']){
                                var killResult = result['data'];
                                var stateInfo = killResult['stateInfo'];
                                //显示秒杀结果
                                node.html('<span class="label label-success">' + stateInfo + '</span>');
                            }
                        })
                    });
                    node.show();
                } else {
                    //未开启秒杀
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    speedKill.countdown(goodsId, now, start, end);
                }
            } else {
                console.log("result" + result);
            }
        });
    },
    //计时
    countdown : function (goodsId, nowTime, startTime, endTime, money) {
        var speedKillBox = $('#speedKill-box');
        var speedKillTimeSpan = $('#speedKill-time-span');
        //时间判断
        if(nowTime > endTime){
            //秒杀结束
            speedKillTimeSpan.html('秒杀结束');
            speedKillBox.hide();
        } else if (nowTime < startTime){
            //秒杀未开始，计时事件绑定
            var killTime = new Date(startTime );
            speedKillTimeSpan.countdown(killTime, function (event) {
                //时间格式
                var format = event.strftime('秒杀开始倒计时： %D天 %H时 %M分 %S秒');
                speedKillTimeSpan.html(format);
                //时间完成后，回调函数
            }).on('finish.countdown', function () {
                //获取秒杀地址，控制实现逻辑
                speedKill.handleSpeedKill(goodsId, speedKillBox, money);
            });
        } else {
            //秒杀开始
            speedKill.handleSpeedKill(goodsId, speedKillBox, money);

            //计时
            var killEndTime = new Date(endTime );
            speedKillTimeSpan.countdown(killEndTime, function (event) {
                //时间格式
                var format = event.strftime('距离秒杀结束： %D天 %H时 %M分 %S秒');
                speedKillTimeSpan.html(format);
            });
        }
    },
    //详情页秒杀逻辑
    detail: {
        //详情页初始化
        init: function (params) {
            //进行手机验证
            //在cookie中查看用户
            var killPhone = $.cookie('killPhone');
            //验证手机号
            if (!speedKill.validatePhone(killPhone)) {
                //绑定phone
                var killPhoneModal = $('#killPhoneModal');
                killPhoneModal.modal({
                    show: true,
                    backdrop: 'static', //禁止位置关闭
                    keyboard: false //关闭键盘事件
                });
                $("#killPhoneBtn").click(function () {
                    var inputPhone = $('#killPhoneKey').val();
                    if (speedKill.validatePhone(inputPhone)) {
                        //将手机号写入cookie
                        $.cookie('killPhone', inputPhone, {expires: 7, path: '/speedKill/'});
                        //刷新页面
                        window.location.reload();
                    } else {
                        $("#killPhoneMessage").hide().html('<lable class="label label-danger">手机号错误！</lable>').show(300);
                    }
                });
            }
            //已经登录
            //计时交互
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var goodsId = params['goodsId'];
            var money = params['money'];
            $.get(speedKill.URL.now(), {}, function (result) {
                if (result && result['success']) {
                    var nowTime = result['data'];
                    //时间判断
                    speedKill.countdown(goodsId, nowTime, startTime, endTime, money);
                } else {
                    console.log('result:' + result);
                }
            });
        }
    }
}