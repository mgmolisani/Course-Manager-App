(function () {
    var $usernameFld, $passwordFld;
    var $loginBtn;
    var userService = new UserServiceClient();

    $(main);

    function main() {
        $usernameFld = $('#usernameFld');
        $passwordFld = $('#passwordFld');
        $loginBtn = $('#loginBtn').click(login);
    }

    function login() {
        var username = $usernameFld.val();
        var password = $passwordFld.val();

        var user = new User(username, password);

        userService
            .login(user, function() {
                window.location.assign('../admin/user-admin.template.client.html');
            }).catch(function(error) {
                alert(error.message);
            });
    }
})();
