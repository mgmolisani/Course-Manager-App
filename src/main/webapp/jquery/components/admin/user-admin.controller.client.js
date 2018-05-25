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

    /**
     * Start the controller.
     */
    function main() {
        //Get area that will be appended to and the template
        $tbody = $('tbody');
        //Save fields as jQuery objects
        $userRowTemplate = $('.wbdv-template');
        $usernameFld = $('#usernameFld');
        $passwordFld = $('#passwordFld');
        $firstNameFld = $('#firstNameFld');
        $lastNameFld = $('#lastNameFld');
        $phoneNumberFld = $('#phoneNumberFld');
        $emailFld = $('#emailFld');
        $dobFld = $('#dobFld');
        $roleFld = $('#roleFld');

        //Configure buttons
        $createBtn = $('.wbdv-create').click(createUser);
        $updateBtn = $('.wbdv-update').click(updateUser);

        findAllUsers();
    }

    /**
     * Creates a new user.
     */
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

        //create the user and then update the page
        userService
            .createUser(user, findAllUsers);
    }

    /**
     * Finds all the users and renders them on the page.
     * Used as a callback after most method calls.
     */
    function findAllUsers() {
        userService
            .findAllUsers(renderUsers);
    }

    /**
     * Finds a user with a given ID and places its information in the edit form fields
     * @param event
     */
    function findUserById(event) {
        $editBtn = $(event.currentTarget);
        //Get the ID of the user
        var userId = $editBtn
            .parent()
            .parent()
            .parent()
            .attr('id');

        //Save the ID of the user globally to the controller function
        //Used by update user to place the info back in the proper spot
        updateId = userId;

        //find the user and render it in the form
        userService
            .findUserById(userId, renderUser);
    }

    /**
     * Deletes a user
     * @param event
     */
    function deleteUser(event) {
        $removeBtn = $(event.currentTarget);
        //Get the ID of the user to delete
        var userId = $removeBtn
            .parent()
            .parent()
            .parent()
            .attr('id');

        //Delete the user and update the page
        userService
            .deleteUser(userId, findAllUsers);
    }

    /**
     * Updates a users information
     */
    function updateUser() {
        var username = $usernameFld.val();
        var firstName = $firstNameFld.val();
        var lastName = $lastNameFld.val();
        var phoneNumber = $phoneNumberFld.val();
        var email = $emailFld.val();
        var dob = $dobFld.val();
        var role = $roleFld.val();

        //Create the new user (password will not be changed so we give it null)
        var user = new User(username, null,
            firstName, lastName,
            phoneNumber, email,
            dob, role);

        //Update the user and update the page
        userService
            .updateUser(updateId, user, findAllUsers);
    }

    /**
     * Passes a user's information into the form fields.
     * @param user the user to populate information for
     */
    function renderUser(user) {
        $usernameFld.val(user.username);
        $firstNameFld.val(user.firstName);
        $lastNameFld.val(user.lastName);
        $phoneNumberFld.val(user.phone);
        $emailFld.val(user.email);
        $dobFld.val(user.dateOfBirth);
        $roleFld.val(user.role);
    }

    /**
     * Updates the table with all of the given users
     * @param users the users to be rendered
     */
    function renderUsers(users) {
        $tbody.empty();
        for(var i = 0; i < users.length; i++) {
            var user = users[i];

            var clone = $userRowTemplate.clone();

            //add the ID and make the block visible
            clone.attr('id', user.id);
            clone.removeClass('wbdv-hidden');

            //Update the html
            clone.find('.wbdv-username').html(user.username);
            clone.find('.wbdv-first-name').html(user.firstName);
            clone.find('.wbdv-last-name').html(user.lastName);
            clone.find('.wbdv-phone-number').html(user.phone);
            clone.find('.wbdv-email').html(user.email);
            clone.find('.wbdv-dob').html(user.dateOfBirth);
            clone.find('.wbdv-role').html(user.role);

            //Add the button functions
            clone.find('.wbdv-remove').click(deleteUser);
            clone.find('.wbdv-edit').click(findUserById);

            //Append the user to the page
            $tbody.append(clone);
        }
    }
})();
