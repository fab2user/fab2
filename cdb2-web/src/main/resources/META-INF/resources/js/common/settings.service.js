(function () {
  'use strict';

  angular
    .module('cdb2')
    .factory('SettingsService', SettingsService);

  SettingsService.$inject = ['$localForage', 'SettingsAPIService'];

  function SettingsService($localForage, SettingsAPIService) {
    var SettingsService = {};

    SettingsService.loadSettings = function () {

      SettingsAPIService.get({}).$promise.then(function (data) {
        $localForage.setItem('country', data.country);
        if (data.nationalIdPrefix) {
          $localForage.setItem('nationalIdPrefix', data.nationalIdPrefix);
        }
      });
    };

    return SettingsService;
  }
})();