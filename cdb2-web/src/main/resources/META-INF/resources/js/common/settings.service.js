(function () {
  'use strict';

  angular
    .module('cdb2')
    .factory('SettingsService', SettingsService);

  SettingsService.$inject = ['$localForage', 'SettingsAPIService', "$translate"];

  function SettingsService($localForage, SettingsAPIService, $translate) {
    var SettingsService = {};

    SettingsService.loadSettings = function () {

      SettingsAPIService.get({}).$promise.then(function (data) {
        $localForage.setItem('country', data.country);
        if (data.nationalIdPrefix) {
          $localForage.setItem('nationalIdPrefix', data.nationalIdPrefix);
        }
        $localForage.setItem('interfaceLanguage', data.i18nInterfaceLanguage);
        $translate.use(data.i18nInterfaceLanguage);
      });
    };

    return SettingsService;
  }
})();