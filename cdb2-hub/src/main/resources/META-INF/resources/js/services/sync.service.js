(function () {
  'use strict';

  angular
    .module('hub')
    .factory('SyncService', SyncService);

    SyncService.$inject = ['$http', '$log', 'SERVER'];

  function SyncService($http, $log, SERVER) {
    var SyncService = {};
    // CountryService.someValue = '';

    SyncService.sync = function (country) {
      // $http.defaults.headers.common['Authorization'] = 'Basic ' + btoa(country.user + ":" + country.password);
      // 1- Query national WS
      // var headers = {
      //   'Authorization': 'Basic ' + btoa(country.user + ":" + country.password)
      // };

    //  return $http.get(country.url + '/api/cdb/send', {headers: headers, params:{countryCode: country.countryCode}})
     return $http.get(SERVER.API + '/cdb/send', {params:{countryCode: country.countryCode}})
      .success(function (data) {
        $log.debug('data received from ' + country.name + ':', data);
      });
    };
    return SyncService;
  }

})();