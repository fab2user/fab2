(function () {
  'use strict';

  angular
    .module('cdb2')
    .controller('LoginController', LoginController);

  LoginController.$inject = ['$rootScope', '$http'];

  function LoginController($rootScope, $http) {
    var vm = this;
    vm.credentials = {};

    vm.login = function () {

      var headers = { Authorization: 'Basic ' + btoa(vm.credentials.username + ":" + vm.credentials.password) };

      $http.get('http://localhost:8080/user', {
        headers: headers
      }).success(function (data) {
        if (data.name) {
          $rootScope.authenticated = true;
        } else {
          $rootScope.authenticated = false;
        }
      }).error(function () {
        $rootScope.authenticated = false;
      });

    };

    vm.logout = function(){
     $http.post('http://localhost:8080/logout', {}).success(function() {
        $rootScope.authenticated = false;
      }).error(function(data) {
        $rootScope.authenticated = false;
      });
    };


  }
})();