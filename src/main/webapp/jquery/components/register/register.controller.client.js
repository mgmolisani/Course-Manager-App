(function () {
    var $usernameFld, $passwordFld, $verifyPasswordFld;
    var $registerBtn;
    var userService = new UserServiceClient();

    $(main);

    /**
     * Start the controller.
     */
    function main() {
        //Save fields as jQuery objects
        $usernameFld = $('#usernameFld');
        $passwordFld = $('#passwordFld');
        $verifyPasswordFld = $('#verifyPasswordFld');

        //Configure buttons
        $registerBtn = $('#registerBtn').click(register);
    }

    /**
     * Registers the user
     */
    function register() {
        var username = $usernameFld.val();
        var password = $passwordFld.val();
        var verifyPassword = $verifyPasswordFld.val();

        //Verify the inputs are valid before attempting to create the new user
        if (username === '' || password === '') {
            alert('Username and password fields cannot be blank');
        } else if (password !== verifyPassword) {
            alert('Password verification failed.');
        } else {
            var user = new User(username, password);
            //Register the user, welcome them, and go to their profile
            //or report why the user couldn't be created (username taken)
            userService
                .register(user, function(newUser) {
                    alert('Welcome, ' + username);
                    window.location.assign('../profile/profile.template.client.html?userId=' + newUser.id);
                }).catch(function(error) {
                    alert(error.message);
            });
        }
    }
})();
