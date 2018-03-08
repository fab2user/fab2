(function() {
    'use strict';
  
    angular.module('hub').factory('CountryAPIService', CountryAPIService);
  
    CountryAPIService.$inject = ['$resource', 'SERVER'];
  
    function CountryAPIService($resource, SERVER) {
      return $resource(SERVER.API + '/country/:id', {}, {
        getAll: {
          isArray: true
        },
        getRefs: {
          isArray: true,
          url: SERVER.API + '/country/refs'
        }
      });
    }
  })();