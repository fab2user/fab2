(function() {
  'use strict';

  angular.module('cdb2').factory('AuthService', AuthService);

  AuthService.$inject = [
    '$rootScope',
    '$http',
    '$log',
    '$q',
    '$sessionStorage',
    '$state',
    '$uibModal',
    '$cookies',
    'SERVER',
    'STORE',
    'EVENT',
    'SettingsService'
  ];
  // APIErrorInterceptor redirects to login page everytime we get forbidden status from the API
  function AuthService(
    $rootScope,
    $http,
    $log,
    $q,
    $sessionStorage,
    $state,
    $uibModal,
    $cookies,
    SERVER,
    STORE,
    EVENT,
    SettingsService
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
            if (!$cookies.get('fab-tos')) {
              var modalTos = $uibModal.open({
                templateUrl: '/js/home/tos.html',
                controller: 'TosController as tosCtrl'
              });
              modalTos.result.then(
                function() {
                  next(data, previousState, deferred);
                },
                function() {
                  deferred.reject('TOS not accepted');
                }
              );
            } else {
              next(data, previousState, deferred);
            }
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

    function next(data, previousState, deferred) {
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
      deferred.resolve(true);
    }

    AuthService.logout = function() {
      $http
        .post(SERVER.ROOT + '/logout', {})
        .success(function() {
          $log.info('User ' + data.name + ' successfully logout !');
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
          $log.info('Error during logout !');
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

    AuthService.deleteUserDataWithoutReload = function() {
      $sessionStorage[STORE.AUTHENTICATED] = false;
      delete $sessionStorage[STORE.USER];
      $state.go('root.login', {}, {});
    };

    return AuthService;
  }
})();
