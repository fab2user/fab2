(function () {
    'use strict';

    angular
        .module('cdb2')
        .factory('AuthService', AuthService);

        AuthService.$inject = ['$http', '$log', '$localForage', '$state'];

    function AuthService($http, $log, $localForage, $state) {
        var AuthService = {};

        AuthService.login = function (username, password) {

            var headers = { Authorization: 'Basic ' + btoa(username + ":" + password) };
      
            $http.get('http://localhost:8080/user', {
              headers: headers
            }).success(function (data) {
              if (data.name) {
                  $log.info('User ' + data.name + ' successfully authenticated !');
                  $localForage.setItem('authenticated', true).then(function(){
                    $localForage.setItem('currentUser',data.name);
                  })
                  .then(function(){
                    $state.go('root.bailiff', {}, {
                      reload: true
                    });
                  });
                  
              } else {
                $localForage.setItem('authenticated', false);
              }
            }).error(function () {
                $localForage.setItem('authenticated', false);
            });
      
          };

          AuthService.logout = function(){
            $http.post('http://localhost:8080/logout', {}).success(function() {
              $localForage.setItem('authenticated', false);
             }).error(function(data) {
              $localForage.setItem('authenticated', false);
             });
           };

        return AuthService;
    }
})();