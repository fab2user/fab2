(function() {
    'use strict';
  
    angular.module('hub').factory('StatusAPIService', StatusAPIService);
  
    StatusAPIService.$inject = ['$resource', 'SERVER'];
  
    function StatusAPIService($resource, SERVER) {
      return $resource(SERVER.API + '/task/:id', {id: '@id'}, {
        getAll: {
          isArray: true
        },
        search: {
        },
        statuses: {
          url: SERVER.API + '/task/status',
          isArray: true
        },
        fix: {
          method: 'PUT'
        }
      });
    }
  })();