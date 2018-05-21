function User(username, password, firstName, lastName,
              phoneNumber, email, dateOfBirth, role) {
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.dateOfBirth = dateOfBirth;
    this.role = role;

    this.setUsername = setUsername;
    this.setPassword = setPassword;
    this.setFirstName = setFirstName;
    this.setLastNmae = setLastName;
    this.setPhoneNumber = setPhoneNumber;
    this.setEmail = setEmail;
    this.setDateOfBirth = setDateOfBirth;
    this.setRole = setRole;

    this.getUsername = getUsername;
    this.getPassword = getPassword;
    this.getFirstName = getFirstName;
    this.getLastNmae = getLastName;
    this.getPhoneNumber = getPhoneNumber;
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

    function setPhoneNumber(phoneNumber) {
        self.phoneNumber = phoneNumber;
    }

    function getPhoneNumber() {
        return self.phoneNumber;
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
