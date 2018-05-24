(function () {
    var $usernameFld;
    var $firstNameFld, $lastNameFld;
    var $phoneNumberFld, $emailFld;
    var $dobFld, $roleFld;
    var $logoutBtn, $updateBtn;
    var userService = new UserServiceClient();

    $(main);

    function main() {
        $usernameFld = $('#usernameFld');
        $firstNameFld = $('#firstNameFld');
        $lastNameFld = $('#lastNameFld');
        $phoneNumberFld = $('#phoneNumberFld');
        $emailFld = $('#emailFld');
        $dobFld = $('#dateOfBirthFld');
        $roleFld = $('#roleFld');
        $updateBtn = $('#updateBtn').click(updateProfile);
        $logoutBtn = $('#logoutBtn').click(logout);
    }

    function logout() {
        userService
            .logout(function() {
                window.location.assign('../login/login.template.client.html');
            }).catch(function(error) {
                alert(error.message);
            });
    }

    function updateProfile() {
        var username = $usernameFld.val();
        var firstName = $firstNameFld.val();
        var lastName = $lastNameFld.val();
        var phoneNumber = $phoneNumberFld.val();
        var email = $emailFld.val();
        var dob = $dobFld.val();
        var role = $roleFld.val();

        var user = new User(username, null,
                            firstName, lastName,
                            phoneNumber, email,
                            dob, role);

        userService
            .login(user, function() {
                alert('Profile updated successfully.');
            }).catch(function(error) {
            alert(error.message);
        });
    }
})();
