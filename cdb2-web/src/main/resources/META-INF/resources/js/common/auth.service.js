(function () {
  'use strict';

  angular
    .module('cdb2')
    .factory('AuthService', AuthService);

  AuthService.$inject = ['$rootScope', '$http', '$log', '$localForage', '$state', '$q', 'SERVER', 'STORE', 'EVENT'];
  // APIErrorInterceptor redirects to login page everytime we get forbidden status from the API
  function AuthService($rootScope, $http, $log, $localForage, $state, $q, SERVER, STORE, EVENT) {
    var AuthService = {};

    AuthService.login = function (username, password, previousState) {

      var headers = {
        Authorization: 'Basic ' + btoa(username + ":" + password)
      };

      $http.get(SERVER.ROOT + '/user', {
        headers: headers
      }).success(function (data) {
        if (data.name) {
          $log.info('User ' + data.name + ' successfully authenticated !');
          $localForage.setItem(STORE.AUTHENTICATED, true).then(function () {
              $localForage.setItem(STORE.USER, data.name).then(function(){
                $rootScope.$broadcast(EVENT.LOGGED_IN);
              });
            })
            .then(function () {
              // Redirect user to where he came from unless he comes from root.login. In this case, redirect him arbitrarily to root.bailiff
              var stateToGo = {name: 'root.bailiff', params: {}};
              if(previousState.name !== 'root.login'){
                stateToGo.name = previousState.name;
                stateToGo.params = previousState.params;
              }
              $state.go(stateToGo.name, stateToGo.params, {
                reload: true
              });
            });

        } else {
          $localForage.setItem(STORE.AUTHENTICATED, false);
        }
      }).error(function () {
        $localForage.setItem(STORE.AUTHENTICATED, false);
      });

    };

    AuthService.logout = function () {
      $http.post(SERVER.ROOT + '/logout', {}).success(function () {
        $localForage.setItem(STORE.AUTHENTICATED, false);
        $localForage.removeItem(STORE.USER);
        $state.go('root.login', {}, {
          reload: true
        });
      }).error(function (data) {
        $localForage.setItem(STORE.AUTHENTICATED, false);
        $localForage.removeItem(STORE.USER);
        $state.go('root.login', {}, {
          reload: true
        });
      });
    };

    AuthService.currentUser = function (config) {
      var deferred = $q.defer();
      $localForage
        .getItem(STORE.USER)
        .then(function (user) {
          deferred.resolve(user);
        });
      return deferred.promise;
    };

    AuthService.deleteUserData = function () {
      $localForage.setItem(STORE.AUTHENTICATED, false);
      $localForage.removeItem(STORE.USER);
      $state.go('root.login', {}, {
        reload: true
      });
    };

    return AuthService;
  }
})();