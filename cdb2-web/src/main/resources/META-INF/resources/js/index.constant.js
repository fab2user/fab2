(function () {
    'use strict';

    angular
        .module('cdb2')
        .constant('SERVER', {
            'ROOT': 'http://localhost:8080',
            'API': 'http://localhost:8080/api'
        })
        .constant('STORE', {
            'USER' :'connected_user',
            'AUTHENTICATED': 'authenticated'
        })
        .constant('EVENT', {
            'LOGGED_IN': 1
        });
})();