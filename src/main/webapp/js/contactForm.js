 $(document).ready(function() {
    $('#contact_form').bootstrapValidator({

        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
             message: {
                           validators: {
                                 stringLength: {
                                   min: 10,
                                   max: 200,
                                   message:'Please enter at least 10 characters and no more than 200'
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
