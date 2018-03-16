(function() {
  'use strict';

  angular.module('cdb2').factory('StatusAPIService', StatusAPIService);

  StatusAPIService.$inject = ['$resource', 'SERVER'];

  function StatusAPIService($resource, SERVER) {
    return $resource(SERVER.API + '/task/geoname/:id', {}, {});
  }
})();
