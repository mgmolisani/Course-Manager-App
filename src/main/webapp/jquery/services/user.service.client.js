function UserServiceClient() {
    this.createUser = createUser;
    this.findAllUsers = findAllUsers;
    this.findUserById = findUserById;
    this.deleteUser = deleteUser;
    this.updateUser = updateUser;
    this.userUrl = '/api/user';
    this.registerUrl = '/api/register';

    var self = this;

    function createUser(user, callback) {
        console.log(JSON.stringify(user));
        return fetch(self.userUrl, {
            method: 'POST',
            body: JSON.stringify(user),
            headers: {
                'content-type': 'application/json'
            }
        }).then(callback);
    }

    function findAllUsers(callback) {
        return fetch(self.userUrl)
            .then(function (response) {
                return response.json();
            }).then(callback);
    }

    function findUserById(userId, callback) {
        return fetch(self.userUrl + '/' + userId)
            .then(function(response){
                return response.json();
            }).then(callback);
    }

    function updateUser(userId, user, callback) {
        return fetch(self.userUrl + '/' + userId, {
            method: 'PUT',
            body: JSON.stringify(user),
            headers: {
                'content-type': 'application/json'
            }
        })
            .then(function(response){
                if(response.bodyUsed) {
                    return response.json();
                } else {
                    return null;
                }
            }).then(callback);
    }

    function deleteUser(userId, callback) {
        return fetch(self.userUrl + '/' + userId, {
            method: 'DELETE'
        }).then(callback);
    }

    function register(user, callback) {
        return fetch(self.registerUrl, {
            method: 'POST',
            body: JSON.stringify({
                                     username: username,
                                     password: password
                                 }),
            headers: {
                'content-type': 'application/json'
            }
        }).then(function(response) {
            if(response.bodyUsed) {
                return response.json();
            } else {
                return null;
            }}).then(callback)
    }
}
