(function() {
  'use strict';

  angular.module('hub').controller('NavBarController', NavBarController);

  NavBarController.$inject = ['$scope', '$translate', '$log','$sessionStorage', 'AuthService', 'STORE', 'EVENT'];

  function NavBarController($scope, $translate, $log, $sessionStorage, AuthService, STORE, EVENT) {
    var vm = this;
    vm.langKeys = ['en', 'fr'];
    vm.isCollapsed = true;

    vm.authenticated = $sessionStorage[STORE.AUTHENTICATED];

    vm.status = {
      isopen: false
    };

    $scope.$on(EVENT.LOGGED_IN, function(){
      vm.authenticated = true;
    });

    vm.switchLang = function(langKey) {
      $log.warn('currently used lang: ' + $translate.use());
      $log.warn("switchLang called with: '" + langKey + "'");
      $translate.use(langKey);
    };

    vm.logout = function(){
      AuthService.logout();
    };
  }

})();
