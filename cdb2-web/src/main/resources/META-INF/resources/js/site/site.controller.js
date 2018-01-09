(function() {
  'use strict';

  angular.module('cdb2').controller('SiteController', SiteController);

  SiteController.$inject = ['$scope', '$translate', '$log'];

  function SiteController($scope, $translate, $log) {
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
      $translate.refresh();
    };

  }

})();
