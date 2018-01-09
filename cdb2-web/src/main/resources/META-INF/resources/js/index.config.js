(function() {
  'use strict';

  angular.module('cdb2').config(config);

  config.$inject = ['$logProvider', '$translateProvider'];

  function config($logProvider, $translateProvider) {
    // Enable log
    $logProvider.debugEnabled(true);
    // Initialize angular-translate
    $translateProvider.useStaticFilesLoader({prefix: 'js/i18n/', suffix: '.json'});
    $translateProvider.preferredLanguage('en');
    $translateProvider.useCookieStorage();
    $translateProvider.useSanitizeValueStrategy('escaped');
    $translateProvider.forceAsyncReload(true);
  }

})();
