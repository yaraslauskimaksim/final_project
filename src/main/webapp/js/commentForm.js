 $(document).ready(function() {
    $('#comment_form').bootstrapValidator({
        fields: {
             description: {
                           validators: {
                                 stringLength: {
                                   min: 10,
                                   max: 100,
                                   message:'Please enter at least 10 characters and no more than 100'
                               },
                               notEmpty: {
                                   message: 'Please supply a message'
                               }
                               }
                           }
                       }
                   })
        .on('success.form.bv', function(e) {
            $('#success_message').slideDown({ opacity: "show" }, "slow") // Do something ...
                $('#contact_form').data('bootstrapValidator').resetForm();

            // Prevent form submission
            e.preventDefault();

            // Get the form instance
            var $form = $(e.target);

            // Get the BootstrapValidator instance
            var bv = $form.data('bootstrapValidator');

 });
});
