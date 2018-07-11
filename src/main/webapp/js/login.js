function submitContactForm(){

    var email = $('#email').val();
    var password = $('#password').val();

        $.ajax({
            type:'POST',
            url:'/frontController?command=login&',
            data:'email='+email+'&password='+password,
            success: function(response) {
              alert("success");

           }

        });


}