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

        userService
            .createUser(user, findAllUsers);
    }

    function findAllUsers() {
        userService
            .findAllUsers(renderUsers);
    }

    function findUserById(event) {
        $editBtn = $(event.currentTarget);
        var userId = $editBtn
            .parent()
            .parent()
            .parent()
            .attr('id');

        updateId = userId;

        userService
            .findUserById(userId, renderUser);
    }

    function deleteUser(event) {
        $removeBtn = $(event.currentTarget);
        var userId = $removeBtn
            .parent()
            .parent()
            .parent()
            .attr('id');

        userService
            .deleteUser(userId, findAllUsers);
    }

    function updateUser() {
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
            .updateUser(updateId, user, findAllUsers);
    }

    function renderUser(user) {
        $usernameFld.val(user.username);
        $firstNameFld.val(user.firstName);
        $lastNameFld.val(user.lastName);
        $phoneNumberFld.val(user.phone);
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
            clone.find('.wbdv-phone-number').html(user.phone);
            clone.find('.wbdv-email').html(user.email);
            clone.find('.wbdv-dob').html(user.dateOfBirth);
            clone.find('.wbdv-role').html(user.role);

            clone.find('.wbdv-remove').click(deleteUser);
            clone.find('.wbdv-edit').click(findUserById);

            $tbody.append(clone);
        }
    }
})();
