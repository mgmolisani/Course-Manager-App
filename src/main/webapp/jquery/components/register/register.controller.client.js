(function () {
    var $usernameFld, $passwordFld, $verifyPasswordFld;
    var $registerBtn;
    var userService = new UserService();

    $(main);

    function main() {
        $usernameFld = $('#usernameFld');
        $passwordFld = $('#passwordFld');
        $verifyPasswordFld = $('#verifyPasswordFld');
        $registerBtn = $('#registerBtn').click(register);
    }

    function register() {
        var username = $usernameFld.val();
        var password = $passwordFld.val();
        var verifyPassword = $verifyPasswordFld.val();

        if (password === verifyPassword && username != null) {
            userService
                .register(username, password);
        }
    }
})();
