(function() {
  'use strict';

  angular.module('hub').factory('AuthService', AuthService);

  AuthService.$inject = [
    '$rootScope',
    '$http',
    '$log',
    '$q',
    '$sessionStorage',
    '$state',
    'SERVER',
    'STORE',
    'EVENT'
  ];
  // APIErrorInterceptor redirects to login page everytime we get forbidden status from the API
  function AuthService(
    $rootScope,
    $http,
    $log,
    $q,
    $sessionStorage,
    $state,
    SERVER,
    STORE,
    EVENT
  ) {
    var AuthService = {};

    AuthService.login = function(username, password, previousState) {
      var deferred = $q.defer();
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
            // Redirect user to where he came from unless he comes from root.login. In this case, redirect him arbitrarily to root.country
            var stateToGo = { name: 'root.country', params: {} };
            if (previousState.name && previousState.name !== 'root.login') {
              stateToGo.name = previousState.name;
              stateToGo.params = previousState.params;
            }
            $state.go(stateToGo.name, stateToGo.params, {
              reload: true
            });
            deferred.resolve(true);
          } else {
            $sessionStorage[STORE.AUTHENTICATED] = false;
            deferred.reject('Bad credentials');
          }
        })
        .error(function() {
          $sessionStorage[STORE.AUTHENTICATED] = false;
          deferred.reject('Bad credentials');
        });
      return deferred.promise;
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

    // AuthService.currentUser = function (config) {
    AuthService.currentUser = function() {
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

    AuthService.deleteUserDataWithoutReload = function() {
      $sessionStorage[STORE.AUTHENTICATED] = false;
      delete $sessionStorage[STORE.USER];
      $state.go('root.login', {}, {});
    };

    return AuthService;
  }
})();
