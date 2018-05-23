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
    }

    function register() {
        var username = $usernameFld.val();
        var password = $passwordFld.val();
        var verifyPassword = $verifyPasswordFld.val();

        if (username === '' || password === '') {
            alert('Username and password fields cannot be blank');
        } else if (password !== verifyPassword) {
            alert('Password verification failed.');
        } else {
            var user = new User(username, password);
            userService
                .register(user, function() {
                    alert('Welcome, ' + username);
                    window.location.assign('../admin/user-admin.template.client.html');
                }).catch(function(error) {
                    alert(error.message);
            });
        }
    }
})();
