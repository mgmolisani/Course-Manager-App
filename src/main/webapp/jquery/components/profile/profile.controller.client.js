(function () {
    var $usernameFld;
    var $firstNameFld, $lastNameFld;
    var $phoneNumberFld, $emailFld;
    var $dobFld, $roleFld;
    var $logoutBtn, $updateBtn;
    var userId;
    var userService = new UserServiceClient();

    $(main);

    function main() {
        userId = getQueryVariable('userId');
        if (userId === null) {
            alert('No user is logged in.')
            window.location.assign('../login/login.template.client.html');
        } else {
            $usernameFld = $('#usernameFld');
            $firstNameFld = $('#firstNameFld');
            $lastNameFld = $('#lastNameFld');
            $phoneNumberFld = $('#phoneNumberFld');
            $emailFld = $('#emailFld');
            $dobFld = $('#dateOfBirthFld');
            $roleFld = $('#roleFld');
            $updateBtn = $('#updateBtn').click(updateProfile);
            $logoutBtn = $('#logoutBtn').click(logout);

            userId = getQueryVariable('userId');

            renderProfile();
        }
    }

    function logout() {
        window.location.assign('../login/login.template.client.html');
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
            .updateProfile(userId, user, function () {
                alert('Profile updated successfully.');
            }).catch(function (error) {
            alert(error.message);
        });
    }

    function getQueryVariable(variable) {
        var query = window.location.search.substring(1);
        var vars = query.split("&");
        for (var i = 0; i < vars.length; i++) {
            var pair = vars[i].split("=");
            if (pair[0] == variable) {
                return pair[1];
            }
        }
        return (null);
    }

    function renderProfile() {
        if (userId) {
            userService
                .findUserById(userId, function (currentUser) {
                    console.log(currentUser);
                    $usernameFld.val(currentUser.username);
                    $firstNameFld.val(currentUser.firstName);
                    $lastNameFld.val(currentUser.lastName);
                    $phoneNumberFld.val(currentUser.phone);
                    $emailFld.val(currentUser.email);
                    $dobFld.val(currentUser.dateOfBirth);
                    $roleFld.val(currentUser.role);
                });
        } else {
            throw new Error("User not logged in");
        }
    }
})();
