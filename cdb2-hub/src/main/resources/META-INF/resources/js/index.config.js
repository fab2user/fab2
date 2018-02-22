(function() {
  'use strict';

  angular.module('hub').config(config);

  config.$inject = ['$logProvider', '$translateProvider', '$httpProvider', '$localStorageProvider', 'toastrConfig', 'SERVER'];

  function config($logProvider, $translateProvider, $httpProvider, $localStorageProvider, toastrConfig, SERVER) {
    // Enable log
    $logProvider.debugEnabled(true);
    
    // Initialize angular-translate
    $translateProvider.useUrlLoader(SERVER.ROOT + '/localisation');
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

    $localStorageProvider.setKeyPrefix('hub');
     
  }

})();
