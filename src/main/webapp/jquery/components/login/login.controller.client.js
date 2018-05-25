(function () {
    var $usernameFld, $passwordFld;
    var $loginBtn;
    var $forgotLink;
    var userService = new UserServiceClient();

    $(main);

    /**
     * Start the controller.
     */
    function main() {
        //Save fields as jQuery objects
        $usernameFld = $('#usernameFld');
        $passwordFld = $('#passwordFld');

        //Configure buttons
        $loginBtn = $('#loginBtn').click(login);
        $forgotLink = $('#forgotLink').click(forgotPassword);
    }

    /**
     * Log in a user
     */
    function login() {
        var username = $usernameFld.val();
        var password = $passwordFld.val();

        var user = new User(username, password);

        //Log in the user and redirect ot the profile page
        userService
            .login(user, function(currentUser) {
                window.location.assign('../profile/profile.template.client.html?userId=' + currentUser.id);
            }).catch(function(error) {
                alert(error.message);
            });
    }

    /**
     * Send email to reset password
     */
    function forgotPassword() {
        var username = $usernameFld.val();
        if (username !== '') {
            userService
                .forgotPassword(username, function() {
                    alert('Check your email for a link to reset your password.')
                }).catch(function(error) {
                    alert(error.message);
            });
        } else {
            alert('Enter your username.');
        }
    }
})();
