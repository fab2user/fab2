(function () {
    'use strict';
  
    angular
      .module('cdb2')
      .factory('SettingsAPIService', SettingsAPIService);
  
      SettingsAPIService.$inject = ['$resource', 'SERVER'];
  
    function SettingsAPIService($resource, SERVER) {
      return $resource(SERVER.API + '/settings', {}, {
        getAll: {
          isArray: true
        },
      });
    }
  
  })();