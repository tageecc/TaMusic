TweenLite.defaultEase = Expo.easeOut;
var utils = {
    map: function (value, min1, max1, min2, max2) {
        var value1 = (value - min1) / (max1 - min1);
        var value2 = value1 * (max2 - min2) + min2;
        return value2;
    }
};
var UIButton = function () {
    var UIButton = function ($element) {
        this.$element = $element;
        this.$shadow = this.$element.find('.btn__shadow');
        this.clickableArea = {
            x: 0,
            y: 0,
            w: this.$element.width(),
            h: this.$element.height()
        };
        this.handlers = {};
        this.$element.on('click', function (evt) {
            var offsetX = evt.pageX - this.$element.offset().left;
            var offsetY = evt.pageY - this.$element.offset().top;
            if (offsetX >= this.clickableArea.x && offsetX <= this.clickableArea.x + this.clickableArea.w && offsetY >= this.clickableArea.y && offsetY <= this.clickableArea.y + this.clickableArea.h) {
                this.trigger('click', evt);
            }
        }.bind(this));
        this.on('click', this.shadow.bind(this));
    };
    UIButton.prototype.shadow = function () {
        var timeline = new TimelineMax();
        timeline.set(this.$shadow, {opacity: 0.15});
        timeline.to(this.$shadow, 1, {opacity: 0});
    };
    UIButton.prototype.on = function (name, fn) {
        if (typeof name === 'string' && typeof fn === 'function') {
            this.handlers[name] = (this.handlers[name] || []).concat(fn);
        }
    };
    UIButton.prototype.trigger = function (name) {
        var args = Array.prototype.slice.call(arguments);
        for (var i = 0; i < (this.handlers[name] || []).length; i++) {
            this.handlers[name][i].apply(this, args.slice(1));
        }
    };
    return UIButton;
}();
var PlayPauseUIButton = function () {
    var PlayPauseUIButton = function ($element) {
        UIButton.call(this, $element);
        this.timeline = new TimelineMax({paused: true});
        this.timeline.staggerTo(this.$element.find('.icon__shape--circle'), 1.75, {
            strokeDasharray: '119.38052 0',
            strokeDashoffset: -119.38052
        }, 0.05, 0);
        this.timeline.to(this.$element.find('.icon__shape--triangle'), 1.75, {
            strokeDasharray: '38 0',
            strokeDashoffset: 38
        }, 0);
        this.timeline.to(this.$element.find('.icon__shape--line'), 0.5, {strokeDashoffset: -12}, 0);
        this.timeline.to(this.$element.find('.icon__shape--line'), 0.5, {opacity: 0}, 0.1);
        this.on('click', this.toggle);
    };
    PlayPauseUIButton.prototype = Object.create(UIButton.prototype);
    PlayPauseUIButton.prototype.toggle = function () {
        if (this.timeline.yoyo()) {
            this.pause();
        } else {
            this.play();
        }
    };
    PlayPauseUIButton.prototype.play = function () {
        this.timeline.yoyo(true).tweenTo(this.timeline.duration());
    };
    PlayPauseUIButton.prototype.pause = function () {
        this.timeline.yoyo(false).tweenTo(0, {ease: Expo.easeOut});
    };
    return PlayPauseUIButton;
}();
var VolumeUIButton = function () {
    var VolumeUIButton = function ($element) {
        UIButton.call(this, $element);
        this.level = 2;
        this.progress = 100;
        this.volume = 100;
        this.clickableArea.w = 45;
        this.gestureManager = new Hammer.Manager(this.$element.find('.icon__shape--circle-controls')[0], {touchAction: 'none'});
        this.gestureManager.add(new Hammer.Press({time: 10}));
        this.gestureManager.add(new Hammer.Pan({
            direction: Hammer.DIRECTION_HORIZONTAL,
            threshold: 0
        }));
        this.timeline = new TimelineMax();
        this.on('click', this.toggle);
    };
    VolumeUIButton.prototype = Object.create(UIButton.prototype);
    VolumeUIButton.prototype.shadow = function () {
        if (!this.timeline.yoyo()) {
            UIButton.prototype.shadow.call(this);
        }
    };
    VolumeUIButton.prototype.toggle = function (evt) {
        if (!this.timeline.yoyo()) {
            this.open();
        }
    };
    VolumeUIButton.prototype.open = function () {
        var tweens = [];
        this.timeline.yoyo(true).clear();
        tweens.push(new TweenMax.to(this.$element.find('.icon__shape--circle-small'), 0.75, {strokeDashoffset: 10.99557}));
        tweens.push(new TweenMax.to(this.$element.find('.icon__shape--circle-medium'), 0.75, {
            opacity: 1,
            strokeDasharray: '0 163.36281',
            strokeDashoffset: -10.21017
        }));
        tweens.push(new TweenMax.to(this.$element.find('.icon__shape--circle-big'), 0.75, {strokeDashoffset: -119.38052}));
        this.timeline.add(tweens);
        var tweens = [];
        tweens.push(new TweenMax.to(this.$element.find('.icon__shape--circle-small'), 0.5, {opacity: 0}));
        tweens.push(new TweenMax.to(this.$element.find('.icon__shape--circle-big'), 0.5, {opacity: 0}));
        this.timeline.add(tweens, 0.25);
        this.timeline.set(this.$element.find('.icon__shape--circle-medium'), {opacity: 0});
        this.timeline.set(this.$element.find('.icon__shape--circle-placeholder'), {opacity: 1});
        var tweens = [];
        tweens.push(new TweenMax.to(this.$element.find('.icon__shape--line-controls.icon__shape--translucide'), 0.5, {strokeDashoffset: 0}));
        tweens.push(new TweenMax.to(this.$element.find('.icon__shape--circle-controls'), 0.5, {
            attr: {r: 8},
            ease: Back.easeOut
        }));
        this.timeline.add(tweens, 0.75);
        this.timeline.to({progress: 0}, 0.5 * this.volume / 100, {
            progress: this.volume,
            onUpdate: function (tween) {
                this.progress = tween.target.progress;
                this.refresh();
            },
            onUpdateParams: ['{self}'],
            onUpdateScope: this
        }, 0.75);
        this.timeline.add(this.drag.bind(this));
    };
    VolumeUIButton.prototype.close = function () {
        var tweens = [];
        this.timeline.yoyo(false).clear();
        this.timeline.to({progress: this.volume}, 0.5 * this.volume / 100, {
            progress: 0,
            onUpdate: function (tween) {
                this.progress = tween.target.progress;
                this.refresh();
            },
            onUpdateParams: ['{self}'],
            onUpdateScope: this
        }, 'progressive');
        tweens.push(new TweenMax.to(this.$element.find('.icon__shape--line-controls.icon__shape--translucide'), 0.5, {strokeDashoffset: 125}));
        tweens.push(new TweenMax.to(this.$element.find('.icon__shape--circle-controls'), 0.5, {attr: {r: 0}}));
        this.timeline.add(tweens, 'progressive-=0.5');
        this.timeline.set(this.$element.find('.icon__shape--circle-medium'), {opacity: 1});
        this.timeline.set(this.$element.find('.icon__shape--circle-placeholder'), {opacity: 0});
        tweens = [];
        tweens.push(new TweenMax.to(this.$element.find('.icon__shape--circle-small'), 0.5, {opacity: !this.level ? 0.2 : 1}));
        tweens.push(new TweenMax.to(this.$element.find('.icon__shape--circle-big'), 0.5, {opacity: this.level < 3 ? 0.2 : 1}));
        tweens.push(new TweenMax.to(this.$element.find('.icon__shape--circle-small'), 0.75, {strokeDashoffset: 0}));
        tweens.push(new TweenMax.to(this.$element.find('.icon__shape--circle-medium'), 0.75, {
            opacity: this.level < 2 ? 0.2 : 1,
            strokeDasharray: '20.42035 163.36281',
            strokeDashoffset: 0
        }));
        tweens.push(new TweenMax.to(this.$element.find('.icon__shape--circle-big'), 0.75, {strokeDashoffset: -89.53539}));
        this.timeline.add(tweens);
        this.gestureManager.off('panstart panmove pancancel panend');
        TweenMax.killTweensOf(self.close);
    };
    VolumeUIButton.prototype.drag = function () {
        var self = this;
        var volume = 0;
        this.gestureManager.on('press', function () {
            TweenMax.to(self.$element.find('.icon__shape--circle-controls'), 0.5, {
                attr: {r: 10},
                opacity: 0.4,
                ease: Back.easeOut
            });
            TweenMax.killTweensOf(self.close);
        });
        this.gestureManager.on('panstart', function () {
            volume = self.volume;
        });
        this.gestureManager.on('panmove', function (evt) {
            self.progress = self.volume = Math.min(Math.max(volume + evt.deltaX * 100 / 125, 0), 100);
            self.refresh();
        });
        this.gestureManager.on('pressup', this.release.bind(this));
        this.gestureManager.on('pancancel panend', this.release.bind(this));
        TweenMax.delayedCall(3, self.close, null, self);
    };
    VolumeUIButton.prototype.release = function () {
        TweenMax.to(this.$element.find('.icon__shape--circle-controls'), 0.5, {
            attr: {r: 8},
            opacity: 0.2
        });
        TweenMax.delayedCall(3, this.close, null, this);
    };
    VolumeUIButton.prototype.refresh = function () {
        var level = 0;
        if (this.volume) {
            level = Math.floor(utils.map(this.volume, 0, 101, 0, 3)) + 1;
        }
        if (level !== this.level) {
            if (!this.level) {
                this.timeline.clear();
                this.timeline.staggerTo(this.$element.find('.icon__shape--line-mute'), 0.5, {strokeDashoffset: 35.3553}, -0.1);
                this.timeline.staggerTo(this.$element.find('.icon__shape--line-mute'), 0.5, {opacity: 0}, -0.1, 0.25);
            } else if (!level) {
                this.timeline.clear();
                this.timeline.set(this.$element.find('.icon__shape--line-mute'), {opacity: 1});
                this.timeline.staggerTo(this.$element.find('.icon__shape--line-mute'), 0.5, {strokeDashoffset: 0}, 0.1);
            }
            this.level = level;
        }
        TweenMax.set(this.$element.find('.icon__shape--line-controls.icon__shape--white'), {strokeDashoffset: 125 * (1 - this.progress / 100)});
        TweenMax.set(this.$element.find('.icon__shape--circle-controls'), {x: 125 * this.progress / 100});
        this.trigger('volumeChange', this.volume);
    };
    return VolumeUIButton;
}();
$('.btn--play-pause').each(function () {
    new PlayPauseUIButton($(this));
});
$('.btn--volume').each(function () {
    var btn = new VolumeUIButton($(this));
    btn.on('volumeChange', function (vol) {
        audio.volume = vol / 100;
    });
});