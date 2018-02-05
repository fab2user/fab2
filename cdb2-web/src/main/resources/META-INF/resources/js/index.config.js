(function() {
  'use strict';

  angular.module('cdb2').config(config);

  config.$inject = ['$logProvider', '$translateProvider', '$httpProvider', '$localForageProvider', 'toastrConfig'];

  function config($logProvider, $translateProvider, $httpProvider, $localForageProvider, toastrConfig) {
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

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    $httpProvider.interceptors.push('apiErrorInterceptor');

    $localForageProvider.config({
      name        : 'cdb', // name of the database and prefix for your data, it is "lf" by default
      storeName   : 'general', // name of the table
      description : 'CDB2 storage'
  });
     
  }

})();
