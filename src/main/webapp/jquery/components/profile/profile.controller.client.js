(function () {
    var $usernameFld;
    var $firstNameFld, $lastNameFld;
    var $phoneNumberFld, $emailFld;
    var $dobFld, $roleFld;
    var $logoutBtn, $updateBtn;
    var userId;
    var userService = new UserServiceClient();

    $(main);

    /**
     * Start the controller.
     */
    function main() {
        userId = getQueryVariable('userId');
        //Ensure the user is logged in before attempting to render the user or run and services
        if (userId === null) {
            alert('No user is logged in.')
            window.location.assign('../login/login.template.client.html');
        } else {
            //Save fields as jQuery objects
            $usernameFld = $('#usernameFld');
            $firstNameFld = $('#firstNameFld');
            $lastNameFld = $('#lastNameFld');
            $phoneNumberFld = $('#phoneNumberFld');
            $emailFld = $('#emailFld');
            $dobFld = $('#dateOfBirthFld');
            $roleFld = $('#roleFld');

            //Configure buttons
            $updateBtn = $('#updateBtn').click(updateProfile);
            $logoutBtn = $('#logoutBtn').click(logout);

            userId = getQueryVariable('userId');

            renderProfile();
        }
    }

    /**
     * Logout the user
     */
    function logout() {
        //Redirect to the login page
        //No need for a serive here as we are not using sessions so a redirect is sufficient
        window.location.assign('../login/login.template.client.html');
    }

    /**
     * Update the user
     */
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

        //Update the user and alert on success
        userService
            .updateProfile(userId, user, function () {
                alert('Profile updated successfully.');
            }).catch(function (error) {
            alert(error.message);
        });
    }

    /**
     * Find the value of the request parameter at the given key
     * @param key the key to search for
     * @returns the value at the key or null
     */
    function getQueryVariable(key) {
        var query = window.location.search.substring(1);
        var vars = query.split("&");
        for (var i = 0; i < vars.length; i++) {
            var pair = vars[i].split("=");
            if (pair[0] == key) {
                return pair[1];
            }
        }
        return (null);
    }

    /**
     * Helper to render the user in the profile fields when the page is loaded
     */
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
