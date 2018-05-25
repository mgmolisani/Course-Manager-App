(function () {
    var $passwordFld, $verifyPasswordFld;
    var $updateBtn;
    var userId;
    var userService = new UserServiceClient();

    $(main);

    /**
     * Start the controller.
     */
    function main() {
        //Save fields as jQuery objects
        $passwordFld = $('#passwordFld');
        $verifyPasswordFld = $('#verifyPasswordFld');

        var query = window.location.search;
        userId = query.substring(query.indexOf('=') + 1);

        //Configure buttons
        $updateBtn = $('#updateBtn').click(updatePassword);
    }

    /**
     * Update the user's password
     */
    function updatePassword() {
        var password = $passwordFld.val();
        var verifyPassword = $verifyPasswordFld.val();

        //Verify the inputs are valid before attempting to create the new user
        if (password !== verifyPassword) {
            alert('Password verification failed.');
        } else {
            //Register the user, welcome them, and go to their profile
            //or report why the user couldn't be created (username taken)
            userService
                .updatePassword(userId, password, function(newUser) {
                    alert('Password updated');
                    window.location.assign('../profile/profile.template.client.html?userId=' + newUser.id);
                }).catch(function(error) {
                alert(error.message);
            });
        }
    }
})();