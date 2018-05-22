(function () {
    var $usernameFld, $passwordFld, $verifyPasswordFld;
    var $registerBtn;
    var userService = new UserServiceClient();

    $(main);

    function main() {
        $usernameFld = $('#usernameFld');
        $passwordFld = $('#passwordFld');
        $verifyPasswordFld = $('#verifyPasswordFld');
        $registerBtn = $('#registerBtn').click(register);
        console.log(userService.getAttr('user'));
    }

    function register() {
        var username = $usernameFld.val();
        var password = $passwordFld.val();
        var verifyPassword = $verifyPasswordFld.val();

        if (password === verifyPassword && username !== '') {
            var user = new User(username, password);
            userService
                .register(user, function(response) {
                    if (response === null) {
                        alert('User account could not be created');
                    } else {
                        alert('Welcome, ' + username);
                    }
                });
        }
    }
})();
