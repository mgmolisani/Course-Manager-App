(function () {
    var $usernameFld, $passwordFld;
    var $firstNameFld, $lastNameFld;
    var $phoneNumberFld, $emailFld;
    var $dobFld, $roleFld;
    var $removeBtn, $editBtn, $createBtn, $updateBtn;
    var $userRowTemplate, $tbody;
    var updateId;
    var userService = new UserServiceClient();

    $(main);

    function main() {
        $tbody = $('tbody');
        $userRowTemplate = $('.wbdv-template');
        $usernameFld = $('#usernameFld');
        $passwordFld = $('#passwordFld');
        $firstNameFld = $('#firstNameFld');
        $lastNameFld = $('#lastNameFld');
        $phoneNumberFld = $('#phoneNumberFld');
        $emailFld = $('#emailFld');
        $dobFld = $('#dobFld');
        $roleFld = $('#roleFld');

        $removeBtn = $('.wbdv-remove').click(deleteUser);
        $editBtn = $('.wbdv-edit').click(findUserById);
        $createBtn = $('.wbdv-create').click(createUser);
        $updateBtn = $('.wbdv-update').click(updateUser);

        findAllUsers();
    }

    function createUser() {
        var username = $usernameFld.val();
        var password = $passwordFld.val();
        var firstName = $firstNameFld.val();
        var lastName = $lastNameFld.val();
        var phoneNumber = $phoneNumberFld.val();
        var email = $emailFld.val();
        var dob = $dobFld.val();
        var role = $roleFld.val();

        var user = new User(username, password,
            firstName, lastName,
            phoneNumber, email,
            dob, role);

        console.log(user);

        userService
            .createUser(user/*, defaultCallback*/)
            .then(findAllUsers);
    }

    function findAllUsers() {
        userService
            .findAllUsers(/*defaultCallback*/)
            .then(renderUsers);
    }

    function findUserById(event) {
        var userId = $(event.currentTarget)
            .parent()
            .parent()
            .parent()
            .attr('id');

        userService
            .findUserById(userId, defaultCallback)
            .then(renderUser);

        updateId = userId;
    }

    function deleteUser(event) {
        var userId = $(event.currentTarget)
            .parent()
            .parent()
            .parent()
            .attr('id');

        userService
            .deleteUser(userId, defaultCallback)
            .then(findAllUsers);
    }

    function updateUser() {
        var username = $usernameFld.val();
        var password = $passwordFld.val();
        var firstName = $firstNameFld.val();
        var lastName = $lastNameFld.val();
        var phoneNumber = $phoneNumberFld.val();
        var email = $emailFld.val();
        var dob = $dobFld.val();
        var role = $roleFld.val();

        var user = new User(username, password,
            firstName, lastName,
            phoneNumber, email,
            dob, role);

        userService
            .updateUser(updateId, user, defaultCallback)
            .then(renderUsers);
    }

    function renderUser(user) {
        $usernameFld.val(user.username);
        $firstNameFld.val(user.firstName);
        $lastNameFld.val(user.lastName);
        $phoneNumberFld.val(user.phoneNumber);
        $emailFld.val(user.email);
        $dobFld.val(user.dateOfBirth);
        $roleFld.val(user.role);
    }

    function renderUsers(users) {
        $tbody.empty();
        for(var i = 0; i < users.length; i++) {
            var user = users[i];
            var clone = $userRowTemplate.clone();

            clone.attr('id', user.id);
            clone.removeClass('wbdv-hidden');

            clone.find('.wbdv-username').html(user.username);
            clone.find('.wbdv-first-name').html(user.firstName);
            clone.find('.wbdv-last-name').html(user.lastName);
            clone.find('.wbdv-phone-number').html(user.phoneNumber);
            clone.find('.wbdv-email').html(user.email);
            clone.find('.wbdv-dob').html(user.dateOfBirth);
            clone.find('.wbdv-role').html(user.role);

            clone.find('.wbdv-remove').click(deleteUser);
            clone.find('.wbdv-edit').click(updateUser);

            $tbody.append(clone);
        }
    }

    function defaultCallback(response) {
        console.log(response);
        return response;
    }
})();
