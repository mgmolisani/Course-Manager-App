function UserServiceClient() {
    this.createUser = createUser;
    this.findAllUsers = findAllUsers;
    this.findUserById = findUserById;
    this.deleteUser = deleteUser;
    this.updateUser = updateUser;
    this.url = '/api/user';

    var self = this;

    function createUser(user/*, callback*/) {
        return fetch(self.url, {
            method: 'POST',
            body: JSON.stringify(user),
            headers: {
                'content-type': 'application/json'
            }
        });//.then(callback);
    }

    function findAllUsers(/*callback*/) {
        return fetch(self.url)
            .then(function (response) {
                console.log(response)
                return response.json();
            });//.then(callback);
    }

    function findUserById(userId, callback) {
        return fetch(self.url + '/' + userId)
            .then(function(response){
                return response.json();
            }).then(callback);
    }

    function updateUser(userId, user, callback) {
        return fetch(self.url + '/' + userId, {
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
        return fetch(self.url + '/' + userId, {
            method: 'DELETE'
        }).then(callback);
    }
}
