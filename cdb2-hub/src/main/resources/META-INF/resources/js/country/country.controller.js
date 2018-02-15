(function () {
    'use strict';
  
    angular
      .module('hub')
      .controller('CountryController', CountryController);
  
      CountryController.$inject = ['$uibModal', 'NgTableParams', 'CountryAPIService'];
  
    function CountryController($uibModal, NgTableParams, CountryAPIService) {
      var vm = this;
      fetchCountries();


      vm.edit = function(country){
        var modalInstance = $uibModal.open({
          templateUrl: '/js/country/country.edit.html',
          controller: 'CountryEditController as countryEditCtrl',
          windowClass: 'modal-md',
          backdrop: 'static',
          resolve: {
            country: country
          }
        });
        modalInstance.result.then(function () {
          fetchCountries();
        });
      };

      function fetchCountries(){
        CountryAPIService.getAll().$promise.then(
          function(success){
            vm.tableParams = new NgTableParams({}, {dataset: success});
          }
        );
      }
    }
  })();
  