// Reservation accordion
var reservation = $('.b-reservation');
var schedules = [];
var mobile = false;
var tablet = false;
var desktop = false;

!function removeTabIndex() {
  reservation.find('.b-reservation__time:not(.b-reservation__time--availibale)').attr('tabindex', '-1');
}();

var backup = reservation.html();

var foldItem = function() {
  reservation.find('.b-reservation__item').removeClass('b-reservation__item--open').find('.b-reservation__section').fadeOut();
  reservation.find('.b-reservation__item:first-child').addClass('b-reservation__item--open').find('.b-reservation__section').fadeIn();
};

$(document).on('click', '.js-reservation', function() {
  var _this = $(this);
  _this.parent().toggleClass('b-reservation__item--open');
  if (_this.parent().is('.b-reservation__item--open')) {
    _this.parent().find('.b-reservation__section').fadeIn();
  } else {
    _this.parent().find('.b-reservation__section').fadeOut();
  }
});

function adaptiveToMobile() {
  mobile = true;
  tablet = false;
  desktop = false;

  schedules = reservation.find('.b-reservation__schedule');
  reservation.empty();

  $.each(schedules, function(i, el) {
    $(el).wrap('<div class="b-reservation__item"><div class="grid"><div class="col-12"></div></div></div>');
    $('<button class="b-reservation__button js-reservation" title="Свернуть"></button>').insertBefore($(el).parent().parent());
  
    reservation.append($(el).parent().parent().parent());
  })

  foldItem();
}

function adaptiveToTablet() {
  mobile = false;
  tablet = true;
  desktop = false;

  schedules = reservation.find('.b-reservation__schedule');
  reservation.empty();
  var prevEl;
  var prevElCol;
  var elCol;

  $.each(schedules, function(i, el) {
    if (i%2) {
      $(el).wrap('<div class="col-6"></div>');
      $(prevEl).wrap('<div class="col-6"></div>');

      elCol = $(el).parent()[0];
      prevElCol = $(prevEl).parent()[0];

      $([prevElCol, elCol]).wrapAll('<div class="b-reservation__item"><div class="grid"></div></div>');
      $('<button class="b-reservation__button js-reservation" title="Свернуть"></button>').insertBefore($(el).parent().parent());
      reservation.append($(el).parent().parent().parent());
    }

    prevEl = el;
  });

  foldItem();
}

function adaptiveToDesktop() {
  mobile = false;
  tablet = false;
  desktop = true;

  reservation.html(backup);
  foldItem();
}

$(window).on('resize', function() {
  if (window.innerWidth < 768 && !mobile) adaptiveToMobile();
  if (window.innerWidth < 1080 && window.innerWidth > 768 && !tablet) adaptiveToTablet();
  if (window.innerWidth > 1080 && !desktop) adaptiveToDesktop();
});

$(window).on('load', function() {
  if (window.innerWidth < 768 && !mobile) adaptiveToMobile();
  if (window.innerWidth < 1080 && window.innerWidth > 768 && !tablet) adaptiveToTablet();
  if (window.innerWidth > 1080 && !desktop) adaptiveToDesktop();
});
