(function() {
  'use strict';

  angular.module('cdb2').factory('StatusService', StatusService);

  StatusService.$inject = [
    '$rootScope',
    '$http',
    '$log',
    '$sessionStorage',
    '$state',
    'SERVER',
    'STORE',
    'EVENT',
    'SettingsService'
  ];
  // APIErrorInterceptor redirects to login page everytime we get forbidden status from the API
  function StatusService(
    $rootScope,
    $http,
    $log,
    $sessionStorage,
    $state,
    SERVER,
    STORE,
    EVENT,
    SettingsService
  ) {
    var AuthService = {};

    AuthService.login = function(username, password, previousState) {
      var headers = {
        Authorization: 'Basic ' + btoa(username + ':' + password)
      };

      $http
        .get(SERVER.ROOT + '/user', {
          headers: headers
        })
        .success(function(data) {
          if (data.name) {
            $log.info('User ' + data.name + ' successfully authenticated !');
            $sessionStorage[STORE.AUTHENTICATED] = true;
            $sessionStorage[STORE.USER] = data.name;
            $rootScope.$broadcast(EVENT.LOGGED_IN);

            // Load and store application properties
            SettingsService.loadSettings();

            // Redirect user to where he came from unless he comes from root.login. In this case, redirect him arbitrarily to root.bailiff
            var stateToGo = { name: 'root.bailiff', params: {} };
            if (previousState.name && previousState.name !== 'root.login') {
              stateToGo.name = previousState.name;
              stateToGo.params = previousState.params;
            }
            $state.go(stateToGo.name, stateToGo.params, {
              reload: true
            });
          } else {
            $sessionStorage[STORE.AUTHENTICATED] = false;
          }
        })
        .error(function() {
          $sessionStorage[STORE.AUTHENTICATED] = false;
        });
    };

    AuthService.logout = function() {
      $http
        .post(SERVER.ROOT + '/logout', {})
        .success(function() {
          $sessionStorage[STORE.AUTHENTICATED] = false;
          delete $sessionStorage[STORE.USER];
          $state.go(
            'root.login',
            {},
            {
              reload: true
            }
          );
        })
        .error(function(data) {
          $sessionStorage[STORE.AUTHENTICATED] = false;
          delete $sessionStorage[STORE.USER];
          $state.go(
            'root.login',
            {},
            {
              reload: true
            }
          );
        });
    };

    AuthService.currentUser = function(config) {
      return $sessionStorage[STORE.USER];
    };

    AuthService.deleteUserData = function() {
      $sessionStorage[STORE.AUTHENTICATED] = false;
      delete $sessionStorage[STORE.USER];
      $state.go(
        'root.login',
        {},
        {
          reload: true
        }
      );
    };

    return AuthService;
  }
})();
