(function () {
  'use strict';

  angular
    .module('cdb2')
    .factory('MunicipalityAPIService', MunicipalityAPIService);

  MunicipalityAPIService.$inject = ['$resource', 'SERVER'];

  function MunicipalityAPIService($resource, SERVER) {
    return $resource(SERVER.API + '/municipality/:id', {}, {
      getAll: {
        isArray: true,
        cache: true //TODO: Check if localforage would be of any value here
      },
      search: {
        isArray: true
      }
    });
  }

})();
