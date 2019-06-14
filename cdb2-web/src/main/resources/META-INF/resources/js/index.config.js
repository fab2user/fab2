(function() {
  'use strict';

  angular.module('cdb2').config(config);

  config.$inject = ['$logProvider', '$translateProvider', '$httpProvider', '$localForageProvider', 'toastrConfig', 'SERVER'];

  function config($logProvider, $translateProvider, $httpProvider, $localForageProvider, toastrConfig, SERVER) {
    // Enable log
    $logProvider.debugEnabled(true);
    
    // Initialize angular-translate
    $translateProvider.useUrlLoader(SERVER.ROOT + '/localisation');
    $translateProvider.preferredLanguage('en');
    $translateProvider.useCookieStorage();
    $translateProvider.useSanitizeValueStrategy('escaped');
    $translateProvider.forceAsyncReload(true);

    toastrConfig.allowHtml = true;
    toastrConfig.timeOut = 5000;
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
