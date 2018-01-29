(function() {
  'use strict';

  angular.module('cdb2').controller('NavBarController', NavBarController);

  NavBarController.$inject = ['$scope', '$translate', '$log'];

  function NavBarController($scope, $translate, $log) {
    var vm = this;
    vm.langKeys = ['en', 'fr'];
    vm.isCollapsed = true;
    vm.status = {
      isopen: false
    };

    vm.switchLang = function(langKey) {
      $log.warn('currently used lang: ' + $translate.use());
      $log.warn("switchLang called with: '" + langKey + "'");
      $translate.use(langKey);
    };

  }

})();
