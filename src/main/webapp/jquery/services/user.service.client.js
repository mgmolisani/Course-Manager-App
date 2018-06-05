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
    this.forgotPassword = forgotPassword;
    this.updatePassword = updatePassword;
    this.userUrl = '/api/user';
    this.registerUrl = '/api/register';
    this.loginUrl = '/api/login';
    this.profileUrl = '/api/profile';
    this.passwordUrl = '/api/password/';

    var self = this;

    /**
     * Sends a create user request
     * @param user the user to create
     * @param callback
     * @returns {Promise<Response>}
     */
    function createUser(user, callback) {
        return fetch(self.userUrl, {
            method: 'POST',
            body: JSON.stringify(user),
            credentials: 'include',
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

    /**
     * Sends a findAllUsers request
     * @param callback
     * @returns {Promise<Response>}
     */
    function findAllUsers(callback) {
        return fetch(self.userUrl,{
            credentials: 'include'})
            .then(function (response) {
                var responseJson = response.json();
                if (response.ok) {
                    return responseJson.then(callback);
                }
                return responseJson.then(errorCallback);
            });
    }

    /**
     * Sends a findUserById request
     * @param userId the user ID
     * @param callback
     * @returns {Promise<Response>}
     */
    function findUserById(userId, callback) {
        return fetch(self.userUrl + '/' + userId,{
                                                     credentials: 'include'})
            .then(function (response) {
                var responseJson = response.json();
                if (response.ok) {
                    return responseJson.then(callback);
                }
                return responseJson.then(errorCallback);
            });
    }

    /**
     * Sends a findUserByUsername request
     * @param username the username of the user to find
     * @param callback
     * @returns {Promise<Response>}
     */
    function findUserByUsername(username, callback) {
        return fetch(self.userUrl + '?' + username,{
                                                       credentials: 'include'})
            .then(function (response) {
                var responseJson = response.json();
                if (response.ok) {
                    return responseJson.then(callback);
                }
                return responseJson.then(errorCallback);
            });
    }

    /**
     * Sends an updateUser request
     * @param userId the user ID to update
     * @param user the information to use to update the user
     * @param callback
     * @returns {Promise<Response>}
     */
    function updateUser(userId, user, callback) {
        return fetch(self.userUrl + '/' + userId, {
                                                      method: 'PUT',
                                                      body: JSON.stringify(user),
                                                      credentials: 'include',
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

    /**
     * Sends an updateProfile request
     * @param userId the user ID to update
     * @param user the information to use to update the user
     * @param callback
     * @returns {Promise<Response>}
     */
    function updateProfile(userId, user, callback) {
        return fetch(self.profileUrl + '/' + userId, {
                                                         method: 'PUT',
                                                         body: JSON.stringify(user),
                                                         credentials: 'include',
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

    /**
     * Sends a deleteUser request
     * @param userId the user ID to delete
     * @param callback
     * @returns {Promise<Response>}
     */
    function deleteUser(userId, callback) {
        return fetch(self.userUrl + '/' + userId, {
                                                      method: 'DELETE',
                                                      credentials: 'include'
                                                  })
            .then(function (response) {
                if (response.ok) {
                    return response.text().then(callback);
                }
                return response.json().then(errorCallback);
            });
    }

    /**
     * Sends a registerUser request
     * @param user the user to register
     * @param callback
     * @returns {Promise<Response>}
     */
    function register(user, callback) {
        return fetch(self.registerUrl, {
            method: 'POST',
            body: JSON.stringify(user),
            credentials: 'include',
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

    /**
     * Sends a login request
     * @param user the user to login
     * @param callback
     * @returns {Promise<Response>}
     */
    function login(user, callback) {
        return fetch(self.loginUrl, {
            method: 'POST',
            body: JSON.stringify(user),
            credentials: 'include',
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

    /**
     * Send a forgotPassword request
     * @param username
     * @param callback
     * @returns {Promise<Response>}
     */
    function forgotPassword(username, callback) {
        return fetch(self.passwordUrl + "/" + username,{
                                                           credentials: 'include'
        })
            .then(function (response) {
                if (response.ok) {
                    return response.text().then(callback);
                }
                return response.json().then(errorCallback);
            });
    }

    /**
     * Sends the updatePassword request.
     * @param userId
     * @param password
     * @param callback
     * @returns {Promise<Response>}
     */
    function updatePassword(userId, password, callback) {
        return fetch(self.passwordUrl + '/' + userId, {
                                                          method: 'PUT',
                                                          body: password,
                                                          credentials: 'include',
                                                          headers: {
                                                              'content-type': 'text/plain'
                                                          }
                                                      }).then(function (response) {
            var responseJson = response.json();
            if (response.ok) {
                return responseJson.then(callback);
            }
            return responseJson.then(errorCallback);
        });
    }

    /**
     * A generic error callback to handle errors sent by the middle tier responses to the requests
     * @param data the returned error object to handle
     */
    function errorCallback(data) {
        console.log(data);
        throw new Error(data.message);
    }
}
