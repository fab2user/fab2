(function() {
  'use strict';

  angular.module('cdb2').factory('InstrumentAPIService', InstrumentAPIService);

  InstrumentAPIService.$inject = ['$resource', 'SERVER0'];

  function InstrumentAPIService($resource, SERVER) {
    return $resource(SERVER.API + '/instrument/:id', {}, {
      getAll: {
        isArray: true
      }
    });
  }

})();
