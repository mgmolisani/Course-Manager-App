    function User(username, password, firstName, lastName,
              phone, email, dateOfBirth, role) {
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;
    this.email = email;
    this.dateOfBirth = dateOfBirth;
    this.role = role;

    this.setUsername = setUsername;
    this.setPassword = setPassword;
    this.setFirstName = setFirstName;
    this.setLastNmae = setLastName;
    this.setPhone = setPhone;
    this.setEmail = setEmail;
    this.setDateOfBirth = setDateOfBirth;
    this.setRole = setRole;

    this.getUsername = getUsername;
    this.getPassword = getPassword;
    this.getFirstName = getFirstName;
    this.getLastNmae = getLastName;
    this.getPhone = getPhone;
    this.getEmail = getEmail;
    this.getDateOfBirth = getDateOfBirth;
    this.getRole = getRole;

    var self = this;

    function setUsername(username) {
        self.username = username;
    }

    function getUsername() {
        return self.username;
    }

    function setPassword(password) {
        self.password = password;
    }

    function getPassword() {
        return self.password;
    }

    function setFirstName(firstName) {
        self.firstName = firstName;
    }

    function getFirstName() {
        return self.firstName;
    }

    function setLastName(lastName) {
        self.lastName = lastName;
    }

    function getLastName() {
        return self.lastName;
    }

    function setPhone(phoneNumber) {
        self.phone = phone;
    }

    function getPhone() {
        return self.phone;
    }

    function setEmail(email) {
        self.email = email;
    }

    function getEmail() {
        return self.email;
    }

    function setDateOfBirth(dateOfBirth) {
        self.dateOfBirth = dateOfBirth;
    }

    function getDateOfBirth() {
        return self.dateOfBirth;
    }

    function setRole(role) {
        self.role = role;
    }

    function getRole() {
        return self.role;
    }

}
