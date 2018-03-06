(function () {
  'use strict';

  angular
    .module('hub')
    .factory('CountryService', CountryService);

  CountryService.$inject = ['$http', '$log'];

  function CountryService($http, $log) {
    var CountryService = {};
    // CountryService.someValue = '';

    CountryService.sync = function (country) {
      $http.defaults.headers.common['Authorization'] = 'Basic ' + btoa(country.user + ":" + country.password);
      // 1- Query national WS
      // var headers = {
      //   'Authorization': 'Basic ' + btoa(country.user + ":" + country.password)
      // };

    //  return $http.get(country.url + '/api/cdb/send', {headers: headers, params:{countryCode: country.countryCode}})
     return $http.get(country.url + '/api/cdb/send', {params:{countryCode: country.countryCode}})
      .success(function (data) {
        $log.debug('data received from ' + country.name + ':', data);
      });
    };
    return CountryService;
  }

})();