$(function() {
  $(":radio").keypress(check_submit).each(function() {
    check_submit();
  });
});

function check_submit() {
  if ($(this).val().length == 0) {
    $(":submit").attr("disabled", true);
  } else {
    $(":submit").removeAttr("disabled");
  }
}