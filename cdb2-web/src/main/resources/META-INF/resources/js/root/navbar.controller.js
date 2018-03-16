(function() {
  'use strict';

  angular.module('cdb2').controller('NavBarController', NavBarController);

  NavBarController.$inject = [
    '$scope',
    '$translate',
    '$log',
    'AuthService',
    'EVENT'
  ];

  function NavBarController($scope, $translate, $log, AuthService, EVENT) {
    var vm = this;
    vm.langKeys = ['en', 'fr'];
    vm.isCollapsed = true;

    vm.authenticated = !!AuthService.currentUser();

    vm.status = {
      isopen: false
    };

    $scope.$on(EVENT.LOGGED_IN, function() {
      vm.authenticated = true;
    });

    $scope.$on(EVENT.GEONAME_UPDATE, function(event, params) {
      vm.statusMessage = params.status;
      vm.spin = params.status !== 'OK' && params.status !== 'ERROR';
    });

    vm.switchLang = function(langKey) {
      $log.warn('currently used lang: ' + $translate.use());
      $log.warn("switchLang called with: '" + langKey + "'");
      $translate.use(langKey);
    };

    vm.logout = function() {
      AuthService.logout();
    };
  }
})();
