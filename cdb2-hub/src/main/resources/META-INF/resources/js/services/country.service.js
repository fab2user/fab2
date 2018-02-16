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
      // 1- Query national WS
      var headers = {
        Authorization: 'Basic ' + btoa(country.user + ":" + country.password)
      };

      $http.get(country.url, {headers: headers})
      .success(function (data) {
        $log.debug('data received from ' + country.name + ':', data);
      });

      // TODO: 2- Push retrieved data to CDB. Now that we have the data in json, we just have to send it to CDB (probably after some reformatting ...)
      return 'ok';
    };
    return CountryService;
  }

})();