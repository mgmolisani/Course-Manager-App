function UserServiceClient() {
    this.createUser = createUser;
    this.findAllUsers = findAllUsers;
    this.findUserById = findUserById;
    this.findUserByUsername = findUserByUsername;
    this.deleteUser = deleteUser;
    this.updateUser = updateUser;
    this.register = register;
    this.login = login;
    this.updateProfile = updateProfile;
    this.userUrl = '/api/user';
    this.registerUrl = '/api/register';
    this.loginUrl = '/api/login';
    this.profileUrl = '/api/profile';

    var self = this;

    function createUser(user, callback) {
        return fetch(self.userUrl, {
            method: 'POST',
            body: JSON.stringify(user),
            headers: {
                'content-type': 'application/json'
            }
        }).then(function (response) {
            var responseJson = response.json();
            if (response.ok) {
                return responseJson.then(callback);
            }
            return responseJson.then(errorCallback);
        });
    }

    function findAllUsers(callback) {
        return fetch(self.userUrl)
            .then(function (response) {
                var responseJson = response.json();
                if (response.ok) {
                    return responseJson.then(callback);
                }
                return responseJson.then(errorCallback);
            });
    }

    function findUserById(userId, callback) {
        return fetch(self.userUrl + '/' + userId)
            .then(function (response) {
                var responseJson = response.json();
                if (response.ok) {
                    return responseJson.then(callback);
                }
                return responseJson.then(errorCallback);
            });
    }

    function findUserByUsername(username, callback) {
        return fetch(self.userUrl + '?' + username)
            .then(function (response) {
                var responseJson = response.json();
                if (response.ok) {
                    return responseJson.then(callback);
                }
                return responseJson.then(errorCallback);
            });
    }

    function updateUser(userId, user, callback) {
        return fetch(self.userUrl + '/' + userId, {
                                                      method: 'PUT',
                                                      body: JSON.stringify(user),
                                                      headers: {
                                                          'content-type': 'application/json'
                                                      }
                                                  })
            .then(function (response) {
                var responseJson = response.json();
                if (response.ok) {
                    return responseJson.then(
                        callback);
                }
                return responseJson.then(
                    errorCallback);
            });
    }

    function updateProfile(userId, user, callback) {
        return fetch(self.profileUrl + '/' + userId, {
                                                         method: 'PUT',
                                                         body: JSON.stringify(user),
                                                         headers: {
                                                             'content-type': 'application/json'
                                                         }
                                                     })
            .then(function (response) {
                var responseJson = response.json();
                if (response.ok) {
                    return responseJson.then(
                        callback);
                }
                return responseJson.then(
                    errorCallback);
            });
    }

    function deleteUser(userId, callback) {
        return fetch(self.userUrl + '/' + userId, {
                                                      method: 'DELETE'
                                                  }).then(callback);
    }

    function register(user, callback) {
        return fetch(self.registerUrl, {
            method: 'POST',
            body: JSON.stringify(user),
            headers: {
                'content-type': 'application/json'
            }
        }).then(function (response) {
            var responseJson = response.json();
            if (response.ok) {
                return responseJson.then(callback);
            }
            return responseJson.then(errorCallback);
        });
    }

    function login(user, callback) {
        return fetch(self.loginUrl, {
            method: 'POST',
            body: JSON.stringify(user),
            headers: {
                'content-type': 'application/json'
            }
        }).then(function (response) {
            var responseJson = response.json();
            if (response.ok) {
                return responseJson.then(callback);
            }
            return responseJson.then(errorCallback);
        });
    }

    function errorCallback(data) {
        console.log(data);
        throw new Error(data.message);
    }
}
