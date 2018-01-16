(function() {
  'use strict';

  angular.module('cdb2').config(config);

  config.$inject = ['$logProvider', '$translateProvider', 'toastrConfig'];

  function config($logProvider, $translateProvider, toastrConfig) {
    // Enable log
    $logProvider.debugEnabled(true);
    // Initialize angular-translate
    $translateProvider.useStaticFilesLoader({prefix: 'js/i18n/', suffix: '.json'});
    $translateProvider.preferredLanguage('en');
    $translateProvider.useCookieStorage();
    $translateProvider.useSanitizeValueStrategy('escaped');
    $translateProvider.forceAsyncReload(true);

    toastrConfig.allowHtml = true;
    toastrConfig.timeOut = 3000;
    toastrConfig.positionClass = 'toast-top-right';
    toastrConfig.preventDuplicates = false;
    toastrConfig.preventOpenDuplicates = true;
    toastrConfig.progressBar = true;
  }

})();
